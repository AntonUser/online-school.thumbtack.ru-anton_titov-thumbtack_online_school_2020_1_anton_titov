package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.Dao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.Requirement;
import net.thumbtack.school.hiring.model.Vacancy;

public class EmployerDaoImpl implements Dao<Employer> {
    private DataBase dataBase;

    public EmployerDaoImpl() {
        this.dataBase = DataBase.getInstance();
    }

    public String registerEmployer(Employer employer) throws ServerException {
        return dataBase.registerUser(employer);
    }

    public String loginEmployer(String login, String password) throws ServerException {
        return dataBase.loginUser(login, password);
    }

    public void logOut(String token) {
        dataBase.logoutUser(token);
    }

    public void removeAccount(String token) throws ServerException {
        dataBase.removeAccount(token);
    }

    public Employer getEmployerById(String id) {
        return (Employer) dataBase.getUserById(id);
    }

    public void addVacancy(Vacancy vacancy, String token) {
        dataBase.addVacancy(vacancy, token);
    }

    public void removeVacancy(String name, String token) throws ServerException {
        dataBase.removeVacancy(name, token);
    }

    public void addRequirement(Requirement requirement, String token, String nameVacancy) throws ServerException {
        dataBase.addRequirementInVacancy(requirement, token, nameVacancy);
    }

    public void removeRequirement(String token, String nameVacancy, String nameRequirement) throws ServerException {
    dataBase.removeRequirementOfVacancy(token, nameVacancy, nameRequirement);
    }

    /*
    public Employer getByLoginAndPassword(String login, String password) throws ServerException {
        return dataBase.getEmployerByLoginAndPassword(login, password);
    }

    public Employer getById(String id) {
        return dataBase.getEmployerById(id);
    }

    public void setAccountStatus(String token, boolean status) throws ServerException {
        dataBase.setAccountEmployerStatus(token, status);
    }

    public boolean isActivity(String token) {
        return dataBase.isActivityEmployer(token);
    }


    public String loginEmployer(String login, String password) throws ServerException {
        return dataBase.loginEmployer(login, password);
    }

    public void removeAccount(String token) {
        dataBase.removeAccountEmployer(token);
    }
*/
    @Override
    public void save(Employer employer) throws ServerException {
        dataBase.saveUser(employer);
    }

    @Override
    public void update(String id, Employer newEmployer) throws ServerException {
//        dataBase.updateEmployer(id, newEmployer);
    }

    @Override
    public void delete(String id) {
//        dataBase.deleteEmployer(id);
    }
}