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
package net.leadware.persistence.tools.core.dao.utils;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.VariableMapper;

/**
 * Classe representant le contexte d'evaluation d'une Expression
 * @author Jean-Jacques ETUNÈ NGI
 */
public class DAOValidatorELContext extends ELContext {
	
	/**
	 * Resolver de type Bean
	 */
	private ELResolver resolver;
	
	/* (non-Javadoc)
	 * @see javax.el.ELContext#getELResolver()
	 */
	@Override
	public ELResolver getELResolver() {
		
		// On retourne le resolver
		return resolver;
	}
	
	/**
	 * Methode de modification du Resolver
	 * @param resolver	Resolver de type Bean
	 */
	public void setELResolver(ELResolver resolver) {
		
		// On positionne le Resolver
		this.resolver = resolver;
	}

	/* (non-Javadoc)
	 * @see javax.el.ELContext#getFunctionMapper()
	 */
	@Override
	public FunctionMapper getFunctionMapper() {
		
		// On retourne null
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.el.ELContext#getVariableMapper()
	 */
	@Override
	public VariableMapper getVariableMapper() {
		
		// On retourne null
		return null;
	}
}
