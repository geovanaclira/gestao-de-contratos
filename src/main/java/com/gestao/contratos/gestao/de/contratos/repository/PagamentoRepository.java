package com.gestao.contratos.gestao.de.contratos.repository;

import com.gestao.contratos.gestao.de.contratos.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByContratoIdAndDataPagamentoIsNull(Long contratoId);
}
