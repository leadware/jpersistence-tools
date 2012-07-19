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
package com.bulk.persistence.tools.validator.base;

import java.lang.annotation.Annotation;
import javax.persistence.EntityManager;

import com.bulk.persistence.tools.dao.api.constants.DAOMode;
import com.bulk.persistence.tools.dao.api.constants.DAOValidatorEvaluationTime;
import com.bulk.persistence.tools.validator.IDAOValidator;

/**
 * Classe abstraite definissant les bases de l'implementation d'une regle contenant d'autres regles de validation
 * @author Jean-Jacques
 */
public abstract class AbstractDAOValidatorsRule implements IDAOValidator<Annotation> {

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
