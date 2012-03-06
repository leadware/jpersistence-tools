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
package com.bulksoft.persistence.utils.dao.tools;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.el.ValueExpression;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bulksoft.persistence.utils.annotations.Parameter;
import com.bulksoft.persistence.utils.annotations.validator.DAOValidatorRule;
import com.bulksoft.persistence.utils.annotations.validator.engine.IDAOValidator;

import de.odysseus.el.ExpressionFactoryImpl;

/**
 * Classe d'aide pour les annotations DAO
 * @author Jean-Jacques
 * @version 2.0
 */
public class DAOValidatorHelper {
	
	/**
	 *  Un Logger
	 */
	private static Log logger = LogFactory.getLog(DAOValidatorHelper.class);


	/**
	 * Delimiteur gauche Simple
	 */
	public static String SIMPLE_LEFT_DELIMITER = "${";
	
	/**
	 * Delimiteur droit simple
	 */
	public static String SIMPLE_RIGHT_DELIMITER = "}";
	
	/**
	 * Delimiteurs de variables d'environnement
	 */
	public static String ENV_LEFT_DELIMITER = "\\$\\{";
	public static String ENV_OPEN = "\\{";
	public static String ENV_CLOSE = "\\}";
	public static String ENV = "[\\w||\\.]+";
	public static String ENV_CHAIN_PATTERN = ENV_LEFT_DELIMITER + ENV + ENV_CLOSE;
    
    /**
     * Delimiteurs des fonction
     */
	public static String FUNCTION_LEFT_DELIMITER = "\\$F_";
	public static String FUNCTION_NAME = "\\w+";
	public static String FUNCTION_OPEN = "\\(";
	public static String FUNCTION_CLOSE = "\\)";
	public static String FUNCTION_PARAMETER = "[\\w|\\W|\\.]+";
	public static String SPLITTER_CHAIN = " |=|,|;|:|<|>|!|\\?|\\*|\\+|/|-|%|\\)|\\(";
	public static String SIMPLE_FUNCTION_LEFT_DELIMITER = "$F_";
	public static String SIMPLE_FUNCTION_OPEN = "(";
	public static String SIMPLE_FUNCTION_CLOSE = ")";
    
    /**
     * Pattern des chaines contenant des fonctions
     */
	public static String FUNC_CHAIN_PATTERN = FUNCTION_LEFT_DELIMITER + FUNCTION_NAME + FUNCTION_OPEN + FUNCTION_PARAMETER + FUNCTION_CLOSE;
	
	/**
	 * Methode permettant de verifier qu'un objet est un parametre
	 * @param entity	Entite a verifier
	 * @return	Etat de parametrage
	 */
	public static final boolean isParameter(Object entity) {
		
		// Si l'Objet est null
		if(entity == null) return false;

		// On obtient la classe
		Class<?> entityClass = entity.getClass();
		
		// Si l'Objet est une instance de classe
		if(entity instanceof Class<?>) entityClass = (Class<?>) entity;
		
		// Recherche du marqueur
		Parameter marqueur = entityClass.getAnnotation(Parameter.class);
		
		// On retourne l'etat
		return marqueur != null;
	}
	
	/**
	 * Methode permettant de verifier si une annotation est
	 * compatible avec le Framework JPersistenceUtils
	 * @param annotation	Annotation a controler
	 * @return	Etat d'appartenance
	 */
	public static boolean isDAOValidatorAnnotation(Annotation annotation) {
		
		// Un Log
		logger.debug("DAOValidatorHelper#isDAOValidatorAnnotation");
		
		// Si lm'annotation est nulle
		if(annotation == null) {
			
			// Un Log
			logger.debug("DAOValidatorHelper#isDAOValidatorAnnotation - Annotation nulle");
			
			// On retourne false
			return false;
		}
		
		// Un Log
		logger.debug("DAOValidatorHelper#isDAOValidatorAnnotation - Obtention de la classe de l'annotation");
		
		// Obtention de la classe de cette annotation
		Class<?> annotationClass = annotation.annotationType();
		
		// Un Log
		logger.debug("DAOValidatorHelper#isDAOValidatorAnnotation - Classe de l'annotation [" + annotationClass.getName() + "]");
		
		// Un Log
		logger.debug("DAOValidatorHelper#isDAOValidatorAnnotation - Recherche de l'annotation de reference du Framework [" + DAOValidatorRule.class.getName() + "]");
		
		// Recherchje de l'annotation de validation
		DAOValidatorRule logicAnnotation = annotationClass.getAnnotation(DAOValidatorRule.class);

		// Un Log
		logger.debug("DAOValidatorHelper#isDAOValidatorAnnotation [" + (logicAnnotation != null) + "]");
		
		// On retourne le resultat
		return logicAnnotation != null;
	}
	
