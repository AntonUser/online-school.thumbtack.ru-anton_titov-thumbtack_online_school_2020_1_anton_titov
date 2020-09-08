package net.thumbtack.school.hiring.model;

import java.util.*;

//cделать 2 коллекции в одной обязательные требования в другой нет(коллекция пара-ключ)
public class Vacancy {
	// REVU просто name. И так ясно, чего
    private String namePost;
    private int salary;
    // REVU эти 2 map мне непонятны
    // что такое String и Integer ?
    // давайте в скайпе обсудим, можно голосом
    private Map<String, Integer> obligatoryDemands;
    private Map<String, Integer> notObligatoryDemands;
    // REVU а это что такое ?
    // токены только юзерам выдаются
    private String token;
    private boolean status;

    public Vacancy(String namePost, int salary, Map<String, Integer> obligatoryDemands, Map<String, Integer> notObligatoryDemands, String token) {
        this.namePost = namePost;
        this.salary = salary;
        this.token = token;
        this.status = true;
        this.obligatoryDemands = obligatoryDemands;
        this.notObligatoryDemands = notObligatoryDemands;
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

    public Map<String, Integer> getNotObligatoryDemands() {
        return notObligatoryDemands;
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
        if (newDemand.isNecessary()) {
            obligatoryDemands.remove(nameDemand, obligatoryDemands.get(nameDemand));
            obligatoryDemands.put(newDemand.getNameDemand(), newDemand.getSkill());
        } else {
            notObligatoryDemands.remove(nameDemand, notObligatoryDemands.get(nameDemand));
            notObligatoryDemands.put(newDemand.getNameDemand(), newDemand.getSkill());
        }
    }

    public Set<String> getNamesDemands() {
        Set<String> outSet = new HashSet<>();
        outSet.addAll(obligatoryDemands.keySet());
        outSet.addAll(notObligatoryDemands.keySet());
        return outSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return salary == vacancy.salary &&
                status == vacancy.status &&
                Objects.equals(namePost, vacancy.namePost) &&
                Objects.equals(obligatoryDemands, vacancy.obligatoryDemands) &&
                Objects.equals(notObligatoryDemands, vacancy.notObligatoryDemands) &&
                Objects.equals(token, vacancy.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namePost, salary, obligatoryDemands, notObligatoryDemands, token, status);
    }
}
