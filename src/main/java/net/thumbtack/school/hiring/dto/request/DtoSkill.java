package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.Objects;

public class DtoSkill {
    private String name;
    private int level;

    public DtoSkill(String name, int level) {
        this.name = name;
        this.level = level;
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

    public void validate() throws ServerException {
        if (name == null || name.isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_NAME);
        }
        if (level < 1 || level > 5) {
            throw new ServerException(ErrorCode.SKILL_BORDER);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoSkill dtoSkill = (DtoSkill) o;
        return level == dtoSkill.level &&
                Objects.equals(name, dtoSkill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level);
    }
}
