package com.jeremiasmiguel.cursospringmc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jeremiasmiguel.cursospringmc.domain.Cidade;

// indicando que a interface é um Repository, e extendendo a classe JpaRepository indicando que a interface
// manuseará a classe Cidade, com o identificador sendo um inteiro (atributo ID de cidade)
// com ele, é possível realizar operações de acesso a dados
@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

	@Transactional(readOnly=true)
	@Query("SELECT obj FROM Cidade obj WHERE obj.estado.id = :idEstado ORDER BY obj.nome")
	public List<Cidade> findCidades(@Param("idEstado") Integer idEstado);
	
}
