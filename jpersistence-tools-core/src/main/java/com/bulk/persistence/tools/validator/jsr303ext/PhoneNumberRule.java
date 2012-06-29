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

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bulk.persistence.tools.api.validator.jsr303ext.annotations.PhoneNumber;

/**
 * Classe implementant la regle de validation contr lant que la valeur d'une propri t 
 * correspond   une valeur d'une liste donn e
 * @author Jean-Jacques ETUNÈ NGI
 */
public class PhoneNumberRule implements ConstraintValidator<PhoneNumber, String> {
	
	/**
	 * Etat de contole de la chaine vide
	 */
	private boolean matchOnEmpty = true;
	
	/*
	 * (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(PhoneNumber annotation) {
		
		// Initialisation de l'Etat de Controle de la chaine vide
		this.matchOnEmpty = annotation.matchOnEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintContext) {
		
		// Si l'objet est null : false
		if(value == null) return matchOnEmpty;
		
		// Si la cha ne est vide : false
		if(value == null || value.length() == 0) return matchOnEmpty;
		
		// On Construit l'expression r guli re de test
		String expression = "\\+{0,1}\\d*";
		
		// Le Pattern representant une chaine AlphaNumerique
		Pattern pattern = Pattern.compile(expression);
		
		// On retourne le r sultat du test
		return pattern.matcher(value).matches();
	}
}
