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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.leadware.persistence.tools.api.validator.jsr303ext.annotations.Interval;


/**
 * Classe implementant la regle de validation controlant que la valeur d'une proprité se trouve dans un intervalle donne 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 31 janv. 2018 - 14:29:30
 */
public class IntervalRule implements ConstraintValidator<Interval, Number> {
	
	/**
	 * Valeur minimum de l'intervalle
	 */
	private Number min = 0;
	
	/**
	 * Valeur maximale de l'intervalle
	 */
	private Number max = 100;
	
	/*
	 * (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
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

	/*
	 * (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(Number value, ConstraintValidatorContext constraintContext) {
		
		// Si l'objet est null
		if(value == null) return false;

		// On retourne la comparaison
		return (min.doubleValue() <= value.doubleValue()) && (max.doubleValue() >= value.doubleValue());
	}
}
