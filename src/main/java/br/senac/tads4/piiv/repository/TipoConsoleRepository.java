package br.senac.tads4.piiv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.senac.tads4.piiv.model.TipoConsole;

@Repository
public interface TipoConsoleRepository extends JpaRepository<TipoConsole, Long> {

}
