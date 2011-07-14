package com.yashiro.persistence.utils.dao.entities.idclass.generators;

import org.hibernate.Session;
import org.hibernate.dialect.Dialect;

/**
 * Classe d'implementation par defaut du generateur de prefix
 * @author Jean-Jacques
 * @version 1.0
 */
public class DefaultPrefixGenerator implements IPrefixGenerator {

	/* (non-Javadoc)
	 * @see com.yashiro.persistence.utils.dao.entities.idclass.generators.IPrefixGenerator#generate(java.lang.Object)
	 */
	@Override
	public Object generate(Object entity) {
		
		// On genere un prefix vide
		return "";
	}

	/* (non-Javadoc)
	 * @see com.yashiro.persistence.utils.dao.entities.idclass.generators.IPrefixGenerator#initialize(org.hibernate.dialect.Dialect, org.hibernate.Session)
	 */
	@Override
	public void initialize(Dialect dialect, Session session) {}

}
