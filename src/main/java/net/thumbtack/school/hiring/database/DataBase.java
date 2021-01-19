package net.thumbtack.school.hiring.database;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;

import java.io.Serializable;
import java.util.*;

public final class DataBase implements Serializable {
    private static DataBase instance = null;
    private Map<String, User> allUsers;//String - логин пользователя, User - пользователь
    private Map<String, User> activeUsers;//string - id объекта, User - пользователь
    private Set<String> demandSkills;
    private Multimap<Skill, Employee> userSkills;//?string - скилл,Employee - пользователь у которого он есть
    private Multimap<Requirement, Vacancy> requirementsVacancies;

    //добавить мап скилл-объекты
    private DataBase() {
        allUsers = new HashMap<>();
        activeUsers = new HashMap<>();
        demandSkills = new HashSet<>();
        userSkills = TreeMultimap.create((o1, o2) -> {
            int i = o1.getName().compareTo(o2.getName());
            if (i != 0) {
                return i;
            }
            return o1.getLevel() - o2.getLevel();
        }, (o1, o2) -> {
            return o1.getLastName().compareTo(o2.getLastName());
        });
        requirementsVacancies = TreeMultimap.create((o1, o2) -> {
            int i = o1.getNecessary().compareTo(o2.getNecessary());
            if (i != 0) {
                return i;
            } else if (o1.getName().compareTo(o2.getName()) != 0) {
                return o1.getName().compareTo(o2.getName());
            }
            return o1.getLevel() - o2.getLevel();
        }, (o1, o2) -> {
            return o1.getName().compareTo(o2.getName());
        });
    }

    public static DataBase getInstance() {
        if (instance == null) {
            return instance = new DataBase();
        }
        return instance;
    }

    public static void setInstance(DataBase newInstance) {
        instance = newInstance;
    }

    public String registerUser(User user) throws ServerException {
        if (allUsers.putIfAbsent(user.getLogin(), user) != null) {
            throw new ServerException(ErrorCode.BUSY_LOGIN_EXCEPTION);
        }
        String token = UUID.randomUUID().toString();
        activeUsers.put(token, user);
        if (user instanceof Employee) {
            Employee employee = (Employee) user;
            for (Skill skill : employee.getAttainmentsList()) {
                userSkills.put(skill, employee);
            }
        }
        if (user instanceof Employer) {
            Employer employer = (Employer) user;
            for (Vacancy vacancy : employer.getAllVacancies()) {
                this.addVacancy(vacancy, token);
            }
        }
        return token;
    }

    public String loginUser(String login, String password) throws ServerException {
        User user = allUsers.get(login);
        if (user == null) {
            throw new ServerException(ErrorCode.NOT_FOUND_USER);
        }
        if (!user.getPassword().equals(password)) {
            throw new ServerException(ErrorCode.WRONG_PASSWORD);
        }
        if (activeUsers.containsValue(user)) {
            throw new ServerException(ErrorCode.ALREADY_LOGGED_IN);
        }
        String token = UUID.randomUUID().toString();
        activeUsers.put(token, user);
        return token;
    }

    public void removeAccount(String token) throws ServerException {
        User user = activeUsers.get(token);
        if (user == null) {
            throw new ServerException(ErrorCode.NOT_FOUND_USER);
        }
        if (user instanceof Employer) {
            allUsers.remove(user.getLogin());
            activeUsers.remove(token, user);
            Employer employer = (Employer) user;
            for (Vacancy vacancy : employer.getAllVacancies()) {
                for (Requirement requirement : vacancy.getRequirements()) {
                    requirementsVacancies.remove(requirement, vacancy);
                }
            }
        }
        if (user instanceof Employee) {
            allUsers.remove(user.getLogin());
            activeUsers.remove(token, user);
            Employee employee = (Employee) user;
            for (Skill skill : employee.getAttainmentsList()) {
                userSkills.remove(skill, employee);
            }
        }
    }

    public void logoutUser(String token) {
        activeUsers.remove(token);
    }

    public User getUserById(String id) throws ServerException {
        User user = activeUsers.get(id);
        if (user == null) {
            throw new ServerException(ErrorCode.NO_SUCH_USER);
        }
        return user;
    }

    public void saveUser(User user) {
        allUsers.put(user.getLogin(), user);
    }

    public void addVacancy(Vacancy vacancy, String token) throws ServerException {
        Employer employer = (Employer) getUserById(token);
        employer.addVacancy(vacancy);
        saveUser(employer);
        activeUsers.replace(token, employer);
        allUsers.replace(employer.getLogin(), employer);
        for (Requirement requirement : vacancy.getRequirements()) {
            requirementsVacancies.put(requirement, vacancy);
        }
        this.addSubSet(vacancy.getNamesRequirements());
    }

