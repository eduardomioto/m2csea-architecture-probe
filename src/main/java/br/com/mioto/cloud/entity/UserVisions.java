package br.com.mioto.cloud.entity;

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
@Table(name="user_visions")
public class UserVisions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")        
    private Long userId;

    @Column(name = "vision_id")
    private String visionId;

    @Column(name = "preference_order")
    private String preferenceOrder;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getVisionId() {
        return visionId;
    }

    public void setVisionId(String visionId) {
        this.visionId = visionId;
    }

    public String getPreferenceOrder() {
        return preferenceOrder;
    }

    public void setPreferenceOrder(String preferenceOrder) {
        this.preferenceOrder = preferenceOrder;
    }

    
}
