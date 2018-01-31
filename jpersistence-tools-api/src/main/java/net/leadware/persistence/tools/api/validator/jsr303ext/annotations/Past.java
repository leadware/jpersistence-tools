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

import net.leadware.persistence.tools.api.validator.jsr303ext.PastRule;


/**
 * Annotation permettant de verifier qu'une date est dans le passe 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 31 janv. 2018 - 14:32:31
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PastRule.class)
@Documented
public @interface Past {

	/**
	 * Méthode d'obtention du message  en cas de violation de la règle
	 * @return	Message en cas de violation de la règle
	 */
	String message() default "javax.validation.ext.past.fail";
	
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
}
