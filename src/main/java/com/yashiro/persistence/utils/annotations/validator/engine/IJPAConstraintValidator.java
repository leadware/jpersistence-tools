/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine;

import javax.persistence.EntityManager;

/**
 * Interface definissant le contrat des classes de validation des contraintes
 * d'integrites et referentielles sur un objet metier
 * @author Jean-Jacques
 * @version 1.0
 */
public interface IJPAConstraintValidator {
	
	/**
	 * Initialisation du Validateur
	 */
	public void init(EntityManager entityManager, Object entity);
	
	/**
	 * Methode de valiodation des contraintes d'integrites
	 */
	public void validateIntegrityConstraint();
	
	/**
	 * Methode de validation des contraintes referentielles
	 */
	public void validateReferentialConstraint();
}
