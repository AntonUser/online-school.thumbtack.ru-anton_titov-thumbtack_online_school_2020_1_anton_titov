package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.Dao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.model.Vacancy;

import java.util.List;
import java.util.Set;

public class VacancyDao implements Dao<Vacancy, List<Vacancy>> {
    private DataBase dataBase;

    public VacancyDao(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Vacancy getVacancyByTokenAndName(String token, String namePost) {
        return dataBase.getVacancyByTokenAndName(token, namePost);
    }

    @Override
    public List<Vacancy> getAll() {
        return dataBase.getVacanciesList();
    }

    @Override
    public void save(Vacancy vacancy) {
        dataBase.addVacancy(vacancy);
    }

    @Override
    public void update(Vacancy oldObject, Vacancy newObject) {
        dataBase.updateVacancy(oldObject, newObject);
    }

    @Override
    public void delete(Vacancy object) {
        dataBase.deleteVacancy(object);
    }

    public Set<String> getAllDemandSkills() {
        return dataBase.getDemandSkillsSet();
    }
}