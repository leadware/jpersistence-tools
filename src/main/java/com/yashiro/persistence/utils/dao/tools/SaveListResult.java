/**
 * 
 */
package com.yashiro.persistence.utils.dao.tools;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Classe representant le resultat de l'appel de la methode d'enregistrement d'une liste
 * d'entites
 * @author Jean-Jacques
 * @version 1.0
 */
public class SaveListResult<T> implements Serializable {

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Liste des Entites enregistrees
	 */
	private List<T> registered = null;
	
	/**
	 * MAP des exception par Entites
	 */
	private Map<T, Throwable> exceptionPerEntity = null;

	/**
	 * Constructeur avec initialisation des parametres
	 * @param registered	Liste des Entites enregistrees
	 * @param exceptionPerEntity	MAP des exception par Entites
	 */
	public SaveListResult(List<T> registered, Map<T, Throwable> exceptionPerEntity) {
		this.registered = registered;
		this.exceptionPerEntity = exceptionPerEntity;
	}

	/**
	 * Methode d'obtention de la Liste des Entites enregistrees
	 * @return the registered
	 */
	public List<T> getRegistered() {
		return Collections.unmodifiableList(registered);
	}

	/**
	 * Methode d'obtention de la MAP des exception par Entites
	 * @return MAP des exception par Entites
	 */
	public Map<T, Throwable> getExceptionPerEntity() {
		return Collections.unmodifiableMap(exceptionPerEntity);
	}
	
	
}
