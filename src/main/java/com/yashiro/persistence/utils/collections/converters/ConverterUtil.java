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
package com.yashiro.persistence.utils.collections.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.BaseJPersistenceUtilsException;
import com.yashiro.persistence.utils.collections.converters.exceptions.NoKeyPropertyForConversionException;
import com.yashiro.persistence.utils.dao.tools.DAOValidatorHelper;

/**
 * Classe d'aide permettant de convertir des ensembles de donnees en d'autres
 * @author Jean-Jacques
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class ConverterUtil {
	
	/**
	 * Methode permettant de transformer une Collection d'Objet en Map
	 * @param <KEYTYPE>	Parametre de Type de la Cle
	 * @param <OBJECTTYPE>	Parametre de Type du contenu de la collection
	 * @param collection	Collection a trangormer
	 * @param keyPropertyName	Propriete de l'objet permettant l'indexation
	 * @return	Map des Objet range suivant la propriete cle
	 */
	public static <KEYTYPE, OBJECTTYPE> Map<KEYTYPE, OBJECTTYPE> convertCollectionToMap(Collection<OBJECTTYPE> collection, String keyPropertyName) {
		
		// Si la collection est nulle
		if(collection == null) return null;
		
		// Map a retourner
		Map<KEYTYPE, OBJECTTYPE> map = new HashMap<KEYTYPE, OBJECTTYPE>();
		
		// Si la collection est vide
		if(collection.size() == 0) return map;
		
		// Si la propriete cle est vide
		if(keyPropertyName == null || keyPropertyName.trim().length() == 0) throw new NoKeyPropertyForConversionException();
		
		// Parcours de la collection
		for (OBJECTTYPE object : collection) {
			
			try {
				
				// Valeur de la propriete
				KEYTYPE keyProperty = (KEYTYPE) DAOValidatorHelper.evaluateValueExpression("${" + keyPropertyName + "}", object);
				
				// Ajout dans la MAP
				map.put(keyProperty, object);
				
			} catch (Exception e) {
				
				// On relance
				throw new BaseJPersistenceUtilsException("convertCollectionToMap.error", e);
			}
			
		}
		
		// On retourne la MAP
		return map;
	}
	
	/**
	 * Methode de conversion d'une collection en Ensemble
	 * @param <OBJECTTYPE>	Parametre de Type du contenu de la Collection
	 * @param collection	Collection a convertir
	 * @return	Ensemble converti
	 */
	public static <OBJECTTYPE> Set<OBJECTTYPE> convertCollectionToSet(Collection<OBJECTTYPE> collection) {
		
		// Si la collection est nulle
		if(collection == null) return null;
		
		// On retourne l'ensemble
		return new HashSet<OBJECTTYPE>(collection);
	}

	/**
	 * Methode de conversion d'une collection en Liste
	 * @param <OBJECTTYPE>	Parametre de Type du contenu de la Collection
	 * @param collection	Collection a convertir
	 * @return	Liste converti
	 */
	public static <OBJECTTYPE> List<OBJECTTYPE> convertCollectionToList(Collection<OBJECTTYPE> collection) {
		
		// Si la collection est nulle
		if(collection == null) return null;
		
		// On retourne la Liste
		return new ArrayList<OBJECTTYPE>(collection);
	}
	
	/**
	 * Methode de conversion d'une MAP en Collection
	 * @param <KEYTYPE>	Parametre de Type de la Cle
	 * @param <OBJECTTYPE>	Parametre de Type du contenu de la MAP
	 * @param map	MAP a convertir
	 * @return	Collection convertie
	 */
	public static <KEYTYPE, OBJECTTYPE> Collection<OBJECTTYPE> convertMapToCollection(Map<KEYTYPE, OBJECTTYPE> map) {
		
		// Si la collection est nulle
		if(map == null) return null;
		
		// On retourne l'ensemble
		return map.values();
	}
	
	/**
	 * Methode de conversion d'une MAP en Liste
	 * @param <KEYTYPE>	Parametre de Type de la Cle
	 * @param <OBJECTTYPE>	Parametre de Type du contenu de la MAP
	 * @param map	MAP a convertir
	 * @return	Liste convertie
	 */
	public static <KEYTYPE, OBJECTTYPE> List<OBJECTTYPE> convertMapToList(Map<KEYTYPE, OBJECTTYPE> map) {
		
		// Si la collection est nulle
		if(map == null) return null;
		
		// On retourne l'ensemble
		return new ArrayList<OBJECTTYPE>(map.values());
	}

	/**
	 * Methode de conversion d'une MAP en Set
	 * @param <KEYTYPE>	Parametre de Type de la Cle
	 * @param <OBJECTTYPE>	Parametre de Type du contenu de la MAP
	 * @param map	MAP a convertir
	 * @return	Ensemble convertie
	 */
	public static <KEYTYPE, OBJECTTYPE> Set<OBJECTTYPE> convertMapToSet(Map<KEYTYPE, OBJECTTYPE> map) {
		
		// Si la collection est nulle
		if(map == null) return null;
		
		// On retourne l'ensemble
		return new HashSet<OBJECTTYPE>(map.values());
	}
}
