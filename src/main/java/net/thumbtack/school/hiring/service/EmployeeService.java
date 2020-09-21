package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.daoimpl.DemandSkillDaoImpl;
import net.thumbtack.school.hiring.daoimpl.EmployeeDaoImpl;
import net.thumbtack.school.hiring.daoimpl.VacancyDaoImpl;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.responce.*;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Skill;
import net.thumbtack.school.hiring.model.Employee;

import java.util.UUID;

//REVU: проверь все свои валидации
// там не должно быть NullPointerException, используй свой ServerException и лови тут его
public class EmployeeService {
    private VacancyDaoImpl vacancyDaoImpl = new VacancyDaoImpl();
    private static Gson gson = new Gson();
    private EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
    private DemandSkillDaoImpl demandSkillDaoImpl = new DemandSkillDaoImpl();

    public String registerEmployee(String json) {
        Employee employee;
        try {
            EmployeeDtoRegisterRequest employeeDtoRegisterRequest = getClassInstanceFromJson(EmployeeDtoRegisterRequest.class, json);
            validateEmployeeDtoRegisterRequest(employeeDtoRegisterRequest);
            employee = new Employee(employeeDtoRegisterRequest.getFirstName(),
                    employeeDtoRegisterRequest.getPatronymic(), employeeDtoRegisterRequest.getLastName(),
                    employeeDtoRegisterRequest.getLogin(), employeeDtoRegisterRequest.getPassword(),
                    employeeDtoRegisterRequest.getEmail(), employeeDtoRegisterRequest.getAttainmentsList());
            return gson.toJson(employeeDao.registerEmployee(employee));
        } catch (ServerException se) {
            return gson.toJson(new ErrorToken(se.getErrorCode().getErrorCode()));
        }
    }

