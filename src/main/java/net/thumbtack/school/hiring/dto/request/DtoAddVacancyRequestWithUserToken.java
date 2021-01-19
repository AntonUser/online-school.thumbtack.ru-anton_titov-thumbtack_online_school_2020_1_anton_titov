package net.thumbtack.school.hiring.dto.request;

import java.util.List;

public class DtoAddVacancyRequestWithUserToken extends DtoAddVacancyRequest {
    private String userToken;

    public DtoAddVacancyRequestWithUserToken(String namePost, int salary, List<DtoRequirement> requirements, String userToken) {
        super(namePost, salary, requirements);
        this.userToken = userToken;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
