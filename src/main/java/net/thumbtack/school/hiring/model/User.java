package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.Objects;

//REVU: если набор полей ФИО разный, лучше не выделять общего, иначе появляются вопросы
// а вот email можно вынести как общее поле
// достаточно будет id, login, password, email полей
public class User {
    private String id;
    //REVU: странно, что есть firstName и patronymic, но нет lastName
    private String firstName;
    private String patronymic;
    private String login;
    private String password;

    public User(String id, String firstName, String patronymic, String login, String password) throws ServerException {
        setId(id);
        setFirstName(firstName);
        setPatronymic(patronymic);
        setLogin(login);
        setPassword(password);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(patronymic, user.patronymic) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, patronymic, login, password);
    }
}
