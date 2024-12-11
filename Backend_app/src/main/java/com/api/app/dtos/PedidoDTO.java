package com.api.app.dtos;

import com.api.app.enums.PedidoStatus;
import com.api.app.models.PedidoModel;

import java.util.UUID;

public class PedidoDTO {

    private UUID id;
    private UUID clienteId;
    private String clienteNome;
    private String descricao;
    private Double valor;
    private PedidoStatus status;
    private String nomeCliente; // Novo campo


    public PedidoDTO(PedidoModel pedido) {
        this.id = pedido.getId();
        this.clienteId = pedido.getCliente().getId();
        this.clienteNome = pedido.getCliente().getNome();
        this.descricao = pedido.getDescricao();
        this.valor = pedido.getValor();
        this.status = pedido.getStatus();
        this.nomeCliente = pedido.getCliente() != null ? pedido.getCliente().getNome() : "N/A";

    }

    // Getters e Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public PedidoStatus getStatus() {
        return status;
    }

    public void setStatus(PedidoStatus status) {
        this.status = status;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
}
