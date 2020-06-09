package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.Set;

//REVU: почему не имплементируется интерфейс Dao?
public class DemandSkillDao {
    private DataBase dataBase;

    public DemandSkillDao(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Set<String> getAll() {
        return dataBase.getDemandSkillsSet();
    }

    public void save(String name) throws ServerException {
        dataBase.addDemandSkill(name);
    }

    public void saveSubList(Set<String>subSet) {
        //REVU: пустое тело метода
    }

}
