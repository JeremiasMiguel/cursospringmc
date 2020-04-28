package com.jeremiasmiguel.cursospringmc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jeremiasmiguel.cursospringmc.domain.Cidade;
import com.jeremiasmiguel.cursospringmc.domain.Estado;
import com.jeremiasmiguel.cursospringmc.dto.CidadeDTO;
import com.jeremiasmiguel.cursospringmc.dto.EstadoDTO;
import com.jeremiasmiguel.cursospringmc.services.CidadeService;
import com.jeremiasmiguel.cursospringmc.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {

	@Autowired
	EstadoService estadoService;
	@Autowired
	CidadeService cidadeService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() { 
		List<Estado> listaEstados = estadoService.findAll();
		List<EstadoDTO> listaEstadosDTO = listaEstados.stream().map(objetoEstado -> new EstadoDTO(objetoEstado)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaEstadosDTO);
	}
	
	@RequestMapping(value="/{estadoId}/cidades", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findByEstado(@PathVariable Integer estadoId) { // PathVariable -> Variável que está presente no ENDPOINT
		List<Cidade> listaCidades = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> listaCidadesDTO = listaCidades.stream().map(objetoCidade -> new CidadeDTO(objetoCidade)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaCidadesDTO);
	}
	
}
