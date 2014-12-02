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

import net.leadware.persistence.tools.api.utils.restrictions.impl.AbstractPredicate;

/**
 * Classe representant un predicat de like avec prise en charge du toLower
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Enterprise Architect)</a>
 * @since 25 juin 2014 20:46:39
 */
public class LikeIgnoreCase extends AbstractPredicate {
	

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Nom de la propriete
	 */
	protected String property;
	
	/**
	 * Valeur de la Propriete
	 */
	protected String value;

	/**
	 * Constructeur avec initialisation des parametres
	 * @param property	Nom de la propriete
	 * @param value	Valeur de la propriete
	 */
	public LikeIgnoreCase(String property, String value) {
		this.property = property;
		this.value = value;
	}
	
	
	/* (non-Javadoc)
	 * @see net.leadware.persistence.tools.api.utils.restrictions.Predicate#generateJPAPredicate(javax.persistence.criteria.CriteriaBuilder, javax.persistence.criteria.Root)
	 */
	@Override
	public Predicate generateJPAPredicate(CriteriaBuilder criteriaBuilder, Root<?> root) {
		
		// On retourne le predicat
		return criteriaBuilder.like(criteriaBuilder.lower(this.<String>buildPropertyPath(root, property)), value.toLowerCase());
	}

}
