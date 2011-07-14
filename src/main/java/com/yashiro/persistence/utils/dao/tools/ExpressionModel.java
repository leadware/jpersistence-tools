/**
 * 
 */
package com.yashiro.persistence.utils.dao.tools;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe permettant de stocker les informations sur une Expression (Apres resolution)
 * @author Jean-Jacques
 * @version 2.0
 */
public class ExpressionModel implements Serializable {

	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Expression Originale
	 */
	private String originalExpression;
	
	/**
	 * Expression transformee
	 */
	private String computedExpression;
	
	/**
	 * Parametres de l'expression
	 */
	private Map<String, String> parameters = new HashMap<String, String>();
	
	/**
	 * Constructeur par defaut
	 */
	public ExpressionModel() {}

	/**
	 * Constructeur avec Initialisation de l'expression Originale
	 * @param originalExpression	Expression Originale
	 */
	public ExpressionModel(String originalExpression) {
		this.originalExpression = originalExpression;
		this.computedExpression = originalExpression;
	}

	/**
	 * Methode d'obtention de l'Expression Originale
	 * @return Expression Originale
	 */
	public String getOriginalExpression() {
		return originalExpression;
	}

	/**
	 * Methode de mise a jour de l'Expression Originale
	 * @param originalExpression Expression Originale
	 */
	public void setOriginalExpression(String originalExpression) {
		this.originalExpression = originalExpression;
	}

	/**
	 * Methode d'obtention de l'Expression transformee
	 * @return Expression transformee
	 */
	public String getComputedExpression() {
		return computedExpression;
	}

	/**
	 * Methode de mise a jour de l'Expression transformee
	 * @param computedExpression Expression transformee
	 */
	public void setComputedExpression(String computedExpression) {
		this.computedExpression = computedExpression;
	}

	/**
	 * Methode d'obtention des Parametres de l'expression
	 * @return Parametres de l'expression
	 */
	public Map<String, String> getParameters() {
		return Collections.unmodifiableMap(parameters);
	}
	
	/**
	 * Methode d'ajout d'un Parametre
	 * @param parameterName	Nom du parametre
	 * @param parameterExpression	Expression du parametre
	 */
	public void addParameter(String parameterName, String parameterExpression) {
		
		// Si le nom du parametre est null
		if(parameterName == null || parameterName.trim().length() == 0) return;
		
		// Si l'expression est vide
		if(parameterExpression == null || parameterExpression.trim().length() == 0) return;
		
		// On ajoute le Parametre
		this.parameters.put(parameterName, parameterExpression);
	}
}
