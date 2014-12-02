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
package net.leadware.persistence.tools.test.dao.entities.base;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Classe representant la racine des classes conforme a la classe de parametrage mere
 * Elle definie un mapping de type: Table-Per-Class-Hierarchy
 * @author  Jean-Jacques ETUNÈ NGI
 * Super Classe
 * @see	{@link AbstractParameterBase}
 */
@Entity(name = "TPHParameterBase")
@Table(name = "GENEZIS_TPH_PARAMETER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PARAMETER_DISCRIMINATOR", discriminatorType = DiscriminatorType.STRING)
public class TPHParameterBase extends AbstractParameterBase {

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public TPHParameterBase() {
		
		// Appel Parent
		super();
	}
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param code	Code du parametre
	 * @param designation	Libelle du parametre
	 */
	public TPHParameterBase(String code, String designation) {
		
		// Appel parent
		super(code, designation);
	}
}
