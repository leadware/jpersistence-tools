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
package com.bulk.persistence.tools.test.dao.entities.sx.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.bulk.persistence.tools.api.validator.jsr303ext.annotations.NotEmpty;
import com.bulk.persistence.tools.test.dao.entities.sx.constants.Sex;
import com.bulk.persistence.tools.test.dao.entities.sx.constants.UserState;

/**
 * Classe de base des Utilisateurs
 * @author Jean-Jacques ETUNÈ NGI
 */
@MappedSuperclass
public class UserBase implements Serializable, Comparable<UserBase> {
	
	/**
	 * ID Genere par Eclipse
	 */
	protected static final long serialVersionUID = 1L;
	

	/**
	 * Duree minimale de validite de session inactive
	 */
	public static final int MIN_SESSION_INVALIDITY_MAX = 10;
	
	/**
	 * Nom de l'Utilisateur
	 */
	@Column(name = "LASTNAME", nullable = false)
	@NotEmpty(message = "userbase.lastname.empty")
	protected String lastName;

	/**
	 * Prenom de l'Utilisateur
	 */
	@Column(name = "FIRSTNAME")
	protected String firstName;

	/**
	 * Nom de connexion de l'utilisateur (Unique)
	 */
	@Column(name = "LOGIN", nullable = false, unique = true)
	@NotEmpty(message = "userbase.login.notempty")
	protected String login;
	
	/**
	 * Mot de passe de l'utilisateur
	 */
	@Column(name = "PASSWORD", nullable = false)
	@NotEmpty(message = "userbase.password.notempty")
	protected String password;
	
	/**
	 * Email de l'utilisateur
	 */
	@Column(name = "EMAIL", nullable = true)
	protected String email;
	
	/**
	 * Numero de telephone de l'utilisateur
	 */
	@Column(name = "PHONE", nullable = true)
	@Pattern(regexp = "\\+{0,1}\\d{5,20}", message = "userbase.phone.invalidphone")
	protected String phone;

	/**
	 * Etat de l'utilisateur (Valide ou Suspendu)
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "STATE", nullable = false)
	@NotNull(message = "userbase.state.null")
	protected UserState state = UserState.VALID;

	/**
	 * Sexe de l'Utilisateur
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "SEX", nullable = false)
	@NotNull(message = "userbase.sex.null")
	protected Sex sex = Sex.MAN;
	
	/**
	 * Duree Maximale (en minute) d'une session Inactive (30 mn)
	 */
	@Column(name = "SESSION_INACTIVITY_MAX_TIME", nullable = false)
	@NotNull(message = "userbase.sessionInactivityMaxTime.null")
	@Min(value = MIN_SESSION_INVALIDITY_MAX, message = "userbase.sessionInactivityMaxTime.minvalue")
	protected Integer sessionInactivityMaxTime = 30;
	
	/**
	 * Version de l'enregistrement
	 */
	@Version
	@Column(name = "USER_VERSION")
	private Integer version = 0;
	
	/**
	 * Skin de l'Utilisateur
	 */
	@Column(name = "USER_SKIN")
	private String skin = "e-DarkX";
	
	/**
	 * Constructeur avec initialisation
	 * @param lastName	Nom de l'Utilisateur
	 * @param firstName	Prenom de l'Utilisateur
	 * @param sex Sexe de l'Utilisateur
	 * @param login		Nom de connexion de l'utilisateur
	 * @param password	Mot de passe de l'utilisateur
	 * @param email		Email de l'utilisateur
	 * @param phone		Numero de telephone de l'utilisateur
	 * @param state		Etat de l'utilisateur
	 */
	public UserBase(String lastName, String firstName, Sex sex, String login, String password, String email, String phone, UserState state) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.login = login;
		this.password = password;
		this.email = email;
		this.phone = phone;		
		this.setState(state);
		this.setSex(sex);
		this.setSessionInactivityMaxTime(sessionInactivityMaxTime);
		
