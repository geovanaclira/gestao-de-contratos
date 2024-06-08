package com.gestao.contratos.gestao.de.contratos.controller;

import com.gestao.contratos.gestao.de.contratos.model.AditivoDTO;
import com.gestao.contratos.gestao.de.contratos.model.Contrato;
import com.gestao.contratos.gestao.de.contratos.repository.ContratoRepository;
import com.gestao.contratos.gestao.de.contratos.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.ReactiveSortHandlerMethodArgumentResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/contratos")
public class ContratoController {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private NotificacaoService notificacaoService;

    @PostMapping
    public ResponseEntity<Contrato> createContrato(@RequestBody Contrato contrato) {
        contratoRepository.save(contrato);
        // Adicionar lógica para notificação automática de termo aditivo
        notificacaoService.verificarEEnviarNotificacoes(contrato);
        return ResponseEntity.ok(contrato);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contrato> getContratoById(@PathVariable Long id) {
        return contratoRepository.findById(id)
                .map(contrato -> ResponseEntity.ok(contrato))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Contrato>> getAllContratos() {
        List<Contrato> contratos = contratoRepository.findAll();
        if (contratos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(contratos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contrato> updateContrato(@PathVariable Long id, @RequestBody Contrato novoContrato) {
        return contratoRepository.findById(id)
                .map(contratoExistente -> {
                    contratoExistente.setNumeroContrato(novoContrato.getNumeroContrato());
                    contratoExistente.setVersao(novoContrato.getVersao());
                    contratoExistente.setCodigoPOA(novoContrato.getCodigoPOA());
                    contratoExistente.setObjeto(novoContrato.getObjeto());
                    contratoExistente.setEspecificacao(novoContrato.getEspecificacao());
                    contratoExistente.setUnidadeMedida(novoContrato.getUnidadeMedida());
                    contratoExistente.setQuantidade(novoContrato.getQuantidade());
                    contratoExistente.setValorUnitario(novoContrato.getValorUnitario());
                    contratoExistente.setValorTotal(novoContrato.getValorTotal());
                    contratoExistente.setFornecedor(novoContrato.getFornecedor());
                    contratoExistente.setCnpjFornecedor(novoContrato.getCnpjFornecedor());
                    contratoExistente.setResponsavelFornecedor(novoContrato.getResponsavelFornecedor());
                    contratoExistente.setTelefoneResponsavel(novoContrato.getTelefoneResponsavel());
                    contratoExistente.setEmailResponsavel(novoContrato.getEmailResponsavel());
                    contratoExistente.setDataInicio(novoContrato.getDataInicio());
                    contratoExistente.setDataFim(novoContrato.getDataFim());
                    contratoExistente.setIndiceReajuste(novoContrato.getIndiceReajuste());  // Atribuição do valor Enum
                    contratoExistente.setFiscalContratoNome(novoContrato.getFiscalContratoNome());
                    contratoExistente.setFiscalContratoMatricula(novoContrato.getFiscalContratoMatricula());
                    contratoExistente.setFiscalContratoTelefone(novoContrato.getFiscalContratoTelefone());
                    contratoExistente.setFiscalContratoEmail(novoContrato.getFiscalContratoEmail());
                    contratoExistente.setPeriodicidadePagamento(novoContrato.getPeriodicidadePagamento());
                    contratoExistente.setTipoGerencia(novoContrato.getTipoGerencia());

                    contratoRepository.save(contratoExistente);
                    return ResponseEntity.ok(contratoExistente);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContrato(@PathVariable Long id) {
        return contratoRepository.findById(id)
                .map(contrato -> {
                    contratoRepository.delete(contrato);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/aditivo")
    public ResponseEntity<?> adicionarAditivo(@PathVariable Long id, @RequestBody AditivoDTO aditivoDTO) {
        return contratoRepository.findById(id)
                .map(contrato -> {
                    if (aditivoDTO.getNovaDataInicial() != null) {
                        contrato.setDataInicio(aditivoDTO.getNovaDataInicial());
                    }
                    if (aditivoDTO.getNovaDataFinal() != null) {
                        contrato.setDataFim(aditivoDTO.getNovaDataFinal());
                    }

                    if (aditivoDTO.getPorcentagemQuantidade() != null) {
                        BigDecimal porcentagemQuantidade = BigDecimal.valueOf(aditivoDTO.getPorcentagemQuantidade());
                        BigDecimal baseQuantidade = contrato.getQuantidade();
                        BigDecimal alteracaoQuantidade = baseQuantidade.multiply(porcentagemQuantidade.divide(BigDecimal.valueOf(100)));

                        if (porcentagemQuantidade.compareTo(BigDecimal.valueOf(25.0)) > 0) {
                            return ResponseEntity.badRequest().body("A alteração percentual não pode exceder 25%");
                        }

                        if (aditivoDTO.isSupressao()) {
                            contrato.setQuantidade(baseQuantidade.subtract(alteracaoQuantidade));
                        } else {
                            contrato.setQuantidade(baseQuantidade.add(alteracaoQuantidade));
                        }
                    }

                    contratoRepository.save(contrato);
                    return ResponseEntity.ok(contrato);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
