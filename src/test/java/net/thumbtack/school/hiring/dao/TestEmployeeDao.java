package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.daoimpl.EmployeeDao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employee;
import org.junit.jupiter.api.Test;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestEmployeeDao {
    @Test
    public void testGetAll() throws ServerException {
        DataBase dataBase = DataBase.getInstance();
        EmployeeDao employeeDao = new EmployeeDao(dataBase);
        employeeDao.save(new Employee(UUID.randomUUID().toString(), "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru"));
        employeeDao.save(new Employee(UUID.randomUUID().toString(), "Ivan", "Petrovich", "Petrovsky", "petr87", "wh45dssdc9", "petr@mail.ru"));
        assertEquals(2, employeeDao.getAll().size());
    }

    @Test
    public void testGet() throws ServerException {
        DataBase dataBase = DataBase.getInstance();
        EmployeeDao employeeDao = new EmployeeDao(dataBase);
        Employee employee1 = new Employee(UUID.randomUUID().toString(), "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru");
        Employee employee2 = new Employee(UUID.randomUUID().toString(), "Ivan", "Petrovich", "Petrovsky", "petr87", "wh45dssdc9", "petr@mail.ru");
        employeeDao.save(employee1);
        employeeDao.save(employee2);
        assertEquals(employee1, employeeDao.get(employee1.getId()));
        employeeDao.delete(employee1);
        employeeDao.delete(employee2);
    }

    @Test
    public void testSave() throws ServerException {
        DataBase dataBase = DataBase.getInstance();
        EmployeeDao employeeDao = new EmployeeDao(dataBase);
        String id = UUID.randomUUID().toString();
        Employee employee = new Employee(id, "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru");
        employeeDao.save(employee);
        try {
            employeeDao.save(employee);
        } catch (ServerException se) {
            assertEquals(ErrorCode.REPEATING_EMPLOYEE, se.getErrorCode());
        } finally {
            employeeDao.delete(employee);
        }
    }

    @Test
    public void testUpdate() throws ServerException {
        DataBase dataBase = DataBase.getInstance();
        EmployeeDao employeeDao = new EmployeeDao(dataBase);
        String id = "25s5wef55wef85wef8wde5wef";
        Employee employee = new Employee(id, "Ivan", "Petrovich", "Petrovsky", "petr87", "wh45dssdc9", "petr@mail.ru");
        Employee newEmployee = new Employee(id, "Genadiy", "Petrovich", "Petrovsky", "petr87", "wh45dssdc9", "petr@mail.ru");
        employeeDao.save(employee);
        employeeDao.update(employee, newEmployee);
        assertEquals(newEmployee, employeeDao.get(id));
        employeeDao.delete(newEmployee);
    }
}
