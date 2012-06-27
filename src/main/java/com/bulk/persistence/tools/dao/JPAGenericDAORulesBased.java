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
package com.bulk.persistence.tools.dao;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.OptimisticLockException;
import javax.persistence.Query;
import com.bulk.persistence.tools.exceptions.EntityNotDeletedException;
import com.bulk.persistence.tools.exceptions.EntityNotSavedException;
import com.bulk.persistence.tools.exceptions.JPersistenceToolsException;
import com.bulk.persistence.tools.exceptions.NullEntityException;
import com.bulk.persistence.tools.exceptions.ValidatorInstanciationException;
import com.bulk.persistence.tools.validator.IDAOValidator;
import com.bulk.persistence.tools.validator.annotations.DAOConstraint;
import com.bulk.persistence.tools.validator.annotations.IdentityDAOValidator;
import com.bulk.persistence.tools.validator.annotations.IntegrityConstraintDAOValidator;
import com.bulk.persistence.tools.validator.engine.JSR303ValidatorEngine;
import com.bulksoft.persistence.utils.dao.constant.DAOMode;
import com.bulksoft.persistence.utils.dao.constant.DAOValidatorEvaluationTime;
import com.bulksoft.persistence.utils.dao.tools.AliasesContainer;
import com.bulksoft.persistence.utils.dao.tools.DAOValidatorHelper;
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
 * 			<li>{@link DAOConstraint}
 * 		</ol>
 * 		<i>Engine</i>
 * 		<ol>
 * 			<li>{@link JSR303ValidatorEngine}</li>
 * 			<li>{@link IDAOValidator}</li>
 * 		</ol>
 * 	</b>
 * @version 2.0
 */
@SuppressWarnings("unchecked")
public abstract class JPAGenericDAORulesBased implements IJPAGenericDAO {
	
	@Override
	public <T> void delete(T entity) {
		
		// Si l'entite est nulle
		if(entity == null) throw new NullEntityException();
		
		// Validation de l'entite
		validateEntityConstraints(entity, DAOMode.DELETE, DAOValidatorEvaluationTime.PRE_CONDITION);
		
		try {
			
			// On reattache
			entity = getEntityManager().merge(entity);
			
			// Suppression
			getEntityManager().remove(entity);
			
		} catch (Exception e) {
			
			// On relance
			throw new JPersistenceToolsException("JPAGenericDAORulesBased.delete.error", e);
		}
		
		// Validation de l'entite
		validateEntityConstraints(entity, DAOMode.DELETE, DAOValidatorEvaluationTime.POST_CONDITION);
	}

