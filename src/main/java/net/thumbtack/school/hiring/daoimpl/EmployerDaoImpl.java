package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.Dao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.Requirement;
import net.thumbtack.school.hiring.model.Vacancy;

import java.util.List;

public class EmployerDaoImpl implements Dao<Employer> {
    private DataBase dataBase;

    public EmployerDaoImpl() {
        this.dataBase = DataBase.getInstance();
    }

    public String registerEmployer(Employer employer) throws ServerException {
        return dataBase.registerUser(employer);
    }

    public String loginEmployer(String login, String password) throws ServerException {
        return dataBase.loginUser(login, password);
    }

    public void logOut(String token) {
        dataBase.logoutUser(token);
    }

    public Employer getEmployerById(String id) throws ServerException {
        return (Employer) dataBase.getUserById(id);
    }

    public void addVacancy(Vacancy vacancy, String token) throws ServerException {
        dataBase.addVacancy(vacancy, token);
    }

    public void removeVacancy(String name, String token) throws ServerException {
        dataBase.removeVacancy(name, token);
    }

    public void addRequirement(Requirement requirement, String token, String nameVacancy) throws ServerException {
        dataBase.addRequirementInVacancy(requirement, token, nameVacancy);
    }

    public void removeRequirement(String token, String nameVacancy, String nameRequirement) throws ServerException {
        dataBase.removeRequirementOfVacancy(token, nameVacancy, nameRequirement);
    }

    public List<Employee> getEmployeesListNotLess(List<Requirement> requirements) {
        return dataBase.getEmployeesListNotLess(requirements);
    }

    public List<Employee> getEmployeesListObligatoryDemand(List<Requirement> requirements) {
        return dataBase.getEmployeesListObligatoryDemand(requirements);
    }

    public List<Employee> getEmployeesListOnlyName(List<Requirement> requirements) {
        return dataBase.getEmployeesListOnlyName(requirements);
    }

    public List<Employee> getEmployeesListWithOneDemand(List<Requirement> requirements) {
        return dataBase.getEmployeesListWithOneDemand(requirements);
    }

    public void enableVacancy(String idVacancy, String tokenEmployer) throws ServerException {
        dataBase.enableVacancy(idVacancy, tokenEmployer);
    }

    public void disableVacancy(String idVacancy, String tokenEmployer) throws ServerException {
        dataBase.disableVacancy(idVacancy, tokenEmployer);
    }

    public List<Vacancy> getAllVacanciesByToken(String token) throws ServerException {
        return dataBase.getAllVacanciesByToken(token);
    }

    public List<Vacancy> getAllActiveVacanciesByToken(String token) throws ServerException {
        return dataBase.getAllActiveVacanciesByToken(token);
    }

    public List<Vacancy> getAllNotActiveVacanciesByToken(String token) throws ServerException {
        return dataBase.getAllNotActiveVacanciesByToken(token);
    }

    @Override
    public void save(Employer employer) throws ServerException {
        dataBase.saveUser(employer);
    }

    @Override
    public void update(String id, Employer newEmployer) throws ServerException {
        dataBase.updateEmployer(id, newEmployer);
    }

    @Override
    public void delete(String id) throws ServerException {
        dataBase.removeAccount(id);
    }
}