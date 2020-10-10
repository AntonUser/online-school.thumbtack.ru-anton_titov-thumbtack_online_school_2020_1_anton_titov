package net.thumbtack.school.hiring.dto.request;

public class RequirementDtoRequest extends SkillDtoRequest {
    private String nameDemand;
    private int skill;
    private boolean necessary;

    public RequirementDtoRequest(String name, int level, String nameDemand, int skill, boolean necessary) {
        super(name, level);
        this.nameDemand = nameDemand;
        this.skill = skill;
        this.necessary = necessary;
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
}
