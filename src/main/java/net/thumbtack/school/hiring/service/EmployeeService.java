package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.daoimpl.DemandSkillDao;
import net.thumbtack.school.hiring.daoimpl.EmployeeDao;
import net.thumbtack.school.hiring.daoimpl.VacancyDao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.dto.request.*;
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
    private DataBase dataBase;
    private VacancyDao vacancyDao;
    private Gson gson;
    private EmployeeDao eDao;

    public EmployeeService(DataBase dataBase) {
        this.dataBase = dataBase;
        gson = new Gson();
        eDao = new EmployeeDao(dataBase);
        vacancyDao = new VacancyDao(dataBase);
    }

    public String registerEmployee(String json) {
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest;
        Employee employee;
        employeeDtoRegisterRequest = gson.fromJson(json, EmployeeDtoRegisterRequest.class);
        if (employeeDtoRegisterRequest.getFirstName().isEmpty() || employeeDtoRegisterRequest.getLastName().isEmpty() ||
                employeeDtoRegisterRequest.getPatronymic().isEmpty() || employeeDtoRegisterRequest.getEmail().isEmpty() ||
                employeeDtoRegisterRequest.getLogin().isEmpty() || employeeDtoRegisterRequest.getPassword().isEmpty()) {
            return gson.toJson(new ErrorToken("Одно из полей employee имеет ошибочное значение"));
        }
        try {
            employee = new Employee(UUID.randomUUID().toString(), employeeDtoRegisterRequest.getFirstName(),
                    employeeDtoRegisterRequest.getPatronymic(), employeeDtoRegisterRequest.getLastName(),
                    employeeDtoRegisterRequest.getLogin(), employeeDtoRegisterRequest.getPassword(),
                    employeeDtoRegisterRequest.getEmail(), employeeDtoRegisterRequest.isStatus(),
                    employeeDtoRegisterRequest.getAttainmentsList());
            eDao.save(employee);
        } catch (ServerException se) {
            return gson.toJson(new ErrorToken(se.getErrorCode().getErrorCode()));
        }
        return gson.toJson(new DtoRegisterResponse(employee.getId()));
    }

    public String loginEmployee(String json) {
        DtoLoginRequest dtoLoginRequest;
        Employee employee;
        dtoLoginRequest = gson.fromJson(json, DtoLoginRequest.class);
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
        DtoSkills skills = convertSkills(abilitiesJson);
        List<Vacancy> vacancies = vacancyDao.getAll();
        List<Vacancy> outVacancies = new ArrayList<>();
        if (validateSkills(skills)) {
            return gson.toJson(new ErrorToken("Список умений пуст или индентификатор пользователя null"));
        }
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
        return gson.toJson(outVacancies);
    }

    public String getVacanciesObligatoryDemand(String abilitiesJson) {
        DtoSkills skills = convertSkills(abilitiesJson);
        List<Vacancy> vacancies = vacancyDao.getAll();
        List<Vacancy> outVacancies = new ArrayList<>();
        if (validateSkills(skills)) {
            return gson.toJson(new ErrorToken("Список умений пуст или индентификатор пользователя null"));
        }
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
        return gson.toJson(outVacancies);
    }

    public String getVacancies(String abilitiesJson) {
        DtoSkills skills = convertSkills(abilitiesJson);
        List<Vacancy> vacancies = vacancyDao.getAll();
        List<Vacancy> outVacancies = new ArrayList<>();
        if (validateSkills(skills)) {
            return gson.toJson(new ErrorToken("Список умений пуст или индентификатор пользователя null"));
        }
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
        return gson.toJson(outVacancies);
    }

    public String getVacanciesWithOneDemand(String abilitiesJson) {
        DtoSkills skills = convertSkills(abilitiesJson);
        List<Vacancy> vacancies = vacancyDao.getAll();
        List<Vacancy> outVacancies = new ArrayList<>();
        if (validateSkills(skills)) {
            return gson.toJson(new ErrorToken("Список умений пуст или индентификатор пользователя null"));
        }
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
        return gson.toJson(outVacancies);
    }

    private DtoSkills convertSkills(String abilitiesJson) {
        return gson.fromJson(abilitiesJson, DtoSkills.class);
    }

    private boolean validateSkills(DtoSkills dtoSkills) {
        return dtoSkills.getSkills().isEmpty() || dtoSkills.getToken().isEmpty();
    }

    public String getAllDemandSkills() {
        vacancyDao = new VacancyDao(dataBase);
        return gson.toJson(new DtoAllDemandsSkillsResponse(vacancyDao.getAllDemandSkills()));
    }

    public String addEmployeeSkill(String skillJson) {
        DtoSkillResponse skill = gson.fromJson(skillJson, DtoSkillResponse.class);
        if (skill.getNameSkill().isEmpty() || skill.getSkill() < 1 || skill.getSkill() > 5 || skill.getToken().isEmpty()) {
            return gson.toJson(new ErrorToken("Одно из полей имеет ошибочное значение!"));
        }
        DemandSkillDao demandSkillDao = new DemandSkillDao(dataBase);
        demandSkillDao.save(skill.getNameSkill());//добавляю всегда но так как умения/требования хранятся в Set повторяющиеся будут удаляться
        Employee oldEmployee = eDao.getById(skill.getToken());
        Employee newEmployee = oldEmployee;
        newEmployee.addAttainments(new Attainments(skill.getNameSkill(), skill.getSkill()));
        eDao.update(oldEmployee, newEmployee);
        return gson.toJson(new ErrorToken());
    }

    public String updateEmployeeSkill(String oldSkillJson, String newSkillJson) {
        Employee employee;
        DtoSkillResponse oldSkill = gson.fromJson(oldSkillJson, DtoSkillResponse.class);
        if (validateSkill(oldSkill)) {
            return gson.toJson(new ErrorToken("Одно из полей oldSkill имеет неверное значение"));
        }
        Attainments oldAttainment = new Attainments(oldSkill.getNameSkill(), oldSkill.getSkill());
        DtoSkillResponse newSkill = gson.fromJson(newSkillJson, DtoSkillResponse.class);
        if (validateSkill(newSkill)) {
            return gson.toJson(new ErrorToken("Одно из полей newSkill имеет неверное значение"));
        }
        Attainments newAttainments = new Attainments(newSkill.getNameSkill(), newSkill.getSkill());
        employee = eDao.getById(oldSkill.getToken());
        employee.updateAttainments(oldAttainment, newAttainments);
        return gson.toJson(new ErrorToken());
    }

    public String removeEmployeeSkill(String skillJson) {
        Employee employee;
        DtoSkillResponse skill = gson.fromJson(skillJson, DtoSkillResponse.class);
        if (validateSkill(skill)) {
            return gson.toJson(new ErrorToken("Одно из полей skill имеет неверное значение"));
        }
        Attainments attainment = new Attainments(skill.getNameSkill(), skill.getSkill());
        employee = eDao.getById(skill.getToken());
        employee.removeAttainments(attainment);
        return gson.toJson(new ErrorToken());
    }

    private boolean validateSkill(DtoSkillResponse dtoSkill) {
        return dtoSkill.getNameSkill().isEmpty() || dtoSkill.getSkill() < 1 || dtoSkill.getSkill() > 5 || dtoSkill.getToken().isEmpty();
    }

    public String setStatus(String statusJson) {
        DtoStatusEmployeeRequest dtoStatusEmployeeRequest = gson.fromJson(statusJson, DtoStatusEmployeeRequest.class);
        if (dtoStatusEmployeeRequest.getToken().isEmpty()) {
            return gson.toJson(new ErrorToken("Невалидное значение токена"));
        }
        Employee employee = eDao.getById(dtoStatusEmployeeRequest.getToken());
        if (employee == null) {
            return gson.toJson(new ErrorToken("Пользователь с таким id не найден"));
        }
        Employee newEmployee = employee;
        newEmployee.setStatus(dtoStatusEmployeeRequest.isStatus());
        eDao.update(employee, newEmployee);
        return gson.toJson(new ErrorToken());
    }
}