    public void removeVacancy(String name, String token) throws ServerException {
        Employer employer = (Employer) getUserById(token);
        employer.removeVacancy(name);
        saveUser(employer);
        activeUsers.put(token, employer);
    }

    public void addSkillForEmployee(Skill attainment, String token) throws ServerException {
        this.addDemandSkill(attainment.getName());//добавляю всегда но так как умения/требования хранятся в Set повторяющиеся будут удаляться
        Employee employee = (Employee) getUserById(token);
        if (employee.getStatus().equals(EmployeeStatus.ACTIVE)) {
            employee.getAttainmentsList().forEach(skill -> userSkills.remove(skill, employee));

            employee.addAttainments(attainment);

            employee.getAttainmentsList().forEach(skill -> userSkills.put(skill, employee));

        } else {
            employee.addAttainments(attainment);
        }
        saveUser(employee);
        activeUsers.put(token, employee);
    }

    public void updateEmployeeSkill(String token, String oldNameSkill, Skill newSkill) throws ServerException {
        Employee employee = (Employee) getUserById(token);
        employee.updateAttainments(oldNameSkill, newSkill);
        saveUser(employee);
        activeUsers.put(token, employee);
    }

    public void removeEmployeeSkill(String token, String name) throws ServerException {
        Employee employee = (Employee) activeUsers.get(token);
        Skill skill = employee.getSkillByName(name);
        employee.removeAttainments(skill);
        saveUser(employee);
        activeUsers.replace(token, employee);
        allUsers.replace(employee.getLogin(), employee);
    }

    public void addRequirementInVacancy(Requirement requirement, String token, String nameVacancy) throws ServerException {
        Employer employer = (Employer) getUserById(token);
        requirementsVacancies.removeAll(employer);
        employer.getVacancyByName(nameVacancy).addRequirement(requirement);
        for (Vacancy vacancy : employer.getAllVacancies()) {
            for (Requirement requirement1 : vacancy.getRequirements()) {
                requirementsVacancies.put(requirement1, vacancy);
            }
        }
        saveUser(employer);
        activeUsers.put(token, employer);
    }

    public void removeRequirementOfVacancy(String token, String nameVacancy, String nameRequirement) throws ServerException {
        Employer employer = (Employer) getUserById(token);
        employer.getVacancyByName(nameVacancy).removeDemand(nameRequirement);
        saveUser(employer);
        activeUsers.put(token, employer);
    }

    public List<Vacancy> getAllVacanciesByToken(String token) throws ServerException {
        Employer employer = (Employer) this.getUserById(token);
        Set<Vacancy> vacancies = new HashSet<>(employer.getAllVacancies());
        return new ArrayList<>(vacancies);
    }

    public List<Vacancy> getAllActiveVacanciesByToken(String token) throws ServerException {
        Employer employer = (Employer) this.getUserById(token);
        Set<Vacancy> outList = new HashSet<>(employer.getAllVacancies());
        outList.removeIf(vacancy -> vacancy.getStatus().equals(VacancyStatus.NOT_ACTIVE));
        return new ArrayList<>(outList);
    }

    public List<Vacancy> getAllNotActiveVacanciesByToken(String token) throws ServerException {
        Employer employer = (Employer) this.getUserById(token);
        Set<Vacancy> outList = new HashSet<>(employer.getAllVacancies());
        outList.removeIf(vacancy -> vacancy.getStatus().equals(VacancyStatus.ACTIVE));
        return new ArrayList<>(outList);
    }

    public List<Vacancy> getVacanciesListNotLess(List<Skill> skills) {
        NavigableMap<Requirement, Collection<Vacancy>> navigableMap = ((TreeMultimap<Requirement, Vacancy>) requirementsVacancies).asMap();
        Collection<Vacancy> vacancies = new HashSet<>();
        Set<Map.Entry<Requirement, Collection<Vacancy>>> entrySet;
        int i;
        for (Skill skill : skills) {
            entrySet = navigableMap.subMap(new Requirement(skill.getName(), 0, ConditionsRequirements.NECESSARY), new Requirement(skill.getName(), skill.getLevel() + 1, ConditionsRequirements.NECESSARY)).entrySet();
            for (Map.Entry<Requirement, Collection<Vacancy>> entry : entrySet) {
                vacancies.addAll(entry.getValue());
            }
            entrySet = navigableMap.subMap(new Requirement(skill.getName(), 0, ConditionsRequirements.NOT_NECESSARY), new Requirement(skill.getName(), skill.getLevel() + 1, ConditionsRequirements.NOT_NECESSARY)).entrySet();
            for (Map.Entry<Requirement, Collection<Vacancy>> entry : entrySet) {
                vacancies.addAll(entry.getValue());
            }
        }
        for (Vacancy vacancy : new HashSet<>(vacancies)) {
            for (Requirement requirement : vacancy.getRequirements()) {
                i = 0;
                for (Skill skill : skills) {
                    if (skill.getName().equals(requirement.getName()) && skill.getLevel() >= requirement.getLevel()) {
                        break;
                    }
                    i++;
                }
                if (i == skills.size()) {
                    vacancies.remove(vacancy);
                    break;
                }
            }
        }
        return new ArrayList<>(vacancies);
    }

