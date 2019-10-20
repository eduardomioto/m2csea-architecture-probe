package br.com.mioto.cloud.vo;

public class ComputationalResources implements Comparable<ComputationalResources> {

    private String microservice;
    private Double cpu;
    private Double ram;
    private Double io;
    private Double network;
    public String getMicroservice() {
        return microservice;
    }
    public void setMicroservice(String microservice) {
        this.microservice = microservice;
    }
    public Double getCpu() {
        return cpu;
    }
    public void setCpu(Double cpu) {
        this.cpu = cpu;
    }
    public Double getRam() {
        return ram;
    }
    public void setRam(Double ram) {
        this.ram = ram;
    }
    public Double getIo() {
        return io;
    }
    public void setIo(Double io) {
        this.io = io;
    }
    public Double getNetwork() {
        return network;
    }
    public void setNetwork(Double network) {
        this.network = network;
    }
    @Override
    public String toString() {
        return "ComputationalResources [microservice=" + microservice + ", cpu=" + cpu + ", ram=" + ram + ", io=" + io + ", network=" + network + "]";
    }

    @Override
    public int compareTo(ComputationalResources o) {

        if((cpu + ram) == (o.getCpu() + o.getRam())) {
            return 0;
        } else if((cpu + ram) > (o.getCpu() + o.getRam())) {
            return 1;
        } else {
            return -1;
        }
    }

}
