package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorStrings;

import java.util.Objects;

public class DtoLoginRequest {
    private String login;
    private String password;

    public DtoLoginRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void validate() throws NullPointerException {
        if (getPassword() == null || getPassword().isEmpty()) {
            throw new NullPointerException(ErrorStrings.PASSWORD_ERROR.getStringMessage());
        } else if (getLogin() == null || getLogin().isEmpty()) {
            throw new NullPointerException(ErrorStrings.LOGIN_ERROR.getStringMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoLoginRequest that = (DtoLoginRequest) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
