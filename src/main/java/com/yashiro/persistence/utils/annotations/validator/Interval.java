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

import com.yashiro.persistence.utils.annotations.validator.engine.IntervalRule;

/**
 * Annotation de la règle de validation permettant de contrôler que la valeur d'une propriété
 * se trouve bien dans un intervalle donne
 * @author Jean-Jacques
 * @version 1.0
 */
@ValidatorClass(value = IntervalRule.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Interval {
	
	/**
	 * Valeur minimum de l'intervalle
	 * @return	Valeur minimum de l'intervalle
	 */
	float min() default 0.0F;
	
	/**
	 * Valeur maximum de l'intervalle
	 * @return	Valeur maximum de l'intervalle
	 */
	float max();
	
	/**
	 * Message d'erreur
	 * @return	Message d'erreur
	 */
	String message() default "Interval.value.out";
}
