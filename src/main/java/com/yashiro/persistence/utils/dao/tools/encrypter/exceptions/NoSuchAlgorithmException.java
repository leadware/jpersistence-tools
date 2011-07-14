/**
 * 
 */
package com.yashiro.persistence.utils.dao.tools.encrypter.exceptions;

import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.BaseJPersistenceUtilsException;

/**
 * Classe representant une exception due a l'absence d'un algorithme de cryptage
 * @author Jean-Jacques
 * @version 1.0
 */
public class NoSuchAlgorithmException extends BaseJPersistenceUtilsException{

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public NoSuchAlgorithmException() {
		
		// Initialisation du Parent
		super("NoSuchAlgorithmException.message.code");
	}
	
	/**
	 * Constructeur avec initialisation de la cause
	 */
	public NoSuchAlgorithmException(Throwable cause) {
		
		// Initialisation du Parent
		super("NoSuchAlgorithmException.message.code", cause);
	}
}
