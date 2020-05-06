package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.Objects;

public class Employee extends User {
    private String lastName;
    private String firstName;
    private String patronymic;

    public Employee(String id, String firstName, String patronymic, String lastName, String login, String password, String email) throws ServerException {
        super(id, login, password, email);
        setLastName(lastName);
        setFirstName(firstName);
        setPatronymic(patronymic);
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(lastName, employee.lastName) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(patronymic, employee.patronymic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), lastName, firstName, patronymic);
    }
}
