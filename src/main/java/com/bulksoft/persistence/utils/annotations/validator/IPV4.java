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

import com.bulksoft.persistence.utils.annotations.validator.engine.IPV4Rule;

/**
 * Annotation de la rgle de validation permettant de contrler que la valeur d'une proprit
 * representant bien une adresse IPV4
 * @author Jean-Jacques
 * @version 1.0
 */
@ValidatorClass(value = IPV4Rule.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IPV4 {
	
	/**
	 * Methode d'obtention du message a afficher lors du non respect de la rgle
	 * @return	Le Message
	 */
	String message() default "IPV4Adress.invalidpropertyvalue";
}
