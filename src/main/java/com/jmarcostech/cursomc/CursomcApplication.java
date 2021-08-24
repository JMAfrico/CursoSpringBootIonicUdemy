package com.jmarcostech.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jmarcostech.cursomc.domain.Categoria;
import com.jmarcostech.cursomc.domain.Cidade;
import com.jmarcostech.cursomc.domain.Cliente;
import com.jmarcostech.cursomc.domain.Endereco;
import com.jmarcostech.cursomc.domain.Estado;
import com.jmarcostech.cursomc.domain.Pagamento;
import com.jmarcostech.cursomc.domain.PagamentoComBoleto;
import com.jmarcostech.cursomc.domain.PagamentoComCartao;
import com.jmarcostech.cursomc.domain.Pedido;
import com.jmarcostech.cursomc.domain.Produto;
import com.jmarcostech.cursomc.domain.enums.TipoCliente;
import com.jmarcostech.cursomc.domain.enums.EstadoPagamento;
import com.jmarcostech.cursomc.repositories.CategoriaRepository;
import com.jmarcostech.cursomc.repositories.CidadeRepository;
import com.jmarcostech.cursomc.repositories.ClienteRepository;
import com.jmarcostech.cursomc.repositories.EnderecoRepository;
import com.jmarcostech.cursomc.repositories.EstadoRepository;
import com.jmarcostech.cursomc.repositories.PagamentoRepository;
import com.jmarcostech.cursomc.repositories.PedidoRepository;
import com.jmarcostech.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository catrepo;
	@Autowired
	private ProdutoRepository prodrepo;
	@Autowired
	private EstadoRepository estadorepo;
	@Autowired
	private CidadeRepository cidaderepo;
	@Autowired
	private ClienteRepository clienterepo;
	@Autowired
	private EnderecoRepository enderecorepo;
	@Autowired
	private PedidoRepository pedidorepo;
	@Autowired
	private PagamentoRepository pagamentorepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//CATEGORIA COM PRODUTOS//
		
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		
		Produto p1 = new Produto(null,"Computador",2000.00);
		Produto p2 = new Produto(null,"Impressora",800.00);
		Produto p3 = new Produto(null,"Mouse",80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		catrepo.saveAll(Arrays.asList(cat1,cat2));
		prodrepo.saveAll(Arrays.asList(p1,p2,p3));
		
		//CIDADE COM ESTADOS//
		
		Estado est1= new Estado(null,"Minas gerais");
		Estado est2= new Estado(null,"São Paulo");
		
		Cidade cid1 = new Cidade(null,"Uberlândia", est1);
		Cidade cid2 = new Cidade(null,"São Paulo", est2);
		Cidade cid3 = new Cidade(null,"Campinas", est2);
		
		est1.getCidade().addAll(Arrays.asList(cid1));
		est2.getCidade().addAll(Arrays.asList(cid2,cid3));
		
		estadorepo.saveAll(Arrays.asList(est1,est2));
		cidaderepo.saveAll(Arrays.asList(cid1,cid2,cid3));
		
		//CLIENTES COM ENDERECOS
		
		Cliente cli1 = new Cliente(null,"Maria Silva","maria@gmail.com","36378912377",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("155466788","955884842"));
		
		Endereco e1 = new Endereco(null,"Rua Flores","300","Apto 303","Jardim","38859515",cli1,cid1);
		Endereco e2 = new Endereco(null,"Avenida Matos","105","Sala 800","Centro","99459445",cli1,cid2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienterepo.saveAll(Arrays.asList(cli1));
		enderecorepo.saveAll(Arrays.asList(e1,e2));
		
		//PEDIDOS E PAGAMENTOS
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null,sdf.parse("30/09/2017 10:32"), cli1,e1);
		Pedido ped2 = new Pedido(null,sdf.parse("10/10/2017 19:35"), cli1,e2);
		
		Pagamento pagt1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagt1);
		
		Pagamento pagt2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagt2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidorepo.saveAll(Arrays.asList(ped1,ped2));
		pagamentorepo.saveAll(Arrays.asList(pagt1,pagt2));
		
		
	}

}
