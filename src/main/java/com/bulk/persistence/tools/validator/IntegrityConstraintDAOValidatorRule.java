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

import java.lang.reflect.Method;

import javax.persistence.EntityManager;

import com.bulk.persistence.tools.validator.annotations.IntegrityConstraintDAOValidator;
import com.bulk.persistence.tools.validator.engine.JSR303ValidatorEngine;
import com.bulksoft.persistence.utils.dao.constant.DAOMode;
import com.bulksoft.persistence.utils.dao.constant.DAOValidatorEvaluationTime;
import com.bulksoft.persistence.utils.dao.tools.DAOValidatorHelper;

/**
 * Classe d'implementation de la validation par defaut des operations d'enregistrement et de mise a jour
 * @author Jean-Jacques
 * @version 2.0
 */
public class IntegrityConstraintDAOValidatorRule implements IDAOValidator<IntegrityConstraintDAOValidator> {

	/**
	 * Temps d'evaluation systeme
	 */
	protected DAOValidatorEvaluationTime systemEvaluationTime;
	
	/**
	 * Mode DAO du systeme
	 */
	protected DAOMode systemDAOMode;
	
	/**
	 * Moteur de validation
	 */
	protected JSR303ValidatorEngine validatorEngine;
	
	/**
	 * Annotation en cours
	 */
	protected IntegrityConstraintDAOValidator annotation;
	
	@Override
	public void initialize(IntegrityConstraintDAOValidator annotation, EntityManager entityManager, DAOMode mode, DAOValidatorEvaluationTime evaluationTime) {
		
		// On initialise les champs
		this.annotation = annotation;
		this.systemDAOMode = mode;
		this.systemEvaluationTime = evaluationTime;
		
		// Initialisation du moteur de validation
		initializeValidatorEngine(this.annotation.validatorEngineClass());
	}

	@Override
	public void processValidation(Object entity) {

		// Si on ne doit pas evaluer cette annotation
		if(!this.isProcessable()) {
			
			// On sort
			return;
		}
		
		// On valide les contraintes d'integrites
		validatorEngine.validate(entity);
	}

	/**
	 * methode permettant de tester si l'annotation doit-etre executee
	 * @return	Etat d'execution de l'annotation
	 */
	protected boolean isProcessable() {
		
		// Comparaison des modes
		boolean correctMode = DAOValidatorHelper.arraryContains(annotation.mode(), this.systemDAOMode);
		
		// Comparaison des instants d'evaluation
		boolean correctTime = DAOValidatorHelper.arraryContains(annotation.evaluationTime(), this.systemEvaluationTime);
		
		// On retourne la comparaison des deux
		return correctMode && correctTime;
	}
	
	@Override
	public Object[] getMessageParameters(Object entity) {
		
		// On retourne null
		return null;
	}
	
	/**
	 * Méthode d'initialisation du moteur de validation
	 * @param validatorEngineClass	Classe du moteur de validation
	 */
	private void initializeValidatorEngine(Class<? extends JSR303ValidatorEngine> validatorEngineClass) {
		
		try {

			// Obtention de la méthode statique de construction de l'instance
			Method getDefaultInstance = validatorEngineClass.getMethod("getDefaultInstance");
			
			// Instanciation
			validatorEngine = (JSR303ValidatorEngine) getDefaultInstance.invoke(validatorEngineClass);
			
		} catch (Exception e) {
			
			// Trace
			e.printStackTrace();
		}
	}
}
