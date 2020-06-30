package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.daoimpl.DemandSkillDao;
import net.thumbtack.school.hiring.daoimpl.EmployeeDao;
import net.thumbtack.school.hiring.daoimpl.EmployerDao;
import net.thumbtack.school.hiring.daoimpl.VacancyDao;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.responce.*;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Demand;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.Vacancy;

import java.util.UUID;

public class EmployerService {
    private EmployeeDao employeeDao;
    private Gson gson;
    private VacancyDao vacancyDao;
    private EmployerDao employerDao;
    private DemandSkillDao demandSkillDao;

    public EmployerService() {
        employerDao = new EmployerDao();
        employeeDao = new EmployeeDao();
        gson = new Gson();
        vacancyDao = new VacancyDao();
        demandSkillDao = new DemandSkillDao();
    }

    public String registerEmployer(String json) {
        EmployerDtoRegisterRequest employerDtoRegisterRequest = gson.fromJson(json, EmployerDtoRegisterRequest.class);
        Employer employer;
        try {
            employerDtoRegisterRequest.validate();
            employer = new Employer(employerDtoRegisterRequest.getName(), employerDtoRegisterRequest.getAddress(),
                    employerDtoRegisterRequest.getEmail(), UUID.randomUUID().toString(),
                    employerDtoRegisterRequest.getFirstName(), employerDtoRegisterRequest.getPatronymic(),
                    employerDtoRegisterRequest.getLastName(), employerDtoRegisterRequest.getLogin(),
                    employerDtoRegisterRequest.getPassword(), true);
            employerDao.save(employer);
        } catch (ServerException se) {
            return gson.toJson(new ErrorToken(se.getErrorCode().getErrorCode()));
        }
        return gson.toJson(new DtoRegisterResponse(employer.getId()));
    }

