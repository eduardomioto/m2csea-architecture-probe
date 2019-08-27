package br.com.mioto.cloud.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="user_roles")
public class UserRoles implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 580715684020093151L;

    @Id
    @Column(name = "id_user")        
    private Long idUser;
    
    @Id
    @Column(name = "id_role")
    private Long id_role;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getId_role() {
        return id_role;
    }

    public void setId_role(Long id_role) {
        this.id_role = id_role;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    
}
