package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.dto.request.DtoAddVacancyRequest;

import java.util.ArrayList;

public class Vacancy extends DtoAddVacancyRequest {

    private boolean status;

    public Vacancy(String namePost, int salary, ArrayList<Demand> demands, String idEmployer) {
        super(namePost, salary, demands, idEmployer);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {

        this.status = status;
    }
}
