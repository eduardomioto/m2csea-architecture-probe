package br.com.mioto.cloud.vo;

public class SonarIssues {

    private String project;

    private String debt;

    private Double debitInMinutes;

    private String effort;

    private Double efforInMinutes;

    private String severity;

    private String status;

    private String message;

    private String type;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

    public Double getDebitInMinutes() {
        return debitInMinutes;
    }

    public void setDebitInMinutes(Double debitInMinutes) {
        this.debitInMinutes = debitInMinutes;
    }

    public String getEffort() {
        return effort;
    }

    public void setEffort(String effort) {
        this.effort = effort;
    }

    public Double getEfforInMinutes() {
        return efforInMinutes;
    }

    public void setEfforInMinutes(Double efforInMinutes) {
        this.efforInMinutes = efforInMinutes;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
