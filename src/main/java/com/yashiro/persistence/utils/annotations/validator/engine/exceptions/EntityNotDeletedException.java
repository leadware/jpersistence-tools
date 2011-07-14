/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine.exceptions;


/**
 * Exception levee lorsqu'une suppresison ne se termine pas correctement
 * @author Jean-Jacques
 * @version 1.0
 */
public class EntityNotDeletedException extends BaseJPersistenceUtilsException {
	
	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Entite non enregistree
	 */
	private Object notDeleted;
	
	/**
	 * Constructeur par defaut
	 */
	public EntityNotDeletedException(Object notDeleted, Throwable cause) {
		
		// Initialisation Parente
		super("EntityNotDeletedException.message", cause);
		
		// Initialisation de la MAP d'information
		this.notDeleted = notDeleted;
	}

	/**
	 * Methode d'obtention de l'Entite non supprimee
	 * @return Entite non supprimee
	 */
	public Object getNotDeleted() {
		return notDeleted;
	}
}
