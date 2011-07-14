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

import com.yashiro.persistence.utils.annotations.validator.engine.IPV4Rule;

/**
 * Annotation de la règle de validation permettant de contrôler que la valeur d'une propriété
 * representant bien une adresse IPV4
 * @author Jean-Jacques
 * @version 1.0
 */
@ValidatorClass(value = IPV4Rule.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IPV4 {
	
	/**
	 * Methode d'obtention du message a afficher lors du non respect de la règle
	 * @return	Le Message
	 */
	String message() default "IPV4Adress.invalidpropertyvalue";
}
