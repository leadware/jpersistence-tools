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

import java.lang.annotation.Annotation;
import javax.persistence.EntityManager;

import com.bulk.persistence.tools.api.validator.annotations.CheckEntityExistValidator;
import com.bulk.persistence.tools.api.validator.annotations.InstanceWithFieldValidator;
import com.bulk.persistence.tools.dao.api.constants.DAOMode;
import com.bulk.persistence.tools.dao.api.constants.DAOValidatorEvaluationTime;
import com.bulk.persistence.tools.dao.api.constants.ValidatorExpressionType;

/**
 * Classe d'implementation de la regle de controle @CheckEntityExistValidator
 * @author Jean-Jacques ETUNÈ NGI
 */
public class CheckEntityExistValidatorRule implements IDAOValidator<CheckEntityExistValidator> {

	/**
	 * Le gestionnaire d'entites
	 */
	protected EntityManager entityManager;
	
	/**
	 * L'annotation en cours
	 */
	protected CheckEntityExistValidator annotation;
	
	/**
	 * Temps d'evaluation systeme
	 */
	protected DAOValidatorEvaluationTime systemEvaluationTime;
	
	/**
	 * Mode DAO du systeme
	 */
	protected DAOMode systemDAOMode;
	
	/**
	 * Validateur de taille
	 */
	protected InstanceWithFieldValidatorRule instanceValidator = new InstanceWithFieldValidatorRule();
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.validator.IDAOValidator#initialize(java.lang.annotation.Annotation, javax.persistence.EntityManager, com.bulksoft.persistence.utils.dao.constant.DAOMode, com.bulksoft.persistence.utils.dao.constant.DAOValidatorEvaluationTime)
	 */
	@Override
	public void initialize(CheckEntityExistValidator annotation, EntityManager entityManager, DAOMode mode, DAOValidatorEvaluationTime evaluationTime) {
		
		// Sauvegarde des parametres
		this.annotation = annotation;
		this.entityManager = entityManager;
		this.systemDAOMode = mode;
		this.systemEvaluationTime = evaluationTime;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.validator.IDAOValidator#processValidation(java.lang.Object)
	 */
	@Override
	public void processValidation(Object entity) {
		
		// Instanciation d'une annotation de validation de taille
		InstanceWithFieldValidator instanceAnnotation = new InstanceWithFieldValidator() {

			@Override
			public String contextField() {
				
				// On positionne le champ contextuel
				return annotation.contextIDField();
			}

			@Override
			public DAOValidatorEvaluationTime[] evaluationTime() {
				
				// On positionne le temps d'evaluation
				return annotation.evaluationTime();
			}

			@Override
			public int max() {
				
				// On positionne la valeur MAX
				return Integer.MAX_VALUE;
			}

			@Override
			public String message() {
				
				// On positionne le message
				return annotation.message();
			}

			@Override
			public int min() {
				
				// On positionne la valeur min
				return 1;
			}

			@Override
			public DAOMode[] mode() {
				
				// On positionne le mode
				return annotation.mode();
			}

			@Override
			public String persistentField() {
				
				// On positionne le champ persistant
				return annotation.idField();
			}

			@Override
			public Class<?> targetClass() {
				
				// On positionne la classe cible
				return annotation.targetClass();
			}

			@Override
			public ValidatorExpressionType type() {
				
				// On positionne le type d'expression
				return annotation.type();
			}

			@Override
			public Class<? extends Annotation> annotationType() {
				
				// On retourne le type de l'annotation
				return InstanceWithFieldValidator.class;
			}

			@Override
			public String[] parameters() {
				
				// On retourne la liste d'expression de parametres
				return annotation.parameters();
			}
		};
		
		// Initialisation du validateur de regle
		instanceValidator.initialize(instanceAnnotation, this.entityManager, this.systemDAOMode, this.systemEvaluationTime);
		
		// On valide
		instanceValidator.processValidation(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.validator.IDAOValidator#getMessageParameters(java.lang.Object)
	 */
	@Override
	public Object[] getMessageParameters(Object entity) {
		
		// On retourne null
		return null;
	}
}
