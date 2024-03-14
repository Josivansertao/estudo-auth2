package com.auth.estudoauth2.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.estudoauth2.model.Login;

@Repository
public interface UsuarioRepository extends JpaRepository<Login, Long> {

    public Login findByLogin(String login);
}
