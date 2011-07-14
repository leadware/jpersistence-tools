/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine.exceptions;

import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.BaseJPersistenceUtilsException;

/**
 * Exception levee lors d'une tentative d'operation de parametrage sur un objet n'etant pas un objet de parametrage
 * @author Jean-Jacques
 * @version 2.0
 */
public class NotParameterException extends BaseJPersistenceUtilsException {

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Objet cause de l'exception
	 */
	private Object entity;
	
	/**
	 * Constructeur
	 * @param entity	Entite cause
	 */
	public NotParameterException(Object entity) {
		
		// Initialisation parente
		super("NotParameterException.message");
		
		// Initialisation de la cause
		this.entity = entity;
	}

	/**
	 * Methode d'obtention de l'Objet cause de l'exception
	 * @return Objet cause de l'exception
	 */
	public Object getEntity() {
		return entity;
	}
}
