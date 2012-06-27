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

import com.bulksoft.persistence.utils.dao.constant.DAOMode;
import com.bulksoft.persistence.utils.dao.constant.DAOValidatorEvaluationTime;

/**
 * Interface des classes implementant la logique de validation d'une 
 * Fonction-Annotation
 * @author Jean-Jacques ETUNÈ NGI
 */
public interface IDAOValidator<A extends Annotation> {
	
	/**
	 * Methode d'initialisation de la classe d'implementation de la logique de validation
	 * @param annotation	Annotation en cours
	 */
	public void initialize(A annotation, EntityManager entityManager, DAOMode systemMode, DAOValidatorEvaluationTime systemEvaluationTime);
	
	/**
	 * Methode d'obtention des parametres du message a afficher
	 * @return	Tableau des parametres du message a afficher
	 */
	public Object[] getMessageParameters(Object entity);
	
	/**
	 * Methode d'execution de la validation sur une entite donnee
	 * @param entity	Entite a valider
	 */
	public void processValidation(Object entity);
}
