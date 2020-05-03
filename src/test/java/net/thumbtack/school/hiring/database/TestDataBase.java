package net.thumbtack.school.hiring.database;


import net.thumbtack.school.hiring.exception.ServerException;

import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Employer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TestDataBase {
    @Test
    public void testGetAddEmployee() throws ServerException {
        DataBase dataBase = DataBase.getInstance();
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(UUID.randomUUID().toString(), "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru"));
        list.add(new Employee(UUID.randomUUID().toString(), "Ivan", "Petrovich", "Petrovsky", "petr87", "wh45dssdc9", "petr@mail.ru"));
        dataBase.setEmployeeList(list);
        assertEquals(list, dataBase.getEmployeeList());
    }

    @Test
    public void testGetAddEmployer() throws ServerException {
        DataBase dataBase = DataBase.getInstance();
        List<Employer> list = new ArrayList<>();
        list.add(new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", UUID.randomUUID().toString(), "Gleb", "Sergeevich", "G05", "w5464x52"));
        list.add(new Employer("SPOService", "654255 Nefteyugansk ul.Laso d.3", "spo@gmail.com", UUID.randomUUID().toString(), "Dmitry", "Sergeevich", "D88d", "q5464x52"));
        dataBase.setEmployerList(list);
        assertEquals(list, dataBase.getEmployerList());

    }
}
