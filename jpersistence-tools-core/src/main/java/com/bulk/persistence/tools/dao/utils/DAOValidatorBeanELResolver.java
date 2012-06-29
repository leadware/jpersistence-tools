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
package com.bulk.persistence.tools.dao.utils;

import javax.el.BeanELResolver;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.PropertyNotFoundException;

/**
 * Classe representant un ELResolver etandant le type BeanELResolver (Pour la resolution des proprietes
 * @author Jean-Jacques ETUNÈ NGI
 */
public class DAOValidatorBeanELResolver extends BeanELResolver {
	
	/**
	 * Objet de base (Initialise par l'instance de l'objet a valider)
	 */
	private Object base = null;
	
	/**
	 * Constructeur avec initialisation de l'objet de base initial
	 * @param base	Objet de base (Initialise par l'instance de l'objet a valider)
	 */
	public DAOValidatorBeanELResolver(Object base) {
		this.base = base;
	}
	
	@Override
	public Object getValue(ELContext context, Object base, Object property) throws NullPointerException, PropertyNotFoundException, ELException {
		
		// Si l'Objet de base est null
		if(base != null) this.base = base;
		
		// On obtient la Valeur
		Object value = super.getValue(context, this.base, property);
		
		// On retourne le resultat
		return value;
	}
	
	/**
	 * Methode de mise a jour de l'Objet de base (Initialise par l'instance de l'objet a valider)
	 * @param base Objet de base (Initialise par l'instance de l'objet a valider)
	 */
	public void setBase(Object base) {
		this.base = base;
	}
}