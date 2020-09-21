package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.*;

public class DtoAddVacancyRequest {
    private String namePost;
    private int salary;
    private Map<String, Integer> obligatoryDemands;
    private Map<String, Integer> notObligatoryDemands;
    private String token;

    public DtoAddVacancyRequest(String namePost, int salary, Map<String, Integer> obligatoryDemands, Map<String, Integer> notObligatoryDemands, String token) {
        this.namePost = namePost;
        this.salary = salary;
        this.obligatoryDemands = obligatoryDemands;
        this.notObligatoryDemands = notObligatoryDemands;
        this.token = token;
    }

    public String getNamePost() {
        return namePost;
    }

    public void setNamePost(String namePost) {
        this.namePost = namePost;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getToken() {
        return token;
    }

    public Map<String, Integer> getObligatoryDemands() {
        return obligatoryDemands;
    }

    public void setObligatoryDemands(Map<String, Integer> obligatoryDemands) {
        this.obligatoryDemands = obligatoryDemands;
    }

    public Map<String, Integer> getNotObligatoryDemands() {
        return notObligatoryDemands;
    }

    public void setNotObligatoryDemands(Map<String, Integer> notObligatoryDemands) {
        this.notObligatoryDemands = notObligatoryDemands;
    }

    public void validate() throws ServerException, NullPointerException {
        if (getNamePost() == null || getNamePost().isEmpty()) {
            throw new ServerException(ErrorCode.NULL_NAME_POST_EXCEPTION);
        } else if (getSalary() < 0) {
            throw new ServerException(ErrorCode.SALARY_EXCEPTION);
        } else if (getToken() == null || getToken().isEmpty()) {
            throw new ServerException(ErrorCode.NULL_TOKEN_EXCEPTION);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoAddVacancyRequest that = (DtoAddVacancyRequest) o;
        return salary == that.salary &&
                Objects.equals(namePost, that.namePost) &&
                Objects.equals(obligatoryDemands, that.obligatoryDemands) &&
                Objects.equals(notObligatoryDemands, that.notObligatoryDemands) &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namePost, salary, obligatoryDemands, notObligatoryDemands, token);
    }
}
