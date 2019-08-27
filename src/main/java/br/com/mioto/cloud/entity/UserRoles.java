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


}
