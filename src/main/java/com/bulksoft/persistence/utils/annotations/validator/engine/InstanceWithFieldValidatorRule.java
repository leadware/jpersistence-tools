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
package com.bulksoft.persistence.utils.annotations.validator.engine;

import java.lang.annotation.Annotation;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bulksoft.persistence.utils.annotations.validator.InstanceWithFieldValidator;
import com.bulksoft.persistence.utils.annotations.validator.SizeDAOValidator;
import com.bulksoft.persistence.utils.dao.constant.DAOMode;
import com.bulksoft.persistence.utils.dao.constant.DAOValidatorEvaluationTime;
import com.bulksoft.persistence.utils.dao.constant.ValidatorExpressionType;
import com.bulksoft.persistence.utils.dao.tools.DAOValidatorHelper;

/**
 * Classe d'implementation de la regle de controle
 * @author Jean-Jacques
 * @version 2.0
 */
public class InstanceWithFieldValidatorRule implements IDAOValidator<InstanceWithFieldValidator> {

	/**
	 * Un logger
	 */
	protected Log logger = LogFactory.getLog(InstanceWithFieldValidatorRule.class);
	
	/**
	 * Le gestionnaire d'entites
	 */
	protected EntityManager entityManager;
	
	/**
	 * L'annotation en cours
	 */
	protected InstanceWithFieldValidator annotation;
	
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
	protected SizeDAOValidatorRule sizeValidator = new SizeDAOValidatorRule();
	
	@Override
	public void initialize(InstanceWithFieldValidator annotation, EntityManager entityManager, DAOMode mode, DAOValidatorEvaluationTime evaluationTime) {
		
		// Sauvegarde des parametres
		this.annotation = annotation;
		this.entityManager = entityManager;
		this.systemDAOMode = mode;
		this.systemEvaluationTime = evaluationTime;
	}
	
	@Override
	public void processValidation(Object entity) {
		
		// Un message
		logger.trace("InstanceWithFieldValidatorRule#processValidation");
		
		// Si la classe cible n'est pas specifiee
		if(annotation.targetClass() == null) {

			// Un message
			logger.trace("InstanceWithFieldValidatorRule#processValidation - Classe cible non specifiee");
			
			// On sort
			return;
		}
		
		// Si le champ n'est pas specifie
		if(annotation.contextField() == null || annotation.contextField().trim().length() == 0) {
			
			// Un message
			logger.trace("InstanceWithFieldValidatorRule#processValidation - Champ cible non specifiee");
			
			// On sort
			return;
		}
		
		// Le parametre
		String parameter = annotation.contextField().trim();
		
		// Si le field n'est ni une expression ni une fonction (On habille en expression)
		if(!DAOValidatorHelper.isExpressionContainPattern(parameter, DAOValidatorHelper.ENV_CHAIN_PATTERN) && !DAOValidatorHelper.isExpressionContainPattern(parameter, DAOValidatorHelper.FUNC_CHAIN_PATTERN)) {
			
			// Un message
			logger.trace("InstanceWithFieldValidatorRule#processValidation - Pas d'expression ni de fonction dans le champ");
			
			// On construit le parametre
			parameter = DAOValidatorHelper.SIMPLE_LEFT_DELIMITER + parameter + DAOValidatorHelper.SIMPLE_RIGHT_DELIMITER;
			
		}
				
		// Requete de test
		StringBuffer requestBuffer = new StringBuffer();
		
		requestBuffer.append("from ");
		requestBuffer.append(annotation.targetClass().getName());
		requestBuffer.append(" c where ");
		requestBuffer.append("c.".concat(annotation.persistentField().trim()));
		requestBuffer.append(" = ");
		requestBuffer.append(parameter.trim());
		
		// Un Log
		logger.trace("InstanceWithFieldValidatorRule#processValidation - Builded Request: " + requestBuffer.toString());
		
		// La requete
		final String request = requestBuffer.toString();
		
		// Un Log
		logger.trace("InstanceWithFieldValidatorRule#processValidation - Instanciation d'une annotation de validation de taille");
		
		// Instanciation d'une annotation de validation de taille
		Annotation sizeAnnotation = new SizeDAOValidator() {

			@Override
			public DAOValidatorEvaluationTime[] evaluationTime() {
				
				// On positionne la min
				return annotation.evaluationTime();
			}

			@Override
			public String expr() {
				
				// On positionne l'expression
				return request;
			}

			@Override
			public long max() {
				
				// On positionne la max
				return annotation.max();
			}

			@Override
			public String message() {
				
				// On positionne la min
				return annotation.message();
			}

			@Override
			public long min() {
				
				// On positionne la min
				return annotation.min();
			}

			@Override
			public DAOMode[] mode() {
				
				// On positionne le mode
				return annotation.mode();
			}

			@Override
			public ValidatorExpressionType type() {
				
				// On positionne le type
				return annotation.type();
			}

			@Override
			public Class<? extends Annotation> annotationType() {
				
				// On positionne la classe
				return SizeDAOValidator.class;
			}

			@Override
			public String[] parameters() {
				
				// On retourne la liste des expressions de parametres
				return annotation.parameters();
			}
			
		};
		
		// Un Log
		logger.trace("InstanceWithFieldValidatorRule#processValidation - Initialisation de l'annotation de validation de taille");
		
		// Initialisation du validateur de regle
		sizeValidator.initialize(sizeAnnotation, this.entityManager, this.systemDAOMode, this.systemEvaluationTime);
		
		// On valide
		sizeValidator.processValidation(entity);
	}

	@Override
	public Object[] getMessageParameters(Object entity) {
		
		// On retourne null
		return null;
	}
}
