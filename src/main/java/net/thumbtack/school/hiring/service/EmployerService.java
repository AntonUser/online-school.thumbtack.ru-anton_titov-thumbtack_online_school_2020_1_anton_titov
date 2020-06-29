package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.daoimpl.DemandSkillDao;
import net.thumbtack.school.hiring.daoimpl.EmployeeDao;
import net.thumbtack.school.hiring.daoimpl.EmployerDao;
import net.thumbtack.school.hiring.daoimpl.VacancyDao;
import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.responce.*;
import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ErrorStrings;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Demand;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.Vacancy;

import java.util.UUID;

//REVU: обычно методы распологают следующий образом:
// конструкторы, затем public и потом private
//// так удобнее читать код

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
        EmployerDtoRegisterRequest employerDtoRegisterRequest;
        Employer employer;
        employerDtoRegisterRequest = gson.fromJson(json, EmployerDtoRegisterRequest.class);
        try {
            employerDtoRegisterRequest.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        try {
            employer = new Employer(employerDtoRegisterRequest.getName(), employerDtoRegisterRequest.getAddress(),
                    employerDtoRegisterRequest.getEmail(), UUID.randomUUID().toString(),
                    employerDtoRegisterRequest.getFirstName(), employerDtoRegisterRequest.getPatronymic(),
                    employerDtoRegisterRequest.getLogin(), employerDtoRegisterRequest.getPassword(), true);
            employerDao.save(employer);
        } catch (ServerException se) {
            return gson.toJson(new ErrorToken(se.getErrorCode().getErrorCode()));
        }
        return gson.toJson(new DtoRegisterResponse(employer.getId()));
    }

    public String loginEmployer(String json) {
        DtoLoginRequest dtoLoginRequest;
        Employer employer;
        dtoLoginRequest = gson.fromJson(json, DtoLoginRequest.class);
        try {
            dtoLoginRequest.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }

        try {
            employer = employerDao.getByLoginAndPassword(dtoLoginRequest.getLogin(), dtoLoginRequest.getPassword());
        } catch (ServerException ex) {
            return gson.toJson(new ErrorToken(ex.getErrorCode().getErrorCode()));
        }
        employerDao.setAccountStatus(employer.getId(), true);
        return gson.toJson(new DtoLoginResponse(employer.getId()));
    }

    public String addVacancy(String vacancyJson) throws ServerException {
        DtoAddVacancyRequest dtoAddVacancyRequest = gson.fromJson(vacancyJson, DtoAddVacancyRequest.class);
        try {
            dtoAddVacancyRequest.validate();
        } catch (ServerException | NullPointerException e) {
            return gson.toJson(new ErrorToken(e.getMessage()));
        }
        validateActivity(dtoAddVacancyRequest.getToken());
        Vacancy vacancy = new Vacancy(dtoAddVacancyRequest.getNamePost(), dtoAddVacancyRequest.getSalary(), dtoAddVacancyRequest.getDemands(), dtoAddVacancyRequest.getToken());
        vacancyDao.save(vacancy);
        demandSkillDao.saveSubList(vacancy.getNamesDemands());
        return gson.toJson(new DtoTokenResponse(vacancy.getToken()));
    }

    public String getEmployeeListNotLess(String demandsJson) throws ServerException {
        DtoDemands dtoDemands = convertDemands(demandsJson);
        try {
            dtoDemands.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoDemands.getToken());
        return gson.toJson(new DtoEmployeesResponse(employeeDao.getEmployeeListNotLessByDemand(dtoDemands.getDemands())));
    }

    public String getEmployeeListObligatoryDemand(String demandsJson) throws ServerException {
        DtoDemands dtoDemands = convertDemands(demandsJson);
        try {
            dtoDemands.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoDemands.getToken());
        return gson.toJson(new DtoEmployeesResponse(employeeDao.getEmployeeListObligatoryDemandByDemands(dtoDemands.getDemands())));
    }

    public String getEmployee(String demandsJson) throws ServerException {
        DtoDemands dtoDemands = convertDemands(demandsJson);
        try {
            dtoDemands.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoDemands.getToken());
        return gson.toJson(new DtoEmployeesResponse(employeeDao.getEmployeeListByDemands(dtoDemands.getDemands())));
    }

    public String getEmployeeWithOneDemand(String demandsJson) throws ServerException {
        DtoDemands dtoDemands = convertDemands(demandsJson);
        try {
            dtoDemands.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoDemands.getToken());
        return gson.toJson(new DtoEmployeesResponse(employeeDao.getEmployeeListWithOneDemandByDemands(dtoDemands.getDemands())));
    }

    public String getAllVacancies(String tokenJson) throws ServerException {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        try {
            dtoToken.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoToken.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDao.getVacanciesListByToken(dtoToken.getToken())));
    }

    public String getActivityVacancies(String tokenJson) throws ServerException {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        try {
            dtoToken.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoToken.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDao.getActivityVacanciesListByToken(dtoToken.getToken())));
    }

    public String getNotActivityVacancies(String tokenJson) throws ServerException {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        try {
            dtoToken.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoToken.getToken());
        return gson.toJson(new DtoVacanciesResponse(vacancyDao.getNotActivityVacanciesListByToken(dtoToken.getToken())));
    }

    public String setVacancyStatus(String statusJson) throws ServerException {
        DtoStatusVacancyRequest dtoStatusRequest = gson.fromJson(statusJson, DtoStatusVacancyRequest.class);
        try {
            dtoStatusRequest.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoStatusRequest.getToken());
        Vacancy vacancy = vacancyDao.getVacancyByTokenAndName(dtoStatusRequest.getToken(), dtoStatusRequest.getNamePost());
        if (vacancy == null) {
            return gson.toJson(new ErrorToken(ErrorStrings.VACANCY_ERROR.getStringMessage()));
        }
        vacancy.setStatus(dtoStatusRequest.isStatus());
        vacancyDao.update(vacancy, vacancy);
        return gson.toJson(new DtoVacancyResponse(vacancy.getNamePost(), vacancy.getSalary(), vacancy.getDemands(), vacancy.getToken(), vacancy.isStatus()));
    }

    public String setAccountStatus(String jsonStatus) throws ServerException {
        DtoToken dtoToken = gson.fromJson(jsonStatus, DtoToken.class);
        try {
            dtoToken.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoToken.getToken());
        employerDao.setAccountStatus(dtoToken.getToken(), false);
        return gson.toJson(new DtoTokenResponse(dtoToken.getToken()));
    }

    public String updateDemandsInVacancy(String newDemandJson) throws ServerException {
        Vacancy vacancy;
        DtoUpdateDemandRequest dtoNewDemand = gson.fromJson(newDemandJson, DtoUpdateDemandRequest.class);
        try {
            dtoNewDemand.validate();
        } catch (ServerException | NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoNewDemand.getToken());
        Demand newDemand = new Demand(dtoNewDemand.getNameDemand(), dtoNewDemand.getSkill(), dtoNewDemand.isNecessary());
        vacancy = vacancyDao.getVacancyByTokenAndName(dtoNewDemand.getToken(), dtoNewDemand.getNameVacancy());
        vacancy.updateDemand(dtoNewDemand.getOldNameDemand(), newDemand);
        return gson.toJson(new DtoDemandResponse(vacancy.getToken(), newDemand.getNameDemand(), newDemand.getSkill(), newDemand.isNecessary()));
    }

    public String removeVacancy(String vacancyJson) throws ServerException {
        DtoRemoveVacancyRequest dtoRemoveVacancyRequest = gson.fromJson(vacancyJson, DtoRemoveVacancyRequest.class);
        try {
            dtoRemoveVacancyRequest.validate();
        } catch (ServerException | NullPointerException e) {
            return gson.toJson(new ErrorToken(e.getMessage()));
        }
        validateActivity(dtoRemoveVacancyRequest.getToken());
        Vacancy vacancy = new Vacancy(dtoRemoveVacancyRequest.getNamePost(), dtoRemoveVacancyRequest.getSalary(), dtoRemoveVacancyRequest.getDemands(), dtoRemoveVacancyRequest.getToken());
        vacancyDao.delete(vacancy);
        return gson.toJson(new DtoTokenResponse(vacancy.getToken()));
    }

    public String removeAccountEmployer(String tokenJson) throws ServerException {
        DtoToken dtoToken = gson.fromJson(tokenJson, DtoToken.class);
        try {
            dtoToken.validate();
        } catch (NullPointerException ex) {
            return gson.toJson(new ErrorToken(ex.getMessage()));
        }
        validateActivity(dtoToken.getToken());
        vacancyDao.removeAllVacanciesByToken(dtoToken.getToken());
        employerDao.delete(employerDao.getById(dtoToken.getToken()));
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