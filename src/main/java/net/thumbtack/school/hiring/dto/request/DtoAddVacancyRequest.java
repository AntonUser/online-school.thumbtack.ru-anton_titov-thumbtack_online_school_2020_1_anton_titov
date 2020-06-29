package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ErrorStrings;
import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Demand;

import java.util.*;

public class DtoAddVacancyRequest {
    private String namePost;
    private int salary;
    private List<Demand> demands;
    private String token;

    public DtoAddVacancyRequest(String namePost, int salary, List<Demand> demands, String token) {
        this.namePost = namePost;
        this.salary = salary;
        this.demands = demands;
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

    public ArrayList<Demand> getDemands() {
        return new ArrayList<>(demands);
    }

    public Set<String> getNamesDemands() {
        Set<String> outSet = new HashSet<>();
        for (Demand demand : demands) {
            outSet.add(demand.getNameDemand());
        }
        return outSet;
    }

    public void setDemands(List<Demand> demands) {
        this.demands = demands;
    }

    public void validate() throws ServerException, NullPointerException {
        if (getNamePost()==null||getNamePost().isEmpty()) {
            throw new NullPointerException(ErrorStrings.NAME_POST_ERROR.getStringMessage());
        } else if (getSalary() < 0) {
            throw new ServerException(ErrorCode.SALARY_EXCEPTION);
        } else if (getToken()==null||getToken().isEmpty()) {
            throw new NullPointerException(ErrorStrings.TOKEN_ERROR.getStringMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoAddVacancyRequest that = (DtoAddVacancyRequest) o;
        return salary == that.salary &&
                Objects.equals(namePost, that.namePost) &&
                Objects.equals(demands, that.demands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namePost, salary, demands);
    }
}
