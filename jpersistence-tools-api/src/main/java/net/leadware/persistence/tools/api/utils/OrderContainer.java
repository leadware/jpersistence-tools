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
package net.leadware.persistence.tools.api.utils;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.leadware.persistence.tools.api.dao.constants.OrderType;


/**
 * Classe representant un conteneur d'ordre de tri des resultats
 * @author Jean-Jacques ETUNÈ NGI
 */
public class OrderContainer implements Serializable {
	
	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Liste des ordre de tri
	 */
	private Map<String, OrderType> orders = new HashMap<String, OrderType>();
	
	/**
	 * Obtention de l'instance
	 * @return	Instance de travail
	 */
	public static OrderContainer newInstance() {
		
		// On retourne l'instance
		return new OrderContainer();
	}
		
	/**
	 * Methode d'ajout d'un ordre de tri
	 * @param order	Ordre de tri a ajouter
	 * @return	Conteneur d'ordre de tri
	 */
	public OrderContainer add(String property, OrderType orderType) {
		
		// Si la ppt est nulle
		if(property == null || property.trim().length() == 0) return this;
		
		// Si le Type d'ordre est null
		if(orderType == null) return this;
		
		// Ajout
		orders.put(property.trim(), orderType);
		
		// On retourne le conteneur
		return this;
	}

	/**
	 * Methode d'obtention de la Map des ordre de tri
	 * @return Map des ordre de tri
	 */
	public Map<String, OrderType> getOrders() {
		return Collections.unmodifiableMap(orders);
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
