package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.daoimpl.DemandSkillDaoImpl;
import net.thumbtack.school.hiring.daoimpl.EmployeeDaoImpl;
import net.thumbtack.school.hiring.daoimpl.EmployerDaoImpl;
import net.thumbtack.school.hiring.daoimpl.VacancyDaoImpl;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.responce.*;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Requirement;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.Vacancy;

import java.util.UUID;

public class EmployerService {
    private EmployeeDaoImpl employeeDaoImpl;
    private Gson gson;
    private VacancyDaoImpl vacancyDaoImpl;
    private EmployerDaoImpl employerDaoImpl;
    private DemandSkillDaoImpl demandSkillDaoImpl;

    public EmployerService() {
        employerDaoImpl = new EmployerDaoImpl();
        employeeDaoImpl = new EmployeeDaoImpl();
        gson = new Gson();
        vacancyDaoImpl = new VacancyDaoImpl();
        demandSkillDaoImpl = new DemandSkillDaoImpl();
    }

    public String registerEmployer(String json) {
        try {
            getClassInstanceFromJson(EmployerDtoRegisterRequest.class, json);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getErrorCode().getErrorCode()));
        }
        EmployerDtoRegisterRequest employerDtoRegisterRequest = gson.fromJson(json, EmployerDtoRegisterRequest.class);
        Employer employer;
        try {
            employerDtoRegisterRequest.validate();
            employer = new Employer(employerDtoRegisterRequest.getName(), employerDtoRegisterRequest.getAddress(),
                    employerDtoRegisterRequest.getEmail(), UUID.randomUUID().toString(),
                    employerDtoRegisterRequest.getFirstName(), employerDtoRegisterRequest.getPatronymic(),
                    employerDtoRegisterRequest.getLastName(), employerDtoRegisterRequest.getLogin(),
                    employerDtoRegisterRequest.getPassword(), true);
            employerDaoImpl.registerEmployer(employer);
        } catch (ServerException se) {
            return gson.toJson(new ErrorToken(se.getErrorCode().getErrorCode()));
        }
        return gson.toJson(new DtoRegisterResponse(employer.getId()));
    }

    public String loginEmployer(String json) {
        String token;
        try {
            getClassInstanceFromJson(DtoLoginRequest.class, json);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getErrorCode().getErrorCode()));
        }
        DtoLoginRequest dtoLoginRequest = gson.fromJson(json, DtoLoginRequest.class);
        try {
            dtoLoginRequest.validate();
            token = employerDaoImpl.loginEmployer(dtoLoginRequest.getLogin(), dtoLoginRequest.getPassword());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getErrorCode().getErrorCode()));
        }
        return gson.toJson(new DtoLoginResponse(token));
    }

    public String logOut(String json) {
        try {
            getClassInstanceFromJson(DtoToken.class, json);
            DtoToken dtoToken = gson.fromJson(json, DtoToken.class);
            dtoToken.validate();
            employerDaoImpl.logOut(dtoToken.getToken());
            return gson.toJson(dtoToken);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getErrorCode().getErrorCode()));
        }
    }
   /* public String addVacancy(String vacancyJson) throws ServerException {
        try {
            getClassInstanceFromJson(DtoAddVacancyRequest.class, vacancyJson);
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getErrorCode().getErrorCode()));
        }
        DtoAddVacancyRequest dtoAddVacancyRequest = gson.fromJson(vacancyJson, DtoAddVacancyRequest.class);
        try {
            dtoAddVacancyRequest.validate();
        } catch (ServerException e) {
            return gson.toJson(new ErrorToken(e.getMessage()));
        }
        // REVU не надо токен в вакансию заносить
        // вместо validateActivity лучше было бы получить по токену Employer
        // а для этого в БД добавить Map<String, User> userByToken
        // и потом этого Employer передавать в vacancyDao.save
        // будет логично
        // vacancyDao.save(employer, new Vacancy...
        // validateActivity(dtoAddVacancyRequest.getToken());
        employerDaoImpl.getEmployerById(dtoAddVacancyRequest.getToken())
        vacancyDaoImpl.save(new Vacancy(dtoAddVacancyRequest.getNamePost(), dtoAddVacancyRequest.getSalary(), dtoAddVacancyRequest.getObligatoryDemands(), dtoAddVacancyRequest.getNotObligatoryDemands(), dtoAddVacancyRequest.getToken()));
        return gson.toJson(new DtoTokenResponse(dtoAddVacancyRequest.getToken()));
    }*/
