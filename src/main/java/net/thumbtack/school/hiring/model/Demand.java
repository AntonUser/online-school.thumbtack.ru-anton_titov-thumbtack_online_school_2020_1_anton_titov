package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.Objects;

// REVU Requirement лучше
// REVU а не сделать ли его наследником от Skill ?  
public class Demand {
	// REVU просто name. И так ясно, чего
    private String nameDemand;
    private int skill;
    private boolean necessary;

    public Demand(String nameDemand, int skillLevel, boolean necessary) {
        this.nameDemand = nameDemand;
        this.skill = skillLevel;
        setNecessary(necessary);
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

    public void setSkill(int skill) throws ServerException {
        if (skill < 1 || skill > 5) {
            throw new ServerException(ErrorCode.ERRONEOUS_SKILL_EXCEPTION);
        }
        this.skill = skill;
    }

    public boolean isNecessary() {
        return necessary;
    }

    public void setNecessary(boolean necessary) {
        this.necessary = necessary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Demand demand = (Demand) o;
        return skill == demand.skill &&
                necessary == demand.necessary &&
                Objects.equals(nameDemand, demand.nameDemand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameDemand, skill, necessary);
    }
}
