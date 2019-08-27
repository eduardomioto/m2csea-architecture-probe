package br.com.mioto.cloud.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="visions")
public class Visions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vision_id")
    private Long visionId;

    @Column(name = "vision_name")
    private String visionName;

    @Column(name = "vision_desc")
    private Date visionDesc;

    public Long getVisionId() {
        return visionId;
    }

    public void setVisionId(Long visionId) {
        this.visionId = visionId;
    }

    public String getVisionName() {
        return visionName;
    }

    public void setVisionName(String visionName) {
        this.visionName = visionName;
    }

    public Date getVisionDesc() {
        return visionDesc;
    }

    public void setVisionDesc(Date visionDesc) {
        this.visionDesc = visionDesc;
    }

    
}
