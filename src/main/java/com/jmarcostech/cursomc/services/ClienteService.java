package com.jmarcostech.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmarcostech.cursomc.domain.Cidade;
import com.jmarcostech.cursomc.domain.Cliente;
import com.jmarcostech.cursomc.domain.Endereco;
import com.jmarcostech.cursomc.domain.enums.TipoCliente;
import com.jmarcostech.cursomc.dto.ClienteDTO;
import com.jmarcostech.cursomc.dto.ClienteNewDTO;
import com.jmarcostech.cursomc.repositories.ClienteRepository;
import com.jmarcostech.cursomc.repositories.EnderecoRepository;
import com.jmarcostech.cursomc.services.exceptions.DataIntegrityException;
import com.jmarcostech.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienterepository;
	
	@Autowired
	private EnderecoRepository enderecorepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> cliente = clienterepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Cliente: " + Cliente.class.getName()));
	
	}
	
	//SERVICE PARA SALVAR NOVO CLIENTE E NOVO ENDERECO
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = clienterepository.save(obj);
		enderecorepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	//SERVICE PARA ALTERAR CLIENTE SE ELE JÁ EXISTIR
	public Cliente update(Cliente obj) {
		Cliente newobj = find(obj.getId());
		updateData(newobj,obj);
		return clienterepository.save(newobj);
	}
		
	//SERVICE PARA DELETAR CLIENTE SE ELE SE ELE JÁ EXISTIR, PORÉM SEM PEDIDOS RELACIONADOS
	public void delete(Integer id) {
		find(id);
		try {
		clienterepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar cliente que possui pedidos efetuados");
		}
	}
		
	//SERVICE PARA LISTAR TODOS CLIENTES
	public List<Cliente> findAll() {
		return clienterepository.findAll();
	}
		
	//SERVICE PARA PAGINAR CLIENTES
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction),orderBy);
		return clienterepository.findAll(pageRequest);
	}
		
	//SERVICE QUE PASSA OS PARÂMETROS DA CATEGORIA, PARA CATEGORIADTO, PARA PODER SER VALIDADO PELO REQUEST
	public Cliente fromDTO(ClienteDTO objDTO) {
		//return new Cliente(objDTO.getId(),objDTO.getNome());
		return new Cliente(objDTO.getId(),objDTO.getNome(),objDTO.getEmail(),null,null);
	}
	
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null,objDTO.getNome(),objDTO.getEmail(),objDTO.getCpfOuCnpj(),TipoCliente.toEnum(objDTO.getTipo()));
		Cidade cid = new Cidade(objDTO.getCidadeId(),null,null);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(),objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(),cli,cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if(objDTO.getTelefone2()!= null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3()!= null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		
		return cli;
		
	}
		
	//SERVICE QUE PUXA OS DADOS ORIGINAIS DO BANCO QUE PODEM SER ALTERADOS
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());	
	}
}
