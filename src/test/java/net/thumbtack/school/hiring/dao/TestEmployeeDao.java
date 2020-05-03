package net.thumbtack.school.hiring.dao;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class TestEmployeeDao {
    @Test
    public void testGet() throws ServerException {
        Gson gson = new Gson();
        DataBase dataBase = DataBase.getInstance();
        EmployeeDao employeeDao = new EmployeeDao();
        Employee employee1 = new Employee(UUID.randomUUID().toString(), "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru");
        Employee employee2 = new Employee(UUID.randomUUID().toString(), "Ivan", "Petrovich", "Petrovsky", "petr87", "wh45dssdc9", "petr@mail.ru");
        employeeDao.save(gson.toJson(employee1), dataBase);
        employeeDao.save(gson.toJson(employee2), dataBase);
        assertEquals(gson.toJson(employee1), employeeDao.get(employee1.getId(), dataBase));
    }

    @Test
    public void testGetAll() throws ServerException {
        Gson gson = new Gson();
        DataBase dataBase = DataBase.getInstance();
        EmployeeDao employeeDao = new EmployeeDao();
        dataBase.setEmployeeList(new ArrayList<>());
        employeeDao.save(gson.toJson(new Employee(UUID.randomUUID().toString(), "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru")), dataBase);
        employeeDao.save(gson.toJson(new Employee(UUID.randomUUID().toString(), "Ivan", "Petrovich", "Petrovsky", "petr87", "wh45dssdc9", "petr@mail.ru")), dataBase);
        assertEquals(2, employeeDao.getAll(dataBase).size());
    }

    @Test
    public void testSave() throws ServerException {
        Gson gson = new Gson();
        DataBase dataBase = DataBase.getInstance();
        EmployeeDao employeeDao = new EmployeeDao();
        String id = UUID.randomUUID().toString();
        employeeDao.save(gson.toJson(new Employee(id, "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru")), dataBase);
        try {
            employeeDao.save(gson.toJson(new Employee(id, "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru")), dataBase);
        } catch (ServerException se) {
            assertEquals(ErrorCode.REPEATING_EMPLOYEE, se.getErrorCode());
        }
    }

    @Test
    public void testUpdate() throws ServerException {
        Gson gson = new Gson();
        DataBase dataBase = DataBase.getInstance();
        EmployeeDao employeeDao = new EmployeeDao();
        String id = "25s5wef55wef85wef8wde5wef";
        Employee employee = new Employee(id, "Ivan", "Petrovich", "Petrovsky", "petr87", "wh45dssdc9", "petr@mail.ru");
        Employee newEmployee = new Employee(id, "Genadiy", "Petrovich", "Petrovsky", "petr87", "wh45dssdc9", "petr@mail.ru");
        employeeDao.save(gson.toJson(employee), dataBase);
        employeeDao.update(gson.toJson(employee), gson.toJson(newEmployee), dataBase);
        assertEquals(newEmployee, gson.fromJson(employeeDao.get(id, dataBase), Employee.class));
    }

    @Test
    public void testDelete() throws ServerException {
        Gson gson = new Gson();
        DataBase dataBase = DataBase.getInstance();
        EmployeeDao employeeDao = new EmployeeDao();
        String id = "25s5wef55wef85wef8wde5wef";
        Employee employee = new Employee(id, "Genadiy", "Petrovich", "Petrovsky", "petr87", "wh45dssdc9", "petr@mail.ru");
        employeeDao.delete(gson.toJson(employee), dataBase);
        assertNull(employeeDao.get(id, dataBase));
    }
}
