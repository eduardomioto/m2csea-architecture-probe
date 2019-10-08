package br.com.mioto.cloud.vo;

public class ConsulHealthcheck implements Comparable<ConsulHealthcheck>{

    private String name;
    private Long checksPassing;
    private Long checksWarning;
    private Long checksCritical;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getChecksPassing() {
        return checksPassing;
    }
    public void setChecksPassing(Long checksPassing) {
        this.checksPassing = checksPassing;
    }
    public Long getChecksWarning() {
        return checksWarning;
    }
    public void setChecksWarning(Long checksWarning) {
        this.checksWarning = checksWarning;
    }
    public Long getChecksCritical() {
        return checksCritical;
    }
    public void setChecksCritical(Long checksCritical) {
        this.checksCritical = checksCritical;
    }

    @Override
    public String toString() {
        return "ConsulHealthcheck [name=" + name + ", checksPassing=" + checksPassing + ", checksWarning=" + checksWarning + ", checksCritical="
                + checksCritical + "]";
    }
    @Override
    public int compareTo(ConsulHealthcheck o) {
        if(checksCritical == o.getChecksCritical()) {
            return 0;
        } else if(checksCritical > o.getChecksCritical()) {
            return 1;
        } else {
            return -1;
        }
    }

}
