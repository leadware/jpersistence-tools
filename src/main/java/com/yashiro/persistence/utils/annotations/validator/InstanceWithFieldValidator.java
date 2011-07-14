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

import com.yashiro.persistence.utils.annotations.validator.engine.InstanceWithFieldValidatorRule;
import com.yashiro.persistence.utils.dao.constant.DAOMode;
import com.yashiro.persistence.utils.dao.constant.DAOValidatorEvaluationTime;
import com.yashiro.persistence.utils.dao.constant.ValidatorExpressionType;

/**
 * Validateur permettant de vérifier qu'une liste de champs est unique sur le Contexte de Persistence
 * @author Jean-Jacques
 * @version 2.0
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@DAOValidatorRule(logicClass = InstanceWithFieldValidatorRule.class)
public @interface InstanceWithFieldValidator {
	
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
	 * Methode d'obtention du Champ a tester sur le contexte
	 * @return	Champ a tester sur le contexte
	 */
	public String contextField();
	
	/**
	 * Methode d'obtention du champ persistant a tester
	 * @return	Champ persistant a tester
	 */
	public String persistentField();
	
	/**
	 * Methode d'obtention du nombre minimum
	 * @return	Taille minimum
	 */
	public int min() default 0;
	
	/**
	 * Methode d'obtention du nombre maximum
	 * @return	Taille maximum
	 */
	public int max() default Integer.MAX_VALUE;
	
	/**
	 * Message lors de la violation de la contrainte
	 * @return	Message
	 */
	public String message() default "InstanceWithFieldValidator.error";

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
