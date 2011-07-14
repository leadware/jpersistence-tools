/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine.exceptions;

/**
 * Exception levee lors de l'instanciation du Validateur
 * @author Jean-Jacques
 * @version 1.0
 */
public class ValidatorInstanciationException extends BaseJPersistenceUtilsException {

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public ValidatorInstanciationException(Throwable cause) {
		
		// Initialisation Parente
		super("ValidatorInstanciationException.message", cause);
	}
}