	/**
	 * Methode permettant de charger toutes les annotations DAO sur l'objet pour un mode donne et un temps d'evaluation donne
	 * @param object	Objet a inspecter
	 * @return	Liste des annotations DAO retrouvees
	 */
	public static List<Annotation> loadDAOValidatorAnnotations(Object object) {
		
		// Un Log
		logger.debug("DAOValidatorHelper#loadDAOValidatorAnnotations");
		
		// Liste des annotations retrouvees
		List<Annotation> daoAnnotations = new ArrayList<Annotation>();
		
		// Si l'objet est null
		if(object == null) {
			
			// Un Log
			logger.debug("DAOValidatorHelper#loadDAOValidatorAnnotations - L'objet a inpecter est null");
			
			// On retourne une liste vide
			return daoAnnotations;
		}
		
		// Un Log
		logger.debug("DAOValidatorHelper#loadDAOValidatorAnnotations - Obtention de toutes les annotations de l'objet");
		
		// Obtention des annotations de la classe
		Annotation[] objectAnnotations = object.getClass().getAnnotations();
		
		// Si le tableau est vide
		if(objectAnnotations == null || objectAnnotations.length == 0) {
			
			// Un Log
			logger.debug("DAOValidatorHelper#loadDAOValidatorAnnotations - L'objet n'est pas annote");
			
			// On retourne une liste vide
			return daoAnnotations;
		}
		
		// Un Log
		logger.debug("DAOValidatorHelper#loadDAOValidatorAnnotations - Parcours de la liste de ses annotations");
		
		// Parcours
		for (Annotation annotation : objectAnnotations) {
			
			// Si c'est une annotation du Framework
			if(isDAOValidatorAnnotation(annotation)) {

				// Un Log
				logger.debug("DAOValidatorHelper#loadDAOValidatorAnnotations - Ajout de l'annotation");
				
				// On ajoute l'annotation
				daoAnnotations.add(annotation);
			}
		}
		
		// Un Log
		logger.debug("DAOValidatorHelper#loadDAOValidatorAnnotations - On retourne la liste");
		
		// On retourne la liste
		return daoAnnotations;
	}
	
	/**
	 * Methode permettant de charger toutes les Classes de validation de l'Objet en fonction du Mode
	 * @param object	Objet a inspecter
	 * @return	Liste des classes d'implementation
	 */
	public static List<Class<? extends IDAOValidator<? extends Annotation>>> loadDAOValidatorClass(Object object) {
		
		// Un Log
		logger.debug("DAOValidatorHelper#loadDAOValidatorClass");
		
		// Liste de classes de validation retrouvees
		List<Class<? extends IDAOValidator<? extends Annotation>>> result = new ArrayList<Class<? extends IDAOValidator<? extends Annotation>>>();
		
		// Si l'objet est null
		if(object == null) {
			
			// Un Log
			logger.debug("DAOValidatorHelper#loadDAOValidatorClass - L'objet a inspecter est null");
			
			// On retourne une liste vide
			return result;
		}

		// Un Log
		logger.debug("DAOValidatorHelper#loadDAOValidatorClass - Obtention de toutes les annotations de l'objet");
		
		// Obtention des annotations de la classe
		Annotation[] objectAnnotations = object.getClass().getAnnotations();
		
		// Si le tableau est vide
		if(objectAnnotations == null || objectAnnotations.length == 0) {

			// Un Log
			logger.debug("DAOValidatorHelper#loadDAOValidatorClass - L'objet n'est pas annote");
			
			// On retourne une liste vide
			return result;
		}

		// Un Log
		logger.debug("DAOValidatorHelper#loadDAOValidatorClass - Parcours de la liste de ses annotations");
		
		// Parcours
		for (Annotation annotation : objectAnnotations) {
			
			// Si c'est une annotation du Framework
			if(isDAOValidatorAnnotation(annotation)) {
				
				// Un Log
				logger.debug("DAOValidatorHelper#loadDAOValidatorClass - Annotation du Framework [" + annotation.getClass().getName() + "]");
				
				// Obtention de l'annotation de validfation
				DAOValidatorRule daoAnnotation = annotation.annotationType().getAnnotation(DAOValidatorRule.class);

				// Un Log
				logger.debug("DAOValidatorHelper#loadDAOValidatorClass - Ajout de la classe d'implementation de l'annotation");
				
				// On ajoute l'annotation
				result.add(daoAnnotation.logicClass());
			}
		}
		
		// Un Log
		logger.debug("DAOValidatorHelper#loadDAOValidatorClass - On retourne la liste");
		
		// On retourne la liste
		return result;
		
	}
	
	/**
	 * Methode permettant d'obtenir la classe d'implementation de la 
	 * logique de validation parametree sur l'annotation
	 * @param annotation	Annotation a inspecter
	 * @return	Class d'implementation de la logique de validation
	 */
	public static Class<? extends IDAOValidator<? extends Annotation>> getValidationLogicClass(Annotation annotation) {
		
		// Un Log
		logger.debug("DAOValidatorHelper#getValidationLogicClass");
		
		// Si l'annotation est nulle
		if(annotation == null) {

			// Un Log
			logger.debug("DAOValidatorHelper#getValidationLogicClass - Annotation nulle");
			
			// On retourne null
			return null;
		}
		
		// Un Log
		logger.debug("DAOValidatorHelper#getValidationLogicClass - Obtention de l'annotation de Reference");
		
		// Obtention de l'annotation DAO
		DAOValidatorRule logicAnnotation = annotation.annotationType().getAnnotation(DAOValidatorRule.class);
		
		// Un Log
		logger.debug("DAOValidatorHelper#getValidationLogicClass - L'annotation de validation est-elle Nulle? : [" + (logicAnnotation == null) + "]");
		
		// Un Log
		logger.debug("DAOValidatorHelper#getValidationLogicClass - Classe de regle: [" + logicAnnotation.logicClass().getName() + "]");
		
		// On retourne cette annotation
		return logicAnnotation.logicClass();
	}
	
