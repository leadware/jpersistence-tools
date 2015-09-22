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
package net.leadware.persistence.tools.generator.base;

import java.lang.annotation.Annotation;

import javax.persistence.EntityManager;

import net.leadware.persistence.tools.api.dao.constants.DAOMode;
import net.leadware.persistence.tools.api.dao.constants.DAOValidatorEvaluationTime;
import net.leadware.persistence.tools.api.generator.base.IDAOGeneratorManager;
import net.leadware.persistence.tools.core.dao.utils.DAOValidatorHelper;

/**
 * Classe de base des implementation des gestionnaire de generateurs 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 22 sept. 2015 - 18:02:51
 */
public abstract class AbstractDAOGeneratorManager implements IDAOGeneratorManager<Annotation> {
	
	/**
	 * Le gestionnaire d'entites
	 */
	protected EntityManager entityManager;
	
	/**
	 * L'annotation en cours
	 */
	protected Annotation annotation;
	
	/**
	 * Temps d'evaluation systeme
	 */
	protected DAOValidatorEvaluationTime systemEvaluationTime;
	
	/**
	 * Mode DAO du systeme
	 */
	protected DAOMode systemDAOMode;
	
	/*
	 * (non-Javadoc)
	 * @see net.leadware.persistence.tools.api.generator.base.IDAOGeneratorManager#initialize(java.lang.annotation.Annotation, javax.persistence.EntityManager, net.leadware.persistence.tools.api.dao.constants.DAOMode, net.leadware.persistence.tools.api.dao.constants.DAOValidatorEvaluationTime)
	 */
	@Override
	public void initialize(Annotation annotation, EntityManager entityManager, DAOMode mode, DAOValidatorEvaluationTime evaluationTime) {
		
		// Initialisation de l'annotation en cours
		this.annotation = annotation;
		
		// Positionnement du gestionnaire d'entites
		this.entityManager = entityManager;
		
		// Initialisation du mode DAO
		this.systemDAOMode = mode;
		
		// Initialisation de l'instant d'evaluation
		this.systemEvaluationTime = evaluationTime;
	}
	
	/**
	 * methode permettant de tester si l'annotation doit-etre executee
	 * @return	Etat d'execution de l'annotation
	 */
	protected boolean isProcessable() {
		
		// Comparaison des modes
		boolean correctMode = DAOValidatorHelper.arraryContains(getAnnotationMode(), this.systemDAOMode);
		
		// Comparaison des instants d'evaluation
		boolean correctTime = DAOValidatorHelper.arraryContains(getAnnotationEvaluationTime(), this.systemEvaluationTime);
		
		// On retourne la comparaison des deux
		return correctMode && correctTime;
	}
	
	/**
	 * Methode permettant d'obtention des modes DAO de l'annotation en cours
	 * @return	Tableau des modes de DAO
	 */
	protected abstract DAOMode[] getAnnotationMode();
	
	/**
	 * Methode permettant d'obtention des instant d'evalutaion DAO de l'annotation en cours
	 * @return	Tableau des modes de DAO
	 */
	protected abstract DAOValidatorEvaluationTime[] getAnnotationEvaluationTime();
}