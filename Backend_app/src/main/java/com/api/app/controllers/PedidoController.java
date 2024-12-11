package com.api.app.controllers;

import com.api.app.dtos.PedidoDTO;
import com.api.app.models.PedidoModel;
import com.api.app.services.PedidoService;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://127.0.0.1:5500") // Permitir apenas o frontend

public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/listOrders")
    public ResponseEntity<List<PedidoDTO>> listarPedidos() {
        List<PedidoDTO> pedidos = pedidoService.listarPedidos().stream()
                .map(PedidoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/getOrder/{id}")
    public ResponseEntity<PedidoDTO> buscarPedidoPorId(@PathVariable UUID id) {
        Optional<PedidoModel> pedido = pedidoService.buscarPorId(id);
        return pedido.map(value -> ResponseEntity.ok(new PedidoDTO(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/createOrder")
    public ResponseEntity<PedidoModel> criarPedido(@RequestBody PedidoModel pedido) {
        PedidoModel novoPedido = pedidoService.salvarPedido(pedido);
        return ResponseEntity.ok(novoPedido);
    }

    @PutMapping("/updateOrder/{id}")
    public ResponseEntity<PedidoModel> atualizarPedido(@PathVariable UUID id, @RequestBody PedidoModel pedidoAtualizado) {
        Optional<PedidoModel> pedidoExistente = pedidoService.buscarPorId(id);

        if (pedidoExistente.isPresent()) {
            PedidoModel pedido = pedidoExistente.get();
            pedido.setDescricao(pedidoAtualizado.getDescricao());
            pedido.setValor(pedidoAtualizado.getValor());
            pedido.setStatus(pedidoAtualizado.getStatus());
            PedidoModel pedidoAtualizadoResult = pedidoService.salvarPedido(pedido);
            return ResponseEntity.ok(pedidoAtualizadoResult);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteOrder/{id}")
    public ResponseEntity<?> deletarPedido(@PathVariable UUID id) {
        try {
            System.out.println("Recebida requisição para deletar pedido com ID: " + id);
            pedidoService.deletarPedido(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            System.err.println("Erro ao excluir pedido: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }




}
