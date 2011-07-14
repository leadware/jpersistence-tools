/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine;

import java.util.regex.Pattern;

import org.hibernate.validator.Validator;

import com.yashiro.persistence.utils.annotations.validator.PhoneNumber;

/**
 * Classe implementant la regle de validation contr lant que la valeur d'une propri t 
 * correspond   une valeur d'une liste donn e
 * @author Jean-Jacques
 * @version 1.0
 */
public class PhoneNumberRule implements Validator<PhoneNumber> {
	
	/**
	 * Etat de contole de la chaine vide
	 */
	private boolean matchOnEmpty = true;
	
	/* (non-Javadoc)
	 * @see org.hibernate.validator.Validator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(PhoneNumber annotation) {
		
		// Initialisation de l'Etat de Controle de la chaine vide
		this.matchOnEmpty = annotation.matchOnEmpty();
	}

	/* (non-Javadoc)
	 * @see org.hibernate.validator.Validator#isValid(java.lang.Object)
	 */
	@Override
	public boolean isValid(Object value) {
		
		// Si l'objet est null : false
		if(value == null) return matchOnEmpty;
		
		// Si l'objet n'est pas une chaine : false
		if(!(value instanceof String)) return false;
		
		// On caste
		String stringValue = (String) value;
		
		// Si la cha ne est vide : false
		if(stringValue == null || stringValue.length() == 0) return matchOnEmpty;
		
		// On Construit l'expression r guli re de test
		String expression = "\\+{0,1}\\d*";
		
		// Le Pattern representant une chaine AlphaNumerique
		Pattern pattern = Pattern.compile(expression);
		
		// On retourne le r sultat du test
		return pattern.matcher(stringValue).matches();
	}
}
