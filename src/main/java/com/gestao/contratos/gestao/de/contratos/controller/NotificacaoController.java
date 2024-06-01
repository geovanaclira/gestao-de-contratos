package com.gestao.contratos.gestao.de.contratos.controller;

import com.gestao.contratos.gestao.de.contratos.model.Contrato;
import com.gestao.contratos.gestao.de.contratos.model.NotificacaoDTO;
import com.gestao.contratos.gestao.de.contratos.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @PostMapping("/criar")
    public ResponseEntity<?> criarNotificacao(@RequestBody NotificacaoDTO notificacaoDTO) {
        notificacaoService.criarNotificacao(notificacaoDTO.getContrato(), notificacaoDTO.getMensagem());
        return ResponseEntity.ok().build();
    }
}
