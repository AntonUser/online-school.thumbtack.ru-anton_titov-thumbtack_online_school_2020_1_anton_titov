package net.thumbtack.school.hiring.dto.request;

public class DtoRemoveSkillRequest {
    private String oldNameSkill;
    private String token;

    public DtoRemoveSkillRequest(String oldNameSkill, String token) {
        this.oldNameSkill = oldNameSkill;
        this.token = token;
    }

    public String getOldNameSkill() {
        return oldNameSkill;
    }

    public void setOldNameSkill(String oldNameSkill) {
        this.oldNameSkill = oldNameSkill;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
