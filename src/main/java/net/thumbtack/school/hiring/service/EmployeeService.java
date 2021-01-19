package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.daoimpl.DemandSkillDaoImpl;
import net.thumbtack.school.hiring.daoimpl.EmployeeDaoImpl;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.response.*;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.*;

import java.util.ArrayList;
import java.util.List;

//REVU: проверь все свои валидации
// там не должно быть NullPointerException, используй свой ServerException и лови тут его
public class EmployeeService {
    private static Gson gson = new Gson();
    private EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
    private DemandSkillDaoImpl demandSkillDaoImpl = new DemandSkillDaoImpl();

    public String registerEmployee(String json) {
        try {
            EmployeeDtoRegisterRequest employeeDtoRegisterRequest = getClassInstanceFromJson(EmployeeDtoRegisterRequest.class, json);
            validateEmployeeDtoRegisterRequest(employeeDtoRegisterRequest);
            List<Skill> attainments = new ArrayList<>();
            for (DtoSkill skill : employeeDtoRegisterRequest.getAttainmentsList()) {
                attainments.add(new Skill(skill.getName(), skill.getLevel()));
            }
            Employee employee = new Employee(employeeDtoRegisterRequest.getFirstName(),
                    employeeDtoRegisterRequest.getPatronymic(), employeeDtoRegisterRequest.getLastName(),
                    employeeDtoRegisterRequest.getLogin(), employeeDtoRegisterRequest.getPassword(),
                    employeeDtoRegisterRequest.getEmail(), attainments);
            String token = employeeDao.registerEmployee(employee);
            return gson.toJson(new DtoToken(token));
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

    public String getVacanciesNotLess(String abilitiesJson) {
        DtoSkills skills = convertSkills(abilitiesJson);
        List<Skill> skillsList = new ArrayList<>();
        List<DtoVacancyResponse> outList = new ArrayList<>();
        for (DtoSkill dtoSkill : skills.getSkills()) {
            skillsList.add(new Skill(dtoSkill.getName(), dtoSkill.getLevel()));
        }
        List<Vacancy> vacancies = employeeDao.getVacanciesListNotLess(skillsList);
        List<DtoRequirement> requirements = new ArrayList<>();
        for (Vacancy vacancy : vacancies) {
            for (Requirement requirement : vacancy.getRequirements()) {
                requirements.add(new DtoRequirement(requirement.getName(), requirement.getLevel(), requirement.getNecessary().toString()));
            }
            outList.add(new DtoVacancyResponse(vacancy.getId(), vacancy.getName(), vacancy.getSalary(), new ArrayList<>(requirements), vacancy.getStatus().toString()));
            requirements.clear();
        }
        return gson.toJson(new DtoVacanciesResponse(outList));
    }

    public String getVacanciesObligatoryDemand(String abilitiesJson) {
        DtoSkills skills = convertSkills(abilitiesJson);
        List<Skill> skillsList = new ArrayList<>();
        List<DtoVacancyResponse> outList = new ArrayList<>();
        for (DtoSkill dtoSkill : skills.getSkills()) {
            skillsList.add(new Skill(dtoSkill.getName(), dtoSkill.getLevel()));
        }
        List<Vacancy> vacancies = employeeDao.getVacanciesListObligatoryDemand(skillsList);
        List<DtoRequirement> requirements = new ArrayList<>();
        for (Vacancy vacancy : vacancies) {
            for (Requirement requirement : vacancy.getRequirements()) {
                requirements.add(new DtoRequirement(requirement.getName(), requirement.getLevel(), requirement.getNecessary().toString()));
            }
            outList.add(new DtoVacancyResponse(vacancy.getId(), vacancy.getName(), vacancy.getSalary(), new ArrayList<>(requirements), vacancy.getStatus().toString()));
            requirements.clear();
        }
        return gson.toJson(new DtoVacanciesResponse(outList));
    }

    public String getVacanciesOnlyName(String abilitiesJson) {
        DtoSkills skills = convertSkills(abilitiesJson);
        List<Skill> skillsList = new ArrayList<>();
        List<DtoVacancyResponse> outList = new ArrayList<>();
        for (DtoSkill dtoSkill : skills.getSkills()) {
            skillsList.add(new Skill(dtoSkill.getName(), dtoSkill.getLevel()));
        }
        List<Vacancy> vacancies = employeeDao.getVacanciesListOnlyName(skillsList);
        List<DtoRequirement> requirements = new ArrayList<>();
        for (Vacancy vacancy : vacancies) {
            for (Requirement requirement : vacancy.getRequirements()) {
                requirements.add(new DtoRequirement(requirement.getName(), requirement.getLevel(), requirement.getNecessary().toString()));
            }
            outList.add(new DtoVacancyResponse(vacancy.getId(), vacancy.getName(), vacancy.getSalary(), new ArrayList<>(requirements), vacancy.getStatus().toString()));
            requirements.clear();
        }
        return gson.toJson(new DtoVacanciesResponse(outList));
    }

    public String getVacanciesWithOneDemand(String abilitiesJson) {
        DtoSkills skills = convertSkills(abilitiesJson);
        List<Skill> skillsList = new ArrayList<>();
        List<DtoVacancyResponse> outList = new ArrayList<>();
        for (DtoSkill dtoSkill : skills.getSkills()) {
            skillsList.add(new Skill(dtoSkill.getName(), dtoSkill.getLevel()));
        }
        List<Vacancy> vacancies = employeeDao.getVacanciesListWithOneDemand(skillsList);
        List<DtoRequirement> requirements = new ArrayList<>();
        for (Vacancy vacancy : vacancies) {
            for (Requirement requirement : vacancy.getRequirements()) {
                requirements.add(new DtoRequirement(requirement.getName(), requirement.getLevel(), requirement.getNecessary().toString()));
            }
            outList.add(new DtoVacancyResponse(vacancy.getId(), vacancy.getName(), vacancy.getSalary(), new ArrayList<>(requirements), vacancy.getStatus().toString()));
            requirements.clear();
        }
        return gson.toJson(new DtoVacanciesResponse(outList));
    }

    public String setAccountStatusEnable(String tokenJson) {
        DtoToken token = gson.fromJson(tokenJson, DtoToken.class);
        try {
            employeeDao.setAccountStatusEnable(token.getToken());
            return gson.toJson(new DtoResponseMessage("SUCCESSFUL"));
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e));
        }
    }

