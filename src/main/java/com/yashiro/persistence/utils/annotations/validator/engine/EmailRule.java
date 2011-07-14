/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine;

import java.util.regex.Pattern;

import org.hibernate.validator.Validator;

import com.yashiro.persistence.utils.annotations.validator.Email;

/**
 * Implementation de la regle de validation Email
 * @author Jean-Jacques
 * @version 1.0
 */
public class EmailRule implements Validator<Email> {
	
	/**
	 * Constante de construction du Pattern d'Email
	 */
	private static String ATOM = "[^\\x00-\\x1F^\\(^\\)^\\<^\\>^\\@^\\,^\\;^\\:^\\\\^\\\"^\\.^\\[^\\]^\\s]";
	private static String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)*";
	private static String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";
	
	/**
	 * Etat de contole de la chaine vide
	 */
	private boolean matchOnEmpty = true;
	
	/**
	 * Pattern des Mails
	 */
	private Pattern pattern = null;
	
	/*
	 * (non-Javadoc)
	 * @see org.hibernate.validator.EmailValidator#initialize(org.hibernate.validator.Email)
	 */
	public void initialize(Email email) {
		
		// Initialisation de l'Etat de Controle de la chaine vide
		this.matchOnEmpty = email.matchOnEmpty();
		
		// Construction du pattern des mails
		pattern = Pattern.compile(
				"^" + ATOM + "+(\\." + ATOM + "+)*@"
				+ DOMAIN
				+ "|"
				+ IP_DOMAIN
				+ ")$",
				java.util.regex.Pattern.CASE_INSENSITIVE
		);
	}

	@Override
	public boolean isValid(Object value) {

		// Si l'objet est null : false
		if(value == null) return matchOnEmpty;

		// Si l'objet n'est pas une chaine : false
		if(!(value instanceof String)) return false;
		
		// On caste
		String stringValue = (String) value;

		// Si la chaîne est vide : false
		if(stringValue == null || stringValue.length() == 0) return matchOnEmpty;
		
		// On retourne le matching
		return pattern.matcher(stringValue).matches();
	}

}
