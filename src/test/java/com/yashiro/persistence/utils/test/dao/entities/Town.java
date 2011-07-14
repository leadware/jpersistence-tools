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
 * Classe representant une ville attachee a un Pays
 * @author Jean-Jacques
 * @see {@link TPSParameterBase}
 * @version 1.0
 */
@SizeDAOValidators({
	@SizeDAOValidator(mode = DAOMode.SAVE,   expr = "from Town t where (t.code = ${code})", max = 0, message = "Town.save.code.exist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from Town t where (t.id = ${id})", min = 1, message = "Town.update.id.notexist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from Town t where (t.id != ${id}) and (t.code = ${code})", max = 0, message = "Town.update.code.notunique")
})
@Entity(name = "Town")
@Table(name = "GENEZIS_TOWN")
public class Town extends TPSParameterBase {

    /**
	 * ID Genere Par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Region parente de la ville
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGION_ID", nullable = false)
	@NotNull(message = "Town.region.null")
	private Region  region;
	
	/**
	 * Constructeur par defaut
	 */
	public Town() {
		
		// Appel Parent
		super();
	}
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param code	Code de la region
	 * @param designation	Nom de la region
	 * @param region	Region parente de la ville
	 */
	public Town(String code, String designation, Region region) {
		
		// Appel parent
		super(code, designation);
		
		// Initialisation de la region
		this.region = region;
	}
	
	/**
	 * Methode d'obtention de la Region parente de la ville
	 * @return	Region parente de la ville
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * Methode de mise a jour de la Region parente de la ville
	 * @param region parente de la ville
	 */
	public void setRegion(Region region) {
		this.region = region;
	}
}
