/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yashiro.persistence.utils.annotations.validator.engine.IntegrityConstraintDAOValidatorRule;
import com.yashiro.persistence.utils.dao.constant.DAOMode;
import com.yashiro.persistence.utils.dao.constant.DAOValidatorEvaluationTime;

/**
 * Annotation permettant de verifier les contraintes d'intergrites
 * @author Jean-Jacques
 * @version 2.0
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DAOValidatorRule(logicClass = IntegrityConstraintDAOValidatorRule.class)
public @interface IntegrityConstraintDAOValidator {

	/**
	 * Methode permettant d'obtenir le mode d'utilisation de l'instance de l'annotation
	 * @return	Mode DAO de l'instance de l'annotation
	 */
	public DAOMode[] mode() default DAOMode.SAVE;

	/**
	 * Methode permettant d'obtenir le l'instant d'evaluation de l'annotation
	 * @return	Instant d'evaluation de l'annotation
	 */
	public DAOValidatorEvaluationTime[] evaluationTime() default DAOValidatorEvaluationTime.PRE_CONDITION;
}
