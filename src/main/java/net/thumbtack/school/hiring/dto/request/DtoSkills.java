package net.thumbtack.school.hiring.dto.request;

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
}
