package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Demand;

import java.util.ArrayList;

public class DtoRemoveVacancyRequest extends DtoAddVacancyRequest {
    boolean status;

    public DtoRemoveVacancyRequest(String namePost, int salary, ArrayList<Demand> demands, String token, boolean status) {
        super(namePost, salary, demands, token);
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void validate() throws NullPointerException, ServerException {
        super.validate();
    }
}
