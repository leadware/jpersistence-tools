/**
 * 
 */
package com.yashiro.persistence.utils.dao.tools.encrypter.exceptions;

import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.BaseJPersistenceUtilsException;

/**
 * Classe representant une exception due a une mauvaise initialisation de l'algorithme d'encryptage
 * @author Jean-Jacques
 * @version 1.0
 */
public class NoSuchPaddingException extends BaseJPersistenceUtilsException{

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public NoSuchPaddingException() {
		
		// Initialisation du Parent
		super("NoSuchPaddingException.message.code");
	}

	/**
	 * Constructeur avec initialisation de la cause
	 */
	public NoSuchPaddingException(Throwable cause) {
		
		// Initialisation du Parent
		super("NoSuchPaddingException.message.code", cause);
	}
}
