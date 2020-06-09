package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.daoimpl.DemandSkillDao;
import net.thumbtack.school.hiring.daoimpl.EmployeeDao;
import net.thumbtack.school.hiring.daoimpl.VacancyDao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.dto.request.DtoLoginRequest;
import net.thumbtack.school.hiring.dto.request.DtoSkillResponse;
import net.thumbtack.school.hiring.dto.request.DtoSkills;
import net.thumbtack.school.hiring.dto.request.EmployeeDtoRegisterRequest;
import net.thumbtack.school.hiring.dto.responce.DtoAllDemandsSkillsResponse;
import net.thumbtack.school.hiring.dto.responce.DtoLoginResponse;
import net.thumbtack.school.hiring.dto.responce.DtoRegisterResponse;
import net.thumbtack.school.hiring.dto.responce.ErrorToken;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EmployeeService {
    //REVU: все поля должны быть private
    private DataBase dataBase;
    VacancyDao vacancyDao;
    //REVU: а вот dto и списки для ответа сервера должны быть локальными переменными методов
    DtoSkills skills;
    List<Vacancy> vacancies;
    List<Vacancy> outVacancies;

    public EmployeeService(DataBase dataBase) {
        //REVU: проинициализируй тут еще и все поля класса через new
        this.dataBase = dataBase;
    }

    public String registerEmployee(String json) {
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest;
        Employee employee;
        //REVU: gson лучше вынести в поле класса
        Gson gson = new Gson();
        //REVU: зачем еще раз new Gson()?
        employeeDtoRegisterRequest = new Gson().fromJson(json, EmployeeDtoRegisterRequest.class);
        try {
            //REVU: а будет валидация полей перед созданием employee? сейчас не вижу.
            employee = new Employee(UUID.randomUUID().toString(), employeeDtoRegisterRequest.getFirstName(),
                    employeeDtoRegisterRequest.getPatronymic(), employeeDtoRegisterRequest.getLastName(),
                    employeeDtoRegisterRequest.getLogin(), employeeDtoRegisterRequest.getPassword(),
                    employeeDtoRegisterRequest.getEmail(), employeeDtoRegisterRequest.isStatus(),
                    employeeDtoRegisterRequest.getAttainmentsList());
        } catch (ServerException se) {
            return gson.toJson(new ErrorToken("Одно из полей employee - null"));
        }
        //REVU: лучше сделать 1 try-catch блок, все равно ловишь одно и то же ServerException
        // также вместо строк используй errorCode из ServerException
        // то есть new ErrorToken(se.getErrorCode().getErrorCode())

        //REVU: dao должно быть полем класса, а не локальной переменной метода
        EmployeeDao eDao = new EmployeeDao(dataBase);
        try {
            eDao.save(employee);
        } catch (ServerException se) {
            return gson.toJson(new ErrorToken("Повторяющийся employee"));
        }

        return new Gson().toJson(new DtoRegisterResponse(employee.getId()));
    }

    public String loginEmployee(String json) {
        DtoLoginRequest dtoLoginRequest;
        Employee employee;
        Gson gson = new Gson();
        EmployeeDao eDao = new EmployeeDao(dataBase);
        dtoLoginRequest = new Gson().fromJson(json, DtoLoginRequest.class);
        if (dtoLoginRequest.getLogin() == null || dtoLoginRequest.getPassword() == null) {
            return gson.toJson(new ErrorToken("логин или пароль пустой"));
        }
        employee = eDao.getByLoginAndPassword(dtoLoginRequest.getLogin(), dtoLoginRequest.getPassword());
        if (employee == null) {
            return gson.toJson(new ErrorToken("не верный логин или пароль"));
        }
        return gson.toJson(new DtoLoginResponse(employee.getId()));
    }

    public String getVacanciesNotLess(String abilitiesJson) {
        init(abilitiesJson);
        int i = 0;
        for (Vacancy vacancy : vacancies) {//сортирую в новый список по совпадениям полей
            for (Demand demand : vacancy.getDemands()) {
                for (Demand demandSkill : skills.getSkills()) {
                    if (demandSkill.getNameDemand().equals(demand.getNameDemand()) && demandSkill.getSkill() >= demand.getSkill()) {
                        i++;
                    }
                }
            }
            if (i == vacancy.getDemands().size()) {
                outVacancies.add(vacancy);
            }
        }
        return new Gson().toJson(outVacancies);
    }

    public String getVacanciesObligatoryDemand(String abilitiesJson) {
        init(abilitiesJson);
        int i = 0, count = 0;
        for (Vacancy vacancy : vacancies) {//сортирую в новый список по совпадениям полей
            count = 0;
            for (Demand demand : vacancy.getDemands()) {
                for (Demand demandSkill : skills.getSkills()) {
                    if (demandSkill.getNameDemand().equals(demand.getNameDemand()) &&
                            demandSkill.getSkill() >= demand.getSkill() &&
                            demandSkill.isNecessary()) {
                        i++;
                    }
                    if (demandSkill.isNecessary()) {
                        count++;
                    }
                }
            }
            if (i == count) {
                outVacancies.add(vacancy);
            }
        }
        return new Gson().toJson(outVacancies);
    }

    public String getVacancies(String abilitiesJson) {
        init(abilitiesJson);
        int i = 0;
        for (Vacancy vacancy : vacancies) {//сортирую в новый список по совпадениям полей
            for (Demand demand : vacancy.getDemands()) {
                for (Demand demandSkill : skills.getSkills()) {
                    if (demandSkill.getNameDemand().equals(demand.getNameDemand())) {
                        i++;
                    }
                }
            }
            if (i == vacancy.getDemands().size()) {
                outVacancies.add(vacancy);
            }
        }
        return new Gson().toJson(outVacancies);
    }

    public String getVacanciesWithOneDemand(String abilitiesJson) {
        init(abilitiesJson);
        int i = 0;
        for (Vacancy vacancy : vacancies) {//сортирую в новый список по совпадениям полей
            for (Demand demand : vacancy.getDemands()) {
                for (Demand demandSkill : skills.getSkills()) {
                    if (demandSkill.getNameDemand().equals(demand.getNameDemand()) && demandSkill.getSkill() >= demand.getSkill()) {
                        i++;
                    }
                }
            }
            if (i != 0) {
                outVacancies.add(vacancy);
            }
        }
        return new Gson().toJson(outVacancies);
    }

    //REVU: все поля должны инициализироваться в конструкторе
    private void init(String abilitiesJson) {
        vacancyDao = new VacancyDao(dataBase);
        //REVU: а вспомогательные списки и dto - в методах, где они используются
        // можно вынести отдельный метод при необходимости, но пусть он возвращает значение в локальную переменную
//        private DtoSkills convertSkills(String abilitiesJson) {
//            return gson.fromJson(abilitiesJson, DtoSkills.class);
//        }
        skills = new Gson().fromJson(abilitiesJson, DtoSkills.class);
        vacancies = vacancyDao.getAll();
        outVacancies = new ArrayList<>();
    }

    public String getAllDemandSkills() {
        vacancyDao = new VacancyDao(dataBase);
        return new Gson().toJson(new DtoAllDemandsSkillsResponse(vacancyDao.getAllDemandSkills()));
    }

    public String addEmployeeSkill(String skillJson) {
        DtoSkillResponse skill = new Gson().fromJson(skillJson, DtoSkillResponse.class);
        if (skill.getNameSkill().isEmpty() || skill.getSkill() < 1 || skill.getSkill() > 5 || skill.getToken().isEmpty()) {
            return new Gson().toJson(new ErrorToken("Одно из полей имеет ошибочное значение!"));
        }
        DemandSkillDao demandSkillDao = new DemandSkillDao(dataBase);

        try {
            demandSkillDao.save(skill.getNameSkill());//добавляю всегда но так как умения/требования хранятся в Set повторяющиеся будут удаляться
        } catch (ServerException e) {
            return new Gson().toJson(new ErrorToken("Поле названия умения имеет ошибку!"));
        }

        EmployeeDao employeeDao = new EmployeeDao(dataBase);
        Employee oldEmployee = employeeDao.getById(skill.getToken());
        Employee newEmployee = oldEmployee;
        newEmployee.addAttainments(new Attainments(skill.getNameSkill(), skill.getSkill()));
        employeeDao.update(oldEmployee, newEmployee);
        return new Gson().toJson(new ErrorToken());
    }

}

