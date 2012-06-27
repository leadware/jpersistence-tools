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
package com.bulk.persistence.tools.validator.jsr303ext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bulk.persistence.tools.validator.jsr303ext.annotations.AuthorizedStringValuesList;

/**
 * Classe implementant la regle de validation contrôlant que la valeur d'une propriété
 * correspond à une valeur d'une liste donnée
 * @author Jean-Jacques ETUNÈ NGI
 */
public class AuthorizedStringValuesListRule implements ConstraintValidator<AuthorizedStringValuesList, String> {

	/**
	 * Liste des Valeurs permises
	 */
	private String[] values = new String[]{};
	
	/**
	 * Etat de prise en compte de la casse
	 */
	private boolean caseSensitive = false;
	
	/*
	 * (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(AuthorizedStringValuesList annotation) {
		
		// Si l'annotation est nulle : on sort
		if(annotation == null) return;
		
		// Obtention de la liste
		String[] annotationValues = annotation.values();
		
		// Si la liste de l'annotation est vide : on sort
		if(annotationValues == null || annotationValues.length == 0) return;
		
		// On affecte la liste de valeurs
		values = annotationValues;
		
		// On affecte l'état de prise en compte de la casse
		caseSensitive = annotation.caseSensitive();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintContext) {
		
		// Si la liste des valeurs permisse est vide : true
		if(values == null || values.length == 0) return true;
		
		// Si l'objet est null : false
		if(value == null) return false;
		
		// Si l'objet n'est pas une chaine : false
		if(!(value instanceof String)) return false;
		
		// On caste
		String stringValue = (String) value;
		
		// Si la chaîne est vide : false
		if(stringValue == null || stringValue.length() == 0) return false;
		
		// On recherche la valeur
		for (String authValue : values) {
			
			// Si la Casse est prise en compte
			if(caseSensitive) {
				
				// Si la valeur Correspond : true
				if(authValue.equals(stringValue)) return true;
				
			} else {

				// Si la valeur Correspond : true
				if(authValue.equalsIgnoreCase(stringValue)) return true;
			}
		}
		
		// On retourne false
		return false;
	}
}
