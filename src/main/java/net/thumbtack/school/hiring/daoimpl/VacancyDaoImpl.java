package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.Dao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Requirement;
import net.thumbtack.school.hiring.model.Vacancy;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class VacancyDaoImpl implements Dao<Vacancy, List<Vacancy>> {
    private DataBase dataBase;

    public VacancyDaoImpl() {
        this.dataBase = DataBase.getInstance();
    }

/*
    public List<Vacancy> getVacanciesListByToken(String token) {
        return dataBase.getVacanciesListByToken(token);
    }

    public List<Vacancy> getActivityVacanciesListByToken(String token) {
        return dataBase.getActivityVacanciesListByToken(token);
    }

    public List<Vacancy> getNotActivityVacanciesListByToken(String token) {
        return dataBase.getNotActivityVacanciesListByToken(token);
    }*/

    public List<Vacancy> getVacanciesListNotLess(Map<String, Integer> skills) {
        return dataBase.getVacanciesListNotLess(skills);
    }

    public List<Vacancy> getVacanciesListObligatoryDemand(Map<String, Integer> skills) {
        return dataBase.getVacanciesListObligatoryDemand(skills);
    }

    public List<Vacancy> getVacanciesListOnlyName(Map<String, Integer> skills) {
        return dataBase.getVacanciesListOnlyName(skills);
    }

    public List<Vacancy> getVacanciesListWithOneDemand(Map<String, Integer> skills) {
        return dataBase.getVacanciesListWithOneDemand(skills);
    }

   /* public void updateDemandInVacancy(Requirement demand, String token, String nameVacancy, String oldNameDemand) throws ServerException {
        dataBase.updateDemandInVacancy(demand, token, nameVacancy, oldNameDemand);
    }*/

    public List<Vacancy> getAll() {
        return dataBase.getVacanciesList();
    }

    @Override
    public void save(Vacancy vacancy) {
        dataBase.addVacancy(vacancy);
    }

    @Override
//не знаю что с этим сделать только в этом классе имплементации не совсем подходят, в других Dao все как нужно
    public void update(String string, Vacancy vacancy) {
    }
/*
    public void update(String namePost, String tokenEmployer, Vacancy newVacancy) throws ServerException {
        dataBase.updateVacancy(namePost, tokenEmployer, newVacancy);
    }*/

    @Override//не знаю что с этим сделать
    public void delete(String string) {
    }
/*
    public void delete(String namePost, String tokenEmployer) throws ServerException {
        dataBase.deleteVacancy(tokenEmployer, namePost);
    }*/

    public Set<String> getAllDemandSkills() {
        return dataBase.getDemandSkillsSet();
    }
}