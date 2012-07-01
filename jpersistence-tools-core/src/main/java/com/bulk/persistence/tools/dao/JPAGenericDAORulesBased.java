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
import java.util.HashSet;
import java.util.List;
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

import com.bulk.persistence.tools.api.exceptions.JPersistenceToolsException;
import com.bulk.persistence.tools.api.exceptions.NullEntityException;
import com.bulk.persistence.tools.api.exceptions.ValidatorInstanciationException;
import com.bulk.persistence.tools.api.validator.annotations.DAOConstraint;
import com.bulk.persistence.tools.api.validator.annotations.IdentityDAOValidator;
import com.bulk.persistence.tools.api.validator.annotations.IntegrityConstraintDAOValidator;
import com.bulk.persistence.tools.dao.api.constants.DAOMode;
import com.bulk.persistence.tools.dao.api.constants.DAOValidatorEvaluationTime;
import com.bulk.persistence.tools.dao.utils.DAOValidatorHelper;
import com.bulk.persistence.tools.validator.IDAOValidator;
import com.bulk.persistence.tools.validator.engine.JSR303ValidatorEngine;

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
public abstract class JPAGenericDAORulesBased<T extends Object> implements IJPAGenericDAO<T> {
	
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
	protected boolean postValidateReferentialConstraintOnSave = true;

	/**
	 * Etat de pré-validation des contraintes referentielles en mode UPDATE
	 */
	protected boolean preValidateReferentialConstraintOnUpdate = true;

	/**
	 * Etat de post-validation des contraintes referentielles en mode UPDATE
	 */
	protected boolean postValidateReferentialConstraintOnUpdate = true;

	/**
	 * Etat de pré-validation des contraintes referentielles en mode DELETE
	 */
	protected boolean preValidateReferentialConstraintOnDelete = true;

	/**
	 * Etat de post-validation des contraintes referentielles en mode DELETE
	 */
	protected boolean postValidateReferentialConstraintOnDelete = true;

	
	
	/**
	 * Méthode de mise à jour de l'Etat de validation des constraintes d'integrites en mode SAVE
	 * @param validateIntegrityConstraint Etat de validation des constraintes d'integrites en mode SAVE
	 */
	public void setValidateIntegrityConstraintOnSave(boolean validateIntegrityConstraintOnSave) {
		this.validateIntegrityConstraintOnSave = validateIntegrityConstraintOnSave;
	}

	/**
	 * Méthode de mise à jour de l'Etat de validation des constraintes d'integrites en mode UPDATE
	 * @param validateIntegrityConstraint Etat de validation des constraintes d'integrites en mode UPDATE
	 */
	public void setValidateIntegrityConstraintOnUpdate(boolean validateIntegrityConstraintOnUpdate) {
		this.validateIntegrityConstraintOnUpdate = validateIntegrityConstraintOnUpdate;
	}
	
	/**
	 * Méthode de mise à jour de l'Etat de pré-validation des contraintes referentielles en mode SAVE
	 * @param preValidateReferentialConstraintOnSave Etat de pré-validation des contraintes referentielles en mode SAVE
	 */
	public void setPreValidateReferentialConstraintOnSave(boolean preValidateReferentialConstraintOnSave) {
		this.preValidateReferentialConstraintOnSave = preValidateReferentialConstraintOnSave;
	}

	/**
	 * Méthode de mise à jour de l'Etat de post-validation des contraintes referentielles en mode SAVE
	 * @param validateReferentialConstraint Etat de postvalidation des contraintes referentielles en mode SAVE
	 */
	public void setPostValidateReferentialConstraintOnSave(boolean postValidateReferentialConstraintOnSave) {
		this.postValidateReferentialConstraintOnSave = postValidateReferentialConstraintOnSave;
	}

	/**
	 * Méthode de mise à jour de l'Etat de pré-validation des contraintes referentielles en mode UPDATE
	 * @param preValidateReferentialConstraintOnUpdate Etat de pré-validation des contraintes referentielles en mode UPDATE
	 */
	public void setPreValidateReferentialConstraintOnUpdate(boolean preValidateReferentialConstraintOnUpdate) {
		this.preValidateReferentialConstraintOnUpdate = preValidateReferentialConstraintOnUpdate;
	}

	/**
	 * Méthode de mise à jour de l'Etat de post-validation des contraintes referentielles en mode UPDATE
	 * @param postValidateReferentialConstraintOnUpdate Etat de postvalidation des contraintes referentielles en mode UPDATE
	 */
	public void setPostValidateReferentialConstraintOnUpdate(boolean postValidateReferentialConstraintOnUpdate) {
		this.postValidateReferentialConstraintOnUpdate = postValidateReferentialConstraintOnUpdate;
	}

