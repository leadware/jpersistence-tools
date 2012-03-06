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
package com.bulksoft.persistence.utils.dao.tools.encrypter.exceptions;

import com.bulksoft.persistence.utils.annotations.validator.engine.exceptions.BaseJPersistenceUtilsException;

/**
 * Classe representant une exception due a une mauvaise initialisation de la cle d'un algorithme (Le type de cle ne correspond pas a l'algorithme)
 * @author Jean-Jacques
 * @version 1.0
 */
public class InvalidKeySpecificationException extends BaseJPersistenceUtilsException{

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public InvalidKeySpecificationException() {
		
		// Initialisation du Parent
		super("InvalidKeySpecificationException.message.code");
	}
	
	/**
	 * Constructeur avec initialisation de la cause
	 */
	public InvalidKeySpecificationException(Throwable cause) {
		
		// Initialisation du Parent
		super("InvalidKeySpecificationException.message.code", cause);
	}
}
