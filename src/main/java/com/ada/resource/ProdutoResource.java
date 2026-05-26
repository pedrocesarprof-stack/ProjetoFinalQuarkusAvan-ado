package com.ada.resource;

import com.ada.entity.Produto;
import com.ada.kafka.EstoqueKafkaProducer;
import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@Path("/produtos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Inject
    EstoqueKafkaProducer kafkaProducer;

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    @CacheResult(cacheName = "produtos")
    public List<Produto> listar() {
        return Produto.listAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"USER", "ADMIN"})
    @CacheResult(cacheName = "produtos")
    public Response buscarPorId(@PathParam("id") Long id) {
        Produto produto = Produto.findById(id);
        if (produto == null) {
            return Response.status(404).entity(Map.of("erro", "Produto não encontrado")).build();
        }
        return Response.ok(produto).build();
    }

    @POST
    @RolesAllowed("ADMIN")
    @Transactional
    @CacheInvalidateAll(cacheName = "produtos")
    public Response cadastrar(Produto produto) {
        if (produto.nome == null || produto.preco == null) {
            return Response.status(400).entity(Map.of("erro", "Campos obrigatórios não informados")).build();
        }
        produto.persist();

        if (produto.estoque < 5) {
            kafkaProducer.enviarAlerta(produto.nome, produto.estoque);
        }

        return Response.status(201).entity(produto).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    @Transactional
    @CacheInvalidateAll(cacheName = "produtos")
    public Response atualizar(@PathParam("id") Long id, Produto dados) {
        if (dados.nome == null || dados.preco == null) {
            return Response.status(400).entity(Map.of("erro", "Campos obrigatórios não informados")).build();
        }

        Produto produto = Produto.findById(id);
        if (produto == null) {
            return Response.status(404).entity(Map.of("erro", "Produto não encontrado")).build();
        }

        produto.nome = dados.nome;
        produto.descricao = dados.descricao;
        produto.preco = dados.preco;
        produto.estoque = dados.estoque;

        if (produto.estoque < 5) {
            kafkaProducer.enviarAlerta(produto.nome, produto.estoque);
        }

        return Response.ok(produto).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    @Transactional
    @CacheInvalidateAll(cacheName = "produtos")
    public Response deletar(@PathParam("id") Long id) {
        Produto produto = Produto.findById(id);
        if (produto == null) {
            return Response.status(404).entity(Map.of("erro", "Produto não encontrado")).build();
        }
        produto.delete();
        return Response.noContent().build();
    }
}

