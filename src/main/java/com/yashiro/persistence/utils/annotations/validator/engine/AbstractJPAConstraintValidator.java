/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine;

import javax.persistence.EntityManager;


/**
 * Classe representant une implementation partielle du Validateur referentiel
 * @author Jean-Jacques
 * @version 1.0
 */
public abstract class AbstractJPAConstraintValidator implements IJPAConstraintValidator {
	
	/**
	 * Un Gestionnaire d'entite
	 */
	protected EntityManager entityManager;
	
	/**
	 * Objet à valider
	 */
	protected Object entity;
	
	/* (non-Javadoc)
	 * @see com.yashiro.persistence.utils.annotations.dao.datamodel.IJPAConstraintValidator#init(javax.persistence.EntityManager, java.lang.Object)
	 */
	@Override
	public void init(EntityManager entityManager, Object entity) {
		
		// Initialisation des parametres
		this.entityManager = entityManager;
		this.entity = entity;
	}
	
	@Override
	public void validateIntegrityConstraint() {
		
		// Validation des contraintes d'integrites
		ValidatorEngine.validate(entity);
	}
}
