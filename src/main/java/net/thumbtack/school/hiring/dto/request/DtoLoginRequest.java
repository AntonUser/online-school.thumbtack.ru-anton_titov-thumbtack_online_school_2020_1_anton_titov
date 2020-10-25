package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

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

    public void validate() throws ServerException {
        if (getPassword() == null || getPassword().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_PASSWORD);
        } else if (getLogin() == null || getLogin().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_LOGIN);
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
