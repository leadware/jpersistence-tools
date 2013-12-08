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
package net.leadware.persistence.tools.api.validator.jsr303ext;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.leadware.persistence.tools.api.validator.jsr303ext.annotations.Length;


/**
 * Classe d'implementation de la règle de gestion de la taille
 * @author Jean-Jacques ETUNÈ NGI
 */
public class LengthRule implements ConstraintValidator<Length, Object> {
	
	/**
	 * Longueur minimale
	 */
	private int min;
	
	/**
	 * Longueur maximale
	 */
	private int max;
	
	/**
	 * Etat de validation en cas d'objet null
	 */
	private boolean acceptNullObject;
	
	/**
	 * Etat de suppression des espaces exterieurs
	 */
	private boolean trimString;
		
	/*
	 * (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(Length annotation) {
		
		// On initialise les parametres
		min = annotation.min();
		max = annotation.max();
		acceptNullObject = annotation.acceptNullObject();
		trimString = annotation.trimString();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext constraintContext) {
		
		// Si l'Objet est null
		if(obj == null) return acceptNullObject;
		
		// Si l'Objet est un tableau
		if(obj.getClass().isArray()) {
			
			// Taille
			int size = Array.getLength(obj);
			
			// On retourne la comparaison
			return (size >= min && size <= max);
		}
		
		// Si l'objet est une collection
		if(obj instanceof Collection<?>) {
			
			// Taille
			int size = ((Collection<?>) obj).size();
			
			// On retourne la comparaison
			return (size >= min && size <= max);
		}

		// Si l'objet est une map
		if(obj instanceof Map<?, ?>) {
			
			// Taille
			int size = ((Map<?, ?>) obj).size();
			
			// On retourne la comparaison
			return (size >= min && size <= max);
		}
		
		// Si l'Objet est une instance de Chaine
		if(obj instanceof String) {
			
			// Chaine locale
			String localString = ((String) obj);
			
			// Si on doit trimmer
			if(trimString) localString = localString.trim();
			
			// Taille
			int size = localString.length();
			
			// On retourne la comparaison
			return (size >= min && size <= max);
		}
		
		// Si c'est autre chose on retourne false
		return false;
	}

}
