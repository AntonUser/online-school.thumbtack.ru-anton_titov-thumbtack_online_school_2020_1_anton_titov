package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.List;

public class DtoRequirements {
    private String token;
    private List<DtoRequirement> demands;

    public DtoRequirements(String token, List<DtoRequirement> demands) {
        this.token = token;
        this.demands = demands;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<DtoRequirement> getDemands() {
        return demands;
    }

    public void setDemands(List<DtoRequirement> demands) {
        this.demands = demands;
    }

    public void validate() throws ServerException {
        if (getDemands().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_DEMANDS_LIST);
        } else if (getToken() == null || getToken().isEmpty()) {
            throw new ServerException(ErrorCode.EMPTY_TOKEN);
        }
    }
}
