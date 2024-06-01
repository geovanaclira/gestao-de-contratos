package com.gestao.contratos.gestao.de.contratos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Contrato contrato;

    private Date dataVencimento;
    private Date dataPagamento;
    private BigDecimal valorPago;

    // Getters and Setters omitted for brevity
}
