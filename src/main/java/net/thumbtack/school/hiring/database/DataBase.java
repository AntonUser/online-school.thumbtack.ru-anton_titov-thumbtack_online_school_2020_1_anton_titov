package net.thumbtack.school.hiring.database;

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

    //REVU: эти методы, стандартные для классов DTO, моделей, неприминимы здесь
    // особенно сеттеры
    // если тебе нужно добавить объект, сделай метод addEmployee тут, вместо того, чтобы доставать список и добавлять в него
    // а если возвращаешь список всех работников, то верни копию, чтобы никто из вне не мог изменить твою БД
    public List<Employer> getEmployerList() {
        return employerList;
    }

    public void setEmployerList(List<Employer> employerList) {
        DataBase.employerList = employerList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        DataBase.employeeList = employeeList;
    }
}

