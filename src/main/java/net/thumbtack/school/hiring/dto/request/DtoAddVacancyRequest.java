package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.model.Demand;

import java.util.*;

public class DtoAddVacancyRequest {
    private String namePost;
    private int salary;
    private ArrayList<Demand> demands;
    private String token;

    public DtoAddVacancyRequest(String namePost, int salary, ArrayList<Demand> demands, String token) {
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

    public void setDemands(ArrayList<Demand> demands) {
        this.demands = demands;
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