    public String logOut(String json) {
        try {
            DtoToken dtoToken = getClassInstanceFromJson(DtoToken.class, json);
            dtoToken.validate();
            employeeDao.logOut(dtoToken.getToken());
            return gson.toJson(dtoToken);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getErrorCode().getErrorCode()));
        }
    }

    public String loginEmployee(String json) {
        try {
            DtoLoginRequest dtoLoginRequest = getClassInstanceFromJson(DtoLoginRequest.class, json);
            dtoLoginRequest.validate();
            String employeeToken = employeeDao.loginEmployee(dtoLoginRequest.getLogin(), dtoLoginRequest.getPassword());
            return gson.toJson(new DtoLoginResponse(employeeToken));
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
    }


    public String getVacanciesNotLess(String abilitiesJson) throws ServerException {
        DtoSkills skills = convertSkills(abilitiesJson);
        try {
            skills.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(skills.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDaoImpl.getVacanciesListNotLess(skills.getSkills())));
    }

    public String getVacanciesObligatoryDemand(String abilitiesJson) throws ServerException {
        DtoSkills skills = convertSkills(abilitiesJson);
        try {
            skills.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(skills.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDaoImpl.getVacanciesListObligatoryDemand(skills.getSkills())));
    }

    public String getVacancies(String abilitiesJson) throws ServerException {
        DtoSkills skills = convertSkills(abilitiesJson);
        try {
            skills.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(skills.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDaoImpl.getVacanciesListOnlyName(skills.getSkills())));
    }

    public String getVacanciesWithOneDemand(String abilitiesJson) throws ServerException {
        DtoSkills skills = convertSkills(abilitiesJson);
        try {
            skills.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(skills.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDaoImpl.getVacanciesListWithOneDemand(skills.getSkills())));
    }

    public String getAllDemandSkills(String tokenJson) throws ServerException {
        DtoToken token = gson.fromJson(tokenJson, DtoToken.class);
        try {
            token.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(token.getToken());
        return gson.toJson(new DtoAllDemandsSkillsResponse(vacancyDaoImpl.getAllDemandSkills()));
    }

    /*
        public String addEmployeeSkill(String skillJson) throws ServerException {
            DtoSkillRequest skill = gson.fromJson(skillJson, DtoSkillRequest.class);
            try {
                skill.validate();
            } catch (ServerException ex) {
                return gson.toJson(new ErrorToken(ex.getMessage()));
            }
            validateActivity(skill.getToken());
            eDao.addSkillForEmployee(new Skill(skill.getNameSkill(), skill.getSkill()), skill.getToken());
            return gson.toJson(new DtoAttainmentsResponse(skill.getNameSkill(), skill.getSkill()));
        }
    */
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
        Skill newAttainments = new Skill(newSkill.getNameSkill(), newSkill.getSkill());
        employee = employeeDao.getEmployeeById(newSkill.getToken());
        employee.updateAttainments(newSkill.getOldNameSkill(), newAttainments);
        return gson.toJson(new ErrorToken());
    }

    /*public String removeEmployeeSkill(String skillJson) throws ServerException {
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

        Skill attainment = new Skill(skill.getNameSkill(), skill.getSkill());
        employee = employeeDao.getEmployeeById(skill.getToken());
        employee.removeAttainments(attainment);
        return gson.toJson(new DtoTokenResponse(employee.getId()));
    }*/

    public String removeEmployee(String tokenJson) throws ServerException {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        try {
            dtoToken.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoToken.getToken());
        employeeDao.delete(dtoToken.getToken());
        return gson.toJson(new DtoTokenResponse(dtoToken.getToken()));
    }

    /*public String updateEmployee(String tokenJson) throws ServerException {
        DtoUpdateEmployeeRequest dtoUpdateEmployeeRequest = gson.fromJson(tokenJson, DtoUpdateEmployeeRequest.class);
        try {
            dtoUpdateEmployeeRequest.validate();
            employeeDao.update(dtoUpdateEmployeeRequest.getId(), new Employee(dtoUpdateEmployeeRequest.getFirstName(),
                    dtoUpdateEmployeeRequest.getPatronymic(), dtoUpdateEmployeeRequest.getLastName(),
                    dtoUpdateEmployeeRequest.getLogin(), dtoUpdateEmployeeRequest.getPassword(),
                    dtoUpdateEmployeeRequest.getEmail(), dtoUpdateEmployeeRequest.getAttainmentsList()));
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getErrorCode().getErrorCode()));
        }
        return gson.toJson(new DtoTokenResponse(dtoUpdateEmployeeRequest.getId()));
    }*/

    private DtoSkills convertSkills(String abilitiesJson) {
        return gson.fromJson(abilitiesJson, DtoSkills.class);
    }

    private void validateActivity(String token) throws ServerException {
        if (employeeDao.getEmployeeById(token) == null) {
            throw new ServerException(ErrorCode.AUTHORIZATION_EXCEPTION);
        }
    }

    private static <T> T getClassInstanceFromJson(Class<T> xClass, String json) throws ServerException {
        try {
            return gson.fromJson(json, xClass);
        } catch (JsonSyntaxException ex) {
            throw new ServerException(ErrorCode.JSON_EXCEPTION);
        }
    }

    private static void validateEmployeeDtoRegisterRequest(EmployeeDtoRegisterRequest employeeDtoRegisterRequest) throws ServerException {
        if (employeeDtoRegisterRequest.getFirstName().isEmpty()) {
            throw new ServerException(ErrorCode.NULL_FIRST_NAME_EXCEPTION);
        }
        if (employeeDtoRegisterRequest.getLastName() == null || employeeDtoRegisterRequest.getLastName().isEmpty()) {
            throw new ServerException(ErrorCode.NULL_LAST_NAME_EXCEPTION);
        }
        if (employeeDtoRegisterRequest.getEmail() == null || employeeDtoRegisterRequest.getEmail().isEmpty()) {
            throw new ServerException(ErrorCode.EMAIL_EXCEPTION);
        }
        if (employeeDtoRegisterRequest.getLogin() == null || employeeDtoRegisterRequest.getLogin().isEmpty()) {
            throw new ServerException(ErrorCode.NULL_LOGIN_EXCEPTION);
        }
        if (employeeDtoRegisterRequest.getPassword() == null || employeeDtoRegisterRequest.getPassword().isEmpty()) {
            throw new ServerException(ErrorCode.NULL_PASSWORD_EXCEPTION);
        }
    }
}

