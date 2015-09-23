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
package net.leadware.persistence.tools.api.generator.base;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.persistence.EntityManager;

import net.leadware.persistence.tools.api.dao.constants.DAOMode;
import net.leadware.persistence.tools.api.dao.constants.DAOValidatorEvaluationTime;

/**
 * Interface des classes implementant un gestionnaire de generateur 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 22 sept. 2015 - 17:19:29
 */
public interface IDAOGeneratorManager<A extends Annotation> {
	
	/**
	 * Methode d'initialisation de la classe d'implementation de la logique de validation
	 * @param annotation	Annotation en cours
	 */
	public void initialize(A annotation, EntityManager entityManager, DAOMode systemMode, DAOValidatorEvaluationTime systemEvaluationTime);
	
	/**
	 * Methode d'execution de la generation sur une entite donnee
	 * @param entity	Entite cible de la generation
	 * @param field Champ cible de la generation
	 */
	public void processGeneration(Object entity, Field field);
}
