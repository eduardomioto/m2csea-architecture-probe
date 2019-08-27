package br.com.mioto.cloud.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="user_roles")
public class UserRoles {

    @Column(name = "id_user")        
    private Long idUser;
    
    @Column(name = "id_role")
    private Long id_role;


}
