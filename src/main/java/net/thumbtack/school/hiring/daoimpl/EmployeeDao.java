package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.Dao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employee;

import java.util.List;

public class EmployeeDao implements Dao<Employee, List<Employee>> {
    private DataBase dataBase;

    public EmployeeDao(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Employee getByLoginAndPassword(String login, String password) {
        return dataBase.getEmployeeByLoginAndPassword(login, password);
    }

    public Employee getById(String id) {
        return dataBase.getEmployeeById(id);
    }

    @Override
    public List<Employee> getAll() {
        return dataBase.getEmployeeList();
    }

    @Override
    public void save(Employee employee) throws ServerException {
        if (!employee.getAttainmentsList().isEmpty()) {
            //REVU: тут лучше не создавать dao, а использовать метод БД, который используется в saveSubList методе
            DemandSkillDao demandSkillDao = new DemandSkillDao(dataBase);//если умения уже есть, то добавим их в общий список
            demandSkillDao.saveSubList(employee.getNamesAttainments());
        }
        dataBase.addEmployee(employee);
    }

    @Override
    public void update(Employee oldEmployee, Employee newEmployee) {
        dataBase.updateEmployee(oldEmployee, newEmployee);
    }

    @Override
    public void delete(Employee employee) {
        dataBase.deleteEmployee(employee);
    }
}
