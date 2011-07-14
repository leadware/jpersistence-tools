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

import com.yashiro.persistence.utils.annotations.validator.engine.NotEmptyDAOValidatorsRule;

/**
 * Annotation permettant de valider sur la base d'une liste de validateurs de type @NotEmptyDAOValidator
 * @author Jean-Jacques
 * @version 2.0
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@DAOValidatorRule(logicClass = NotEmptyDAOValidatorsRule.class)
public @interface NotEmptyDAOValidators {
	
	/**
	 * Methode d'obtention de la liste des validateurs
	 * @return	Liste des validateurs
	 */
	public NotEmptyDAOValidator[] value();
}
