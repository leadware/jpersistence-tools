package com.yashiro.persistence.utils.test.dao.entities.base;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Classe representant la racine des classes de parametrage mappee
 * par la strategie: Table-Per-SubClass
 * @author Jean-Jacques
 * Super Classe
 * @see	{@link AbstractParameterBase}
 * @version 1.0
 */
@Entity(name = "TPSParameterBase")
@Table(name = "GENEZIS_TPS_PARAMETER")
@Inheritance(strategy = InheritanceType.JOINED)
public class TPSParameterBase extends AbstractParameterBase {
	
	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public TPSParameterBase() {
		
		// Appel Parent
		super();
	}

	/**
	 * Constructeur avec initialisation des parametres
	 * @param code	Code du parametre
	 * @param designation	Libelle du parametre
	 */
	public TPSParameterBase(String code, String designation) {

		// Appel Parent
		super(code, designation);
	}
	
	@Override
	public boolean equals(Object parameter) {
		
		// Appel Parent
		return super.equals(parameter);
	}
}