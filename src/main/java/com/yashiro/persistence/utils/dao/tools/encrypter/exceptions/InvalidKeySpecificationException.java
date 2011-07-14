/**
 * 
 */
package com.yashiro.persistence.utils.dao.tools.encrypter.exceptions;

import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.BaseJPersistenceUtilsException;

/**
 * Classe representant une exception due a une mauvaise initialisation de la cle d'un algorithme (Le type de cle ne correspond pas a l'algorithme)
 * @author Jean-Jacques
 * @version 1.0
 */
public class InvalidKeySpecificationException extends BaseJPersistenceUtilsException{

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public InvalidKeySpecificationException() {
		
		// Initialisation du Parent
		super("InvalidKeySpecificationException.message.code");
	}
	
	/**
	 * Constructeur avec initialisation de la cause
	 */
	public InvalidKeySpecificationException(Throwable cause) {
		
		// Initialisation du Parent
		super("InvalidKeySpecificationException.message.code", cause);
	}
}
