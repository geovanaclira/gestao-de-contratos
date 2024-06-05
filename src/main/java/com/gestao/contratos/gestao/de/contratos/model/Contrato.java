package com.gestao.contratos.gestao.de.contratos.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numeroContrato;

    @Column(nullable = false)
    private String versao;

    @Column(nullable = false)
    private String codigoPOA; // Código alfanumérico para POA

    @Column(nullable = false)
    private String objeto;

    @Column(nullable = false)
    private String especificacao;

    @Column(nullable = false)
    private String unidadeMedida;

    @Column(nullable = false)
    private BigDecimal quantidade;

    @Column(nullable = false)
    private BigDecimal valorUnitario;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private String fornecedor;

    @Column(nullable = false)
    private String cnpjFornecedor;

    @Column(nullable = false)
    private String responsavelFornecedor;

    @Column(nullable = false)
    private String telefoneResponsavel;

    @Column(nullable = false)
    private String emailResponsavel;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataFim;

    @Column(nullable = false)
    private BigDecimal indiceReajuste;

    @Column(nullable = false)
    private String fiscalContratoNome;

    @Column(nullable = false)
    private String fiscalContratoMatricula;

    @Column(nullable = false)
    private String fiscalContratoTelefone;

    @Column(nullable = false)
    private String fiscalContratoEmail;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PeriodicidadePagamento periodicidadePagamento;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoGerencia tipoGerencia;
    // Getters and Setters omitted for brevity


}
