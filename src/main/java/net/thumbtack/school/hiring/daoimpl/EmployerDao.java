package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.Dao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employer;

import java.util.List;

public class EmployerDao implements Dao<Employer, List<Employer>> {
    private DataBase dataBase;

    public EmployerDao(DataBase dataBase) {
        this.dataBase = dataBase;
    }


    public Employer getByLoginAndPassword(String login, String password) {
        return dataBase.getEmployerByLoginAndPassword(login, password);
    }

    public Employer getById(String id) {
        return dataBase.getEmployerById(id);
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