package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.exception.ErrorCode;
import net.thumbtack.school.hiring.exception.ServerException;

import java.util.List;

public class DtoDemands {
    private String token;
    private List<RequirementDtoRequest> demands;

    public DtoDemands(String token, List<RequirementDtoRequest> demands) {
        this.token = token;
        this.demands = demands;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<RequirementDtoRequest> getDemands() {
        return demands;
    }

    public void setDemands(List<RequirementDtoRequest> demands) {
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