	/**
	 * Méthode de mise à jour de l'Etat de pré-validation des contraintes referentielles en mode DELETE
	 * @param preValidateReferentialConstraintOnDelete Etat de pré-validation des contraintes referentielles en mode DELETE
	 */
	public void setPreValidateReferentialConstraintOnDelete(boolean preValidateReferentialConstraintOnDelete) {
		this.preValidateReferentialConstraintOnDelete = preValidateReferentialConstraintOnDelete;
	}

	/**
	 * Méthode de mise à jour de l'Etat de post-validation des contraintes referentielles en mode DELETE
	 * @param postValidateReferentialConstraintOnDelete Etat de postvalidation des contraintes referentielles en mode DELETE
	 */
	public void setPostValidateReferentialConstraintOnDelete(boolean postValidateReferentialConstraintOnDelete) {
		this.postValidateReferentialConstraintOnDelete = postValidateReferentialConstraintOnDelete;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.IJPAGenericDAO#delete(java.lang.Class, java.lang.Object)
	 */
	@Override
	public void delete(Class<T> entityClass, Object entityID) {

		// Si la Classe a interroger est nulle
		if(entityClass == null) {
			
			// On leve une exception
			throw new JPersistenceToolsException("jpagenericdaorulesbased.delete.class.null");
		}

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
		if(this.preValidateReferentialConstraintOnDelete) validateEntityReferentialConstraint(entity, DAOMode.DELETE, DAOValidatorEvaluationTime.PRE_CONDITION);

		try {
			
			// Suppression
			getEntityManager().remove(entity);
			
		} catch (Exception e) {
			
			// On relance
			throw new JPersistenceToolsException("jpagenericdaorulesbased.delete.error", e);
		}
		
		// Validation de l'entite
		if(this.postValidateReferentialConstraintOnDelete) validateEntityReferentialConstraint(entity, DAOMode.DELETE, DAOValidatorEvaluationTime.POST_CONDITION);
	}

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.IJPAGenericDAO#clean(java.lang.Class)
	 */
	@Override
	public void clean(Class<T> entityClass) {
		
		// La requete
		Query q = getEntityManager().createQuery("delete from " + entityClass.getCanonicalName());
		
		// Execution
		try {
			
			// Execution
			q.executeUpdate();
			
		} catch (Exception e) {
			
			// On relance
			throw new JPersistenceToolsException("jpagenericdaorulesbased.clean.error", e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.IJPAGenericDAO#save(java.lang.Object)
	 */
	@Override
	public T save(T entity) {
		
		// Si l'entite est nulle
		if(entity == null) throw new NullEntityException();
		
		// Si on doit valider les contraintes d'integrites
		if(this.validateIntegrityConstraintOnSave) validateEntityIntegrityConstraint(entity, DAOMode.SAVE, DAOValidatorEvaluationTime.PRE_CONDITION);
		
		// Si on doit pre-valider les contraintes referentielles
		if(this.preValidateReferentialConstraintOnSave) validateEntityReferentialConstraint(entity, DAOMode.SAVE, DAOValidatorEvaluationTime.PRE_CONDITION);
		
		try {
			
			// Enregistrement
			entity = getEntityManager().merge(entity);
			
		} catch (Exception e) {
			
			// On relance
			throw new JPersistenceToolsException("jpagenericdaorulesbased.save.error", e);
		}
		
		// Validation de l'entite
		if(this.postValidateReferentialConstraintOnSave) validateEntityReferentialConstraint(entity, DAOMode.SAVE, DAOValidatorEvaluationTime.POST_CONDITION);
		
		// On retourne l'entite enregistree
		return entity;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.IJPAGenericDAO#update(java.lang.Object)
	 */
	@Override
	public T update(T entity) {
		
		// Si l'entite est nulle
		if(entity == null) throw new NullEntityException();

		// Si on doit valider les contraintes d'integrites
		if(this.validateIntegrityConstraintOnUpdate) validateEntityIntegrityConstraint(entity, DAOMode.UPDATE, DAOValidatorEvaluationTime.PRE_CONDITION);
		
		// Si on doit pre-valider les contraintes referentielles
		if(this.preValidateReferentialConstraintOnUpdate) validateEntityReferentialConstraint(entity, DAOMode.UPDATE, DAOValidatorEvaluationTime.PRE_CONDITION);
		
		// Le resultat
		T result = null;
		
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
		if(this.postValidateReferentialConstraintOnUpdate) validateEntityReferentialConstraint(entity, DAOMode.UPDATE, DAOValidatorEvaluationTime.POST_CONDITION);
		
		// On retourne le resultat
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.IJPAGenericDAO#findByPrimaryKey(java.lang.Class, java.lang.String, java.lang.Object, java.util.HashSet)
	 */
	@Override
	public T findByPrimaryKey(Class<T> entityClass, String entityIDName, Object entityID, HashSet<String> properties) {
		
		// Si la Classe a interroger est nulle
		if(entityClass == null) {
			
			// On leve une exception
			throw new JPersistenceToolsException("jpagenericdaorulesbased.findbyprimarykey.class.null");
		}

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
		
		// Obtention du Constructeur de Criteres
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
				
		// Création du constructeur de requete par critères
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		
		// Root element
		Root<T> root = criteriaQuery.from(entityClass);

		// Select Clause
		criteriaQuery.select(root);
		
		// Paramètre
		ParameterExpression<Object> idParameter = criteriaBuilder.parameter(Object.class, "entityID");
		
		// Condition sur l'ID
		criteriaQuery.where(criteriaBuilder.equal(root.get(entityIDName.trim()), idParameter));
		
		// Ajout des propriétés à charger en EAGER
		addProperties(root, criteriaQuery, properties);
		
		// Requete basée sur les critères
		TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);
		
		// Positionnement du Paramètre
		query.setParameter("entityID", entityID);
		
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
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.IJPAGenericDAO#filter(java.lang.Class, java.util.List, java.util.List, java.util.Set, int, int)
	 */
	@Override
	public List<T> filter(Class<T> entityClass, List<Predicate> predicates, List<Order> orders, Set<String> properties, int firstResult, int maxResult) {

		// Si la Classe a interroger est nulle
		if(entityClass == null) {
			
			// On leve une exception
			throw new JPersistenceToolsException("jpagenericdaorulesbased.filter.class.null");
		}
		
		// Constructeur de Criteres
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		
		// Requete de criteres
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		
		// Construction de la racine
		Root<T> root = criteriaQuery.from(entityClass);
		
		// Selection de la racine
		criteriaQuery.select(root);
		
		// Ajout des Prédicats
		addPredicates(criteriaBuilder, criteriaQuery, predicates);
		
		// Ajout des Odres
		addOrders(criteriaQuery, orders);
		
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
	
	/**
	 * Méthode de chargement des ordres
	 * @param <T>	Paramètre de type
	 * @param criteriaQuery	Requete de critères
	 * @param orders	Liste des ordres
	 */
	protected void addOrders(CriteriaQuery<T> criteriaQuery, List<Order> orders) {
		
		// Si la liste est vide
		if(orders == null || orders.size() == 0) return;
		
		// Ajout de la liste
		criteriaQuery.orderBy(orders);
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
	 * Méthode de construction d'un chemin de propriété à partir de la racine
	 * @param <T>	Paramètre de type
	 * @param root	Racine
	 * @param stringPath	Chemin sous forme de chaine
	 * @return	Chemin recherché sous forme Path
	 */
	protected Path<T> buildPropertyPath(Root<T> root, String stringPath) {
		
		// Si la racine est nulle
		if(root == null) return null;
		
		// Si la chaine est vide
		if(stringPath == null || stringPath.trim().length() == 0) return null;
		
		// Le Path à retournet
		Path<T> path = root;
		
		// On splitte sur le séparateur de champs
		String[] hierarchicalPaths = stringPath.trim().split("\\.");
		
		// Parcours
		for (String unitPath : hierarchicalPaths) {
			
			// Si le path est vide ou est une suite d'espace
			if(unitPath == null || unitPath.trim().length() == 0) continue;
			
			// Acces à la ppt
			path = path.get(unitPath.trim());
		}
		
		// On retourne le Path
		return path;
	}
	
	/**
	 * Méthode de validation des contraintes d'integrités
	 * @param entity	Entité à valider
	 * @param mode	Mode DAO
	 * @param validationTime	Moment d'évaluation
	 */
	protected void validateEntityIntegrityConstraint(Object entity, DAOMode mode, DAOValidatorEvaluationTime validationTime) {

		// Si on est en PRE-CONDITION et en mode Engegistrement ou Modification
		if(validationTime.equals(DAOValidatorEvaluationTime.PRE_CONDITION)  && !mode.equals(DAOMode.DELETE)) {
			
			// Validation des contraintes d'integrites
			JSR303ValidatorEngine.getDefaultInstance().validate(entity);
		}
		
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

			@Override
			public Class<? extends JSR303ValidatorEngine> validatorEngineClass() {
				
				// Validator Engine Class
				return JSR303ValidatorEngine.class;
			}
		};
		
		// On retourne l'annotation
		return defaultSaveOrUpdateAnnotation;
	}

}
