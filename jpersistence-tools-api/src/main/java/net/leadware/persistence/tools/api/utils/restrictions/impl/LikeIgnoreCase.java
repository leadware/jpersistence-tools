package net.leadware.persistence.tools.api.utils.restrictions.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.leadware.persistence.tools.api.utils.restrictions.impl.AbstractPredicate;

/**
 * Classe representant un predicat de like avec prise en charge du toLower
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Enterprise Architect)</a>
 * @since 25 juin 2014 20:46:39
 */
public class LikeIgnoreCase extends AbstractPredicate {
	

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Nom de la propriete
	 */
	protected String property;
	
	/**
	 * Valeur de la Propriete
	 */
	protected String value;

	/**
	 * Constructeur avec initialisation des parametres
	 * @param property	Nom de la propriete
	 * @param value	Valeur de la propriete
	 */
	public LikeIgnoreCase(String property, String value) {
		this.property = property;
		this.value = value;
	}
	
	
	/* (non-Javadoc)
	 * @see net.leadware.persistence.tools.api.utils.restrictions.Predicate#generateJPAPredicate(javax.persistence.criteria.CriteriaBuilder, javax.persistence.criteria.Root)
	 */
	@Override
	public Predicate generateJPAPredicate(CriteriaBuilder criteriaBuilder, Root<?> root) {
		
		// On retourne le predicat
		return criteriaBuilder.like(criteriaBuilder.lower(this.<String>buildPropertyPath(root, property)), value.toLowerCase());
	}

}