	@Override
	public <T> List<T> deleteList(List<T> entities, boolean rollbackOnException) {
		
		// Liste des non enregistrees
		List<T> notDeleted = new ArrayList<T>();
		
		// Si la liste est vide
		if(entities == null || entities.size() == 0) return notDeleted;
		
		// Parcours de la liste
		for (T entity : entities) {
			
			try {
				
				// Tentative de suppression
				delete(entity);
				
			} catch (Exception e) {
				
				// Si on sort en cas d'exception
				if(rollbackOnException) {
					
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
			throw new JPersistenceToolsException("JPAGenericDAORulesBased.clean.error");
		}
	}
	
	@Override
	public <T> T save(T entity) {
		
		// Si l'entite est nulle
		if(entity == null) throw new NullEntityException();
		
		// Validation de l'entite
		validateEntityConstraints(entity, DAOMode.SAVE, DAOValidatorEvaluationTime.PRE_CONDITION);
		
		try {
			
			// Enregistrement
			entity = getEntityManager().merge(entity);
			
		} catch (Exception e) {
			
			// On relance
			throw new JPersistenceToolsException("JPAGenericDAORulesBased.save.error", e);
		}
		
		// Validation de l'entite
		validateEntityConstraints(entity, DAOMode.SAVE, DAOValidatorEvaluationTime.POST_CONDITION);
		
		// On retourne l'entite enregistree
		return entity;
	}
	
	@Override
	public <T> SaveListResult<T> saveList(List<T> entities, boolean rollbackOnException) {
		
		// Si la liste est vide
		if(entities == null || entities.size() == 0) {
			
			// On retourne null
			return null;
		}
		
		// Liste des Entites non enregistrees
		Map<T, Throwable> notRegistered = new HashMap<T, Throwable>();
		
		// Liste des Entites enregistrees
		List<T> registered = new ArrayList<T>();
		
		// Parcours
		for (T entity : entities) {
			
			try {
				
				// Enregistrement
				registered.add(save(entity));
				
			} catch (Throwable e) {
				
				// Si on sort en cas d'exception
				if(rollbackOnException) {
					
					// On relance l'exception
					throw new EntityNotSavedException(entity, e);
				}
				
				// Ajout de l'entite non enregistree et de son exception
				notRegistered.put(entity, e);
			}
		}

		// Initialisation du resultat
		SaveListResult<T> result = new SaveListResult<T>(registered, notRegistered);
		
		// On retourne la liste des Entites enregistrees
		return result;
	}

	@Override
	public <T> T update(T entity) {
		
		// Si l'entite est nulle
		if(entity == null) throw new NullEntityException();
		
		// Validation de l'entite
		validateEntityConstraints(entity, DAOMode.UPDATE, DAOValidatorEvaluationTime.PRE_CONDITION);
		
		// Le resultat
		T result = null;
		
		try {
			
			// On obtient le resultat
			result = getEntityManager().merge(entity);
			
		} catch (OptimisticLockException e) {
			
			// On relance
			throw new JPersistenceToolsException("JPAGenericDAORulesBased.update.optimisticklockexception", e);
			
		} catch (Exception e) {
			
			// On relance
			throw new JPersistenceToolsException("JPAGenericDAORulesBased.update.error", e);
		}

		// Validation de l'entite
		validateEntityConstraints(entity, DAOMode.UPDATE, DAOValidatorEvaluationTime.POST_CONDITION);
		
		// On retourne le resultat
		return result;
	}
	
	@Override
	public <T> T findByPrimaryKey(Class<T> entityClass, Object entityID, PropertyContainer properties) {
		
		// Si la Classe a interroger est nulle
		if(entityClass == null) {
			
			// On leve une exception
			throw new JPersistenceToolsException("JPAGenericDAORulesBased.findByPrimaryKey.class.null");
		}
		
		// Si l'ID de l'Objet est null
		if(entityID == null) {
			
			// On leve une exception
			throw new JPersistenceToolsException("JPAGenericDAORulesBased.findByPrimaryKey.id.null");
		}
		
		// Obtention de la session Hibernate en cours
		Session session = (Session) getEntityManager().getDelegate();
		
		// Creation de la requete de recherche
		Criteria criteria = session.createCriteria(entityClass);
				
		// Ajout de la restriction sur l'ID
		criteria.add(Restrictions.idEq(entityID));
		
		// Ajout des proprietes a charger
		addProperties(criteria, properties);
		
		// Execution de la requete
		T result = (T) criteria.uniqueResult();
		
		// On retourne le resultat
		return result;
	}
	
	@Override
	public <T> List<T> filter(Class<T> entityClass, AliasesContainer alias,
			RestrictionsContainer restrictions, OrderContainer orders,
			LoaderModeContainer loaderModes, int firstResult, int maxResult) {
		
		// Si la Classe a interroger est nulle
		if(entityClass == null) {
			
			// On leve une exception
			throw new JPersistenceToolsException("JPAGenericDAORulesBased.filter.class.null");
		}
		
		// Obtention de la session Hibernate en cours
		Session session = (Session) getEntityManager().getDelegate();
		
		// Creation de la requete de recherche
		Criteria criteria = session.createCriteria(entityClass);
		
		// Liste resultat
		List<T> result = new ArrayList<T>();

		// Traitement de l'index du premier resultat
		
		// Si l'index du premier element est < 0
		if(firstResult < 0) criteria.setFirstResult(0);
		else criteria.setFirstResult(firstResult);
		
		// Traitement du nombre max de resultat
		
		// Si le nombre max d'element est <= 0
		if(maxResult > 0) criteria.setMaxResults(maxResult);
		
		// Traitement des Alias
		addAliases(criteria, alias);
		
		// Traitement des Restrictions
		addRestrictions(criteria, restrictions);		
		
		// Traitements des Ordres de tri
		addOrders(criteria, orders);
		
		// Traitement des modes de chargements
		addLoaderMode(criteria, loaderModes);
		
		// On positionne la distinction des enregistrements
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		// Exceution de la requete
		result = criteria.list();
		
		// On retourne la liste
		return result;
	}
	
	@Override
	public <T, U> List<U> filter(Class<T> entityClass, Class<U> selectedColumnClass, String selectedColumName, AliasesContainer alias,
			RestrictionsContainer restrictions, OrderContainer orders,
			LoaderModeContainer loaderModes, int firstResult, int maxResult) {
		
		// Si la Classe a interroger est nulle
		if(entityClass == null) {
			
			// On leve une exception
			throw new JPersistenceToolsException("JPAGenericDAORulesBased.filter.class.null");
		}
		
		// Si la classe du type de retour n'est pas precisee
		if(selectedColumnClass == null) {
			
			// On leve une exception
			throw new JPersistenceToolsException("JPAGenericDAORulesBased.filter.returnvalue.class.null");
		}

		// Si le nom de la propriete a retourner est vide
		if(selectedColumName == null || selectedColumName.trim().length() == 0) {
			
			// On leve une exception
			throw new JPersistenceToolsException("JPAGenericDAORulesBased.filter.returnvalue.name.null");
		}
		
		// Obtention de la session Hibernate en cours
		Session session = (Session) getEntityManager().getDelegate();
		
		// Creation de la requete de recherche
		Criteria criteria = session.createCriteria(entityClass);
		
		// Liste resultat
		List<U> result = new ArrayList<U>();

		// Traitement de l'index du premier resultat
		
		// Si l'index du premier element est < 0
		if(firstResult < 0) criteria.setFirstResult(0);
		else criteria.setFirstResult(firstResult);
		
		// Traitement du nombre max de resultat
		
		// Si le nombre max d'element est <= 0
		if(maxResult > 0) criteria.setMaxResults(maxResult);
		
		// Traitement des Alias
		addAliases(criteria, alias);
		
		// Traitement des Restrictions
		addRestrictions(criteria, restrictions);		
		
		// Traitements des Ordres de tri
		addOrders(criteria, orders);
		
		// Traitement des modes de chargements
		addLoaderMode(criteria, loaderModes);
		
		// On ajoute la projection
		criteria.setProjection(Projections.property(selectedColumName.trim()));
		
		// Exceution de la requete
		result = criteria.list();
		
		// On retourne la liste
		return result;
	}
	
	/**
	 * Methode de validation des contraintes sur l'entite
	 * @param entity	Entite a valider
	 */
	private void validateEntityConstraints(Object entity, DAOMode mode, DAOValidatorEvaluationTime validationTime) {
		
		// Si on est en PRE-CONDITION
		if(validationTime.equals(DAOValidatorEvaluationTime.PRE_CONDITION)) {
			
			// Validation des contraintes d'integrites
			JSR303ValidatorEngine.validate(entity);
		}
		
		// Obtention de la liste des annotations DAO qui sont sur la classe
		List<Annotation> daoAnnotations = DAOValidatorHelper.loadDAOValidatorAnnotations(entity);
		
		// Si la liste est vide
		if(daoAnnotations == null || daoAnnotations.size() == 0) {
			
			// On ajoute le validateur par defaut
			daoAnnotations.add(buildDefaultValidatorAnnotation(mode, validationTime));
		}
		
		// On parcours cette liste
		for (Annotation daoAnnotation : daoAnnotations) {

			// Obtention de la classe du Validateur
			Class<?> validatorClass = DAOValidatorHelper.getValidationLogicClass(daoAnnotation);

			// Le validateur
			IDAOValidator<Annotation> validator = null;
			
			try {
				
				// On instancie le validateur
				validator = (IDAOValidator<Annotation>) validatorClass.newInstance();
				
				// Initialisation du Validateur
				validator.initialize(daoAnnotation, getEntityManager(), mode, validationTime);
				
			} catch (Throwable e) {
				
				// On relance l'exception
				throw new ValidatorInstanciationException(e);
			}
			
			// Validation des contraintes d'integrites
			validator.processValidation(entity);
			
		}
	}
	
	/**
	 * Methode de construction de la classe de validation par defaut pour le mode DAO donne ainsi que son annotation
	 * @param mode	Mode DAO
	 * @return	Classe du validateur par defaut et son annotation
	 */
	private Annotation buildDefaultValidatorAnnotation(DAOMode mode, DAOValidatorEvaluationTime validationTime) {
		
		// On affecte le mode
		final DAOMode fMode = mode;
		
		// On affecte le moment
		final DAOValidatorEvaluationTime fEvaluationTime = validationTime;
		
		// Si le mode est null
		if(mode == null || mode.equals(DAOMode.DELETE)) {
			
			// Instanciation de l'annotation
			Annotation defaultDeleteAnnotation = new IdentityDAOValidator() {
				
				@Override
				public Class<? extends Annotation> annotationType() {
					
					// On retourne la classe de l'annotation Identite
					return IdentityDAOValidator.class;
				}
			};
			
			// On retourne l'annotation
			return defaultDeleteAnnotation;
		}
		
		// Si on est en POST_CONDITION
		if(validationTime == null || validationTime.equals(DAOValidatorEvaluationTime.POST_CONDITION)) {
			
			// Instanciation de l'annotation
			Annotation defaultPOSTCONDITIONAnnotation = new IdentityDAOValidator() {
				
				@Override
				public Class<? extends Annotation> annotationType() {
					
					// On retourne la classe de l'annotation Identite
					return IdentityDAOValidator.class;
				}
			};
			
			// On retourne l'annotation
			return defaultPOSTCONDITIONAnnotation;
		}
		
		// Instanciation de l'annotation
		Annotation defaultSaveOrUpdateAnnotation = new IntegrityConstraintDAOValidator() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				
				// On retourne la classe de l'annotation de gestion des contraintes
				return IntegrityConstraintDAOValidator.class;
			}

			@Override
			public DAOMode[] mode() {
				
				// On retourne le mode par defaut
				return new DAOMode[]{fMode};
			}

			@Override
			public DAOValidatorEvaluationTime[] evaluationTime() {
				
				// On retourne le mode d'evaluation
				return new DAOValidatorEvaluationTime[]{fEvaluationTime};
			}
		};
		
		// On retourne l'annotation
		return defaultSaveOrUpdateAnnotation;
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
		
		// Liberation
		container.clear();
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

		// Liberation
		container.clear();
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

		// Liberation
		container.clear();
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
		
		// Liberation
		container.clear();
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
			criteria.setFetchMode(property, FetchMode.JOIN);
		}

		// Liberation
		container.clear();
	}
}
