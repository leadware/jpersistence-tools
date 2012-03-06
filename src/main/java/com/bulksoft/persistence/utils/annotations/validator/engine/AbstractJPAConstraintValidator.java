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


/**
 * Classe representant une implementation partielle du Validateur referentiel
 * @author Jean-Jacques
 * @version 1.0
 */
public abstract class AbstractJPAConstraintValidator implements IJPAConstraintValidator {
	
	/**
	 * Un Gestionnaire d'entite
	 */
	protected EntityManager entityManager;
	
	/**
	 * Objet valider
	 */
	protected Object entity;
	
	/* (non-Javadoc)
	 * @see com.yashiro.persistence.utils.annotations.dao.datamodel.IJPAConstraintValidator#init(javax.persistence.EntityManager, java.lang.Object)
	 */
	@Override
	public void init(EntityManager entityManager, Object entity) {
		
		// Initialisation des parametres
		this.entityManager = entityManager;
		this.entity = entity;
	}
	
	@Override
	public void validateIntegrityConstraint() {
		
		// Validation des contraintes d'integrites
		ValidatorEngine.validate(entity);
	}
}
