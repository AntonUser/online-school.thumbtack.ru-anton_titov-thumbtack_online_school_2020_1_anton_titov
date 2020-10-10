package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.daoimpl.DemandSkillDaoImpl;
import net.thumbtack.school.hiring.daoimpl.EmployeeDaoImpl;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.responce.*;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Skill;
import net.thumbtack.school.hiring.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//REVU: проверь все свои валидации
// там не должно быть NullPointerException, используй свой ServerException и лови тут его
public class EmployeeService {
    private static Gson gson = new Gson();
    // REVU слева пишем интерфейс, если он есть, то есть
    // private EmployeeDao employeeDao = new EmployeeDaoImpl();
    // сейчас не получится - нет в EmployeeDao методов, которые есть в EmployeeDaoImpl
    // они там не как методы от интерфейса, а сами по себе
    // надо их добавить
    // аналогично другие DAO
    private EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
    private DemandSkillDaoImpl demandSkillDaoImpl = new DemandSkillDaoImpl();

    public String registerEmployee(String json) {
        try {
            EmployeeDtoRegisterRequest employeeDtoRegisterRequest = getClassInstanceFromJson(EmployeeDtoRegisterRequest.class, json);
            validateEmployeeDtoRegisterRequest(employeeDtoRegisterRequest);
            List<Skill> attainments = new ArrayList<>();
            for (SkillDtoRequest skill : employeeDtoRegisterRequest.getAttainmentsList()) {
                attainments.add(new Skill(skill.getName(), skill.getLevel()));
            }
            Employee employee = new Employee(employeeDtoRegisterRequest.getFirstName(),
                    employeeDtoRegisterRequest.getPatronymic(), employeeDtoRegisterRequest.getLastName(),
                    employeeDtoRegisterRequest.getLogin(), employeeDtoRegisterRequest.getPassword(),
                    employeeDtoRegisterRequest.getEmail(), attainments);
            String token = employeeDao.registerEmployee(employee);
            return gson.toJson(token);
        } catch (ServerException se) {
            return gson.toJson(new ErrorDtoResponse(se));
        }
    }

    public String logOut(String json) {
        try {
            DtoToken dtoToken = getClassInstanceFromJson(DtoToken.class, json);
            dtoToken.validate();
            employeeDao.logOut(dtoToken.getToken());
            return gson.toJson(dtoToken.getToken());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public String loginEmployee(String json) {
        try {
            DtoLoginRequest dtoLoginRequest = getClassInstanceFromJson(DtoLoginRequest.class, json);
            dtoLoginRequest.validate();
            String employeeToken = employeeDao.loginEmployee(dtoLoginRequest.getLogin(), dtoLoginRequest.getPassword());
            return gson.toJson(new DtoLoginResponse(employeeToken));
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }


    public String getVacanciesNotLess(String abilitiesJson) throws ServerException {
        DtoSkills skills = convertSkills(abilitiesJson);
        try {
            skills.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
        validateActivity(skills.getToken());
        List<Skill> skillsList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : skills.getSkills().entrySet()) {
            skillsList.add(new Skill(entry.getKey(), entry.getValue()));
        }
        return gson.toJson(new DtoVacanciesResponse(employeeDao.getVacanciesListNotLess(skillsList)));
    }

    public String getVacanciesObligatoryDemand(String abilitiesJson) throws ServerException {
        DtoSkills skills = convertSkills(abilitiesJson);
        try {
            skills.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
        validateActivity(skills.getToken());
        List<Skill> skillsList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : skills.getSkills().entrySet()) {
            skillsList.add(new Skill(entry.getKey(), entry.getValue()));
        }
        return gson.toJson(new DtoVacanciesResponse(employeeDao.getVacanciesListObligatoryDemand(skillsList)));
    }

    public String getVacanciesOnlyName(String abilitiesJson) throws ServerException {
        DtoSkills skills = convertSkills(abilitiesJson);
        try {
            skills.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
        validateActivity(skills.getToken());
        List<Skill> skillsList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : skills.getSkills().entrySet()) {
            skillsList.add(new Skill(entry.getKey(), entry.getValue()));
        }
        return gson.toJson(new DtoVacanciesResponse(employeeDao.getVacanciesListOnlyName(skillsList)));
    }

    public String getVacanciesWithOneDemand(String abilitiesJson) throws ServerException {
        DtoSkills skills = convertSkills(abilitiesJson);
        try {
            skills.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
        validateActivity(skills.getToken());
        List<Skill> skillsList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : skills.getSkills().entrySet()) {
            skillsList.add(new Skill(entry.getKey(), entry.getValue()));
        }
        return gson.toJson(new DtoVacanciesResponse(employeeDao.getVacanciesListWithOneDemand(skillsList)));
    }

   /* public String getAllDemandSkills(String tokenJson) throws ServerException {
        DtoToken token = gson.fromJson(tokenJson, DtoToken.class);
        try {
            token.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
        validateActivity(token.getToken());
        return gson.toJson(new DtoAllDemandsSkillsResponse(employeeDao.getAllDemandSkills()));
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
        DtoSkillRequest newSkill = gson.fromJson(newSkillJson, DtoSkillRequest.class);
        try {
            newSkill.validate();
            validateActivity(newSkill.getToken());
            employeeDao.updateEmployeeSkill(newSkill.getToken(), newSkill.getOldNameSkill(), new Skill(newSkill.getNameSkill(), newSkill.getSkill()));
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
        return gson.toJson(new ErrorDtoResponse());
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
            return gson.toJson(new ErrorDtoResponse(ex));
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
        // REVU не мешает еще на null проверить
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

