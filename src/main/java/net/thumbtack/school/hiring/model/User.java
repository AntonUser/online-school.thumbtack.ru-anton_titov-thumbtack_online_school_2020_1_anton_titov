package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.Objects;

public class User {
    private String id;
    private String login;
    private String password;
    private String email;
    private String lastName;
    private String firstName;
    private String patronymic;
    private boolean activity;//true профиль активен, false нет

    public User(String id, String login, String password, String email, boolean activity) throws ServerException {
        if (login == null || login.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_LOGIN_EXCEPTION);
        }
        this.login = login;
        this.id = id;
        setPassword(password);
        setEmail(email);
        this.activity = activity;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
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

    public boolean isActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return activity == user.activity &&
                Objects.equals(id, user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(patronymic, user.patronymic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, email, lastName, firstName, patronymic, activity);
    }
}
