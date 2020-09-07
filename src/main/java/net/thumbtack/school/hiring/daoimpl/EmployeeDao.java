package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.Dao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Attainments;
import net.thumbtack.school.hiring.model.Demand;
import net.thumbtack.school.hiring.model.Employee;

import java.util.List;

public class EmployeeDao implements Dao<Employee, List<Employee>> {
    private DataBase dataBase;

    public EmployeeDao() {
        this.dataBase = DataBase.getInstance();
    }

    public String loginEmployee(String login, String password) throws ServerException {
        return dataBase.loginEmployee(login, password);
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
        return dataBase.isActivityEmployee(token);
    }

    public void setAccountStatus(String token, boolean status) throws ServerException {
        dataBase.setAccountEmployeeStatus(token, status);
    }

    public void addSkillForEmployee(Attainments attainments, String token) throws ServerException {
        dataBase.addSkillForEmployee(attainments, token);
    }

    public void setEmployeeStatus(String token, boolean status) throws ServerException {
        dataBase.setEmployeeStatus(token, status);
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
    public void update(String id, Employee newEmployee) throws ServerException {
        dataBase.updateEmployee(id, newEmployee);
    }

    @Override
    public void delete(String id) {
        dataBase.deleteEmployee(id);
    }
}
