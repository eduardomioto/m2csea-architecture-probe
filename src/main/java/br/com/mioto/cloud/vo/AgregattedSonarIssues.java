package br.com.mioto.cloud.vo;

public class AgregattedSonarIssues implements Comparable<AgregattedSonarIssues> {

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




    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((debitInMinutes == null) ? 0 : debitInMinutes.hashCode());
        result = (prime * result) + ((efforInMinutes == null) ? 0 : efforInMinutes.hashCode());
        result = (prime * result) + ((project == null) ? 0 : project.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AgregattedSonarIssues other = (AgregattedSonarIssues) obj;
        if (debitInMinutes == null) {
            if (other.debitInMinutes != null) {
                return false;
            }
        } else if (!debitInMinutes.equals(other.debitInMinutes)) {
            return false;
        }
        if (efforInMinutes == null) {
            if (other.efforInMinutes != null) {
                return false;
            }
        } else if (!efforInMinutes.equals(other.efforInMinutes)) {
            return false;
        }
        if (project == null) {
            if (other.project != null) {
                return false;
            }
        } else if (!project.equals(other.project)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(AgregattedSonarIssues o) {
        return getEfforInMinutes().compareTo(o.getEfforInMinutes());
    }

}
