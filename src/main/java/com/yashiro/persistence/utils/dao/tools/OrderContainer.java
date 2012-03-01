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
package com.yashiro.persistence.utils.dao.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.criterion.Order;

/**
 * Classe representant un conteneur d'ordre de tri des resultats
 * @author Jean-Jacques
 * @version 1.0
 */
public class OrderContainer implements Serializable {

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Liste des ordre de tri
	 */
	private List<Order> orders = new ArrayList<Order>();
	
	/**
	 * Obtention de l'instance
	 * @return	Instance de travail
	 */
	public static synchronized final OrderContainer getInstance() {
		
		// On retourne l'instance
		return new OrderContainer();
	}
		
	/**
	 * Methode d'ajout d'un ordre de tri
	 * @param order	Ordre de tri a ajouter
	 * @return	Conteneur d'ordre de tri
	 */
	public OrderContainer add(Order order) {
		
		// Si l'ordre n'est pas null
		if(order != null) orders.add(order);
		
		// On retourne le conteneur
		return this;
	}

	/**
	 * Methode d'obtention de la Liste des ordre de tri
	 * @return Liste des ordre de tri
	 */
	public List<Order> getOrders() {
		return Collections.unmodifiableList(orders);
	}
	
	/**
	 * Methode d'obtention de la taille du conteneur
	 * @return	Taille du conteneur
	 */
	public int size() {
		
		// On retourne la taille de la collection
		return this.orders.size();
	}

	/**
	 * Methode de vidage du conteneur
	 */
	public void clear() {
		
		// Si la collection est non nulle
		if(orders != null) orders.clear();
	}
}
