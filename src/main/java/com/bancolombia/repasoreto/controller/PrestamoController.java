package com.bancolombia.repasoreto.controller;

import com.bancolombia.repasoreto.model.DTO.PrestamoDTO;
import com.bancolombia.repasoreto.model.Prestamo;
import com.bancolombia.repasoreto.service.PrestamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {
    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping("/solicitar")
    public Prestamo solicitarPrestamo(@RequestBody PrestamoDTO prestamoDTO) {
        return prestamoService.solicitarPrestamo(prestamoDTO);
    }

    @GetMapping("/historial/{clienteId}")
    public List<Prestamo> obtenerHistorial(@PathVariable Long clienteId) {
        return prestamoService.obtenerHistorialPrestamosPorCliente(clienteId);
    }

    @PutMapping("/{prestamoId}/aprobar")
    public ResponseEntity<String> aprobarPrestamo(@PathVariable Long prestamoId) {
        prestamoService.cambiarEstadoPrestamo(prestamoId, "Aprobado");
        return ResponseEntity.ok("Préstamo aprobado exitosamente.");
    }

    @PutMapping("/{prestamoId}/rechazar")
    public ResponseEntity<String> rechazarPrestamo(@PathVariable Long prestamoId) {
        prestamoService.cambiarEstadoPrestamo(prestamoId, "Rechazado");
        return ResponseEntity.ok("Préstamo rechazado exitosamente.");
    }

    // Consultar estado de un préstamo por ID
    @GetMapping("/{prestamoId}/estado")
    public ResponseEntity<String> consultarEstadoPrestamo(@PathVariable Long prestamoId) {
        String estado = prestamoService.obtenerEstadoPrestamo(prestamoId);
        return ResponseEntity.ok("El estado del préstamo es: " + estado);
    }

}