		if(this.login != null) this.login = this.login.trim().toUpperCase();
		if(this.firstName != null) this.firstName = this.firstName.trim().toUpperCase();
		if(this.lastName != null) this.lastName = this.lastName.trim().toUpperCase();
	}
	
	/**
	 * Constructeur par defaut
	 */
	public UserBase() {}

	/**
	 * Methode d'obtention de la Version de l'enregistrement
	 * @return Version de l'enregistrement
	 */
	public Integer getVersion() {
		return version;
	}
	
	/**
	 * Methode de mise a jour de la Version de l'enregistrement
	 * @param version Version de l'enregistrement
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	/**
	 * Methode d'obtention du Nom de l'Utilisateur
	 * @return Nom de l'Utilisateur
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * Methode de mise a jour du Nom de l'Utilisateur
	 * @param lastName Nom de l'Utilisateur
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
		
		if(this.lastName != null) this.lastName = this.lastName.trim().toUpperCase();
	}


	/**
	 * Methode d'obtention du Prenom de l'Utilisateur
	 * @return Prenom de l'Utilisateur
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * Methode de mise a jour du Prenom de l'Utilisateur
	 * @param firstName Prenom de l'Utilisateur
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		
		if(this.firstName != null) this.firstName = this.firstName.trim().toUpperCase();
	}
	
	/**
	 * Methode d'obtention du login de l'utilisateur
	 * @return Login de l'utilisateur
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Methode de mise a jour du login de l'utilisateur
	 * @param login Nouveau Login
	 */
	public void setLogin(String login) {
		this.login = login;
		if(this.login != null) this.login = this.login.trim().toUpperCase();
	}

	/**
	 * Methode d'obtention du mot de passe de l'utilisateur
	 * @return Mot de passe de l'utilisateur
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Methode de mise a jour du mot de passe de l'utilisateur
	 * @param password Nouveau mot de passe
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Methode d'obtention de l'email de l'utilisateur
	 * @return Email de l'utilisateur
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Methode de mise a jour de l'email de l'utilisateur
	 * @param email Nouvel Email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Methode d'obtention du telephone de l'utilisateur
	 * @return telephone de l'utilisateur
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Methode de mise a jour du telephone de l'utilisateur
	 * @param phone Nouveau numero de telephone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Methode d'obtention de l'etat de l'utilisateur
	 * @return Etat de l'utilisateur
	 */
	public UserState getState() {
		return state;
	}

	/**
	 * Methode de mise a jour de l'etat de l'utilisateur
	 * @param state Nouvel Etat
	 */
	public void setState(UserState state) {
		this.state = state;
		
		// Si l'etat est null
		if(this.state == null) this.state = UserState.VALID;
	}
	
	/**
	 * Methode permettant de savoir si l'Utilisateur est suspendu
	 * @return	Etat de suspension de l'Utilisateur
	 */
	public boolean isSuspended() {
		
		// On retourne l'etat
		return this.state.equals(UserState.SUSPENDED);
	}

	/**
	 * Methode permettant de savoir si l'Utilisateur est Valide
	 * @return	Etat de Validite de l'Utilisateur
	 */
	public boolean isValid() {
		
		// On retourne l'etat
		return this.state.equals(UserState.VALID);
	}
	
	/**
	 * Methode d'obtention du Sexe de l'Utilisateur
	 * @return Sexe de l'Utilisateur
	 */
	public Sex getSex() {
		return sex;
	}


	/**
	 * Methode de mise a jour du Sexe de l'Utilisateur
	 * @param sex Sexe de l'Utilisateur
	 */
	public void setSex(Sex sex) {
		this.sex = sex;
		
		// Si le sex est null
		if(sex == null) this.sex = Sex.MAN;
	}
	
	/**
	 * Methode d'obtention de la Duree Maximale (en minute) d'une session Inactive (30 mn)
	 * @return Duree Maximale (en minute) d'une session Inactive (30 mn)
	 */
	public Integer getSessionInactivityMaxTime() {
		return sessionInactivityMaxTime;
	}
	
	/**
	 * Methode de mise a jour de la Duree Maximale (en minute) d'une session Inactive (30 mn)
	 * @param sessionInactivityMaxTime Duree Maximale (en minute) d'une session Inactive (30 mn)
	 */
	public void setSessionInactivityMaxTime(Integer sessionInactivityMaxTime) {
		this.sessionInactivityMaxTime = sessionInactivityMaxTime;
		
		// Si la duree est nulle
		if(sessionInactivityMaxTime == null) this.sessionInactivityMaxTime = 30;
	}
	
	/**
	 * Methode d'obtention du Skin de l'Utilisateur
	 * @return Skin de l'Utilisateur
	 */
	public String getSkin() {
		return skin;
	}

	/**
	 * Methode de mise a jour du Skin de l'Utilisateur
	 * @param skin Skin de l'Utilisateur
	 */
	public void setSkin(String skin) {
		this.skin = skin;
		
		// Si le skin est null
		if(this.skin == null || this.skin.trim().length() == 0) this.skin = "Classic";
	}
	
	@Override
	public boolean equals(Object obj) {
		
		// Si le parametre est null : false
		if(obj == null) return false;
		
		// Si le parametre n'est pas de la classe : false
		if(!(obj instanceof UserBase)) return false;
		
		// On caste
		UserBase user = (UserBase) obj;
		
		// Si le login du parametre est null
		if(user.login == null || user.login.trim().length() == 0) return false;

		// Si le login en cours est null
		if(login == null || login.trim().length() == 0) return false;
						
		// On retourne la comparaison des login
		return this.login.equalsIgnoreCase(user.login);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(UserBase user) {
		
		// Si le parametre est null : 1
		if(user == null) return 1;
		
		// Si l'utilisateur n'a pas de nom de connexion : 1
		if(user.getLogin() == null || user.getLogin().trim().length() == 0) return 1;
		
		// Si l'objet en cours n'a pas de nom de connexion : -1
		if(this.getLogin() == null || this.getLogin().trim().length() == 0) return -1;
		
		// On retourne le resultat
		return this.getLogin().compareTo(user.getLogin());
	}
	
}