    public List<Vacancy> getVacanciesListObligatoryDemand(List<Skill> skills) {
        NavigableMap<Requirement, Collection<Vacancy>> navigableMap = ((TreeMultimap<Requirement, Vacancy>) requirementsVacancies).asMap();
        Collection<Vacancy> vacancies = new HashSet<>();
        Set<Map.Entry<Requirement, Collection<Vacancy>>> entrySet;
        int i;
        for (Skill skill : skills) {
            entrySet = navigableMap.subMap(new Requirement(skill.getName(), 0, ConditionsRequirements.NECESSARY), new Requirement(skill.getName(), skill.getLevel() + 1, ConditionsRequirements.NECESSARY)).entrySet();
            for (Map.Entry<Requirement, Collection<Vacancy>> entry : entrySet) {
                vacancies.addAll(entry.getValue());
            }
        }
        for (Vacancy vacancy : new HashSet<>(vacancies)) {
            for (Requirement requirement : vacancy.getRequirements()) {
                i = 0;
                for (Skill skill : skills) {
                    if (skill.getName().equals(requirement.getName()) && skill.getLevel() >= requirement.getLevel() && requirement.getNecessary().equals(ConditionsRequirements.NECESSARY)) {
                        break;
                    }
                    i++;
                }
                if (i == skills.size()) {
                    vacancies.remove(vacancy);
                    break;
                }
            }
        }
        return new ArrayList<>(vacancies);
    }

    public List<Vacancy> getVacanciesListOnlyName(List<Skill> skills) {
        NavigableMap<Requirement, Collection<Vacancy>> navigableMap = ((TreeMultimap<Requirement, Vacancy>) requirementsVacancies).asMap();
        Collection<Vacancy> vacancies = new HashSet<>(requirementsVacancies.values());
        Collection<Collection<Vacancy>> temporalVacanciesCollection;
        Set<String> skillsNames = new HashSet<>();
        for (Skill skill : skills) {
            skillsNames.add(skill.getName());
        }
        for (Skill skill : skills) {
            temporalVacanciesCollection = navigableMap.subMap(new Requirement(skill.getName(), 0, ConditionsRequirements.NECESSARY), new Requirement(skill.getName(), 6, ConditionsRequirements.NECESSARY)).values();
            for (Collection<Vacancy> vacancies1 : temporalVacanciesCollection) {
                vacancies.addAll(vacancies1);
            }
            temporalVacanciesCollection = navigableMap.subMap(new Requirement(skill.getName(), 0, ConditionsRequirements.NOT_NECESSARY), new Requirement(skill.getName(), 6, ConditionsRequirements.NOT_NECESSARY)).values();
            for (Collection<Vacancy> vacancies1 : temporalVacanciesCollection) {
                vacancies.addAll(vacancies1);
            }
        }
        vacancies.removeIf(vacancy -> !skillsNames.containsAll(vacancy.getNamesRequirements()));
        return new ArrayList<>(vacancies);
    }

    public List<Vacancy> getVacanciesListWithOneDemand(List<Skill> skills) {
        NavigableMap<Requirement, Collection<Vacancy>> navigableMap = ((TreeMultimap<Requirement, Vacancy>) requirementsVacancies).asMap();
        Collection<Vacancy> vacancies = new HashSet<>();
        Collection<Collection<Vacancy>> temporalVacanciesCollection;
        for (Skill skill : skills) {

            temporalVacanciesCollection = navigableMap.subMap(new Requirement(skill.getName(), 0, ConditionsRequirements.NECESSARY), new Requirement(skill.getName(), skill.getLevel() + 1, ConditionsRequirements.NECESSARY)).values();
            for (Collection<Vacancy> vacancies1 : temporalVacanciesCollection) {
                vacancies.addAll(vacancies1);
            }
            temporalVacanciesCollection = navigableMap.subMap(new Requirement(skill.getName(), 0, ConditionsRequirements.NOT_NECESSARY), new Requirement(skill.getName(), skill.getLevel() + 1, ConditionsRequirements.NOT_NECESSARY)).values();
            for (Collection<Vacancy> vacancies1 : temporalVacanciesCollection) {
                vacancies.addAll(vacancies1);
            }
        }
        return new ArrayList<>(vacancies);

    }