/*
    public String getEmployeeListNotLess(String demandsJson) throws ServerException {
        DtoDemands dtoDemands = convertDemands(demandsJson);
        try {
            dtoDemands.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoDemands.getToken());
        return gson.toJson(new DtoEmployeesResponse(employeeDaoImpl.getEmployeeListNotLessByDemand(dtoDemands.getDemands())));
    }

    public String getEmployeeListObligatoryDemand(String demandsJson) throws ServerException {
        DtoDemands dtoDemands = convertDemands(demandsJson);
        try {
            dtoDemands.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoDemands.getToken());
        return gson.toJson(new DtoEmployeesResponse(employeeDaoImpl.getEmployeeListObligatoryDemandByDemands(dtoDemands.getDemands())));
    }

    public String getEmployee(String demandsJson) throws ServerException {
        DtoDemands dtoDemands = convertDemands(demandsJson);
        try {
            dtoDemands.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoDemands.getToken());
        return gson.toJson(new DtoEmployeesResponse(employeeDaoImpl.getEmployeeListByDemands(dtoDemands.getDemands())));
    }

    public String getEmployeeWithOneDemand(String demandsJson) throws ServerException {
        DtoDemands dtoDemands = convertDemands(demandsJson);
        try {
            dtoDemands.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoDemands.getToken());
        return gson.toJson(new DtoEmployeesResponse(employeeDaoImpl.getEmployeeListWithOneDemandByDemands(dtoDemands.getDemands())));
    }

    public String getAllVacancies(String tokenJson) throws ServerException {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        try {
            dtoToken.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoToken.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDaoImpl.getVacanciesListByToken(dtoToken.getToken())));
    }

    public String getActivityVacancies(String tokenJson) throws ServerException {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        try {
            dtoToken.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoToken.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDaoImpl.getActivityVacanciesListByToken(dtoToken.getToken())));
    }

    public String getNotActivityVacancies(String tokenJson) throws ServerException {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        try {
            dtoToken.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoToken.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDaoImpl.getNotActivityVacanciesListByToken(dtoToken.getToken())));
    }

    public String setVacancyStatus(String statusJson) throws ServerException {
        DtoStatusVacancyRequest dtoStatusRequest = gson.fromJson(statusJson, DtoStatusVacancyRequest.class);
        Vacancy vacancy;
        validateActivity(dtoStatusRequest.getToken());
        try {
            dtoStatusRequest.validate();
            vacancy = vacancyDaoImpl.setStatus(dtoStatusRequest.getToken(), dtoStatusRequest.getNamePost(), dtoStatusRequest.isStatus());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        return gson.toJson(new DtoVacancyResponse(vacancy.getName(), vacancy.getSalary(), vacancy.getObligatoryDemands(), vacancy.getNotObligatoryDemands(), vacancy.getToken(), vacancy.isStatus()));
    }

    public String setAccountStatus(String jsonStatus) throws ServerException {
        DtoToken dtoToken = gson.fromJson(jsonStatus, DtoToken.class);
        try {
            dtoToken.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoToken.getToken());
        employerDaoImpl.setAccountStatus(dtoToken.getToken(), false);
        return gson.toJson(new DtoTokenResponse(dtoToken.getToken()));
    }

    public String updateDemandsInVacancy(String newDemandJson) throws ServerException {
        DtoUpdateDemandRequest dtoNewDemand = gson.fromJson(newDemandJson, DtoUpdateDemandRequest.class);
        validateActivity(dtoNewDemand.getToken());
        try {
            dtoNewDemand.validate();
            vacancyDaoImpl.updateDemandInVacancy(new Requirement(dtoNewDemand.getNameDemand(), dtoNewDemand.getSkill(), dtoNewDemand.isNecessary()), dtoNewDemand.getToken(), dtoNewDemand.getNameVacancy(), dtoNewDemand.getOldNameDemand());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        return gson.toJson(new DtoDemandResponse(dtoNewDemand.getToken(), dtoNewDemand.getNameDemand(), dtoNewDemand.getSkill(), dtoNewDemand.isNecessary()));
    }

    public String updateEmployer(String tokenJson) throws ServerException {
        DtoUpdateEmployerRequest dtoUpdateFirstName = gson.fromJson(tokenJson, DtoUpdateEmployerRequest.class);
        validateActivity(dtoUpdateFirstName.getId());
        try {
            dtoUpdateFirstName.validate();
            employerDaoImpl.update(dtoUpdateFirstName.getId(), new Employer(dtoUpdateFirstName.getName(), dtoUpdateFirstName.getAddress(),
                    dtoUpdateFirstName.getEmail(), dtoUpdateFirstName.getId(),
                    dtoUpdateFirstName.getFirstName(), dtoUpdateFirstName.getPatronymic(),
                    dtoUpdateFirstName.getLastName(), dtoUpdateFirstName.getLogin(),
                    dtoUpdateFirstName.getPassword(), dtoUpdateFirstName.isActivity()));
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getErrorCode().getErrorCode()));
        }
        return gson.toJson(new DtoTokenResponse(dtoUpdateFirstName.getId()));
    }

    public String removeVacancy(String vacancyJson) throws ServerException {
        DtoRemoveVacancyRequest dtoRemoveVacancyRequest = gson.fromJson(vacancyJson, DtoRemoveVacancyRequest.class);
        validateActivity(dtoRemoveVacancyRequest.getToken());
        try {
            dtoRemoveVacancyRequest.validate();

        } catch (ServerException e) {
            return gson.toJson(new ErrorToken(e.getMessage()));
        }

        vacancyDaoImpl.delete(dtoRemoveVacancyRequest.getNamePost(), dtoRemoveVacancyRequest.getToken());
        return gson.toJson(new DtoTokenResponse(dtoRemoveVacancyRequest.getToken()));
    }

    public String removeAccountEmployer(String tokenJson) throws ServerException {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        validateActivity(dtoToken.getToken());
        try {
            dtoToken.validate();
            employerDaoImpl.removeAccount(dtoToken.getToken());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        return gson.toJson(new DtoTokenResponse(dtoToken.getToken()));
    }*/

    private DtoDemands convertDemands(String demandsJson) {
        return gson.fromJson(demandsJson, DtoDemands.class);
    }

    private void getClassInstanceFromJson(Class c, String json) throws ServerException {
        try {
            gson.fromJson(json, c);
        } catch (JsonSyntaxException ex) {
            throw new ServerException(ErrorCode.JSON_EXCEPTION);
        }
    }
}