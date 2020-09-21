package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.daoimpl.EmployerDaoImpl;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employer;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEmployerDao {
   /* @Test
    public void testGetAll() throws ServerException {
        EmployerDaoImpl employerDaoImpl = new EmployerDaoImpl();
//        Employer employer = employerDao.getByLoginAndPassword("iva01", "w5464x52");
    //    employerDao.delete(employer.getId());
        employerDaoImpl.save(new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", UUID.randomUUID().toString(), "Vasily", "Sergeevich", "Petrov", "iva11", "w5464x52", true));
        employerDaoImpl.save(new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", UUID.randomUUID().toString(), "Vasily", "Sergeevich", "Petrov", "iva07", "w5464x42", true));
        assertEquals(2, employerDaoImpl.getAll().size());
        System.out.println("");
    }

    @Test
    public void testGet() throws ServerException {
        EmployerDaoImpl employerDaoImpl = new EmployerDaoImpl();
        Employer employer1 = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", UUID.randomUUID().toString(), "Vasily", "Sergeevich", "Petrov", "iva09", "w5464x52", true);
        Employer employer2 = new Employer("SPO", "646255 Tver' ul.Gagarina d.3", "spos@gmail.com", UUID.randomUUID().toString(), "Ivan", "Sergeevich", "Petrov", "iva03", "wfb464x2", true);
        employerDaoImpl.save(employer1);
        employerDaoImpl.save(employer2);
        assertEquals(employer1, employerDaoImpl.getById(employer1.getId()));
        employerDaoImpl.delete(employer1.getId());
        employerDaoImpl.delete(employer2.getId());
    }

    @Test
    public void testSave() throws ServerException {
        EmployerDaoImpl employerDaoImpl = new EmployerDaoImpl();
        Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", UUID.randomUUID().toString(), "Vasily", "Sergeevich", "Petrov", "iva01", "w5464x52", true);
        employerDaoImpl.save(employer);
        try {
            employerDaoImpl.save(employer);
        } catch (ServerException se) {
            assertEquals(ErrorCode.REPEATING_EMPLOYER, se.getErrorCode());
        } finally {
            employerDaoImpl.delete(employer.getId());
        }
    }

    @Test
    public void testUpdate() throws ServerException {
        EmployerDaoImpl employerDaoImpl = new EmployerDaoImpl();
        String id = "25s5wef55wef85wef8wde5wef";
        Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", id, "Vasily", "Sergeevich", "Petrov", "iva22", "w5466651", true);
        Employer newEmployer = new Employer("SPO", "646255 Tver' ul.Gagarina d.3", "spos@gmail.com", id, "Ivan", "Sergeevich", "Petrov", "iva55", "wfb4774x2", true);
        employerDaoImpl.save(employer);
        employerDaoImpl.update(employer.getId(), newEmployer);
        assertEquals(newEmployer, employerDaoImpl.getById(id));
        employerDaoImpl.delete(newEmployer.getId());

    }*/
}
