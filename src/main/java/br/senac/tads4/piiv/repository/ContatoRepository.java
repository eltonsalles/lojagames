package br.senac.tads4.piiv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.senac.tads4.piiv.model.Contato;
import br.senac.tads4.piiv.repository.helper.contato.ContatosQueries;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long>, ContatosQueries {

}
