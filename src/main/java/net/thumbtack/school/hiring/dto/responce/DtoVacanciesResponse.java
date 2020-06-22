package net.thumbtack.school.hiring.dto.responce;

import net.thumbtack.school.hiring.model.Vacancy;

import java.util.List;

public class DtoVacanciesResponse {
    private List<Vacancy> vacanciesList;

    public DtoVacanciesResponse(List<Vacancy> vacanciesList) {
        this.vacanciesList = vacanciesList;
    }

    public List<Vacancy> getVacanciesList() {
        return vacanciesList;
    }

    public void setVacanciesList(List<Vacancy> vacanciesList) {
        this.vacanciesList = vacanciesList;
    }
}
