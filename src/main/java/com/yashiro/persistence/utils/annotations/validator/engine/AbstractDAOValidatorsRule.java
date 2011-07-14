/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine;

import java.lang.annotation.Annotation;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yashiro.persistence.utils.dao.constant.DAOMode;
import com.yashiro.persistence.utils.dao.constant.DAOValidatorEvaluationTime;

/**
 * Classe abstraite definissant les bases de l'implementation d'une regle contenant d'autres regles de validation
 * @author Jean-Jacques
 * @version 2.0
 */
public abstract class AbstractDAOValidatorsRule implements IDAOValidator<Annotation> {

	/**
	 * Un logger
	 */
	protected Log logger = LogFactory.getLog(AbstractDAOValidatorsRule.class);
	
	/**
	 * Le gestionnaire d'entites
	 */
	protected EntityManager entityManager;
	
	/**
	 * L'annotation en cours
	 */
	protected Annotation annotation;
	
	/**
	 * Liste des annotations de validations contenues dans cette annotation
	 */
	protected Annotation[] validators;

	/**
	 * Temps d'evaluation systeme
	 */
	protected DAOValidatorEvaluationTime systemEvaluationTime;
	
	/**
	 * Mode DAO du systeme
	 */
	protected DAOMode systemDAOMode;
		
	/**
	 * Methode permettant d'obtenir la liste des Validateurs
	 * @return	Liste des validateurs
	 */
	protected abstract Annotation[] getValidators();
	
	@Override
	public void initialize(Annotation annotation, EntityManager entityManager, DAOMode mode, DAOValidatorEvaluationTime evaluationTime) {
		
		// Sauvegarde des parametres
		this.annotation = annotation;
		this.entityManager = entityManager;
		this.systemDAOMode = mode;
		this.systemEvaluationTime = evaluationTime;
		
		// Obtention des validateurs
		this.validators = getValidators();
	}
	
	@Override
	public Object[] getMessageParameters(Object entity) {
		
		// On retourne null
		return null;
	}
}
