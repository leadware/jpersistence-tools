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

import com.bulk.persistence.tools.api.validator.jsr303ext.annotations.IPV4;

/**
 * Regle de validation de l'IPV4
 * @author Jean-Jacques ETUNÈ NGI
 */
public class IPV4Rule implements ConstraintValidator<IPV4, String> {

	/*
	 * (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(IPV4 annotation) {}

	/*
	 * (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintContext) {
		
		// Si l'objet est null : false
		if(value == null) return false;
		
		// Si l'objet n'est pas une chaine : false
		if(!(value instanceof String)) return false;
		
		// On caste
		String stringValue = (String) value;
		
		// Si la chaine est vide : false
		if(stringValue == null || stringValue.length() == 0) return false;
		
		// Obtention du tableau de valeurs
		String[] parts = stringValue.split("\\.");
		
		// Si la tableau n'a pas une taille de 4
		if(parts == null || parts.length < 4) return false;
		
		// Pattern Numerique
		Pattern pattern = Pattern.compile("\\d+");
		
		// Parcours du tableau
		for (String part : parts) {

			// Si ce n'est pas un Numerique
			if(!pattern.matcher(part).matches()) return false;
		}
		
		// On retourne true
		return true;
	}

}
