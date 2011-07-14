/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine;

import javax.persistence.EntityManager;

import com.yashiro.persistence.utils.annotations.validator.IdentityDAOValidator;
import com.yashiro.persistence.utils.dao.constant.DAOMode;
import com.yashiro.persistence.utils.dao.constant.DAOValidatorEvaluationTime;

/**
 * Classe d'implementation de la regle de validation Identity
 * @author Jean-Jacques
 * @version 2.0
 */
public class IdentityDAOValidatorRule implements IDAOValidator<IdentityDAOValidator> {

	@Override
	public void initialize(IdentityDAOValidator annotation, EntityManager entityManager, DAOMode mode, DAOValidatorEvaluationTime evaluationTime) {}

	@Override
	public void processValidation(Object entity) {}

	@Override
	public Object[] getMessageParameters(Object entity) {
		
		// On retourne null
		return null;
	}

}
