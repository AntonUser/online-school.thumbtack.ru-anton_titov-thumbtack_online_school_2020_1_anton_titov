package net.thumbtack.school.hiring.dto.response;

import java.util.List;

public class DtoVacanciesResponse {
    private List<DtoVacancyResponse> vacanciesList;

    public DtoVacanciesResponse(List<DtoVacancyResponse> vacanciesList) {
        this.vacanciesList = vacanciesList;
    }

    public List<DtoVacancyResponse> getVacanciesList() {
        return vacanciesList;
    }

    public void setVacanciesList(List<DtoVacancyResponse> vacanciesList) {
        this.vacanciesList = vacanciesList;
    }
}
