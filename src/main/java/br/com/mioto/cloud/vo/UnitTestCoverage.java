package br.com.mioto.cloud.vo;

public class UnitTestCoverage implements Comparable<UnitTestCoverage> {

    private String microservice;
    private Double coverage;

    public String getMicroservice() {
        return microservice;
    }
    public void setMicroservice(String microservice) {
        this.microservice = microservice;
    }
    public Double getCoverage() {
        return coverage;
    }
    public void setCoverage(Double coverage) {
        this.coverage = coverage;
    }

    @Override
    public String toString() {
        return "UnitTestCoverage [microservice=" + microservice + ", coverage=" + coverage + "]";
    }

    @Override
    public int compareTo(UnitTestCoverage o) {
        if(coverage == o.getCoverage()) {
            return 0;
        } else if(coverage > o.getCoverage()) {
            return 1;
        } else {
            return -1;
        }
    }

}
