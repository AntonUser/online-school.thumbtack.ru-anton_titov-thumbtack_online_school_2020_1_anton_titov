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
    private VacancyDaoImpl vacancyDaoImpl;
    private Gson gson;
    // REVU Не сокр. :-) И используйте интерфейс, если он есть
    // private EmployeeDaol employeeDao;
    private EmployeeDaoImpl eDao;
    private DemandSkillDaoImpl demandSkillDaoImpl;

    public EmployeeService() {
    	// REVU инициализируйте прямо в описании
        gson = new Gson();
        eDao = new EmployeeDaoImpl();
        vacancyDaoImpl = new VacancyDaoImpl();
        demandSkillDaoImpl = new DemandSkillDaoImpl();
    }

    public String registerEmployee(String json) {
        Employee employee;
        try {
            getClassInstanceFromJson(EmployeeDtoRegisterRequest.class, json);
            // REVU если getClassInstanceFromJson вернет результат, то этот вызов будет не нужен
            EmployeeDtoRegisterRequest employeeDtoRegisterRequest = gson.fromJson(json, EmployeeDtoRegisterRequest.class);
            employeeDtoRegisterRequest.validate();
            // REVU UUID.randomUUID().toString() тут передавать незачем, пусть конструктор сам поставит
            employee = new Employee(UUID.randomUUID().toString(), employeeDtoRegisterRequest.getFirstName(),
                    employeeDtoRegisterRequest.getPatronymic(), employeeDtoRegisterRequest.getLastName(),
                    employeeDtoRegisterRequest.getLogin(), employeeDtoRegisterRequest.getPassword(),
                    employeeDtoRegisterRequest.getEmail(), employeeDtoRegisterRequest.getAttainmentsList(), true);
            eDao.registerEmployee(employee);
        } catch (ServerException se) {
            return gson.toJson(new ErrorToken(se.getErrorCode().getErrorCode()));
        }
        return gson.toJson(new DtoRegisterResponse(employee.getId()));
    }

    public String logOut(String json) {
        try {
            getClassInstanceFromJson(DtoToken.class, json);
            DtoToken dtoToken = gson.fromJson(json, DtoToken.class);
            dtoToken.validate();
            eDao.logOut(dtoToken.getToken());
            return gson.toJson(dtoToken);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getErrorCode().getErrorCode()));
        }
    }

    public String loginEmployee(String json) {
        try {
            getClassInstanceFromJson(DtoLoginRequest.class, json);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getErrorCode().getErrorCode()));
        }
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
        employee = eDao.getEmployeeById(newSkill.getToken());
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

        Skill attainment = new Skill(skill.getNameSkill(), skill.getSkill());
        employee = eDao.getEmployeeById(skill.getToken());
        employee.removeAttainments(attainment);
        return gson.toJson(new DtoTokenResponse(employee.getId()));
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
                    dtoUpdateEmployeeRequest.getEmail(), dtoUpdateEmployeeRequest.getAttainmentsList(), dtoUpdateEmployeeRequest.isActivity()));
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getErrorCode().getErrorCode()));
        }
        return gson.toJson(new DtoTokenResponse(dtoUpdateEmployeeRequest.getId()));
    }

    private DtoSkills convertSkills(String abilitiesJson) {
        return gson.fromJson(abilitiesJson, DtoSkills.class);
    }

    private void validateActivity(String token) throws ServerException {
        if (eDao.getEmployeeById(token) == null) {
            throw new ServerException(ErrorCode.AUTHORIZATION_EXCEPTION);
        }
    }

    // REVU Вы лишь проверку сделали, а надо было вернуть экземпляр класса c
// 	public static <T> T getClassInstanceFromJson(String json, Class<T> clazz) throws ServerException {
// это шаблонный метод (не класс!)
// https://docs.oracle.com/javase/tutorial/extra/generics/methods.html    
    
    
    private void getClassInstanceFromJson(Class c, String json) throws ServerException {
        try {
            gson.fromJson(json, c);
        } catch (JsonSyntaxException ex) {
            throw new ServerException(ErrorCode.JSON_EXCEPTION);
        }
    }
}

