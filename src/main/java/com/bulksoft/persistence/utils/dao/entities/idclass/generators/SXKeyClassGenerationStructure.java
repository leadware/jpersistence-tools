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
package com.bulksoft.persistence.utils.dao.entities.idclass.generators;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe representant la structure de la configuration du generateur StringKeyClassGenerator
 * @author Jean-Jacques
 * @version 1.0
 */
@Entity(name = "SXKeyClassGenerationStructure")
@Table(name = SXKeyClassGenerationStructure.GENERATION_TABLE_NAME)
public class SXKeyClassGenerationStructure implements Serializable {

	/**
	 * ID Genere par eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Nom de la table de Generation
	 */
	public static final String GENERATION_TABLE_NAME = "T_GENERATOR";
	
	/**
	 * Nom de la colonne portant le nom de la classe pour laquelle la cle est generee
	 */
	public static final String GENERATION_DISCRIMINATOR_COLUMN_NAME = "GENERATOR_CLASS";
	
	/**
	 * Nom de la colonne portant la derniere valeur generee
	 */
	public static final String GENERATION_LAST_VALUE_COLUMN_NAME = "LAST_VALUE";
	
	/**
	 * Nom de la colonne portant la derniere valeur generee
	 */
	public static final String GENERATION_ID_COLUMN_NAME = "ID";
		
	/**
	 * Nom de la colonne portant le nombre de lettre a prendre sur le nom de la classe
	 */
	public static final String GENERATION_NAME_SIZE_COLUMN_NAME = "GENERATOR_NAME_SIZE";
	
	/**
	 * Propriete contenant le nom de la classe de generation du prefixe d'ID
	 */
	public static final String PREFIX_GENERATOR_CLASS_NAME = "prefixGeneratorClassName";
	
	/**
	 * ID de la table
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = GENERATION_ID_COLUMN_NAME)
	protected long id;
	
	/**
	 * Nom de la Table de generation
	 */
	@Column(name = GENERATION_TABLE_NAME)
	protected String generationTableName;
	
	/**
	 * Nom de la colonne contenant le nom de la classe pour laquelle on genere l'ID
	 */
	@Column(name = GENERATION_DISCRIMINATOR_COLUMN_NAME)
	protected String generationDiscriminatorColumnName;
	
	/**
	 * Nom de la colonne contenant la derniere valeur generee
	 */
	@Column(name = GENERATION_LAST_VALUE_COLUMN_NAME)
	protected String generationLastValueColumnName;
	
	/**
	 * Nom de la colonne contenant le nombre de caractere a prendre sur le nom de la classe
	 */
	@Column(name = GENERATION_NAME_SIZE_COLUMN_NAME)
	protected String generationNameSizeColumnName;
	
	
	/**
	 * Constructeur par defaut
	 */
	public SXKeyClassGenerationStructure() {}
}
