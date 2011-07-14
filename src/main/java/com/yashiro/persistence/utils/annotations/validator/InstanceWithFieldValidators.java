/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yashiro.persistence.utils.annotations.validator.engine.InstanceWithFieldValidatorsRule;

/**
 * Validateur permettant de verifier qu'une liste de champs est unique sur le Contexte de Persistence
 * @author Jean-Jacques
 * @version 2.0
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@DAOValidatorRule(logicClass = InstanceWithFieldValidatorsRule.class)
public @interface InstanceWithFieldValidators {
	
	/**
	 * Methode d'obtention de la liste des Annotation a valider
	 * @return	Liste des Annotation a valider
	 */
	public InstanceWithFieldValidator[] value();
}