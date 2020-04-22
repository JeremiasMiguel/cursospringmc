
package com.jeremiasmiguel.cursospringmc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jeremiasmiguel.cursospringmc.domain.Cliente;
import com.jeremiasmiguel.cursospringmc.domain.Pedido;

// indicando que a interface é um Repository, e extendendo a classe JpaRepository indicando que a interface
// manuseará a classe Categoria, com o identificador sendo um inteiro (atributo ID de pedido)
// com ele, é possível realizar operações de acesso a dados
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

	// Buscando pedidos por cliente específico
	@Transactional(readOnly=true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
	
}
