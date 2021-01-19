package net.thumbtack.school.hiring.dto.request;

import java.util.Set;

public class DtoSkills {
    private Set<DtoSkill> skills;

    public DtoSkills(Set<DtoSkill> skills) {
        this.skills = skills;
    }

    public DtoSkills() {
    }

    public Set<DtoSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<DtoSkill> skills) {
        this.skills = skills;
    }

}
