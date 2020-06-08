package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.exception.ServerException;

public interface Dao<T, K> {

       K getAll();

    void save(T object) throws ServerException;

    void update(T oldObject, T newObject);

    void delete(T object);
}
