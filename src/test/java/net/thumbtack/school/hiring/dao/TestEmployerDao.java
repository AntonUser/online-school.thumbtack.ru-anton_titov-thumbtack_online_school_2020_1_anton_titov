package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.daoimpl.EmployerDao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employer;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEmployerDao {
    @Test
    public void testGetAll() throws ServerException {
        DataBase dataBase = DataBase.getInstance();
        EmployerDao employerDao = new EmployerDao(dataBase);
        employerDao.save(new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", UUID.randomUUID().toString(), "Vasily", "Sergeevich", "iva05", "w5464x52"));
        employerDao.save(new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", UUID.randomUUID().toString(), "Vasily", "Sergeevich", "iva05", "w5464x52"));
        assertEquals(2, employerDao.getAll().size());
    }

    @Test
    public void testGet() throws ServerException {
        DataBase dataBase = DataBase.getInstance();
        EmployerDao employerDao = new EmployerDao(dataBase);
        Employer employer1 = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", UUID.randomUUID().toString(), "Vasily", "Sergeevich", "iva05", "w5464x52");
        Employer employer2 = new Employer("SPO", "646255 Tver' ul.Gagarina d.3", "spos@gmail.com", UUID.randomUUID().toString(), "Ivan", "Sergeevich", "iva05", "wfb464x2");
        employerDao.save(employer1);
        employerDao.save(employer2);
        assertEquals(employer1, employerDao.get(employer1.getId()));
        employerDao.delete(employer1);
        employerDao.delete(employer2);
    }

    @Test
    public void testSave() throws ServerException {
        DataBase dataBase = DataBase.getInstance();
        EmployerDao employerDao = new EmployerDao(dataBase);
        String id = UUID.randomUUID().toString();
        Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", UUID.randomUUID().toString(), "Vasily", "Sergeevich", "iva05", "w5464x52");
        employerDao.save(employer);
        try {
            employerDao.save(employer);
        } catch (ServerException se) {
            assertEquals(ErrorCode.REPEATING_EMPLOYER, se.getErrorCode());
        } finally {
            employerDao.delete(employer);
        }
    }

    @Test
    public void testUpdate() throws ServerException {
        DataBase dataBase = DataBase.getInstance();
        EmployerDao employerDao = new EmployerDao(dataBase);
        String id = "25s5wef55wef85wef8wde5wef";
        Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", id, "Vasily", "Sergeevich", "iva05", "w5464x52");
        Employer newEmployer = new Employer("SPO", "646255 Tver' ul.Gagarina d.3", "spos@gmail.com", id, "Ivan", "Sergeevich", "iva05", "wfb464x2");
        employerDao.save(employer);
        employerDao.update(employer, newEmployer);
        assertEquals(newEmployer, employerDao.get(id));
        employerDao.delete(newEmployer);
    }
}
