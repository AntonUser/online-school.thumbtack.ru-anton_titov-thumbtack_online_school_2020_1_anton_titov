package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import org.junit.jupiter.api.Test;


import java.util.UUID;


import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

public class TestEmployee {
    @Test
    public void testId() throws ServerException {
        String uuid = UUID.randomUUID().toString();
        Employee employee = new Employee(uuid, "Vasily", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru");
        assertEquals(uuid, employee.getId());
    }

    @Test
    public void testFirstName() throws ServerException {
        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), null, "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_FIRST_NAME_EXCEPTION, e.getErrorCode());
        }

        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_FIRST_NAME_EXCEPTION, e.getErrorCode());
        }

        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Vasiliy", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru");
            employee.setFirstName("");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_FIRST_NAME_EXCEPTION, e.getErrorCode());
        }

        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Vasiliy", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru");
            employee.setFirstName(null);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_FIRST_NAME_EXCEPTION, e.getErrorCode());
        }
    }

    @Test
    public void testLastName() {
        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Vasiliy", "", "", "petrov008", "wh45dh79", "petrow@mail.ru");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_LAST_NAME_EXCEPTION, e.getErrorCode());
        }

        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Vasily", "", null, "petrov008", "wh45dh79", "petrow@mail.ru");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_LAST_NAME_EXCEPTION, e.getErrorCode());
        }

        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Vasiliy", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru");
            employee.setLastName("");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_LAST_NAME_EXCEPTION, e.getErrorCode());
        }

        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Vasiliy", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru");
            employee.setLastName(null);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_LAST_NAME_EXCEPTION, e.getErrorCode());
        }
    }

    @Test
    public void testLogin() {
        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Vasiliy", "", "Petrov", "", "wh45dh79", "petrow@mail.ru");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_LOGIN_EXCEPTION, e.getErrorCode());
        }

        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Vasily", "", "Petrov", null, "wh45dh79", "petrow@mail.ru");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_LOGIN_EXCEPTION, e.getErrorCode());
        }

        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Vasiliy", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru");
            employee.setLogin("");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_LOGIN_EXCEPTION, e.getErrorCode());
        }

        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Vasiliy", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru");
            employee.setLogin(null);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_LOGIN_EXCEPTION, e.getErrorCode());
        }
    }

    @Test
    public void testPassword() {
        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Vasiliy", "", "Petrov", "petrov008", "", "petrow@mail.ru");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_PASSWORD_EXCEPTION, e.getErrorCode());
        }

        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Vasily", "", "Petrov", "petrov008", null, "petrow@mail.ru");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_PASSWORD_EXCEPTION, e.getErrorCode());
        }

        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Vasiliy", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru");
            employee.setPassword("");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_PASSWORD_EXCEPTION, e.getErrorCode());
        }

        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Vasiliy", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru");
            employee.setPassword(null);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.NULL_PASSWORD_EXCEPTION, e.getErrorCode());
        }
    }

    @Test
    public void testEmail() {
        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Vasiliy", "", "Petrov", "petrov008", "wh45dh79", "");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.EMAIL_EXCEPTION, e.getErrorCode());
        }

        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Vasiliy", "", "Petrov", "petrov008", "wh45dh79", null);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.EMAIL_EXCEPTION, e.getErrorCode());
        }

        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Vasiliy", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru");
            employee.setEmail("");
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.EMAIL_EXCEPTION, e.getErrorCode());
        }

        try {
            Employee employee = new Employee(UUID.randomUUID().toString(), "Vasiliy", "", "Petrov", "petrov008", "wh45dh79", "petrow@mail.ru");
            employee.setEmail(null);
            fail();
        } catch (ServerException e) {
            assertEquals(ErrorCode.EMAIL_EXCEPTION, e.getErrorCode());
        }
    }
}
