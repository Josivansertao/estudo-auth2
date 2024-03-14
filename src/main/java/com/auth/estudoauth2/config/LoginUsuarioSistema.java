package com.auth.estudoauth2.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.auth.estudoauth2.model.Login;

public class LoginUsuarioSistema extends User {

    private Login login;

    public LoginUsuarioSistema(Login login, Collection<? extends GrantedAuthority> authorities) {
        super(login.getLogin(), login.getSenha(), authorities);
        this.login = login;
    }

    public Login getLogin() {
        return this.login;
    }

}
