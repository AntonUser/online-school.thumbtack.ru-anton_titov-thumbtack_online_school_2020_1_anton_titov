package net.thumbtack.school.database.mybatis.dao;

import net.thumbtack.school.database.model.School;

import java.util.List;

public interface SchoolDao {

    School insert(School school);

    School getById(int id);

    List<School> getAllLazy();

    List<School> getAllUsingJoin();

    void update(School school);

    void delete(School school);

    void deleteAll();

    School insertSchoolTransactional(School school2018);
}