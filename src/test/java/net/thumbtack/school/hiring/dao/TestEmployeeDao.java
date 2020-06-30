package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.daoimpl.EmployeeDao;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Attainments;
import net.thumbtack.school.hiring.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestEmployeeDao {
    @Test
    public void testGetAll() throws ServerException {
        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.save(new Employee(UUID.randomUUID().toString(), "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru", true, new ArrayList<>(), true));
        employeeDao.save(new Employee(UUID.randomUUID().toString(), "Ivan", "Petrovich", "Petrovsky", "petr87", "wh45dssdc9", "petr@mail.ru", true, new ArrayList<>(), true));
        assertEquals(2, employeeDao.getAll().size());
    }

    @Test
    public void testGet() throws ServerException {
        EmployeeDao employeeDao = new EmployeeDao();
        Employee employee1 = new Employee(UUID.randomUUID().toString(), "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru", true, new ArrayList<>(), true);
        Employee employee2 = new Employee(UUID.randomUUID().toString(), "Ivan", "Petrovich", "Petrovsky", "petr87", "wh45dssdc9", "petr@mail.ru", true, new ArrayList<>(), true);
        employeeDao.save(employee1);
        employeeDao.save(employee2);
        assertEquals(employee1, employeeDao.getById(employee1.getId()));
        employeeDao.delete(employee1.getId());
        employeeDao.delete(employee2.getId());
    }

    @Test
    public void testSave() throws ServerException {
        EmployeeDao employeeDao = new EmployeeDao();
        String id = UUID.randomUUID().toString();
        Employee employee = new Employee(id, "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru", true, new ArrayList<>(), true);
        employeeDao.save(employee);
        try {
            employeeDao.save(employee);
        } catch (ServerException se) {
            assertEquals(ErrorCode.REPEATING_EMPLOYEE, se.getErrorCode());
        } finally {
            employeeDao.delete(employee.getId());
        }
    }

    @Test
    public void testUpdate1() throws ServerException {
        EmployeeDao employeeDao = new EmployeeDao();
        String id = UUID.randomUUID().toString();
        List<Attainments> attainments = new ArrayList<>();
        attainments.add(new Attainments("ss", 4));
        Employee employee = new Employee(id, "Egor", "Ivanovich", "Ivanov", "Egorka", "eeeeee", "egorkinmail@mail.ru", true, attainments, true);
        Employee newEmployee = new Employee(id, "Egor", "Nicolaevich", "Ivanov", "Egorka", "ee444", "egorkinmail@mail.ru", true, attainments, true);
        employeeDao.save(employee);
        employeeDao.update(employee.getId(), newEmployee);
        assertEquals(newEmployee, employeeDao.getById(id));
        employeeDao.delete(newEmployee.getId());
    }
}
