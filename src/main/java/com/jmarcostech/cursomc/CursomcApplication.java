package com.jmarcostech.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jmarcostech.cursomc.domain.Categoria;
import com.jmarcostech.cursomc.domain.Cidade;
import com.jmarcostech.cursomc.domain.Estado;
import com.jmarcostech.cursomc.domain.Produto;
import com.jmarcostech.cursomc.repositories.CategoriaRepository;
import com.jmarcostech.cursomc.repositories.CidadeRepository;
import com.jmarcostech.cursomc.repositories.EstadoRepository;
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
		
	}

}
