package com.auth.estudoauth2.config.tokem;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.auth.estudoauth2.dto.AuthDto;

public interface AutenticaoCriarTokenServiceQuery extends UserDetailsService {

    public String obterToken(AuthDto authDto);

    public String validarToken(String token);
}
