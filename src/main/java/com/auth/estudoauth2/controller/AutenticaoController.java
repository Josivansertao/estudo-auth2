package com.auth.estudoauth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.auth.estudoauth2.config.tokem.AutenticaoCriarTokenServiceQuery;
import com.auth.estudoauth2.dto.AuthDto;

@RestController
@RequestMapping("/auth")
public class AutenticaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AutenticaoCriarTokenServiceQuery autenticaoCriarTokenServiceQuery;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String auth(@RequestBody AuthDto authDto) {

        UsernamePasswordAuthenticationToken usuatioToken = new UsernamePasswordAuthenticationToken(authDto.login(),
                authDto.senha());
        authenticationManager.authenticate(usuatioToken);

        return autenticaoCriarTokenServiceQuery.obterToken(authDto);
    }

}
