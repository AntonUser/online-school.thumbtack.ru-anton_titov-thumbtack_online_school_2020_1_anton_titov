package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.daoimpl.EmployerDao;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employer;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEmployerDao {
    @Test
    public void testGetAll() throws ServerException {
        EmployerDao employerDao = new EmployerDao();
//        Employer employer = employerDao.getByLoginAndPassword("iva01", "w5464x52");
    //    employerDao.delete(employer.getId());
        employerDao.save(new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", UUID.randomUUID().toString(), "Vasily", "Sergeevich", "Petrov", "iva11", "w5464x52", true));
        employerDao.save(new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", UUID.randomUUID().toString(), "Vasily", "Sergeevich", "Petrov", "iva07", "w5464x42", true));
        assertEquals(2, employerDao.getAll().size());
        System.out.println("");
    }

    @Test
    public void testGet() throws ServerException {
        EmployerDao employerDao = new EmployerDao();
        Employer employer1 = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", UUID.randomUUID().toString(), "Vasily", "Sergeevich", "Petrov", "iva09", "w5464x52", true);
        Employer employer2 = new Employer("SPO", "646255 Tver' ul.Gagarina d.3", "spos@gmail.com", UUID.randomUUID().toString(), "Ivan", "Sergeevich", "Petrov", "iva03", "wfb464x2", true);
        employerDao.save(employer1);
        employerDao.save(employer2);
        assertEquals(employer1, employerDao.getById(employer1.getId()));
        employerDao.delete(employer1.getId());
        employerDao.delete(employer2.getId());
    }

    @Test
    public void testSave() throws ServerException {
        EmployerDao employerDao = new EmployerDao();
        Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", UUID.randomUUID().toString(), "Vasily", "Sergeevich", "Petrov", "iva01", "w5464x52", true);
        employerDao.save(employer);
        try {
            employerDao.save(employer);
        } catch (ServerException se) {
            assertEquals(ErrorCode.REPEATING_EMPLOYER, se.getErrorCode());
        } finally {
            employerDao.delete(employer.getId());
        }
    }

    @Test
    public void testUpdate() throws ServerException {
        EmployerDao employerDao = new EmployerDao();
        String id = "25s5wef55wef85wef8wde5wef";
        Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", id, "Vasily", "Sergeevich", "Petrov", "iva22", "w5466651", true);
        Employer newEmployer = new Employer("SPO", "646255 Tver' ul.Gagarina d.3", "spos@gmail.com", id, "Ivan", "Sergeevich", "Petrov", "iva55", "wfb4774x2", true);
        employerDao.save(employer);
        employerDao.update(employer.getId(), newEmployer);
        assertEquals(newEmployer, employerDao.getById(id));
        employerDao.delete(newEmployer.getId());

    }
}
