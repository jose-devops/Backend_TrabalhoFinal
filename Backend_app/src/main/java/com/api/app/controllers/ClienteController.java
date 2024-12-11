package com.api.app.controllers;

import com.api.app.models.ClienteModel;
import com.api.app.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Criar um novo cliente
    @PostMapping("/createClient")
    public ResponseEntity<?> criarCliente(@Valid @RequestBody ClienteModel cliente) {
        try {
            // Validação de CPF único
            if (clienteService.findByCpf(cliente.getCpf()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Erro: CPF já cadastrado!");
            }

            ClienteModel clienteCriado = clienteService.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao criar cliente: " + e.getMessage());
        }
    }



    @DeleteMapping("/deleteClient/{id}")
    public ResponseEntity<?> deletarCliente (@PathVariable UUID id) {
        if (clienteService.findById(id).isPresent()) {
            try {
                clienteService.deleteById(id);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro interno ao deletar cliente: " + e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
    }


    // Listar todos os clientes


    @GetMapping("/listClients")
    public List<ClienteModel> listar() {
        return clienteService.findAll();
    }



    // Buscar cliente por ID --NÃO TESTADOOO
    @GetMapping("/client/{id}")
    public ResponseEntity<ClienteModel> buscarPorId(@PathVariable UUID id) {
        Optional<ClienteModel> cliente = clienteService.findById(id);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }



    // Atualizar cliente existente
    @PutMapping("/updateClient/{id}")
    public ResponseEntity<?> salvarCliente (@PathVariable UUID id, @Valid @RequestBody ClienteModel clienteAtualizado) {
        Optional<ClienteModel> cliente = clienteService.findById(id);
        if (cliente.isPresent()) {
            try {
                ClienteModel clienteExistente = cliente.get();
                clienteAtualizado.setId(clienteExistente.getId());
                return ResponseEntity.ok(clienteService.save(clienteAtualizado));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro interno ao atualizar cliente: " + e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
    }


}
