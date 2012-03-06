/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.bulksoft.persistence.utils.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bulksoft.persistence.utils.annotations.validator.DAOValidatorRule;
import com.bulksoft.persistence.utils.annotations.validator.JPADataConstraintValidators;
import com.bulksoft.persistence.utils.annotations.validator.engine.AbstractJPAConstraintValidator;
import com.bulksoft.persistence.utils.annotations.validator.engine.DefaultJPAConstraintValidator;
import com.bulksoft.persistence.utils.annotations.validator.engine.DoNothingJPAConstraintValidator;
import com.bulksoft.persistence.utils.annotations.validator.engine.IDAOValidator;
import com.bulksoft.persistence.utils.annotations.validator.engine.IJPAConstraintValidator;
import com.bulksoft.persistence.utils.annotations.validator.engine.ValidatorEngine;
import com.bulksoft.persistence.utils.annotations.validator.engine.exceptions.BaseJPersistenceUtilsException;
import com.bulksoft.persistence.utils.annotations.validator.engine.exceptions.EntityNotDeletedException;
import com.bulksoft.persistence.utils.annotations.validator.engine.exceptions.EntityNotSavedException;
import com.bulksoft.persistence.utils.annotations.validator.engine.exceptions.ValidatorInstanciationException;
import com.bulksoft.persistence.utils.dao.constant.DAOMode;
import com.bulksoft.persistence.utils.dao.tools.AliasesContainer;
import com.bulksoft.persistence.utils.dao.tools.LoaderModeContainer;
import com.bulksoft.persistence.utils.dao.tools.OrderContainer;
import com.bulksoft.persistence.utils.dao.tools.PropertyContainer;
import com.bulksoft.persistence.utils.dao.tools.RestrictionsContainer;
import com.bulksoft.persistence.utils.dao.tools.SaveListResult;

/**
 * Classe abstraite representant une base DAO generique compatible JPA et basee sur
 * les annotations de validations des entites et sur les moteur de validations
 * @author Jean-Jacques
 * @see 
 * 	<b>
 * 		<i>Annotations</i>
 * 		<ol>
 * 			<li>{@link JPADataConstraintValidators}</li>
 * 			<li>{@link DAOValidatorRule}
 * 		</ol>
 * 		<i>Engine</i>
 * 		<ol>
 * 			<li>{@link IJPAConstraintValidator}</li>
 * 			<li>{@link AbstractJPAConstraintValidator}</li>
 * 			<li>{@link ValidatorEngine}</li>
 * 			<li>{@link IDAOValidator}</li>
 * 		</ol>
 * 	</b>
 * @version 2.0
 */
@SuppressWarnings("unchecked")
public abstract class JPAGenericDAO implements IJPAGenericDAO {
	
	/**
	 * Un Logger
	 */
	private Log logger = LogFactory.getLog(JPAGenericDAO.class);
	
	@Override
	public <T> void delete(T entity) {
		
		// Une information
		logger.info("JPAGenericDAO#delete");
		
		// Une information
		logger.info("JPAGenericDAO#delete - Chargement de la classe de validation");
		
		// Validation de l'entite
		validateEntityConstraints(entity, DAOMode.DELETE);
		
		try {

			// Une information
			logger.info("JPAGenericDAO#delete - Suppression de l'Objet");
			
			// Suppression
			getEntityManager().remove(entity);
			
		} catch (Exception e) {
			
			// On relance
			throw new BaseJPersistenceUtilsException("JPAGenericDAO.delete.error", e);
		}
	}

	@Override
	public <T> List<T> deleteList(List<T> entities, boolean rollbackOnException) {

		// Une information
		logger.info("JPAGenericDAO#delete [List]");

		// Liste des non enregistrees
		List<T> notDeleted = new ArrayList<T>();
		
		// Si la liste est vide
		if(entities == null || entities.size() == 0) return notDeleted;
		
		// Une information
		logger.info("JPAGenericDAO#delete [List] - Parcours de la liste");
		
		// Parcours de la liste
		for (T entity : entities) {
			
			try {
				
				// Tentative de suppression
				delete(entity);
				
			} catch (Exception e) {

				// Une information
				logger.info("JPAGenericDAO#delete [List] - Erreur lors de la suppression [" + e.getClass().getName() + ", " + e.getMessage() + "]");
				
				// Si on sort en cas d'exception
				if(rollbackOnException) {
					
					// Une information
					logger.info("JPAGenericDAO#delete [List] - On relance l'exception [rollbackOnException: " + rollbackOnException + "]");
					
					// On relance l'exception
					throw new EntityNotDeletedException(entity, e);
				}
				
				// On ajoute dans la liste des non supprimees
				notDeleted.add(entity);
			}
		}
		
		// On retourne la liste
		return notDeleted;
	}
	
