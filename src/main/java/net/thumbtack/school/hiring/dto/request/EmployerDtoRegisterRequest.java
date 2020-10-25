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
            throw new ServerException(ErrorCode.EMPTY_FIRST_NAME);
        } else if (getLastName() == null || getLastName().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_LAST_NAME);
        } else if (getEmail() == null || getEmail().isEmpty()) {
            throw new ServerException(ErrorCode.EMAIL_EXCEPTION);
        } else if (getLogin() == null || getLogin().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_LOGIN);
        } else if (getPassword() == null || getPassword().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_PASSWORD);
        } else if (getAddress() == null || getAddress().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_ADDRESS);
        } else if (getName() == null || getName().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_NAME);
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