    public List<Employee> getEmployeesListNotLess(List<Requirement> requirements) {
        Set<Employee> employees = new HashSet<>(userSkills.values());
        for (Requirement requirement : requirements) {
            List<Collection<Employee>> collection = new ArrayList<>(((TreeMultimap) userSkills).asMap().subMap(new Skill(requirement.getName(), requirement.getLevel()), new Skill(requirement.getName(), 6)).values());
            employees.retainAll(collection.get(0));
        }
        return new ArrayList<>(employees);
    }

    public List<Employee> getEmployeesListObligatoryDemand(List<Requirement> requirements) {
        List<Requirement> obligatoryRequirement = new ArrayList<>();
        for (Requirement requirement : requirements) {
            if (requirement.getNecessary().equals(ConditionsRequirements.NECESSARY)) {
                obligatoryRequirement.add(requirement);
            }
        }
        return this.getEmployeesListNotLess(obligatoryRequirement);
    }

    public List<Employee> getEmployeesListOnlyName(List<Requirement> requirements) {
        Set<Employee> employees = new HashSet<>(userSkills.values());
        for (Requirement requirement : requirements) {
            List<Collection<Employee>> collection = new ArrayList<>(((TreeMultimap) userSkills).asMap().subMap(new Skill(requirement.getName(), 1), new Skill(requirement.getName(), 6)).values());
            employees.retainAll(collection.get(0));
        }
        return new ArrayList<>(employees);
    }

    public List<Employee> getEmployeesListWithOneDemand(List<Requirement> requirements) {
        Set<Employee> employees = new HashSet<>();
        for (Requirement requirement : requirements) {
            List<Collection<Employee>> collection = new ArrayList<>(((TreeMultimap) userSkills).asMap().subMap(new Skill(requirement.getName(), requirement.getLevel()), new Skill(requirement.getName(), 6)).values());
            employees.addAll(collection.get(0));
        }
        return new ArrayList<>(employees);
    }

    public Set<String> getDemandSkillsSet() {
        return Collections.unmodifiableSet(demandSkills);
    }

    public void addSubSet(Set<String> subSet) {
        demandSkills.addAll(subSet);
    }

    public void addDemandSkill(String nameDemandSkill) {
        demandSkills.add(nameDemandSkill);
    }

    public void disableEmployee(String token) throws ServerException {
        Employee employee = (Employee) activeUsers.get(token);
        if (employee.getStatus().equals(EmployeeStatus.NOT_ACTIVE)) {
            throw new ServerException(ErrorCode.EMPLOYEE_DISABLE);
        }
        userSkills.removeAll(employee);
        employee.setStatus(EmployeeStatus.NOT_ACTIVE);
        activeUsers.replace(token, employee);
        allUsers.replace(employee.getLogin(), employee);
    }

    public void enableEmployee(String token) throws ServerException {
        Employee employee = (Employee) activeUsers.get(token);
        if (employee.getStatus().equals(EmployeeStatus.ACTIVE)) {
            throw new ServerException(ErrorCode.EMPLOYEE_ENABLE);
        }
        for (Skill skill : employee.getAttainmentsList()) {
            userSkills.put(skill, employee);
        }
        employee.setStatus(EmployeeStatus.ACTIVE);
        activeUsers.replace(token, employee);
        allUsers.replace(employee.getLogin(), employee);
    }

    public void disableVacancy(String idVacancy, String tokenEmployer) throws ServerException {
        Employer employer = (Employer) this.getUserById(tokenEmployer);
        Vacancy vacancy = employer.getVacancyById(idVacancy);
        if (vacancy.getStatus() == VacancyStatus.NOT_ACTIVE) {
            throw new ServerException(ErrorCode.VACANCY_DISABLE);
        }
        vacancy.getRequirements().forEach(requirement -> requirementsVacancies.remove(requirement, vacancy));
        vacancy.setStatus(VacancyStatus.NOT_ACTIVE);
        employer.removeVacancy(vacancy.getName());
        employer.addVacancy(vacancy);
        activeUsers.replace(tokenEmployer, employer);
        allUsers.replace(employer.getLogin(), employer);
    }

