package br.com.mioto.cloud.vo;

public class AgregattedSonarIssues {

    private String project;

    private Double debitInMinutes;

    private Double efforInMinutes;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Double getDebitInMinutes() {
        return debitInMinutes;
    }

    public void setDebitInMinutes(Double debitInMinutes) {
        this.debitInMinutes = debitInMinutes;
    }

    public Double getEfforInMinutes() {
        return efforInMinutes;
    }

    public void setEfforInMinutes(Double efforInMinutes) {
        this.efforInMinutes = efforInMinutes;
    }

}
