/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.bulk.persistence.tools.validator;

import java.util.List;
import javax.persistence.Query;

import com.bulk.persistence.tools.api.exceptions.DAOValidationException;
import com.bulk.persistence.tools.api.validator.annotations.NotEmptyDAOValidator;
import com.bulk.persistence.tools.dao.api.constants.DAOMode;
import com.bulk.persistence.tools.dao.api.constants.DAOValidatorEvaluationTime;
import com.bulk.persistence.tools.dao.api.constants.ValidatorExpressionType;
import com.bulk.persistence.tools.validator.base.AbstractExpressionBasedDAOValidatorRule;

/**
 * Classe d'implementation de la regle de validation @NotEmptyDAOValidator
 * @author Jean-Jacques ETUNÈ NGI
 */
@SuppressWarnings("unchecked")
public class NotEmptyDAOValidatorRule extends AbstractExpressionBasedDAOValidatorRule {
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.validator.IDAOValidator#processValidation(java.lang.Object)
	 */
	@Override
	public void processValidation(Object entity) {
		
		// Si on ne doit pas evaluer cette annotation
		if(!this.isProcessable()) {
			
			// On sort
			return;
		}
		
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

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.validator.base.AbstractExpressionBasedDAOValidatorRule#getExpression()
	 */
	@Override
	protected String getExpression() {
		
		// On caste l'annotation
		NotEmptyDAOValidator notNull = (NotEmptyDAOValidator) this.annotation;
		
		// On retourne l'expression
		return notNull.expr();
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.validator.base.AbstractExpressionBasedDAOValidatorRule#getType()
	 */
	@Override
	protected ValidatorExpressionType getType() {
		
		// On caste l'annotation
		NotEmptyDAOValidator notNull = (NotEmptyDAOValidator) this.annotation;
		
		// On retourne le type de validation
		return notNull.type();
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.validator.base.AbstractExpressionBasedDAOValidatorRule#getMessage()
	 */
	@Override
	protected String getMessage() {
		
		// On caste l'annotation
		NotEmptyDAOValidator notNull = (NotEmptyDAOValidator) this.annotation;
		
		// On retourne le type de validation
		return notNull.message();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.validator.base.AbstractExpressionBasedDAOValidatorRule#getAnnotationEvaluationTime()
	 */
	@Override
	protected DAOValidatorEvaluationTime[] getAnnotationEvaluationTime() {
		
		// On caste l'annotation
		NotEmptyDAOValidator notEmpty = (NotEmptyDAOValidator) this.annotation;
		
		// On retourne le type de validation
		return notEmpty.evaluationTime();
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.validator.base.AbstractExpressionBasedDAOValidatorRule#getAnnotationMode()
	 */
	@Override
	protected DAOMode[] getAnnotationMode() {
		
		// On caste l'annotation
		NotEmptyDAOValidator notEmpty = (NotEmptyDAOValidator) this.annotation;
		
		// On retourne le type de validation
		return notEmpty.mode();
	}
}
