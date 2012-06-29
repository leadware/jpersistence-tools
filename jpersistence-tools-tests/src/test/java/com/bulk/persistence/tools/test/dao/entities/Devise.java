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

import com.bulk.persistence.tools.test.dao.entities.base.TPSParameterBase;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Classe representant une region du systeme
 * @author Jean-Jacques
 * @see {@link TPSParameterBase}
 * @version 1.0
 */

@Entity(name = "Devise")
@Table(name = "GENEZIS_DEVISE")
public class Devise extends TPSParameterBase {

	/**
	 * ID genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Taux de change de la devise par rapport
	 * � la devise de r�f�rence
	 */
	private Float exchangeRate;
	
	/**
	 * Code ISO de la devise.
	 */
	private String codeISO;
	

	/**
	 * Constructeur par d�faut
	 */
	public Devise(){
		
		//Appel parent
		super();
	}
	
	/**
	 * Constructeur d'initialisation
	 * @param code	Code local de la devise
	 * @param designation	Description de la devise
	 * @param exchangeRate	Taux de change
	 * @param codeISO	Code ISO de la devise
	 */
	public Devise(String code, String designation, Float exchangeRate, String codeISO){

		//Appel du constructeur du parent
		super(code, designation);
		
		// On positionne les parametres additionnels
		this.codeISO = codeISO;
		this.exchangeRate = exchangeRate;
	}

	/**
	 * Methode permettant d'obtenir le taux de change de la devise
	 * par rapport � la devise de r�f�rence. 
	 * @return Taux de change
	 */
	public float getExchangeRate() {
		return exchangeRate;
	}

	/**
	 * Methode permettant de d�finir le taux de change 
	 * de la devise par rapport � la devise de r�f�rence
	 * @param exchangeRate
	 */
	public void setExchangeRate(float exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * Methode permettant d'obtenir le Code ISO de la devise
	 * @return Code ISO
	 */
	public String getCodeISO() {
		return codeISO;
	}
	
   /**
    * Methode permettant de d�finir le Code ISO de la devise.
    * @param codeISO 
    */
	public void setCodeISO(String codeISO) {
		this.codeISO = codeISO;
	}
}
