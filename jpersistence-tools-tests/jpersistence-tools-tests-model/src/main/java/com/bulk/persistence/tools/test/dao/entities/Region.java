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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.bulk.persistence.tools.api.validator.annotations.SizeDAOValidator;
import com.bulk.persistence.tools.api.validator.annotations.SizeDAOValidators;
import com.bulk.persistence.tools.dao.api.constants.DAOMode;
import com.bulk.persistence.tools.test.dao.entities.base.TPSParameterBase;

/**
 * Classe representant une region du systeme
 * @author  Jean-Jacques ETUNÈ NGI
 * @see {@link TPSParameterBase}
 */
@SizeDAOValidators({
	@SizeDAOValidator(mode = DAOMode.SAVE,   expr = "from Region r where (r.code = ${code})", max = 0, message = "Region.save.code.exist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from Region r where (r.id = ${id})", min = 1, message = "Region.update.id.notexist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from Region r where (r.id != ${id}) and (r.code = ${code})", max = 0, message = "Region.update.code.notunique")
})
@Entity(name = "Region")
@Table(name = "GENEZIS_REGION")
public class Region extends TPSParameterBase {
	
    /**
	 * ID Genere Par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Pays parent de la region
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID", nullable = false)
	@NotNull(message = "Region.country.null")
	private Country  country;
	
	/**
	 * Constructeur par defaut
	 */
	public Region() {
		
		// Appel Parent
		super();
	}
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param code	Code de la region
	 * @param designation	Nom de la region
	 * @param country	Pays parent de la region
	 */
	public Region(String code, String designation, Country country) {
		
		// Appel parent
		super(code, designation);
		
		// Initialisation du pays
		this.country = country;
	}
	
	/**
	 * Methode d'obtention du Pays parent de la region
	 * @return	Pays parent de la region
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * Methode de mise a jour du Pays parent de la region
	 * @param country parent de la region
	 */
	public void setCountry(Country country) {
		this.country = country;
	}
}
