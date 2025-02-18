package com.bancolombia.repasoreto.model.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PrestamoDTO {
    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto debe ser mayor que 0")
    private BigDecimal monto;

    @NotNull(message = "La tasa de interés no puede ser nula")
    @Positive(message = "La tasa de interés debe ser mayor que 0")
    private BigDecimal interes;

    @NotNull(message = "La duración en meses no puede ser nula")
    @Min(value = 1, message = "La duración del préstamo debe ser al menos de 1 mes")
    private int duracionMeses;

    @NotNull(message = "El ID del cliente no puede ser nulo")
    @Positive(message = "El ID del cliente debe ser un número positivo")
    private Long clienteId;

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getInteres() {
        return interes;
    }

    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public void setDuracionMeses(int duracionMeses) {
        this.duracionMeses = duracionMeses;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}