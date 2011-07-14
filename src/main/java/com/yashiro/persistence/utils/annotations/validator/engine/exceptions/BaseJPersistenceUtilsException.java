/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine.exceptions;


/**
 * <p>
 * 	<b>Classe de Base des exception du Framework JPersistenceUtils</b>
 * 	@author Jean-Jacques
 * 	@version 1.0
 * </p>
 */
public class BaseJPersistenceUtilsException extends RuntimeException {
	
	/**
	 * Message d'erreur par defaut
	 */
	public static String DEFAULT_MESSAGE = "BaseJPersistenceUtilsException.mesasge.default";
	
	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Constructeur par défaut
	 */
	public BaseJPersistenceUtilsException() {
		
		// Appel du Constructeur de RuntimeException
		super(DEFAULT_MESSAGE);
	}
	
	/**
	 * Constructeur avec Message
	 * @param message	Message a afficher
	 */
	public BaseJPersistenceUtilsException(String message){
		
		// Appel du constructeur de RuntimeException
		super(message);
	}
	
	/**
	 * Constructeur avec Message et de la cause
	 * @param message	Message a afficher
	 * @param t	Cause de l'exception
	 */
	public BaseJPersistenceUtilsException(String message, Throwable t) {
		
		// Appel du constructeur de Runtime
		super(message, t);
	}
	
	/**
	 * Constructeur avec de la cause
	 * @param t	Exception cause
	 */
	public BaseJPersistenceUtilsException(Throwable t) {
		
		// Appel du constructeur de Runtime
		super(t);
	}
}