	@Override
	public <T> List<T> clean(Class<T> entityClass) {
		
		// La requete
		Query q = getEntityManager().createQuery("from " + entityClass.getCanonicalName());
		
		// Execution
		try {
			
			// Execution
			return deleteList(q.getResultList(), false);
			
		} catch (Exception e) {
			
			// On relance
			throw new BaseJPersistenceUtilsException("JPAGenericDAO.clean.error");
		}
	}
	
	@Override
	public <T> T save(T entity) {
		
		// Une information
		logger.info("JPAGenericDAO#save");
		
		// Une information
		logger.info("JPAGenericDAO#save - Chargement de la classe de validation");
		
		// Validation de l'entite
		validateEntityConstraints(entity, DAOMode.SAVE);
		
		try {
			
			// Enregistrement
			logger.info("EM Contains OBJ: [" + entity.toString() + "]: " + getEntityManager().contains(entity));
			entity = getEntityManager().merge(entity);
			
		} catch (Exception e) {
			
			// On relance
			throw new BaseJPersistenceUtilsException("JPAGenericDAO.save.error", e);
		}
		
		// On retourne l'entite enregistree
		return entity;
	}
	
	@Override
	public <T> SaveListResult<T> saveList(List<T> entities, boolean rollbackOnException) {
		
		// Une information
		logger.info("JPAGenericDAO#save[List]");
		
		// Si la liste est vide
		if(entities == null || entities.size() == 0) {
			
			// Une information
			logger.info("JPAGenericDAO#save[List] - Liste des entites a enregistrer vide");
			
			// On retourne null
			return null;
		}
		
		// Une information
		logger.info("JPAGenericDAO#save[List] - Initialisation de la MAP des Exceptions classees par Entites non enregistrees");
		
		// Liste des Entites non enregistrees
		Map<T, Throwable> notRegistered = new HashMap<T, Throwable>();
		
		// Une information
		logger.info("JPAGenericDAO#save[List] - Initialisation de la Liste des Entites enregistrees");
		
		// Liste des Entites enregistrees
		List<T> registered = new ArrayList<T>();
		
		// Une information
		logger.info("JPAGenericDAO#save[List] - Parcours de la liste des entites a enregistrer");
		
		// Parcours
		for (T entity : entities) {
			
			try {
				
				// Une information
				logger.info("JPAGenericDAO#save[List] - Tentative d'enregistrement (Methode save)");
				
				// Enregistrement
				registered.add(save(entity));
				
			} catch (Throwable e) {
				
				// Une information
				logger.info("JPAGenericDAO#save[List] - Erreur lors de l'enregistrement [" + e.getClass().getName() + ", " + e.getMessage() + "]");
				
				// Si on sort en cas d'exception
				if(rollbackOnException) {
					
					// Une information
					logger.info("JPAGenericDAO#save[List] - On relance l'exception [rollbackOnException: " + rollbackOnException + "]");
					
					// On relance l'exception
					throw new EntityNotSavedException(entity, e);
				}

				// Une information
				logger.info("JPAGenericDAO#save[List] - Enregistrement de l'erreur sur l'entite");
				
				// Ajout de l'entite non enregistree et de son exception
				notRegistered.put(entity, e);
			}
		}

		// Une information
		logger.info("JPAGenericDAO#save[List] - Initialisation du Resultat");
		
		// Initialisation du resultat
		SaveListResult<T> result = new SaveListResult<T>(registered, notRegistered);
		
		// Une information
		logger.info("JPAGenericDAO#save[List] - On retourne le resultat");
		
		// On retourne la liste des Entites enregistrees
		return result;
	}

