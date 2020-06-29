package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.Dao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.model.Demand;
import net.thumbtack.school.hiring.model.Vacancy;

import java.util.List;
import java.util.Set;

public class VacancyDao implements Dao<Vacancy, List<Vacancy>> {
    private DataBase dataBase;

    public VacancyDao() {
        this.dataBase = DataBase.getInstance();
    }

    public Vacancy getVacancyByTokenAndName(String token, String namePost) {
        return dataBase.getVacancyByTokenAndName(token, namePost);
    }

    public List<Vacancy> getVacanciesListByToken(String token) {
        return dataBase.getVacanciesListByToken(token);
    }

    public List<Vacancy> getActivityVacanciesListByToken(String token) {
        return dataBase.getActivityVacanciesListByToken(token);
    }

    public List<Vacancy> getNotActivityVacanciesListByToken(String token) {
        return dataBase.getNotActivityVacanciesListByToken(token);
    }

    public List<Vacancy> getVacanciesListNotLess(List<Demand> skills) {
        return dataBase.getVacanciesListNotLess(skills);
    }

    public List<Vacancy> getVacanciesListObligatoryDemand(List<Demand> skills) {
        return dataBase.getVacanciesListObligatoryDemand(skills);
    }

    public List<Vacancy> getVacanciesListOnlyName(List<Demand> skills) {
        return dataBase.getVacanciesListOnlyName(skills);
    }

    public List<Vacancy> getVacanciesListWithOneDemand(List<Demand> skills) {
        return dataBase.getVacanciesListWithOneDemand(skills);
    }

    public void removeAllVacanciesByToken(String token) {
        dataBase.removeAllVacanciesByToken(token);
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