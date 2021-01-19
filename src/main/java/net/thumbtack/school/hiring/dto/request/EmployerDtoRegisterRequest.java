package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Vacancy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployerDtoRegisterRequest extends DtoRegisterRequest {
    private String name;
    private String address;
    private List<DtoAddVacancyRequest> vacancies;

    public EmployerDtoRegisterRequest(String firstName, String lastName, String patronymic, String login, String password, String name, String address, String email) {
        super(firstName, lastName, patronymic, login, password, email);
        setName(name);
        setAddress(address);
        vacancies = new ArrayList<>();
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

    public List<DtoAddVacancyRequest> getVacancies() {
        return vacancies;
    }

    public DtoAddVacancyRequest getVacancyByName(String name) throws ServerException {
        for (DtoAddVacancyRequest dtoAddVacancyRequest : vacancies) {
            if (dtoAddVacancyRequest.getNamePost().equals(name)) {
                return dtoAddVacancyRequest;
            }
        }
        throw new ServerException(ErrorCode.NOT_FOUND_VACANCY_NAME);
    }

    public void setVacancies(List<DtoAddVacancyRequest> vacancies) {
        this.vacancies = vacancies;
    }

    public void addVacancy(DtoAddVacancyRequest vacancy) {
        vacancies.add(vacancy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EmployerDtoRegisterRequest that = (EmployerDtoRegisterRequest) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(vacancies, that.vacancies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, address, vacancies);
    }
}