	/**
	 * Methode permettant de savoir si un Objet de type T est contenu dans un Tableau d'objet de type T
	 * @param array	Tableau d'objet d'un type T
	 * @param value	Objet d'un Type T
	 * @return	Etat de presence
	 */
	public static <T extends Object> boolean arraryContains(T[] array, T value) {
		
		// Un log
		logger.debug("DAOValidatorHelper#arraryContains");
		
		// Si le tableau est vide
		if(array == null || array.length == 0) {
			
			// Un log
			logger.debug("DAOValidatorHelper#arraryContains - Empty Array");
			
			// On retourne false
			return false;
		}
		
		// Si le mode est vide
		if(value == null) {
			
			// Un log
			logger.debug("DAOValidatorHelper#arraryContains - Empty Value");
			
			// On retourne false
			return false;
		}
		
		// Un Log
		logger.debug("DAOValidatorHelper#arraryContains - Value: " + value.toString());
				
		// Mode dedans
		boolean modeIn = false;
		
		// Index du Tableau
		int index = 0;
		
		// Parcours
		while(index < array.length && !modeIn) {
			
			// Valeur du Tableau
			T tValue = array[index++];
			
			// On affiche les valeurs a comparer
			logger.debug("DAOValidatorHelper#arraryContains - Value: " + value.toString() + ", ArrayValue: " + tValue.toString());
			
			modeIn = tValue.equals(value);
		}
		
		// On retourne false
		return modeIn;
	}
	
	/**
	 * Methode permettant de verifier si un chemin contient des variables d'environnement
	 * @param expression	Chaine a controler
	 * @return	Resultat de la verification
	 */
	public static boolean isExpressionContainsENV(String expression) {
		
		// Un Log
		logger.debug("DAOValidatorHelper#isExpressionContainsENV");
		
		// Si la chaine est vide : false
		if(expression == null || expression.trim().length() == 0) {
			
			// Un Log
			logger.debug("DAOValidatorHelper#isExpressionContainsENV - La chaene est vide");
			
			// On retourne false
			return false;
		}
		
		// On split
		return isExpressionContainPattern(expression, ENV_CHAIN_PATTERN);
	}
	
	/**
	 * Methode permettant d'obtenir la liste des sous-chaines representant des ENV
	 * @param expression Chaine a scruter
	 * @return	Liste des ENVs
	 */
	public static String[] getENVTokens(String expression) {
		
		// Un Log
		logger.debug("DAOValidatorHelper#getFunctionTokens");
		
		// Un log
		logger.debug("DAOValidatorHelper#getFunctionTokens - Chaene : " + expression);
		
		// On retourne le tableau
		return extractToken(expression, ENV_CHAIN_PATTERN);
	}
	
	/**
	 * Methode permettant de resoudre les variables d'environnement dans une chemin
	 * @param expression	Expression du chemin
	 * @return	Expression resolue
	 */
	public static String resolveEnvironmentsParameters(String expression) {

		// Un Log
		logger.debug("DAOValidatorHelper#resolveEnvironmentsParameters");
		
		// Si l'expression est vide
		if(expression == null || expression.trim().length() == 0) {
			
			// Un Log
			logger.debug("DAOValidatorHelper#resolveEnvironmentsParameters - Expression vide");
			
			// On retourne null
			return null;
		}
		
		// Un Log
		logger.debug("DAOValidatorHelper#resolveEnvironmentsParameters - Expression [" + expression + "]");
		
		// Tant que la chaene traitee contient des ENVs
		while(isExpressionContainPattern(expression, ENV_CHAIN_PATTERN)) {

			// Un Log
			logger.debug("DAOValidatorHelper#resolveEnvironmentsParameters - Expression contenant des ENVs: Extraction des ENVs...");
			
			// Obtention de la liste des ENVs
			String[] envs = extractToken(expression, ENV_CHAIN_PATTERN);

			// Un Log
			logger.debug("DAOValidatorHelper#resolveEnvironmentsParameters - Nombre de ENVs: " + envs.length);
			
			// Parcours
			for (String env : envs) {
				
				String cleanEnv = env.replace("${", "");
				cleanEnv = cleanEnv.replace("}", "");
				
				// Un Log
				logger.debug("DAOValidatorHelper#resolveEnvironmentsParameters - ENV Courant [" + cleanEnv + "]");
				
				// On remplace l'occurence courante par le nom de la variable
				expression = expression.replace(env, System.getProperty(cleanEnv));
			}
		}

		// Un Log
		logger.debug("DAOValidatorHelper#resolveEnvironmentsParameters - Expression resolue: " + expression);
		
		// On retourne l'expression
		return expression;
	}
	
