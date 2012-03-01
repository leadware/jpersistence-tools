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

import com.yashiro.persistence.utils.annotations.validator.engine.DefaultJPAConstraintValidator;
import com.yashiro.persistence.utils.annotations.validator.engine.DoNothingJPAConstraintValidator;
import com.yashiro.persistence.utils.annotations.validator.engine.IJPAConstraintValidator;

/**
 * Annotation permettant de specifier la/les classes permettant de verifier 
 * les contraintes d'integrites et referentielles sur un objet lors de 
 * l'enregistrement, la mise a jour ou la suppression
 * @author Jean-Jacques
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JPADataConstraintValidators {

	/**
	 * Mode enregistrement
	 */
	public static final int SAVE_MODE = 0;
	
	/**
	 * Mode mise a jour
	 */
	public static final int UPDATE_MODE = 1;
	
	/**
	 * Mode suppression
	 */
	public static final int DELETE_MODE = 2;
	
	/**
	 * Classe du Validateur implementant l'interface de validation lors de l'enregistrement
	 * @return	Classe du validateur
	 */
	public Class<? extends IJPAConstraintValidator> saveValidator() default DefaultJPAConstraintValidator.class;
	
	/**
	 * Classe du Validateur implementant l'interface de validation lors de la mise a jour
	 * @return	Classe du validateur
	 */
	public Class<? extends IJPAConstraintValidator> updateValidator() default DefaultJPAConstraintValidator.class;
	
	/**
	 * Classe du Validateur implementant l'interface de validation lors de la suppression
	 * @return	Classe du validateur
	 */
	public Class<? extends IJPAConstraintValidator> removeValidator() default DoNothingJPAConstraintValidator.class;
}
