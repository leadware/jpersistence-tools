package com.yashiro.persistence.utils.test.dao.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import com.yashiro.persistence.utils.annotations.validator.SizeDAOValidator;
import com.yashiro.persistence.utils.annotations.validator.SizeDAOValidators;
import com.yashiro.persistence.utils.dao.constant.DAOMode;
import com.yashiro.persistence.utils.test.dao.entities.base.TPHParameterBase;

/**
 * Classe representant un Pays
 * @author Jean-Jacques
 * @see {@link TPHParameterBase}
 * @version 1.0
 */
@SizeDAOValidators({
	@SizeDAOValidator(mode = DAOMode.SAVE,   expr = "from Country c where (c.code = ${code})", max = 0, message = "Country.save.code.exist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from Country c where (c.id = ${id})", min = 1, message = "Country.update.id.notexist"),
	@SizeDAOValidator(mode = DAOMode.UPDATE, expr = "from Country c where (c.id != ${id}) and (c.code = ${code})", max = 0, message = "Country.update.code.notunique")
})
@Entity(name = "Country")
@DiscriminatorValue(value = "Country")
public class Country extends TPHParameterBase {
	
	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public Country() {
		
		// Appel Parent
		super();
	}
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param code	Code du pays
	 * @param designation	Libelle du pays
	 */
	public Country(String code, String designation) {
		
		// Appel parent
		super(code, designation);
	}
}
