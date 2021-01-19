package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

public class DtoSkillRequest extends DtoRemoveSkillRequest {

    private String nameSkill;
    private int level;

    public DtoSkillRequest(String token, String nameSkill, int level, String oldNameSkill) {
        super(oldNameSkill, token);
        this.nameSkill = nameSkill;
        this.level = level;
    }

    public String getNameSkill() {
        return nameSkill;
    }

    public void setNameSkill(String nameSkill) {
        this.nameSkill = nameSkill;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void validate() throws ServerException {
        if (getNameSkill() == null || getNameSkill().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_NAME_SKILL);
        } else if (getLevel() < 1 || getLevel() > 5) {
            throw new ServerException(ErrorCode.SKILL_BORDER);
        } else if (getToken() == null || getToken().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_TOKEN);
        } else if (getOldNameSkill() == null || getOldNameSkill().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_NAME_SKILL);
        }
    }
}
