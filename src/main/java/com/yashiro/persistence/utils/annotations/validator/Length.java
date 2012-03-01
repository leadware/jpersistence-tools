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
package com.yashiro.persistence.utils.annotations.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

import com.yashiro.persistence.utils.annotations.validator.engine.LengthRule;

/**
 * Annotation permettant de verifier que la longueur d'une chaine
 * ou d'une collection se trouve dans un intervalle
 * @author Jean-Jacques
 * @version 1.0
 */
@Documented
@ValidatorClass(value = LengthRule.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {
	
	/**
	 * Methode d'obtention de la longueur minimale
	 * @return longueur minimale
	 */
	public int min() default 0;
	
	/**
	 * Methode d'obtention de la longueur maximale
	 * @return longueur maximale
	 */
	public int max() default Integer.MAX_VALUE;
	
	/**
	 * Methode d'obtention de l'etat de validation en cas d'objet null
	 * @return	etat de validation en cas d'objet null
	 */
	public boolean validOnNullObject() default false;
	
	/**
	 * Methode d'obtention du Message d'erreur
	 * @return	Message d'erreur
	 */
	public String message() default "hibernate.validator.length.message";
}
