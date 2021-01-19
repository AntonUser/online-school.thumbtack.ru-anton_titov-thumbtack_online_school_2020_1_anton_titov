package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

public class AddSkillRequest {
    private String name;
    private int level;
    private String token;

    public AddSkillRequest(String name, int level, String token) {
        this.name = name;
        this.level = level;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getToken() {
        return token;
    }

    public void validate() throws ServerException {
        if (name == null || name.isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_NAME);
        }
        if (level < 1 || level > 5) {
            throw new ServerException(ErrorCode.SKILL_BORDER);
        }
        if (token == null || token.isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_TOKEN);
        }
    }
}
