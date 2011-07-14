/**
 * 
 */
package com.yashiro.persistence.utils.dao.tools.encrypter.exceptions;

import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.BaseJPersistenceUtilsException;

/**
 * Classe representant une exception due a une Erreur d'Entrée/Sortie
 * @author Jean-Jacques
 * @version 1.0
 */
public class IOException extends BaseJPersistenceUtilsException {

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public IOException() {
		
		// Initialisation du Parent
		super("IOException.message.code");
	}
	
	/**
	 * Constructeur avec initialisation de la cause
	 */
	public IOException(Throwable cause) {
		
		// Initialisation du Parent
		super("IOException.message.code", cause);
	}
}
