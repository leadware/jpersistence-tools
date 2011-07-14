/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;

import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.InvalidEntityInstanceStateException;

/**
 * Classe permettant d'effectuer la validation d'instance manuellement
 * @author Jean-Jacques
 * @version 1.0
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ValidatorEngine {

	/**
	 * Methode permettant de valider une classe
	 * @param o	Objet  valider
	 */
	
	public static void validate(Object o) {
		
		// Le Moteur de validation
		ClassValidator validator = new ClassValidator(o.getClass());
		
		// Obtention de la liste des valeurs indsirables
		InvalidValue[] values = validator.getInvalidValues(o);
		
		// Si la liste est vide
		if(values == null || values.length == 0) return;
				
		// On lve une Exception
		throw new InvalidEntityInstanceStateException(new InvalidStateException(values));
	}
}
