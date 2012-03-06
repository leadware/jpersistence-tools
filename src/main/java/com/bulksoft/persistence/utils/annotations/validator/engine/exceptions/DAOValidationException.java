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

/**
 * Exception levee lors de la validation d'une entite
 * @author Jean-Jacques
 * @version 1.0
 */
public class DAOValidationException extends BaseJPersistenceUtilsException {

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Liste des parametres du message
	 */
	private Object[] parameters = null;
	
	/**
	 * Constructeur par defaut
	 */
	public DAOValidationException(String message) {
		
		// Initialisation Parente
		super(message);
	}
	
	/**
	 * Constructeur par defaut
	 */
	public DAOValidationException(String message, Object[] parameters) {
		
		// Initialisation Parente
		super(message);
		
		// Parametres
		this.parameters = parameters;
	}

	/**
	 * Methode d'obtention de la Liste des parametres du message
	 * @return Liste des parametres du message
	 */
	public Object[] getParameters() {
		return parameters;
	}

	/**
	 * Methode de mise a jour de la Liste des parametres du message
	 * @param parameters Liste des parametres du message
	 */
	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}
}
