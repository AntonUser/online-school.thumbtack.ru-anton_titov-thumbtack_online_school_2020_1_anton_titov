package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.Dao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employer;

import java.util.List;

public class EmployerDaoImpl implements Dao<Employer, List<Employer>> {
    private DataBase dataBase;

    public EmployerDaoImpl() {
        this.dataBase = DataBase.getInstance();
    }

    public String registerEmployer(Employer employer) {
        return dataBase.registerUser(employer);
    }

    public String loginEmployer(String login, String password) {
        return dataBase.loginUser(login, password);
    }

    public void logOut(String token) {
        dataBase.logoutUser(token);
    }

    public void removeAccount(String token) {
        dataBase.removeAccount(token);
    }

    public Employer getEmployerById(String id) {
        return (Employer) dataBase.getUserById(id);
    }

    /*
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


    public String loginEmployer(String login, String password) throws ServerException {
        return dataBase.loginEmployer(login, password);
    }

    public void removeAccount(String token) {
        dataBase.removeAccountEmployer(token);
    }
*/
    @Override
    public void save(Employer employer) throws ServerException {
        dataBase.saveUser(employer);
    }

    @Override
    public void update(String id, Employer newEmployer) throws ServerException {
//        dataBase.updateEmployer(id, newEmployer);
    }

    @Override
    public void delete(String id) {
//        dataBase.deleteEmployer(id);
    }
}