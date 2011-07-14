package com.yashiro.persistence.utils.test.dao.entities.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import com.yashiro.persistence.utils.annotations.validator.Length;


/**
 * Classe de base des parametrages
 * @author Jean-Jacques
 * @version 1.0
 */
@MappedSuperclass
public class AbstractParameterBase implements Serializable, Comparable<AbstractParameterBase> {
	
	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constante definissant la taille Max des codes de parametrage
	 */
	public static final int CODE_MAX_LENGTH = 25;
	
	/**
	 * Constante definissant la taille Min des codes de parametrage
	 */
	public static final int CODE_MIN_LENGTH = 1;
	
	/**
	 * Constante definissant la taille Max du libelle du parametre
	 */
	public static final int DESIGNATION_MAX_LENGTH = 300;
	
	/**
	 * Identifiant artificiel
	 */
	@Id
	@SequenceGenerator(name="Seq_AbstractParameterBase", sequenceName="SEQ_ABSTRACT_PARAMETER_BASE", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Seq_AbstractParameterBase")
	@Column(name = "ID")
	private Long id;
	
	/**
	 * Code du parametre
	 */
	@Column(name = "CODE", nullable = false, length = CODE_MAX_LENGTH)
	@Length(min = CODE_MIN_LENGTH, max = CODE_MAX_LENGTH, message = "AbstractParameterBase.code.length")
	protected String code;
	
	/**
	 * Libelle du parametre
	 */
	@Column(name = "DESIGNATION", nullable = false, length = DESIGNATION_MAX_LENGTH)
	@Length(min = 1, max = DESIGNATION_MAX_LENGTH, message = "AbstractParameterBase.designation.length")
	protected String designation;
	
	/**
	 * Version de l'enregistrement
	 */
	@Version
	@Column(name = "PARAMETER_VERSION")
	private Integer version = 1;
	
	/**
	 * Constructeur par defaut
	 */
	public AbstractParameterBase() {}

	/**
	 * Constructeur avec initialisation des parametres
	 * @param code	Code du type de piece
	 * @param designation	Libelle du type de piece
	 */
	public AbstractParameterBase(String code, String designation) {
		this.code = code;
		this.designation = designation;
		if(this.code != null) this.code = this.code.trim().toUpperCase();
		if(this.designation != null) this.designation = this.designation.trim().toUpperCase();
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
	 * Methode d'obtention de l'Identifiant artificiel de l'agence
	 * @return Identifiant artificiel de l'agence
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Methode de mise a jour de l'Identifiant artificiel de l'agence
	 * @param id Identifiant artificiel de l'agence
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Methode d'obtention du Code du parametre
	 * @return Code du parametre
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Methode de mise a jour du Code du parametre
	 * @param code Code du parametre
	 */
	public void setCode(String code) {
		this.code = code;
		if(this.code != null) this.code = this.code.trim().toUpperCase();
	}

	/**
	 * Methode d'obtention du Libelle du parametre
	 * @return Libelle du parametre
	 */
	public String getDesignation() {
		return designation;
	}
	
	/**
	 * Methode de mise a jour du Libelle du parametre
	 * @param designation Libelle du parametre
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
		if(this.designation != null) this.designation = this.designation.trim().toUpperCase();
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
		if(!(parameter instanceof AbstractParameterBase)) return false;
		
		// On caste
		AbstractParameterBase abstractParameter = (AbstractParameterBase) parameter;
		
		// Si le Parametre a un ID
		if(abstractParameter.id != null) {
			
			// Si l'Objet courant n'a pas d'ID
			if(id == null) return false;
			
			// On retourne la comparaison des IDs
			return abstractParameter.id.equals(id);
			
		} else {
			
			// Si l'Objet en cours a un Id
			if(id != null) return false;
			
		}
		
		// Si le code du parametre est vide
		if(abstractParameter.code == null || abstractParameter.code.trim().length() == 0) return false;
		
		// Si le code du parametre en cours est vide
		if(code == null || code.trim().length() == 0) return false;
		
		// On retourne la comparaison des codes
		return code.equals(abstractParameter.code);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(AbstractParameterBase abstractParameter) {
		
		// Si le parametre est null
		if(abstractParameter == null) return -1;
		
		// Si le code du parametre est vide
		if(abstractParameter.code == null || abstractParameter.code.trim().length() == 0) return -1;
		
		// Si le code du parametre en cours est vide
		if(code == null || code.trim().length() == 0) return 1;
		
		// On retourne la comparaison des codes
		return code.compareTo(abstractParameter.code);
	}
	
	@Override
	public String toString() {
		
		// Le Buffer
		StringBuffer buffer = new StringBuffer();
		
		// ID
		buffer.append("ID: ").append(id);

		// Separateur
		buffer.append(", ");
		
		// Code
		buffer.append("Code: ").append(code);
		
		// Separateur
		buffer.append(", ");
		
		// Designation
		buffer.append("Designation: ").append(designation);
		
		// On retourne le Buffer
		return buffer.toString();
	}
}
