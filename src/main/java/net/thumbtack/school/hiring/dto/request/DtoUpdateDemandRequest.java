package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ErrorStrings;
import net.thumbtack.school.hiring.exception.ServerException;

public class DtoUpdateDemandRequest {
    private String oldNameDemand;
    private String token;
    private String nameVacancy;
    private String nameDemand;
    private int skill;
    private boolean necessary;

    public DtoUpdateDemandRequest(String oldName, String token, String nameVacancy, String nameDemand, int skill, boolean necessary) {
        this.oldNameDemand = oldName;
        this.token = token;
        this.nameVacancy = nameVacancy;
        this.nameDemand = nameDemand;
        this.skill = skill;
        this.necessary = necessary;
    }

    public String getOldNameDemand() {
        return oldNameDemand;
    }

    public void setOldNameDemand(String oldNameDemand) {
        this.oldNameDemand = oldNameDemand;
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

    public String getNameDemand() {
        return nameDemand;
    }

    public void setNameDemand(String nameDemand) {
        this.nameDemand = nameDemand;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public boolean isNecessary() {
        return necessary;
    }

    public void setNecessary(boolean necessary) {
        this.necessary = necessary;
    }

    public void validate() throws NullPointerException, ServerException {
        if (getNameVacancy() == null || getNameVacancy().isEmpty()) {
            throw new NullPointerException(ErrorStrings.NAME_VACANCY_ERROR.getStringMessage());
        } else if (getOldNameDemand() == null || getOldNameDemand().isEmpty()) {
            throw new NullPointerException(ErrorStrings.DEMAND_NAME_ERROR.getStringMessage());
        } else if (getNameDemand() == null || getNameDemand().isEmpty()) {
            throw new NullPointerException(ErrorStrings.DEMAND_NAME_ERROR.getStringMessage());
        } else if (getToken() == null || getToken().isEmpty()) {
            throw new NullPointerException(ErrorStrings.TOKEN_ERROR.getStringMessage());
        } else if (getSkill() < 1 || getSkill() > 5) {
            throw new ServerException(ErrorCode.SKILL_BORDER_EXCEPTION);
        }
    }

}
