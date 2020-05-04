package net.thumbtack.school.hiring.dao;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employee;

import java.util.List;

//REVU: необходимо сдеать интерфейс EmployeeDao (или IEmployeeDao, чтоб имена были разные)
// в котором прописать все методы, а тут уже имплементировать интерфейс и реализовывать методы
public class EmployeeDao {

    //REVU: сделай DataBase полем данного класа EmployeeDao, чтобы не передалать БД во все методы параметром
    public String get(String idEmployeeJson, DataBase dataBase) {
        Gson gson = new Gson();
        //REVU: сделай в БД метод: public Employee getEmployeeById(String id)
        // и тут его вызывай, причем не делай из Employee json тут (это обязанность сервисов)
        // этот класс взаимодействует с базой данных и только
        List<Employee> list = getAll(dataBase);
        for (Employee employee : list) {
            if (idEmployeeJson.equals(employee.getId())) {
                return gson.toJson(employee);
            }
        }
        return null;
    }

    public List<Employee> getAll(DataBase dataBase) {
        //REVU: вот тот метод выглядит хорошо, он не делает ничего лишнего
            return dataBase.getEmployeeList();
    }

    public void save(String employeeJson, DataBase dataBase) throws ServerException {
        if (get(employeeJson, dataBase) != null) {
            throw new ServerException(ErrorCode.REPEATING_EMPLOYEE);
        }
        Gson gson = new Gson();
        List<Employee> list = getAll(dataBase);
        //REVU: переделывать json в модель - обязанность сервиса
        // кроме того, сначала нужно сделать из json объект DTO и только потом класс модели
        // этот метод должен принимать готового Employee и сохранять его в базу (вызывать метод save(Employee emp) из БД)
        list.add(gson.fromJson(employeeJson, Employee.class));
        dataBase.setEmployeeList(list);
    }

    //REVU: аналогично предыдущим методам - никаких json
    public void update(String oldEmployeeJson, String newEmployeeJson, DataBase dataBase) {
        Gson gson = new Gson();
        List<Employee> list = getAll(dataBase);
        list.set(list.indexOf(gson.fromJson(oldEmployeeJson, Employee.class)), gson.fromJson(newEmployeeJson, Employee.class));
        dataBase.setEmployeeList(list);
    }

    //REVU: аналогично предыдущим методам - никаких json
    public void delete(String employeeJson, DataBase dataBase) {
        Gson gson = new Gson();
        List<Employee> list = getAll(dataBase);
        list.remove(gson.fromJson(employeeJson, Employee.class));
        dataBase.setEmployeeList(list);
    }
}
