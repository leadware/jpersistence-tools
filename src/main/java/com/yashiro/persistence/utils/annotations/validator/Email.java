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

import com.yashiro.persistence.utils.annotations.validator.engine.EmailRule;

/**
 * Annotation permettant de valider la validite d'un Email
 * @author Jean-Jacques
 * @version 1.0
 */
@ValidatorClass(value = EmailRule.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Email {
	
	/**
	 * Message d'erreur
	 */
	String message();

	/**
	 * Methode permettant de savoir si on controle la chaine vide
	 * @return	Etat de contole de la chaine vide
	 */
	boolean matchOnEmpty() default true;
}
