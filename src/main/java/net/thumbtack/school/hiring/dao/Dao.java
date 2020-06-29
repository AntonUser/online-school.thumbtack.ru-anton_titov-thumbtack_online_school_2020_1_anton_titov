package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.exception.ServerException;

public interface Dao<T, K> {
    //REVU: можно еще добавить метод getById
    //в одной из имплиментаций(DemandSkillDao) его не реализовать

    //REVU: попроавь отступ
    // почему нельзя вернуть List<T> ? чтоб не вводить переменную K

    //Потому что в одной из имплементаций содержится set
    K getAll();

    void save(T object) throws ServerException;

    void update(T oldObject, T newObject);

    void delete(T object);
}
