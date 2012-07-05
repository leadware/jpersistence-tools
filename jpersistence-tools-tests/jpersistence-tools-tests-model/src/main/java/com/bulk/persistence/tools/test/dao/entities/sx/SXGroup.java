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
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.bulk.persistence.tools.api.validator.annotations.SizeDAOValidator;
import com.bulk.persistence.tools.api.validator.annotations.SizeDAOValidators;
import com.bulk.persistence.tools.api.validator.jsr303ext.annotations.Length;
import com.bulk.persistence.tools.dao.api.constants.DAOMode;
import com.bulk.persistence.tools.test.dao.entities.sx.constants.UserExpirationStrategy;

/**
 * Classe representant un ensemble de Roles du Systeme a proteger (Groupe)
 * @author Jean-Jacques ETUNÈ NGI
 */
@SizeDAOValidators({
	@SizeDAOValidator(mode = DAOMode.SAVE,   expr = "from SXGroup g where (g.name = ${name})", max = 0, message = "SXGroup.save.name.exist"),
	@SizeDAOValidator(mode = DAOMode.SAVE,   expr = "from SXGroup g where (g.code = ${code})", max = 0, message = "SXGroup.save.code.exist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from SXGroup g where (g.id = ${id})", min = 1, message = "SXGroup.update.id.notexist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from SXGroup g where (g.id != ${id}) and (g.name = ${name})", max = 0, message = "SXGroup.update.name.notunique"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from SXGroup g where (g.id != ${id}) and (g.code = ${code})", max = 0, message = "SXGroup.update.code.notunique")
})
@Entity(name = "SXGroup")
@Table(name = "SX_GROUP")
@SequenceGenerator(name="Seq_SXGroup", sequenceName="SEQ_SX_GROUP", allocationSize = 1, initialValue = 1)
public class SXGroup implements Serializable, Comparable<SXGroup> {
	
	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
		   
	/**
	 * Identifiant du Groupe
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Seq_SXGroup")
    @Column(name = "ID")
	private Long id;
	
	/**
	 * Code du groupe
	 */
	@Column(name = "CODE", nullable = false, unique = true)
	@Length(min = 1, message = "SXGroup.code.empty")
	private String code; 
	
	/**
	 * Nom du Groupe
	 */
	@Column(name = "NAME", nullable = false, unique = true)
	@Length(min = 1, message = "SXGroup.name.empty")
	private String name;
	
	/**
	 * Description du Groupe
	 */
	@Column(name = "DESCRIPTION")
	private String description;
	
	/**
	 * Strategie de gestion de l'expiration des utilisateurs du Groupe
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "EXPIRATION_STRATEGY", nullable = false)
	@NotNull(message = "SXGroup.expirationStrategy.null")
	private UserExpirationStrategy expirationStrategy = UserExpirationStrategy.PERMANENT; 
	
	/**
	 * Date de creation
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATION_DATE", nullable = false)
	private Date creationDate = new Date();
	
	/**
	 * Periode de validite des utilisateurs du groupe
	 */
	private Integer validityPeriod = -1;
		
	/**
	 * Ensemble des roles de ce RolesSet
	 */
	@ManyToMany(cascade = CascadeType.REMOVE)
	@JoinTable(
		name = "SX_AUTHORIZATION",
		joinColumns = {@JoinColumn(name = "GROUP_ID")},
		inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")}
	)
	private Set<SXRole> roles = new HashSet<SXRole>();

	/**
	 * Version de l'enregistrement
	 */
	@Version
	@Column(name = "GROUP_VERSION")
	private Integer version = 0;
	
	/**
	 * Constructeur par defaut
	 */
	public SXGroup() {}   
	
	public SXGroup(SXGroup group) {
		this.id = group.id;
		this.code = group.code;
		this.name = group.name;
		this.description = group.description;
		this.roles = new HashSet<SXRole>(group.getRoles());
	}
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param roles	Ensemble des roles de ce RolesSet
	 */
	public SXGroup(String code, String name, String description, Set<SXRole> roles) {
		this.roles = roles;
		this.code = code;
		this.description = description;
		this.name = name;
		if(this.code != null) this.code = this.code.trim().toUpperCase();
		if(this.name != null) this.name = this.name.trim().toUpperCase();
	}
	
	/**
	 * Methode d'obtention de l'Identifiant du role
	 * @return	Identifiant du role
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Methode de mise a jour de l'Identifiant du role
	 * @param id	Identifiant du role
	 */
	public void setId(Long id) {
		this.id = id;
	}

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
	 * Methode d'obtention du Nom du Groupe
	 * @return Nom du Groupe
	 */
	public String getName() {
		return name;
	}

	/**
	 * Methode de mise a jour du Nom du Groupe
	 * @param name Nom du Groupe
	 */
	public void setName(String name) {
		this.name = name;
		if(this.name != null) this.name = this.name.trim().toUpperCase();
	}
	
	/**
	 * Methode d'obtention du Code du Groupe
	 * @return Code du Groupe
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Methode de mise a jour du Code du Groupe
	 * @param code Code du Groupe
	 */
	public void setCode(String code) {
		this.code = code;
		if(this.code != null) this.code = this.code.trim().toUpperCase();
	}

	/**
	 * Methode d'Obtention de la Description du Groupe
	 * @return Description du Groupe
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Methode de mise a jour de la Description du Groupe
	 * @param description Description du Groupe
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Methode d'obtention de la Strategie de gestion de l'expiration des utilisateurs du Groupe
	 * @param description Strategie de gestion de l'expiration des utilisateurs du Groupe
	 */
	public UserExpirationStrategy getExpirationStrategy() {
		return expirationStrategy;
	}

	/**
	 * Methode de mise a jour de la Strategie de gestion de l'expiration des utilisateurs du Groupe
	 * @return Strategie de gestion de l'expiration des utilisateurs du Groupe
	 */
	public void setExpirationStrategy(UserExpirationStrategy expirationStrategy) {
		this.expirationStrategy = expirationStrategy;
	}

	/**
	 * Methode d'obtention de la Date de creation
	 * @return Date de creation
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Methode de mise a jour de la Date de creation
	 * @param creationDate Date de creation
	 */
	protected void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
		
		// Si la Date est nulle
		if(this.creationDate == null) this.creationDate = new Date();
	}

	/**
	 * Methode d'obtention de la Periode de validite des utilisateurs du groupe
	 * @return Periode de validite des utilisateurs du groupe
	 */
	public Integer getValidityPeriod() {
		return validityPeriod;
	}

	/**
	 * Methode de mise a jour de la Periode de validite des utilisateurs du groupe
	 * @param validityPeriod Periode de validite des utilisateurs du groupe
	 */
	public void setValidityPeriod(Integer validityPeriod) {
		this.validityPeriod = validityPeriod;
	}
	
	/**
	 * Methode d'obtention de l'Ensemble des roles de ce RolesSet
	 * @return Ensemble des roles de ce RolesSet
	 */
	public Set<SXRole> getRoles() {
		return Collections.unmodifiableSet(roles);
	}

	/**
	 * Methode de mise a jour de l'Ensemble des roles de ce RolesSet
	 * @param roles Ensemble des roles de ce RolesSet
	 */
	public void setRoles(Set<SXRole> roles) {
		this.roles = roles;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object parameter) {
		
		// Si le parametre est null
		if(parameter == null) return false;
		
		// Si le parametre n'est pas de l'instance
		if(!(parameter instanceof SXGroup)) return false;
		
		// On caste
		SXGroup data = (SXGroup) parameter;
		
		// Si le nom du groupe est vide
		if(data.name == null || data.name.trim().length() == 0) return false;
		
		// Si le nom en cours est vide
		if(name == null || name.trim().length() == 0) return false;
		
		// On retourne la comparaison des noms
		return name.equals(data.name);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(SXGroup parameter) {
		
		// Si le parametre est null
		if(parameter == null) return -1;
		
		// Si le nom du role est vide
		if(parameter.name == null || parameter.name.trim().length() == 0) return -1;
		
		// Si le nom du role en cours est vide
		if(name == null || name.trim().length() == 0) return 1;
		
		// On retourne la comparaison des noms
		return name.compareTo(parameter.name);
	}
}
