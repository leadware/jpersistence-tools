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
package net.leadware.persistence.tools.api.utils.restrictions.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * Classe représentant un predicat de isNull
 * @author <a href="mailto:jetune@yahoo.fr">Jean-Jacques ETUNE NGI</a>
 * @since 26 avr. 2013 : 08:30:19
 */
public class IsNull extends AbstractPredicate {

	/**
	 * ID Genere Par Eclipse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Nom de la propriete
	 */
	protected String property;

	/**
	 * Constructeur avec initialisation des parametres
	 * @param property	Nom de la propriete
	 * @param value	Valeur de la propriete
	 */
	public IsNull(String property) {
		this.property = property;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.leadware.persistence.tools.api.utils.restrictions.Predicate#generateJPAPredicate(javax.persistence.criteria.CriteriaBuilder, javax.persistence.criteria.Root)
	 */
	public Predicate generateJPAPredicate(CriteriaBuilder criteriaBuilder, Root<?> root) {
		
		// On retourne le predicat
		return criteriaBuilder.isNull(this.<Boolean>buildPropertyPath(root, property));
	}
}
