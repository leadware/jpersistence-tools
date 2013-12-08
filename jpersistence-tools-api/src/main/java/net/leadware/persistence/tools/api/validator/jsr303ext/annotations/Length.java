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
package net.leadware.persistence.tools.api.validator.jsr303ext.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import net.leadware.persistence.tools.api.validator.jsr303ext.LengthRule;


/**
 * Annotation permettant de verifier que la longueur d'une chaine
 * ou d'une collection se trouve dans un intervalle
 * @author Jean-Jacques ETUNÈ NGI
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LengthRule.class)
@Documented
public @interface Length {
	
	/**
	 * Méthode d'obtention du message  en cas de violation de la règle
	 * @return	Message en cas de violation de la règle
	 */
	String message() default "javax.validation.ext.length.fail";
	
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
	 * Méthode d'obtention de la longueur minimale
	 * @return longueur minimale
	 */
	public int min() default 0;
	
	/**
	 * Méthode d'obtention de la longueur maximale
	 * @return longueur maximale
	 */
	public int max() default Integer.MAX_VALUE;
	
	/**
	 * Méthode de positionnement de la validation en cas d'objet null
	 * @return	etat de validation en cas d'objet null
	 */
	public boolean acceptNullObject() default false;
	
	/**
	 * Méthode de positionnement de la suppression des espaces extérieurs
	 * @return	etat de suppression des espaces extérieurs
	 */
	public boolean trimString() default true;
}
