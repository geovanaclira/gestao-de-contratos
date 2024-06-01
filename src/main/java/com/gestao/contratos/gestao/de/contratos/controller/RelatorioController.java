package com.gestao.contratos.gestao.de.contratos.controller;

import com.gestao.contratos.gestao.de.contratos.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/indicadores")
    public ResponseEntity<Map<String, Object>> getIndicadores() {
        Map<String, Object> indicadores = relatorioService.getIndicadoresDetalhados();
        return ResponseEntity.ok(indicadores);
    }
}
