package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ServerException;
import net.thumbtack.school.hiring.model.Demand;

import java.util.List;

public class DtoDemands {
    private String token;
    private List<Demand> demands;

    public DtoDemands(String token, List<Demand> demands) throws ServerException {
        this.token = token;
        this.demands = demands;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Demand> getDemands() {
        return demands;
    }

    public void setDemands(List<Demand> demands) {
        this.demands = demands;
    }
}
