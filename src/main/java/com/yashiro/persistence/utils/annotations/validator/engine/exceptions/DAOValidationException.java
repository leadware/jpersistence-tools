/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine.exceptions;

/**
 * Exception levee lors de la validation d'une entite
 * @author Jean-Jacques
 * @version 1.0
 */
public class DAOValidationException extends BaseJPersistenceUtilsException {

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Liste des parametres du message
	 */
	private Object[] parameters = null;
	
	/**
	 * Constructeur par defaut
	 */
	public DAOValidationException(String message) {
		
		// Initialisation Parente
		super(message);
	}
	
	/**
	 * Constructeur par defaut
	 */
	public DAOValidationException(String message, Object[] parameters) {
		
		// Initialisation Parente
		super(message);
		
		// Parametres
		this.parameters = parameters;
	}

	/**
	 * Methode d'obtention de la Liste des parametres du message
	 * @return Liste des parametres du message
	 */
	public Object[] getParameters() {
		return parameters;
	}

	/**
	 * Methode de mise a jour de la Liste des parametres du message
	 * @param parameters Liste des parametres du message
	 */
	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}
}
