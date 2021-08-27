package com.jmarcostech.cursomc.dto;

import java.io.Serializable;

import com.jmarcostech.cursomc.domain.Categoria;

//ESSA CLASSE É UTILIZADA PARA FILTRAR O QUE VAMOS MOSTRAR NA TELA
//ANTES DO MÉTODO REQUEST SER CHAMADO. 
//A ORDEM DE EXECUÇÃO É = CATEGORIASERVICE > CATEGORIA RESOURCE CHAMA O CATEGORIADTO, 
//QUE CHAMA O CATEGORIA, DEPOIS RETORNA PRO RESOURCE E EXECUTA O REQUEST
public class CategoriaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	

	private Integer id;
	
	private String nome;
	
	public CategoriaDTO() {
		// TODO Auto-generated constructor stub
	}
	
	//Busca os dados somenteda categoria
	public CategoriaDTO(Categoria obj) {
		id = obj.getId();
		nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
