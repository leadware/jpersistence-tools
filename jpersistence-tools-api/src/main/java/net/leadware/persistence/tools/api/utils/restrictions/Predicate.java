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
package net.leadware.persistence.tools.api.utils.restrictions;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

/**
 * Classe représentant un predicat de selection
 * @author <a href="mailto:jetune@yahoo.fr">Jean-Jacques ETUNE NGI</a>
 * @since 26 avr. 2013 : 08:17:04
 */
public interface Predicate extends Serializable {
	
	/**
	 * Methode de construction d'un Predicat JPA 2
	 * @param <Y>	Parametre de type de la valeur de la propriete
	 * @param criteriaBuilder	Constructeur de critere
	 * @param root	Racine de la requete par critere
	 * @return	Predicat JPA 2
	 */
	public javax.persistence.criteria.Predicate generateJPAPredicate(CriteriaBuilder criteriaBuilder, Root<?> root);
}
