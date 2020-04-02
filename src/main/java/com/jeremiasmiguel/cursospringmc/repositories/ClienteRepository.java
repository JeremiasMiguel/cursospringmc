package com.jeremiasmiguel.cursospringmc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jeremiasmiguel.cursospringmc.domain.Cliente;

// indicando que a interface é um Repository, e extendendo a classe JpaRepository indicando que a interface
// manuseará a classe Cliente, com o identificador sendo um inteiro (atributo ID de cliente)
// com ele, é possível realizar operações de acesso a dados
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
