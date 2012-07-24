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
package com.bulk.persistence.tools.api.collection.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bulk.persistence.tools.api.collection.utils.exception.NoMapKeyPropertyException;
import com.bulk.persistence.tools.api.exceptions.JPersistenceToolsException;
import com.bulk.persistence.tools.dao.utils.DAOValidatorHelper;

/**
 * Classe d'aide permettant de convertir des ensembles de donnees en d'autres
 * @author Jean-Jacques
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class ConverterUtil {
	
	/**
	 * Methode permettant de transformer une Collection d'Objet en Map
	 * @param <K>	Parametre de Type de la Cle
	 * @param <T>	Parametre de Type du contenu de la collection
	 * @param collection	Collection a trangormer
	 * @param keyPropertyName	Propriete de l'objet permettant l'indexation
	 * @return	Map des Objet range suivant la propriete cle
	 */
	public static <K, T> Map<K, T> convertCollectionToMap(Collection<T> collection, String keyPropertyName) {
		
		// Si la collection est nulle
		if(collection == null) return null;
		
		// Map a retourner
		Map<K, T> map = new HashMap<K, T>();
		
		// Si la collection est vide
		if(collection.size() == 0) return map;
		
		// Si la propriete cle est vide
		if(keyPropertyName == null || keyPropertyName.trim().length() == 0) throw new NoMapKeyPropertyException();
		
		// Parcours de la collection
		for (T object : collection) {
			
			try {
				
				// Valeur de la propriete
				K keyProperty = (K) DAOValidatorHelper.evaluateValueExpression("${" + keyPropertyName + "}", object);
				
				// Ajout dans la MAP
				map.put(keyProperty, object);
				
			} catch (Exception e) {
				
				// On relance
				throw new JPersistenceToolsException("convertCollectionToMap.error", e);
			}
			
		}
		
		// On retourne la MAP
		return map;
	}
	
	/**
	 * Methode de conversion d'une collection en Ensemble
	 * @param <T>	Parametre de Type du contenu de la Collection
	 * @param collection	Collection a convertir
	 * @return	Ensemble converti
	 */
	public static <T> Set<T> convertCollectionToSet(Collection<T> collection) {
		
		// Si la collection est nulle
		if(collection == null) return null;
		
		// On retourne l'ensemble
		return new HashSet<T>(collection);
	}

	/**
	 * Methode de conversion d'une collection en Liste
	 * @param <T>	Parametre de Type du contenu de la Collection
	 * @param collection	Collection a convertir
	 * @return	Liste converti
	 */
	public static <T> List<T> convertCollectionToList(Collection<T> collection) {
		
		// Si la collection est nulle
		if(collection == null) return null;
		
		// On retourne la Liste
		return new ArrayList<T>(collection);
	}
	
	/**
	 * Methode de conversion d'une MAP en Collection
	 * @param <K>	Parametre de Type de la Cle
	 * @param <T>	Parametre de Type du contenu de la MAP
	 * @param map	MAP a convertir
	 * @return	Collection convertie
	 */
	public static <K, T> Collection<T> convertMapToCollection(Map<K, T> map) {
		
		// Si la collection est nulle
		if(map == null) return null;
		
		// On retourne l'ensemble
		return map.values();
	}
	
	/**
	 * Methode de conversion d'une MAP en Liste
	 * @param <K>	Parametre de Type de la Cle
	 * @param <T>	Parametre de Type du contenu de la MAP
	 * @param map	MAP a convertir
	 * @return	Liste convertie
	 */
	public static <K, T> List<T> convertMapToList(Map<K, T> map) {
		
		// Si la collection est nulle
		if(map == null) return null;
		
		// On retourne l'ensemble
		return new ArrayList<T>(map.values());
	}

	/**
	 * Methode de conversion d'une MAP en Set
	 * @param <K>	Parametre de Type de la Cle
	 * @param <T>	Parametre de Type du contenu de la MAP
	 * @param map	MAP a convertir
	 * @return	Ensemble convertie
	 */
	public static <K, T> Set<T> convertMapToSet(Map<K, T> map) {
		
		// Si la collection est nulle
		if(map == null) return null;
		
		// On retourne l'ensemble
		return new HashSet<T>(map.values());
	}
	
	/**
	 * Méthode de conversion d'un tableau de T en Set
	 * @param <T>	Parametre de type de contenu
	 * @param objects	Tableau a convertir
	 */
	public static <T> Set<T> convertArrayToSet(T...objects) {
		
		// Si le tableau est null
		if(objects == null) return null;
		
		// Le Set
		Set<T> set = new HashSet<T>();
		
		// Parcours du tableau
		for(T object : objects) set.add(object);
		
		// On retourne le set
		return set;
	}

	/**
	 * Méthode de conversion d'un tableau de T en List
	 * @param <T>	Parametre de type de contenu
	 * @param objects	Tableau a convertir
	 */
	public static <T> List<T> convertArrayToList(T...objects) {
		
		// Si le tableau est null
		if(objects == null) return null;
		
		// Le Set
		List<T> list = new ArrayList<T>();
		
		// Parcours du tableau
		for(T object : objects) list.add(object);
		
		// On retourne le List
		return list;
	}
}
