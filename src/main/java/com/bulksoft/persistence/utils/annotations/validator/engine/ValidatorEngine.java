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

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;

import com.bulksoft.persistence.utils.annotations.validator.engine.exceptions.InvalidEntityInstanceStateException;

/**
 * Classe permettant d'effectuer la validation d'instance manuellement
 * @author Jean-Jacques
 * @version 1.0
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ValidatorEngine {

	/**
	 * Methode permettant de valider une classe
	 * @param o	Objet  valider
	 */
	
	public static void validate(Object o) {
		
		// Le Moteur de validation
		ClassValidator validator = new ClassValidator(o.getClass());
		
		// Obtention de la liste des valeurs indsirables
		InvalidValue[] values = validator.getInvalidValues(o);
		
		// Si la liste est vide
		if(values == null || values.length == 0) return;
				
		// On lve une Exception
		throw new InvalidEntityInstanceStateException(new InvalidStateException(values));
	}
}
