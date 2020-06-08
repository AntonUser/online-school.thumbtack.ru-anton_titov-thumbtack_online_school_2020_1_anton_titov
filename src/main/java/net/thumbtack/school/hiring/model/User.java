package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.Objects;

public class User {
    private String id;
    private String login;
    private String password;
    private String email;

    public User(String id, String login, String password, String email) throws ServerException {
        if (login == null || login.isEmpty()) {
            throw new ServerException(ErrorCode.NULL_LOGIN_EXCEPTION);
        }
        this.login = login;
        this.id = id;
        setPassword(password);
        setEmail(email);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, email);
    }
}