	/**
	 * Methode de resolution d'une Expression
	 * @param expression	Expression a transformer
	 * @return	Modele de l'expression transformee
	 */
	public static ExpressionModel computeExpression(String expression) {
		
		// Un Log
		logger.debug("DAOValidatorHelper#computeExpression");
		
		// Si l'expression est vide
		if(expression == null || expression.trim().length() == 0) {
			
			// Un Log
			logger.debug("DAOValidatorHelper#computeExpression - Expression vide");
			
			// On retourne null
			return null;
		}
		
		// Un Log
		logger.debug("DAOValidatorHelper#computeExpression - Expression [" + expression + "]");
		
		// On Instancie un model d'expression
		ExpressionModel expressionModel = new ExpressionModel(expression.trim());
		
		// Index de l'iteration
		int i = 0;
		
		// Si la chaine contient des Fonctions
		if(isExpressionContainPattern(expression.trim(), FUNC_CHAIN_PATTERN)) {
			
			// Un Log
			logger.debug("DAOValidatorHelper#computeExpression - Expression contenant des fonction: Extraction des fonctions...");
			
			// Obtention de la liste des Fonctions
			String[] functions = extractToken(expression, FUNC_CHAIN_PATTERN);

			// Un Log
			logger.debug("DAOValidatorHelper#computeExpression - Nombre de fonctions: " + functions.length);
			
			// Parcours
			for (String function : functions) {

				// Chaine en cours
				String currentExpression = expressionModel.getComputedExpression();
				
				// Un Log
				logger.debug("DAOValidatorHelper#computeExpression - Fonction Courante [" + function + "]");
				
				// Nom de la Variable
				String parameterName = "var" + i++;

				// Un Log
				logger.debug("DAOValidatorHelper#computeExpression - Nom du Parametre courant [" + parameterName + "]");
				
				// On remplace l'occurence courante par le nom de la variable
				currentExpression = currentExpression.replace(function, ":" + parameterName);
				
				// Un Log
				logger.debug("DAOValidatorHelper#computeExpression - Mise a jour de l'expression");

				// On met a jour l'expression computee
				expressionModel.setComputedExpression(currentExpression);
				
				// Un Log
				logger.debug("DAOValidatorHelper#computeExpression - Ajout du Parametre dans le modele");
				
				// On ajoute les parametres
				expressionModel.addParameter(parameterName, function);
			}
			
		}
						
		// Tant que la chaene traitee contient des ENVs
		while(isExpressionContainPattern(expressionModel.getComputedExpression(), ENV_CHAIN_PATTERN)) {
			
			// Un Log
			logger.debug("DAOValidatorHelper#computeExpression - Expression contenant des ENVs: Extraction des ENVs...");
			
			// Obtention de la liste des ENVs
			String[] envs = extractToken(expressionModel.getComputedExpression(), ENV_CHAIN_PATTERN);

			// Un Log
			logger.debug("DAOValidatorHelper#computeExpression - Nombre de ENVs: " + envs.length);
			
			// Parcours
			for (String env : envs) {
				
				// Chaine en cours
				String currentExpression = expressionModel.getComputedExpression();
				
				// Un Log
				logger.debug("DAOValidatorHelper#computeExpression - ENV Courant [" + env + "]");
				
				// Nom de la Variable
				String parameterName = "var" + i++;

				// Un Log
				logger.debug("DAOValidatorHelper#computeExpression - Nom du Parametre courant [" + parameterName + "]");
				
				// On remplace l'occurence courante par le nom de la variable
				currentExpression = currentExpression.replace(env, ":" + parameterName);
				
				// Un Log
				logger.debug("DAOValidatorHelper#computeExpression - Mise a jour de l'expression");

				// On met a jour l'expression computee
				expressionModel.setComputedExpression(currentExpression);
				
				// Un Log
				logger.debug("DAOValidatorHelper#computeExpression - Ajout du Parametre dans le modele");
				
				// On ajoute les parametres
				expressionModel.addParameter(parameterName, env);
			}
		}
		
		// Un Log
		logger.debug("DAOValidatorHelper#computeExpression - On retourne le modele");
		
		// On retourne l'expression
		return expressionModel;
	}
	
	/**
	 * Methode permettant de verifier si un chemin contient des Fonctions
	 * @param expression	Chaine a controler
	 * @return	Resultat de la verification
	 */
	public static boolean isExpressionContainsFunction(String expression) {
		
		// Un Log
		logger.debug("DAOValidatorHelper#isExpressionContainsFunction");
		
		// Si la chaine est vide : false
		if(expression == null || expression.trim().length() == 0) {
			
			// Un Log
			logger.debug("DAOValidatorHelper#isExpressionContainsFunction - La chaene est vide");
			
			// On retourne false
			return false;
		}
		
		// On split
		return isExpressionContainPattern(expression, FUNC_CHAIN_PATTERN);
	}
	
