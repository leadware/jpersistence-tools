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

import com.yashiro.persistence.utils.annotations.validator.engine.CheckEntityExistValidatorRule;
import com.yashiro.persistence.utils.dao.constant.DAOMode;
import com.yashiro.persistence.utils.dao.constant.DAOValidatorEvaluationTime;
import com.yashiro.persistence.utils.dao.constant.ValidatorExpressionType;

/**
 * Validateur permettant de verifier qu'une de classe existe dans le Contexte de Persistance
 * @author Jean-Jacques
 * @version 2.0
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@DAOValidatorRule(logicClass = CheckEntityExistValidatorRule.class)
public @interface CheckEntityExistValidator {
	
	/**
	 * Methode permettant d'obtenir le type d'expression
	 * @return	Type d'expression
	 */
	public ValidatorExpressionType type() default ValidatorExpressionType.JPQL;
	
	/**
	 * Methode permettant d'obtenir la classe cible du test
	 * @return	Classe cible du test
	 */
	public Class<?> targetClass();
	
	/**
	 * Methode d'obtention du Champ de contenant la valeur de l'ID
	 * @return	Champ de contenant la valeur de l'ID
	 */
	public String contextIDField();
	
	/**
	 * Methode d'obtention du champ ID a tester
	 * @return	Champ persistant a tester
	 */
	public String idField() default "id";
	
	/**
	 * Message lors de la violation de la contrainte
	 * @return	Message
	 */
	public String message() default "CheckEntityExistValidator.error";

	/**
	 * Methode d'obtention de la liste des parametres de l'annotation
	 * @return	Liste des parametres de l'annotation
	 */
	public String[] parameters() default {};
	
	/**
	 * Methode permettant d'obtenir le mode d'utilisation de l'instance de l'annotation
	 * @return	Modes DAO de l'instance de l'annotation
	 */
	public DAOMode[] mode() default {DAOMode.SAVE, DAOMode.UPDATE};

	/**
	 * Methode permettant d'obtenir le l'instant d'evaluation de l'annotation
	 * @return	Instants d'evaluation de l'annotation
	 */
	public DAOValidatorEvaluationTime[] evaluationTime() default DAOValidatorEvaluationTime.PRE_CONDITION;
}
