package net.thumbtack.school.hiring.dto.response;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoAttainmentsResponse that = (DtoAttainmentsResponse) o;
        return getSkill() == that.getSkill() && Objects.equals(getNameSkill(), that.getNameSkill());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNameSkill(), getSkill());
    }
}
