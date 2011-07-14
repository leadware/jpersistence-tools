/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

import com.yashiro.persistence.utils.annotations.validator.engine.PhoneNumberRule;

/**
 * Annotation de la règle de validation permettant de controler que la valeur d'une propriété
 * represente bien un numero de telephone
 * @author Jean-Jacques
 * @version 1.0
 */
@ValidatorClass(value = PhoneNumberRule.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PhoneNumber {
	
	/**
	 * Methode d'obtention du message a afficher lors du non respect de la règle
	 * @return	Le Message
	 */
	String message() default "PhoneNumber.invalidpropertyvalue";
	
	/**
	 * Methode permettant de savoir si on controle la chaine vide
	 * @return	Etat de contole de la chaine vide
	 */
	boolean matchOnEmpty() default true;
}
