package br.com.mioto.cloud.vo;

public class ResponseTime implements Comparable<ResponseTime> {

    private String project;

    private Double average;

    private Double averageLastSevenDays;

    private Double averageLastThirtyDays;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Double getAverageLastThirtyDays() {
        return averageLastThirtyDays;
    }

    public void setAverageLastThirtyDays(Double averageLastThirtyDays) {
        this.averageLastThirtyDays = averageLastThirtyDays;
    }

    public Double getAverageLastSevenDays() {
        return averageLastSevenDays;
    }

    public void setAverageLastSevenDays(Double averageLastSevenDays) {
        this.averageLastSevenDays = averageLastSevenDays;
    }

    @Override
    public int compareTo(ResponseTime o) {
        if(average == o.getAverage()) {
            return 0;
        } else if(average > o.getAverage()) {
            return 1;
        } else {
            return -1;
        }
    }

}
