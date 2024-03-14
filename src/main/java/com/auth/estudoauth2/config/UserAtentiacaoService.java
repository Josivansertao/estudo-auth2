package com.auth.estudoauth2.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.estudoauth2.config.tokem.AutenticaoCriarTokenServiceQuery;
import com.auth.estudoauth2.dto.AuthDto;
import com.auth.estudoauth2.model.Login;
import com.auth.estudoauth2.security.UsuarioRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class UserAtentiacaoService implements AutenticaoCriarTokenServiceQuery {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login usuario = usuarioRepository.findByLogin(username);
        if (usuario == null) {
            throw new RuntimeException("Usuário ou senha inválido!");
        }
        return new LoginUsuarioSistema(usuario, permissoes(usuario));
    }

    private Collection<? extends GrantedAuthority> permissoes(Login login) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        login.getPermissoes().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
        return authorities;
    }

    @Override
    public String obterToken(AuthDto authDto) {
        Login usuario = usuarioRepository.findByLogin(authDto.login());
        return gerarTokenJwt(usuario);
    }

    public String gerarTokenJwt(Login login) {

        try {

            Algorithm algorithm = Algorithm.HMAC256("my-secret");
            String token = JWT.create()
                    .withIssuer("oficina-amigo-api")
                    .withSubject(login.getLogin())
                    .withExpiresAt(gerarHoraExpiracaoToken())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException ex) {
            throw new RuntimeException("Erro ao gerar o token: " + ex.getMessage());
        }
    }

    private Instant gerarHoraExpiracaoToken() {
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret");
            return JWT.require(algorithm)
                    .withIssuer("oficina-amigo-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            return "";
        }
    }

}
