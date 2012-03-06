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
package com.bulksoft.persistence.utils.annotations.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

import com.bulksoft.persistence.utils.annotations.validator.engine.IntervalRule;

/**
 * Annotation de la regle de validation permettant de contrler que la valeur d'une proprite
 * se trouve bien dans un intervalle donne
 * @author Jean-Jacques
 * @version 1.0
 */
@ValidatorClass(value = IntervalRule.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Interval {
	
	/**
	 * Valeur minimum de l'intervalle
	 * @return	Valeur minimum de l'intervalle
	 */
	float min() default 0.0F;
	
	/**
	 * Valeur maximum de l'intervalle
	 * @return	Valeur maximum de l'intervalle
	 */
	float max();
	
	/**
	 * Message d'erreur
	 * @return	Message d'erreur
	 */
	String message() default "Interval.value.out";
}
