/**
 * 
 */
package com.yashiro.persistence.utils.test.dao.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.yashiro.persistence.utils.test.dao.entities.base.TPSParameterBase;
import org.hibernate.validator.NotNull;

import com.yashiro.persistence.utils.annotations.validator.SizeDAOValidator;
import com.yashiro.persistence.utils.annotations.validator.SizeDAOValidators;
import com.yashiro.persistence.utils.dao.constant.DAOMode;

/**
 * Classe representant une region du systeme
 * @author Jean-Jacques
 * @see {@link TPSParameterBase}
 * @version 1.0
 */
@SizeDAOValidators({
	@SizeDAOValidator(mode = DAOMode.SAVE,   expr = "from Region r where (r.code = ${code})", max = 0, message = "Region.save.code.exist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from Region r where (r.id = ${id})", min = 1, message = "Region.update.id.notexist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from Region r where (r.id != ${id}) and (r.code = ${code})", max = 0, message = "Region.update.code.notunique")
})
@Entity(name = "Region")
@Table(name = "GENEZIS_REGION")
public class Region extends TPSParameterBase {
	
    /**
	 * ID Genere Par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Pays parent de la region
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID", nullable = false)
	@NotNull(message = "Region.country.null")
	private Country  country;
	
	/**
	 * Constructeur par defaut
	 */
	public Region() {
		
		// Appel Parent
		super();
	}
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param code	Code de la region
	 * @param designation	Nom de la region
	 * @param country	Pays parent de la region
	 */
	public Region(String code, String designation, Country country) {
		
		// Appel parent
		super(code, designation);
		
		// Initialisation du pays
		this.country = country;
	}
	
	/**
	 * Methode d'obtention du Pays parent de la region
	 * @return	Pays parent de la region
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * Methode de mise a jour du Pays parent de la region
	 * @param country parent de la region
	 */
	public void setCountry(Country country) {
		this.country = country;
	}
}
