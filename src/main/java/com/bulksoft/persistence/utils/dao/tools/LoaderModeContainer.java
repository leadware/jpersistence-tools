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
import java.util.HashMap;
import java.util.Map;

import org.hibernate.FetchMode;


/**
 * Conteneur de mode de chargement de proprietes (EAGER or LAZY)
 * Si une propriete a deja un mode de chargement, il est remplace
 * @author Jean-Jacques
 * @version 1.0
 */
public class LoaderModeContainer implements Serializable {

	/**
	 * ID genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Carte des modes de chargements des proprietes
	 */
	private Map<String, FetchMode> loaderMode = new HashMap<String, FetchMode>();
	
	/**
	 * Obtention de l'instance
	 * @return	Instance de travail
	 */
	public static synchronized final LoaderModeContainer getInstance() {
		
		// On retourne l'instance
		return new LoaderModeContainer();
	}
	
	/**
	 * Methode d'ajout d'un mode de chargement pour une propriete
	 * @param property	Propriete a charger
	 * @return	Conteneur de mode de chargement
	 */
	public LoaderModeContainer add(String property, FetchMode mode) {
		
		// Si la propriete n'est pas vide
		if(property != null && property.trim().length() > 0) {
			
			// Si le mode n'est pas null
			if(mode != null) loaderMode.put(property.trim(), mode);
		}
		
		// On retourne le conteneur
		return this;
	}


	/**
	 * Methode d'obtention de la Carte des modes de chargements des proprietes
	 * @return Carte des modes de chargements des proprietes
	 */
	public Map<String, FetchMode> getLoaderMode() {
		return Collections.unmodifiableMap(loaderMode);
	}
	
	/**
	 * Methode d'obtention de la taille du conteneur
	 * @return	Taille du conteneur
	 */
	public int size() {
		
		// On retourne la taille de la collection
		return this.loaderMode.size();
	}

	/**
	 * Methode de vidage du conteneur
	 */
	public void clear() {
		
		// Si la collection est non nulle
		if(loaderMode != null) loaderMode.clear();
	}
}
