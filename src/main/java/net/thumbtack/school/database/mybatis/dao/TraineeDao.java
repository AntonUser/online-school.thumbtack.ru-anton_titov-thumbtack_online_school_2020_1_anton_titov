package net.thumbtack.school.database.mybatis.dao;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.Trainee;

import java.util.List;

public interface TraineeDao {

    Trainee insert(Group group, Trainee trainee);

    Trainee getById(int id);

    List<Trainee> getAll();

    Trainee update(Trainee trainee);

    List<Trainee> getAllWithParams(String firstName, String lastName, Integer rating);

    void batchInsert(List<Trainee> trainees);

    void delete(Trainee trainee);

    void deleteAll();
}