	/**
	 * Methode permettant de verifier si un chemin contient des Fonctions
	 * @param expression	Chaine a controler
	 * @return	Resultat de la verification
	 */
	public static boolean isExpressionContainPattern(String expression, String pattern) {
		
		try {
			
			// Un Log
			logger.debug("DAOValidatorHelper#isExpressionContainPattern");
			
			// Si la chaine est vide : false
			if(expression == null || expression.trim().length() == 0) {
				
				// Un Log
				logger.debug("DAOValidatorHelper#isExpressionContainPattern - La chaene est vide");
				
				// On retourne false
				return false;
			}
			
			// Un Log
			logger.debug("DAOValidatorHelper#isExpressionContainPattern - Pattern: " + pattern);
			logger.debug("DAOValidatorHelper#isExpressionContainPattern - Chaene : " + expression);
			
			// Construction d'un Pattern
			Pattern regex = Pattern.compile(".*" + pattern + ".*");
			
			// On retourne le resultat
			return regex.matcher(expression).matches();
			
		} catch (PatternSyntaxException e) {
			
			// On affiche
			e.printStackTrace();
			
			// On leve l'exception relative
			throw new RuntimeException(pattern, e);
		}
	}
		
	/**
	 * Methode permettant d'obtenir la derniere occurence d'une sous-cahene 
	 * correspondant au pattern des fonction dans une chaine
	 * @param expression	Chaine a scruter
	 * @return	Derniere occurence
	 */
	public static String [] getFunctionTokens(String expression) {
		
		// Un Log
		logger.debug("DAOValidatorHelper#getFunctionTokens");
		
		// Un log
		logger.debug("DAOValidatorHelper#getFunctionTokens - Chaene : " + expression);
		
		// On retourne le tableau
		return extractToken(expression, FUNC_CHAIN_PATTERN);
	}
	
	/**
	 * Methode permettant d'extraire le nom de la fonction
	 * @param functionToken	Topken de fonction
	 * @return	Nom de la fonction
	 */
	public static String extractFunctionName(String functionToken) {
		
		// Si le Token est null
		if(functionToken == null || functionToken.trim().length() == 0){
			
			// Un Log
			logger.debug("DAOValidatorHelper#extractFunctionName - La chaene est vide");
			
			// On retourne la chaine
			return functionToken;
		}
		
		int index0 = functionToken.indexOf(SIMPLE_FUNCTION_LEFT_DELIMITER);
		int index1 = functionToken.indexOf(SIMPLE_FUNCTION_OPEN);
		
		// Extraction du nom de la fonction
		String fName = functionToken.substring(index0 + SIMPLE_FUNCTION_LEFT_DELIMITER.length(), index1);
		
		// Un Log
		logger.debug("DAOValidatorHelper#extractFunctionName - Function Name: " + fName);
		
		// On retourne la deuxieme
		return fName;
	}
	
	public static String extractParameter(String expression) {
		
		// Si l'expression est nulle
		if(expression == null || expression.trim().length() == 0) return expression;
		
		// Obtention du nom de la fonction
		String functionName = extractFunctionName(expression);
		
		// Index de debut
		int begin = 0;
		
		// Si le nom de la fonction est vide
		if(functionName != null && functionName.trim().length() > 0) begin = expression.indexOf(functionName) + functionName.length() + 1;
		
		// Index de fin du delimiteur de debut
		int end = expression.lastIndexOf(SIMPLE_FUNCTION_CLOSE);
		
		// On retourne le parametre
		return expression.substring(begin, end);
	}
	
	/**
	 * Methode d'extraction de toutes les sous-chaines respectant le pattern donne
	 * @param expression	Expression mere
	 * @param pattern	Pattern a rechercher
	 * @return	Liste des sous-chaines respectant ce pattern
	 */
	public static String[] extractToken(String expression, String pattern) {
		
		// Un Log
		logger.debug("DAOValidatorHelper#extractToken");
		
		// Un log
		logger.debug("DAOValidatorHelper#extractToken - Chaene Mere : " + expression);
		logger.debug("DAOValidatorHelper#extractToken - Pattern     : " + pattern);
		
		// Si la chaine est vide
		if(expression == null || expression.trim().length() == 0) {
			
			// Un Log
			logger.debug("DAOValidatorHelper#extractToken - Expression vide");
			
			// On retourne null;
			return null;
		}
		
		// Si le pattern est null
		if(pattern == null) {
			
			// Un Log
			logger.debug("DAOValidatorHelper#extractToken - Pattern Nul");
			
			// On retourne null;
			return null;
		}
		
		// On splitte par l'espace
		String[] spacePlitted = expression.split(SPLITTER_CHAIN);
		
		// Array des Tokens
		StringBuffer aTokens = new StringBuffer();
		
		// Un Index
		int index = 0;
		
		// On parcours le tableau
		for (String spaceToken : spacePlitted) {
			
			// Si le token ne respecte pas le pattern
			if(isExpressionContainPattern(spaceToken, pattern)) {

				// Si on est pas au premier
				if(index++ > 0) aTokens.append("@");
				
				// On ajoute
				aTokens.append(spaceToken);
			}
		}
		
		// On split la chaine originale avec ce pattern
		return aTokens.toString().split("@");
	}

