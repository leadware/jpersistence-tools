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
package com.bulk.persistence.tools.api.validator.annotations.marker;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bulk.persistence.tools.dao.api.constants.DAOValidatorEvaluationTime;
import com.bulk.persistence.tools.validator.IDAOValidator;

/**
 * Annotation permettant de specifier classe implementant la logique 
 * de validation d'une Fonction-Annotation
 * @author Jean-Jacques ETUNÈ NGI
 */
@Target(value = {ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DAOConstraint {
	
	/**
	 * Methode permettant d'obtenir le moment d'evaluation de la regle
	 * @return Moment d'evaluation de la regle
	 */
	public DAOValidatorEvaluationTime evaluationTime() default DAOValidatorEvaluationTime.PRE_CONDITION;
	
	/**
	 * Methode d'obtention de la classe d'implementation de la logique de validation
	 * @return	Classe d'implementation de la logique de validation
	 */
	public Class<? extends IDAOValidator<? extends Annotation>> validatedBy();
}
