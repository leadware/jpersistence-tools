/**
 * 
 */
package com.yashiro.persistence.utils.dao.tools.encrypter.exceptions;

import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.BaseJPersistenceUtilsException;

/**
 * Classe representant une exception due a une mauvaise initialisation du padding d'un algorithme
 * @author Jean-Jacques
 * @version 1.0
 */
public class BadPaddingException extends BaseJPersistenceUtilsException {

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public BadPaddingException() {
		
		// Initialisation du Parent
		super("BadPaddingException.message.code");
	}
	
	/**
	 * Constructeur avec initialisation de la cause
	 */
	public BadPaddingException(Throwable cause) {
		
		// Initialisation du Parent
		super("BadPaddingException.message.code", cause);
	}
}
