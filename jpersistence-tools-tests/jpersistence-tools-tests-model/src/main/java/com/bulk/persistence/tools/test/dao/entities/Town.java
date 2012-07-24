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

import com.bulk.persistence.tools.api.dao.constants.DAOMode;
import com.bulk.persistence.tools.api.validator.annotations.SizeDAOValidator;
import com.bulk.persistence.tools.api.validator.annotations.SizeDAOValidators;
import com.bulk.persistence.tools.test.dao.entities.base.TPSParameterBase;

/**
 * Classe representant une ville attachee a un Pays
 * @author  Jean-Jacques ETUNÈ NGI
 * @see {@link TPSParameterBase}
 */
@SizeDAOValidators({
	@SizeDAOValidator(mode = DAOMode.SAVE,   expr = "from Town t where (t.code = ${code})", max = 0, message = "Town.save.code.exist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from Town t where (t.id = ${id})", min = 1, message = "Town.update.id.notexist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from Town t where (t.id != ${id}) and (t.code = ${code})", max = 0, message = "Town.update.code.notunique")
})
@Entity(name = "Town")
@Table(name = "GENEZIS_TOWN")
public class Town extends TPSParameterBase {

    /**
	 * ID Genere Par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Region parente de la ville
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGION_ID", nullable = false)
	@NotNull(message = "Town.region.null")
	private Region  region;
	
	/**
	 * Constructeur par defaut
	 */
	public Town() {
		
		// Appel Parent
		super();
	}
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param code	Code de la region
	 * @param designation	Nom de la region
	 * @param region	Region parente de la ville
	 */
	public Town(String code, String designation, Region region) {
		
		// Appel parent
		super(code, designation);
		
		// Initialisation de la region
		this.region = region;
	}
	
	/**
	 * Methode d'obtention de la Region parente de la ville
	 * @return	Region parente de la ville
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * Methode de mise a jour de la Region parente de la ville
	 * @param region parente de la ville
	 */
	public void setRegion(Region region) {
		this.region = region;
	}
}
