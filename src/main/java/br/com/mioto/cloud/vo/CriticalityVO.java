package br.com.mioto.cloud.vo;

public class CriticalityVO {

    private String microservice;
    private String vision;
    private Integer criticalityFactor;
    private String value;

    public String getMicroservice() {
        return microservice;
    }
    public void setMicroservice(String microservice) {
        this.microservice = microservice;
    }
    public String getVision() {
        return vision;
    }
    public void setVision(String vision) {
        this.vision = vision;
    }
    public Integer getCriticalityFactor() {
        return criticalityFactor;
    }
    public void setCriticalityFactor(Integer criticalityFactor) {
        this.criticalityFactor = criticalityFactor;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }



}
