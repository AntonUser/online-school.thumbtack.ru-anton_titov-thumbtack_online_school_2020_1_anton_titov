package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.daoimpl.DemandSkillDao;
import net.thumbtack.school.hiring.daoimpl.EmployeeDao;
import net.thumbtack.school.hiring.daoimpl.VacancyDao;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.responce.*;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ErrorStrings;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Attainments;
import net.thumbtack.school.hiring.model.Employee;

import java.util.UUID;

//+перенос логики в бд
//+правильное расположение
//+список ошибок
//+валидация в dto
//+экземпляры dao
public class EmployeeService {
    private VacancyDao vacancyDao;
    private Gson gson;
    private EmployeeDao eDao;
    DemandSkillDao demandSkillDao;

    public EmployeeService() {
        gson = new Gson();
        eDao = new EmployeeDao();
        vacancyDao = new VacancyDao();
        demandSkillDao = new DemandSkillDao();
    }

    public String registerEmployee(String json) {
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest;
        Employee employee;
        employeeDtoRegisterRequest = gson.fromJson(json, EmployeeDtoRegisterRequest.class);
        try {
            employeeDtoRegisterRequest.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        try {
            employee = new Employee(UUID.randomUUID().toString(), employeeDtoRegisterRequest.getFirstName(),
                    employeeDtoRegisterRequest.getPatronymic(), employeeDtoRegisterRequest.getLastName(),
                    employeeDtoRegisterRequest.getLogin(), employeeDtoRegisterRequest.getPassword(),
                    employeeDtoRegisterRequest.getEmail(), employeeDtoRegisterRequest.isStatus(),
                    employeeDtoRegisterRequest.getAttainmentsList(), true);
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
        try {
            dtoLoginRequest.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        employee = eDao.getByLoginAndPassword(dtoLoginRequest.getLogin(), dtoLoginRequest.getPassword());
        if (employee == null) {
            return gson.toJson(new ErrorToken(ErrorStrings.AUTHORISATION_ERROR.getStringMessage()));
        }
        eDao.setAccountStatus(employee.getId(), true);
        return gson.toJson(new DtoLoginResponse(employee.getId()));
    }

    public String getVacanciesNotLess(String abilitiesJson) throws ServerException {
        DtoSkills skills = convertSkills(abilitiesJson);
        try {
            skills.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(skills.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDao.getVacanciesListNotLess(skills.getSkills())));
    }

    public String getVacanciesObligatoryDemand(String abilitiesJson) throws ServerException {
        DtoSkills skills = convertSkills(abilitiesJson);
        try {
            skills.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(skills.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDao.getVacanciesListObligatoryDemand(skills.getSkills())));
    }

    public String getVacancies(String abilitiesJson) throws ServerException {
        DtoSkills skills = convertSkills(abilitiesJson);
        try {
            skills.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(skills.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDao.getVacanciesListOnlyName(skills.getSkills())));
    }

    public String getVacanciesWithOneDemand(String abilitiesJson) throws ServerException {
        DtoSkills skills = convertSkills(abilitiesJson);
        try {
            skills.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(skills.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDao.getVacanciesListWithOneDemand(skills.getSkills())));
    }

    public String getAllDemandSkills(String tokenJson) throws ServerException {
        DtoToken token = gson.fromJson(tokenJson, DtoToken.class);
        try {
            token.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(token.getToken());
        return gson.toJson(new DtoAllDemandsSkillsResponse(vacancyDao.getAllDemandSkills()));
    }

    public String addEmployeeSkill(String skillJson) throws ServerException {
        DtoSkillRequest skill = gson.fromJson(skillJson, DtoSkillRequest.class);
        try {
            skill.validate();
        } catch (ServerException | NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(skill.getToken());
        demandSkillDao.save(skill.getNameSkill());//добавляю всегда но так как умения/требования хранятся в Set повторяющиеся будут удаляться
        Employee employee = eDao.getById(skill.getToken());
        employee.addAttainments(new Attainments(skill.getNameSkill(), skill.getSkill()));
        eDao.update(employee, employee);
        return gson.toJson(new DtoAttainmentsResponse(skill.getNameSkill(), skill.getSkill()));
    }

    public String updateEmployeeSkill(String newSkillJson) throws ServerException {
        Employee employee;
        DtoSkillRequest newSkill = gson.fromJson(newSkillJson, DtoSkillRequest.class);
        try {
            newSkill.validate();
        } catch (ServerException | NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(newSkill.getToken());
        Attainments newAttainments = new Attainments(newSkill.getNameSkill(), newSkill.getSkill());
        employee = eDao.getById(newSkill.getToken());
        employee.updateAttainments(newSkill.getOldNameSkill(), newAttainments);
        return gson.toJson(new ErrorToken());
    }

    public String removeEmployeeSkill(String skillJson) throws ServerException {
        Employee employee;
        DtoSkillRequest skill = gson.fromJson(skillJson, DtoSkillRequest.class);
        try {
            skill.validate();
        } catch (NullPointerException | ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(skill.getToken());
        Attainments attainment = new Attainments(skill.getNameSkill(), skill.getSkill());
        employee = eDao.getById(skill.getToken());
        employee.removeAttainments(attainment);
        return gson.toJson(new DtoTokenResponse(employee.getId()));
    }

    public String setStatus(String statusJson) throws ServerException {
        DtoStatusEmployeeRequest dtoStatusEmployeeRequest = gson.fromJson(statusJson, DtoStatusEmployeeRequest.class);
        try {
            dtoStatusEmployeeRequest.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoStatusEmployeeRequest.getToken());
        Employee employee = eDao.getById(dtoStatusEmployeeRequest.getToken());
        if (employee == null) {
            return gson.toJson(new ErrorToken(ErrorStrings.EMPLOYEE_ERROR.getStringMessage()));
        }
        employee.setStatus(dtoStatusEmployeeRequest.isStatus());
        eDao.update(employee, employee);
        return gson.toJson(new ErrorToken());
    }

    public String setAccountStatus(String jsonStatus) throws ServerException {
        DtoToken dtoToken = gson.fromJson(jsonStatus, DtoToken.class);
        try {
            dtoToken.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoToken.getToken());
        eDao.setAccountStatus(dtoToken.getToken(), false);
        return gson.toJson(new DtoTokenResponse(dtoToken.getToken()));
    }

    public String removeEmployee(String tokenJson) throws ServerException {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        try {
            dtoToken.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoToken.getToken());
        eDao.delete(eDao.getById(dtoToken.getToken()));
        return gson.toJson(new DtoTokenResponse(dtoToken.getToken()));
    }

    private DtoSkills convertSkills(String abilitiesJson) {
        return gson.fromJson(abilitiesJson, DtoSkills.class);
    }

    private void validateActivity(String token) throws ServerException {
        if (!eDao.isActivity(token)) {
            throw new ServerException(ErrorCode.AUTHORIZATION_EXCEPTION);
        }
    }
}

