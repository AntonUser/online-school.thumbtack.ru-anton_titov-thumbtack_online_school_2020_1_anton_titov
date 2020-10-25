package net.thumbtack.school.hiring.dto.response;

import net.thumbtack.school.hiring.model.Employee;

import java.util.List;

public class DtoEmployeesResponse {
    private List<Employee> employeeList;

    public DtoEmployeesResponse(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