	/**
	 * Methode d'evaluation d'une Expression (Fonctionnelle ou de ENV sur u Objet) sur un objet donne
	 * @param expression	Expression a evaluer
	 * @param target	Objet cible de l'evaluation
	 * @return	Resultat de l'evaluation
	 */
	public static Object evaluateValueExpression(String expression, Object target) {
		
		// Executeur des methodes sur le contexte
		DAOValidatorMethodExpressionExecutor methodExecutor = new DAOValidatorMethodExpressionExecutor();
		
		// Resolver
		DAOValidatorBeanELResolver resolver = new DAOValidatorBeanELResolver(null);
		
		// Contexte d'evaluation
		DAOValidatorELContext context = new DAOValidatorELContext();

		// Fabrique d'expression
		ExpressionFactoryImpl expressionFactory = new ExpressionFactoryImpl();
		
		// Un message
		logger.debug("DAOValidatorHelper#evaluateValueExpression");
		
		// Si l'expression est nulle
		if(expression == null || expression.trim().length() == 0) return null;
		
		// Si l'objet cible est null
		if(target == null) return null;
		
		// Expression Locale
		String localExpression = expression;
		
		// Nom de la fonction
		String functionName = null;

		// Objet resultat
		Object result = null;
		
		// Si l'expression contient une fonction
		if(DAOValidatorHelper.isExpressionContainPattern(expression, DAOValidatorHelper.FUNC_CHAIN_PATTERN)) {
			
			// Resultat de l'evaluation des parametres
			Object parameterEvaluation = null;
			
			// Un message
			logger.debug("DAOValidatorHelper#evaluateValueExpression - Expression fonctionnelle");
			
			// Obtention du Nom de la fonction
			functionName = DAOValidatorHelper.extractFunctionName(expression);
			
			// On extrait le parametre
			String functionParameter = DAOValidatorHelper.extractParameter(expression);
			
			// Si le parametre est une fonction
			if(DAOValidatorHelper.isExpressionContainPattern(functionParameter, DAOValidatorHelper.FUNC_CHAIN_PATTERN)) {
				
				// Un message
				logger.debug("DAOValidatorHelper#evaluateValueExpression - Le parametre est Expression fonctionnelle : Appel recursif");
				
				// Appel recursif
				parameterEvaluation = evaluateValueExpression(functionParameter, target);
				
			} else if(DAOValidatorHelper.isExpressionContainPattern(functionParameter, DAOValidatorHelper.ENV_CHAIN_PATTERN)) {
				
				// Un message
				logger.debug("DAOValidatorHelper#evaluateValueExpression - Le parametre est Expression ENV : Appel recursif");
				
				// Appel recursif
				parameterEvaluation = evaluateValueExpression(functionParameter, target);
				
			} else if(functionParameter != null && functionParameter.equals("$$")) {
				
				// Un message
				logger.debug("DAOValidatorHelper#evaluateValueExpression - Le parametre null: Action sur l'Objet en cours");
				
				// Appel recursif
				parameterEvaluation = target;
				
			}
			
			// Si l'executeur de fonction possede cette fonction
			if(methodExecutor.getMethodsName().contains(functionName)) {
				
				// Un message
				logger.debug("DAOValidatorHelper#evaluateValueExpression - Methode existante pour l'executeur de methode");
				
				// Si la chaene parametre est vide
				if(functionParameter == null || functionParameter.trim().length() == 0) result = methodExecutor.invoke(functionName);
				
				// On evalue la fonction
				result = methodExecutor.invoke(functionName, parameterEvaluation);
				
			} else {

				// Un message
				logger.debug("DAOValidatorHelper#evaluateValueExpression - Methode non existante pour l'executeur de methode (On retourne l'evaluation du parametre)");
				
				// On recupere le parametre
				result = parameterEvaluation;
				
			}
			
		} else if(DAOValidatorHelper.isExpressionContainPattern(expression, DAOValidatorHelper.ENV_CHAIN_PATTERN)) {
					
			// Un message
			logger.debug("DAOValidatorHelper#evaluateValueExpression - Expression contenant des ENVs : Initialisation de l'objet de Base");
			
			// Initialisation de la cible dans le resolver
			resolver.setBase(target);

			// Un message
			logger.debug("DAOValidatorHelper#evaluateValueExpression - Initialisation du Resolver");
			
			// Initialisation du resolver dans le contexte
			context.setELResolver(resolver);

			// Un message
			logger.debug("DAOValidatorHelper#evaluateValueExpression - Creation de l'expression");
			
			// Instanciation d'une expression
			ValueExpression ve = expressionFactory.createValueExpression(context, localExpression, Object.class);
			
			// On calcule le resultat
			result = ve.getValue(context);
			
		}
		
		// On affiche le resultat
		logger.debug("DAOValidatorHelper#evaluateValueExpression - Resultat de l'expression: " + result);
		
		// On retourne le resultat de l'evalo
		return result;
	}

