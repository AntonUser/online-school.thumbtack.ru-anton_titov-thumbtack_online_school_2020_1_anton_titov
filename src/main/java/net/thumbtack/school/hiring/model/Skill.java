package net.thumbtack.school.hiring.model;

public class Skill {
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

    public void setLevel(int level) {
        this.level = level;
    }
}
