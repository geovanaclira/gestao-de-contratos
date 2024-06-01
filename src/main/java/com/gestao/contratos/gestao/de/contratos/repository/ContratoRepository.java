package com.gestao.contratos.gestao.de.contratos.repository;


import com.gestao.contratos.gestao.de.contratos.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
}