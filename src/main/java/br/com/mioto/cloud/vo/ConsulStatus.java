package br.com.mioto.cloud.vo;

public class ConsulStatus implements Comparable<ConsulStatus>{

    private String name;
    private String status;
    private Integer downtimeOccurrences;

    public Integer getDowntimeOccurrences() {
        return downtimeOccurrences;
    }

    public void setDowntimeOccurrences(Integer downtimeOccurrences) {
        this.downtimeOccurrences = downtimeOccurrences;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ConsulStatus [name=" + name + ", status=" + status + ", downtimeOccurrences=" + downtimeOccurrences + "]";
    }

    @Override
    public int compareTo(ConsulStatus o) {
        if(downtimeOccurrences == o.getDowntimeOccurrences()) {
            return 0;
        } else if(downtimeOccurrences > o.getDowntimeOccurrences()) {
            return 1;
        } else {
            return -1;
        }
    }

}
