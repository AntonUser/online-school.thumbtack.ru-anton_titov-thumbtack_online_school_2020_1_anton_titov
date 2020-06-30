package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.Objects;

public class Employer extends User {
    private String name;
    private String address;
    private String patronymic;

    public Employer(String name, String address, String email, String id, String firstName, String patronymic, String lastName, String login, String password, boolean activity) throws ServerException {
        super(id, login, password, email, activity);
        setAddress(address);
        setName(name);
        setPatronymic(patronymic);
        setLastName(lastName);
        setFirstName(firstName);
        setPatronymic(patronymic);
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employer employer = (Employer) o;
        return Objects.equals(name, employer.name) &&
                Objects.equals(address, employer.address) &&
                Objects.equals(patronymic, employer.patronymic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, address, patronymic);
    }
}
