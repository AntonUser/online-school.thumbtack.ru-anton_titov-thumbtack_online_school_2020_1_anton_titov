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

    public Employer getEmployerById(String id) {
        for (Employer employer : employerList) {
            if (id.equals(employer.getId())) {
                return employer;
            }
        }
        return null;
    }

    public Employer getEmployerByLoginAndPassword(String login, String password) {
        for (Employer employer : employerList) {
            if (login.equals(employer.getLogin()) && password.equals(employer.getPassword())) {
                return employer;
            }
        }
        return null;
    }

    public void updateEmployee(Employee oldEmployee, Employee newEmployee) {
        employeeList.set(employeeList.indexOf(oldEmployee), newEmployee);
    }

    public void updateEmployer(Employer oldEmployer, Employer newEmployer) {
        employerList.set(employerList.indexOf(oldEmployer), newEmployer);
    }

    public void updateVacancy(Vacancy oldVacancy, Vacancy newVacancy) {
        vacanciesList.set(vacanciesList.indexOf(oldVacancy), newVacancy);
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

    public void deleteDemandSkill(String name) {
        demandSkills.remove(name);
    }
}

