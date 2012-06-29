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
package com.bulk.persistence.tools.validator.base;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.bulk.persistence.tools.dao.api.constants.DAOMode;
import com.bulk.persistence.tools.dao.api.constants.DAOValidatorEvaluationTime;
import com.bulk.persistence.tools.dao.api.constants.ValidatorExpressionType;
import com.bulk.persistence.tools.dao.utils.DAOValidatorHelper;
import com.bulk.persistence.tools.dao.utils.ExpressionModel;
import com.bulk.persistence.tools.validator.IDAOValidator;

/**
 * Classe abstraite definissant les bases de l'implementation d'une regle
 * @author Jean-Jacques ETUNÈ NGI
 */
public abstract class AbstractExpressionBasedDAOValidatorRule implements IDAOValidator<Annotation> {
	
	/**
	 * Le gestionnaire d'entites
	 */
	protected EntityManager entityManager;
	
	/**
	 * L'annotation en cours
	 */
	protected Annotation annotation;
	
	/**
	 * Modele de l'expression en parametre
	 */
	protected ExpressionModel expressionModel;
	
	/**
	 * Temps d'evaluation systeme
	 */
	protected DAOValidatorEvaluationTime systemEvaluationTime;
	
	/**
	 * Mode DAO du systeme
	 */
	protected DAOMode systemDAOMode;
			
	/**
	 * Methode permettant d'obtenir l'expression
	 * @return	Expression de l'annotation
	 */
	protected abstract String getExpression();
	
	/**
	 * Methode permettant d'obtenir le message a afficher en cas de violation de contrainte
	 * @return	Message a afficher en cas de violation de contrainte
	 */
	protected abstract String getMessage();
	
	/**
	 * Methode permettant d'obtenir le type de regle
	 * @return	Type de regle
	 */
	protected abstract ValidatorExpressionType getType();
	
	/**
	 * Methode permettant d'obtenir le mode DAO de l'annotation
	 * @return Mode DAO de l'annotation
	 */
	protected abstract DAOMode[] getAnnotationMode();
	
	/**
	 * Methode permettant d'obtenir l'instant d'evaluation de l'annotation
	 * @return	Instant d'evaluation de l'annotation
	 */
	protected abstract DAOValidatorEvaluationTime[] getAnnotationEvaluationTime();
	
	@Override
	public void initialize(Annotation annotation, EntityManager entityManager, DAOMode systemMode, DAOValidatorEvaluationTime systemEvaluationTime) {
		
		// Sauvegarde des parametres
		this.annotation = annotation;
		this.entityManager = entityManager;
		this.systemDAOMode = systemMode;
		this.systemEvaluationTime = systemEvaluationTime;
		
		// Obtention de l'expression
		String expression = getExpression();
		
		// Obtention du Type
		expressionModel = DAOValidatorHelper.computeExpression(expression);
	}
	
	/**
	 * Methode de construction de la requete
	 * @return	Requete
	 */
	protected Query buildQuery(Object target) {
		
		// Si le modele est null
		if(expressionModel == null) return null;
		
		// Instanciation de la requete
		Query query = this.entityManager.createQuery(expressionModel.getComputedExpression());
		
		// MAP des parametres
		Map<String, String> parameters = expressionModel.getParameters();
		
		// Si la MAP n'est pas vide
		if(parameters != null && parameters.size() > 0) {
			
			// Ensemble des cles
			Set<String> keys = parameters.keySet();
			
			// Parcours de l'ensemble des cles
			for (String key : keys) {
				
				// Ajout du parametre
				query.setParameter(key, DAOValidatorHelper.evaluateValueExpression(parameters.get(key), target));
			}
		}
		
		// On retourne la requete
		return query;
	}
	
	/**
	 * methode permettant de tester si l'annotation doit-etre executee
	 * @return	Etat d'execution de l'annotation
	 */
	protected boolean isProcessable() {
		
		// Comparaison des modes
		boolean correctMode = DAOValidatorHelper.arraryContains(getAnnotationMode(), this.systemDAOMode);
		
		// Comparaison des instants d'evaluation
		boolean correctTime = DAOValidatorHelper.arraryContains(getAnnotationEvaluationTime(), this.systemEvaluationTime);
		
		// On retourne la comparaison des deux
		return correctMode && correctTime;
	}
	
	@Override
	public Object[] getMessageParameters(Object entity) {
		
		// Obtention des expression de parametres
		String[] parametersExpressions = getMessageParametersExpressions();
		
		// Si la liste est vide
		if(parametersExpressions == null || parametersExpressions.length == 0) return null;
		
		// Liste des Parametres
		ArrayList<Object> lParameters = new ArrayList<Object>();
		
		// Modele d'expression
		ExpressionModel expressionModel = null;
		
		// Parcours
		LBLEXPR : for (String expr : parametersExpressions) {
			
			try {
				
				// Resolution de l'expression
				expressionModel = DAOValidatorHelper.computeExpression(expr);
				
				// MAP des parametres
				Map<String, String> exprParameters = expressionModel.getParameters();
				
				// Si la MAP est vide
				if(exprParameters == null || exprParameters.size() == 0) continue LBLEXPR;
				
				// Ensemble des cles
				Set<String> keySet = exprParameters.keySet();
				
				// Parcours
				for (String key : keySet) {
					
					try {

						// Ajout
						lParameters.add(DAOValidatorHelper.evaluateValueExpression(exprParameters.get(key), entity));
						
					} catch (Exception e) {
						
						// On ajoute la cle
						lParameters.add("[" + key + "]");
					}
				}
				
			} catch (Exception e) {
				
				// On ajoute la cle
				lParameters.add("[" + expr + "]");
			}
			
		}
		
		// On retourne la liste des parametres
		return lParameters.toArray();
	}
	
	/**
	 * Methode permettant d'obtenir la liste des expressions de messages
	 * @return	liste des expressions de messages
	 */
	protected String[] getMessageParametersExpressions() {
		
		// On retourne le vide
		return null;
	}
}
