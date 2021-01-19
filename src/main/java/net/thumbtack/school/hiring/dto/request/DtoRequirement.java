package net.thumbtack.school.hiring.dto.request;

import java.util.Objects;

public class DtoRequirement extends DtoSkill {
    String necessary;

    public DtoRequirement(String name, int level, String necessary) {
        super(name, level);
        this.necessary = necessary;
    }

    public String getNecessary() {
        return necessary;
    }

    public void setNecessary(String necessary) {
        this.necessary = necessary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DtoRequirement that = (DtoRequirement) o;
        return Objects.equals(getNecessary(), that.getNecessary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNecessary());
    }
}
