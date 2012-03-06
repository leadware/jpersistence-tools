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
package com.bulksoft.persistence.utils.dao.tools;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Classe representant le resultat de l'appel de la methode d'enregistrement d'une liste
 * d'entites
 * @author Jean-Jacques
 * @version 1.0
 */
public class SaveListResult<T> implements Serializable {

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Liste des Entites enregistrees
	 */
	private List<T> registered = null;
	
	/**
	 * MAP des exception par Entites
	 */
	private Map<T, Throwable> exceptionPerEntity = null;

	/**
	 * Constructeur avec initialisation des parametres
	 * @param registered	Liste des Entites enregistrees
	 * @param exceptionPerEntity	MAP des exception par Entites
	 */
	public SaveListResult(List<T> registered, Map<T, Throwable> exceptionPerEntity) {
		this.registered = registered;
		this.exceptionPerEntity = exceptionPerEntity;
	}

	/**
	 * Methode d'obtention de la Liste des Entites enregistrees
	 * @return the registered
	 */
	public List<T> getRegistered() {
		return Collections.unmodifiableList(registered);
	}

	/**
	 * Methode d'obtention de la MAP des exception par Entites
	 * @return MAP des exception par Entites
	 */
	public Map<T, Throwable> getExceptionPerEntity() {
		return Collections.unmodifiableMap(exceptionPerEntity);
	}
	
	
}
