/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine;

import java.lang.annotation.Annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yashiro.persistence.utils.annotations.validator.InstanceWithFieldValidator;
import com.yashiro.persistence.utils.annotations.validator.InstanceWithFieldValidators;

/**
 * Classe d'implementation de la regle de validation @InstanceWithFieldValidators
 * @author Jean-Jacques
 * @version 2.0
 */
public class InstanceWithFieldValidatorsRule extends AbstractDAOValidatorsRule {
	
	/**
	 * Un logger
	 */
	protected Log logger = LogFactory.getLog(InstanceWithFieldValidatorsRule.class);
	
	@Override
	public void processValidation(Object entity) {
		
		// Un Log
		logger.trace("InstanceWithFieldValidatorsRule#processValidation");
		
		// Si la liste des validateurs est vide
		if(this.validators == null || this.validators.length == 0) {
			
			// Un Log
			logger.trace("InstanceWithFieldValidatorsRule#processValidation - Pas de validateur");
						
			// On sort
			return;
		}
		
		// Classe d'execution de la validation
		InstanceWithFieldValidatorRule rule = new InstanceWithFieldValidatorRule();
		
		// Parcours de la liste
		for (Annotation annotation : this.validators) {
			
			// On initialize
			rule.initialize((InstanceWithFieldValidator) annotation, entityManager, this.systemDAOMode, this.systemEvaluationTime);
			
			// On valide
			rule.processValidation(entity);
		}
	}

	@Override
	protected Annotation[] getValidators() {
		
		// Un Log
		logger.trace("InstanceWithFieldValidatorsRule#getValidators");
		
		// Si l'annotation en cours est nulle
		if(this.annotation == null) {

			// Un Log
			logger.trace("InstanceWithFieldValidatorsRule#getValidators - L'annotation en cours est nulle");
			
			// On retourne null
			return null;
		}
		
		// Si l'annotation en cours n'est pas de l'instance
		if(!(annotation instanceof InstanceWithFieldValidators)) {
			
			// Un Log
			logger.trace("InstanceWithFieldValidatorsRule#getValidators - L'annotation en cours n'est pas une instance de @SizeDAOValidators");
			
			// On retourne null
			return null;
		}
		
		// On caste
		InstanceWithFieldValidators castedValidators = (InstanceWithFieldValidators) annotation;

		// Un Log
		logger.trace("InstanceWithFieldValidatorsRule#getValidators - Obtention de la liste des annotations");
		
		// Liste des Annotations
		InstanceWithFieldValidator[] tValidators = castedValidators.value();
		
		// On retourne la liste
		return tValidators;
	}
}
