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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Classe permettant de resoudre l'execution de certaines methodes sur l'objet contexte
 * @author Jean-Jacques
 * @version 2.0
 */
public class DAOValidatorMethodExpressionExecutor {
	
	/**
	 * Map des methodes
	 */
	private static Map<String, Method> methods = new HashMap<String, Method>();
	
	/**
	 * Delimiteur de debut de fonction
	 */
	public static final String FUNCTION_BEGIN_DELIMITER = "\\$F\\_\\w+\\(";
	
	/**
	 * Delimiteur de fin de fonction
	 */
	public static final String FUNCTION_END_DELIMITER = "\\}";
	
	/**
	 * Delimiteur de debut de fonction
	 */
	public static final String SIMPLE_FUNCTION_BEGIN_DELIMITER = "$F{";
	
	/**
	 * Delimiteur de fin de fonction
	 */
	public static final String SIMPLE_FUNCTION_END_DELIMITER = "}";
	
	/**
	 * Constructeur par defaut
	 */
	public DAOValidatorMethodExpressionExecutor() {
		
		// Mise en place des methodes
		try {
			
			// Obtention de la liste des methodes
			Method [] allMethods = DAOValidatorMethodExpressionExecutor.class.getMethods();
			
			// Si la liste n'est pas vide
			if(allMethods != null && allMethods.length > 0) {
				
				// Parcours
				for (Method method : allMethods) {
					
					// Ajout dans la liste
					methods.put(method.getName(), method);
				}
			}
		} catch (Throwable e) {
			
			// On affiche
			e.printStackTrace();
		}
	}
	
	/**
	 * Methode d'obtention du nom de la classe d'un Object
	 * @param object	Objet dont on recherche le nom de classe
	 * @return	Nom de la classe de l'Objet
	 */
	public String ctxClassName(Object object) {
		
		// Si le nom est null
		if(object == null) return Object.class.getName();
		
		// On retourne le nom de la classe
		return object.getClass().getName();
	}
	
	/**
	 * Methode d'obtention des noms de methodes
	 * @return	Ensemble des noms de methodes
	 */
	public Set<String> getMethodsName() {
		
		// On retourne l'ensemble des cles
		return methods.keySet();
		
	}
		
	/**
	 * Methode d'execution de la methode
	 * @param methodName	Nom de la methode a executer
	 * @param parameters	Parametres de la methode
	 * @return	Valeur de retour
	 */
	public Object invoke(String methodName, Object...parameters) {
		
		// Si le nom de la methode est vide
		if(methodName == null || methodName.trim().length() == 0) return null;
		
		// Obtention de la methode
		Method method = methods.get(methodName.trim());
		
		// Si la methode n'existe pas
		if(method == null) return null;
		
		try {
			
			// Apel
			Object result = method.invoke(this, parameters);
			
			// On retourne le resultat
			return result;
			
		} catch (Exception e) {
			
			// On affiche
			e.printStackTrace();
			
			// On retourne null
			return null;
		}
	}
}
