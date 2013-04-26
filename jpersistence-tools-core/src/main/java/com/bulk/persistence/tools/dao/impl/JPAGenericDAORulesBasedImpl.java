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
package com.bulk.persistence.tools.dao.impl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bulk.persistence.tools.api.dao.constants.DAOMode;
import com.bulk.persistence.tools.api.dao.constants.DAOValidatorEvaluationTime;
import com.bulk.persistence.tools.api.dao.constants.OrderType;
import com.bulk.persistence.tools.api.exceptions.JPersistenceToolsException;
import com.bulk.persistence.tools.api.validator.annotations.marker.DAOConstraint;
import com.bulk.persistence.tools.api.validator.base.IDAOValidator;
import com.bulk.persistence.tools.api.validator.jsr303ext.engine.JSR303ValidatorEngine;
import com.bulk.persistence.tools.dao.JPAGenericDAO;
import com.bulk.persistence.tools.dao.utils.DAOValidatorHelper;

/**
 * Classe abstraite representant une base DAO generique compatible JPA et basee sur
 * les annotations de validations des entites et sur les moteur de validations
 * @author Jean-Jacques ETUNÈ NGI
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
 */
@SuppressWarnings("unchecked")
public abstract class JPAGenericDAORulesBasedImpl<T extends Object> implements JPAGenericDAO<T> {
	
	/**
	 * Classe de l'entite managee
	 */
	protected Class<T> entityClass = getManagedEntityClass();
	
	/**
	 * Requete par critere
	 */
	protected CriteriaQuery<T> criteriaQuery = null;
	
	/**
	 * Constructeur de critere
	 */
	protected CriteriaBuilder criteriaBuilder;
	
	/**
	 * racine des criteres
	 */
	protected Root<T> root = null;
	
	/**
	 * Alias de l'entité root
	 */
	protected final String ROOT_ALIAS = "ENTITY_CLASS__";
	
	/**
	 * Etat de validation des constraintes d'integrites en mode SAVE
	 */
	protected boolean validateIntegrityConstraintOnSave = true;

	/**
	 * Etat de validation des constraintes d'integrites en mode UPDATE
	 */
	protected boolean validateIntegrityConstraintOnUpdate = true;
	
	/**
	 * Etat de pré-validation des contraintes referentielles en mode SAVE
	 */
	protected boolean preValidateReferentialConstraintOnSave = true;

	/**
	 * Etat de post-validation des contraintes referentielles en mode SAVE
	 */
	protected boolean postValidateReferentialConstraintOnSave = false;

	/**
	 * Etat de pré-validation des contraintes referentielles en mode UPDATE
	 */
	protected boolean preValidateReferentialConstraintOnUpdate = true;

	/**
	 * Etat de post-validation des contraintes referentielles en mode UPDATE
	 */
	protected boolean postValidateReferentialConstraintOnUpdate = false;

	/**
	 * Etat de pré-validation des contraintes referentielles en mode DELETE
	 */
	protected boolean preValidateReferentialConstraintOnDelete = true;

