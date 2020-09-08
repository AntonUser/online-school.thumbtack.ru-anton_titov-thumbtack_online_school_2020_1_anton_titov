package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

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

    // REVU лучше этот код перенести в сервис, сделав там private static метод
    // сервису лучше знать, как правильно валидировать
    // может, в зависимости от чего-то нужно иногда так, а иногда иначе
    public void validate() throws ServerException {
        if (getFirstName() == null || getFirstName().isEmpty()) {
            throw new ServerException(ErrorCode.NULL_FIRST_NAME_EXCEPTION);
        } else if (getLastName() == null || getLastName().isEmpty()) {
            throw new ServerException(ErrorCode.NULL_LAST_NAME_EXCEPTION);
        } else if (getEmail() == null || getEmail().isEmpty()) {
            throw new ServerException(ErrorCode.EMAIL_EXCEPTION);
        } else if (getLogin() == null || getLogin().isEmpty()) {
            throw new ServerException(ErrorCode.NULL_LOGIN_EXCEPTION);
        } else if (getPassword() == null || getPassword().isEmpty()) {
            throw new ServerException(ErrorCode.NULL_PASSWORD_EXCEPTION);
        } else if (getAddress() == null || getAddress().isEmpty()) {
            throw new ServerException(ErrorCode.NULL_ADDRESS_EXCEPTION);
        } else if (getName() == null || getName().isEmpty()) {
            throw new ServerException(ErrorCode.NULL_NAME_EXCEPTION);
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
