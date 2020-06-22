package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.dto.request.DtoAddVacancyRequest;

import java.util.ArrayList;
import java.util.List;

//REVU: модель не должна зависеть от dto
// даже если поля одни и те же, они должны дублироваться в dto и модели
public class Vacancy extends DtoAddVacancyRequest {

    private boolean status;

    public Vacancy(String namePost, int salary, ArrayList<Demand> demands, String idEmployer) {
        super(namePost, salary, demands, idEmployer);
        this.status = true;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void updateDemand(Demand oldDemand, Demand newDemand) {
        List<Demand> allDemands =getDemands();
        allDemands.set(allDemands.indexOf(oldDemand),newDemand);
        setDemands(allDemands);
    }
}
