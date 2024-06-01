package com.gestao.contratos.gestao.de.contratos.service;

import com.gestao.contratos.gestao.de.contratos.model.Processo;
import com.gestao.contratos.gestao.de.contratos.repository.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.gestao.contratos.gestao.de.contratos.model.TipoProcesso.CELEBRACAO;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;

    public void handleProcessCreation(Processo processo) {
        switch (processo.getTipoProcesso()) {
            case CELEBRACAO:
                // Lógica específica para celebração
                break;
            case ADITIVO_OU_SUPRESSAO:
                // Verificar se é necessário notificar sobre a proximidade do fim da vigência
                break;
            case PAGAMENTO:
                // Verificar condições de pagamento
                break;
            // Adicionar outros casos conforme necessário
        }
    }
}
