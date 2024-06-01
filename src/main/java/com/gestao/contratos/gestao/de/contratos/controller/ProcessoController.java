package com.gestao.contratos.gestao.de.contratos.controller;

import com.gestao.contratos.gestao.de.contratos.model.Processo;
import com.gestao.contratos.gestao.de.contratos.repository.ProcessoRepository;
import com.gestao.contratos.gestao.de.contratos.service.ProcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/processos")
public class ProcessoController {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ProcessoService processoService;

    @PostMapping
    public ResponseEntity<Processo> createProcesso(@RequestBody Processo processo) {
        Processo savedProcesso = processoRepository.save(processo);
        processoService.handleProcessCreation(savedProcesso);
        return ResponseEntity.ok(savedProcesso);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Processo> getProcessoById(@PathVariable Long id) {
        return processoRepository.findById(id)
                .map(ResponseEntity::ok) // Garante que o método 'ok' seja chamado corretamente
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Processo>> getAllProcessos() {
        List<Processo> processos = processoRepository.findAll();
        return ResponseEntity.ok(processos); // Envolva a lista em uma ResponseEntity
    }
}
