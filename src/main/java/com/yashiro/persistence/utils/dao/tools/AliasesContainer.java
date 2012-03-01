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
package com.yashiro.persistence.utils.dao.tools;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe representant un AliasesContainer de critere
 * @author Jean-Jacques
 * @version 1.0
 */
public class AliasesContainer implements Serializable {

	/**
	 * ID Genere Par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Carte des AliasesContainer
	 */
	private Map<String, String> aliases = new HashMap<String, String>();
	
	/**
	 * Obtention de l'instance
	 * @return	Instance de travail
	 */
	public static synchronized final AliasesContainer getInstance() {
		
		// On retourne l'instance
		return new AliasesContainer();
	}
	
	/**
	 * Methode d'ajout d'un AliasesContainer sur une propriete 
	 * (Si la propriete a deja un AliasesContainer, celui-ci est remplace)
	 * @param property	Propriete
	 * @return	AliasesContainer
	 */
	public AliasesContainer add(String property, String alias) {
		
		// Si le nom de la propriete est vide
		if(property == null || property.trim().length() == 0) return this;
		
		// Si le nom de l'AliasesContainer est vide
		if(alias == null || alias.trim().length() == 0) return this;
		
		// Ajout de l'AliasesContainer
		aliases.put(property, alias);
		
		// On retourne l'objet en cours
		return this;
	}

	/**
	 * Methode d'obtention de la Carte des AliasesContainer
	 * @return Carte des AliasesContainer
	 */
	public Map<String, String> getAliases() {
		return Collections.unmodifiableMap(aliases);
	}
	
	/**
	 * Methode d'obtention de la taille du conteneur
	 * @return	Taille du conteneur
	 */
	public int size() {
		
		// On retourne la taille de la collection
		return this.aliases.size();
	}

	/**
	 * Methode de vidage du conteneur
	 */
	public void clear() {
		
		// Si la collection est non nulle
		if(aliases != null) aliases.clear();
	}
}
