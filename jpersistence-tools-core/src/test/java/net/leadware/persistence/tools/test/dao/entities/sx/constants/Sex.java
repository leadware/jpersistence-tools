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
package net.leadware.persistence.tools.test.dao.entities.sx.constants;

/**
 * Enumeration des types de sexe
 * @author Jean-Jacques
 * @version 1.0
 */
public enum Sex {
	
	/**
	 * Sexe Masculin
	 */
	MAN("Sex.man"),
	
	/**
	 * Sexe Feminin
	 */
	WOMAN("Sex.woman");
	
	/**
	 * Valeur de la constante (Code)
	 */
	private final String value;
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param value	Valeur du sexe
	 */
	private Sex(String value) {
		
		// Initialisation de la valeur
		this.value = value;
	}

	/**
	 * Obtention de la valeur du sexe
	 * @return	Valeur du sexe
	 */
	public String value() {
        return value;
    }
	
	public String getValue() {
        return value;
    }
}
