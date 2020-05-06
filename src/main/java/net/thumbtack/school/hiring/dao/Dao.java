package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.List;

public interface Dao<T> {
    abstract public T get(String id);

    abstract public List<T> getAll();

    abstract public void save(T object) throws ServerException;

    abstract public void update(T oldObject, T newObject);

    abstract public void delete(T object);
}
