package com.jeremiasmiguel.cursospringmc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

	/* Convertendo uma String em uma lista de números inteiros
	 * Exemplo: passado "1, 2, 3".
	 * Será retornada uma lista [1, 2, 3]
	*/
	public static List<Integer> decodeIntList(String string) {
		String[] vetor = string.split(",");
		List<Integer> lista = new ArrayList<>();
		
		for(int i=0; i < vetor.length; i++) {
			lista.add(Integer.parseInt(vetor[i]));
		}
		
		return lista;
		
		// return Arrays.asList(string.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
	
	/*
	 * Convertendo uma lista de caracteres com espaços ou símbolos em uma lista de caracteres simples
	 */
	public static String decodeParam(String string) {
		try {
			return URLDecoder.decode(string, "UTF-8");
		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
}
