package com.yashiro.persistence.utils.test.dao.entities.base;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Classe representant la racine des classes conforme a la classe de parametrage mere
 * Elle definie un mapping de type: Table-Per-Class-Hierarchy
 * @author NKOU NKOU Joseph Junior
 * @author Jean-Jacques
 * Super Classe
 * @see	{@link AbstractParameterBase}
 * @version 1.0
 */
@Entity(name = "TPHParameterBase")
@Table(name = "GENEZIS_TPH_PARAMETER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PARAMETER_DISCRIMINATOR", discriminatorType = DiscriminatorType.STRING)
public class TPHParameterBase extends AbstractParameterBase {

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public TPHParameterBase() {
		
		// Appel Parent
		super();
	}
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param code	Code du parametre
	 * @param designation	Libelle du parametre
	 */
	public TPHParameterBase(String code, String designation) {
		
		// Appel parent
		super(code, designation);
	}
}
