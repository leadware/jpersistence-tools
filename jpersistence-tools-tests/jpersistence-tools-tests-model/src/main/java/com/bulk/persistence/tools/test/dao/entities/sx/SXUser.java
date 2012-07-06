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
package com.bulk.persistence.tools.test.dao.entities.sx;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bulk.persistence.tools.api.validator.annotations.SizeDAOValidator;
import com.bulk.persistence.tools.api.validator.annotations.SizeDAOValidators;
import com.bulk.persistence.tools.dao.api.constants.DAOMode;
import com.bulk.persistence.tools.test.dao.entities.Town;
import com.bulk.persistence.tools.test.dao.entities.sx.base.UserBase;
import com.bulk.persistence.tools.test.dao.entities.sx.constants.Sex;
import com.bulk.persistence.tools.test.dao.entities.sx.constants.UserState;

/**
 * Classe representant un utilisateur de la plateforme
 * @author Jean-Jacques ETUNÈ NGI
 */
@SizeDAOValidators({
	@SizeDAOValidator(mode = DAOMode.SAVE,   expr = "from SXUser u where (u.login = ${login})", max = 0, message = "user.save.login.exist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from SXUser u where (u.id = ${id})", min = 1, message = "user.update.id.notexist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from SXUser u where (u.id != ${id}) and (u.login = ${login})", max = 0, message = "user.update.login.notunique")
})
@Entity(name = "SXUser")
@Table(name = "SX_USER")
@SequenceGenerator(name="Seq_SXUser", sequenceName="SEQ_SX_USER", allocationSize = 1, initialValue = 1)
public class SXUser extends UserBase implements Serializable {
	
	/**
	 * ID Genere par eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identifiant du role
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Seq_SXUser")
    @Column(name = "ID")
	protected Long id;
	
	/**
	 * Groupes de l'Utilisateur
	 */
	@ManyToMany
	@JoinTable(
			name = "SX_USER_GROUP",
			joinColumns = {@JoinColumn(name = "USER_ID")},
			inverseJoinColumns = {@JoinColumn(name = "GROUP_ID")}
		)
	protected Set<SXGroup> groups;
	
	/**
	 * Ensemble des roles privés de ce User
	 */
	@ManyToMany
	@JoinTable(
		name = "SX_USER_PRIVATE_AUTHORIZATION",
		joinColumns = {@JoinColumn(name = "USER_ID", unique = false)},
		inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", unique = false)}
	)
	private Set<SXRole> privateRoles = new HashSet<SXRole>();

	/**
	 * Constructeur par defaut
	 */
	public SXUser() {}
	
	/**
	 * Constructeur par recopie
	 * @param usr
	 */
	public SXUser(SXUser usr) {
		this.email = usr.email;
		this.firstName = usr.firstName;
		this.id = usr.id;
		this.lastName = usr.lastName;
		this.login = usr.login;
		this.password = usr.password;
		this.phone = usr.phone;
		this.sessionInactivityMaxTime = usr.sessionInactivityMaxTime;
		this.sex = usr.sex;
		this.state = usr.state;
		this.groups = new HashSet<SXGroup>(usr.groups);
	}
	
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
	public SXUser(String lastName, String firstName, Sex sex, String login, String password, String email, String phone, UserState state) {
		
		// Appel Parent
		super(lastName, firstName, sex, login, password, email, phone, state);
	}

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
	public SXUser(String lastName, String firstName, 
				Sex sex, String login, String password, 
				String email, String phone, UserState state, 
				Town town, Set<SXGroup> groups) {
		
		// Appel Parent
		super(lastName, firstName, sex, login, password, email, phone, state);
		
		// Ville
		this.town = town;
		this.groups = groups;
	}
	
	/**
	 * Methode d'obtention du code de l'utilisateur
	 * @return ID de l'utilisateur
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Methode de mise a jour du code de l'utilisateur
	 * @param id Nouvel Identifiant
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Methode d'obtention des Groupes de l'Utilisateur
	 * @return Groupes de l'Utilisateur
	 */
	public Set<SXGroup> getGroups() {
		return groups;
	}
	
	/**
	 * Methode de mise a jour des Groupes de l'Utilisateur
	 * @param groups Groupes de l'Utilisateur
	 */
	public void setGroups(Set<SXGroup> groups) {
		this.groups = groups;
		
		// Si le Set est vide
		if(this.groups == null) this.groups = new HashSet<SXGroup>();
	}

	/**
	 * Methode d'obtention des Roles privés de l'Utilisateur
	 * @return Roles privés de l'Utilisateur
	 */
	public Set<SXRole> getPrivateRoles() {
		return privateRoles;
	}
	
	/**
	 * Methode de mise a jour des Roles privés de l'Utilisateur
	 * @param privateRoles Roles privés de l'Utilisateur
	 */
	public void setPrivateRole(Set<SXRole> privateRoles) {
		this.privateRoles = privateRoles;
		
		// Si le Set est vide
		if(this.privateRoles == null) this.privateRoles = new HashSet<SXRole>();
	}
	
	/**
	 * Retourne le nom complet de l'utilisateur
	 * @return completeUserName
	 */
	public String getCompleteUserName(){
		
		return (this.lastName != null ? this.lastName : "") + " " + (this.firstName != null ? this.firstName : "");  
		
	}
}
