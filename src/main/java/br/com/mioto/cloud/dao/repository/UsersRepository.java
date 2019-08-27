package br.com.mioto.cloud.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mioto.cloud.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {} 


