package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.exception.ServerException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employer extends User {
    private String name;
    private String address;
    private List<Vacancy> vacancies;

    public Employer(String name, String address, String email, String firstName, String patronymic, String lastName, String login, String password) throws ServerException {
        super(login, password, email, lastName, firstName, patronymic);
        this.address = address;
        this.name = name;
        vacancies = new ArrayList<>();
    }

    public void addVacancy(Vacancy vacancy) {
        vacancies.add(vacancy);
    }

    public Vacancy getVacancyById(String id) {
        for (Vacancy vacancy : vacancies) {
            if (vacancy.getId().equals(id)) {
                return vacancy;
            }
        }
        return null;
    }

    public List<Vacancy> getAllVacancies() {
        return new ArrayList<>(vacancies);
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
        Employer employer = (Employer) o;
        return Objects.equals(name, employer.name) &&
                Objects.equals(address, employer.address) &&
                Objects.equals(vacancies, employer.vacancies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, address, vacancies);
    }
}
