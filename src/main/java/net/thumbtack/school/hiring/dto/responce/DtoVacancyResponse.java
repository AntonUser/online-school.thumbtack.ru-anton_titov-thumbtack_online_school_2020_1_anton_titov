package net.thumbtack.school.hiring.dto.responce;

import net.thumbtack.school.hiring.model.Demand;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DtoVacancyResponse {
    private String namePost;
    private int salary;
    private Map<String, Integer> obligatoryDemands;
    private Map<String, Integer> notObligatoryDemands;
    private String token;
    private boolean status;

    public DtoVacancyResponse(String namePost, int salary, Map<String, Integer> obligatoryDemands, Map<String, Integer> notObligatoryDemands, String token, boolean status) {
        this.namePost = namePost;
        this.salary = salary;
        this.obligatoryDemands = obligatoryDemands;
        this.notObligatoryDemands = notObligatoryDemands;
        this.token = token;
        this.status = status;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoVacancyResponse that = (DtoVacancyResponse) o;
        return salary == that.salary &&
                status == that.status &&
                Objects.equals(namePost, that.namePost) &&
                Objects.equals(obligatoryDemands, that.obligatoryDemands) &&
                Objects.equals(notObligatoryDemands, that.notObligatoryDemands) &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namePost, salary, obligatoryDemands, notObligatoryDemands, token, status);
    }
}
