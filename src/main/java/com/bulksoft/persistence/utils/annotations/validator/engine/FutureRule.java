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
package com.bulksoft.persistence.utils.annotations.validator.engine;

import java.util.Date;

import org.hibernate.validator.Validator;

import com.bulksoft.persistence.utils.annotations.validator.Past;

/**
 * Classe implementant la regle de validation controlant que la valeur d'une date
 * se trouve dans le futur
 * @author Jean-Jacques
 * @version 1.0
 */
public class FutureRule implements Validator<Past> {
		
	/* (non-Javadoc)
	 * @see org.hibernate.validator.Validator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(Past annotation) {}

	/* (non-Javadoc)
	 * @see org.hibernate.validator.Validator#isValid(java.lang.Object)
	 */
	@Override
	public boolean isValid(Object value) {
		
		// Si l'objet est null
		if(value == null) return false;
		
		// Si l'objet n'est pas de type date (On l'ignore en retournant true - Comme s'il n'ya eu aucun test)
		if(!(value instanceof Date)) return true;
		
		// On caste
		Date valueDate = (Date) value;
		
		// On retourne la comparaison
		return valueDate.after(new Date());
	}
}
