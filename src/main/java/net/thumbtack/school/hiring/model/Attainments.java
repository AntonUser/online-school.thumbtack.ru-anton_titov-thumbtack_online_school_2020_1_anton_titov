package net.thumbtack.school.hiring.model;

import java.util.List;

// REVU почему множественное число ? Название вводит в заблуждение
// я сначала увидел в Employee private List<Attainments> attainmentsList;
// и у меня появились мысли о списке списков
// кстати, Skill лучше
public class Attainments {
	// REVU просто name. И так ясно, чего
    private String nameSkill;
    // REVU лучше level
    private int skill;

    public Attainments(String nameSkill, int skill) {
        this.nameSkill = nameSkill;
        this.skill = skill;
    }

    public String getNameSkill() {
        return nameSkill;
    }

    public void setNameSkill(String nameSkill) {
        this.nameSkill = nameSkill;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }
}
