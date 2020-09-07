package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.daoimpl.DemandSkillDao;
import net.thumbtack.school.hiring.daoimpl.EmployeeDao;
import net.thumbtack.school.hiring.daoimpl.VacancyDao;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.responce.*;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Attainments;
import net.thumbtack.school.hiring.model.Employee;

import java.util.UUID;

//REVU: проверь все свои валидации
// там не должно быть NullPointerException, используй свой ServerException и лови тут его
public class EmployeeService {
    private VacancyDao vacancyDao;
    private Gson gson;
    private EmployeeDao eDao;
    private DemandSkillDao demandSkillDao;

    public EmployeeService() {
        gson = new Gson();
        eDao = new EmployeeDao();
        vacancyDao = new VacancyDao();
        demandSkillDao = new DemandSkillDao();
    }

    public String registerEmployee(String json) {
        EmployeeDtoRegisterRequest employeeDtoRegisterRequest = gson.fromJson(json, EmployeeDtoRegisterRequest.class);
        Employee employee;
        try {
            employeeDtoRegisterRequest.validate();
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
        String employeeToken;
        dtoLoginRequest = gson.fromJson(json, DtoLoginRequest.class);
        try {
            dtoLoginRequest.validate();
            employeeToken = eDao.loginEmployee(dtoLoginRequest.getLogin(), dtoLoginRequest.getPassword());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        return gson.toJson(new DtoLoginResponse(employeeToken));
    }

    public String getVacanciesNotLess(String abilitiesJson) throws ServerException {
        DtoSkills skills = convertSkills(abilitiesJson);
        try {
            skills.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(skills.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDao.getVacanciesListNotLess(skills.getSkills())));
    }

    public String getVacanciesObligatoryDemand(String abilitiesJson) throws ServerException {
        DtoSkills skills = convertSkills(abilitiesJson);
        try {
            skills.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(skills.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDao.getVacanciesListObligatoryDemand(skills.getSkills())));
    }

    public String getVacancies(String abilitiesJson) throws ServerException {
        DtoSkills skills = convertSkills(abilitiesJson);
        try {
            skills.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(skills.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDao.getVacanciesListOnlyName(skills.getSkills())));
    }

    public String getVacanciesWithOneDemand(String abilitiesJson) throws ServerException {
        DtoSkills skills = convertSkills(abilitiesJson);
        try {
            skills.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(skills.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDao.getVacanciesListWithOneDemand(skills.getSkills())));
    }

    public String getAllDemandSkills(String tokenJson) throws ServerException {
        DtoToken token = gson.fromJson(tokenJson, DtoToken.class);
        try {
            token.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(token.getToken());
        return gson.toJson(new DtoAllDemandsSkillsResponse(vacancyDao.getAllDemandSkills()));
    }

    public String addEmployeeSkill(String skillJson) throws ServerException {
        DtoSkillRequest skill = gson.fromJson(skillJson, DtoSkillRequest.class);
        try {
            skill.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(skill.getToken());
        eDao.addSkillForEmployee(new Attainments(skill.getNameSkill(), skill.getSkill()), skill.getToken());
        return gson.toJson(new DtoAttainmentsResponse(skill.getNameSkill(), skill.getSkill()));
    }

    public String updateEmployeeSkill(String newSkillJson) throws ServerException {
        Employee employee;
        DtoSkillRequest newSkill = gson.fromJson(newSkillJson, DtoSkillRequest.class);
        try {
            newSkill.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(newSkill.getToken());
        //REVU: вынеси всю логику в БД, чтобы была как бы одна операция
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
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(skill.getToken());
        //REVU: вынеси всю логику в БД, чтобы была как бы одна операция
        // то есть не надо тут брать employee by id, передай id в БД
        Attainments attainment = new Attainments(skill.getNameSkill(), skill.getSkill());
        employee = eDao.getById(skill.getToken());
        employee.removeAttainments(attainment);
        return gson.toJson(new DtoTokenResponse(employee.getId()));
    }

    public String setStatus(String statusJson) throws ServerException {
        DtoStatusEmployeeRequest dtoStatusEmployeeRequest = gson.fromJson(statusJson, DtoStatusEmployeeRequest.class);
        validateActivity(dtoStatusEmployeeRequest.getToken());
        try {
            dtoStatusEmployeeRequest.validate();
            eDao.setEmployeeStatus(dtoStatusEmployeeRequest.getToken(), dtoStatusEmployeeRequest.isStatus());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        return gson.toJson(new ErrorToken());
    }

    public String setAccountStatus(String jsonStatus) throws ServerException {
        DtoToken dtoToken = gson.fromJson(jsonStatus, DtoToken.class);
        validateActivity(dtoToken.getToken());
        try {
            dtoToken.validate();
            eDao.setAccountStatus(dtoToken.getToken(), false);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        return gson.toJson(new DtoTokenResponse(dtoToken.getToken()));
    }

    public String removeEmployee(String tokenJson) throws ServerException {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        try {
            dtoToken.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoToken.getToken());
        eDao.delete(dtoToken.getToken());
        return gson.toJson(new DtoTokenResponse(dtoToken.getToken()));
    }

    public String updateEmployee(String tokenJson) throws ServerException {
        DtoUpdateEmployeeRequest dtoUpdateEmployeeRequest = gson.fromJson(tokenJson, DtoUpdateEmployeeRequest.class);
        try {
            dtoUpdateEmployeeRequest.validate();
            eDao.update(dtoUpdateEmployeeRequest.getId(), new Employee(dtoUpdateEmployeeRequest.getId(), dtoUpdateEmployeeRequest.getFirstName(),
                    dtoUpdateEmployeeRequest.getPatronymic(), dtoUpdateEmployeeRequest.getLastName(),
                    dtoUpdateEmployeeRequest.getLogin(), dtoUpdateEmployeeRequest.getPassword(),
                    dtoUpdateEmployeeRequest.getEmail(), dtoUpdateEmployeeRequest.isStatus(),
                    dtoUpdateEmployeeRequest.getAttainmentsList(), dtoUpdateEmployeeRequest.isActivity()));
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getErrorCode().getErrorCode()));
        }
        return gson.toJson(new DtoTokenResponse(dtoUpdateEmployeeRequest.getId()));
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

