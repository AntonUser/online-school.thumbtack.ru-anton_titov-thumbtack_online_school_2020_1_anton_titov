package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorStrings;
import net.thumbtack.school.hiring.model.Attainments;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeDtoRegisterRequest {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String login;
    private String password;
    private String email;
    private boolean status;
    private List<Attainments> attainmentsList;

    public EmployeeDtoRegisterRequest(String firstName, String lastName, String patronymic, String login, String password, String email, boolean status, List<Attainments> attainmentsList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.login = login;
        this.password = password;
        this.email = email;
        this.status = status;
        this.attainmentsList = attainmentsList;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Attainments> getAttainmentsList() {
        return attainmentsList;
    }

    public void setAttainmentsList(List<Attainments> attainmentsList) {
        this.attainmentsList = attainmentsList;
    }

    public void validate() throws NullPointerException {
        if (getFirstName().isEmpty()) {
            throw new NullPointerException(ErrorStrings.FIRST_NAME_ERROR.getStringMessage());
        } else if (getLastName() == null || getLastName().isEmpty()) {
            throw new NullPointerException(ErrorStrings.LAST_NAME_ERROR.getStringMessage());
        } else if (getEmail() == null || getEmail().isEmpty()) {
            throw new NullPointerException(ErrorStrings.EMAIL_ERROR.getStringMessage());
        } else if (getLogin() == null || getLogin().isEmpty()) {
            throw new NullPointerException(ErrorStrings.LOGIN_ERROR.getStringMessage());
        } else if (getPassword() == null || getPassword().isEmpty()) {
            throw new NullPointerException(ErrorStrings.PASSWORD_ERROR.getStringMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EmployeeDtoRegisterRequest that = (EmployeeDtoRegisterRequest) o;
        return status == that.status &&
                Objects.equals(attainmentsList, that.attainmentsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status, attainmentsList);
    }
}
