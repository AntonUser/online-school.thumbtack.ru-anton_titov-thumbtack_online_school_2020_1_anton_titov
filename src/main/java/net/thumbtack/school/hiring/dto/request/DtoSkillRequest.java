package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ErrorStrings;
import net.thumbtack.school.hiring.exception.ServerException;

public class DtoSkillRequest {
    private String oldNameSkill;
    private String token;
    private String nameSkill;
    private int skill;

    public DtoSkillRequest(String token, String nameSkill, int skill, String oldNameSkill) {
        this.token = token;
        this.nameSkill = nameSkill;
        this.skill = skill;
        this.oldNameSkill = oldNameSkill;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNameSkill() {
        return nameSkill;
    }

    public void setNameSkill(String nameSkill) {
        this.nameSkill = nameSkill;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public String getOldNameSkill() {
        return oldNameSkill;
    }

    public void setOldNameSkill(String oldNameSkill) {
        this.oldNameSkill = oldNameSkill;
    }

    public void validate() throws ServerException, NullPointerException {
        if (getNameSkill() == null || getNameSkill().isEmpty()) {
            throw new NullPointerException(ErrorStrings.NAME_SKILL_ERROR.getStringMessage());
        } else if (getSkill() < 1 || getSkill() > 5) {
            throw new ServerException(ErrorCode.SKILL_BORDER_EXCEPTION);
        } else if (getToken() == null || getToken().isEmpty()) {
            throw new NullPointerException(ErrorStrings.TOKEN_ERROR.getStringMessage());
        } else if (getOldNameSkill() == null || getOldNameSkill().isEmpty()) {
            throw new NullPointerException(ErrorStrings.NAME_SKILL_ERROR.getStringMessage());
        }
    }
}
