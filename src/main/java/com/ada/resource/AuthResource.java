package com.ada.resource;

import com.ada.dto.LoginRequest;
import com.ada.dto.RegisterRequest;
import com.ada.entity.Usuario;
import com.ada.service.TokenService;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    TokenService tokenService;

    @POST
    @Path("/register")
    @Transactional
    public Response register(RegisterRequest request) {
        if (request.nome == null || request.email == null || request.senha == null || request.role == null) {
            return Response.status(400).entity(Map.of("erro", "Campos obrigatórios não informados")).build();
        }

        if (Usuario.findByEmail(request.email) != null) {
            return Response.status(409).entity(Map.of("erro", "E-mail já cadastrado")).build();
        }

        Usuario usuario = new Usuario();
        usuario.nome = request.nome;
        usuario.email = request.email;
        usuario.senha = BcryptUtil.bcryptHash(request.senha);
        usuario.role = request.role;
        usuario.persist();

        return Response.status(201).entity(Map.of(
                "id", usuario.id,
                "nome", usuario.nome,
                "email", usuario.email,
                "role", usuario.role
        )).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        if (request.email == null || request.senha == null) {
            return Response.status(400).entity(Map.of("erro", "Campos obrigatórios não informados")).build();
        }

        Usuario usuario = Usuario.findByEmail(request.email);
        if (usuario == null || !BcryptUtil.matches(request.senha, usuario.senha)) {
            return Response.status(401).entity(Map.of("erro", "E-mail ou senha inválidos")).build();
        }

        String token = tokenService.gerarToken(usuario.email, usuario.role);
        return Response.ok(Map.of(
                "token", token,
                "tipo", "Bearer",
                "role", usuario.role
        )).build();
    }
}

