package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.Dao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Skill;

import java.util.List;

public class EmployeeDaoImpl implements Dao<Employee, List<Employee>> {
    private DataBase dataBase;

    public EmployeeDaoImpl() {
        this.dataBase = DataBase.getInstance();
    }

    public String registerEmployee(Employee employee) {
        return dataBase.registerUser(employee);
    }

    public String loginEmployee(String login, String password) {
        return dataBase.loginUser(login, password);
    }

    public Employee getEmployeeById(String id) {
        return (Employee) dataBase.getUserById(id);
    }

    public void logOut(String token) {
        dataBase.logoutUser(token);
    }

    public void removeAccount(String token) {
        dataBase.removeAccount(token);
    }

    public void removeEmployeeSkill(String id, Skill skill) {
        dataBase.removeEmployeeSkill(id, skill);
    }
    /*public Employee getById(String id) {
        return dataBase.(id);
    }*/
/*
    public List<Employee> getEmployeeListNotLessByDemand(List<Requirement> demands) {
        return dataBase.getEmployeeListNotLessByDemands(demands);
    }

    public List<Employee> getEmployeeListObligatoryDemandByDemands(List<Requirement> demands) {
        return dataBase.getEmployeeListObligatoryDemandByDemands(demands);
    }

    public List<Employee> getEmployeeListByDemands(List<Requirement> demands) {
        return dataBase.getEmployeeListByDemands(demands);
    }

    public List<Employee> getEmployeeListWithOneDemandByDemands(List<Requirement> demands) {
        return dataBase.getEmployeeListWithOneDemandByDemands(demands);
    }

    public boolean isActivity(String token) {
        return dataBase.isActivityEmployee(token);
    }

    public void setAccountStatus(String token, boolean status) throws ServerException {
        dataBase.setAccountEmployeeStatus(token, status);
    }

    public void addSkillForEmployee(Skill attainments, String token) throws ServerException {
        dataBase.addSkillForEmployee(attainments, token);
    }

    public void setEmployeeStatus(String token, boolean status) throws ServerException {
        dataBase.setEmployeeStatus(token, status);
    }*/

    @Override
    public void save(Employee employee) throws ServerException {
        dataBase.saveUser(employee);
    }

    @Override
    public void update(String id, Employee newEmployee) throws ServerException {
        //   dataBase.updateEmployee(id, newEmployee);
    }

    @Override
    public void delete(String id) {
        //  dataBase.deleteEmployee(id);
    }
}
