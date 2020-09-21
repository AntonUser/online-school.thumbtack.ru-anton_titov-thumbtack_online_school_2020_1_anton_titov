package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.daoimpl.EmployeeDaoImpl;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Skill;
import net.thumbtack.school.hiring.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestEmployeeDaoImpl {
  /*  @Test
    public void testGetAll() throws ServerException {
        EmployeeDaoImpl employeeDaoImpl = new EmployeeDaoImpl();
        employeeDaoImpl.save(new Employee(UUID.randomUUID().toString(), "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru", true, new ArrayList<>(), true));
        employeeDaoImpl.save(new Employee(UUID.randomUUID().toString(), "Ivan", "Petrovich", "Petrovsky", "petr87", "wh45dssdc9", "petr@mail.ru", true, new ArrayList<>(), true));
        assertEquals(2, employeeDaoImpl.getAll().size());
    }

    @Test
    public void testGet() throws ServerException {
        EmployeeDaoImpl employeeDaoImpl = new EmployeeDaoImpl();
        Employee employee1 = new Employee(UUID.randomUUID().toString(), "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru", true, new ArrayList<>(), true);
        Employee employee2 = new Employee(UUID.randomUUID().toString(), "Ivan", "Petrovich", "Petrovsky", "petr87", "wh45dssdc9", "petr@mail.ru", true, new ArrayList<>(), true);
        employeeDaoImpl.save(employee1);
        employeeDaoImpl.save(employee2);
        assertEquals(employee1, employeeDaoImpl.getById(employee1.getId()));
        employeeDaoImpl.delete(employee1.getId());
        employeeDaoImpl.delete(employee2.getId());
    }

    @Test
    public void testSave() throws ServerException {
        EmployeeDaoImpl employeeDaoImpl = new EmployeeDaoImpl();
        String id = UUID.randomUUID().toString();
        Employee employee = new Employee(id, "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru", true, new ArrayList<>(), true);
        employeeDaoImpl.save(employee);
        try {
            employeeDaoImpl.save(employee);
        } catch (ServerException se) {
            assertEquals(ErrorCode.REPEATING_EMPLOYEE, se.getErrorCode());
        } finally {
            employeeDaoImpl.delete(employee.getId());
        }
    }

    @Test
    public void testUpdate1() throws ServerException {
        EmployeeDaoImpl employeeDaoImpl = new EmployeeDaoImpl();
        String id = UUID.randomUUID().toString();
        List<Skill> attainments = new ArrayList<>();
        attainments.add(new Skill("ss", 4));
        Employee employee = new Employee(id, "Egor", "Ivanovich", "Ivanov", "Egorka", "eeeeee", "egorkinmail@mail.ru", true, attainments, true);
        Employee newEmployee = new Employee(id, "Egor", "Nicolaevich", "Ivanov", "Egorka", "ee444", "egorkinmail@mail.ru", true, attainments, true);
        employeeDaoImpl.save(employee);
        employeeDaoImpl.update(employee.getId(), newEmployee);
        assertEquals(newEmployee, employeeDaoImpl.getById(id));
        employeeDaoImpl.delete(newEmployee.getId());
    }*/
}
