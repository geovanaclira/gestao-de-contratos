package com.gestao.contratos.gestao.de.contratos.service;

import com.gestao.contratos.gestao.de.contratos.model.Contrato;
import com.gestao.contratos.gestao.de.contratos.model.Notificacao;
import com.gestao.contratos.gestao.de.contratos.model.Pagamento;
import com.gestao.contratos.gestao.de.contratos.repository.NotificacaoRepository;
import com.gestao.contratos.gestao.de.contratos.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;

    public void verificarEEnviarNotificacoes(Contrato contrato) {
        verificarSolicitacaoAditivo(contrato);
        verificarSolicitacaoReajuste(contrato);
        verificarAtrasoPagamento(contrato);
    }

    private void verificarSolicitacaoAditivo(Contrato contrato) {
        Date quatroMesesAntes = new Date(contrato.getDataFim().getTime() - (4L * 30 * 24 * 60 * 60 * 1000)); // Aprox. 4 meses
        if (new Date().after(quatroMesesAntes)) {
            criarNotificacao(contrato, "Alerta: Solicitação de termo aditivo necessário em menos de 4 meses.");
        }
    }

    private void verificarSolicitacaoReajuste(Contrato contrato) {
        if (new Date().compareTo(contrato.getDataFim()) >= 0) {
            criarNotificacao(contrato, "Alerta: Prazo de solicitação de reajuste até o final da vigência do contrato.");
        }
    }

    private void verificarAtrasoPagamento(Contrato contrato) {
        List<Pagamento> pagamentosAtrasados = pagamentoRepository.findByContratoIdAndDataPagamentoIsNull(contrato.getId());
        for (Pagamento pagamento : pagamentosAtrasados) {
            if (new Date().after(pagamento.getDataVencimento())) {
                criarNotificacao(contrato, "Atraso no pagamento detectado. Vencimento: " + pagamento.getDataVencimento());
            }
        }
    }

    public void criarNotificacao(Contrato contrato, String mensagem) {
        Notificacao notificacao = new Notificacao();
        notificacao.setContrato(contrato);
        notificacao.setMensagem(mensagem);
        notificacao.setDataNotificacao(new Date());
        notificacaoRepository.save(notificacao);
    }
}
