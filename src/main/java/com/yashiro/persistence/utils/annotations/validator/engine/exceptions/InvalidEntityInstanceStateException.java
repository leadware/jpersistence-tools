/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine.exceptions;



import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;


/**
 * Classe representant une exception levee par Hibernate Validator
 * @author Jean-Jacques
 * @version 1.0
 */

public class InvalidEntityInstanceStateException extends BaseJPersistenceUtilsException {
	
	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Tableau des codes d'erreurs
	 */
	private String[] messages = null;
	
	/**
	 * Constructeur par defaut
	 */
	public InvalidEntityInstanceStateException(InvalidStateException e) {
		
		// Initialisation du Parent
		super(e.getInvalidValues()[0].getMessage(), e);
		
		// Initialisation des codes
		buildMessages(e);
	}
	
	/**
	 * Constructeur avec initialisation des parametres mesasgeCode et Exception
	 * @param messageCode	Code du message
	 */
	public InvalidEntityInstanceStateException(String messageCode) {
		
		// Initialisation du Parent
		super(messageCode);
		
		// Initialisation des codes
		this.messages = new String[] {messageCode};
	}
	
	/**
	 * Methode d'obtention du Tableau des codes d'erreurs
	 * @return Tableau des codes d'erreurs
	 */
	protected String[] getMessages() {
		return messages;
	}

	/**
	 * Methode de construction de la liste des messages
	 * @param e	Exception contenant la liste des messages
	 */
	private void buildMessages(InvalidStateException e) {
		
		// Si l'exception est nulle
		if(e == null) return;
		
		// Tableau des Valeurs invalides
		InvalidValue [] values = e.getInvalidValues();
		
		// Si la liste est vide
		if(values == null || values.length == 0) return;
		
		// Instanciation de la liste de codes d'erreurs
		this.messages = new String[values.length];
		
		// Parcours
		for (int i = 0; i < values.length; i++) {
			
			// Ajout dans la liste de codes
			this.messages[i] = values[i].getMessage();
		}
	}
}
