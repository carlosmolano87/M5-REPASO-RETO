package com.bancolombia.repasoreto.service;

import com.bancolombia.repasoreto.model.Cliente;
import com.bancolombia.repasoreto.model.Prestamo;
import com.bancolombia.repasoreto.repository.PrestamoRepository;
import com.bancolombia.repasoreto.repository.ClienteRepository;

import com.bancolombia.repasoreto.model.DTO.PrestamoDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrestamoService {
    private final PrestamoRepository prestamoRepository;
    private final ClienteRepository clienteRepository;

    public PrestamoService(PrestamoRepository prestamoRepository, ClienteRepository clienteRepository) {
        this.prestamoRepository = prestamoRepository;
        this.clienteRepository = clienteRepository;
    }

    public Prestamo solicitarPrestamo(PrestamoDTO prestamoDTO) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(prestamoDTO.getClienteId());
        if (clienteOpt.isEmpty()) {
            throw new RuntimeException("Cliente no encontrado");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setMonto(prestamoDTO.getMonto());
        prestamo.setInteres(prestamoDTO.getInteres());
        prestamo.setDuracionMeses(prestamoDTO.getDuracionMeses());
        prestamo.setEstado("Pendiente");
        prestamo.setCliente(clienteOpt.get());

        return prestamoRepository.save(prestamo);
    }

    public List<Prestamo> obtenerHistorialPrestamosPorCliente(Long clienteId) {
        return prestamoRepository.findByClienteId(clienteId);
    }

    @Transactional
    public void cambiarEstadoPrestamo(Long prestamoId, String nuevoEstado) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + prestamoId));

        if (!prestamo.getEstado().equalsIgnoreCase("Pendiente")) {
            throw new RuntimeException("El préstamo ya ha sido procesado.");
        }

        prestamo.setEstado(nuevoEstado);
        prestamo.setFechaActualizacion(LocalDateTime.now());
        prestamoRepository.save(prestamo);
    }

    // Obtener el estado de un préstamo específico
    public String obtenerEstadoPrestamo(Long prestamoId) {
        return prestamoRepository.findById(prestamoId)
                .map(Prestamo::getEstado)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + prestamoId));
    }

    public Optional<Prestamo> consultarPrestamoPorId(Long id) {
        return prestamoRepository.findById(id);
    }

    public List<PrestamoDTO> obtenerUltimosTresPrestamos(Long clienteId) {
        return prestamoRepository.findTop3ByClienteIdOrderByFechaCreacionDesc(clienteId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private PrestamoDTO convertirADTO(Prestamo prestamo) {
        PrestamoDTO dto = new PrestamoDTO();
        dto.setMonto(prestamo.getMonto());
        dto.setInteres(prestamo.getInteres());
        dto.setDuracionMeses(prestamo.getDuracionMeses());
        dto.setClienteId(prestamo.getCliente().getId());
        return dto;
    }
}