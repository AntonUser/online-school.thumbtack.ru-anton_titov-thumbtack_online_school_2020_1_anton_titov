package net.thumbtack.school.hiring.database;

import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Skill;
import net.thumbtack.school.hiring.model.User;
import net.thumbtack.school.hiring.model.Vacancy;

import java.io.Serializable;
import java.util.*;

public final class DataBase implements Serializable {
    private static DataBase instance = null;
    private Map<String, User> allUsers;//String - логин пользователя, User - пользователь
    private Map<String, User> activeUsers;//string - id объекта, User - пользователь
    private ArrayList<Vacancy> vacanciesList;
    private Set<String> demandSkills;

    private DataBase() {
        allUsers = new HashMap<>();
        activeUsers = new HashMap<>();
        vacanciesList = new ArrayList<>();
        demandSkills = new HashSet<>();
    }

    public static DataBase getInstance() {
        if (instance == null) {
            return instance = new DataBase();
        }
        return instance;
    }

    public static synchronized void setInstance(DataBase newInstance) {
        instance = newInstance;
    }

    public String registerUser(User user) {
        allUsers.put(user.getLogin(), user);
        activeUsers.put(user.getId(), user);
        return user.getId();
    }

    public String loginUser(String login, String password) {
        User user = allUsers.get(login);
        if (user.getPassword().equals(password)) {
            activeUsers.put(user.getId(), user);
            return user.getId();
        }
        return null;
    }

    public void removeAccount(String token) {
        User user = activeUsers.get(token);
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

    public void removeEmployeeSkill(String id, Skill skill) {
        Employee employee = (Employee) activeUsers.get(id);
        employee.removeAttainments(skill);
    }


   /* public List<Employee> getEmployeeList() {
        return new ArrayList<>(employeeList.values());
    }

    public List<Employer> getEmployerList() {
        return new ArrayList<>(employerList.values());
    }*/

    public List<Vacancy> getVacanciesList() {
        return new ArrayList<>(vacanciesList);
    }

    public List<Vacancy> getVacanciesListNotLess(Map<String, Integer> skills) {
        int i;
        List<Vacancy> outList = new ArrayList<>();
        Map<String, Integer> demands = new HashMap();
        List<Vacancy> vacancies = new ArrayList<>(vacanciesList);
        for (Vacancy vacancy : vacancies) {
            demands.putAll(vacancy.getNotObligatoryDemands());
            demands.putAll(vacancy.getObligatoryDemands());
            if (skills.keySet().containsAll(demands.keySet())) {
                Iterator<Map.Entry<String, Integer>> itr = demands.entrySet().iterator();
                i = 0;
                while (itr.hasNext()) {
                    Map.Entry<String, Integer> entry = itr.next();
                    if (skills.get(entry.getKey()) < entry.getValue()) {
                        break;
                    }
                    i++;
                }
                if (i == demands.size()) {
                    outList.add(vacancy);
                }
                demands.clear();
            }
        }
        return outList;
    }

    public List<Vacancy> getVacanciesListObligatoryDemand(Map<String, Integer> skills) {
        int i;
        List<Vacancy> outList = new ArrayList<>();
        List<Vacancy> vacancies = new ArrayList<>(vacanciesList);
        for (Vacancy vacancy : vacancies) {
            if (skills.keySet().containsAll(vacancy.getObligatoryDemands().keySet())) {
                Iterator<Map.Entry<String, Integer>> itr = vacancy.getObligatoryDemands().entrySet().iterator();
                i = 0;
                while (itr.hasNext()) {
                    Map.Entry<String, Integer> entry = itr.next();
                    if (skills.get(entry.getKey()) < entry.getValue()) {
                        break;
                    }
                    i++;
                }
                if (i == vacancy.getObligatoryDemands().size()) {
                    outList.add(vacancy);
                }
            }
        }
        return outList;
    }

    public List<Vacancy> getVacanciesListOnlyName(Map<String, Integer> skills) {
        List<Vacancy> outList = new ArrayList<>();
        Map<String, Integer> demands = new HashMap();
        List<Vacancy> vacancies = new ArrayList<>(vacanciesList);
        for (Vacancy vacancy : vacancies) {
            demands.putAll(vacancy.getNotObligatoryDemands());
            demands.putAll(vacancy.getObligatoryDemands());
            if (skills.keySet().containsAll(demands.keySet())) {
                Iterator<Map.Entry<String, Integer>> itr = demands.entrySet().iterator();
                outList.add(vacancy);
            }
            demands.clear();
        }
        return outList;
    }

    public List<Vacancy> getVacanciesListWithOneDemand(Map<String, Integer> skills) {//!!!!!!
        int i;
        List<Vacancy> outList = new ArrayList<>();
        Map<String, Integer> demands = new HashMap();
        List<Vacancy> vacancies = new ArrayList<>(vacanciesList);
        for (Vacancy vacancy : vacancies) {
            demands.putAll(vacancy.getNotObligatoryDemands());
            demands.putAll(vacancy.getObligatoryDemands());
            demands.keySet().retainAll(skills.keySet());
            if (demands.size() != 0) {
                Iterator<Map.Entry<String, Integer>> itr = demands.entrySet().iterator();
                i = 0;
                while (itr.hasNext()) {
                    Map.Entry<String, Integer> entry = itr.next();
                    if (skills.get(entry.getKey()) < entry.getValue()) {
                        break;
                    }
                    i++;
                }
                if (i != 0) {
                    outList.add(vacancy);
                }
                demands.clear();
            }
        }
        return outList;
    }

    public Set<String> getDemandSkillsSet() {
        return new HashSet<>(demandSkills);
    }

    public void addSubSet(Set<String> subSet) {
        demandSkills.addAll(subSet);
    }

    public void addDemandSkill(String nameDemandSkill) {
        demandSkills.add(nameDemandSkill);
    }
/*
    public void addSkillForEmployee(Skill attainments, String token) throws ServerException {
        this.addDemandSkill(attainments.getName());//добавляю всегда но так как умения/требования хранятся в Set повторяющиеся будут удаляться
        Employee employee = getEmployeeById(token);
        employee.addAttainments(attainments);
        updateEmployee(employee.getId(), employee);
    }

    /*public void addEmployer(Employer employer) throws ServerException {
        // REVU не надо проверять.  Просто попробуйте добавить с этим логином с помощью putIfAbsent и проверьте, что из этого вышло
        // а до пароля Вам тут и дела нет - все равно добавить нельзя с этим логином
        if (getEmployeeByLoginAndPassword(employer.getLogin(), employer.getPassword()) != null) {
            throw new ServerException(ErrorCode.REPEATING_EMPLOYER);
        }
        employerList.put(employer.getId(), employer);
    }*/

    public void addVacancy(Vacancy vacancy) {
        vacanciesList.add(vacancy);
        this.addSubSet(vacancy.getNamesDemands());
    }

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
        vacanciesList.clear();
        allUsers.clear();
        activeUsers.clear();
        demandSkills.clear();
    }
}

