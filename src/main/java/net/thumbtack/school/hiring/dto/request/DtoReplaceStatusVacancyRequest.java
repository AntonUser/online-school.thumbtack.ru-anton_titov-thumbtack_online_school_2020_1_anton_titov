package net.thumbtack.school.hiring.dto.request;

public class DtoReplaceStatusVacancyRequest {
    private String idVacancy;
    private String tokenEmployer;

    public DtoReplaceStatusVacancyRequest(String idVacancy, String tokenEmployer) {
        this.idVacancy = idVacancy;
        this.tokenEmployer = tokenEmployer;
    }

    public String getIdVacancy() {
        return idVacancy;
    }

    public void setIdVacancy(String idVacancy) {
        this.idVacancy = idVacancy;
    }

    public String getTokenEmployer() {
        return tokenEmployer;
    }

    public void setTokenEmployer(String tokenEmployer) {
        this.tokenEmployer = tokenEmployer;
    }
}
