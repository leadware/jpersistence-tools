/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine;

import java.util.Date;

import org.hibernate.validator.Validator;

import com.yashiro.persistence.utils.annotations.validator.Past;

/**
 * Classe implementant la regle de validation contrôlant que la valeur d'une date
 * se trouve dans le passe
 * @author Jean-Jacques
 * @version 1.0
 */
public class PastRule implements Validator<Past> {
		
	/* (non-Javadoc)
	 * @see org.hibernate.validator.Validator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(Past annotation) {}

	/* (non-Javadoc)
	 * @see org.hibernate.validator.Validator#isValid(java.lang.Object)
	 */
	@Override
	public boolean isValid(Object value) {
		
		// Si l'objet est null
		if(value == null) return false;
		
		// Si l'objet n'est pas de type date (On l'ignore en retournant true - Comme s'il n'ya eu aucun test)
		if(!(value instanceof Date)) return true;
		
		// On caste
		Date valueDate = (Date) value;
		
		// On retourne la comparaison
		return valueDate.before(new Date());
	}
}
