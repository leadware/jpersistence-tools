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

import javax.persistence.EntityManager;

import net.leadware.persistence.tools.api.generator.IFieldGenerator;

/**
 * Classe de base des generateurs de type IFieldGenerator 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 22 sept. 2015 - 19:25:20
 */
public abstract class AbstractFieldGenerator implements IFieldGenerator {

	/**
	 * Le gestionnaire d'entites
	 */
	protected EntityManager entityManager;
	
	/**
	 * Entite cible
	 */
	protected Object entity;
	
	/**
	 * Nom de la propriete cible
	 */
	protected String fieldName;
	
	/* (non-Javadoc)
	 * @see net.leadware.persistence.tools.api.generator.IFieldGenerator#setEntityManager(javax.persistence.EntityManager)
	 */
	@Override
	public void setEntityManager(EntityManager entityManager) {
		
		// Positionnement du gestionnaire d'entite
		this.entityManager = entityManager;
	}
	
	/* (non-Javadoc)
	 * @see net.leadware.persistence.tools.api.generator.IFieldGenerator#setEntity(java.lang.Object)
	 */
	@Override
	public void setEntity(Object entity) {
		
		// Initialisation de l'entite cible
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see net.leadware.persistence.tools.api.generator.IFieldGenerator#setFieldName(java.lang.String)
	 */
	@Override
	public void setFieldName(String fieldName) {
		
		// Initialisation du nom du champ cible
		this.fieldName = fieldName;
	}
	
	/* (non-Javadoc)
	 * @see net.leadware.persistence.tools.api.generator.IFieldGenerator#initialize()
	 */
	@Override
	public void initialize() {}
}