	/**
	 * Etat de post-validation des contraintes referentielles en mode DELETE
	 */
	protected boolean postValidateReferentialConstraintOnDelete = false;
	
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#setValidateIntegrityConstraintOnSave(boolean)
	 */
	public void setValidateIntegrityConstraintOnSave(boolean validateIntegrityConstraintOnSave) {
		this.validateIntegrityConstraintOnSave = validateIntegrityConstraintOnSave;
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#setValidateIntegrityConstraintOnUpdate(boolean)
	 */
	public void setValidateIntegrityConstraintOnUpdate(boolean validateIntegrityConstraintOnUpdate) {
		this.validateIntegrityConstraintOnUpdate = validateIntegrityConstraintOnUpdate;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#setPreValidateReferentialConstraintOnSave(boolean)
	 */
	public void setPreValidateReferentialConstraintOnSave(boolean preValidateReferentialConstraintOnSave) {
		this.preValidateReferentialConstraintOnSave = preValidateReferentialConstraintOnSave;
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#setPostValidateReferentialConstraintOnSave(boolean)
	 */
	public void setPostValidateReferentialConstraintOnSave(boolean postValidateReferentialConstraintOnSave) {
		this.postValidateReferentialConstraintOnSave = postValidateReferentialConstraintOnSave;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#setPreValidateReferentialConstraintOnUpdate(boolean)
	 */
	public void setPreValidateReferentialConstraintOnUpdate(boolean preValidateReferentialConstraintOnUpdate) {
		this.preValidateReferentialConstraintOnUpdate = preValidateReferentialConstraintOnUpdate;
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#setPostValidateReferentialConstraintOnUpdate(boolean)
	 */
	public void setPostValidateReferentialConstraintOnUpdate(boolean postValidateReferentialConstraintOnUpdate) {
		this.postValidateReferentialConstraintOnUpdate = postValidateReferentialConstraintOnUpdate;
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#setPreValidateReferentialConstraintOnDelete(boolean)
	 */
	public void setPreValidateReferentialConstraintOnDelete(boolean preValidateReferentialConstraintOnDelete) {
		this.preValidateReferentialConstraintOnDelete = preValidateReferentialConstraintOnDelete;
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#setPostValidateReferentialConstraintOnDelete(boolean)
	 */
	public void setPostValidateReferentialConstraintOnDelete(boolean postValidateReferentialConstraintOnDelete) {
		this.postValidateReferentialConstraintOnDelete = postValidateReferentialConstraintOnDelete;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#delete(java.lang.Class, java.lang.Object)
	 */
	@Override
	public void delete(Object entityID) {
		
		// Suppression
		delete(entityID, preValidateReferentialConstraintOnDelete, postValidateReferentialConstraintOnDelete);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#delete(java.lang.Class, java.lang.Object, boolean, boolean)
	 */
	@Override
	public void delete(Object entityID, boolean preValidateReferentialConstraint, boolean postValidateReferentialConstraint) {
		
		// Si l'ID de l'Objet est null
		if(entityID == null) {
			
			// On leve une exception
			throw new JPersistenceToolsException("jpagenericdaorulesbased.delete.id.null");
		}
		
		// Entité à supprimer
		T entity = null;
		
		try {
			
			// On reattache
			entity = getEntityManager().find(entityClass, entityID);
			
		} catch (EntityNotFoundException e) {
			
			// On relance
			throw new JPersistenceToolsException("jpagenericdaorulesbased.delete.entitynotexist", e);
		}

		// Validation de l'entite
		if(preValidateReferentialConstraint) validateEntityReferentialConstraint(entity, DAOMode.DELETE, DAOValidatorEvaluationTime.PRE_CONDITION);

		try {
			
			// Suppression
			getEntityManager().remove(entity);
			
		} catch (Exception e) {
			
			// On relance
			throw new JPersistenceToolsException("jpagenericdaorulesbased.delete.error", e);
		}
		
		// Validation de l'entite
		if(postValidateReferentialConstraint) validateEntityReferentialConstraint(entity, DAOMode.DELETE, DAOValidatorEvaluationTime.POST_CONDITION);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#clean(java.lang.Class)
	 */
	@Override
	public void clean() {
		
		// Criteria Builder
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		
		// Création du constructeur de requete par critères
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		
		// Root element
		Root<T> root = criteriaQuery.from(entityClass);

		// Select Clause
		criteriaQuery.select(root);

		// Requete basée sur les critères
		TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);
		
		// Liste des Objets
		List<T> entities = query.getResultList();
		
		// Parcours
		for(T entity : entities) getEntityManager().remove(entity);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#save(java.lang.Object)
	 */
	@Override
	public T save(T entity) {
		
		// On retourne l'entite enregistree
		return save(entity, validateIntegrityConstraintOnSave, preValidateReferentialConstraintOnSave, postValidateReferentialConstraintOnSave);
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#save(java.lang.Object, boolean, boolean, boolean)
	 */
	@Override
	public T save(T entity, boolean validateIntegrityConstraint, boolean preValidateReferentialConstraint, boolean postValidateReferentialConstraint) {
		
		// Si l'entite est nulle
		if(entity == null) throw new JPersistenceToolsException("NullEntityException.message");
		
		// Si on doit valider les contraintes d'integrites
		if(validateIntegrityConstraint) validateEntityIntegrityConstraint(entity, DAOMode.SAVE, DAOValidatorEvaluationTime.PRE_CONDITION);
		
		// Si on doit pre-valider les contraintes referentielles
		if(preValidateReferentialConstraint) validateEntityReferentialConstraint(entity, DAOMode.SAVE, DAOValidatorEvaluationTime.PRE_CONDITION);
		
		try {
			
			// Enregistrement
			entity = getEntityManager().merge(entity);
			
		} catch (Exception e) {
			
			// On relance
			throw new JPersistenceToolsException("jpagenericdaorulesbased.save.error", e);
		}
		
		// Validation de l'entite
		if(postValidateReferentialConstraint) validateEntityReferentialConstraint(entity, DAOMode.SAVE, DAOValidatorEvaluationTime.POST_CONDITION);
		
		// On retourne l'entite enregistree
		return entity;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#update(java.lang.Object)
	 */
	@Override
	public T update(Object id, T entity) {
		
		// On retourne le resultat
		return update(id, entity, validateIntegrityConstraintOnUpdate, preValidateReferentialConstraintOnUpdate, postValidateReferentialConstraintOnUpdate);
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#update(java.lang.Object, boolean, boolean, boolean)
	 */
	@Override
	public T update(Object id, T entity, boolean validateIntegrityConstraint, boolean preValidateReferentialConstraint, boolean postValidateReferentialConstraint) {
		
		// Si l'ID est null
		if(id == null) throw new JPersistenceToolsException("jpagenericdaorulesbased.update.entityid.null");
		
		// Si l'entite est nulle
		if(entity == null) throw new JPersistenceToolsException("NullEntityException.message");

		// Si on doit valider les contraintes d'integrites
		if(validateIntegrityConstraint) validateEntityIntegrityConstraint(entity, DAOMode.UPDATE, DAOValidatorEvaluationTime.PRE_CONDITION);
		
		// Si on doit pre-valider les contraintes referentielles
		if(preValidateReferentialConstraint) validateEntityReferentialConstraint(entity, DAOMode.UPDATE, DAOValidatorEvaluationTime.PRE_CONDITION);
		
		// Le resultat
		T result = null;
		
		// Entité en Base de données
		T oldEntity = null;
		
		try {
			
			// Recherche de l'entité
			oldEntity = (T) getEntityManager().find(entityClass, id);
			
		} catch (EntityNotFoundException e) {
			
			// On relance
			throw new JPersistenceToolsException("jpagenericdaorulesbased.update.entity.notfound");
			
		} catch (Exception e) {
			
			// On relance
			throw new JPersistenceToolsException("jpagenericdaorulesbased.update.entity.error.loading.old.entity", e);
		}
		
		// Si l'entité n'est pas retrouvée
		if(oldEntity == null) throw new JPersistenceToolsException("jpagenericdaorulesbased.update.entity.notfound");
		
		try {
			
			// On obtient le resultat
			result = getEntityManager().merge(entity);
			
		} catch (OptimisticLockException e) {
			
			// On relance
			throw new JPersistenceToolsException("jpagenericdaorulesbased.update.optimisticklockexception", e);
			
		} catch (Exception e) {
			
			// On relance
			throw new JPersistenceToolsException("jpagenericdaorulesbased.update.error", e);
		}

		// Validation de l'entite
		if(postValidateReferentialConstraint) validateEntityReferentialConstraint(entity, DAOMode.UPDATE, DAOValidatorEvaluationTime.POST_CONDITION);
		
		// On retourne le resultat
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#findByPrimaryKey(java.lang.Class, java.lang.String, java.lang.Object, java.util.HashSet)
	 */
	@Override
	public T findByPrimaryKey(String entityIDName, Object entityID, Set<String> properties) {
		
		// Si le nom de la propriété ID de l'Objet est null
		if(entityIDName == null || entityIDName.trim().length() == 0) {
			
			// On leve une exception
			throw new JPersistenceToolsException("jpagenericdaorulesbased.findbyprimarykey.idname.null");
		}
		
		// Si l'ID de l'Objet est null
		if(entityID == null) {
			
			// On leve une exception
			throw new JPersistenceToolsException("jpagenericdaorulesbased.findbyprimarykey.id.null");
		}

		// Criteria Builder
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		
		// Création du constructeur de requete par critères
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);

		// Construction de la racine
		Root<T> root = criteriaQuery.from(entityClass);
		
		// Select Clause
		criteriaQuery.select(root);
		
		// Paramètre
		ParameterExpression<Object> idParameter = criteriaBuilder.parameter(Object.class, entityIDName);
		
		// Condition sur l'ID
		criteriaQuery.where(criteriaBuilder.equal(root.get(entityIDName.trim()), idParameter));
		
		// Ajout des propriétés à charger en EAGER
		addProperties(root, criteriaQuery, properties);
		
		// Requete basée sur les critères
		TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);
		
		// Positionnement du Paramètre
		query.setParameter(entityIDName, entityID);
		
		try {

			// On retourne le résultat
			return query.getSingleResult();
			
		} catch (NoResultException e) {
			
			// On retourne null
			return null;
			
		} catch (NonUniqueResultException e) {
			
			// On leve une exception
			throw new JPersistenceToolsException("jpagenericdaorulesbased.findbyprimarykey.entityidname.invalid");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#findByUniqueProperty(java.lang.String, java.lang.Object, java.util.Set)
	 */
	@Override
	public T findByUniqueProperty(String propertyName, Object propertyValue,
			Set<String> properties) {

		// Si le nom de la propriété est null
		if(propertyName == null || propertyName.trim().length() == 0) {
			
			// On leve une exception
			throw new JPersistenceToolsException("jpagenericdaorulesbased.findbyuniqueproperty.propertyname.null");
		}
		
		// Si la valeur de la propriete est nulle
		if(propertyValue == null) {
			
			// On leve une exception
			throw new JPersistenceToolsException("jpagenericdaorulesbased.findbyuniqueproperty.propertyvalue.null");
		}

		// Criteria Builder
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		
		// Création du constructeur de requete par critères
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);

		// Construction de la racine
		Root<T> root = criteriaQuery.from(entityClass);
		
		// Select Clause
		criteriaQuery.select(root);

		// Paramètre
		ParameterExpression<Object> propertyParameter = criteriaBuilder.parameter(Object.class, propertyName);
		
		// Clause where
		criteriaQuery.where(criteriaBuilder.equal(root.get(propertyName.trim()), propertyParameter));
		
		// Ajout des propriétés à charger en EAGER
		addProperties(root, criteriaQuery, properties);

		// Requete basée sur les critères
		TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);
		
		// Positionnement du Paramètre
		query.setParameter(propertyName, propertyValue);

		try {

			// On retourne le résultat
			return query.getSingleResult();
			
		} catch (NoResultException e) {
			
			// On retourne null
			return null;
			
		} catch (NonUniqueResultException e) {
			
			// On leve une exception
			throw new JPersistenceToolsException("jpagenericdaorulesbased.findbyuniqueproperty.entitypropertyname.notunique");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#filter(java.util.List, java.util.Map, java.util.Set, int, int)
	 */
	@Override
	public synchronized List<T> filter(List<Predicate> predicates, Map<String, OrderType> orders, Set<String> properties, int firstResult, int maxResult) {
		
		// Criteria Builder
		criteriaBuilder = getEntityManager().getCriteriaBuilder();
		
		// Requete de criteres
		criteriaQuery = criteriaBuilder.createQuery(entityClass);
		
		// Construction de la racine
		root = criteriaQuery.from(entityClass);
		
		// On positionne l'Alias
		root.alias(ROOT_ALIAS);
		
		// Selection de la racine
		criteriaQuery.select(root);
		
		// Ajout des Prédicats
		addPredicates(criteriaBuilder, criteriaQuery, predicates);
		
		// Ajout des Odres
		addOrders(root, criteriaQuery, orders);
		
		// Ajout des Modes de chargements
		addProperties(root, criteriaQuery, properties);
		
		// Construction de la requete basée sur les critères
		TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);

		// Traitement de l'index du premier resultat
		
		// Si l'index du premier element est < 0
		if(firstResult < 0) query.setFirstResult(0);
		else query.setFirstResult(firstResult);
		
		// Traitement du nombre max de resultat
		
		// Si le nombre max d'element est <= 0
		if(maxResult > 0) query.setMaxResults(maxResult);
		
		// Execution
		List<T> results = query.getResultList();
		
		// On retourne le résultat
		return results;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#executeCriteria(javax.persistence.criteria.CriteriaQuery, java.util.Map)
	 */
	@Override
	public <Q> List<Q> executeCriteria(CriteriaQuery<Q> criteriaQuery, Map<String, Object> parameters) {
		
		// Si la Requete est nulle
		if(criteriaQuery == null) throw new JPersistenceToolsException("jpagenericdaorulesbased.executeCriteria.query.null");
		
		// Requete Typee
		TypedQuery<Q> query = getEntityManager().createQuery(criteriaQuery);
		
		// Ajout des Parametres
		addQueryParameters(query, parameters);
		
		// Execution
		return query.getResultList();
	}
	
	/**
	 * Méthode d'ajout de parametres à la requete
	 * @param query	Requete
	 * @param parameters	Map des parametres
	 */
	protected void addQueryParameters(Query query, Map<String, Object> parameters) {
		
		// Si la MAP est nulle
		if(parameters == null || parameters.size() == 0) return;
		
		// Parcours
		for(Entry<String, Object> entry : parameters.entrySet()) {
			
			// Ajout
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * Méthode de chargement des ordres
	 * @param <T>	Paramètre de type
	 * @param criteriaQuery	Requete de critères
	 * @param orders	Liste des ordres
	 */
	protected void addOrders(Root<T> root, CriteriaQuery<T> criteriaQuery, Map<String, OrderType> orders) {
		
		// Si la liste est vide
		if(orders == null || orders.size() == 0) return;
		
		// Liste d'ordres
		List<Order> lOrders = new ArrayList<Order>();
		
		// Parcours
		for (String property : orders.keySet()) {
			
			// Si la propriete est vide
			if(property == null || property.trim().length() == 0) continue;
			
			// Evaluation de la ppt
			Path<?> path = buildPropertyPathForAnyType(root, property.trim());
			
			// Obtention du Type
			OrderType type = orders.get(property);
			
			// Si le type est null
			if(type == null) continue;
			
			// Si on est en ASC
			if(type.equals(OrderType.ASC)) lOrders.add(getActiveCriteriaBuilder().asc(path));
			else lOrders.add(getActiveCriteriaBuilder().desc(path));
		}
		
		// Ajout
		criteriaQuery.orderBy(lOrders);
	}
	
	/**
	 * Méthode de chargement des prédicats
	 * @param <T>	Paramètre de type
	 * @param criteriaBuilder Constructeur de critères
	 * @param criteriaQuery	Requete de critères
	 * @param predicates	Liste des predicats
	 */
	protected void addPredicates(CriteriaBuilder criteriaBuilder, CriteriaQuery<T> criteriaQuery, List<Predicate> predicates) {
		
		// Si la liste de predicats est vide
		if(predicates == null || predicates.size() == 0) return;
		
		// Si la liste des prdicats est de taille 1
		if(predicates.size() == 1) criteriaQuery.where(predicates.get(0));
		
		// Sinon
		else criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
	}
	
	/**
	 * Methode d'ajout des Proprietes a charger a la requete de recherche
	 * @param <T>	Paramètre de type d'entités
	 * @param root	Entités objet du from
	 * @param properties	Conteneur de propriétés
	 */
	protected void addProperties(Root<T> root, Set<String> properties) {
		
		// Si le conteneur est vide
		if(properties == null || properties.size() == 0) return;
		
		// Parcours du conteneur
		for (String property : properties) {
			
			// Si la ppt est nulle ou vide
			if(property == null || property.trim().length() == 0) continue;
			
			// Ajout de l'ordre de tri
			root.fetch(property, JoinType.LEFT);
		}
	}

	/**
	 * Methode d'ajout des Proprietes a charger a la requete de recherche
	 * @param <T>	Paramètre de type d'entités
	 * @param root	Entités objet du from
	 * @param query Requete sur l'entité
	 * @param properties	Conteneur de propriétés
	 */
	protected void addProperties(Root<T> root, CriteriaQuery<T> query, Set<String> properties) {
		
		// Ajout des ppt
		addProperties(root, properties);
		
		// On positionne le distict
		query.distinct(true);
	}
	
	/**
	 * Méthode de validation des contraintes d'integrités
	 * @param entity	Entité à valider
	 * @param mode	Mode DAO
	 * @param validationTime	Moment d'évaluation
	 */
	protected void validateEntityIntegrityConstraint(Object entity, DAOMode mode, DAOValidatorEvaluationTime validationTime) {

		// Validation des contraintes d'integrites
		JSR303ValidatorEngine.getDefaultInstance().validate(entity);
	}
	
	/**
	 * Méthode de validation des contraintes referentielles
	 * @param entity	Entité à valider
	 * @param mode	Mode DAO
	 * @param validationTime	Moment d'évaluation
	 */
	protected void validateEntityReferentialConstraint(Object entity, DAOMode mode, DAOValidatorEvaluationTime validationTime) {

		// Obtention de la liste des annotations DAO qui sont sur la classe
		List<Annotation> daoAnnotations = DAOValidatorHelper.loadDAOValidatorAnnotations(entity);
		
		// Si la liste est vide
		if(daoAnnotations == null || daoAnnotations.size() == 0) return;
		
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
				throw new JPersistenceToolsException("ValidatorInstanciationException.message", e);
			}
			
			// Validation des contraintes d'integrites
			validator.processValidation(entity);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#getActiveCriteriaBuilder()
	 */
	public CriteriaBuilder getActiveCriteriaBuilder() {
		return getEntityManager().getCriteriaBuilder();
	}
	
	/**
	 * Méthode d'obtention de la racine active de critère
	 * @return racine active de critère
	 */
	protected Root<T> getActiveRoot() {
		
		// Si le root est null
		if(root == null) {
			
			// On crée le ROOT
			root = getActiveCriteriaBuilder().createQuery(getManagedEntityClass()).from(getManagedEntityClass());
			
			// On positionne l'Alias
			root.alias(ROOT_ALIAS);
		}
		
		// On retourne le Root
		return this.root;
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#buildPropertyPath(java.lang.String)
	 */
	@Override
	public <Y extends Comparable<Y>> Path<Y> buildPropertyPath(String stringPath) {
		
		// On construit le chemin
		return buildPropertyPath(getActiveRoot(), stringPath);
	}

	/**
	 * Méthode de construction d'un chemin de propriété à partir de la racine
	 * @param <Y>	Paramètre de type du chemin final
	 * @param root	Racine
	 * @param stringPath	Chemin sous forme de chaine
	 * @return	Chemin recherché sous forme Path
	 */
	protected Path<?> buildPropertyPathForAnyType(Root<T> root, String stringPath) {
		
		// Si la racine est nulle
		if(root == null) return null;
		
		// Si la chaine est vide
		if(stringPath == null || stringPath.trim().length() == 0) return null;
		
		// Le Path à retournet
		Path<?> path = null;
		
		// On splitte sur le séparateur de champs
		String[] hierarchicalPaths = stringPath.trim().split("\\.");
		
		// Obtention du premier chemin
		path = root.get(hierarchicalPaths[0]);
		
		// Si la taille est > 1
		if(hierarchicalPaths.length > 1) {

			// Parcours
			for (int i = 1; i < hierarchicalPaths.length; i++) {
				
				// Le chemin
				String unitPath = hierarchicalPaths[i];
				
				// Si le path est vide ou est une suite d'espace
				if(unitPath == null || unitPath.trim().length() == 0) continue;
				
				// Acces à la ppt
				path = path.get(unitPath.trim());
			}
		}
		
		// On retourne le Path
		return path;
	}
	
	/**
	 * Méthode de construction d'un chemin de propriété à partir de la racine
	 * @param <Y>	Paramètre de type du chemin final
	 * @param root	Racine
	 * @param stringPath	Chemin sous forme de chaine
	 * @return	Chemin recherché sous forme Path
	 */
	protected <Y extends Comparable<Y>> Path<Y> buildPropertyPath(Root<T> root, String stringPath) {
		
		// Si la racine est nulle
		if(root == null) return null;
		
		// Si la chaine est vide
		if(stringPath == null || stringPath.trim().length() == 0) return null;
		
		// Le Path à retournet
		Path<? extends Comparable<?>> path = null;
		
		// On splitte sur le séparateur de champs
		String[] hierarchicalPaths = stringPath.trim().split("\\.");
		
		// Obtention du premier chemin
		path = root.get(hierarchicalPaths[0]);
		
		// Si la taille est > 1
		if(hierarchicalPaths.length > 1) {

			// Parcours
			for (int i = 1; i < hierarchicalPaths.length; i++) {
				
				// Le chemin
				String unitPath = hierarchicalPaths[i];
				
				// Si le path est vide ou est une suite d'espace
				if(unitPath == null || unitPath.trim().length() == 0) continue;
				
				// Acces à la ppt
				path = path.get(unitPath.trim());
			}
		}
		
		// On retourne le Path
		return (Path<Y>) path;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#eq(java.lang.String, java.lang.Comparable)
	 */
	@Override
	public <Y extends Comparable<Y>> Predicate eq(String property, Y value) {
		
		// On construit le predicat
		return getActiveCriteriaBuilder().equal(this.<Y>buildPropertyPath(getActiveRoot(), property), value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#notEq(java.lang.String, java.lang.Comparable)
	 */
	@Override
	public <Y extends Comparable<Y>> Predicate notEq(String property, Y value) {
		
		// On construit le predicat
		return getActiveCriteriaBuilder().notEqual(this.<Y>buildPropertyPath(getActiveRoot(), property), value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#gt(java.lang.String, java.lang.Comparable)
	 */
	@Override
	public <Y extends Comparable<Y>> Predicate gt(String property,  Y value) {
		
		// On construit le predicat
		return getActiveCriteriaBuilder().greaterThan(this.<Y>buildPropertyPath(getActiveRoot(), property), value);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#ge(java.lang.String, java.lang.Comparable)
	 */
	@Override
	public <Y extends Comparable<Y>> Predicate ge(String property,  Y value) {
		
		// On construit le predicat
		return getActiveCriteriaBuilder().greaterThanOrEqualTo(this.<Y>buildPropertyPath(getActiveRoot(), property), value);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#lt(java.lang.String, java.lang.Comparable)
	 */
	@Override
	public <Y extends Comparable<Y>> Predicate lt(String property,  Y value) {
		
		// On construit le predicat
		return getActiveCriteriaBuilder().lessThan(this.<Y>buildPropertyPath(getActiveRoot(), property), value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#le(java.lang.String, java.lang.Comparable)
	 */
	@Override
	public <Y extends Comparable<Y>> Predicate le(String property,  Y value) {
		
		// On construit le predicat
		return getActiveCriteriaBuilder().lessThanOrEqualTo(this.<Y>buildPropertyPath(getActiveRoot(), property), value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#like(java.lang.String, java.lang.String)
	 */
	@Override
	public Predicate like(String property,  String pattern) {
		
		// On construit le predicat
		return getActiveCriteriaBuilder().like(this.<String>buildPropertyPath(getActiveRoot(), property), pattern);
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#notLike(java.lang.String, java.lang.String)
	 */
	@Override
	public Predicate notLike(String property,  String pattern) {
		
		// On construit le predicat
		return getActiveCriteriaBuilder().notLike(this.<String>buildPropertyPath(getActiveRoot(), property), pattern);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#between(java.lang.String, java.lang.Comparable, java.lang.Comparable)
	 */
	@Override
	public <Y extends Comparable<Y>> Predicate between(String property, Y minValue, Y maxValue) {

		// On construit le predicat
		return getActiveCriteriaBuilder().between(this.<Y>buildPropertyPath(getActiveRoot(), property), minValue, maxValue);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#isNull(java.lang.String)
	 */
	@Override
	public Predicate isNull(String property) {

		// On construit le predicat
		return getActiveCriteriaBuilder().isNull(this.<Boolean>buildPropertyPath(getActiveRoot(), property));
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#isNotNull(java.lang.String)
	 */
	@Override
	public Predicate isNotNull(String property) {

		// On construit le predicat
		return getActiveCriteriaBuilder().isNotNull(this.<Boolean>buildPropertyPath(getActiveRoot(), property));
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#isTrue(java.lang.String)
	 */
	@Override
	public Predicate isTrue(String property) {

		// On construit le predicat
		return getActiveCriteriaBuilder().isTrue(this.<Boolean>buildPropertyPath(getActiveRoot(), property));
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#isFalse(java.lang.String)
	 */
	@Override
	public Predicate isFalse(String property) {

		// On construit le predicat
		return getActiveCriteriaBuilder().isFalse(this.<Boolean>buildPropertyPath(getActiveRoot(), property));
	}
}
