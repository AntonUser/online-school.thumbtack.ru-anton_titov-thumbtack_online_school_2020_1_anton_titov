package net.thumbtack.school.hiring.dto.responce;

public class DtoAttainmentsResponse {
    private String nameSkill;
    private int skill;

    public DtoAttainmentsResponse(String nameSkill, int skill) {
        this.nameSkill = nameSkill;
        this.skill = skill;
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
}
