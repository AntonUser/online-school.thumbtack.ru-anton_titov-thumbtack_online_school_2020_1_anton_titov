package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.Dao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Skill;
import net.thumbtack.school.hiring.model.Vacancy;

import java.util.List;

public class EmployeeDaoImpl implements Dao<Employee> {
    private DataBase dataBase;

    public EmployeeDaoImpl() {
        this.dataBase = DataBase.getInstance();
    }

    public String registerEmployee(Employee employee) throws ServerException {
        return dataBase.registerUser(employee);
    }

    public String loginEmployee(String login, String password) throws ServerException {
        return dataBase.loginUser(login, password);
    }

    public Employee getEmployeeById(String id) throws ServerException {
        return (Employee) dataBase.getUserById(id);
    }

    public void logOut(String token) {
        dataBase.logoutUser(token);
    }


    public void addSkill(Skill attainment, String token) throws ServerException {
        dataBase.addSkillForEmployee(attainment, token);
    }

    public void removeEmployeeSkill(String id, String name) throws ServerException {
        dataBase.removeEmployeeSkill(id, name);
    }

    public void updateEmployeeSkill(String token, String oldNameSkill, Skill newSkill) throws ServerException {
        dataBase.updateEmployeeSkill(token, oldNameSkill, newSkill);
    }

    public List<Vacancy> getVacanciesListNotLess(List<Skill> skills) {
        return dataBase.getVacanciesListNotLess(skills);
    }

    public List<Vacancy> getVacanciesListObligatoryDemand(List<Skill> skills) {
        return dataBase.getVacanciesListObligatoryDemand(skills);
    }

    public List<Vacancy> getVacanciesListOnlyName(List<Skill> skills) {
        return dataBase.getVacanciesListOnlyName(skills);
    }

    public List<Vacancy> getVacanciesListWithOneDemand(List<Skill> skills) {
        return dataBase.getVacanciesListWithOneDemand(skills);
    }

    public void setAccountStatusEnable(String token) throws ServerException {
        dataBase.enableEmployee(token);
    }

    public void setAccountStatusDisable(String token) throws ServerException {
        dataBase.disableEmployee(token);
    }
/*

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
        dataBase.updateEmployee(id, newEmployee);
    }

    @Override
    public void delete(String id) throws ServerException {
        dataBase.removeAccount(id);
    }
}
