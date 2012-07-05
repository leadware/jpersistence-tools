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
package com.bulk.persistence.tools.api.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.criteria.Predicate;

/**
 * Classe representant un conteneur de restrictions
 * @author Jean-Jacques ETUNÈ NGI
 */
public class RestrictionsContainer implements Serializable {

	/**
	 * ID Genere par eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Liste des restrictions
	 */
	private List<Predicate> restrictions = new ArrayList<Predicate>();

	/**
	 * Obtention de l'instance
	 * @return	Instance de travail
	 */
	public static RestrictionsContainer newInstance() {
		
		// On retourne l'instance
		return new RestrictionsContainer();
	}
	
	/**
	 * Methode d'ajout d'une restriction
	 * @param restriction	Restriction a ajouter
	 * @return	Conteneur de restrictions
	 */
	public RestrictionsContainer add(Predicate restriction) {
		
		// Si la restriction est nulle
		if(restriction != null) restrictions.add(restriction);
		
		// On retourne le container
		return this;
	}

	/**
	 * Methode d'obtention de la Liste des restrictions
	 * @return Liste des restrictions
	 */
	public List<Predicate> getRestrictions() {
		return Collections.unmodifiableList(restrictions);
	}
	
	/**
	 * Methode d'obtention de la taille du conteneur
	 * @return	Taille du conteneur
	 */
	public int size() {
		
		// On retourne la taille de la collection
		return this.restrictions.size();
	}
	
	/**
	 * Methode de vidage du conteneur
	 */
	public void clear() {
		
		// Si la collection est non nulle
		if(restrictions != null) restrictions.clear();
	}
}
