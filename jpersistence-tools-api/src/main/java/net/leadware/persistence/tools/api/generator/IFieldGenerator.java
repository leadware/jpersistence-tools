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
package net.leadware.persistence.tools.api.generator;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.EntityManager;

/**
 * Interface de la classe de generation 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 22 sept. 2015 - 15:12:07
 */
public interface IFieldGenerator {

	/**
	 * Methode permettant de positionner l'entite cible
	 * @param entity	 Entite cible
	 */
	public void setEntity(Object entity);
	
	/**
	 * Methode permettant de positionner le champ cible de la generation 
	 * @param field Champ cible de la generation	
	 */
	public void setField(Field field);

	/**
	 * Methode permettant de positionner le gestionnaire d'entites de generation
	 * @param generatorEntityManager Gestionnaire d'entites de generation
	 */
	public void setGeneratorEntityManager(EntityManager generatorEntityManager);
	
	/**
	 * Methode permettant de positionner le gestionnaire d'entites
	 * @param entityManager Gestionnaire d'entites
	 */
	public void setEntityManager(EntityManager entityManager);
	
	/**
	 * Methode permettant d'initialiser le generateur
	 */
	public void initialize();
	
	/**
	 * Methode permettant de generation
	 * @return	Valeur generee
	 */
	public Serializable generate();
}
