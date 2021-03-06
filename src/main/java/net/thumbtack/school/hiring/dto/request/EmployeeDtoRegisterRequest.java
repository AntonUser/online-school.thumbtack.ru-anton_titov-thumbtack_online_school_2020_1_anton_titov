package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Skill;

import java.util.List;
import java.util.Objects;

public class EmployeeDtoRegisterRequest {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String login;
    private String password;
    private String email;
    // REVU можно, конечно, и так - при регистрации сразу указать список умений
    // можно и иначе - при регистрации его не указывать, а потом сделать метод,который добавляет
    // это просто к сведению, ничего делать в этом плане не надо
    // а вот использование класса модели в DTO - плохо
    // лучше, если DTO ничего не будет знать про модель
    // так что стоит завести класс SkillDtoRequest и тут его использовать
    // проверьте и в других DTO
    private List<DtoSkill> attainmentsList;

    public EmployeeDtoRegisterRequest(String firstName, String lastName, String patronymic, String login, String password, String email, List<DtoSkill> attainmentsList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.login = login;
        this.password = password;
        this.email = email;
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

    public List<DtoSkill> getAttainmentsList() {
        return attainmentsList;
    }

    public void setAttainmentsList(List<DtoSkill> attainmentsList) {
        this.attainmentsList = attainmentsList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDtoRegisterRequest that = (EmployeeDtoRegisterRequest) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(patronymic, that.patronymic) &&
                Objects.equals(login, that.login) &&
                Objects.equals(password, that.password) &&
                Objects.equals(email, that.email) &&
                Objects.equals(attainmentsList, that.attainmentsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, patronymic, login, password, email, attainmentsList);
    }
}
