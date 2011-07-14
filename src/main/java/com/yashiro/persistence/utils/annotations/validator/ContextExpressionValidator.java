/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator;

import com.yashiro.persistence.utils.dao.constant.DAOMode;
import com.yashiro.persistence.utils.dao.constant.DAOValidatorEvaluationTime;

/**
 * Validateur d'expression sur le contexte
 * @author Jean-Jacques
 * @version 1.0
 */
public @interface ContextExpressionValidator {
	
	/**
	 * Expression a valider
	 * @return	Expression avalider
	 */
	public String expression();
	
	/**
	 * Valeur attendue
	 * @return Valeur attendue
	 */
	public String value();
	
	/**
	 * Message lors de la violation de la contrainte
	 * @return	Message
	 */
	public String message() default "ContextExpressionValidator.error";

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
