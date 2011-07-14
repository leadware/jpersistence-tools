/**
 * 
 */
package com.yashiro.persistence.utils.dao;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.OptimisticLockException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.yashiro.persistence.utils.annotations.validator.DAOValidatorRule;
import com.yashiro.persistence.utils.annotations.validator.IdentityDAOValidator;
import com.yashiro.persistence.utils.annotations.validator.IntegrityConstraintDAOValidator;
import com.yashiro.persistence.utils.annotations.validator.engine.IDAOValidator;
import com.yashiro.persistence.utils.annotations.validator.engine.ValidatorEngine;
import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.BaseJPersistenceUtilsException;
import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.EntityNotDeletedException;
import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.EntityNotSavedException;
import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.NullEntityException;
import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.ValidatorInstanciationException;
import com.yashiro.persistence.utils.dao.constant.DAOMode;
import com.yashiro.persistence.utils.dao.constant.DAOValidatorEvaluationTime;
import com.yashiro.persistence.utils.dao.tools.AliasesContainer;
import com.yashiro.persistence.utils.dao.tools.DAOValidatorHelper;
import com.yashiro.persistence.utils.dao.tools.LoaderModeContainer;
import com.yashiro.persistence.utils.dao.tools.OrderContainer;
import com.yashiro.persistence.utils.dao.tools.PropertyContainer;
import com.yashiro.persistence.utils.dao.tools.RestrictionsContainer;
import com.yashiro.persistence.utils.dao.tools.SaveListResult;

/**
 * Classe abstraite representant une base DAO generique compatible JPA et basee sur
 * les annotations de validations des entites et sur les moteur de validations
 * @author Jean-Jacques
 * @see 
 * 	<b>
 * 		<i>Annotations</i>
 * 		<ol>
 * 			<li>{@link DAOValidatorRule}
 * 		</ol>
 * 		<i>Engine</i>
 * 		<ol>
 * 			<li>{@link ValidatorEngine}</li>
 * 			<li>{@link IDAOValidator}</li>
 * 		</ol>
 * 	</b>
 * @version 2.0
 */
@SuppressWarnings("unchecked")
public abstract class JPAGenericDAORulesBased implements IJPAGenericDAO {
	
	/**
	 * Un Logger
	 */
	private Log logger = LogFactory.getLog(JPAGenericDAORulesBased.class);
	
	@Override
	public <T> void delete(T entity) {
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#delete");
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#delete - Validation de l'entite");
		
		// Si l'entite est nulle
		if(entity == null) throw new NullEntityException();
		
		// Validation de l'entite
		validateEntityConstraints(entity, DAOMode.DELETE, DAOValidatorEvaluationTime.PRE_CONDITION);
		
		try {

			// Une information
			logger.trace("JPAGenericDAORulesBased#delete - Suppression de l'Objet");
			
			// On reattache
			entity = getEntityManager().merge(entity);
			
			// Suppression
			getEntityManager().remove(entity);
			
		} catch (Exception e) {
			
			// On relance
			throw new BaseJPersistenceUtilsException("JPAGenericDAORulesBased.delete.error", e);
		}
		
		// Validation de l'entite
		validateEntityConstraints(entity, DAOMode.DELETE, DAOValidatorEvaluationTime.POST_CONDITION);
	}

