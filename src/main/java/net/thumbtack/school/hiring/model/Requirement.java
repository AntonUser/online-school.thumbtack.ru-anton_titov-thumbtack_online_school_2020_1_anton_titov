package net.thumbtack.school.hiring.model;

import java.util.Objects;

public class Requirement extends Skill implements Comparable {

	// REVU см. REVU в классе Employee
	// Comparable is a raw type. References to generic type Comparable<T> should be parameterized
    private ConditionsRequirements necessary;

    public Requirement(String nameDemand, int skillLevel, ConditionsRequirements necessary) {
        super(nameDemand, skillLevel);
        this.necessary = necessary;
    }

    public Requirement(Skill skill, ConditionsRequirements necessary) {
        super(skill.getName(), skill.getLevel());
        this.necessary = necessary;
    }

    public ConditionsRequirements getNecessary() {
        return necessary;
    }

    public void setNecessary(ConditionsRequirements necessary) {
        this.necessary = necessary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Requirement that = (Requirement) o;
        return necessary == that.necessary;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), necessary);
    }

    @Override
    public int compareTo(Object o) {
        int i = super.compareTo(o);
        if (i == 0) {
            if (this.necessary.equals(ConditionsRequirements.NECESSARY) && ((Requirement) o).getNecessary().equals(ConditionsRequirements.NOT_NECESSARY)) {
                return 1;
            } else if (this.necessary.equals(ConditionsRequirements.NOT_NECESSARY) && ((Requirement) o).getNecessary().equals(ConditionsRequirements.NECESSARY)) {
                return -1;
            }
            return 0;
        }
        return i;
    }
}

