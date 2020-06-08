package net.thumbtack.school.hiring.dto.request;

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
