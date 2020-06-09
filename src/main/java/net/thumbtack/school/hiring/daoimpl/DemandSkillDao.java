package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.Dao;
import net.thumbtack.school.hiring.database.DataBase;

import java.util.Set;

public class DemandSkillDao implements Dao<String, Set<String>> {
    private DataBase dataBase;

    public DemandSkillDao(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Set<String> getAll() {
        return dataBase.getDemandSkillsSet();
    }

    @Override
    public void save(String name) {
        dataBase.addDemandSkill(name);
    }

    @Override
    public void update(String oldObject, String newObject) {
        dataBase.updateDemandSkill(oldObject, newObject);
    }

    @Override
    public void delete(String name) {
        dataBase.deleteDemandSkill(name);
    }

    public void saveSubList(Set<String> subSet) {
        dataBase.addSubSet(subSet);

    }

}
