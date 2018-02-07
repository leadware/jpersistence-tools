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
package net.leadware.persistence.tools.api.exceptions;


/**
 * Classe representant une exception levee par La validation 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 31 janv. 2018 - 14:22:52
 */
public class InvalidEntityInstanceStateException extends DAOValidationException {
	
	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Nom de l'entité en echec
	 */
	private String entityName;
	
	/**
	 * Nom de la propriété en echec
	 */
	private String propertyName;
	
	/**
	 * Constructeur avec initialisation des parametres
	 * @param entityName Nom de l'entite
	 * @param propertyName Nom de la propriete
	 * @param message	Message de l'exception
	 */
	public InvalidEntityInstanceStateException(String entityName, String propertyName, String message) {
		
		// Initialisation du Parent
		super(message);
		
		// Positionnement des propriétés
		this.entityName = entityName;
		this.propertyName = propertyName;
	}

	/**
	 * Constructeur avec initialisation des parametres
	 * @param entityName Nom de l'entite
	 * @param propertyName Nom de la propriete
	 * @param message	Message de l'exception
	 * @param parameters Parametres du message
	 */
	public InvalidEntityInstanceStateException(String entityName, String propertyName, String message, String[] parameters) {
		
		// Initialisation du Parent
		this(entityName, propertyName, message);
		
		// Positionnement de la propriete parameters
		this.setParameters(parameters);
	}
	
	/**
	 * Méthode d'obtention du nom de l'entité en echec
	 * @return Nom de l'entité en echec
	 */
	public String getEntityName() {
		return this.entityName;
	}

	/**
	 * Méthode d'obtention du nom de la propriété en echec
	 * @return Nom de la propriété en echec
	 */
	public String getPropertyName() {
		return this.propertyName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	public String toString() {
		
		// StringBuilder
		StringBuilder stringBuilder = new StringBuilder();

		// Ajout du Nom de la classe d'Eception
		stringBuilder.append("InvalidEntityInstanceStateException ");

		// Ajout du l'accolade ouvrante
		stringBuilder.append("[");
		
		// Ajout de l'entité
		stringBuilder.append("ENTITY NAME: " + entityName);
		
		// Ajout du Nom de la ppt
		stringBuilder.append(", PROPERTY NAME: " + propertyName);

		// Ajout du Nom du Message
		stringBuilder.append(", MESSAGE: " + getMessage());
		
		// Ajout du l'accolade fermante
		stringBuilder.append("]");
		
		// On retourne la chaine
		return stringBuilder.toString();
	}
}
