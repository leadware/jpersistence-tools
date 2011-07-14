/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine;

/**
 * Classe de validation par defaut des contraintes sur l'entite 
 * (Elle n'effectue que la validation des contraintes d'integrites)
 * @author Jean-Jacques
 * @version 1.0
 */
public class DefaultJPAConstraintValidator extends AbstractJPAConstraintValidator {

	/* (non-Javadoc)
	 * @see com.yashiro.persistence.utils.annotations.validator.engine.IJPAConstraintValidator#validateReferentialConstraint()
	 */
	@Override
	public void validateReferentialConstraint() {}

}