	/**
	 * Methode permettant de tester si un fichier represente une archive EAR
	 * @param path	Chemin vers le fichier
	 * @return Resultat du test
	 */
	public static boolean isEARArchive(String path) {
		
		// Un Log
		logger.debug("DAOValidatorHelper#isEARArchive");
		
		// EAR Descriptor File
		String earMarker = "META-INF/application.xml";
				
		// Fichier Jar
		JarFile jarFile = null;
		
		// Si le chemin est null : false
		if(path == null || path.trim().length() == 0) return false;
		
		// Un Objet File
		File file = new File(path);
		
		// Si le fichier n'existe pas
		if(!file.exists()) return false;
		
		// Si c'est un repertoire
		if(file.isDirectory()) return false;
		
		// Si l'extension n'est pas EAR
		if(!path.endsWith(".ear")) return false;
		
		// Tentative de creation d'une representation de l'archive en memoire
		try {
			
			// Le JarFile
			jarFile = new JarFile(new File(path), true);
			
		} catch (Exception e) {
			
			// On retourne la liste vide
			return false;
		}
		
		// Enumeration des Entrees de l'EAR
		Enumeration<JarEntry> jarEntries = jarFile.entries();
		
		// Si l'enumeration est vide
		if(jarEntries == null || !jarEntries.hasMoreElements()) return false;
		
		// Parcours
		while(jarEntries.hasMoreElements()) {
			
			// Obtention d'une entree
			JarEntry jarEntry = (JarEntry) jarEntries.nextElement();
			
			// Si l'entree correspond e notre marqueur
			if(jarEntry.getName().equals(earMarker)) return true;
		}
		
		// On renvoie false
		return false;
	}
	
	/**
	 * Methode permettant de tester si un fichier represente une archive WAR
	 * @param path	Chemin vers le fichier
	 * @return Resultat du test
	 */
	public static boolean isWARArchive(String path) {
		
		// Un Log
		logger.debug("DAOValidatorHelper#isWARArchive");
		
		// EAR Descriptor File
		String earMarker = "WEB-INF/web.xml";
				
		// Fichier Jar
		JarFile jarFile = null;
		
		// Si le chemin est null : false
		if(path == null || path.trim().length() == 0) return false;
		
		// Un Objet File
		File file = new File(path);
		
		// Si le fichier n'existe pas
		if(!file.exists()) return false;

		// Si c'est un repertoire
		if(file.isDirectory()) return false;

		// Si l'extension n'est pas JAR
		if(!path.endsWith(".war")) return false;
		
		// Tentative de creation d'une representation de l'archive en memoire
		try {
			
			// Le JarFile
			jarFile = new JarFile(new File(path), true);
			
		} catch (Exception e) {
			
			// On retourne la liste vide
			return false;
		}
		
		// Enumeration des Entrees de l'EAR
		Enumeration<JarEntry> jarEntries = jarFile.entries();
		
		// Si l'enumeration est vide
		if(jarEntries == null || !jarEntries.hasMoreElements()) return false;
		
		// Parcours
		while(jarEntries.hasMoreElements()) {
			
			// Obtention d'une entree
			JarEntry jarEntry = (JarEntry) jarEntries.nextElement();
			
			// Si l'entree correspond e notre marqueur
			if(jarEntry.getName().equals(earMarker)) return true;
		}
		
		// On renvoie false
		return false;
	}
	
	/**
	 * Methode permettant de tester si un fichier Jar est invalide
	 * @param path	Chemin vers le fichier
	 * @return Resultat du test
	 */
	public static boolean isJARArchive(String path) {

		// Un Log
		logger.debug("DAOValidatorHelper#isWARArchive");
		
		// Fichier Jar
		JarFile jarFile = null;
		
		// Si le chemin est null : false
		if(path == null || path.trim().length() == 0) return false;
		
		// Un Objet File
		File file = new File(path);
		
		// Si le fichier n'existe pas
		if(!file.exists()){
			
			// On retourne False
			return false;
		}
		
		// Si c'est un repertoire
		if(file.isDirectory()) {
					
			// On retourne false
			return false;
		}
		
		// Si l'extension n'est pas JAR
		if(!path.endsWith(".jar")) {
			
			// On retourne false
			return false;
		}
		
		// Tentative de creation d'une representation de l'archive en memoire
		try {
			
			// Le JarFile
			jarFile = new JarFile(new File(path), true);
			
		} catch (Exception e) {
					
			// On retourne la liste vide
			return false;
		}
		
		// Enumeration des Entrees de l'EAR
		Enumeration<JarEntry> jarEntries = jarFile.entries();
		
		// Si l'enumeration est vide
		if(jarEntries == null || !jarEntries.hasMoreElements()){
				
			// On retourne false
			return false;
		}
		
		// On renvoie false
		return true;
	}
	
