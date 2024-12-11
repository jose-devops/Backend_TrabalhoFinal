package com.api.app.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Table(name = "TB_CLIENTE")
@Data
public class ClienteModel {
    private  static final long serialVersionUID = 1;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String telefone;

    private String endereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference // Gerencia a serialização do lado do cliente
    private List<PedidoModel> pedidos = new ArrayList<>();
}
