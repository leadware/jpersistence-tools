/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import org.hibernate.validator.Validator;

import com.yashiro.persistence.utils.annotations.validator.Length;

/**
 * Classe d'implémentation de la règle de gestion de la taille
 * @author Jean-Jacques
 * @version 1.0
 */
public class LengthRule implements Validator<Length> {
	
	/**
	 * Longueur minimale
	 */
	private int min;
	
	/**
	 * Longueur maximale
	 */
	private int max;
	
	/**
	 * Etat de validation en cas d'objet null
	 */
	private boolean validOnNullObject;
		
	@Override
	public void initialize(Length annotation) {
		
		// On initialise les parametres
		min = annotation.min();
		max = annotation.max();
		validOnNullObject = annotation.validOnNullObject();
	}

	@Override
	public boolean isValid(Object obj) {
		
		// Si l'Objet est null
		if(obj == null) return validOnNullObject;
		
		// Si l'Objet est un tableau
		if(obj.getClass().isArray()) {
			
			// Taille
			int size = Array.getLength(obj);
			
			// On retourne la comparaison
			return (size >= min && size <= max);
		}
		
		// Si l'objet est une collection
		if(obj instanceof Collection<?>) {
			
			// Taille
			int size = ((Collection<?>) obj).size();
			
			// On retourne la comparaison
			return (size >= min && size <= max);
		}

		// Si l'objet est une map
		if(obj instanceof Map<?, ?>) {
			
			// Taille
			int size = ((Map<?, ?>) obj).size();
			
			// On retourne la comparaison
			return (size >= min && size <= max);
		}
		
		// Si l'Objet est une instance de Chaine
		if(obj instanceof String) {
			
			// Taille
			int size = ((String) obj).length();
			
			// On retourne la comparaison
			return (size >= min && size <= max);
		}
		
		// Si c'est autre chose on retourne false
		return false;
	}

}
