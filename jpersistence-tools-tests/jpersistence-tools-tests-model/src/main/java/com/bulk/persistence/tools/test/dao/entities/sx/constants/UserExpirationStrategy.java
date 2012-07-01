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
package com.bulk.persistence.tools.test.dao.entities.sx.constants;

/**
 * Classe representant les etats d'un utilisateur
 * @author Jean-Jacques ETUNÈ NGI
 */
public enum UserExpirationStrategy {
	
	/**
	 * L'utilisateur expire
	 */
	TEMPORARY("TEMPORARY"),
	
	/**
	 * L'utilisateur n'expire pas
	 */
	PERMANENT("PERMANENT");
	
	/**
	 * Valeur de la constante (Code)
	 */
	private final String value;
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param value	Valeur de l'etat
	 */
	private UserExpirationStrategy(String value) {
		
		// Initialisation de la valeur
		this.value = value;
	}
	
	/**
	 * Obtention de la valeur de l'etat
	 * @return	Valeur de l'etat
	 */
	public String value() {
        return value;
    }
	
	/**
	 * Obtention de la valeur de l'etat
	 * @return	Valeur de l'etat
	 */
	public String getValue() {
        return value;
    }
	
	@Override
	public String toString() {
		
		// On retourne la valeur
		return value;
	}
	 
}
