package net.thumbtack.school.hiring.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Vacancy {
    private String namePost;
    private int salary;
    private List<Demand> demands;
    private String token;
    private boolean status;

    public Vacancy(String namePost, int salary, List<Demand> demands, String token) {
        this.namePost = namePost;
        this.salary = salary;
        this.demands = demands;
        this.token = token;
        this.status = true;
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

    public void updateDemand(String nameDemand, Demand newDemand) {
        List<Demand> allDemands = getDemands();
        for (Demand demand : allDemands) {
            if (demand.getNameDemand().equals(nameDemand)) {
                allDemands.set(allDemands.indexOf(demand), newDemand);
                setDemands(allDemands);
                break;
            }
        }
    }

    public Set<String> getNamesDemands() {
        Set<String> outSet = new HashSet<>();
        for (Demand demand : demands) {
            outSet.add(demand.getNameDemand());
        }
        return outSet;
    }
}
