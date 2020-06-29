package net.thumbtack.school.hiring.database;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;


import java.util.*;

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

    public List<Employee> getEmployeeList() {
        return new ArrayList<>(employeeList);
    }

    public List<Employer> getEmployerList() {
        return new ArrayList<>(employerList);
    }

    public List<Vacancy> getVacanciesList() {
        return new ArrayList<>(vacanciesList);
    }

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
        employeeList.add(employee);
    }

    public void addDemandSkill(String nameDemandSkill) {
        demandSkills.add(nameDemandSkill);
    }

    public void addEmployer(Employer employer) throws ServerException {
        if (getEmployeeByLoginAndPassword(employer.getLogin(), employer.getPassword()) != null) {
            throw new ServerException(ErrorCode.REPEATING_EMPLOYER);
        }
        employerList.add(employer);
    }

    public void addVacancy(Vacancy vacancy) {
        vacanciesList.add(vacancy);
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

    public void updateEmployee(Employee oldEmployee, Employee newEmployee) {
        employeeList.set(employeeList.indexOf(getEmployeeById(oldEmployee.getId())), newEmployee);
    }

    public void updateEmployer(Employer oldEmployer, Employer newEmployer) {
        employerList.set(employerList.indexOf(oldEmployer), newEmployer);
    }

    public void updateVacancy(Vacancy oldVacancy, Vacancy newVacancy) {
        vacanciesList.set(vacanciesList.indexOf(getVacancyByTokenAndName(oldVacancy.getToken(), oldVacancy.getNamePost())), newVacancy);
    }

    public void updateDemandSkill(String oldName, String newName) {
        demandSkills.remove(oldName);
        demandSkills.add(newName);
    }

    public void deleteEmployee(Employee employee) {
        employeeList.remove(employee);
    }

    public void deleteEmployer(Employer employer) {
        employerList.remove(employer);
    }

    public void deleteVacancy(Vacancy vacancy) {
        vacanciesList.remove(vacancy);
    }

    public void removeAllVacanciesByToken(String token) {
        List<Vacancy> vacancies = this.getVacanciesList();
        for (Vacancy vacancy : vacancies) {
            if (vacancy.getToken().equals(token)) {
                this.deleteVacancy(vacancy);
            }
        }
    }

    public void deleteDemandSkill(String name) {
        demandSkills.remove(name);
    }

    public static void cleanDataBase() {
        vacanciesList.clear();
        employeeList.clear();
        employerList.clear();
        demandSkills.clear();
    }
}

