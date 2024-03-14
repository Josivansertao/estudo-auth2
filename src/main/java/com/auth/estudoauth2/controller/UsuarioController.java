package com.auth.estudoauth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.estudoauth2.dto.UsuarioDto;
import com.auth.estudoauth2.service.UsuarioSerice;

@RestController
@RequestMapping("/logins")
public class UsuarioController {

    @Autowired
    private UsuarioSerice usuarioSerice;

    @PostMapping
    public UsuarioDto salvar(@RequestBody UsuarioDto usuarioDto) {
        return usuarioSerice.salvar(usuarioDto);
    }

    @GetMapping
    public String ok() {
        return "ok";
    }

}
