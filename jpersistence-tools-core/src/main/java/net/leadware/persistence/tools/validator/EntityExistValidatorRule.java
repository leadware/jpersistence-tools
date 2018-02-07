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

import java.lang.annotation.Annotation;

import javax.persistence.EntityManager;

import net.leadware.persistence.tools.api.dao.constants.DAOMode;
import net.leadware.persistence.tools.api.dao.constants.DAOValidatorEvaluationTime;
import net.leadware.persistence.tools.api.dao.constants.ValidatorExpressionType;
import net.leadware.persistence.tools.api.validator.annotations.EntityExistValidator;
import net.leadware.persistence.tools.api.validator.annotations.SizeDAOValidator;
import net.leadware.persistence.tools.api.validator.base.IDAOValidator;

/**
 * Classe d'implementation de la regle de controle @EntityExistValidator 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 31 janv. 2018 - 14:39:39
 */
public class EntityExistValidatorRule implements IDAOValidator<EntityExistValidator>  {

	/**
	 * Le gestionnaire d'entites
	 */
	protected EntityManager entityManager;
	
	/**
	 * L'annotation en cours
	 */
	protected EntityExistValidator annotation;
	
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

	/*
	 * (non-Javadoc)
	 * @see net.leadware.persistence.tools.validator.IDAOValidator#initialize(java.lang.annotation.Annotation, javax.persistence.EntityManager, com.bulksoft.persistence.utils.dao.constant.DAOMode, com.bulksoft.persistence.utils.dao.constant.DAOValidatorEvaluationTime)
	 */
	@Override
	public void initialize(EntityExistValidator annotation, EntityManager entityManager, DAOMode mode, DAOValidatorEvaluationTime evaluationTime) {
		
		// Sauvegarde des parametres
		this.annotation = annotation;
		this.entityManager = entityManager;
		this.systemDAOMode = mode;
		this.systemEvaluationTime = evaluationTime;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.leadware.persistence.tools.validator.IDAOValidator#processValidation(java.lang.Object)
	 */
	@Override
	public void processValidation(Object entity) {

		// Si la classe cible n'est pas specifiee
		if(annotation.targetClass() == null) {
			
			// On sort
			return;
		}
		
		// Si le champ id n'est pas specifie
		if(annotation.idField() == null || annotation.idField().trim().length() == 0) {
			
			// On sort
			return;
		}
		
		// Champ ID
		String ifField = annotation.idField().trim();
		
		// Buffer de la Requete de test
		StringBuffer requestBuffer = new StringBuffer();
		
		// Construction de la requete
		requestBuffer.append("from ");
		requestBuffer.append(annotation.targetClass().getName());
		requestBuffer.append(" c where ");
		requestBuffer.append("c.".concat(ifField));
		requestBuffer.append(" = ");
		requestBuffer.append("${" + ifField + "}");
		
		// La requete
		final String request = requestBuffer.toString();

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
				return 1;
			}

			@Override
			public String message() {
				
				// On positionne la min
				return annotation.message();
			}

			@Override
			public long min() {
				
				// On positionne la min
				return 1;
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

		// Initialisation du validateur de regle
		sizeValidator.initialize(sizeAnnotation, this.entityManager, this.systemDAOMode, this.systemEvaluationTime);
		
		// On valide
		sizeValidator.processValidation(entity);
	}

	/* (non-Javadoc)
	 * @see net.leadware.persistence.tools.validator.IDAOValidator#getMessageParameters(java.lang.Object)
	 */
	@Override
	public String[] getMessageParameters(Object entity) {
		
		// On retourne null
		return null;
	}
	
}
