package com.jejr.usuario.infrastructure.repository;

import com.jejr.aprendendo_spring.infrastructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
