package com.jmarcostech.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmarcostech.cursomc.domain.ItemPedido;
import com.jmarcostech.cursomc.domain.PagamentoComBoleto;
import com.jmarcostech.cursomc.domain.Pedido;
import com.jmarcostech.cursomc.domain.enums.EstadoPagamento;
import com.jmarcostech.cursomc.repositories.ItemPedidoRepository;
import com.jmarcostech.cursomc.repositories.PagamentoRepository;
import com.jmarcostech.cursomc.repositories.PedidoRepository;
import com.jmarcostech.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidorepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired 
	private PagamentoRepository pagamentorepository;
	
	@Autowired 
	private ProdutoService produtoservice;
	
	@Autowired 
	private ItemPedidoRepository itempedidorepository;
	
	public Pedido find(Integer id) {	
		Optional<Pedido> cat = pedidorepository.findById(id);
		return cat.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
			
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);//inicia o pedido com id nulo
		obj.setData(new Date());//inicia o pedido com a data e hora atuais
		obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);//enquanto o método não for valido, pagamento pendente
		obj.getPagamento().setPedido(obj);//o pagamento deve conhecer seu objeto
		//se o pagamento for do tipo pagamento com boleto...
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getData());
		}
		//salvando o pedido
		obj = pedidorepository.save(obj);
		//salvando o pagamento
		pagamentorepository.save(obj.getPagamento());
		//salvando todos os itens recuperados de Produtos e ItemPedido
		for(ItemPedido ip: obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoservice.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		
		itempedidorepository.saveAll(obj.getItens());
		
		return obj;
	}
	
}
