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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bulk.persistence.tools.api.validator.annotations.SizeDAOValidator;
import com.bulk.persistence.tools.api.validator.annotations.SizeDAOValidators;
import com.bulk.persistence.tools.api.validator.jsr303ext.annotations.Length;
import com.bulk.persistence.tools.dao.api.constants.DAOMode;

/**
 * Classe representant un Role du Systeme a proteger
 * @author Jean-Jacques ETUNÈ NGI
 */
@SizeDAOValidators({
	@SizeDAOValidator(mode = DAOMode.SAVE,   expr = "from SXRole r where (r.name = ${name})", max = 0, message = "SXRole.save.name.exist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from SXRole r where (r.id = ${id})", min = 1, message = "SXRole.update.id.notexist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from SXRole r where (r.id != ${id}) and (r.name = ${name})", max = 0, message = "SXRole.update.name.notunique")
})
@Entity(name = "SXRole")
@Table(name = "SX_ROLE")
@SequenceGenerator(name="Seq_SXRole", sequenceName="SEQ_SX_ROLE", allocationSize = 1, initialValue = 1)
public class SXRole implements Serializable, Comparable<SXRole> {
	
	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identifiant du role
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Seq_SXRole")
    @Column(name = "ID")
	private Long id;
	
	/**
	 * Nom du role
	 */
	@Column(name = "NAME", unique = true)
	@Length(min = 1, message = "SXRole.name.empty")
	private String name;
	
	/**
	 * Nom d'affichage du role
	 */
	@Column(name = "DISPLAYNAME")
	@Length(min = 1, message = "SXRole.displayName.empty")
	private String displayName = "";
	
	/**
	 * Constructeur par defaut
	 */
	public SXRole() {}   
		    
	/**
	 * Constructeur avec initialisation des parametres
	 * @param name	Nom du role
	 */
	public SXRole(String name) {
		this.name = name;
		
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
	 * Methode d'obtention du Nom du role
	 * @return	Nom du role
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Methode de mise a jour du Nom du role
	 * @param name	Nom du role
	 */
	public void setName(String name) {
		this.name = name;
		
	}   
	
	/**
	 * Methode d'obtention du Nom d'affichage du role
	 * @return Nom d'affichage du role
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Methode de mise a jour du Nom d'affichage du role
	 * @param displayName Nom d'affichage du role
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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
		if(!(parameter instanceof SXRole)) return false;
		
		// On caste
		SXRole data = (SXRole) parameter;
		
		// Si le nom du role est vide
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
	public int compareTo(SXRole parameter) {
		
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