package com.jeremiasmiguel.cursospringmc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jeremiasmiguel.cursospringmc.domain.Cidade;

// indicando que a interface é um Repository, e extendendo a classe JpaRepository indicando que a interface
// manuseará a classe Cidade, com o identificador sendo um inteiro (atributo ID de cidade)
// com ele, é possível realziar operações de acesso a dados
@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}
