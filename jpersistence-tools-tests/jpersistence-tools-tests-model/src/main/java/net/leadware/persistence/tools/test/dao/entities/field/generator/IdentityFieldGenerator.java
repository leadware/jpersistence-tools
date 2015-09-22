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
package net.leadware.persistence.tools.test.dao.entities.field.generator;

import java.io.Serializable;

import net.leadware.persistence.tools.api.generator.IFieldGenerator;
import net.leadware.persistence.tools.generator.base.AbstractFieldGenerator;

/**
 * Classe de generation de valeur 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 22 sept. 2015 - 19:24:05
 */
public class IdentityFieldGenerator extends AbstractFieldGenerator implements IFieldGenerator {
	
	/**
	 * Valeur generee
	 */
	public static final String GENERATED_VALUE = "DESIGNATION_GENEREE";
	
	/* (non-Javadoc)
	 * @see net.leadware.persistence.tools.api.generator.IFieldGenerator#generate(java.lang.Object)
	 */
	@Override
	public Serializable generate() {
		
		// On retourne la valeur
		return GENERATED_VALUE;
	}

}
