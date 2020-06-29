package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorStrings;

import java.util.Objects;

public class EmployerDtoRegisterRequest extends DtoRegisterRequest {
    private String name;
    private String address;

    public EmployerDtoRegisterRequest(String firstName, String lastName, String patronymic, String login, String password, String name, String address, String email) {
        super(firstName, lastName, patronymic, login, password, email);
        setName(name);
        setAddress(address);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void validate() throws NullPointerException {
        if (getFirstName() == null || getFirstName().isEmpty()) {
            throw new NullPointerException(ErrorStrings.FIRST_NAME_ERROR.getStringMessage());
        } else if (getLastName() == null || getLastName().isEmpty()) {
            throw new NullPointerException(ErrorStrings.LAST_NAME_ERROR.getStringMessage());
        } else if (getEmail() == null || getEmail().isEmpty()) {
            throw new NullPointerException(ErrorStrings.EMAIL_ERROR.getStringMessage());
        } else if (getLogin() == null || getLogin().isEmpty()) {
            throw new NullPointerException(ErrorStrings.LOGIN_ERROR.getStringMessage());
        } else if (getPassword() == null || getPassword().isEmpty()) {
            throw new NullPointerException(ErrorStrings.PASSWORD_ERROR.getStringMessage());
        } else if (getAddress() == null || getAddress().isEmpty()) {
            throw new NullPointerException(ErrorStrings.ADDRESS_ERROR.getStringMessage());
        } else if (getName() == null || getName().isEmpty()) {
            throw new NullPointerException(ErrorStrings.NAME_ERROR.getStringMessage());
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EmployerDtoRegisterRequest that = (EmployerDtoRegisterRequest) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, address);
    }

}
