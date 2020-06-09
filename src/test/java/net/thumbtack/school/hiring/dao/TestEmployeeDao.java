package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.daoimpl.EmployeeDao;
import net.thumbtack.school.hiring.database.DataBase;
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
        DataBase dataBase = DataBase.getInstance();
        EmployeeDao employeeDao = new EmployeeDao(dataBase);
        employeeDao.save(new Employee(UUID.randomUUID().toString(), "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru", true, new ArrayList<>()));
        employeeDao.save(new Employee(UUID.randomUUID().toString(), "Ivan", "Petrovich", "Petrovsky", "petr87", "wh45dssdc9", "petr@mail.ru", true, new ArrayList<>()));
        assertEquals(2, employeeDao.getAll().size());
    }

    @Test
    public void testGet() throws ServerException {
        DataBase dataBase = DataBase.getInstance();
        EmployeeDao employeeDao = new EmployeeDao(dataBase);
        Employee employee1 = new Employee(UUID.randomUUID().toString(), "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru", true, new ArrayList<>());
        Employee employee2 = new Employee(UUID.randomUUID().toString(), "Ivan", "Petrovich", "Petrovsky", "petr87", "wh45dssdc9", "petr@mail.ru", true, new ArrayList<>());
        employeeDao.save(employee1);
        employeeDao.save(employee2);
        assertEquals(employee1, employeeDao.getById(employee1.getId()));
        employeeDao.delete(employee1);
        employeeDao.delete(employee2);
    }

    @Test
    public void testSave() throws ServerException {
        DataBase dataBase = DataBase.getInstance();
        EmployeeDao employeeDao = new EmployeeDao(dataBase);
        String id = UUID.randomUUID().toString();
        Employee employee = new Employee(id, "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru", true, new ArrayList<>());
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
    public void testUpdate1() throws ServerException {
        DataBase dataBase = DataBase.getInstance();
        EmployeeDao employeeDao = new EmployeeDao(dataBase);
        String id = UUID.randomUUID().toString();
        List<Attainments> attainments = new ArrayList<>();
        attainments.add(new Attainments("ss", 4));
        Employee employee = new Employee(id,"Egor","Ivanovich","Ivanov","Egorka","eeeeee","egorkinmail@mail.ru",true,attainments);
        Employee newEmployee = new Employee(id,"Egor","Nicolaevich","Ivanov","Egorka","ee444","egorkinmail@mail.ru",true,attainments);
        employeeDao.save(employee);
        employeeDao.update(employee, newEmployee);
        assertEquals(newEmployee,employeeDao.getById(id));
        employeeDao.delete(newEmployee);
    }
}