    public void enableVacancy(String idVacancy, String tokenEmployer) throws ServerException {
        Employer employer = (Employer) this.getUserById(tokenEmployer);
        Vacancy vacancy = (Vacancy) employer.getVacancyById(idVacancy);
        if (vacancy.getStatus() == VacancyStatus.ACTIVE) {
            throw new ServerException(ErrorCode.VACANCY_ENABLE);
        }
        for (Requirement requirement : vacancy.getRequirements()) {
            requirementsVacancies.put(requirement, vacancy);
        }
        vacancy.setStatus(VacancyStatus.ACTIVE);
        employer.removeVacancy(vacancy.getName());
        employer.addVacancy(vacancy);
        activeUsers.replace(tokenEmployer, employer);
        allUsers.replace(employer.getLogin(), employer);
    }

    public void updateEmployee(String token, Employee newEmployee) throws ServerException {
        Employee employee = (Employee) this.getUserById(token);
        if (!employee.getStatus().equals(newEmployee.getStatus())) {
            throw new ServerException(ErrorCode.BAN_UPDATE_STATUS);
        }
        newEmployee.setLogin(employee.getLogin());//логин оставляем обязательно старый т.к. менять нельзя

        if (newEmployee.getFirstName() == null) {
            newEmployee.setFirstName(employee.getFirstName());
        }
        if (newEmployee.getPatronymic() == null) {
            newEmployee.setPatronymic(employee.getPatronymic());
        }
        if (newEmployee.getLastName() == null) {
            newEmployee.setLastName(employee.getLastName());
        }
        if (newEmployee.getEmail() == null) {
            newEmployee.setEmail(employee.getEmail());
        }
        if (newEmployee.getPassword() == null) {
            newEmployee.setPassword(employee.getPassword());
        }
        if (newEmployee.getAttainmentsList() == null) {
            newEmployee.setAttainmentsList(employee.getAttainmentsList());
        }
        if (newEmployee.getStatus().equals(EmployeeStatus.ACTIVE)) {
            employee.getAttainmentsList().forEach(skill -> userSkills.remove(skill, employee));
            newEmployee.getAttainmentsList().forEach(skill -> userSkills.put(skill, newEmployee));
        }
        allUsers.replace(employee.getLogin(), employee, newEmployee);
        activeUsers.replace(token, employee, newEmployee);
    }

    public void updateEmployer(String token, Employer newEmployer) throws ServerException {
        Employer employer = (Employer) this.getUserById(token);

        if (newEmployer.getName() == null) {
            newEmployer.setName(employer.getName());
        }
        if (newEmployer.getFirstName() == null) {
            newEmployer.setFirstName(employer.getFirstName());
        }
        if (newEmployer.getPatronymic() == null) {
            newEmployer.setPatronymic(employer.getPatronymic());
        }
        if (newEmployer.getLastName() == null) {
            newEmployer.setLastName(employer.getLastName());
        }
        if (newEmployer.getAddress() == null) {
            newEmployer.setAddress(employer.getAddress());
        }
        if (newEmployer.getEmail() == null) {
            newEmployer.setEmail(employer.getEmail());
        }
        if (newEmployer.getPassword() == null) {
            newEmployer.setPassword(employer.getPassword());
        }
        newEmployer.setVacancies(new HashSet<>(employer.getAllVacancies()));

        allUsers.replace(employer.getLogin(), newEmployer);
        activeUsers.replace(token, newEmployer);
    }
/*
    public void updateVacancy(String nameVacancy, String tokenEmployer, Vacancy newVacancy) throws ServerException {
        Vacancy vacancy = getVacancyByTokenAndName(tokenEmployer, nameVacancy);
        if (vacancy == null) {
            throw new ServerException(ErrorCode.VACANCY_EXCEPTION);
        }
        vacanciesList.set(vacanciesList.indexOf(vacancy), newVacancy);
    }*/

    public void updateDemandSkill(String oldName, String newName) {
        demandSkills.remove(oldName);
        demandSkills.add(newName);
    }
/*
    public void updateDemandInVacancy(Requirement demand, String token, String nameVacancy, String oldNameDemand) throws ServerException {
        Vacancy vacancy = this.getVacancyByTokenAndName(token, nameVacancy);
        if (vacancy == null) {
            throw new ServerException(ErrorCode.VACANCY_EXCEPTION);
        }
        vacancy.updateDemand(oldNameDemand, demand);
    }

  */

    public void deleteDemandSkill(String name) {
        demandSkills.remove(name);
    }

    public void cleanDataBase() {
        allUsers.clear();
        activeUsers.clear();
        demandSkills.clear();
        userSkills.clear();
        requirementsVacancies.clear();
    }
}

