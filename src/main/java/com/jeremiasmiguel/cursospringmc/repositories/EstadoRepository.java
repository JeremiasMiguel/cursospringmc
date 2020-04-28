package com.jeremiasmiguel.cursospringmc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jeremiasmiguel.cursospringmc.domain.Estado;

// indicando que a interface é um Repository, e extendendo a classe JpaRepository indicando que a interface
// manuseará a classe Estado, com o identificador sendo um inteiro (atributo ID de estado)
// com ele, é possível realizar operações de acesso a dados
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

	@Transactional(readOnly = true)
	List<Estado> findAllByOrderByNome();
	
}
