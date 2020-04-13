package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.Exception.ErrorCode;
import net.thumbtack.school.hiring.Exception.ServerException;

import java.util.UUID;

//REVU: Employee и Employer имеют схожие поля
// возможно, будет удобно иметь класс User и от него наследовать эти два класса
public class Employee {
    //REVU:  удобнее сделать id строкой (String)
    // UUID - способ получения уникальной строки
    private UUID id;
    private String firstName;
    private String patronymic;
    private String lastName;
    private String login;
    private String password;
    private String email;


    public Employee(UUID id, String firstName, String patronymic, String lastName, String login, String password, String email) throws ServerException {
        setId(id);
        setFirstName(firstName);
        setPatronymic(patronymic);
        setLastName(lastName);
        setLogin(login);
        setPassword(password);
        setEmail(email);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws ServerException {
        if (firstName == null || firstName.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_FIRST_NAME_EXCEPTION);
        }
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws ServerException {
        if (lastName == null || lastName.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_LAST_NAME_EXCEPTION);
        }
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) throws ServerException {
        if (login == null || login.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_LOGIN_EXCEPTION);
        }
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws ServerException {
        if (password == null || password.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_PASSWORD_EXCEPTION);
        }
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws ServerException {
        if (email == null || email.isEmpty()) {
            throw new ServerException(ErrorCode.EMAIL_EXCEPTION);
        }
        this.email = email;
    }
}
