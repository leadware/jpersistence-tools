package com.yashiro.persistence.utils.dao;

import java.util.List;
import javax.persistence.EntityManager;
import com.yashiro.persistence.utils.dao.tools.AliasesContainer;
import com.yashiro.persistence.utils.dao.tools.LoaderModeContainer;
import com.yashiro.persistence.utils.dao.tools.OrderContainer;
import com.yashiro.persistence.utils.dao.tools.PropertyContainer;
import com.yashiro.persistence.utils.dao.tools.RestrictionsContainer;
import com.yashiro.persistence.utils.dao.tools.SaveListResult;

/**
 * Interface de la DAO Generique compatible JPA
 * @author Jean-Jacques
 * @version 1.0
 */
public interface IJPAGenericDAO {
	
	/**
	 * Nom du Service DAO
	 */
	public static final String SERVICE_NAME = "JPAGenericDAO";
	
	/**
	 * Methode generique d'enregistrement d'une entite JPA annotee
	 * @param <T>	Parametre de type generique
	 * @param entity	Entite a enregistrer
	 * @return	Entite enregistree
	 */
	public <T extends Object> T save(T entity);
	
	/**
	 * Methode d'enregistrement d'une liste d'entites
	 * @param <T>	Parametre de type
	 * @param entities	Liste des Entites a enregistrer
	 * @param rollbackOnException	Etat d'annulation de toutes 
	 * 		  les operations en cas d'exception 
	 * @return	Resultat de lappel de la methode d'enregistrement en cascade
	 */
	public <T extends Object> SaveListResult<T> saveList(List<T> entities, boolean rollbackOnException);
	
	/**
	 * Methode generique de mise a jour d'une entite JPA annotee
	 * @param <T>	Parametre de type generique
	 * @param entity	Entite a mettre a jour
	 * @return	Entite mise a jour
	 */
	public <T extends Object> T update(T entity);
	
	/**
	 * Methode generique de suppression d'une entite JPA annotee
	 * @param <T>	Parametre de type generique
	 * @param entity	Entite a supprimer
	 */
	public <T extends Object> void delete(T entity);
	
	/**
	 * ethode de suppression d'une liste d'entites
	 * @param <T>	Parametre de type
	 * @param entities	Liste des entites a supprimer
	 * @return Liste des entites non supprimees
	 */
	public <T extends Object> List<T> deleteList(List<T> entities, boolean rollbackOnException);
	
	/**
	 * Methode de nettoyage d'une table
	 * @param <T>	Parametre de type
	 * @param entityClass	Classe a nettoyer
	 * @return	Liste des entites non supprimees
	 */
	public <T extends Object> List<T>  clean(Class<T> entityClass);
	
	/**
	 * Methode de filtre des entites d'une classe donnee en fonction des criteres de filtres donnees
	 * @param <T>	Parametre de Type
	 * @param entityClass	Classe des Objets a filtrer
	 * @param alias	Conteneur d'alias
	 * @param restrictions	Conteneur de restrictions
	 * @param orders	Conteneur d'ordes de tri
	 * @param loaderModes	Conteneur de mode de chargement par proprietes
	 * @param firstResult	Index du premier resultat retourne
	 * @param maxResult	Nombre maximum d'elements retournes
	 * @return	Liste des objet trouves
	 */
	public <T extends Object> List<T> filter(Class<T> entityClass, AliasesContainer alias, RestrictionsContainer restrictions, OrderContainer orders, LoaderModeContainer loaderModes, int firstResult, int maxResult);
	
	/**
	 * Methode de filtre des entites d'une classe donnee en fonction des criteres de filtres donnees
	 * @param <T> Parametre de type sur la classe de la requete
	 * @param <U>	Parametre de type sur la colonne a selectionner
	 * @param entityClass	Classe des Objets a filtrer
	 * @param selectedColumnClass	Classe de la colonne a selectionner
	 * @param selectedColumName	Nom de la colonne a selectionner
	 * @param alias	Conteneur d'alias
	 * @param restrictions	Conteneur de restrictions
	 * @param orders	Conteneur d'ordes de tri
	 * @param loaderModes	Conteneur de mode de chargement par proprietes
	 * @param firstResult	Index du premier resultat retourne
	 * @param maxResult	Nombre maximum d'elements retournes
	 * @return	Liste des objet trouves
	 */
	public <T, U> List<U> filter(Class<T> entityClass, Class<U> selectedColumnClass, String selectedColumName, AliasesContainer alias, RestrictionsContainer restrictions, OrderContainer orders, LoaderModeContainer loaderModes, int firstResult, int maxResult);
	
	/**
	 * Methode de chargement immediat des proprietes d'une instance de classe
	 * @param <T>	Parametre de type
	 * @param entityClass	Classe de l'entite cible
	 * @param entityID	ID de l'instance de l'entite
	 * @param properties	Conteneur de proprietes a charger
	 * @return	Instance de la classe avec les proprietes charges
	 */
	public <T extends Object> T findByPrimaryKey(Class<T> entityClass, Object entityID, PropertyContainer properties);
		
	/**
	 * Methode d'obtention du gestionnaire d'entites
	 * @return	Gestionnaire d'entites
	 */
	public EntityManager getEntityManager();
}