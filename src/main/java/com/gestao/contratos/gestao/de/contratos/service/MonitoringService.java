package com.gestao.contratos.gestao.de.contratos.service;

import com.gestao.contratos.gestao.de.contratos.model.Contrato;
import com.gestao.contratos.gestao.de.contratos.model.Pagamento;
import com.gestao.contratos.gestao.de.contratos.repository.ContratoRepository;
import com.gestao.contratos.gestao.de.contratos.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MonitoringService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private NotificacaoService notificacaoService;

    // Verifica a necessidade de renovação de contratos
    @Scheduled(fixedRate = 86400000) // Executa uma vez por dia
    public void checkForContractRenewal() {
        Date currentDate = new Date();
        List<Contrato> contratos = contratoRepository.findAll();
        for (Contrato contrato : contratos) {
            // Considerando 120 dias (aproximadamente 4 meses) como período crítico para renovação
            long daysBeforeExpiry = (contrato.getDataFim().getTime() - currentDate.getTime()) / (1000 * 60 * 60 * 24);
            if (daysBeforeExpiry <= 120) {
                notificacaoService.criarNotificacao(contrato, "O contrato está próximo de expirar e pode necessitar de renovação.");
            }
        }
    }

    @Scheduled(fixedRate = 86400000)
    public void checkForPaymentDelays() {
        Date currentDate = new Date();
        List<Contrato> contratos = contratoRepository.findAll();
        for (Contrato contrato : contratos) {
            List<Pagamento> pagamentos = pagamentoRepository.findByContratoIdAndDataPagamentoIsNull(contrato.getId());
            for (Pagamento pagamento : pagamentos) {
                if (pagamento.getDataVencimento() != null && pagamento.getDataVencimento().before(currentDate)) {
                    notificacaoService.criarNotificacao(pagamento.getContrato(), "Existe um atraso no pagamento do contrato.");
                }
            }
        }
    }
}