	@Override
	public <T> T update(T entity) {
		
		// Une information
		logger.info("JPAGenericDAO#update");
		
		// Une information
		logger.info("JPAGenericDAO#update - Chargement de la classe de validation");
		
		// Validation de l'entite
		validateEntityConstraints(entity, DAOMode.UPDATE);
		
		try {
			
			// On merge et on retourne l'entite mise a jour
			return getEntityManager().merge(entity);
			
		} catch (Exception e) {
			
			// On relance
			throw new BaseJPersistenceUtilsException("JPAGenericDAO.delete.error", e);
		}
	}
	
	@Override
	public <T> T findByPrimaryKey(Class<T> entityClass, Object entityID, PropertyContainer properties) {
		
		// Une information
		logger.info("JPAGenericDAO#findByPrimaryKey");
		
		// Si la Classe a interroger est nulle
		if(entityClass == null) {
			
			// Un Log
			logger.info("JPAGenericDAO#findByPrimaryKey - Classe cible du filtre nulle");
			
			// On leve une exception
			throw new BaseJPersistenceUtilsException("JPAGenericDAO.findByPrimaryKey.class.null");
		}
		
		// Si l'ID de l'Objet est null
		if(entityID == null) {
			
			// Un Log
			logger.info("JPAGenericDAO#findByPrimaryKey - L'identifiant de l'instance cible null");
			
			// On leve une exception
			throw new BaseJPersistenceUtilsException("JPAGenericDAO.findByPrimaryKey.id.null");
		}
		
		// Un Log
		logger.info("JPAGenericDAO#findByPrimaryKey - Obtention de l'objet par son ID");

		// Un Log
		logger.info("JPAGenericDAO#findByPrimaryKey - Obtention de la session deleguee");
		
		// Obtention de la session Hibernate en cours
		Session session = (Session) getEntityManager().getDelegate();
		
		// Un Log
		logger.info("JPAGenericDAO#findByPrimaryKey - Creation de la requete Criteria");
		
		// Creation de la requete de recherche
		Criteria criteria = session.createCriteria(entityClass);
		
		// Un Log
		logger.info("JPAGenericDAO#findByPrimaryKey - Ajout de la restriction sur l'ID");
				
		// Ajout de la restriction sur l'ID
		criteria.add(Restrictions.idEq(entityID));
		
		// Un Log
		logger.info("JPAGenericDAO#findByPrimaryKey - Ajout des proprietes a charger");
		
		// Ajout des proprietes a charger
		addProperties(criteria, properties);
		
		// Un Log
		logger.info("JPAGenericDAO#findByPrimaryKey - Execution de la requete");
		
		// Execution de la requete
		T result = (T) criteria.uniqueResult();

		// Un Log
		logger.info("JPAGenericDAO#findByPrimaryKey - On retourne le resultat");
		
		// On retourne le resultat
		return result;
	}
	
	@Override
	public <T> List<T> filter(Class<T> entityClass, AliasesContainer alias,
			RestrictionsContainer restrictions, OrderContainer orders,
			LoaderModeContainer loaderModes, int firstResult, int maxResult) {
		
		// Une information
		logger.info("JPAGenericDAO#filter");
		
		// Si la Classe a interroger est nulle
		if(entityClass == null) {
			
			// Un Log
			logger.info("JPAGenericDAO#filter - Classe cible du filtre nulle");
			
			// On leve une exception
			throw new BaseJPersistenceUtilsException("JPAGenericDAO.filter.class.null");
		}
		
		// Un Log
		logger.info("JPAGenericDAO#filter - Obtention de la session deleguee");
		
		// Obtention de la session Hibernate en cours
		Session session = (Session) getEntityManager().getDelegate();
		
		// Un Log
		logger.info("JPAGenericDAO#filter - Creation de la requete Criteria");
		
		// Creation de la requete de recherche
		Criteria criteria = session.createCriteria(entityClass);
		
		// Liste resultat
		List<T> result = new ArrayList<T>();

		// Traitement de l'index du premier resultat
		
		// Un Log
		logger.info("JPAGenericDAO#filter - Traitement de l'index du premier resultat");
		
		// Si l'index du premier element est < 0
		if(firstResult < 0) criteria.setFirstResult(0);
		else criteria.setFirstResult(firstResult);
		
		// Traitement du nombre max de resultat
		
		// Un Log
		logger.info("JPAGenericDAO#filter - Traitement du nombre max de resultat");
		
		// Si le nombre max d'element est <= 0
		if(maxResult > 0) criteria.setMaxResults(maxResult);
		
		// Un Log
		logger.info("JPAGenericDAO#filter - Traitement des Alias");
		
		// Traitement des Alias
		addAliases(criteria, alias);
		
		// Un Log
		logger.info("JPAGenericDAO#filter - Traitement des Restrictions");
		
		// Traitement des Restrictions
		addRestrictions(criteria, restrictions);		
		
		// Un Log
		logger.info("JPAGenericDAO#filter - Traitements des Ordres de tri");
		
		// Traitements des Ordres de tri
		addOrders(criteria, orders);
		
		// Un Log
		logger.info("JPAGenericDAO#filter - Traitement des modes de chargements");
		
		// Traitement des modes de chargements
		addLoaderMode(criteria, loaderModes);
		
		// Un Log
		logger.info("JPAGenericDAO#filter - Exceution de la requete");
		
		// Exceution de la requete
		result = criteria.list();

		// Un Log
		logger.info("JPAGenericDAO#filter - On retourne la liste");
		
		// On retourne la liste
		return result;
	}
	
