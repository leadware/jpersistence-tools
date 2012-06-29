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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;

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
	 * Methode generique de mise a jour d'une entite JPA annotee
	 * @param <T>	Parametre de type generique
	 * @param entity	Entite a mettre a jour
	 * @return	Entite mise a jour
	 */
	public <T extends Object> T update(T entity);
	
	/**
	 * Methode generique de suppression d'une entite JPA annotee
	 * @param <T>	Parametre de type generique
	 * @param entityClass	Classe de l'etité à supprimer
	 * @param entityID	Identifiant de l'entité à supprimer
	 */
	public <T extends Object> void delete(Class<T> entityClass, Object entityID);
	
	/**
	 * Methode de nettoyage d'une table
	 * @param <T>	Parametre de type
	 * @param entityClass	Classe a nettoyer
	 */
	public <T> void clean(Class<T> entityClass);
	
	/**
	 * Methode de filtre des entites d'une classe donnee en fonction des criteres de filtres donnees
	 * @param <T>	Parametre de Type
	 * @param entityClass	Classe des Objets a filtrer
	 * @param predicates	Liste des prédicats
	 * @param orders	Liste des Ordre de tri
	 * @param properties	Ensemble de propriétés à charger
	 * @param firstResult	Index du premier resultat retourne
	 * @param maxResult	Nombre maximum d'elements retournes
	 * @return	Liste des objet trouves
	 */
	public <T extends Object> List<T> filter(Class<T> entityClass, List<Predicate> predicates, List<Order> orders, Set<String> properties, int firstResult, int maxResult);
	
	/**
	 * Methode de chargement immediat des proprietes d'une instance de classe
	 * @param <T>	Parametre de type
	 * @param entityClass	Classe de l'entite cible
	 * @param entityIDName Nom de la propriété ID de l'entité
	 * @param entityID	ID de l'instance de l'entite
	 * @param properties	Ensemble de proprietes a charger
	 * @return	Instance de la classe avec les proprietes charges
	 */
	public <T extends Object> T findByPrimaryKey(Class<T> entityClass, String entityIDName, Object entityID, HashSet<String>  properties);
		
	/**
	 * Methode d'obtention du gestionnaire d'entites
	 * @return	Gestionnaire d'entites
	 */
	public EntityManager getEntityManager();
}