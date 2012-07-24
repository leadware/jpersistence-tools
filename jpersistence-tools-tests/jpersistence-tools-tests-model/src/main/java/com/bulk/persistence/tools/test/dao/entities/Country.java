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
package com.bulk.persistence.tools.test.dao.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bulk.persistence.tools.api.dao.constants.DAOMode;
import com.bulk.persistence.tools.api.validator.annotations.SizeDAOValidator;
import com.bulk.persistence.tools.api.validator.annotations.SizeDAOValidators;
import com.bulk.persistence.tools.test.dao.entities.base.TPHParameterBase;

/**
 * Classe representant un Pays
 * @author  Jean-Jacques ETUNÈ NGI
 * @see {@link TPHParameterBase}
 */
@SizeDAOValidators({
	@SizeDAOValidator(mode = DAOMode.SAVE,   expr = "from Country c where (c.code = ${code})", max = 0, message = "Country.save.code.exist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from Country c where (c.id = ${id})", min = 1, message = "Country.update.id.notexist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from Country c where (c.id != ${id}) and (c.code = ${code})", max = 0, message = "Country.update.code.notunique")
})
@Entity(name = "Country")
@DiscriminatorValue(value = "Country")
public class Country extends TPHParameterBase {
	
	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public Country() {
		
		// Appel Parent
		super();
	}
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param code	Code du pays
	 * @param designation	Libelle du pays
	 */
	public Country(String code, String designation) {
		
		// Appel parent
		super(code, designation);
	}
}
