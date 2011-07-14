/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine.exceptions;


/**
 * Exception levee lorsqu'un enregistrement ne se termine pas correctement
 * @author Jean-Jacques
 * @version 1.0
 */
public class EntityNotSavedException extends BaseJPersistenceUtilsException {
	
	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Entite non enregistree
	 */
	private Object notRegistered;
	
	/**
	 * Constructeur par defaut
	 */
	public EntityNotSavedException(Object notRegistered, Throwable cause) {
		
		// Initialisation Parente
		super("EntityNotSavedException.message", cause);
		
		// Initialisation de la MAP d'information
		this.notRegistered = notRegistered;
	}

	/**
	 * Methode d'obtention de l'Entite non enregistree
	 * @return Methode d'obtention de l'Entite non enregistree
	 */
	public Object getNotRegistered() {
		return notRegistered;
	}
}