    public String loginEmployer(String json) {
        DtoLoginRequest dtoLoginRequest;
        String token;
        dtoLoginRequest = gson.fromJson(json, DtoLoginRequest.class);
        try {
            dtoLoginRequest.validate();
            token = employerDao.loginEmployer(dtoLoginRequest.getLogin(), dtoLoginRequest.getPassword());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getErrorCode().getErrorCode()));
        }
        return gson.toJson(new DtoLoginResponse(token));
    }

    public String addVacancy(String vacancyJson) throws ServerException {
        DtoAddVacancyRequest dtoAddVacancyRequest = gson.fromJson(vacancyJson, DtoAddVacancyRequest.class);
        try {
            dtoAddVacancyRequest.validate();
        } catch (ServerException e) {
            return gson.toJson(new ErrorToken(e.getMessage()));
        }
        validateActivity(dtoAddVacancyRequest.getToken());
        vacancyDao.save(new Vacancy(dtoAddVacancyRequest.getNamePost(), dtoAddVacancyRequest.getSalary(), dtoAddVacancyRequest.getDemands(), dtoAddVacancyRequest.getToken()));
        return gson.toJson(new DtoTokenResponse(dtoAddVacancyRequest.getToken()));
    }

    public String getEmployeeListNotLess(String demandsJson) throws ServerException {
        DtoDemands dtoDemands = convertDemands(demandsJson);
        try {
            dtoDemands.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoDemands.getToken());
        return gson.toJson(new DtoEmployeesResponse(employeeDao.getEmployeeListNotLessByDemand(dtoDemands.getDemands())));
    }

    public String getEmployeeListObligatoryDemand(String demandsJson) throws ServerException {
        DtoDemands dtoDemands = convertDemands(demandsJson);
        try {
            dtoDemands.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoDemands.getToken());
        return gson.toJson(new DtoEmployeesResponse(employeeDao.getEmployeeListObligatoryDemandByDemands(dtoDemands.getDemands())));
    }

    public String getEmployee(String demandsJson) throws ServerException {
        DtoDemands dtoDemands = convertDemands(demandsJson);
        try {
            dtoDemands.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoDemands.getToken());
        return gson.toJson(new DtoEmployeesResponse(employeeDao.getEmployeeListByDemands(dtoDemands.getDemands())));
    }

    public String getEmployeeWithOneDemand(String demandsJson) throws ServerException {
        DtoDemands dtoDemands = convertDemands(demandsJson);
        try {
            dtoDemands.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoDemands.getToken());
        return gson.toJson(new DtoEmployeesResponse(employeeDao.getEmployeeListWithOneDemandByDemands(dtoDemands.getDemands())));
    }

    public String getAllVacancies(String tokenJson) throws ServerException {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        try {
            dtoToken.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoToken.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDao.getVacanciesListByToken(dtoToken.getToken())));
    }

    public String getActivityVacancies(String tokenJson) throws ServerException {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        try {
            dtoToken.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoToken.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDao.getActivityVacanciesListByToken(dtoToken.getToken())));
    }

    public String getNotActivityVacancies(String tokenJson) throws ServerException {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        try {
            dtoToken.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoToken.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDao.getNotActivityVacanciesListByToken(dtoToken.getToken())));
    }

    public String setVacancyStatus(String statusJson) throws ServerException {
        DtoStatusVacancyRequest dtoStatusRequest = gson.fromJson(statusJson, DtoStatusVacancyRequest.class);
        Vacancy vacancy;
        validateActivity(dtoStatusRequest.getToken());
        try {
            dtoStatusRequest.validate();
            vacancy = vacancyDao.setStatus(dtoStatusRequest.getToken(), dtoStatusRequest.getNamePost(), dtoStatusRequest.isStatus());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        return gson.toJson(new DtoVacancyResponse(vacancy.getNamePost(), vacancy.getSalary(), vacancy.getDemands(), vacancy.getToken(), vacancy.isStatus()));
    }

    public String setAccountStatus(String jsonStatus) throws ServerException {
        DtoToken dtoToken = gson.fromJson(jsonStatus, DtoToken.class);
        try {
            dtoToken.validate();
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoToken.getToken());
        employerDao.setAccountStatus(dtoToken.getToken(), false);
        return gson.toJson(new DtoTokenResponse(dtoToken.getToken()));
    }

    public String updateDemandsInVacancy(String newDemandJson) throws ServerException {
        DtoUpdateDemandRequest dtoNewDemand = gson.fromJson(newDemandJson, DtoUpdateDemandRequest.class);
        validateActivity(dtoNewDemand.getToken());
        try {
            dtoNewDemand.validate();
            vacancyDao.updateDemandInVacancy(new Demand(dtoNewDemand.getNameDemand(), dtoNewDemand.getSkill(), dtoNewDemand.isNecessary()), dtoNewDemand.getToken(), dtoNewDemand.getNameVacancy(), dtoNewDemand.getOldNameDemand());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        return gson.toJson(new DtoDemandResponse(dtoNewDemand.getToken(), dtoNewDemand.getNameDemand(), dtoNewDemand.getSkill(), dtoNewDemand.isNecessary()));
    }

    public String updateEmployerFirstName(String tokenJson) throws ServerException {
        DtoUpdateFirstName dtoUpdateFirstName = gson.fromJson(tokenJson, DtoUpdateFirstName.class);
        validateActivity(dtoUpdateFirstName.getToken());
        try {
            dtoUpdateFirstName.validate();
            employerDao.updateEmployerFirstName(dtoUpdateFirstName.getToken(), dtoUpdateFirstName.getFirstName());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        return gson.toJson(new DtoTokenResponse(dtoUpdateFirstName.getToken()));
    }

    public String updateEmployerPatronymic(String tokenJson) throws ServerException {
        DtoUpdatePatronymic dtoUpdatePatronymic = gson.fromJson(tokenJson, DtoUpdatePatronymic.class);
        validateActivity(dtoUpdatePatronymic.getToken());
        try {
            dtoUpdatePatronymic.validate();
            employerDao.updateEmployerPatronymic(dtoUpdatePatronymic.getToken(), dtoUpdatePatronymic.getPatronymic());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        return gson.toJson(new DtoTokenResponse(dtoUpdatePatronymic.getToken()));
    }

    public String updateEmployerLastName(String tokenJson) throws ServerException {
        DtoUpdateLastName dtoUpdateLastName = gson.fromJson(tokenJson, DtoUpdateLastName.class);
        validateActivity(dtoUpdateLastName.getToken());
        try {
            dtoUpdateLastName.validate();
            employerDao.updateEmployerLastName(dtoUpdateLastName.getToken(), dtoUpdateLastName.getLastName());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        return gson.toJson(new DtoTokenResponse(dtoUpdateLastName.getToken()));
    }

    public String updateEmployerEmail(String tokenJson) throws ServerException {
        DtoUpdateEmail dtoUpdateEmail = gson.fromJson(tokenJson, DtoUpdateEmail.class);
        validateActivity(dtoUpdateEmail.getToken());
        try {
            dtoUpdateEmail.validate();
            employerDao.updateEmployerEmail(dtoUpdateEmail.getToken(), dtoUpdateEmail.getEmail());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        return gson.toJson(new DtoTokenResponse(dtoUpdateEmail.getToken()));
    }

    public String updateEmployerPassword(String tokenJson) throws ServerException {
        DtoUpdatePassword dtoUpdatePassword = gson.fromJson(tokenJson, DtoUpdatePassword.class);
        validateActivity(dtoUpdatePassword.getToken());
        try {
            dtoUpdatePassword.validate();
            employerDao.updateEmployerPassword(dtoUpdatePassword.getToken(), dtoUpdatePassword.getPassword());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        return gson.toJson(new DtoTokenResponse(dtoUpdatePassword.getToken()));
    }

    public String updateEmployerNameCompany(String tokenJson) throws ServerException {
        DtoUpdateNameCompany dtoUpdateNameCompany = gson.fromJson(tokenJson, DtoUpdateNameCompany.class);
        validateActivity(dtoUpdateNameCompany.getToken());
        try {
            dtoUpdateNameCompany.validate();
            employerDao.updateEmployerNameCompany(dtoUpdateNameCompany.getToken(), dtoUpdateNameCompany.getNameCompany());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        return gson.toJson(new DtoTokenResponse(dtoUpdateNameCompany.getToken()));
    }

    public String updateEmployerAddress(String tokenJson) throws ServerException {
        DtoUpdateAddress dtoUpdateAddress = gson.fromJson(tokenJson, DtoUpdateAddress.class);
        validateActivity(dtoUpdateAddress.getToken());
        try {
            dtoUpdateAddress.validate();
            employerDao.updateEmployerAddress(dtoUpdateAddress.getToken(), dtoUpdateAddress.getAddress());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        return gson.toJson(new DtoTokenResponse(dtoUpdateAddress.getToken()));
    }

    public String removeVacancy(String vacancyJson) throws ServerException {
        DtoRemoveVacancyRequest dtoRemoveVacancyRequest = gson.fromJson(vacancyJson, DtoRemoveVacancyRequest.class);
        validateActivity(dtoRemoveVacancyRequest.getToken());
        try {
            dtoRemoveVacancyRequest.validate();

        } catch (ServerException e) {
            return gson.toJson(new ErrorToken(e.getMessage()));
        }

        vacancyDao.delete(dtoRemoveVacancyRequest.getNamePost(), dtoRemoveVacancyRequest.getToken());
        return gson.toJson(new DtoTokenResponse(dtoRemoveVacancyRequest.getToken()));
    }

    public String removeAccountEmployer(String tokenJson) throws ServerException {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        validateActivity(dtoToken.getToken());
        try {
            dtoToken.validate();
            employerDao.removeAccount(dtoToken.getToken());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        return gson.toJson(new DtoTokenResponse(dtoToken.getToken()));
    }

    private DtoDemands convertDemands(String demandsJson) {
        return gson.fromJson(demandsJson, DtoDemands.class);
    }

    private void validateActivity(String token) throws ServerException {
        if (!employerDao.isActivity(token)) {
            throw new ServerException(ErrorCode.AUTHORIZATION_EXCEPTION);
        }
    }
}