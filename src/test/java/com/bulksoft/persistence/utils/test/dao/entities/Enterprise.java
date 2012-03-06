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
package com.bulksoft.persistence.utils.test.dao.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bulksoft.persistence.utils.test.dao.entities.base.TPSParameterBase;
import com.bulksoft.persistence.utils.test.dao.entities.embedded.Adress;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Range;
import org.hibernate.validator.Valid;
import com.bulksoft.persistence.utils.annotations.validator.SizeDAOValidator;
import com.bulksoft.persistence.utils.annotations.validator.SizeDAOValidators;
import com.bulksoft.persistence.utils.dao.constant.DAOMode;
import com.bulksoft.persistence.utils.dao.tools.encrypter.Encrypter;

/**
 * Classe representant une Entreprise
 * @author Jean-Jacques
 * @see {@link TPSParameterBase}
 * @version 1.0
 * 
 */
@SizeDAOValidators({
	@SizeDAOValidator(mode = DAOMode.SAVE,   expr = "from Enterprise c where (c.taxPayerNumber = ${taxPayerNumber})", max = 0, message = "Enterprise.save.taxpayernumber.exist"),
	@SizeDAOValidator(mode = DAOMode.SAVE,   expr = "from Enterprise c where (c.code = ${code})", max = 0, message = "Enterprise.save.code.exist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from Enterprise c where (c.id != ${id}) and (c.code = ${code})", max = 0, message = "Enterprise.update.code.notunique"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from Enterprise c where (c.id != ${id}) and (c.taxPayerNumber = ${taxPayerNumber})", max = 0, message = "Enterprise.update.taxPayerNumber.notunique")
})
@Entity(name = "Enterprise")
@Table(name = "GENEZIS_ENTERPRISE")
public class Enterprise extends TPSParameterBase {
	
	/**
	 * Code d'iodentification interne de la configuration
	 */
	public static final String DEFAULT_CODE = Encrypter.getInstance().hashText("Kenji Katsumoto").toUpperCase();
	
    /**
	 * ID Genere Par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Sigle de l'entreprise
	 */
	@Column(name = "SIGLE", nullable = false)
	@Length(min = 1, message = "Enterprise.sigle.length")
	private String sigle;
	
	/**
     * Si�ge de l'entreprise
     */
	@Column(name = "SIEGE", nullable = false)
	@Length(min = 1, message = "Enterprise.siege.length")
	private String siege; 
     
    /**
     * Capital de l'entreprise
     */
	@Column(name = "CAPITAL", nullable = false)
	@NotNull(message = "Enterprise.capital.notnull")
	@Range(min = 0, message = "Enterprise.capital.range")
	private Double capital;
    
    /**
     * Num�ro de contribuable de l'entreprise
     */
	@Column(name = "TAXPAYERNUMBER", unique = true)
	@Length(min = 1, message = "Enterprise.taxPayerNumber.length")
	private String taxPayerNumber;
    
    /**
     * Secteur d'activit� de l'entreprise
     */
	@Column(name = "ACTIVITY_SECTOR")
	@Length(min = 1, message = "Enterprise.activitySector.length")
	private String activitySector;
    
    /**
     * Logo de l'entreprise
     */
	@Embedded
    private EmbeddedData logo = new EmbeddedData();
    
    /**
     * Slogan de l'entreprise
     */
    @Column(name = "SLOGAN")
    private String slogan; 
     
    /**
     * Commentaire autres sur l'entreprise
     */
    @Column(name = "DETAILS", length = 1000)
    private String details;
    
    /**
     * Adresse de l'entreprise
     */
    @Embedded
    @Valid
    private Adress adress;
    
    /**
     * Ville d'installation du siege de l'entreprise
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "TOWN_ID", nullable = false)
    @NotNull(message = "enterprise.town.null")
    private Town town;
    
    /**
     * Constructeur par d�faut
     */
    public Enterprise(){
    	
    	//Appel parent
    	super();
    	setCode(DEFAULT_CODE);
    }
    
    /**
     * Constructeur avec initialisation des parametres
     * @param libelle	Nom de l'entreprise
     * @param siege	Siege de l'entreprise
     * @param capital	Capital de l'entreprise
     * @param taxPayerNumber	Numero contribuable de l'entreprise
     * @param activitySector	Secteur d'activite de l'entreprise
     * @param logo	Logo de l'entreprise
     * @param slogan	Slogan de l'entreprise
     * @param details	Autres commentaires sur l'entreprise
     * @param adress	Adresse de l'entreprise
     * @param town	Ville d'installation du siege central de l'entreprise
     */
	public Enterprise(String libelle, String siege, Double capital,
			String taxPayerNumber, String activitySector, EmbeddedData logo,
			String slogan, String details, Adress adress, Town town) {
		super(DEFAULT_CODE, libelle);
		this.siege = siege;
		this.capital = capital;
		this.taxPayerNumber = taxPayerNumber;
		this.activitySector = activitySector;
		this.logo = logo;
		this.slogan = slogan;
		this.details = details;
		this.adress = adress;
		this.town = town;
		
		// Si le Logo est null
		if(this.logo == null) this.logo = new EmbeddedData();
	}
	
	@Override
	public void setCode(String code) {
		
		// On positionne le code
		super.setCode(DEFAULT_CODE);
	}
	
	/**
	 * Methode d'obtention du Siege de l'entreprise
	 * @return Siege de l'entreprise
	 */
	public String getSiege() {
		return siege;
	}

	/**
	 * Methode de mise a jour du Siege de l'entreprise
	 * @param siege Siege de l'entreprise
	 */
	public void setSiege(String siege) {
		this.siege = siege;
	}

	/**
	 * Methode d'obtention du Sigle de l'entreprise
	 * @return Sigle de l'entreprise
	 */
	public String getSigle() {
		return sigle;
	}

	/**
	 * Methode de mise a jour du Sigle de l'entreprise
	 * @param sigle Sigle de l'entreprise
	 */
	public void setSigle(String sigle) {
		this.sigle = sigle;
	}

	/**
	 * Methode d'obtention du Capital de l'entreprise
	 * @return Capital de l'entreprise
	 */
	public Double getCapital() {
		return capital;
	}

	/**
	 * Methode de mise a jour du Capital de l'entreprise
	 * @param capital	Capital de l'entreprise
	 */
	public void setCapital(Double capital) {
		this.capital = capital;
	}

	/**
	 * Methode d'obtention du Numero contribuable de l'entreprise
	 * @return	Numero contribuable de l'entreprise
	 */
	public String getTaxPayerNumber() {
		return taxPayerNumber;
	}

	/**
	 * Methode de mise a jour du Numero contribuable de l'entreprise
	 * @param taxPayerNumber Numero contribuable de l'entreprise
	 */
	public void setTaxPayerNumber(String taxPayerNumber) {
		this.taxPayerNumber = taxPayerNumber;
	}

	/**
	 * Methode d'obtention du Secteur d'activite de l'entreprise
	 * @return Secteur d'activite de l'entreprise
	 */
	public String getActivitySector() {
		return activitySector;
	}

	/**
	 * Methode de mise a jour du Secteur d'activite de l'entreprise
	 * @param activitySector	Secteur d'activite de l'entreprise
	 */
	public void setActivitySector(String activitySector) {
		this.activitySector = activitySector;
	}

	/**
	 * Methode d'obtention du Logo de l'entreprise
	 * @return	Logo de l'entreprise
	 */
	public EmbeddedData getLogo() {
		return logo;
	}

	/**
	 * Methode de mise a jour du Logo de l'entreprise
	 * @param logo	Logo de l'entreprise
	 */
	public void setLogo(EmbeddedData logo) {
		this.logo = logo;
		
		// Si le Logo est null
		if(logo == null) this.logo  = new EmbeddedData();
	}

	/**
	 * Methode d'obtention du Slogan de l'entreprise
	 * @return	Slogan de l'entreprise
	 */
	public String getSlogan() {
		return slogan;
	}

	/**
	 * Methode de mise a jour du Slogan de l'entreprise
	 * @param slogan	Slogan de l'entreprise
	 */
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	/**
	 * Methode d'obtention des Autres commentaires sur l'entreprise
	 * @return Autres commentaires sur l'entreprise
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * Methode de mise a jour des Autres commentaires sur l'entreprise
	 * @param details	Autres commentaires sur l'entreprise
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * Methode d'obtention de l'Adresse de l'entreprise
	 * @return Adresse de l'entreprise
	 */
	public Adress getAdress() {
		return adress;
	}

	/**
	 * Methode de mise a jour de l'Adresse de l'entreprise
	 * @param adress	Adresse de l'entreprise
	 */
	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	/**
	 * Methode d'obtention de la ville d'installation du siege central de l'entreprise
	 * @return	ville d'installation du siege central de l'entreprise
	 */
	public Town getTown() {
		return town;
	}

	/**
	 * Methode de mise a jour de la ville d'installation du siege central de l'entreprise
	 * @param town	ville d'installation du siege central de l'entreprise
	 */
	public void setTown(Town town) {
		this.town = town;
	}
}