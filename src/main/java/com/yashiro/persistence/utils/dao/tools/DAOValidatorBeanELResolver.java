/**
 * 
 */
package com.yashiro.persistence.utils.dao.tools;

import javax.el.BeanELResolver;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.PropertyNotFoundException;

/**
 * Classe representant un ELResolver etandant le type BeanELResolver (Pour la resolution des proprietes
 * @author Jean-Jacques
 * @version 2.0
 */
public class DAOValidatorBeanELResolver extends BeanELResolver {
	
	/**
	 * Objet de base (Initialise par l'instance de l'objet a valider)
	 */
	private Object base = null;
	
	/**
	 * Constructeur avec initialisation de l'objet de base initial
	 * @param base	Objet de base (Initialise par l'instance de l'objet a valider)
	 */
	public DAOValidatorBeanELResolver(Object base) {
		this.base = base;
	}
	
	@Override
	public Object getValue(ELContext context, Object base, Object property) throws NullPointerException, PropertyNotFoundException, ELException {
		
		// Si l'Objet de base est null
		if(base != null) this.base = base;
		
		// On obtient la Valeur
		Object value = super.getValue(context, this.base, property);
		
		// On retourne le resultat
		return value;
	}
	
	/**
	 * Methode de mise a jour de l'Objet de base (Initialise par l'instance de l'objet a valider)
	 * @param base Objet de base (Initialise par l'instance de l'objet a valider)
	 */
	public void setBase(Object base) {
		this.base = base;
	}
}