	@Override
	public <T> List<T> deleteList(List<T> entities, boolean rollbackOnException) {

		// Une information
		logger.trace("JPAGenericDAORulesBased#delete [List]");

		// Liste des non enregistrees
		List<T> notDeleted = new ArrayList<T>();
		
		// Si la liste est vide
		if(entities == null || entities.size() == 0) return notDeleted;
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#delete [List] - Parcours de la liste");
		
		// Parcours de la liste
		for (T entity : entities) {
			
			try {
				
				// Tentative de suppression
				delete(entity);
				
			} catch (Exception e) {

				// Une information
				logger.trace("JPAGenericDAORulesBased#delete [List] - Erreur lors de la suppression [" + e.getClass().getName() + ", " + e.getMessage() + "]");
				
				// Si on sort en cas d'exception
				if(rollbackOnException) {
					
					// Une information
					logger.trace("JPAGenericDAORulesBased#delete [List] - On relance l'exception [rollbackOnException: " + rollbackOnException + "]");
					
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
		
		// La requête
		Query q = getEntityManager().createQuery("from " + entityClass.getCanonicalName());
		
		// Execution
		try {
			
			// Execution
			return deleteList(q.getResultList(), false);
			
		} catch (Exception e) {
			
			// On relance
			throw new BaseJPersistenceUtilsException("JPAGenericDAORulesBased.clean.error");
		}
	}
	
	@Override
	public <T> T save(T entity) {
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#save");
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#save - Chargement de la classe de validation");

		// Si l'entite est nulle
		if(entity == null) throw new NullEntityException();
		
		// Validation de l'entite
		validateEntityConstraints(entity, DAOMode.SAVE, DAOValidatorEvaluationTime.PRE_CONDITION);
		
		try {
			
			// Enregistrement
			entity = getEntityManager().merge(entity);
			
		} catch (Exception e) {
			e.printStackTrace();
			// On relance
			throw new BaseJPersistenceUtilsException("JPAGenericDAORulesBased.save.error", e);
		}
		
		// Validation de l'entite
		validateEntityConstraints(entity, DAOMode.SAVE, DAOValidatorEvaluationTime.POST_CONDITION);
		
		// On retourne l'entite enregistree
		return entity;
	}
	
	@Override
	public <T> SaveListResult<T> saveList(List<T> entities, boolean rollbackOnException) {
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#save[List]");
		
		// Si la liste est vide
		if(entities == null || entities.size() == 0) {
			
			// Une information
			logger.trace("JPAGenericDAORulesBased#save[List] - Liste des entites a enregistrer vide");
			
			// On retourne null
			return null;
		}
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#save[List] - Initialisation de la MAP des Exceptions classees par Entites non enregistrees");
		
		// Liste des Entites non enregistrees
		Map<T, Throwable> notRegistered = new HashMap<T, Throwable>();
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#save[List] - Initialisation de la Liste des Entites enregistrees");
		
		// Liste des Entites enregistrees
		List<T> registered = new ArrayList<T>();
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#save[List] - Parcours de la liste des entites a enregistrer");
		
		// Parcours
		for (T entity : entities) {
			
			try {
				
				// Une information
				logger.trace("JPAGenericDAORulesBased#save[List] - Tentative d'enregistrement (Methode save)");
				
				// Enregistrement
				registered.add(save(entity));
				
			} catch (Throwable e) {
				
				// Une information
				logger.trace("JPAGenericDAORulesBased#save[List] - Erreur lors de l'enregistrement [" + e.getClass().getName() + ", " + e.getMessage() + "]");
				
				// Si on sort en cas d'exception
				if(rollbackOnException) {
					
					// Une information
					logger.trace("JPAGenericDAORulesBased#save[List] - On relance l'exception [rollbackOnException: " + rollbackOnException + "]");
					
					// On relance l'exception
					throw new EntityNotSavedException(entity, e);
				}

				// Une information
				logger.trace("JPAGenericDAORulesBased#save[List] - Enregistrement de l'erreur sur l'entite");
				
				// Ajout de l'entite non enregistree et de son exception
				notRegistered.put(entity, e);
			}
		}

		// Une information
		logger.trace("JPAGenericDAORulesBased#save[List] - Initialisation du Résultat");
		
		// Initialisation du resultat
		SaveListResult<T> result = new SaveListResult<T>(registered, notRegistered);
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#save[List] - On retourne le resultat");
		
		// On retourne la liste des Entites enregistrees
		return result;
	}

	@Override
	public <T> T update(T entity) {
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#update");
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#update - Chargement de la classe de validation");
		
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
			/*
			try {
				
				// Tentative de rafraichissement
				getEntityManager().refresh(entity);
				
			} catch(Exception e1) {}
			*/
			// On relance
			throw new BaseJPersistenceUtilsException("JPAGenericDAORulesBased.update.optimisticklockexception", e);
			
		} catch (Exception e) {
			
			// On relance
			throw new BaseJPersistenceUtilsException("JPAGenericDAORulesBased.update.error", e);
		}

		// Validation de l'entite
		validateEntityConstraints(entity, DAOMode.UPDATE, DAOValidatorEvaluationTime.POST_CONDITION);
		
		// On retourne le resultat
		return result;
	}
	
	@Override
	public <T> T findByPrimaryKey(Class<T> entityClass, Object entityID, PropertyContainer properties) {
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#findByPrimaryKey");
		
		// Si la Classe a interroger est nulle
		if(entityClass == null) {
			
			// Un Log
			logger.trace("JPAGenericDAORulesBased#findByPrimaryKey - Classe cible du filtre nulle");
			
			// On leve une exception
			throw new BaseJPersistenceUtilsException("JPAGenericDAORulesBased.findByPrimaryKey.class.null");
		}
		
		// Si l'ID de l'Objet est null
		if(entityID == null) {
			
			// Un Log
			logger.trace("JPAGenericDAORulesBased#findByPrimaryKey - L'identifiant de l'instance cible null");
			
			// On leve une exception
			throw new BaseJPersistenceUtilsException("JPAGenericDAORulesBased.findByPrimaryKey.id.null");
		}
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#findByPrimaryKey - Obtention de l'objet par son ID");

		// Un Log
		logger.trace("JPAGenericDAORulesBased#findByPrimaryKey - Obtention de la session deleguee");
		
		// Obtention de la session Hibernate en cours
		Session session = (Session) getEntityManager().getDelegate();
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#findByPrimaryKey - Creation de la requête Criteria");
		
		// Creation de la requête de recherche
		Criteria criteria = session.createCriteria(entityClass);
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#findByPrimaryKey - Ajout de la restriction sur l'ID");
				
		// Ajout de la restriction sur l'ID
		criteria.add(Restrictions.idEq(entityID));
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#findByPrimaryKey - Ajout des proprietes a charger");
		
		// Ajout des proprietes a charger
		addProperties(criteria, properties);
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#findByPrimaryKey - Execution de la requête");
		
		// Execution de la requête
		T result = (T) criteria.uniqueResult();

		// Un Log
		logger.trace("JPAGenericDAORulesBased#findByPrimaryKey - On retourne le resultat");
		
		// On retourne le resultat
		return result;
	}
	
	@Override
	public <T> List<T> filter(Class<T> entityClass, AliasesContainer alias,
			RestrictionsContainer restrictions, OrderContainer orders,
			LoaderModeContainer loaderModes, int firstResult, int maxResult) {
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#filter");
		
		// Si la Classe a interroger est nulle
		if(entityClass == null) {
			
			// Un Log
			logger.trace("JPAGenericDAORulesBased#filter - Classe cible du filtre nulle");
			
			// On leve une exception
			throw new BaseJPersistenceUtilsException("JPAGenericDAORulesBased.filter.class.null");
		}
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - Obtention de la session deleguee");
		
		// Obtention de la session Hibernate en cours
		Session session = (Session) getEntityManager().getDelegate();
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - Creation de la requête Criteria");
		
		// Creation de la requête de recherche
		Criteria criteria = session.createCriteria(entityClass);
		
		// Liste resultat
		List<T> result = new ArrayList<T>();

		// Traitement de l'index du premier resultat
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - Traitement de l'index du premier resultat");
		
		// Si l'index du premier element est < 0
		if(firstResult < 0) criteria.setFirstResult(0);
		else criteria.setFirstResult(firstResult);
		
		// Traitement du nombre max de resultat
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - Traitement du nombre max de resultat");
		
		// Si le nombre max d'element est <= 0
		if(maxResult > 0) criteria.setMaxResults(maxResult);
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - Traitement des Alias");
		
		// Traitement des Alias
		addAliases(criteria, alias);
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - Traitement des Restrictions");
		
		// Traitement des Restrictions
		addRestrictions(criteria, restrictions);		
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - Traitements des Ordres de tri");
		
		// Traitements des Ordres de tri
		addOrders(criteria, orders);
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - Traitement des modes de chargements");
		
		// Traitement des modes de chargements
		addLoaderMode(criteria, loaderModes);
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - Exceution de la requête");
		
		// On positionne la distinction des enregistrements
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		// Exceution de la requête
		result = criteria.list();

		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - On retourne la liste");
		
		// On retourne la liste
		return result;
	}
	
	@Override
	public <T, U> List<U> filter(Class<T> entityClass, Class<U> selectedColumnClass, String selectedColumName, AliasesContainer alias,
			RestrictionsContainer restrictions, OrderContainer orders,
			LoaderModeContainer loaderModes, int firstResult, int maxResult) {
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#filter");
		
		// Si la Classe a interroger est nulle
		if(entityClass == null) {
			
			// Un Log
			logger.trace("JPAGenericDAORulesBased#filter - Classe cible du filtre nulle");
			
			// On leve une exception
			throw new BaseJPersistenceUtilsException("JPAGenericDAORulesBased.filter.class.null");
		}
		
		// Si la classe du type de retour n'est pas precisée
		if(selectedColumnClass == null) {
			
			// Un Log
			logger.trace("JPAGenericDAORulesBased#filter - Classe de la valeur de retour est nulle");
			
			// On leve une exception
			throw new BaseJPersistenceUtilsException("JPAGenericDAORulesBased.filter.returnvalue.class.null");
		}

		// Si le nom de la propriété a retourner est vide
		if(selectedColumName == null || selectedColumName.trim().length() == 0) {
			
			// Un Log
			logger.trace("JPAGenericDAORulesBased#filter - Nom de la propriété a retourner est vide");
			
			// On leve une exception
			throw new BaseJPersistenceUtilsException("JPAGenericDAORulesBased.filter.returnvalue.name.null");
		}
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - Obtention de la session deleguee");
		
		// Obtention de la session Hibernate en cours
		Session session = (Session) getEntityManager().getDelegate();
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - Creation de la requête Criteria");
		
		// Creation de la requête de recherche
		Criteria criteria = session.createCriteria(entityClass);
		
		// Liste resultat
		List<U> result = new ArrayList<U>();

		// Traitement de l'index du premier resultat
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - Traitement de l'index du premier resultat");
		
		// Si l'index du premier element est < 0
		if(firstResult < 0) criteria.setFirstResult(0);
		else criteria.setFirstResult(firstResult);
		
		// Traitement du nombre max de resultat
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - Traitement du nombre max de resultat");
		
		// Si le nombre max d'element est <= 0
		if(maxResult > 0) criteria.setMaxResults(maxResult);
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - Traitement des Alias");
		
		// Traitement des Alias
		addAliases(criteria, alias);
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - Traitement des Restrictions");
		
		// Traitement des Restrictions
		addRestrictions(criteria, restrictions);		
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - Traitements des Ordres de tri");
		
		// Traitements des Ordres de tri
		addOrders(criteria, orders);
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - Traitement des modes de chargements");
		
		// Traitement des modes de chargements
		addLoaderMode(criteria, loaderModes);
		
		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - Exceution de la requête");
		
		// On ajoute la projection
		criteria.setProjection(Projections.property(selectedColumName.trim()));
		
		// Exceution de la requête
		result = criteria.list();

		// Un Log
		logger.trace("JPAGenericDAORulesBased#filter - On retourne la liste");
		
		// On retourne la liste
		return result;
	}
	
	/**
	 * Methode de validation des contraintes sur l'entite
	 * @param entity	Entite a valider
	 */
	private void validateEntityConstraints(Object entity, DAOMode mode, DAOValidatorEvaluationTime validationTime) {
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#validateEntityConstraints");
		
		// Si on est en PRE-CONDITION
		if(validationTime.equals(DAOValidatorEvaluationTime.PRE_CONDITION)) {
			
			// Un Log
			logger.trace("JPAGenericDAORulesBased#validateEntityConstraints - Validation des contraintes d'integrites");
			
			// Validation des contraintes d'integrites
			ValidatorEngine.validate(entity);
		}
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#validateEntityConstraints - Chargement des Annotation de validation de l'entite pour le mode [" + mode.toString() + "]");
		
		// Obtention de la liste des annotations DAO qui sont sur la classe
		List<Annotation> daoAnnotations = DAOValidatorHelper.loadDAOValidatorAnnotations(entity);
		
		// Si la liste est vide
		if(daoAnnotations == null || daoAnnotations.size() == 0) {
			
			// Une information
			logger.trace("JPAGenericDAORulesBased#validateEntityConstraints - Aucune annotation de Validation : Construction des annotations par defaut pour le mode");
			
			// On ajoute le validateur par defaut
			daoAnnotations.add(buildDefaultValidatorAnnotation(mode, validationTime));
		}

		// Une information
		logger.trace("JPAGenericDAORulesBased#validateEntityConstraints - Parcours des Annotations de validation");
		
		// On parcours cette liste
		for (Annotation daoAnnotation : daoAnnotations) {

			// Une information
			logger.trace("JPAGenericDAORulesBased#validateEntityConstraints - Annotation en cours [" + daoAnnotation.annotationType().getName() + "]");
			
			// Une information
			logger.trace("JPAGenericDAORulesBased#validateEntityConstraints - Obtention de la classe de validation de cette Annotation");
			
			// Obtention de la classe du Validateur
			Class<?> validatorClass = DAOValidatorHelper.getValidationLogicClass(daoAnnotation);

			// Une information
			logger.trace("JPAGenericDAORulesBased#validateEntityConstraints - Classe de validation [" + validatorClass.getName() + "]");
			
			// Le validateur
			IDAOValidator<Annotation> validator = null;
			
			try {
				
				// Une information
				logger.trace("JPAGenericDAORulesBased#validateEntityConstraints - Instanciation du validateur");
				
				// On instancie le validateur
				validator = (IDAOValidator<Annotation>) validatorClass.newInstance();
				
				// Une information
				logger.trace("JPAGenericDAORulesBased#validateEntityConstraints - Initialisation de l'instance du Validateur");
				
				// Initialisation du Validateur
				validator.initialize(daoAnnotation, getEntityManager(), mode, validationTime);
				
			} catch (Throwable e) {
				
				// Une information
				logger.trace("JPAGenericDAORulesBased#validateEntityConstraints - Erreur lors de la Validation [" + e.getClass().getName() + ", " + e.getMessage() + "]");
				
				// On relance l'exception
				throw new ValidatorInstanciationException(e);
			}

			// Une information
			logger.trace("JPAGenericDAORulesBased#validateEntityConstraints - Execution de la Validation");
			
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
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#buildDefaultValidatorAnnotation");

		// On affecte le mode
		final DAOMode fMode = mode;
		
		// On affecte le moment
		final DAOValidatorEvaluationTime fEvaluationTime = validationTime;
		
		// Si le mode est null
		if(mode == null || mode.equals(DAOMode.DELETE)) {
			
			// Une information
			logger.trace("JPAGenericDAORulesBased#buildDefaultValidatorAnnotation - Mode DELETE ou Pas de Mode: Instanciation de l'annotation");
			
			// Instanciation de l'annotation
			Annotation defaultDeleteAnnotation = new IdentityDAOValidator() {
				
				@Override
				public Class<? extends Annotation> annotationType() {
					
					// On retourne la classe de l'annotation Identite
					return IdentityDAOValidator.class;
				}
			};
			
			// Une information
			logger.trace("JPAGenericDAORulesBased#buildDefaultValidatorAnnotation - Annotation instanciée [" + defaultDeleteAnnotation.annotationType().getName() + "]");
			
			// On retourne l'annotation
			return defaultDeleteAnnotation;
		}
		
		// Si on est en POST_CONDITION
		if(validationTime == null || validationTime.equals(DAOValidatorEvaluationTime.POST_CONDITION)) {
			
			// Une information
			logger.trace("JPAGenericDAORulesBased#buildDefaultValidatorAnnotation - VALIDATION TIME: " + validationTime.toString());
			
			// Instanciation de l'annotation
			Annotation defaultPOSTCONDITIONAnnotation = new IdentityDAOValidator() {
				
				@Override
				public Class<? extends Annotation> annotationType() {
					
					// On retourne la classe de l'annotation Identite
					return IdentityDAOValidator.class;
				}
			};
			
			// Une information
			logger.trace("JPAGenericDAORulesBased#buildDefaultValidatorAnnotation - Annotation instanciée [" + defaultPOSTCONDITIONAnnotation.annotationType().getName() + "]");
			
			// On retourne l'annotation
			return defaultPOSTCONDITIONAnnotation;
		}
		
		// Une information
		logger.trace("JPAGenericDAORulesBased#buildDefaultValidatorAnnotation - Mode SAVE ou UPDATE: Instanciation de l'annotation");
		
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

		// Une information
		logger.trace("JPAGenericDAORulesBased#buildDefaultValidatorAnnotation - Annotation instanciée [" + defaultSaveOrUpdateAnnotation.annotationType().getName() + "]");
		
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
