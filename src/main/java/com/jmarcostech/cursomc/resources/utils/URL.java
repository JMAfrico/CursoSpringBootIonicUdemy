package com.jmarcostech.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


public class URL {

	public static List<Integer> DecoderIntList(String s){
		String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		for(int i=0; i < vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
			
		}
		return list;
		
		//MESMO CÓDIGO ACIMA, MAS EM FUNÇÃO LAMBDA
		//return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
	
	//MÉTODO DECODIFICA O TEXTO DIGITADO NA URL COM ESPAÇO EM BRANCO, E CONVERTE PARA BUSCA SEM DAR ERRO
	public static String DecodeParam(String s) {
		try {
			return URLDecoder.decode(s,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}

	}
}