    public String setAccountStatusDisable(String tokenJson) {
        DtoToken token = gson.fromJson(tokenJson, DtoToken.class);
        try {
            employeeDao.setAccountStatusDisable(token.getToken());
            return gson.toJson(new DtoResponseMessage("SUCCESSFUL"));
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e));
        }
    }

    public String addSkill(String skillJson) {
        AddSkillRequest skill = gson.fromJson(skillJson, AddSkillRequest.class);
        try {
            skill.validate();
            validateActivity(skill.getToken());
            employeeDao.addSkill(new Skill(skill.getName(), skill.getLevel()), skill.getToken());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
        return gson.toJson(new DtoAttainmentsResponse(skill.getName(), skill.getLevel()));
    }

    public String updateEmployeeSkill(String newSkillJson) {
        DtoSkillRequest newSkill = gson.fromJson(newSkillJson, DtoSkillRequest.class);
        try {
            newSkill.validate();
            employeeDao.updateEmployeeSkill(newSkill.getToken(), newSkill.getOldNameSkill(), new Skill(newSkill.getNameSkill(), newSkill.getLevel()));
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
        return gson.toJson(new ErrorDtoResponse());
    }

    public String removeEmployeeSkill(String skillJson) {
        DtoRemoveSkillRequest skill = gson.fromJson(skillJson, DtoRemoveSkillRequest.class);
        try {
            validateActivity(skill.getToken());
            employeeDao.removeEmployeeSkill(skill.getToken(), skill.getOldNameSkill());
        } catch (ServerException e) {
            return gson.toJson(new ErrorDtoResponse(e));
        }
        return gson.toJson(new DtoTokenResponse(skill.getToken()));
    }

    public String removeAccountEmployee(String tokenJson) {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        try {
            dtoToken.validate();
            validateActivity(dtoToken.getToken());
            employeeDao.delete(dtoToken.getToken());
            return gson.toJson(new DtoTokenResponse(dtoToken.getToken()));
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
    }

    public String updateEmployee(String tokenJson) {
        DtoUpdateEmployeeRequest dtoUpdateEmployeeRequest = gson.fromJson(tokenJson, DtoUpdateEmployeeRequest.class);
        try {
            List<Skill> skills = new ArrayList<>();
            if (dtoUpdateEmployeeRequest.getAttainmentsList() != null &&
                    dtoUpdateEmployeeRequest.getAttainmentsList().size() != 0) {
                dtoUpdateEmployeeRequest.getAttainmentsList().forEach(dtoSkill -> skills.add(new Skill(dtoSkill.getName(), dtoSkill.getLevel())));
            }

            Employee employee = new Employee(dtoUpdateEmployeeRequest.getFirstName(),
                    dtoUpdateEmployeeRequest.getPatronymic(), dtoUpdateEmployeeRequest.getLastName(),
                    dtoUpdateEmployeeRequest.getLogin(), dtoUpdateEmployeeRequest.getPassword(),
                    dtoUpdateEmployeeRequest.getEmail(), skills);
            employee.setStatus(EmployeeStatus.valueOf(dtoUpdateEmployeeRequest.getStatus()));
            employeeDao.update(dtoUpdateEmployeeRequest.getId(), employee);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorDtoResponse(ex));
        }
        return gson.toJson(new DtoTokenResponse(dtoUpdateEmployeeRequest.getId()));
    }

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
            throw new ServerException(ErrorCode.EMPTY_FIRST_NAME);
        }
        if (employeeDtoRegisterRequest.getLastName() == null || employeeDtoRegisterRequest.getLastName().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_LAST_NAME);
        }
        if (employeeDtoRegisterRequest.getEmail() == null || employeeDtoRegisterRequest.getEmail().isEmpty()) {
            throw new ServerException(ErrorCode.EMAIL_EXCEPTION);
        }
        if (employeeDtoRegisterRequest.getLogin() == null || employeeDtoRegisterRequest.getLogin().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_LOGIN);
        }
        if (employeeDtoRegisterRequest.getPassword() == null || employeeDtoRegisterRequest.getPassword().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_PASSWORD);
        }
    }
}

