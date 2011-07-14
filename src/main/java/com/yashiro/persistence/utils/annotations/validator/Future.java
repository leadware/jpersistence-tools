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

import com.yashiro.persistence.utils.annotations.validator.engine.FutureRule;

/**
 * Annotation permettant de verifier qu'une date est dans le futur
 * @author Jean-Jacques
 * @version 2.0
 */
@ValidatorClass(value = FutureRule.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Future {
	
	/**
	 * Message a afficher si la date n'est pas dans le futur
	 * @return	Message a afficher si la date n'est pas dans le futur
	 */
	public String message();
}
