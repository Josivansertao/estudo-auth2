package com.auth.estudoauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.estudoauth2.dto.UsuarioDto;
import com.auth.estudoauth2.model.Login;
import com.auth.estudoauth2.security.UsuarioRepository;
import com.auth.estudoauth2.service.query.UsuarioServiceQuery;

@Service
public class UsuarioSerice implements UsuarioServiceQuery {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UsuarioDto salvar(UsuarioDto usuarioDto) {

        Login usuarioLogin = usuarioRepository.findByLogin(usuarioDto.login());

        if (usuarioLogin != null) {
            throw new RuntimeException("Usuário já existe");
        }

        String senhaHash = passwordEncoder.encode(usuarioDto.senha());

        var usuario = new Login(usuarioDto.funcionario(), usuarioDto.login(), senhaHash);
        usuario = usuarioRepository.save(usuario);
        return new UsuarioDto(usuario.getFuncionario(), usuario.getLogin(), usuario.getSenha());
    }

}
