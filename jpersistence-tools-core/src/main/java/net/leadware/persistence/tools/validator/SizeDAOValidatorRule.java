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
package net.leadware.persistence.tools.validator;

import java.util.List;

import javax.persistence.Query;

import net.leadware.persistence.tools.api.dao.constants.DAOMode;
import net.leadware.persistence.tools.api.dao.constants.DAOValidatorEvaluationTime;
import net.leadware.persistence.tools.api.dao.constants.ValidatorExpressionType;
import net.leadware.persistence.tools.api.exceptions.DAOValidationException;
import net.leadware.persistence.tools.api.validator.annotations.SizeDAOValidator;
import net.leadware.persistence.tools.validator.base.AbstractExpressionBasedDAOValidatorRule;

/**
 * Classe d'implementation de la regle de validation @SizeDAOValidator
 * @author Jean-Jacques ETUNÈ NGI
 */
@SuppressWarnings("unchecked")
public class SizeDAOValidatorRule extends AbstractExpressionBasedDAOValidatorRule {
	
	/*
	 * (non-Javadoc)
	 * @see net.leadware.persistence.tools.validator.IDAOValidator#processValidation(java.lang.Object)
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
			
			// On construit la requte
			Query query = this.buildQuery(entity);
			
			// Execution
			List<Object> result = query.getResultList();
			
			// La Taille
			long size = 0;
			
			// Le minimum
			long min = ((SizeDAOValidator) this.annotation).min();
			
			// Le max
			long max = ((SizeDAOValidator) this.annotation).max();
						
			// Si la liste n'est pas vide
			if(result != null && result.size() > 0) size = result.size();
						
			// On compare
			if((min > size) || (max < size)) {
				
				// On lve une exception
				throw new DAOValidationException(getMessage(), getMessageParameters(entity));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.leadware.persistence.tools.validator.base.AbstractExpressionBasedDAOValidatorRule#getExpression()
	 */
	@Override
	protected String getExpression() {
		
		// On caste l'annotation
		SizeDAOValidator castedAnnotation = (SizeDAOValidator) this.annotation;
		
		// On retourne l'expression
		return castedAnnotation.expr();
	}

	/*
	 * (non-Javadoc)
	 * @see net.leadware.persistence.tools.validator.base.AbstractExpressionBasedDAOValidatorRule#getType()
	 */
	@Override
	protected ValidatorExpressionType getType() {
		
		// On caste l'annotation
		SizeDAOValidator castedAnnotation = (SizeDAOValidator) this.annotation;
		
		// On retourne le type de validation
		return castedAnnotation.type();
	}

	/*
	 * (non-Javadoc)
	 * @see net.leadware.persistence.tools.validator.base.AbstractExpressionBasedDAOValidatorRule#getMessage()
	 */
	@Override
	protected String getMessage() {
		
		// On caste l'annotation
		SizeDAOValidator castedAnnotation = (SizeDAOValidator) this.annotation;
		
		// On retourne le type de validation
		return castedAnnotation.message();
	}

	/*
	 * (non-Javadoc)
	 * @see net.leadware.persistence.tools.validator.base.AbstractExpressionBasedDAOValidatorRule#getAnnotationEvaluationTime()
	 */
	@Override
	protected DAOValidatorEvaluationTime[] getAnnotationEvaluationTime() {
		
		// On caste l'annotation
		SizeDAOValidator castedAnnotation = (SizeDAOValidator) this.annotation;
		
		// On retourne le type de validation
		return castedAnnotation.evaluationTime();
	}

	/*
	 * (non-Javadoc)
	 * @see net.leadware.persistence.tools.validator.base.AbstractExpressionBasedDAOValidatorRule#getAnnotationMode()
	 */
	@Override
	protected DAOMode[] getAnnotationMode() {
		
		// On caste l'annotation
		SizeDAOValidator castedAnnotation = (SizeDAOValidator) this.annotation;
		
		// On retourne le type de validation
		return castedAnnotation.mode();
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.leadware.persistence.tools.validator.base.AbstractExpressionBasedDAOValidatorRule#getMessageParametersExpressions()
	 */
	@Override
	protected String[] getMessageParametersExpressions() {

		// On caste l'annotation
		SizeDAOValidator castedAnnotation = (SizeDAOValidator) this.annotation;
		
		// On retourne la liste des messages
		return castedAnnotation.parameters();
	}
}
