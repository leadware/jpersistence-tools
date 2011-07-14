/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yashiro.persistence.utils.annotations.validator.SizeDAOValidator;
import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.DAOValidationException;
import com.yashiro.persistence.utils.dao.constant.DAOMode;
import com.yashiro.persistence.utils.dao.constant.DAOValidatorEvaluationTime;
import com.yashiro.persistence.utils.dao.constant.ValidatorExpressionType;

/**
 * Classe d'implementation de la regle de validation @SizeDAOValidator
 * @author Jean-Jacques
 * @version 2.0
 */
@SuppressWarnings("unchecked")
public class SizeDAOValidatorRule extends AbstractExpressionBasedDAOValidatorRule {
	
	/**
	 * Un logger
	 */
	protected Log logger = LogFactory.getLog(SizeDAOValidatorRule.class);
	
	@Override
	public void processValidation(Object entity) {
		
		// Un message
		logger.debug("SizeDAOValidatorRule#processValidation");
		
		// Si on ne doit pas evaluer cette annotation
		if(!this.isProcessable()) {
			
			// Un log
			logger.debug("NotEmptyDAOValidatorRule#processValidation - Not Processable");
			
			// On sort
			return;
		}
		
		// Le Type
		ValidatorExpressionType type = this.getType();
		
		// Si le type est null
		if(type == null) type = ValidatorExpressionType.JPQL;
		
		// Un message
		logger.debug("SizeDAOValidatorRule#processValidation - Type de Regle: " + type.toString());
		
		// Si le type est HQL|JPQL|EJBQL
		if(type.equals(ValidatorExpressionType.HQL) || type.equals(ValidatorExpressionType.JPQL) || type.equals(ValidatorExpressionType.EJBQL)) {
			
			// On construit la requte
			Query query = this.buildQuery(entity);
			
			// Execution
			List<Object> result = query.getResultList();
			
			// La Taille
			long size = 0;
			
			// Le minimum
			long min = ((SizeDAOValidator)this.annotation).min();
			
			// Le max
			long max = ((SizeDAOValidator)this.annotation).max();
						
			// Si la liste n'est pas vide
			if(result != null && result.size() > 0) size = result.size();
			
			// Un message
			logger.debug("SizeDAOValidatorRule#processValidation - Taille du resultat: " + result.size());
						
			// On compare
			if((min > size) || (max < size)) {

				// Un message
				logger.debug("SizeDAOValidatorRule#processValidation - Rgle non vrifie");
				
				// On lve une exception
				throw new DAOValidationException(getMessage(), getMessageParameters(entity));
			}
		}
	}

	@Override
	protected String getExpression() {
		
		// On caste l'annotation
		SizeDAOValidator castedAnnotation = (SizeDAOValidator) this.annotation;
		
		// On retourne l'expression
		return castedAnnotation.expr();
	}

	@Override
	protected ValidatorExpressionType getType() {
		
		// On caste l'annotation
		SizeDAOValidator castedAnnotation = (SizeDAOValidator) this.annotation;
		
		// On retourne le type de validation
		return castedAnnotation.type();
	}

	@Override
	protected String getMessage() {
		
		// On caste l'annotation
		SizeDAOValidator castedAnnotation = (SizeDAOValidator) this.annotation;
		
		// On retourne le type de validation
		return castedAnnotation.message();
	}

	@Override
	protected DAOValidatorEvaluationTime[] getAnnotationEvaluationTime() {
		
		// On caste l'annotation
		SizeDAOValidator castedAnnotation = (SizeDAOValidator) this.annotation;
		
		// On retourne le type de validation
		return castedAnnotation.evaluationTime();
	}

	@Override
	protected DAOMode[] getAnnotationMode() {
		
		// On caste l'annotation
		SizeDAOValidator castedAnnotation = (SizeDAOValidator) this.annotation;
		
		// On retourne le type de validation
		return castedAnnotation.mode();
	}
	
	@Override
	protected String[] getMessageParametersExpressions() {

		// On caste l'annotation
		SizeDAOValidator castedAnnotation = (SizeDAOValidator) this.annotation;
		
		// On retourne la liste des messages
		return castedAnnotation.parameters();
	}
}
