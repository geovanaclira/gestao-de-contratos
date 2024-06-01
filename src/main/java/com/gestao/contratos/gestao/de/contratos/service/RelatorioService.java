package com.gestao.contratos.gestao.de.contratos.service;

import com.gestao.contratos.gestao.de.contratos.model.Contrato;
import com.gestao.contratos.gestao.de.contratos.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelatorioService {

    @Autowired
    private ContratoRepository contratoRepository;

    public Map<String, Object> getIndicadoresDetalhados() {
        List<Contrato> contratos = contratoRepository.findAll();
        BigDecimal totalExecutado = BigDecimal.ZERO;
        BigDecimal saldoTotal = BigDecimal.ZERO;
        int quantidadeContratosAtivos = 0;

        for (Contrato contrato : contratos) {
            if (contrato.getDataFim().after(new Date())) {
                quantidadeContratosAtivos++;
                BigDecimal valorExecutado = contrato.getValorTotal(); // Simplificação: considerando valor total como executado
                totalExecutado = totalExecutado.add(valorExecutado);
                BigDecimal saldo = contrato.getValorTotal().subtract(valorExecutado);
                saldoTotal = saldoTotal.add(saldo);
            }
        }

        Map<String, Object> indicadores = new HashMap<>();
        indicadores.put("quantidadeContratosAtivos", quantidadeContratosAtivos);
        indicadores.put("totalExecutado", totalExecutado);
        indicadores.put("saldoTotal", saldoTotal);
        return indicadores;
    }
}
