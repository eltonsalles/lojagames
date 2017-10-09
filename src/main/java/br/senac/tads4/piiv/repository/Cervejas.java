package br.senac.tads4.piiv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.senac.tads4.piiv.model.Cerveja;

@Repository
public interface Cervejas extends JpaRepository<Cerveja, Long> {

	public Optional<Cerveja> findBySkuIgnoreCase(String sku);
	
}
