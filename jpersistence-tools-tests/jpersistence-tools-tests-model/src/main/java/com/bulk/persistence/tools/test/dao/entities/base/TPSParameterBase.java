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
package com.bulk.persistence.tools.test.dao.entities.base;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Classe representant la racine des classes de parametrage mappee
 * par la strategie: Table-Per-SubClass
 * @author  Jean-Jacques ETUNÈ NGI
 * Super Classe
 * @see	{@link AbstractParameterBase}
 */
@Entity(name = "TPSParameterBase")
@Table(name = "GENEZIS_TPS_PARAMETER")
@Inheritance(strategy = InheritanceType.JOINED)
public class TPSParameterBase extends AbstractParameterBase {
	
	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public TPSParameterBase() {
		
		// Appel Parent
		super();
	}

	/**
	 * Constructeur avec initialisation des parametres
	 * @param code	Code du parametre
	 * @param designation	Libelle du parametre
	 */
	public TPSParameterBase(String code, String designation) {

		// Appel Parent
		super(code, designation);
	}
	
	@Override
	public boolean equals(Object parameter) {
		
		// Appel Parent
		return super.equals(parameter);
	}
}