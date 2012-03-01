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
package com.yashiro.persistence.utils.annotations.validator.engine;

import java.lang.annotation.Annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yashiro.persistence.utils.annotations.validator.SizeDAOValidator;
import com.yashiro.persistence.utils.annotations.validator.SizeDAOValidators;

/**
 * Classe d'implementation de la regle de validation @SizeDAOValidators
 * @author Jean-Jacques
 * @version 2.0
 */
public class SizeDAOValidatorsRule extends AbstractDAOValidatorsRule {
	
	/**
	 * Un logger
	 */
	protected Log logger = LogFactory.getLog(SizeDAOValidatorsRule.class);
	
	@Override
	public void processValidation(Object entity) {
		
		// Un Log
		logger.debug("SizeDAOValidatorsRule#processValidation");
		
		// Si la liste des validateurs est vide
		if(this.validators == null || this.validators.length == 0) {
			
			// Un Log
			logger.debug("SizeDAOValidatorsRule#processValidation - Pas de validateur");
						
			// On sort
			return;
		}
		
		// Classe d'execution de la validation
		SizeDAOValidatorRule rule = new SizeDAOValidatorRule();
		
		// Parcours de la liste
		for (Annotation annotation : this.validators) {
			
			// On initialize
			rule.initialize(annotation, entityManager, this.systemDAOMode, this.systemEvaluationTime);
			
			// On valide
			rule.processValidation(entity);
		}
	}

	@Override
	protected Annotation[] getValidators() {
		
		// Un Log
		logger.debug("SizeDAOValidatorsRule#getValidators");
		
		// Si l'annotation en cours est nulle
		if(this.annotation == null) {

			// Un Log
			logger.debug("SizeDAOValidatorsRule#getValidators - L'annotation en cours est nulle");
			
			// On retourne null
			return null;
		}
		
		// Si l'annotation en cours n'est pas de l'instance
		if(!(annotation instanceof SizeDAOValidators)) {
			
			// Un Log
			logger.debug("SizeDAOValidatorsRule#getValidators - L'annotation en cours n'est pas une instance de @SizeDAOValidators");
			
			// On retourne null
			return null;
		}
		
		// On caste
		SizeDAOValidators castedValidators = (SizeDAOValidators) annotation;

		// Un Log
		logger.debug("SizeDAOValidatorsRule#getValidators - Obtention de la liste des annotations");
		
		// Liste des Annotations
		SizeDAOValidator[] tValidators = castedValidators.value();
		
		// On retourne la liste
		return tValidators;
	}
}
