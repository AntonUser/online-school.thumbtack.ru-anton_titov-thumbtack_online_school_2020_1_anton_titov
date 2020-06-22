package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Demand;

public class DtoUpdateDemandRequest extends Demand {
    private String token;
    private String nameVacancy;

    public DtoUpdateDemandRequest(String nameDemand, int skillLevel, boolean necessary, String token, String nameVacancy) throws ServerException {
        super(nameDemand, skillLevel, necessary);
        this.token = token;
        this.nameVacancy = nameVacancy;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNameVacancy() {
        return nameVacancy;
    }

    public void setNameVacancy(String nameVacancy) {
        this.nameVacancy = nameVacancy;
    }
}
