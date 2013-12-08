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

import net.leadware.persistence.tools.api.validator.annotations.SizeDAOValidator;
import net.leadware.persistence.tools.api.validator.annotations.SizeDAOValidators;
import net.leadware.persistence.tools.validator.base.AbstractDAOValidatorsRule;

/**
 * Classe d'implementation de la regle de validation @SizeDAOValidators
 * @author Jean-Jacques ETUNÈ NGI
 */
public class SizeDAOValidatorsRule extends AbstractDAOValidatorsRule {
	
	/*
	 * (non-Javadoc)
	 * @see net.leadware.persistence.tools.validator.IDAOValidator#processValidation(java.lang.Object)
	 */
	@Override
	public void processValidation(Object entity) {
		
		// Si la liste des validateurs est vide
		if(this.validators == null || this.validators.length == 0) {
						
			// On sort
			return;
		}
		
		// Classe d'execution de la validation
		SizeDAOValidatorRule rule = new SizeDAOValidatorRule();
		
		// Parcours de la liste
		for (Annotation annotation : this.validators) {
			
			// On initialize
			rule.initialize(annotation, entityManager, this.systemDAOMode, this.systemEvaluationTime);
			
			// On valide
			rule.processValidation(entity);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.leadware.persistence.tools.validator.base.AbstractDAOValidatorsRule#getValidators()
	 */
	@Override
	protected Annotation[] getValidators() {
		
		// Si l'annotation en cours est nulle
		if(this.annotation == null) {
			
			// On retourne null
			return null;
		}
		
		// Si l'annotation en cours n'est pas de l'instance
		if(!(annotation instanceof SizeDAOValidators)) {
			
			// On retourne null
			return null;
		}
		
		// On caste
		SizeDAOValidators castedValidators = (SizeDAOValidators) annotation;
		
		// Liste des Annotations
		SizeDAOValidator[] tValidators = castedValidators.value();
		
		// On retourne la liste
		return tValidators;
	}
}
