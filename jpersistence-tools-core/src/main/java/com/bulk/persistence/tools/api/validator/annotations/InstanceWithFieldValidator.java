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
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bulk.persistence.tools.dao.api.constants.DAOMode;
import com.bulk.persistence.tools.dao.api.constants.DAOValidatorEvaluationTime;
import com.bulk.persistence.tools.dao.api.constants.ValidatorExpressionType;
import com.bulk.persistence.tools.validator.InstanceWithFieldValidatorRule;

/**
 * Validateur permettant de verifier qu'une liste de champs est unique sur le Contexte de Persistence
 * @author Jean-Jacques ETUNÈ NGI
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@DAOConstraint(validatedBy = InstanceWithFieldValidatorRule.class)
public @interface InstanceWithFieldValidator {
	
	/**
	 * Methode permettant d'obtenir le type d'expression
	 * @return	Type d'expression
	 */
	public ValidatorExpressionType type() default ValidatorExpressionType.JPQL;
	
	/**
	 * Methode permettant d'obtenir la classe cible du test
	 * @return	Classe cible du test
	 */
	public Class<?> targetClass();
	
	/**
	 * Methode d'obtention du Champ a tester sur le contexte
	 * @return	Champ a tester sur le contexte
	 */
	public String contextField();
	
	/**
	 * Methode d'obtention du champ persistant a tester
	 * @return	Champ persistant a tester
	 */
	public String persistentField();
	
	/**
	 * Methode d'obtention du nombre minimum
	 * @return	Taille minimum
	 */
	public int min() default 0;
	
	/**
	 * Methode d'obtention du nombre maximum
	 * @return	Taille maximum
	 */
	public int max() default Integer.MAX_VALUE;
	
	/**
	 * Message lors de la violation de la contrainte
	 * @return	Message
	 */
	public String message() default "InstanceWithFieldValidator.error";

	/**
	 * Methode d'obtention de la liste des parametres de l'annotation
	 * @return	Liste des parametres de l'annotation
	 */
	public String[] parameters() default {};
	
	/**
	 * Methode permettant d'obtenir le mode d'utilisation de l'instance de l'annotation
	 * @return	Modes DAO de l'instance de l'annotation
	 */
	public DAOMode[] mode() default {DAOMode.SAVE, DAOMode.UPDATE};

	/**
	 * Methode permettant d'obtenir le l'instant d'evaluation de l'annotation
	 * @return	Instants d'evaluation de l'annotation
	 */
	public DAOValidatorEvaluationTime[] evaluationTime() default DAOValidatorEvaluationTime.PRE_CONDITION;
}
