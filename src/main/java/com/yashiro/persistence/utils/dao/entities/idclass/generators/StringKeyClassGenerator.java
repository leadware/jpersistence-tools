/**
 * 
 */
package com.yashiro.persistence.utils.dao.entities.idclass.generators;

import java.io.Serializable;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;
import org.hibernate.util.PropertiesHelper;

import com.yashiro.persistence.utils.dao.entities.idclass.generators.exceptions.UnknownGeneratorErrorException;

/**
 * Classe de generation d'un ID de type String avec Prefixe dynamique
 * @author Jean-Jacques
 * @version 1.0
 */
public class StringKeyClassGenerator extends LongKeyClassGenerator {
	
	/**
	 *  La classe de generation du prefixe
	 */
	private String prefixGeneratorClassName;
	
	/**
	 *  Un Logger
	 */
	private static Log logger = LogFactory.getLog(StringKeyClassGenerator.class);
	
	/**
	 *  Generateur de prefixe
	 */
	private IPrefixGenerator prefixGenerator = null;
	
	/**
	 *  Le Prefix
	 */
	String prefix = "";

	/**
	 *  Nombre de caractres  prendre sur le nom de la classe
	 */
	private Integer characterNumber;
	
	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		
		// Generation Parente
		Long longID = (Long) super.generate(session, object);
		
		// Un Log
		logger.debug("StringKeyClassGenerator#generate");
		
		// Nom Canonique du subject
		String canonicalSubject = object.getClass().getSimpleName();
		
		// Chargement du PrefixGenerator
		prefixGenerator = loadPrefixGenerator();
		
		// La cl
		String key = "";
		
		try {
			
			// Une Session
			Session s = session.getFactory().getCurrentSession();
			
			// Initialisation du generateur de prefixe
			prefixGenerator.initialize(dialect, s);
			
			// Generation du Prefixe
			String prefixObject = prefixGenerator.generate(object).toString();
			
			// Si le nombre de caractres  prendre sur le nom de la classe
			// est suprieure  la taille du nom : on prend tout le nom
			if(canonicalSubject.length() < characterNumber) characterNumber = canonicalSubject.length();
						
			// Requte de mise  jour
			lastValueUpdateRequest = "update";
			
			// Si l'Objet Prefixe est null
			if(prefixObject == null) prefix = "";
			
			// Sinon
			else prefix = prefixObject.toString();
			
			// Mise en place du Prfix
			if(prefix == null || prefix.trim().length() == 0) prefix = canonicalSubject.toUpperCase().substring(0, characterNumber);
			
			// Mise en place de la cl
			key = prefix + longID.toString();
			
			// Un Log pour la cl
			logger.debug("StringKeyClassGenerator#generate - Generated Key : " + key);
			
			// On force la synchro
			s.flush();
			
			// On enregistre cette valeur dans la Map
			generatedValues.put(subject, longID);
			
			// On retourne la cl
			return key;
			
		} catch (Exception e) {
			
			// On affiche l'erreur
			e.printStackTrace();
			
			// On lance une erreur inconnue
			throw new UnknownGeneratorErrorException(e);
		}
	}
	
	@Override
	public void configure(Type type, Properties properties, Dialect dialect) throws MappingException {
		
		// Initialisation Parente
		super.configure(type, properties, dialect);
		
		// Rcupration du nombre de caractre  prendre sur le nom de la classe
		characterNumber = PropertiesHelper.getInteger(SXKeyClassGenerationStructure.GENERATION_NAME_SIZE_COLUMN_NAME, properties);
		
		// Si ce nombre est null : On initialise 
		if(characterNumber == null) characterNumber = new Integer(0);
		
		// Rcupration du Prfix
		prefixGeneratorClassName = PropertiesHelper.getString(SXKeyClassGenerationStructure.PREFIX_GENERATOR_CLASS_NAME, properties, "").trim();
	}
	
	/**
	 * Methode de chargement du Generateur de prefixe
	 * @return	Generateur de prefixe
	 */
	private IPrefixGenerator loadPrefixGenerator() {
		
		// Si le nom de la classe de generation du prefixe est vide
		if(prefixGeneratorClassName == null || prefixGeneratorClassName.trim().length() == 0) return new DefaultPrefixGenerator();
		
		try {
			
			// On charge la classe
			Class<?> generatorClass = Class.forName(prefixGeneratorClassName);
			
			// Si la classe n'implemente pas l'Interface
			if(!IPrefixGenerator.class.isAssignableFrom(generatorClass)) return new DefaultPrefixGenerator();
			
			// On retourne l'instance
			return (IPrefixGenerator) generatorClass.newInstance();
			
		} catch (Throwable e) {
			
			// On retourne le generateur par defaut
			return new DefaultPrefixGenerator();
		}
	}
}
