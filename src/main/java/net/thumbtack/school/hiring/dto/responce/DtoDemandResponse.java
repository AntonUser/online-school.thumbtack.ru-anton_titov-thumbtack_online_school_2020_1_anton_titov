package net.thumbtack.school.hiring.dto.responce;

public class DtoDemandResponse {
    private String token;
    private String nameDemand;
    private int skill;
    private boolean necessary;

    public DtoDemandResponse(String token, String nameDemand, int skill, boolean necessary) {
        this.token = token;
        this.nameDemand = nameDemand;
        this.skill = skill;
        this.necessary = necessary;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
