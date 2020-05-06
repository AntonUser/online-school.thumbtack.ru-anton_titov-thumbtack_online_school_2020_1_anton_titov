package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employer;

import java.util.List;

public class EmployerDao implements Dao<Employer> {
    DataBase dataBase;

    public EmployerDao(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Employer get(String id) {
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