	/**
	 * Methode d'extraction du Validateur de contraintes
	 * @param entity	Entite a valider
	 * @return	Classe du Validateur
	 */
	private Class<? extends IJPAConstraintValidator> extractConstraintValidator(Object entity, DAOMode mode) {
		
		// Une information
		logger.info("JPAGenericDAO#extractConstraintValidator");
		
		// Obtention du validateur par defaut
		Class<? extends IJPAConstraintValidator> defaultValidator = buildDefaultValidatorClass(mode);
		
		// Si l'Objet est null
		if(entity == null) {
			
			// Un log
			logger.info("JPAGenericDAO#extractConstraintValidator - Entite e valider nulle");
						
			// On retourne le validateur par Defaut
			return defaultValidator;
		}
		
		// Obtention de la Classe de l'entite
		Class<?> entityClass = entity.getClass();
		
		// Recherche de l'annotation de validation
		JPADataConstraintValidators validatorAnnotation = entityClass.getAnnotation(JPADataConstraintValidators.class);
		
		// Si elle ne possede pas cette annotation
		if(validatorAnnotation == null) {
			
			// Un log
			logger.info("JPAGenericDAO#extractConstraintValidator - Entite e valider n'est pas annotee");
						
			// On retourne le validateur par Defaut
			return defaultValidator;
		}
		
		// Obtention du Validateur pour le mode donne
		Class<? extends IJPAConstraintValidator> validatorClass = validatorAnnotation.removeValidator();
		
		// Si on est en mode SAVE
		if(mode.equals(DAOMode.SAVE)) validatorClass = validatorAnnotation.saveValidator();
		
		// Sinon
		else if(mode.equals(DAOMode.UPDATE)) validatorClass = validatorAnnotation.updateValidator();
		
		// Si le validateur est null
		if(validatorClass == null) validatorClass = defaultValidator;
		
		// Un log
		logger.info("JPAGenericDAO#extractConstraintValidator - Classe de validation: " +validatorClass.getName());
		
		// On retourne la classe du Validateur
		return validatorClass;
	}
	
	/**
	 * Methode de validation des contraintes sur l'entite
	 * @param entity	Entite a valider
	 */
	private void validateEntityConstraints(Object entity, DAOMode mode) {

		// Une information
		logger.info("JPAGenericDAO#validateEntityConstraints");
		
		// Obtention de la classe de Validation
		Class<? extends IJPAConstraintValidator> validatorClass = extractConstraintValidator(entity, mode);
		
		// On instancie le validateur
		IJPAConstraintValidator validator = null;
		
		try {
			
			// Une information
			logger.info("JPAGenericDAO#validateEntityConstraints - Instanciation du validateur");
			
			// On instancie le validateur
			validator = validatorClass.newInstance();
			
		} catch (Throwable e) {
			
			// Une information
			logger.info("JPAGenericDAO#validateEntityConstraints - Erreur lors de l'instanciation de la classe de Validation [" + e.getClass().getName() + ", " + e.getMessage() + "]");
			
			// On relance l'exception
			throw new ValidatorInstanciationException(e);
		}
		
		// Initialisation du Validateur
		validator.init(getEntityManager(), entity);
		
		// Validation des contraintes d'integrites
		validator.validateIntegrityConstraint();
		
		// Validation des contraintes referentielles
		validator.validateReferentialConstraint();
	}
	
