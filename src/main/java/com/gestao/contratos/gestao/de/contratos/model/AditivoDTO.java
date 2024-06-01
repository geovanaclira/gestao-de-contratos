package com.gestao.contratos.gestao.de.contratos.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class AditivoDTO {
    private Date novaDataFinal;
    private Integer novaQuantidade;
    private Double porcentagemQuantidade;  // Novo campo para porcentagem de alteração na quantidade
    private Date novaDataInicial;
    private boolean supressao;  // Indica se o aditivo é na verdade uma supressão


    // Getters e Setters
}
