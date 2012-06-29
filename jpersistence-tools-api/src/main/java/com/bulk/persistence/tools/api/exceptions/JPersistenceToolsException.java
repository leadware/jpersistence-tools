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
package com.bulk.persistence.tools.api.exceptions;


/**
 * <p>
 * 	<b>Classe de Base des exception du Framework JPersistenceUtils</b>
 * 	@author Jean-Jacques ETUNÈ NGI
 * </p>
 */
public class JPersistenceToolsException extends RuntimeException {
	
	/**
	 * Message d'erreur par defaut
	 */
	public static String DEFAULT_MESSAGE = "JPersistenceToolsException.mesasge.default";
	
	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Constructeur par defaut
	 */
	public JPersistenceToolsException() {
		
		// Appel du Constructeur de RuntimeException
		super(DEFAULT_MESSAGE);
	}
	
	/**
	 * Constructeur avec Message
	 * @param message	Message a afficher
	 */
	public JPersistenceToolsException(String message){
		
		// Appel du constructeur de RuntimeException
		super(message);
	}
	
	/**
	 * Constructeur avec Message et de la cause
	 * @param message	Message a afficher
	 * @param t	Cause de l'exception
	 */
	public JPersistenceToolsException(String message, Throwable t) {
		
		// Appel du constructeur de Runtime
		super(message, t);
	}
	
	/**
	 * Constructeur avec de la cause
	 * @param t	Exception cause
	 */
	public JPersistenceToolsException(Throwable t) {
		
		// Appel du constructeur de Runtime
		super(t);
	}
}