	/**
	 * Methode de construction de la classe de validation par defaut pour le mode DAO donne
	 * @param mode	Mode DAO
	 * @return	Classe du validateur par defaut
	 */
	private Class<? extends IJPAConstraintValidator> buildDefaultValidatorClass(DAOMode mode) {
		
		// Si le mode est null
		if(mode == null || mode.equals(DAOMode.DELETE)) return DoNothingJPAConstraintValidator.class;
		
		// On retourne le default
		return DefaultJPAConstraintValidator.class;
	}

	/**
	 * Methode d'ajout des Alias a la requete de recherche
	 * @param criteria	Requete de recherche
	 * @param container	Conteneur d'alias
	 */
	private void addAliases(Criteria criteria, AliasesContainer container) {
		
		// Si le conteneur est vide
		if(container == null || container.size() == 0) return;
		
		// Obtention du conteneur
		Map<String, String> lContainer = container.getAliases();
		
		// Obtention de l'esemble des cles
		Set<String> keys = lContainer.keySet();
		
		// Parcours du conteneur
		for (String key : keys) {
			
			// Ajout de l'alias
			criteria.createAlias(key, lContainer.get(key));
		}
	}

	/**
	 * Methode d'ajout des Restrictions a la requete de recherche
	 * @param criteria	Requete de recherche
	 * @param container	Conteneur de restrictions
	 */
	private void addRestrictions(Criteria criteria, RestrictionsContainer container) {
		
		// Si le conteneur est vide
		if(container == null || container.size() == 0) return;
		
		// Obtention du conteneur
		List<Criterion> lContainer = container.getRestrictions();
		
		// Parcours du conteneur
		for (Criterion restriction : lContainer) {
			
			// Ajout de la restriction
			criteria.add(restriction);
		}
	}
	
	/**
	 * Methode d'ajout des Ordres de tri a la requete de recherche
	 * @param criteria	Requete de recherche
	 * @param container	Conteneur d'ordre de tri
	 */
	private void addOrders(Criteria criteria, OrderContainer container) {
		
		// Si le conteneur est vide
		if(container == null || container.size() == 0) return;
		
		// Obtention du conteneur
		List<Order> lContainer = container.getOrders();
		
		// Parcours du conteneur
		for (Order order : lContainer) {
			
			// Ajout de l'ordre de tri
			criteria.addOrder(order);
		}
	}

	/**
	 * Methode d'ajout des Mode de chargement a la requete de recherche
	 * @param criteria	Requete de recherche
	 * @param container	Conteneur de mode de chargement
	 */
	private void addLoaderMode(Criteria criteria, LoaderModeContainer container) {
		
		// Si le conteneur est vide
		if(container == null || container.size() == 0) return;
		
		// Obtention du conteneur
		Map<String, FetchMode> lContainer = container.getLoaderMode();
		
		// Obtention de l'esemble des cles
		Set<String> keys = lContainer.keySet();
		
		// Parcours du conteneur
		for (String key : keys) {
			
			// Ajout de l'alias
			criteria.setFetchMode(key, lContainer.get(key));
		}
	}
	
	/**
	 * Methode d'ajout des Proprietes a charger a la requete de recherche
	 * @param criteria	Requete de recherche
	 * @param container	Conteneur de proprietes
	 */
	private void addProperties(Criteria criteria, PropertyContainer container) {
		
		// Si le conteneur est vide
		if(container == null || container.size() == 0) return;
		
		// Obtention du conteneur
		Set<String> lContainer = container.getProperties();
		
		// Parcours du conteneur
		for (String property : lContainer) {
			
			// Ajout de l'ordre de tri
			criteria.setFetchMode(property, FetchMode.SELECT);
		}
	}
}
