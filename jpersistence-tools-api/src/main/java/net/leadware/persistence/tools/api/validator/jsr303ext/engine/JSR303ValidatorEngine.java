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
package net.leadware.persistence.tools.api.validator.jsr303ext.engine;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.ConstraintDescriptor;

import net.leadware.persistence.tools.api.exceptions.InvalidEntityInstanceStateException;
import net.leadware.persistence.tools.api.validator.jsr303ext.annotations.AuthorizedValues;
import net.leadware.persistence.tools.api.validator.jsr303ext.annotations.Interval;
import net.leadware.persistence.tools.api.validator.jsr303ext.annotations.Length;

/**
 * Classe permettant d'effectuer la validation d'instance manuellement 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 31 janv. 2018 - 14:33:07
 */
public class JSR303ValidatorEngine {
	
	/**
	 * Fabrique de Validateur JSR 303
	 */
	private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
	
	/**
	 * Validateur JSR 303
	 */
	private Validator validator = validatorFactory.getValidator();
	
	/**
	 * Instance de moteur de Validation
	 */
	private static JSR303ValidatorEngine _instance = null;
	
	/**
	 * Constructeur par defaut
	 */
	private JSR303ValidatorEngine() {}
	
	/**
	 * Méthode d'obtention de l'instance de moteur de Validation
	 * @return Instance de moteur de Validation
	 */
	public static synchronized JSR303ValidatorEngine getDefaultInstance() {
		
		// Si l'instance est nulle
		if(_instance == null) _instance = new JSR303ValidatorEngine();
		
		// On retourne l'instance
		return _instance;
	}

	/**
	 * Méthode d'obtention de l'instance de moteur de Validation
	 * @param validatorFactory Fabrique de valudateurs
	 * @return Instance de moteur de Validation
	 */
	public static synchronized JSR303ValidatorEngine getInstance(ValidatorFactory validatorFactory) {
		
		// Si l'instance est nulle
		if(_instance == null) _instance = new JSR303ValidatorEngine();
		
		// On positionne la fabrique de validateur
		_instance.setValidatorFactory(validatorFactory);
		
		// On retourne l'instance
		return _instance;
	}
	
	/**
	 * Méthode de mise à jour de la fabrique de validateur
	 * @param validatorFactory Fabrique de validateur
	 */
	public void setValidatorFactory(ValidatorFactory validatorFactory) {
		
		// Si le paramètre n'est pas null
		if(validatorFactory != null)  {
			
			// On positionne la fabrique
			this.validatorFactory = validatorFactory;
			
			// On construit le validateur
			this.validator = validatorFactory.getValidator();
		}
	}
	
	/**
	 * Methode permettant de valider un onjet
	 * @param entity	Entité  valider
	 * @param <T> Type de l'entite
	 */
	public <T> void validate(T entity) {
		
		// Obtention de l'ensemble des violations
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
		
		// Si l'ensemble est vide
		if(constraintViolations == null || constraintViolations.size() == 0) return;

		// Obtention de la première violation
		ConstraintViolation<T> first = constraintViolations.iterator().next();
		
		// Obtention du descripteur de la contrainte
		ConstraintDescriptor<?> constraintDescriptor = first.getConstraintDescriptor();
		
		// Nom du bean en echec
		String entityName = first.getRootBeanClass().getSimpleName();
		
		// Nom de la propriété en echec
		String propertyName = first.getPropertyPath().toString();
		
		// Message de violation
		String message = first.getMessage();
		
		// Obtention de l'annotation en cours
		String[] parameters = buildAnnotationConstraintParameters(constraintDescriptor.getAnnotation());
		
		// On leve une Exception
		throw new InvalidEntityInstanceStateException(entityName, propertyName, message, parameters);
	}
	
	/**
	 * Methode permettant d'obtenir la liste des parametres d'une annotation de validation de contraintes 
	 * @param annotation	Annotation courante
	 * @return	Tableau des parametres
	 */
	protected String[] buildAnnotationConstraintParameters(Annotation annotation) {
		
		// Si l'annotation est nulle
		if(annotation == null) return null;
		
		// Si l'annotation est de type @AuthorizedValues
		if(annotation instanceof AuthorizedValues) return new String[] { Arrays.toString(((AuthorizedValues) annotation).values()) };
		
		// Si l'annotation est de type @Interval
		if(annotation instanceof Interval) return new String[] { Double.toString(((Interval) annotation).min()), Double.toString(((Interval) annotation).max()) };
		
		// Si l'annotation est de type @Length
		if(annotation instanceof Length) return new String[] { Integer.toString(((Length) annotation).min()), Integer.toString(((Length) annotation).max()) };
		
		// valeur par defaut
		return null;
	}
}
