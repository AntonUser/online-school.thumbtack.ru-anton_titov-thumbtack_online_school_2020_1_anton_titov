package net.thumbtack.school.hiring.database;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Employer;


import java.util.ArrayList;
import java.util.List;

public final class DataBase {
    private static DataBase instance = null;
    private static List<Employer> employerList;
    private static List<Employee> employeeList;

    private DataBase() {
        employeeList = new ArrayList<>();
        employerList = new ArrayList<>();
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

    public void addEmployee(Employee employee) throws ServerException {
        if (getEmployeeById(employee.getId()) != null) {
            throw new ServerException(ErrorCode.REPEATING_EMPLOYEE);
        }
        employeeList.add(employee);
    }

    public void addEmployer(Employer employer) throws ServerException {
        if (getEmployerById(employer.getId()) != null) {
            throw new ServerException(ErrorCode.REPEATING_EMPLOYER);
        }
        employerList.add(employer);
    }

    public Employee getEmployeeById(String id) {
        for (Employee employee : employeeList) {
            if (id.equals(employee.getId())) {
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

    public void updateEmployee(Employee oldEmployee, Employee newEmployee) {
        employeeList.set(employeeList.indexOf(oldEmployee), newEmployee);
    }

    public void updateEmployer(Employer oldEmployer, Employer newEmployer) {
        employerList.set(employerList.indexOf(oldEmployer), newEmployer);
    }

    public void deleteEmployee(Employee employee) {
        employeeList.remove(employee);
    }

    public void deleteEmployer(Employer employer) {
        employerList.remove(employer);
    }
}

