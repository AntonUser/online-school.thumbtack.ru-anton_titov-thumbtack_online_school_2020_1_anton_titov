package net.thumbtack.school.hiring.database;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class DataBase {
    private static DataBase instance = null;
    private static List<Employer> employerList;
    private static List<Employee> employeeList;
    private static List<Vacancy> vacanciesList;
    private static Set<String> demandSkills;

    private DataBase() {
        employeeList = new ArrayList<>();
        employerList = new ArrayList<>();
        vacanciesList = new ArrayList<>();
        demandSkills = new HashSet<>();
    }

    public static synchronized DataBase getInstance() {
        if (instance == null) {
            return instance = new DataBase();
        }
        return instance;
    }

    public static synchronized void setInstance(DataBase newInstance) {
        instance = newInstance;
    }

    public List<Employee> getEmployeeList() {
        return new ArrayList<>(employeeList);
    }

    public List<Employer> getEmployerList() {
        return new ArrayList<>(employerList);
    }

    public List<Vacancy> getVacanciesList() {
        return new ArrayList<>(vacanciesList);
    }

    //REVU: сейчас поиск нужных вакансий идет по всему списку, то есть полным перебором
    // обычно в базе создается конструкция, позволяющая быстро получать нужные вакансии
    // посмотри в сторону доп коллекции
    // https://guava.dev/releases/19.0/api/docs/com/google/common/collect/TreeMultimap.html
    // Сортированный Map с Comparator по ключам
    // подумай, как организовать хранение, чтобы не делать полный перебор каждый раз
    public List<Vacancy> getVacanciesListNotLess(List<Demand> skills) {
        int i = 0;
        List<Vacancy> outVacancies = new ArrayList<>();
        for (Vacancy vacancy : vacanciesList) {//сортирую в новый список по совпадениям полей
            for (Demand demand : vacancy.getDemands()) {
                for (Demand demandSkill : skills) {
                    if (demandSkill.getNameDemand().equals(demand.getNameDemand()) && demandSkill.getSkill() >= demand.getSkill()) {
                        i++;
                    }
                }
            }
            if (i == vacancy.getDemands().size()) {
                outVacancies.add(vacancy);
            }
        }
        return outVacancies;
    }

    public List<Vacancy> getVacanciesListObligatoryDemand(List<Demand> skills) {
        int i = 0, count;
        List<Vacancy> outVacancies = new ArrayList<>();
        for (Vacancy vacancy : vacanciesList) {//сортирую в новый список по совпадениям полей
            count = 0;
            for (Demand demand : vacancy.getDemands()) {
                for (Demand demandSkill : skills) {
                    if (demandSkill.getNameDemand().equals(demand.getNameDemand()) &&
                            demandSkill.getSkill() >= demand.getSkill() &&
                            demandSkill.isNecessary()) {
                        i++;
                    }
                    if (demandSkill.isNecessary()) {
                        count++;
                    }
                }
            }
            if (i == count) {
                outVacancies.add(vacancy);
            }
        }
        return outVacancies;
    }

    public List<Vacancy> getVacanciesListOnlyName(List<Demand> skills) {
        int i = 0;
        List<Vacancy> outVacancies = new ArrayList<>();
        for (Vacancy vacancy : vacanciesList) {//сортирую в новый список по совпадениям полей
            for (Demand demand : vacancy.getDemands()) {
                for (Demand demandSkill : skills) {
                    if (demandSkill.getNameDemand().equals(demand.getNameDemand())) {
                        i++;
                    }
                }
            }
            if (i == vacancy.getDemands().size()) {
                outVacancies.add(vacancy);
            }
        }
        return outVacancies;
    }

    public List<Vacancy> getVacanciesListWithOneDemand(List<Demand> skills) {
        int i = 0;
        List<Vacancy> outVacancies = new ArrayList<>();
        for (Vacancy vacancy : vacanciesList) {//сортирую в новый список по совпадениям полей
            for (Demand demand : vacancy.getDemands()) {
                for (Demand demandSkill : skills) {
                    if (demandSkill.getNameDemand().equals(demand.getNameDemand()) && demandSkill.getSkill() >= demand.getSkill()) {
                        i++;
                    }
                }
            }
            if (i != 0) {
                outVacancies.add(vacancy);
            }
        }
        return outVacancies;
    }

    public Set<String> getDemandSkillsSet() {
        return new HashSet<>(demandSkills);
    }

    public void addSubSet(Set<String> subSet) {
        demandSkills.addAll(subSet);
    }

    public void addEmployee(Employee employee) throws ServerException {
        if (getEmployeeByLoginAndPassword(employee.getLogin(), employee.getPassword()) != null) {
            throw new ServerException(ErrorCode.REPEATING_EMPLOYEE);
        }
        if (!employee.getAttainmentsList().isEmpty()) {
            //если умения уже есть, то добавим их в общий список
            this.addSubSet(employee.getNamesAttainments());
        }
        employeeList.add(employee);
    }

    public void addDemandSkill(String nameDemandSkill) {
        demandSkills.add(nameDemandSkill);
    }

    public void addSkillForEmployee(Attainments attainments, String token) {
        this.addDemandSkill(attainments.getNameSkill());//добавляю всегда но так как умения/требования хранятся в Set повторяющиеся будут удаляться
        Employee employee = getEmployeeById(token);
        employee.addAttainments(attainments);
        updateEmployee(employee.getId(), employee);
    }

    public void addEmployer(Employer employer) throws ServerException {
        if (getEmployeeByLoginAndPassword(employer.getLogin(), employer.getPassword()) != null) {
            throw new ServerException(ErrorCode.REPEATING_EMPLOYER);
        }
        employerList.add(employer);
    }

    public void addVacancy(Vacancy vacancy) {
        vacanciesList.add(vacancy);
        this.addSubSet(vacancy.getNamesDemands());
    }

    public Vacancy getVacancyByTokenAndName(String token, String namePost) {
        for (Vacancy vacancy : vacanciesList) {
            if (token.equals(vacancy.getToken()) && namePost.equals(vacancy.getNamePost())) {
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

    public List<Vacancy> getActivityVacanciesListByToken(String token) {
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
    }

    public String loginEmployee(String login, String password) throws ServerException {
        Employee employee = this.getEmployeeByLoginAndPassword(login, password);
        if (employee == null) {
            throw new ServerException(ErrorCode.AUTHORIZATION_EXCEPTION);
        }
        setAccountEmployeeStatus(employee.getId(), true);
        return employee.getId();
    }

    public boolean isActivityEmployee(String token) {
        Employee employee = this.getEmployeeById(token);
        if (employee != null) {
            return employee.isActivity();
        }
        return false;
    }

    //REVU: то же самое
    // удобно сделать конструкцию для хранения token-employee, чтоб не делать полный перебор
    public Employee getEmployeeById(String id) {
        for (Employee employee : employeeList) {
            if (id.equals(employee.getId())) {
                return employee;
            }
        }
        return null;
    }

    public Employee getEmployeeByLoginAndPassword(String login, String password) {
        for (Employee employee : employeeList) {
            if (login.equals(employee.getLogin()) && password.equals(employee.getPassword())) {
                return employee;
            }
        }
        return null;
    }

    public List<Employee> getEmployeeListNotLessByDemands(List<Demand> demands) {
        int i = 0;
        List<Employee> outList = new ArrayList<>();
        for (Employee employee : employeeList) {
            for (Attainments attainments : employee.getAttainmentsList()) {
                for (Demand demand : demands) {
                    if (demand.getNameDemand().equals(attainments.getNameSkill()) && demand.getSkill() <= attainments.getSkill() && demand.isNecessary()) {
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

    public List<Employee> getEmployeeListObligatoryDemandByDemands(List<Demand> demands) {
        int i = 0, count = 0;
        List<Employee> outList = new ArrayList<>();
        for (Employee employee : employeeList) {
            for (Attainments attainments : employee.getAttainmentsList()) {
                for (Demand demand : demands) {
                    if (demand.getNameDemand().equals(attainments.getNameSkill()) && demand.getSkill() <= attainments.getSkill()) {
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

    public List<Employee> getEmployeeListByDemands(List<Demand> demands) {
        int i = 0;
        List<Employee> outList = new ArrayList<>();
        for (Employee employee : employeeList) {
            for (Attainments attainments : employee.getAttainmentsList()) {
                for (Demand demand : demands) {
                    if (demand.getNameDemand().equals(attainments.getNameSkill())) {
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

    public List<Employee> getEmployeeListWithOneDemandByDemands(List<Demand> demands) {
        int i = 0;
        List<Employee> outList = new ArrayList<>();
        for (Employee employee : employeeList) {
            for (Attainments attainments : employee.getAttainmentsList()) {
                for (Demand demand : demands) {
                    if (demand.getNameDemand().equals(attainments.getNameSkill()) && demand.getSkill() <= attainments.getSkill() && demand.isNecessary()) {
                        i++;
                    }
                }
            }
            if (i != 0) {
                outList.add(employee);
            }
        }
        return outList;
    }

    public String loginEmployer(String login, String password) throws ServerException {
        Employer employer = this.getEmployerByLoginAndPassword(login, password);
        //REVU: null уже быть не может
        if (employer == null) {
            throw new ServerException(ErrorCode.EMPLOYER_EXCEPTION);
        }
        setAccountEmployerStatus(employer.getId(), true);
        return employer.getId();
    }

    public boolean isActivityEmployer(String token) {
        Employer employer = this.getEmployerById(token);
        if (employer != null) {
            return employer.isActivity();
        }
        return false;
    }

    public Employer getEmployerById(String id) {
        for (Employer employer : employerList) {
            if (id.equals(employer.getId())) {
                return employer;
            }
        }
        return null;
    }

    public Employer getEmployerByLoginAndPassword(String login, String password) throws ServerException {
        for (Employer employer : employerList) {
            if (login.equals(employer.getLogin()) && password.equals(employer.getPassword())) {
                return employer;
            }
        }
        throw new ServerException(ErrorCode.GET_USER_EXCEPTION);
    }

    public void updateEmployee(String id, Employee newEmployee) {
        employeeList.set(employeeList.indexOf(getEmployeeById(id)), newEmployee);
    }

    public void updateEmployer(String id, Employer newEmployer) {
        employerList.set(employerList.indexOf(this.getEmployerById(id)), newEmployer);
    }

    public void updateVacancy(String nameVacancy, String tokenEmployer, Vacancy newVacancy) throws ServerException {
        Vacancy vacancy = getVacancyByTokenAndName(tokenEmployer, nameVacancy);
        if (vacancy == null) {
            throw new ServerException(ErrorCode.VACANCY_EXCEPTION);
        }
        vacanciesList.set(vacanciesList.indexOf(vacancy), newVacancy);
    }

    public void updateDemandSkill(String oldName, String newName) {
        demandSkills.remove(oldName);
        demandSkills.add(newName);
    }

    public void updateDemandInVacancy(Demand demand, String token, String nameVacancy, String oldNameDemand) throws ServerException {
        Vacancy vacancy = this.getVacancyByTokenAndName(token, nameVacancy);
        if (vacancy == null) {
            throw new ServerException(ErrorCode.VACANCY_EXCEPTION);
        }
        vacancy.updateDemand(oldNameDemand, demand);
    }

    public void deleteEmployee(String id) {
        employeeList.remove(this.getEmployeeById(id));
    }

    public void deleteEmployer(String id) {
        employerList.remove(this.getEmployerById(id));
    }

    public void removeAccountEmployer(String token) {
        this.removeAllVacanciesByToken(token);
        //REVU: а как сохраняются данные (прогресс) после удаления, если идет удаление из списка?
        this.deleteEmployer(token);//пока что так
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
                    this.deleteVacancy(vacancy.getToken(), vacancy.getNamePost());
                } catch (ServerException e) {
                    //REVU: не нужно отлавливать тут исключение
                }
            }
        }
    }

    public void deleteDemandSkill(String name) {
        demandSkills.remove(name);
    }

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
        updateVacancy(vacancy.getNamePost(), vacancy.getToken(), vacancy);
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

    public void updateEmployerFirstName(String token, String firstName) throws ServerException {
        Employer employer = this.getEmployerById(token);
        if (employer == null) {
            throw new ServerException(ErrorCode.EMPLOYER_EXCEPTION);
        }
        employer.setFirstName(firstName);
        updateEmployer(employer.getId(), employer);
    }

    public void updateEmployerPatronymic(String token, String patronymic) throws ServerException {
        Employer employer = this.getEmployerById(token);
        if (employer == null) {
            throw new ServerException(ErrorCode.EMPLOYER_EXCEPTION);
        }
        employer.setPatronymic(patronymic);
        updateEmployer(employer.getId(), employer);
    }

    public void updateEmployerLastName(String token, String lastName) throws ServerException {
        Employer employer = this.getEmployerById(token);
        if (employer == null) {
            throw new ServerException(ErrorCode.EMPLOYER_EXCEPTION);
        }
        employer.setLastName(lastName);
        updateEmployer(employer.getId(), employer);
    }

    public void updateEmployerEmail(String token, String email) throws ServerException {
        Employer employer = this.getEmployerById(token);
        if (employer == null) {
            throw new ServerException(ErrorCode.EMPLOYER_EXCEPTION);
        }
        employer.setEmail(email);
        updateEmployer(employer.getId(), employer);
    }

    public void updateEmployerPassword(String token, String password) throws ServerException {
        Employer employer = this.getEmployerById(token);
        if (employer == null) {
            throw new ServerException(ErrorCode.EMPLOYER_EXCEPTION);
        }
        employer.setPassword(password);
        updateEmployer(employer.getId(), employer);
    }

    public void updateEmployerNameCompany(String token, String nameCompany) throws ServerException {
        Employer employer = this.getEmployerById(token);
        if (employer == null) {
            throw new ServerException(ErrorCode.EMPLOYER_EXCEPTION);
        }
        employer.setName(nameCompany);
        updateEmployer(employer.getId(), employer);
    }

    public void updateEmployerAddress(String token, String address) throws ServerException {
        Employer employer = this.getEmployerById(token);
        if (employer == null) {
            throw new ServerException(ErrorCode.EMPLOYER_EXCEPTION);
        }
        employer.setAddress(address);
        updateEmployer(employer.getId(), employer);
    }

    public void updateEmployeeFirstName(String token, String firstName) throws ServerException {
        Employee employee = getEmployeeById(token);
        if (employee == null) {
            throw new ServerException(ErrorCode.EMPLOYEE_EXCEPTION);
        }
        employee.setFirstName(firstName);
        this.updateEmployee(token, employee);
    }

    public void updateEmployeePatronymic(String token, String patronymic) throws ServerException {
        Employee employee = getEmployeeById(token);
        if (employee == null) {
            throw new ServerException(ErrorCode.EMPLOYEE_EXCEPTION);
        }
        employee.setPatronymic(patronymic);
        this.updateEmployee(token, employee);
    }

    public void updateEmployeeLastName(String token, String lastName) throws ServerException {
        Employee employee = getEmployeeById(token);
        if (employee == null) {
            throw new ServerException(ErrorCode.EMPLOYEE_EXCEPTION);
        }
        employee.setLastName(lastName);
        this.updateEmployee(token, employee);
    }

    public void updateEmployeeEmail(String token, String email) throws ServerException {
        Employee employee = getEmployeeById(token);
        if (employee == null) {
            throw new ServerException(ErrorCode.EMPLOYEE_EXCEPTION);
        }
        employee.setEmail(email);
        this.updateEmployee(token, employee);
    }

    public void updateEmployeePassword(String token, String password) throws ServerException {
        Employee employee = getEmployeeById(token);
        if (employee == null) {
            throw new ServerException(ErrorCode.EMPLOYEE_EXCEPTION);
        }
        employee.setPassword(password);
        this.updateEmployee(token, employee);
    }

    public static void cleanDataBase() {
        vacanciesList.clear();
        employeeList.clear();
        employerList.clear();
        demandSkills.clear();
    }
}

