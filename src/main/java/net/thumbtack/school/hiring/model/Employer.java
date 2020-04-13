package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.Exception.ErrorCode;
import net.thumbtack.school.hiring.Exception.ServerException;

public class Employer {
    //REVU: а почему у Employee есть id, а у этого класса нет?
    private String name;
    private String address;
    private String email;
    private String firstName;
    private String patronymic;
    private String login;
    private String password;

    public Employer(String name, String address, String email, String firstName, String patronymic, String login, String password) throws ServerException {
        setAddress(address);
        setEmail(email);
        setFirstName(firstName);
        setLogin(login);
        setName(name);
        setPassword(password);
        setPatronymic(patronymic);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws ServerException {
        if (name == null || name.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_NAME_EXCEPTION);
        }
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws ServerException {
        if (address == null || address.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_ADDRESS_EXCEPTION);
        }
        this.address = address;
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
}
