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
/*            int i = o1.getName().compareTo(o2.getName());
            if (i != 0) {
                return i;
            } else if (o1.getLevel() - o2.getLevel() != 0) {
                return o1.getLevel() - o2.getLevel();
            }
            return o1.getNecessary().compareTo(o2.getNecessary());*/
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
        String token = UUID.randomUUID().toString();
        activeUsers.put(token, user);
        return token;
    }

    public void removeAccount(String token) throws ServerException {
        User user = activeUsers.get(token);
        if (user == null) {
            throw new ServerException(ErrorCode.NOT_FOUND_USER);
        }
        allUsers.remove(user.getLogin());
        activeUsers.remove(token, user);
    }

    public void logoutUser(String token) {
        activeUsers.remove(token);
    }

    public User getUserById(String id) {
        return activeUsers.get(id);
    }

    public void saveUser(User user) {
        allUsers.put(user.getLogin(), user);
    }

    public void addVacancy(Vacancy vacancy, String token) {
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
        employee.addAttainments(attainment);
        saveUser(employee);
        activeUsers.put(token, employee);
    }

    public void updateEmployeeSkill(String token, String oldNameSkill, Skill newSkill) {
        Employee employee = (Employee) getUserById(token);
        employee.updateAttainments(oldNameSkill, newSkill);
        saveUser(employee);
        activeUsers.put(token, employee);
    }

    public void removeEmployeeSkill(String id, String name) throws ServerException {
        Employee employee = (Employee) activeUsers.get(id);
        employee.removeAttainments(employee.getSkillByName(name));
        saveUser(employee);
        activeUsers.put(id, employee);
    }

    public void addRequirementInVacancy(Requirement requirement, String token, String nameVacancy) throws ServerException {
        Employer employer = (Employer) getUserById(token);
        employer.getVacancyByName(nameVacancy).addRequirement(requirement);
        saveUser(employer);
        activeUsers.put(token, employer);
    }

    public void removeRequirementOfVacancy(String token, String nameVacancy, String nameRequirement) throws ServerException {
        Employer employer = (Employer) getUserById(token);
        employer.getVacancyByName(nameVacancy).removeDemand(nameRequirement);
        saveUser(employer);
        activeUsers.put(token, employer);
    }
   /* public List<Employee> getEmployeeList() {
        return new ArrayList<>(employeeList.values());
    }

    public List<Employer> getEmployerList() {
        return new ArrayList<>(employerList.values());
    }*/

   /* public List<Vacancy> getVacanciesList() {
        return new ArrayList<>(vacanciesList);
    }*/

    /*Collection<Employee> employees = userSkills.get(skills.get(0).getName());
        for (Skill skill : skills) {
            employees.retainAll(userSkills.get(skill.getName()));
        } для получения работников
     */
    public List<Vacancy> getVacanciesListNotLess(List<Skill> skills) {
        NavigableMap<Requirement, Collection<Vacancy>> navigableMap = ((TreeMultimap<Requirement, Vacancy>) requirementsVacancies).asMap();
        Collection<Vacancy> vacancies = new HashSet<>(requirementsVacancies.values());
        Collection<Collection<Vacancy>> temporalVacanciesCollection;
        boolean chek;
        List<Vacancy> vacancies2 = new ArrayList<>();
        Set<Vacancy> vacanciesWithOneRequirement = new HashSet<>();
        for (Skill skill : skills) {
            chek = false;
            temporalVacanciesCollection = navigableMap.subMap(new Requirement(skill.getName(), 0, ConditionsRequirements.NECESSARY), new Requirement(skill.getName(), skill.getLevel() + 1, ConditionsRequirements.NECESSARY)).values();
            for (Collection<Vacancy> vacancies1 : temporalVacanciesCollection) {
                vacancies2.addAll(vacancies1);
            }
            temporalVacanciesCollection = navigableMap.subMap(new Requirement(skill.getName(), 0, ConditionsRequirements.NOT_NECESSARY), new Requirement(skill.getName(), skill.getLevel() + 1, ConditionsRequirements.NOT_NECESSARY)).values();
            for (Collection<Vacancy> vacancies1 : temporalVacanciesCollection) {
                vacancies2.addAll(vacancies1);
            }
            if (vacancies2.size() != 0) {
                for (Vacancy vacancy : vacancies2) {
                    if (vacancy.getRequirements().size() == 1) {
                        vacanciesWithOneRequirement.add(vacancy);//чтобы элементы с одним требованием не потерялись при пересечении(т.к они в одном листе)
                        chek = true;
                    }
                }
                if (!chek) {
                    vacancies.retainAll(vacancies2);
                }
                vacancies2.clear();
            }
        }
        vacancies.addAll(vacanciesWithOneRequirement);
        return new ArrayList<>(vacancies);
    }

    public List<Vacancy> getVacanciesListObligatoryDemand(List<Skill> skills) {
        NavigableMap<Requirement, Collection<Vacancy>> navigableMap = ((TreeMultimap<Requirement, Vacancy>) requirementsVacancies).asMap();
        Collection<Vacancy> vacancies = new HashSet<>(requirementsVacancies.values());
        Collection<Collection<Vacancy>> temporalVacanciesCollection;
        boolean chek;
        List<Vacancy> vacancies2 = new ArrayList<>();
        Set<Vacancy> vacanciesWithOneRequirement = new HashSet<>();
        for (Skill skill : skills) {
            chek = false;
            temporalVacanciesCollection = navigableMap.subMap(new Requirement(skill.getName(), 0, ConditionsRequirements.NECESSARY), new Requirement(skill.getName(), skill.getLevel() + 1, ConditionsRequirements.NECESSARY)).values();
            for (Collection<Vacancy> vacancies1 : temporalVacanciesCollection) {
                vacancies2.addAll(vacancies1);
            }
            if (vacancies2.size() != 0) {
                for (Vacancy vacancy : vacancies2) {
                    if (vacancy.getRequirements().size() == 1) {
                        vacanciesWithOneRequirement.add(vacancy);//чтобы элементы с одним требованием не потерялись при пересечении(т.к они в одном листе)
                        chek = true;
                    }
                }
                if (!chek) {
                    vacancies.retainAll(vacancies2);
                }
                vacancies2.clear();
            }
        }
        vacancies.addAll(vacanciesWithOneRequirement);
        return new ArrayList<>(vacancies);
    }

    public List<Vacancy> getVacanciesListOnlyName(List<Skill> skills) {
        NavigableMap<Requirement, Collection<Vacancy>> navigableMap = ((TreeMultimap<Requirement, Vacancy>) requirementsVacancies).asMap();
        Collection<Vacancy> vacancies = new HashSet<>(requirementsVacancies.values());
        Collection<Collection<Vacancy>> temporalVacanciesCollection;
        Set<String> skillsNames = new HashSet<>();
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
        for (Skill skill : skills) {
            skillsNames.add(skill.getName());
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

    public Set<String> getDemandSkillsSet() {
        return Collections.unmodifiableSet(demandSkills);
    }

    public void addSubSet(Set<String> subSet) {
        demandSkills.addAll(subSet);
    }

    public void addDemandSkill(String nameDemandSkill) {
        demandSkills.add(nameDemandSkill);
    }
/*

    /*public Vacancy getVacancyByTokenAndName(String token, String namePost) {
        for (Vacancy vacancy : vacanciesList) {
            if (token.equals(vacancy.getToken()) && namePost.equals(vacancy.getName())) {
                return vacancy;
            }
        }
        return null;
    }

    public List<Vacancy> getVacanciesListByToken(String token) {
        List<Vacancy> outVacanciesList = new ArrayList<>();
        for (Vacancy vacancy : vacanciesList) {
            if (vacancy.getToken().equals(token)) {
                outVacanciesList.add(vacancy);
            }
        }
        return outVacanciesList;
    }
*/
  /*  public List<Vacancy> getActivityVacanciesListByToken(String token) {
        List<Vacancy> vacancies = new ArrayList<>();
        for (Vacancy vacancy : vacanciesList) {
            if (vacancy.isStatus() && vacancy.getToken().equals(token)) {
                vacancies.add(vacancy);
            }
        }
        return vacancies;
    }

    public List<Vacancy> getNotActivityVacanciesListByToken(String token) {
        List<Vacancy> vacancies = new ArrayList<>();
        for (Vacancy vacancy : vacanciesList) {
            if (!vacancy.isStatus() && vacancy.getToken().equals(token)) {
                vacancies.add(vacancy);
            }
        }
        return vacancies;
    }*/

  /*  public Employee getEmployeeById(String id) {
        Collection<Employee> employees = employeeList.get(id);
        if (employees.isEmpty()) {
            return null;
        }
        return employees.iterator().next();
    }

    public Employee getEmployeeByLoginAndPassword(String login, String password) {
        for (Employee employee : employeeList.values()) {
            if (login.equals(employee.getLogin()) && password.equals(employee.getPassword())) {
                return employee;
            }
        }
        return null;
    }

    public List<Employee> getEmployeeListNotLessByDemands(List<Requirement> demands) {
        int i = 0;
        List<Employee> outList = new ArrayList<>();
        for (Employee employee : employeeList.values()) {
            for (Skill attainments : employee.getAttainmentsList()) {
                for (Requirement demand : demands) {
                    if (demand.getNameDemand().equals(attainments.getName()) && demand.getSkill() <= attainments.getLevel() && demand.isNecessary()) {
                        i++;
                    }
                }
            }
            if (i == demands.size()) {
                outList.add(employee);
            }
        }
        return outList;
    }
*//*
    public List<Employee> getEmployeeListObligatoryDemandByDemands(List<Requirement> demands) {
        int i = 0, count = 0;
        List<Employee> outList = new ArrayList<>();
        for (Employee employee : employeeList.values()) {
            for (Skill attainments : employee.getAttainmentsList()) {
                for (Requirement demand : demands) {
                    if (demand.getNameDemand().equals(attainments.getName()) && demand.getSkill() <= attainments.getLevel()) {
                        i++;
                    }
                    if (demand.isNecessary()) {
                        count++;
                    }
                }
            }
            if (i == count) {
                outList.add(employee);
            }
        }
        return outList;
    }

    public List<Employee> getEmployeeListByDemands(List<Requirement> demands) {
        int i = 0;
        List<Employee> outList = new ArrayList<>();
        for (Employee employee : employeeList.values()) {
            for (Skill attainments : employee.getAttainmentsList()) {
                for (Requirement demand : demands) {
                    if (demand.getNameDemand().equals(attainments.getName())) {
                        i++;
                    }
                }
            }
            if (i == demands.size()) {
                outList.add(employee);
            }
        }
        return outList;
    }

    public List<Employee> getEmployeeListWithOneDemandByDemands(List<Requirement> demands) {
        int i = 0;
        List<Employee> outList = new ArrayList<>();
        for (Employee employee : employeeList.values()) {
            for (Skill attainments : employee.getAttainmentsList()) {
                for (Requirement demand : demands) {
                    if (demand.getNameDemand().equals(attainments.getName()) && demand.getSkill() <= attainments.getLevel() && demand.isNecessary()) {
                        i++;
                    }
                }
            }
            if (i != 0) {
                outList.add(employee);
            }
        }
        return outList;
    }*/

   /* public Employer getEmployerById(String id) {
        Collection<Employer> employers = employerList.get(id);
        if (employers.isEmpty()) {
            return null;
        }
        return employers.iterator().next();
    }

    public Employer getEmployerByLoginAndPassword(String login, String password) throws ServerException {
        for (Employer employer : employerList.values()) {
            if (login.equals(employer.getLogin()) && password.equals(employer.getPassword())) {
                return employer;
            }
        }
        throw new ServerException(ErrorCode.GET_USER_EXCEPTION);
    }

    public void updateEmployee(String id, Employee newEmployee) throws ServerException {
        List<Employee> value = new ArrayList<>();
        Employee employee = this.getEmployeeById(id);
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
        value.add(newEmployee);
        employeeList.replaceValues(id, value);
    }*/
/*
    public void updateEmployer(String id, Employer newEmployer) throws ServerException {
        List<Employer> value = new ArrayList<>();
        Employer employer = this.getEmployerById(id);
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
        value.add(newEmployer);
        employerList.replaceValues(id, value);
    }

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

    public void deleteEmployee(String id) {
        employeeList.removeAll(id);
    }

    public void deleteEmployer(String id) {
        employerList.removeAll(id);
    }

    public void removeAccountEmployer(String token) {
        this.removeAllVacanciesByToken(token);
        //REVU: а как сохраняются данные (прогресс) после удаления, если идет удаление из списка?
        this.deleteEmployer(token);
    }

    public void deleteVacancy(String token, String namePost) throws ServerException {
        Vacancy vacancy = getVacancyByTokenAndName(token, namePost);
        if (vacancy == null) {
            throw new ServerException(ErrorCode.VACANCY_EXCEPTION);
        }
        vacanciesList.remove(vacancy);
    }

    public void removeAllVacanciesByToken(String token) {
        List<Vacancy> vacancies = this.getVacanciesList();
        for (Vacancy vacancy : vacancies) {
            if (vacancy.getToken().equals(token)) {
                try {
                    this.deleteVacancy(vacancy.getToken(), vacancy.getName());
                } catch (ServerException e) {
                    //REVU: не нужно отлавливать тут исключение
                }
            }
        }
    }*/

    public void deleteDemandSkill(String name) {
        demandSkills.remove(name);
    }

    /*
        public void setAccountEmployeeStatus(String token, boolean status) throws ServerException {
            Employee employee = getEmployeeById(token);
            if (employee == null) {
                throw new ServerException(ErrorCode.EMPLOYEE_EXCEPTION);
            }
            employee.setActivity(status);
            this.updateEmployee(employee.getId(), employee);
        }

        public void setAccountEmployerStatus(String token, boolean status) throws ServerException {
            Employer employer = this.getEmployerById(token);
            if (employer == null) {
                throw new ServerException(ErrorCode.EMPLOYER_EXCEPTION);
            }
            employer.setActivity(status);
            updateEmployer(employer.getId(), employer);
        }

        public Vacancy setVacancyStatus(String token, String namePost, boolean status) throws ServerException {
            Vacancy vacancy = this.getVacancyByTokenAndName(token, namePost);
            if (vacancy == null) {
                throw new ServerException(ErrorCode.VACANCY_EXCEPTION);
            }
            vacancy.setStatus(status);
            updateVacancy(vacancy.getName(), vacancy.getToken(), vacancy);
            return vacancy;
        }

        public void setEmployeeStatus(String token, boolean status) throws ServerException {
            Employee employee = getEmployeeById(token);
            if (employee == null) {
                throw new ServerException(ErrorCode.EMPLOYEE_EXCEPTION);
            }
            employee.setStatus(status);
            updateEmployee(employee.getId(), employee);
        }
    */
    public void cleanDataBase() {
        allUsers.clear();
        activeUsers.clear();
        demandSkills.clear();
        userSkills.clear();
        requirementsVacancies.clear();
    }
}

