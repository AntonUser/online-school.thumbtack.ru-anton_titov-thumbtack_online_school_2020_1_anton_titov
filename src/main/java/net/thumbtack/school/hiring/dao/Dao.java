package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.exception.ServerException;

public interface Dao<T> {

    void save(T object) throws ServerException;

    void update(String id, T newObject) throws ServerException;

    void delete(String id) throws ServerException;
}
