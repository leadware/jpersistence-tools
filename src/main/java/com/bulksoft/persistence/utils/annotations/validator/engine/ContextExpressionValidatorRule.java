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

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bulksoft.persistence.utils.annotations.validator.ContextExpressionValidator;
import com.bulksoft.persistence.utils.dao.constant.DAOMode;
import com.bulksoft.persistence.utils.dao.constant.DAOValidatorEvaluationTime;
import com.bulksoft.persistence.utils.dao.tools.DAOValidatorHelper;

/**
 * Classe d'implementation de la regle de validation @ContextExpressionValidator
 * @author Jean-Jacques
 * @version 1.0
 */
public class ContextExpressionValidatorRule implements IDAOValidator<ContextExpressionValidator> {
	
	/**
	 * Un logger
	 */
	protected Log logger = LogFactory.getLog(ContextExpressionValidatorRule.class);
	
	/**
	 * Temps d'evaluation systeme
	 */
	protected DAOValidatorEvaluationTime systemEvaluationTime;
	
	/**
	 * Mode DAO du systeme
	 */
	protected DAOMode systemDAOMode;
	
	/**
	 * Annotation en cours
	 */
	protected ContextExpressionValidator annotation;
	
	@Override
	public void initialize(ContextExpressionValidator annotation, EntityManager entityManager, DAOMode systemMode, DAOValidatorEvaluationTime systemEvaluationTime) {
		
		// Sauvegarde des parametres
		this.annotation = annotation;
		this.systemDAOMode = systemMode;
		this.systemEvaluationTime = systemEvaluationTime;
	}

	@Override
	public void processValidation(Object entity) {
		
		// Si on ne doit pas evaluer cette annotation
		if(!this.isProcessable()) {
			
			// Un log
			logger.trace("ContextExpressionValidatorRule#processValidation - Not Processable");
			
			// On sort
			return;
		}
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
}
