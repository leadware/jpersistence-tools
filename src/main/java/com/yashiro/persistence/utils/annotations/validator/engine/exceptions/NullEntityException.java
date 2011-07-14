/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine.exceptions;


/**
 * Exception levee lors d'une operation sur une entite nulle
 * @author Jean-Jacques
 * @version 1.0
 */
public class NullEntityException extends BaseJPersistenceUtilsException {
	
	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public NullEntityException() {
		
		// Initialisation Parente
		super("NullEntityException.message");
	}
}
