package net.thumbtack.school.hiring.dto.responce;

import net.thumbtack.school.hiring.model.Demand;

import java.util.List;

public class DtoVacancyResponse {
    private String namePost;
    private int salary;
    private List<Demand> demands;
    private String token;
    private boolean status;

    public DtoVacancyResponse(String namePost, int salary, List<Demand> demands, String token, boolean status) {
        this.namePost = namePost;
        this.salary = salary;
        this.demands = demands;
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

    public List<Demand> getDemands() {
        return demands;
    }

    public void setDemands(List<Demand> demands) {
        this.demands = demands;
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
}
