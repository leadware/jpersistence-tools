/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine;

import java.lang.annotation.Annotation;
import javax.persistence.EntityManager;

import com.yashiro.persistence.utils.dao.constant.DAOMode;
import com.yashiro.persistence.utils.dao.constant.DAOValidatorEvaluationTime;

/**
 * Interface des classes implementant la logique de validation d'une 
 * Fonction-Annotation
 * @author Jean-Jacques
 * @version 2.0
 */
public interface IDAOValidator<A extends Annotation> {
	
	/**
	 * Methode d'initialisation de la classe d'implementation de la logique de validation
	 * @param annotation	Annotation en cours
	 */
	public void initialize(A annotation, EntityManager entityManager, DAOMode systemMode, DAOValidatorEvaluationTime systemEvaluationTime);
	
	/**
	 * Methode d'obtention des parametres du message a afficher
	 * @return	Tableau des parametres du message a afficher
	 */
	public Object[] getMessageParameters(Object entity);
	
	/**
	 * Methode d'execution de la validation sur une entite donnee
	 * @param entity	Entite a valider
	 */
	public void processValidation(Object entity);
}
