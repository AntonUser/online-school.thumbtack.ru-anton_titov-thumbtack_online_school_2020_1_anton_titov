package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEmployer {

  /*  @Test
    public void testFirstName() throws ServerException {
        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "", "Sergeevich", "Petrov", "iva05", "w5464x52", true);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_FIRST_NAME_EXCEPTION, e.getErrorCode());
        }

        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", null, "Sergeevich", "Petrov", "iva05", "w5464x52", true);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_FIRST_NAME_EXCEPTION, e.getErrorCode());
        }

        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", "iva05", "w5464x52", true);
            employer.setFirstName("");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_FIRST_NAME_EXCEPTION, e.getErrorCode());
        }

        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", "iva05", "w5464x52", true);
            employer.setFirstName(null);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_FIRST_NAME_EXCEPTION, e.getErrorCode());
        }
    }

    @Test
    public void testName() {
        try {
            Employer employer = new Employer("", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", "iva05", "w5464x52", true);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_NAME_EXCEPTION, e.getErrorCode());
        }

        try {
            Employer employer = new Employer(null, "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", "iva05", "w5464x52", true);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_NAME_EXCEPTION, e.getErrorCode());
        }

        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", "iva05", "w5464x52", true);
            employer.setName("");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_NAME_EXCEPTION, e.getErrorCode());
        }

        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", "iva05", "w5464x52", true);
            employer.setName(null);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_NAME_EXCEPTION, e.getErrorCode());
        }
    }

    @Test
    public void testLogin() {
        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", "", "w5464x52", true);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_LOGIN_EXCEPTION, e.getErrorCode());
        }

        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", null, "w5464x52", true);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_LOGIN_EXCEPTION, e.getErrorCode());
        }

        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", "", "w5464x52", true);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_LOGIN_EXCEPTION, e.getErrorCode());
        }

        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", null, "w5464x52", true);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_LOGIN_EXCEPTION, e.getErrorCode());
        }
    }

    @Test
    public void testPassword() {
        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", "iva05", "", true);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_PASSWORD_EXCEPTION, e.getErrorCode());
        }

        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", "iva05", null, true);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_PASSWORD_EXCEPTION, e.getErrorCode());
        }

        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", "iva05", "w5464x52", true);
            employer.setPassword("");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_PASSWORD_EXCEPTION, e.getErrorCode());
        }

        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", "iva05", "w5464x52", true);
            employer.setPassword(null);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_PASSWORD_EXCEPTION, e.getErrorCode());
        }
    }

    @Test
    public void testEmail() {
        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "", "", "Ivan", "Sergeevich", "Petrov", "iva05", "w5464x52", true);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.EMAIL_EXCEPTION, e.getErrorCode());
        }

        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", null, "", "Ivan", "Sergeevich", "Petrov", "iva05", "w5464x52", true);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.EMAIL_EXCEPTION, e.getErrorCode());
        }

        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", "iva05", "w5464x52", true);
            employer.setEmail("");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.EMAIL_EXCEPTION, e.getErrorCode());
        }

        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", "iva05", "w5464x52", true);
            employer.setEmail(null);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.EMAIL_EXCEPTION, e.getErrorCode());
        }
    }

    @Test
    public void testAddress() {
        try {
            Employer employer = new Employer("Thumbtack", "", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", "iva05", "w5464x52", true);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_ADDRESS_EXCEPTION, e.getErrorCode());
        }

        try {
            Employer employer = new Employer("Thumbtack", null, "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", "iva05", "w5464x52", true);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_ADDRESS_EXCEPTION, e.getErrorCode());
        }

        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", "iva05", "w5464x52", true);
            employer.setAddress("");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_ADDRESS_EXCEPTION, e.getErrorCode());
        }

        try {
            Employer employer = new Employer("Thumbtack", "646255 Omsk ul.Gagarina d.3", "thumbtack@gmail.com", "", "Ivan", "Sergeevich", "Petrov", "iva05", "w5464x52", true);
            employer.setAddress(null);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_ADDRESS_EXCEPTION, e.getErrorCode());
        }
    }*/
}
