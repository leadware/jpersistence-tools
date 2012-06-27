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
package com.bulk.persistence.tools.validator.jsr303ext.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import com.bulk.persistence.tools.validator.jsr303ext.PhoneNumberRule;

/**
 * Annotation de la règle de validation permettant de controler que la valeur d'une propriété
 * represente bien un numero de telephone
 * @author Jean-Jacques ETUNÈ NGI
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberRule.class)
@Documented
public @interface PhoneNumber {
	
	/**
	 * Méthode d'obtention du message  en cas de violation de la règle
	 * @return	Message en cas de violation de la règle
	 */
	String message() default "javax.validation.ext.phonenumber.fail";
	
	/**
	 * Méthode d'obtention des Groupes de validation du validateur
	 * @return Groupes de validation du validateur
	 */
	Class<?>[] groups() default {};
	
	/**
	 * Méthode d'obtention du payload de validation
	 * @return	Payload de validation
	 */
	Class<? extends Payload>[] payload() default {};

	/**
	 * Méthode permettant de savoir si on controle la chaine vide
	 * @return	Etat de contole de la chaine vide
	 */
	boolean matchOnEmpty() default true;
}
