package com.jmarcostech.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmarcostech.cursomc.domain.Cliente;
import com.jmarcostech.cursomc.repositories.ClienteRepository;
import com.jmarcostech.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienterepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> cliente = clienterepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Cliente: " + Cliente.class.getName()));
	
	}
}
