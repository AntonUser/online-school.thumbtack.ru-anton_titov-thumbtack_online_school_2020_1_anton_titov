package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employee;

import java.util.List;

public class EmployeeDao implements Dao<Employee> {
    DataBase dataBase;

    public EmployeeDao(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Employee get(String id) {
        return dataBase.getEmployeeById(id);
    }

    @Override
    public List<Employee> getAll() {
        return dataBase.getEmployeeList();
    }

    @Override
    public void save(Employee employee) throws ServerException {
        dataBase.addEmployee(employee);
    }

    @Override
    public void update(Employee oldEmployee, Employee newEmployee) {
        dataBase.updateEmployee(oldEmployee, newEmployee);
    }

    @Override
    public void delete(Employee employee) {
        dataBase.deleteEmployee(employee);
    }
}
