/**
 * 
 */
package com.yashiro.persistence.utils.dao.tools.encrypter.exceptions;

import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.BaseJPersistenceUtilsException;

/**
 * Classe representant une exception due a une mauvaise initialisation de la cle d'un algorithme
 * @author Jean-Jacques
 * @version 1.0
 */
public class InvalidAlgorithmKeyException extends BaseJPersistenceUtilsException {

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public InvalidAlgorithmKeyException() {
		
		// Initialisation du Parent
		super("InvalidAlgorithmKeyException.message.code");
	}
	
	/**
	 * Constructeur par defaut
	 */
	public InvalidAlgorithmKeyException(Throwable cause) {
		
		// Initialisation du Parent
		super("InvalidAlgorithmKeyException.message.code", cause);
	}
}
