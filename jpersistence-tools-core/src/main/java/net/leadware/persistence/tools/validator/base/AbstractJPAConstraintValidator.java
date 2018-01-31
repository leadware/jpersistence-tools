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
package net.leadware.persistence.tools.validator.base;

import javax.persistence.EntityManager;

import net.leadware.persistence.tools.api.validator.base.IJPAConstraintValidator;
import net.leadware.persistence.tools.api.validator.jsr303ext.engine.JSR303ValidatorEngine;


/**
 * Classe representant une implementation partielle du Validateur referentiel 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 31 janv. 2018 - 14:40:44
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
	
	/**
	 * Moteur de validation
	 */
	protected JSR303ValidatorEngine validatorEngine = JSR303ValidatorEngine.getDefaultInstance();
	
	/*
	 * (non-Javadoc)
	 * @see net.leadware.persistence.tools.validator.IJPAConstraintValidator#init(javax.persistence.EntityManager, java.lang.Object)
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
		validatorEngine.validate(entity);
	}
	
	/**
	 * Méthode de mise à jour du moteur de validation
	 * @param validatorEngine Moteur de validation
	 */
	public void setValidatorEngine(JSR303ValidatorEngine validatorEngine) {
		
		// Si le validateur n'est pas null
		if(validatorEngine != null) this.validatorEngine = validatorEngine;
	}
}
