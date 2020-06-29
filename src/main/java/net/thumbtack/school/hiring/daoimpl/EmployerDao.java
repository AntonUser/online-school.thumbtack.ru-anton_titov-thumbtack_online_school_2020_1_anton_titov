package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.Dao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employee;
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

    public void setAccountStatus(String token, boolean status) {
        Employer employer = getById(token);
        employer.setActivity(status);
        dataBase.updateEmployer(employer, employer);
    }

    public boolean isActivity(String token) {
        Employer employer = dataBase.getEmployerById(token);
        if (employer != null) {
            return employer.isActivity();
        }
        return false;
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
    public void update(Employer oldEmployer, Employer newEmployer) {
        dataBase.updateEmployer(oldEmployer, newEmployer);
    }

    @Override
    public void delete(Employer employer) {
        dataBase.deleteEmployer(employer);
    }
}