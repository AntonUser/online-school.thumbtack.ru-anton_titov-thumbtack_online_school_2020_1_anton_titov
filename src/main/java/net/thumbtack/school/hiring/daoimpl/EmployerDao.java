package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.Dao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employer;

import java.util.List;

public class EmployerDao implements Dao<Employer, List<Employer>> {
    private DataBase dataBase;

    public EmployerDao() {
        this.dataBase = DataBase.getInstance();
    }


    public Employer getByLoginAndPassword(String login, String password) throws ServerException {
        return dataBase.getEmployerByLoginAndPassword(login, password);
    }

    public Employer getById(String id) {
        return dataBase.getEmployerById(id);
    }

    public void setAccountStatus(String token, boolean status) throws ServerException {
        dataBase.setAccountEmployerStatus(token, status);
    }

    public boolean isActivity(String token) {
        return dataBase.isActivityEmployer(token);
    }

    public void updateEmployerFirstName(String token, String firstName) throws ServerException {
        dataBase.updateEmployerFirstName(token, firstName);
    }

    public void updateEmployerPatronymic(String token, String patronymic) throws ServerException {
        dataBase.updateEmployerPatronymic(token, patronymic);
    }

    public void updateEmployerLastName(String token, String lastName) throws ServerException {
        dataBase.updateEmployerLastName(token, lastName);
    }

    public void updateEmployerEmail(String token, String email) throws ServerException {
        dataBase.updateEmployerEmail(token, email);
    }

    public void updateEmployerPassword(String token, String password) throws ServerException {
        dataBase.updateEmployerPassword(token, password);
    }

    public void updateEmployerNameCompany(String token, String nameCompany) throws ServerException {
        dataBase.updateEmployerNameCompany(token, nameCompany);
    }

    public void updateEmployerAddress(String token, String address) throws ServerException {
        dataBase.updateEmployerAddress(token, address);
    }

    public String loginEmployer(String login, String password) throws ServerException {
        return dataBase.loginEmployer(login, password);
    }

    public void removeAccount(String token) {
        dataBase.removeAccountEmployer(token);
    }

    @Override
    public List<Employer> getAll() {
        return dataBase.getEmployerList();
    }

    @Override
    public void save(Employer employer) throws ServerException {
        dataBase.addEmployer(employer);
    }

    @Override
    public void update(String id, Employer newEmployer) {
        dataBase.updateEmployer(id, newEmployer);
    }

    @Override
    public void delete(String id) {
        dataBase.deleteEmployer(id);
    }
}