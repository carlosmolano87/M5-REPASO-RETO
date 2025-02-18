package com.bancolombia.repasoreto.repository;

import com.bancolombia.repasoreto.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByClienteId(Long clienteId);
    List<Prestamo> findTop3ByClienteIdOrderByFechaCreacionDesc(Long clienteId);

}
