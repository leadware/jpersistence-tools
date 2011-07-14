/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yashiro.persistence.utils.annotations.validator.IntegrityConstraintDAOValidator;
import com.yashiro.persistence.utils.dao.constant.DAOMode;
import com.yashiro.persistence.utils.dao.constant.DAOValidatorEvaluationTime;
import com.yashiro.persistence.utils.dao.tools.DAOValidatorHelper;

/**
 * Classe d'implementation de la validation par defaut des operations d'enregistrement et de mise a jour
 * @author Jean-Jacques
 * @version 2.0
 */
public class IntegrityConstraintDAOValidatorRule implements IDAOValidator<IntegrityConstraintDAOValidator> {

	/**
	 * Un logger
	 */
	protected Log logger = LogFactory.getLog(IntegrityConstraintDAOValidatorRule.class);
	
	/**
	 * Temps d'evaluation systeme
	 */
	protected DAOValidatorEvaluationTime systemEvaluationTime;
	
	/**
	 * Mode DAO du systeme
	 */
	protected DAOMode systemDAOMode;
	
	/**
	 * Annotation en cours
	 */
	protected IntegrityConstraintDAOValidator annotation;
	
	@Override
	public void initialize(IntegrityConstraintDAOValidator annotation, EntityManager entityManager, DAOMode mode, DAOValidatorEvaluationTime evaluationTime) {
		
		// On initialise les champs
		this.annotation = annotation;
		this.systemDAOMode = mode;
		this.systemEvaluationTime = evaluationTime;
	}

	@Override
	public void processValidation(Object entity) {

		// Si on ne doit pas evaluer cette annotation
		if(!this.isProcessable()) {
			
			// Un log
			logger.trace("NotEmptyDAOValidatorRule#processValidation - Not rocessable");
			
			// On sort
			return;
		}
		
		// On valide les contraintes d'integrites
		ValidatorEngine.validate(entity);
	}

	/**
	 * methode permettant de tester si l'annotation doit-etre executee
	 * @return	Etat d'execution de l'annotation
	 */
	protected boolean isProcessable() {
		
		// Comparaison des modes
		boolean correctMode = DAOValidatorHelper.arraryContains(annotation.mode(), this.systemDAOMode);
		
		// Comparaison des instants d'evaluation
		boolean correctTime = DAOValidatorHelper.arraryContains(annotation.evaluationTime(), this.systemEvaluationTime);
		
		// On retourne la comparaison des deux
		return correctMode && correctTime;
	}
	
	@Override
	public Object[] getMessageParameters(Object entity) {
		
		// On retourne null
		return null;
	}
}
