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
package com.bulksoft.persistence.utils.annotations.validator.engine.exceptions;

import com.bulksoft.persistence.utils.annotations.validator.engine.exceptions.BaseJPersistenceUtilsException;

/**
 * Exception levee lors d'une tentative d'operation de parametrage sur un objet n'etant pas un objet de parametrage
 * @author Jean-Jacques
 * @version 2.0
 */
public class NotParameterException extends BaseJPersistenceUtilsException {

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Objet cause de l'exception
	 */
	private Object entity;
	
	/**
	 * Constructeur
	 * @param entity	Entite cause
	 */
	public NotParameterException(Object entity) {
		
		// Initialisation parente
		super("NotParameterException.message");
		
		// Initialisation de la cause
		this.entity = entity;
	}

	/**
	 * Methode d'obtention de l'Objet cause de l'exception
	 * @return Objet cause de l'exception
	 */
	public Object getEntity() {
		return entity;
	}
}
