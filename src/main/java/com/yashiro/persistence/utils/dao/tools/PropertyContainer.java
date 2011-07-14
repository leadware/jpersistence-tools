/**
 * 
 */
package com.yashiro.persistence.utils.dao.tools;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe representant un conteneur de proprietes
 * @author Jean-Jacques
 * @version 1.0
 */
public class PropertyContainer implements Serializable {

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Ensemble des proprietes
	 */
	private Set<String> properties = new HashSet<String>();
	
	/**
	 * Obtention de l'instance
	 * @return	Instance de travail
	 */
	public static synchronized final PropertyContainer getInstance() {
		
		// On retourne l'instance
		return new PropertyContainer();
	}
	
	/**
	 * Methode d'ajour d'une propriete
	 * @param property	Propriete a ajouter
	 * @return	Conteneur de proprietes
	 */
	public PropertyContainer add(String property) {
		
		// Si la propriete n'est pas nulle
		if(property != null && property.trim().length() > 0) properties.add(property);
		
		// On retourne le conteneur
		return this;
	}
	
	/**
	 * Methode d'obtention de l'Ensemble des proprietes
	 * @return Ensemble des proprietes
	 */
	public Set<String> getProperties() {
		return Collections.unmodifiableSet(properties);
	}

	/**
	 * Methode d'obtention de la taille du conteneur
	 * @return	Taille du conteneur
	 */
	public int size() {
		
		// On retourne la taille de la collection
		return this.properties.size();
	}
	
	/**
	 * Methode de vidage du conteneur
	 */
	public void clear() {
		
		// Si la collection est non nulle
		if(properties != null) properties.clear();
	}
}
