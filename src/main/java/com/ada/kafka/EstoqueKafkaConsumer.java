package com.ada.kafka;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class EstoqueKafkaConsumer {

    private static final Logger LOG = Logger.getLogger(EstoqueKafkaConsumer.class);

    @Incoming("estoque-baixo-in")
    public void consumir(String mensagem) {
        String[] partes = mensagem.split("\\|");
        String nomeProduto = partes[0];
        String estoque = partes.length > 1 ? partes[1] : "?";
        LOG.warnf("[ALERTA DE ESTOQUE] Produto '%s' com estoque baixo (%s unidades). E-mail de alerta enviado para estoque@empresa.com",
                nomeProduto, estoque);
    }
}

