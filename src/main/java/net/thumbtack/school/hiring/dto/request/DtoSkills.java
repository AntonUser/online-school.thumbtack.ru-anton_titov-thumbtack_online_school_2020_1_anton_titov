package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.Map;

public class DtoSkills {
    private Map<String, Integer> skills;
    private String token;

    public DtoSkills(Map<String, Integer> skills, String token) {
        this.skills = skills;
        this.token = token;
    }

    public DtoSkills() {
    }

    public Map<String, Integer> getSkills() {
        return skills;
    }

    public void setSkills(Map<String, Integer> skills) {
        this.skills = skills;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void validate() throws ServerException {
        if (getSkills().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_SKILLS_LIST);
        } else if (getToken() == null || getToken().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_TOKEN);
        }
    }
}
