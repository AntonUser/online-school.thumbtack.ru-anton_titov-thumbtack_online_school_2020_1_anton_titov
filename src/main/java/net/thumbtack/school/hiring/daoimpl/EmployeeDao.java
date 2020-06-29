package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.Dao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Demand;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Employer;

import java.util.List;

public class EmployeeDao implements Dao<Employee, List<Employee>> {
    private DataBase dataBase;

    public EmployeeDao() {
        this.dataBase = DataBase.getInstance();
    }

    public Employee getByLoginAndPassword(String login, String password) {
        return dataBase.getEmployeeByLoginAndPassword(login, password);
    }

    public Employee getById(String id) {
        return dataBase.getEmployeeById(id);
    }

    public List<Employee> getEmployeeListNotLessByDemand(List<Demand> demands) {
        return dataBase.getEmployeeListNotLessByDemands(demands);
    }

    public List<Employee> getEmployeeListObligatoryDemandByDemands(List<Demand> demands) {
        return dataBase.getEmployeeListObligatoryDemandByDemands(demands);
    }

    public List<Employee> getEmployeeListByDemands(List<Demand> demands) {
        return dataBase.getEmployeeListByDemands(demands);
    }

    public List<Employee> getEmployeeListWithOneDemandByDemands(List<Demand> demands) {
        return dataBase.getEmployeeListWithOneDemandByDemands(demands);
    }

    public boolean isActivity(String token) {
        //REVU: не добавляй тут логику, сделай метод в БД, который делает то же самое
        Employee employee = dataBase.getEmployeeById(token);
        if (employee != null) {
            return employee.isActivity();
        }
        return false;
    }

    public void setAccountStatus(String token, boolean status) {
        //REVU: не добавляй тут логику, сделай метод в БД, который делает то же самое
        Employee employee = getById(token);
        employee.setActivity(status);
        dataBase.updateEmployee(employee, employee);
    }

    @Override
    public List<Employee> getAll() {
        return dataBase.getEmployeeList();
    }

    @Override
    public void save(Employee employee) throws ServerException {
        //REVU: не добавляй тут логику, сделай метод в БД, который делает то же самое
        if (!employee.getAttainmentsList().isEmpty()) {
            //если умения уже есть, то добавим их в общий список
            dataBase.addSubSet(employee.getNamesAttainments());
        }
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
