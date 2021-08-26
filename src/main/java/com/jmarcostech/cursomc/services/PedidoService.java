package com.jmarcostech.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmarcostech.cursomc.domain.Pedido;
import com.jmarcostech.cursomc.repositories.PedidoRepository;
import com.jmarcostech.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidorepository;
	
	public Pedido find(Integer id) {	
		Optional<Pedido> cat = pedidorepository.findById(id);
		return cat.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
			
	}
	
}
