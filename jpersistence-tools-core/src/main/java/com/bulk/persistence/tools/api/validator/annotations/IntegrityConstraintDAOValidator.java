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
package com.bulk.persistence.tools.api.validator.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bulk.persistence.tools.dao.api.constants.DAOMode;
import com.bulk.persistence.tools.dao.api.constants.DAOValidatorEvaluationTime;
import com.bulk.persistence.tools.validator.IntegrityConstraintDAOValidatorRule;
import com.bulk.persistence.tools.validator.engine.JSR303ValidatorEngine;

/**
 * Annotation permettant de verifier les contraintes d'intergrites
 * @author Jean-Jacques ETUNÈ NGI
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DAOConstraint(validatedBy = IntegrityConstraintDAOValidatorRule.class)
public @interface IntegrityConstraintDAOValidator {

	/**
	 * Methode permettant d'obtenir le mode d'utilisation de l'instance de l'annotation
	 * @return	Mode DAO de l'instance de l'annotation
	 */
	public DAOMode[] mode() default DAOMode.SAVE;

	/**
	 * Methode permettant d'obtenir le l'instant d'evaluation de l'annotation
	 * @return	Instant d'evaluation de l'annotation
	 */
	public DAOValidatorEvaluationTime[] evaluationTime() default DAOValidatorEvaluationTime.PRE_CONDITION;
	
	/**
	 * Methode d'obtention de la classe du moteur de validation
	 * @return	Classe du moteur de validation
	 */
	public Class<? extends JSR303ValidatorEngine> validatorEngineClass() default JSR303ValidatorEngine.class;
}
