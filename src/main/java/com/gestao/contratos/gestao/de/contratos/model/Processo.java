package com.gestao.contratos.gestao.de.contratos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoProcesso tipoProcesso;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataProcesso;

    @ManyToOne
    private Contrato contrato;

    private String detalhes;

    // Getters and Setters
}
