package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.Objects;

public class Employer extends User {
    //REVU: чем name отличается от firstName в User?
    private String name;
    private String address;
    private String email;

    public Employer(String name, String address, String email, String id, String firstName, String patronymic, String login, String password) throws ServerException {
        super(id, firstName, patronymic, login, password);
        setAddress(address);
        setEmail(email);
        setName(name);

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employer employer = (Employer) o;
        return Objects.equals(name, employer.name) &&
                Objects.equals(address, employer.address) &&
                Objects.equals(email, employer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, email);
    }
}
