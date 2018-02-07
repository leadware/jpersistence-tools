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
package net.leadware.persistence.tools.api.exceptions;

/**
 * Exception levee lors de la validation d'une entite 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 31 janv. 2018 - 14:21:20
 */
public class DAOValidationException extends JPersistenceToolsException {

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param message	Message de l'exception
	 */
	public DAOValidationException(String message) {
		
		// Initialisation Parente
		super(message);
	}
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param message	Message de l'exception
	 * @param parameters Liste des parametres du message
	 */
	public DAOValidationException(String message, String[] parameters) {
		
		// Initialisation Parente
		super(message);
		
		// Parametres
		this.parameters = parameters;
	}
}
