/**
 * 
 */
package com.yashiro.persistence.utils.dao.entities.idclass.generators;

import org.hibernate.Session;
import org.hibernate.dialect.Dialect;

/**
 * Interface de generation du Prefix de la cle
 * @author Jean-Jacques
 * @version 1.0
 */
public interface IPrefixGenerator {
	
	/**
	 * Methode d'initialisation du generateur de prefix
	 * @param dialect	Dialect du SGBD cible
	 * @param session	Session en cours
	 */
	public void initialize(Dialect dialect, Session session);
	
	/**
	 * Methode de generation du prefix de la cle
	 * @param entity	Entite pour laquelle on veut generer le prefix
	 * @return	Prefix genere
	 */
	public Object generate(Object entity);
}
