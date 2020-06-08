package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.daoimpl.EmployerDao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employer;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEmployerDao {
    @Test
    public void testGetAll() throws ServerException {
        DataBase dataBase = DataBase.getInstance();
        EmployerDao employerDao = new EmployerDao(dataBase);
        Employer employer = employerDao.getByLoginAndPassword("iva01", "w5464x52");
        employerDao.delete(employer);
        employerDao.save(new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", UUID.randomUUID().toString(), "Vasily", "Sergeevich", "iva11", "w5464x52"));
        employerDao.save(new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", UUID.randomUUID().toString(), "Vasily", "Sergeevich", "iva07", "w5464x42"));
        assertEquals(2, employerDao.getAll().size());
        System.out.println("");
    }

    @Test
    public void testGet() throws ServerException {
        DataBase dataBase = DataBase.getInstance();
        EmployerDao employerDao = new EmployerDao(dataBase);
        Employer employer1 = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", UUID.randomUUID().toString(), "Vasily", "Sergeevich", "iva09", "w5464x52");
        Employer employer2 = new Employer("SPO", "646255 Tver' ul.Gagarina d.3", "spos@gmail.com", UUID.randomUUID().toString(), "Ivan", "Sergeevich", "iva03", "wfb464x2");
        employerDao.save(employer1);
        employerDao.save(employer2);
        assertEquals(employer1, employerDao.getById(employer1.getId()));
        employerDao.delete(employer1);
        employerDao.delete(employer2);
    }

    @Test
    public void testSave() throws ServerException {
        DataBase dataBase = DataBase.getInstance();
        EmployerDao employerDao = new EmployerDao(dataBase);
        String id = UUID.randomUUID().toString();
        Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", UUID.randomUUID().toString(), "Vasily", "Sergeevich", "iva01", "w5464x52");
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
        Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", id, "Vasily", "Sergeevich", "iva22", "w5466651");
        Employer newEmployer = new Employer("SPO", "646255 Tver' ul.Gagarina d.3", "spos@gmail.com", id, "Ivan", "Sergeevich", "iva55", "wfb4774x2");
        employerDao.save(employer);
        employerDao.update(employer, newEmployer);
        assertEquals(newEmployer, employerDao.getById(id));
        employerDao.delete(newEmployer);

    }
}
