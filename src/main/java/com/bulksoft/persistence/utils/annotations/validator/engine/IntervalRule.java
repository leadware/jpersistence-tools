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

import org.hibernate.validator.Validator;

import com.bulksoft.persistence.utils.annotations.validator.Interval;

/**
 * Classe implementant la regle de validation contrlant que la valeur d'une proprit
 * se trouve dans un intervalle donne
 * @author Jean-Jacques
 * @version 1.0
 */
public class IntervalRule implements Validator<Interval> {
	
	/**
	 * Valeur minimum de l'intervalle
	 */
	private float min = 0;
	
	/**
	 * Valeur maximale de l'intervalle
	 */
	private float max = 100;
	
	/* (non-Javadoc)
	 * @see org.hibernate.validator.Validator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(Interval annotation) {
		
		// Si l'annotation est nulle : on sort
		if(annotation == null) return;
		
		// On rcupre la valeur minimum de l'annotation
		min = annotation.min();
		
		// On rcupre la valeur maximum de l'annotation
		max = annotation.max();
	}

	/* (non-Javadoc)
	 * @see org.hibernate.validator.Validator#isValid(java.lang.Object)
	 */
	@Override
	public boolean isValid(Object value) {
		
		// Si l'objet est null
		if(value == null) return false;
		
		try {
			
			// Tentative de cast en Float
			Float floatValue = Float.valueOf(value.toString().trim());
			
			// On retourne la comparaison
			return (min < floatValue.floatValue()) && (floatValue.floatValue() < max);
			
		} catch (Throwable e) {
			
			// On retourne false
			return false;
		}
	}
}
