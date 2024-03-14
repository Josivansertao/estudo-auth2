package com.auth.estudoauth2.dto;

import com.auth.estudoauth2.model.Funcionario;

public record UsuarioDto(
                Funcionario funcionario,
                String login,
                String senha) {

}