	/**
	 * Methode permettant de tester si une archive contient une entree donnee
	 * @param path	Chemin vers le fichier
	 * @param entryName Nom de l'entree recherchee
	 * @return Resultat du test
	 */
	public static boolean isArchiveContainsEntry(String path, String entryName) {
		
		// Si l'entry est vide
		if(entryName == null || entryName.trim().length() == 0) return false;
		
		// Entry Name
		String entry = entryName;
		
		// Fichier Jar
		JarFile jarFile = null;
		
		// Si le chemin est null : false
		if(path == null || path.trim().length() == 0) return false;
		
		// Un Objet File
		File file = new File(path);
		
		// Si le fichier n'existe pas
		if(!file.exists()) return false;
		
		// Tentative de creation d'une representation de l'archive en memoire
		try {
			
			// Le JarFile
			jarFile = new JarFile(new File(path), true);
			
		} catch (Exception e) {
			
			// On retourne la liste vide
			return false;
		}
		
		// Enumeration des Entrees de l'EAR
		Enumeration<JarEntry> jarEntries = jarFile.entries();
		
		// Si l'enumeration est vide
		if(jarEntries == null || !jarEntries.hasMoreElements()) return false;
		
		// Parcours
		while(jarEntries.hasMoreElements()) {
			
			// Obtention d'une entree
			JarEntry jarEntry = (JarEntry) jarEntries.nextElement();
			
			// Si l'entree correspond e notre marqueur
			if(jarEntry.getName().equals(entry)) return true;
		}
		
		// On renvoie false
		return false;
	}
	
	/**
	 * Methode permettant de savoir si une nom de fichier se termine par l'extension .jar)
	 * @param path	Nom du fichier
	 * @return	Resultat du controle
	 */
	public static boolean isJarFileName(String path) {
		
		// Si le nom est null
		if(path == null || path.trim().length() == 0) return false;
		
		// On retourne la comparaison
		return path.endsWith(".jar");
	}
	
	/**
	 * Methode permettant de savoir si une nom de fichier se termine par l'extension .war)
	 * @param path	Nom du fichier
	 * @return	Resultat du controle
	 */
	public static boolean isWarFileName(String path) {
		
		// Si le nom est null
		if(path == null || path.trim().length() == 0) return false;
		
		// On retourne la comparaison
		return path.endsWith(".war");
	}
	
	/**
	 * Methode permettant de savoir si une nom de fichier se termine par l'extension .sar)
	 * @param path	Nom du fichier
	 * @return	Resultat du controle
	 */
	public static boolean isSarFileName(String path) {
		
		// Si le nom est null
		if(path == null || path.trim().length() == 0) return false;
		
		// On retourne la comparaison
		return path.endsWith(".sar");
	}
	
	/**
	 * Methode permettant de savoir si une nom de fichier se termine par l'extension .beans)
	 * @param path	Nom du fichier
	 * @return	Resultat du controle
	 */
	public static boolean isBeansArchiveFileName(String path) {
		
		// Si le nom est null
		if(path == null || path.trim().length() == 0) return false;
		
		// On retourne la comparaison
		return path.endsWith(".beans");
	}
	
	/**
	 * Methode qui teste si une chaine donnee contient un des caracteres d'une liste
	 * @param text	Chaine dans laquelle on rcherche les caracteres
	 * @param invalidCharacters	Liste ds caracteres recherches
	 * @return	Etat de contenance
	 */
	public static boolean checkContainsInvalidCharacter(String text, String invalidCharacters){
		if(text == null || text.trim().length() == 0 || invalidCharacters == null) return false;
		
		for (char c : invalidCharacters.toCharArray()) {
			if(text.indexOf(new String(new char[]{c})) >= 0) return true;
		}
		
		return false;
	}
	
	/**
	 * Methode qui teste si une chaine ne contient que des caracteres alphanumeriques
	 * @param text	Chaine a tester
	 * @return	Statut de contenance
	 */
	public static boolean isAlphaNumericString(String text){
		
		// Le Pattern representant une chaine AlphaNumerique
		Pattern pattern = Pattern.compile("\\w+");
		
		// Test
		return (text != null) && (text.trim().length() > 0) && (pattern.matcher(text).matches());
	}
	
	
	/**
	 * Methode Main de test
	 * @param args	Arguments de ligne de commande
	 */
	public static void main(String[] args) {
		
		// Chaine contenant des EL
		String chn1 = "from Country c where (c.code = ${code})";
		
		// Obtention du model
		// ExpressionModel model = computeExpression(chn1);
		
		// Affichage du contenu du modele
		// System.out.println("Chaine Originale: " + model.getOriginalExpression());
		// System.out.println("Contain Computee: " + model.getComputedExpression());
		// System.out.println("Parametres: " + model.getParameters());
		
		// Derniere fonction
		// String f = getFunctionTokens(chn1);
		
		// Calcul du Nom de la fonction
		// String functionName = extractFunctionName(f);
		
		// Calcul du Parametre
		// String parameter = extractParameter(f);
		// System.out.println("Expression Contain Function: " + isExpressionContainsFunction(chn1));
		// System.out.println("Expression Contain ENV     : " + isExpressionContainsENV(chn1));
		
		// On compute l'expression
		ExpressionModel model = computeExpression(chn1);
		
		// On affiche
		System.out.println("Expression Initiale: " + model.getOriginalExpression());
		System.out.println("Expression Compilee: " + model.getComputedExpression());
		System.out.println("Parametres         : " + model.getParameters());
		
		
	}
	
	
	
}