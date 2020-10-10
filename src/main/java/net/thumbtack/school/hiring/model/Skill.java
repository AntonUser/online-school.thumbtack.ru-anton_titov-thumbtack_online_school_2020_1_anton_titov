package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.Objects;

public class Skill implements Comparable {
    private String name;
    private int level;

    public Skill(String nameSkill, int skill) {
        this.name = nameSkill;
        this.level = skill;
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

    public void setLevel(int level) throws ServerException {
        if (level < 1 || level > 5) {
            throw new ServerException(ErrorCode.ERRONEOUS_SKILL_EXCEPTION);
        }
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return level == skill.level &&
                Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level);
    }

    @Override
    public int compareTo(Object o) {
        int i = this.getName().compareTo(((Skill) o).getName());
        if (i != 0) {
            return i;
        }
        return this.level - ((Skill) o).getLevel();
    }
}
