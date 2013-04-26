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
package com.bulk.persistence.tools.api.utils.restrictions.impl;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.bulk.persistence.tools.api.utils.restrictions.Predicate;

/**
 * Classe représentant un predicat abstrait
 * @author <a href="mailto:jetune@yahoo.fr">Jean-Jacques ETUNE NGI</a>
 * @since 26 avr. 2013 : 08:33:44
 */
public abstract class AbstractPredicate implements Predicate {
	
	/**
	 * Méthode de construction d'un chemin de propriété à partir de la racine
	 * @param <Y>	Paramètre de type du chemin final
	 * @param root	Racine de la requete
	 * @param stringPath	Chemin sous forme de chaine
	 * @return	Chemin recherché sous forme Path
	 */
	@SuppressWarnings("unchecked")
	protected <Y extends Comparable<Y>> Path<Y> buildPropertyPath(Root<?> root, String stringPath) {
		
		// Si la racine est nulle
		if(root == null) return null;
		
		// Si la chaine est vide
		if(stringPath == null || stringPath.trim().length() == 0) return null;
		
		// Le Path à retournet
		Path<? extends Comparable<?>> path = null;
		
		// On splitte sur le séparateur de champs
		String[] hierarchicalPaths = stringPath.trim().split("\\.");
		
		// Obtention du premier chemin
		path = root.get(hierarchicalPaths[0]);
		
		// Si la taille est > 1
		if(hierarchicalPaths.length > 1) {

			// Parcours
			for (int i = 1; i < hierarchicalPaths.length; i++) {
				
				// Le chemin
				String unitPath = hierarchicalPaths[i];
				
				// Si le path est vide ou est une suite d'espace
				if(unitPath == null || unitPath.trim().length() == 0) continue;
				
				// Acces à la ppt
				path = path.get(unitPath.trim());
			}
		}
		
		// On retourne le Path
		return (Path<Y>) path;
	}
	
}
