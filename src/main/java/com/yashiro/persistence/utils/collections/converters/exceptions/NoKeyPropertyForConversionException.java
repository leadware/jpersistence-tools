/**
 * 
 */
package com.yashiro.persistence.utils.collections.converters.exceptions;

import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.BaseJPersistenceUtilsException;

/**
 * Exception exprimant l'absence de la propriete Cle lors de la conversion d'une Collection en MAP
 * @author Jean-Jacques
 * @version	1.0
 */
public class NoKeyPropertyForConversionException extends BaseJPersistenceUtilsException {
	
	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur par defaut
	 */
	public NoKeyPropertyForConversionException() {
		
		// Initialisation du Parent
		super("NoKeyPropertyForConversionException.message.code");
	}
}
