/**
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
package com.yashiro.persistence.utils.test.dao.entities.embedded;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.yashiro.persistence.utils.annotations.validator.Email;
import com.yashiro.persistence.utils.annotations.validator.PhoneNumber;

/**
 * Classe representant une adresse
 * @author NKOU NKOU Joseph Junior
 * @author Jean-Jacques
 * @version 1.0
 */
@Embeddable
public class Adress implements Serializable {
	
	/**
	 * Default Serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Pattern des numero de telephone
	 */
	public static final String PHONE_PATTERN = "\\+{0,1}\\d+";
	
   /**
	 * Num�ro du t�l�phone fixe
	 */
	@Column(name = "FIXED_PHONE_NUMBER")
	@PhoneNumber(message = "Adress.fixedPhone.invalidtoken")
	private String fixedPhone;
	
	/**
	 * T�l�phone du t�l�phone portable
	 */
	@Column(name = "MOBILE_PHONE_NUMBER")
	@PhoneNumber(message = "Adress.mobilePhone.invalidtoken")
	private String mobilePhone;
	
	/**
	 * Num�ro du fax
	 */
	@Column(name = "FAX_NUMBER")
	@PhoneNumber(message = "Adress.fax.invalidtoken")
	private String fax; 
	
	/**
	 * Adresse �lectronique
	 */
	@Column(name = "EMAIL")
	@Email(message = "Adress.email.invalid")
	private String email;
	
	/**
	 * Adresse du site web
	 */
	@Column(name = "WEB_SITE")
	private String webSite;
	
	/**
	 * Boite postale  ex: BP:401
	 */
	@Column(name = "POSTAL_BOX")
	private String poBox; 
	
	/**
	 * Rue
	 */
	@Column(name = "ROAD")
	private String road; 
	
	/**
	 * Constructeur par d�faut
	 */
	public Adress() {}
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param fixedPhone	Numero de telephone fixe
	 * @param mobilePhone	Numero de telephone mobile
	 * @param fax	Numero de FAX
	 * @param email	Adresse Email
	 * @param webSite	URL  du site Web
	 * @param poBox	Boite postale
	 * @param road	Rue
	 */
   public Adress(String fixedPhone, String mobilePhone, String fax,
			String email, String webSite, String poBox, String road, String town) {
		super();
		this.fixedPhone = fixedPhone;
		this.mobilePhone = mobilePhone;
		this.fax = fax;
		this.email = email;
		this.webSite = webSite;
		this.poBox = poBox;
		this.road = road;
	}


   /**
	 * Methode permettant d'obtenir le Numero de telephone fixe
	 * @return Numero de telephone fixe de l'entreprise
	 */
	public String getFixedPhone() {
		return fixedPhone;
	}

	/**
	 * Methode permettant de mettre a jour le Numero de telephone fixe
	 * @param fixedPhone	Numero de telephone fixe de l'entreprise
	 */
	public void setFixedPhone(String fixedPhone) {
		this.fixedPhone = fixedPhone;
	}

	/**
	 * Methode permettant d'obtenir le umero de telephone mobile
	 * @return umero de telephone mobile de l'entreprise
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * Methode permettant de mettre a jour le umero de telephone mobile
	 * @param mobilePhone	umero de telephone mobile
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	/**
	 * Methode permettant d'obtenir le Numero de FAX
	 * @return Numero de FAX
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * Methode permettant de d�finir le num�ro du fax
	 * @param fax	Numero de FAX
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	/**
	 * Methode permettant d'obtenir l'Adresse Email
	 * @return Adresse Email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Methode permettant de mettre a jour l'Adresse Email
	 * @param email	Adresse Email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Methode permettant d'obtenir l'URL  du site Web
	 * @return URL  du site Web
	 */
	public String getWebSite() {
		return webSite;
	}

	/**
	 * Methode permettant de d�finir l'URL  du site Web
	 * @param webSite	URL  du site Web
	 */
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	/**
	 * Methode permettant d'obtenir la boite postale
	 * @return Boite postale
	 */
	public String getPoBox() {
		return poBox;
	}

	/**
	 * Methode permettant de mettre a jour la boite postale
	 * @param poBox	Boite postale
	 */
	public void setPoBox(String poBox) {
		this.poBox = poBox;
	}

	/**
	 * Methode permettant d'obtenir la rue
	 * @return Rue
	 */
	public String getRoad() {
		return road;
	}

	/**
	 * Methode permettant de mettre a jour la rue
	 * @param road	Rue
	 */
	public void setRoad(String road) {
		this.road = road;
	}
}
