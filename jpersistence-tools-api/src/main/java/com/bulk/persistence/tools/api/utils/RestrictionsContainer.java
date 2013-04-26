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

import com.bulk.persistence.tools.api.utils.restrictions.Predicate;
import com.bulk.persistence.tools.api.utils.restrictions.impl.Eq;
import com.bulk.persistence.tools.api.utils.restrictions.impl.Ge;
import com.bulk.persistence.tools.api.utils.restrictions.impl.Gt;
import com.bulk.persistence.tools.api.utils.restrictions.impl.IsFalse;
import com.bulk.persistence.tools.api.utils.restrictions.impl.IsNotNull;
import com.bulk.persistence.tools.api.utils.restrictions.impl.IsNull;
import com.bulk.persistence.tools.api.utils.restrictions.impl.IsTrue;
import com.bulk.persistence.tools.api.utils.restrictions.impl.Le;
import com.bulk.persistence.tools.api.utils.restrictions.impl.Like;
import com.bulk.persistence.tools.api.utils.restrictions.impl.Lt;
import com.bulk.persistence.tools.api.utils.restrictions.impl.NotEq;
import com.bulk.persistence.tools.api.utils.restrictions.impl.NotLike;

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
	 * Methode d'ajout de la restriction Eq
	 * @param property	Nom de la Propriete
	 * @param value	Valeur de la propriete
	 * @return	Conteneur
	 */
	public <Y extends Comparable<Y>> RestrictionsContainer addEq(String property, Y value) {
		
		// Ajout de la restriction
		restrictions.add(new Eq<Y>(property, value));
		
		// On retourne le conteneur
		return this;
	}

	/**
	 * Methode d'ajout de la restriction NotEq
	 * @param property	Nom de la Propriete
	 * @param value	Valeur de la propriete
	 * @return	Conteneur
	 */
	public <Y extends Comparable<Y>> RestrictionsContainer addNotEq(String property, Y value) {
		
		// Ajout de la restriction
		restrictions.add(new NotEq<Y>(property, value));
		
		// On retourne le conteneur
		return this;
	}

	/**
	 * Methode d'ajout de la restriction GE
	 * @param property	Nom de la Propriete
	 * @param value	Valeur de la propriete
	 * @return	Conteneur
	 */
	public <Y extends Comparable<Y>> RestrictionsContainer addGe(String property, Y value) {
		
		// Ajout de la restriction
		restrictions.add(new Ge<Y>(property, value));
		
		// On retourne le conteneur
		return this;
	}

	/**
	 * Methode d'ajout de la restriction GT
	 * @param property	Nom de la Propriete
	 * @param value	Valeur de la propriete
	 * @return	Conteneur
	 */
	public <Y extends Comparable<Y>> RestrictionsContainer addGt(String property, Y value) {
		
		// Ajout de la restriction
		restrictions.add(new Gt<Y>(property, value));
		
		// On retourne le conteneur
		return this;
	}

	/**
	 * Methode d'ajout de la restriction Lt
	 * @param property	Nom de la Propriete
	 * @param value	Valeur de la propriete
	 * @return	Conteneur
	 */
	public <Y extends Comparable<Y>> RestrictionsContainer addLt(String property, Y value) {
		
		// Ajout de la restriction
		restrictions.add(new Lt<Y>(property, value));
		
		// On retourne le conteneur
		return this;
	}

	/**
	 * Methode d'ajout de la restriction Like
	 * @param property	Nom de la Propriete
	 * @param value	Valeur de la propriete
	 * @return	Conteneur
	 */
	public RestrictionsContainer addLike(String property, String value) {
		
		// Ajout de la restriction
		restrictions.add(new Like(property, value));
		
		// On retourne le conteneur
		return this;
	}

	/**
	 * Methode d'ajout de la restriction NotLike
	 * @param property	Nom de la Propriete
	 * @param value	Valeur de la propriete
	 * @return	Conteneur
	 */
	public RestrictionsContainer addNotLike(String property, String value) {
		
		// Ajout de la restriction
		restrictions.add(new NotLike(property, value));
		
		// On retourne le conteneur
		return this;
	}
	
	/**
	 * Methode d'ajout de la restriction Le
	 * @param property	Nom de la Propriete
	 * @param value	Valeur de la propriete
	 * @return	Conteneur
	 */
	public <Y extends Comparable<Y>> RestrictionsContainer addLe(String property, Y value) {
		
		// Ajout de la restriction
		restrictions.add(new Le<Y>(property, value));
		
		// On retourne le conteneur
		return this;
	}

	/**
	 * Methode d'ajout de la restriction IsFalse
	 * @param property	Nom de la Propriete
	 * @param value	Valeur de la propriete
	 * @return	Conteneur
	 */
	public RestrictionsContainer addIsFalse(String property) {
		
		// Ajout de la restriction
		restrictions.add(new IsFalse(property));
		
		// On retourne le conteneur
		return this;
	}

	/**
	 * Methode d'ajout de la restriction IsTrue
	 * @param property	Nom de la Propriete
	 * @param value	Valeur de la propriete
	 * @return	Conteneur
	 */
	public RestrictionsContainer addIsTrue(String property) {
		
		// Ajout de la restriction
		restrictions.add(new IsTrue(property));
		
		// On retourne le conteneur
		return this;
	}

	/**
	 * Methode d'ajout de la restriction IsNotNull
	 * @param property	Nom de la Propriete
	 * @param value	Valeur de la propriete
	 * @return	Conteneur
	 */
	public RestrictionsContainer addIsNotNull(String property) {
		
		// Ajout de la restriction
		restrictions.add(new IsNotNull(property));
		
		// On retourne le conteneur
		return this;
	}

	/**
	 * Methode d'ajout de la restriction IsNull
	 * @param property	Nom de la Propriete
	 * @param value	Valeur de la propriete
	 * @return	Conteneur
	 */
	public RestrictionsContainer addIsNull(String property) {
		
		// Ajout de la restriction
		restrictions.add(new IsNull(property));
		
		// On retourne le conteneur
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
