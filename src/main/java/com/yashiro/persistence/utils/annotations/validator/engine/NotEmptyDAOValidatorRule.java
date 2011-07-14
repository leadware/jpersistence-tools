/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yashiro.persistence.utils.annotations.validator.NotEmptyDAOValidator;
import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.DAOValidationException;
import com.yashiro.persistence.utils.dao.constant.DAOMode;
import com.yashiro.persistence.utils.dao.constant.DAOValidatorEvaluationTime;
import com.yashiro.persistence.utils.dao.constant.ValidatorExpressionType;

/**
 * Classe d'implementation de la regle de validation @NotEmptyDAOValidator
 * @author Jean-Jacques
 * @version 2.0
 */
@SuppressWarnings("unchecked")
public class NotEmptyDAOValidatorRule extends AbstractExpressionBasedDAOValidatorRule {
	
	/**
	 * Un logger
	 */
	protected Log logger = LogFactory.getLog(NotEmptyDAOValidatorRule.class);
	
	@Override
	public void processValidation(Object entity) {
		
		// Un message
		logger.debug("NotEmptyDAOValidatorRule#processValidation");
		
		// Si on ne doit pas evaluer cette annotation
		if(!this.isProcessable()) {
			
			// Un log
			logger.debug("NotEmptyDAOValidatorRule#processValidation - Not rocessable");
			
			// On sort
			return;
		}
		
		// Resolution des fonctions contextuelles
		// this.expressionModel.setComputedExpression(resolveContextFunctions(this.expressionModel.getComputedExpression(), entity));
		
		// Le Type
		ValidatorExpressionType type = this.getType();
		
		// Si le type est null
		if(type == null) type = ValidatorExpressionType.JPQL;
		
		// Si le type est HQL|JPQL|EJBQL
		if(type.equals(ValidatorExpressionType.HQL) || type.equals(ValidatorExpressionType.JPQL) || type.equals(ValidatorExpressionType.EJBQL)) {
			
			// On construit la requete
			Query query = this.buildQuery(entity);
			
			// Execution
			List<Object> result = query.getResultList();
			
			// Si la liste est vide
			if(result == null || result.size() == 0) throw new DAOValidationException(getMessage());
		}
	}

	@Override
	protected String getExpression() {
		
		// On caste l'annotation
		NotEmptyDAOValidator notNull = (NotEmptyDAOValidator) this.annotation;
		
		// On retourne l'expression
		return notNull.expr();
	}

	@Override
	protected ValidatorExpressionType getType() {
		
		// On caste l'annotation
		NotEmptyDAOValidator notNull = (NotEmptyDAOValidator) this.annotation;
		
		// On retourne le type de validation
		return notNull.type();
	}

	@Override
	protected String getMessage() {
		
		// On caste l'annotation
		NotEmptyDAOValidator notNull = (NotEmptyDAOValidator) this.annotation;
		
		// On retourne le type de validation
		return notNull.message();
	}
	
	@Override
	protected DAOValidatorEvaluationTime[] getAnnotationEvaluationTime() {
		
		// On caste l'annotation
		NotEmptyDAOValidator notEmpty = (NotEmptyDAOValidator) this.annotation;
		
		// On retourne le type de validation
		return notEmpty.evaluationTime();
	}

	@Override
	protected DAOMode[] getAnnotationMode() {
		
		// On caste l'annotation
		NotEmptyDAOValidator notEmpty = (NotEmptyDAOValidator) this.annotation;
		
		// On retourne le type de validation
		return notEmpty.mode();
	}
}
