package net.thumbtack.school.hiring.dao;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employee;

import java.util.List;

public class EmployeeDao {

    public String get(String idEmployeeJson, DataBase dataBase) {
        Gson gson = new Gson();
        List<Employee> list = getAll(dataBase);
        for (Employee employee : list) {
            if (idEmployeeJson.equals(employee.getId())) {
                return gson.toJson(employee);
            }
        }
        return null;
    }

    public List<Employee> getAll(DataBase dataBase) {
            return dataBase.getEmployeeList();
    }

    public void save(String employeeJson, DataBase dataBase) throws ServerException {
        if (get(employeeJson, dataBase) != null) {
            throw new ServerException(ErrorCode.REPEATING_EMPLOYEE);
        }
        Gson gson = new Gson();
        List<Employee> list = getAll(dataBase);
        list.add(gson.fromJson(employeeJson, Employee.class));
        dataBase.setEmployeeList(list);
    }

    public void update(String oldEmployeeJson, String newEmployeeJson, DataBase dataBase) {
        Gson gson = new Gson();
        List<Employee> list = getAll(dataBase);
        list.set(list.indexOf(gson.fromJson(oldEmployeeJson, Employee.class)), gson.fromJson(newEmployeeJson, Employee.class));
        dataBase.setEmployeeList(list);
    }

    public void delete(String employeeJson, DataBase dataBase) {
        Gson gson = new Gson();
        List<Employee> list = getAll(dataBase);
        list.remove(gson.fromJson(employeeJson, Employee.class));
        dataBase.setEmployeeList(list);
    }
}
