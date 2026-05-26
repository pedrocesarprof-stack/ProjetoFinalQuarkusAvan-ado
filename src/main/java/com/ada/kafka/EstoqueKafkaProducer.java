package com.ada.kafka;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class EstoqueKafkaProducer {

    @Channel("estoque-baixo-out")
    Emitter<String> emitter;

    public void enviarAlerta(String nomeProduto, int estoque) {
        String mensagem = nomeProduto + "|" + estoque;
        emitter.send(mensagem);
    }
}

