package br.com.mioto.cloud.vo;

public class ResponseTime implements Comparable<ResponseTime> {

    private String project;

    private Double mean;

    private Double average;

    private Double meanLastSevenDays;

    private Double averageLastSevenDays;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Double getMean() {
        return mean;
    }

    public void setMean(Double mean) {
        this.mean = mean;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Double getMeanLastSevenDays() {
        return meanLastSevenDays;
    }

    public void setMeanLastSevenDays(Double meanLastSevenDays) {
        this.meanLastSevenDays = meanLastSevenDays;
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
