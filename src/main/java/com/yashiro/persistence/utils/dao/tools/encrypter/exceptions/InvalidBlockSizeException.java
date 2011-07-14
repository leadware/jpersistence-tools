/**
 * 
 */
package com.yashiro.persistence.utils.dao.tools.encrypter.exceptions;

import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.BaseJPersistenceUtilsException;

/**
 * Classe representant une exception due a une mauvaise initialisation de la taille des blocs d'un algorithme
 * @author Jean-Jacques
 * @version 1.0
 */
public class InvalidBlockSizeException extends BaseJPersistenceUtilsException{

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public InvalidBlockSizeException() {
		
		// Initialisation du Parent
		super("InvalidBlockSizeException.message.code");
	}

	/**
	 * Constructeur avec initialisation de la cause
	 */
	public InvalidBlockSizeException(Throwable cause) {
		
		// Initialisation du Parent
		super("InvalidBlockSizeException.message.code", cause);
	}
}
