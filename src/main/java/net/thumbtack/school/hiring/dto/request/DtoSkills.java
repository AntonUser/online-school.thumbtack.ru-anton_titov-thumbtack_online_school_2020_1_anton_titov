package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorStrings;
import net.thumbtack.school.hiring.model.Demand;

import java.util.List;

public class DtoSkills {
    private List<Demand> skills;
    private String token;

    public DtoSkills(List<Demand> skills, String token) {
        this.skills = skills;
        this.token = token;
    }

    public DtoSkills() {
    }

    public List<Demand> getSkills() {
        return skills;
    }

    public void setSkills(List<Demand> skills) {
        this.skills = skills;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void validate() throws NullPointerException{
        if (getSkills().isEmpty()) {
            throw new NullPointerException(ErrorStrings.SKILLS_LIST_ERROR.getStringMessage());
        } else if (getToken() == null || getToken().isEmpty()) {
            throw new NullPointerException(ErrorStrings.TOKEN_ERROR.getStringMessage());
        }
    }
}
