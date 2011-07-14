/**
 * 
 */
package com.yashiro.persistence.utils.dao.tools;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.VariableMapper;

/**
 * Classe representant le contexte d'evaluation d'une Expression
 * @author Jean-Jacques
 * @version 2.0
 */
public class DAOValidatorELContext extends ELContext {
	
	/**
	 * Resolver de type Bean
	 */
	private ELResolver resolver;
	
	/* (non-Javadoc)
	 * @see javax.el.ELContext#getELResolver()
	 */
	@Override
	public ELResolver getELResolver() {
		
		// On retourne le resolver
		return resolver;
	}
	
	/**
	 * Methode de modification du Resolver
	 * @param resolver	Resolver de type Bean
	 */
	public void setELResolver(ELResolver resolver) {
		
		// On positionne le Resolver
		this.resolver = resolver;
	}

	/* (non-Javadoc)
	 * @see javax.el.ELContext#getFunctionMapper()
	 */
	@Override
	public FunctionMapper getFunctionMapper() {
		
		// On retourne null
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.el.ELContext#getVariableMapper()
	 */
	@Override
	public VariableMapper getVariableMapper() {
		
		// On retourne null
		return null;
	}
}
