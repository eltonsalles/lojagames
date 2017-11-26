package br.senac.tads4.piiv.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.senac.tads4.piiv.model.Genero;
import br.senac.tads4.piiv.model.Jogo;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long> {

	@Query(value = "SELECT * FROM produto as p "
			+ "INNER JOIN tipo_console as c ON c.id = p.id_tipo_console "
			+ "INNER JOIN jogo as j ON j.id_produto = p.id "
			+ "WHERE j.id_genero = :genero and c.id = :console", nativeQuery = true)
	List<Jogo> findByGeneroAndTipoConsole(@Param("genero") Long genero, @Param("console") Long console);
	
	List<Jogo> findByGenero(Genero genero, Pageable limit);
}