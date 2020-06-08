package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.model.Attainments;

public class DtoSkillResponse extends Attainments {
    private String token;
    public DtoSkillResponse(String nameSkill, int skill, String token) {
        super(nameSkill, skill);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
