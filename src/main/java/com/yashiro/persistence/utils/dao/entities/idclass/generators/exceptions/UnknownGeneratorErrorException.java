/**
 * 
 */
package com.yashiro.persistence.utils.dao.entities.idclass.generators.exceptions;

import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.BaseJPersistenceUtilsException;

/**
 * Classe reprsentant l'exception gnre lorsqu'une erreur inconnue survient
 * durant la gnration d'Identifiant
 * @author Jean-Jacques
 * @version 1.0
 */
public class UnknownGeneratorErrorException extends BaseJPersistenceUtilsException{

	/**
	 * ID genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public UnknownGeneratorErrorException(Throwable e) {
		
		// Appel Parent
		super("generator.unkowngeneratorerror", e);
	}
}
