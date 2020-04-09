package com.jeremiasmiguel.cursospringmc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

// Classe que herda os atributos padrões e genéricos, e também conterá a lista de erros que podem ser ocasionados (FieldMessage)
public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValidationError(Integer status, String mensagem, Long timestamp) {
		super(status, mensagem, timestamp);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
}
