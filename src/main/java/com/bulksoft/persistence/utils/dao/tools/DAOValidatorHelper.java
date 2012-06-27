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

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.el.ValueExpression;
import com.bulk.persistence.tools.validator.IDAOValidator;
import com.bulk.persistence.tools.validator.annotations.DAOConstraint;
import de.odysseus.el.ExpressionFactoryImpl;

/**
 * Classe d'aide pour les annotations DAO
 * @author Jean-Jacques
 * @version 2.0
 */
public class DAOValidatorHelper {
	
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
	 * Methode permettant de verifier si une annotation est
	 * compatible avec le Framework jpersistence-tools
	 * @param annotation	Annotation a controler
	 * @return	Etat d'appartenance
	 */
	public static boolean isDAOValidatorAnnotation(Annotation annotation) {
		
		// Si lm'annotation est nulle
		if(annotation == null) {
			
			// On retourne false
			return false;
		}
		
		// Obtention de la classe de cette annotation
		Class<?> annotationClass = annotation.annotationType();
		
		// Recherchje de l'annotation de validation
		DAOConstraint logicAnnotation = annotationClass.getAnnotation(DAOConstraint.class);
		
		// On retourne le resultat
		return logicAnnotation != null;
	}
	
	/**
	 * Methode permettant de charger toutes les annotations DAO sur l'objet pour un mode donne et un temps d'evaluation donne
	 * @param object	Objet a inspecter
	 * @return	Liste des annotations DAO retrouvees
	 */
	public static List<Annotation> loadDAOValidatorAnnotations(Object object) {
		
		// Liste des annotations retrouvees
		List<Annotation> daoAnnotations = new ArrayList<Annotation>();
		
		// Si l'objet est null
		if(object == null) {
			
			// On retourne une liste vide
			return daoAnnotations;
		}
		
		// Obtention des annotations de la classe
		Annotation[] objectAnnotations = object.getClass().getAnnotations();
		
		// Si le tableau est vide
		if(objectAnnotations == null || objectAnnotations.length == 0) {
			
			// On retourne une liste vide
			return daoAnnotations;
		}
		
		// Parcours
		for (Annotation annotation : objectAnnotations) {
			
			// Si c'est une annotation du Framework
			if(isDAOValidatorAnnotation(annotation)) {
				
				// On ajoute l'annotation
				daoAnnotations.add(annotation);
			}
		}
		
		// On retourne la liste
		return daoAnnotations;
	}
	
	/**
	 * Methode permettant de charger toutes les Classes de validation de l'Objet en fonction du Mode
	 * @param object	Objet a inspecter
	 * @return	Liste des classes d'implementation
	 */
	public static List<Class<? extends IDAOValidator<? extends Annotation>>> loadDAOValidatorClass(Object object) {
		
		// Liste de classes de validation retrouvees
		List<Class<? extends IDAOValidator<? extends Annotation>>> result = new ArrayList<Class<? extends IDAOValidator<? extends Annotation>>>();
		
		// Si l'objet est null
		if(object == null) {
			
			// On retourne une liste vide
			return result;
		}
		
		// Obtention des annotations de la classe
		Annotation[] objectAnnotations = object.getClass().getAnnotations();
		
		// Si le tableau est vide
		if(objectAnnotations == null || objectAnnotations.length == 0) {
			
			// On retourne une liste vide
			return result;
		}
		
		// Parcours
		for (Annotation annotation : objectAnnotations) {
			
			// Si c'est une annotation du Framework
			if(isDAOValidatorAnnotation(annotation)) {
				
				// Obtention de l'annotation de validfation
				DAOConstraint daoAnnotation = annotation.annotationType().getAnnotation(DAOConstraint.class);
				
				// On ajoute l'annotation
				result.add(daoAnnotation.validatedBy());
			}
		}
		
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
		
		// Si l'annotation est nulle
		if(annotation == null) {
			
			// On retourne null
			return null;
		}
		
		// Obtention de l'annotation DAO
		DAOConstraint logicAnnotation = annotation.annotationType().getAnnotation(DAOConstraint.class);
		
		// On retourne cette annotation
		return logicAnnotation.validatedBy();
	}
	
	/**
	 * Methode permettant de savoir si un Objet de type T est contenu dans un Tableau d'objet de type T
	 * @param array	Tableau d'objet d'un type T
	 * @param value	Objet d'un Type T
	 * @return	Etat de presence
	 */
	public static <T extends Object> boolean arraryContains(T[] array, T value) {
		
		// Si le tableau est vide
		if(array == null || array.length == 0) {
			
			// On retourne false
			return false;
		}
		
		// Si le mode est vide
		if(value == null) {
			
			// On retourne false
			return false;
		}
			
		// Mode dedans
		boolean modeIn = false;
		
		// Index du Tableau
		int index = 0;
		
		// Parcours
		while(index < array.length && !modeIn) {
			
			// Valeur du Tableau
			T tValue = array[index++];
			
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
		
		// Si la chaine est vide : false
		if(expression == null || expression.trim().length() == 0) {
			
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
		
		// On retourne le tableau
		return extractToken(expression, ENV_CHAIN_PATTERN);
	}
	
	/**
	 * Methode permettant de resoudre les variables d'environnement dans une chemin
	 * @param expression	Expression du chemin
	 * @return	Expression resolue
	 */
	public static String resolveEnvironmentsParameters(String expression) {

		// Si l'expression est vide
		if(expression == null || expression.trim().length() == 0) {
			
			// On retourne null
			return null;
		}
		
		// Tant que la chaene traitee contient des ENVs
		while(isExpressionContainPattern(expression, ENV_CHAIN_PATTERN)) {

			// Obtention de la liste des ENVs
			String[] envs = extractToken(expression, ENV_CHAIN_PATTERN);

			// Parcours
			for (String env : envs) {
				
				String cleanEnv = env.replace("${", "");
				cleanEnv = cleanEnv.replace("}", "");
				
				// On remplace l'occurence courante par le nom de la variable
				expression = expression.replace(env, System.getProperty(cleanEnv));
			}
		}
		
		// On retourne l'expression
		return expression;
	}
	
	/**
	 * Methode de resolution d'une Expression
	 * @param expression	Expression a transformer
	 * @return	Modele de l'expression transformee
	 */
	public static ExpressionModel computeExpression(String expression) {
		
		// Si l'expression est vide
		if(expression == null || expression.trim().length() == 0) {
			
			// On retourne null
			return null;
		}
		
		// On Instancie un model d'expression
		ExpressionModel expressionModel = new ExpressionModel(expression.trim());
		
		// Index de l'iteration
		int i = 0;
		
		// Si la chaine contient des Fonctions
		if(isExpressionContainPattern(expression.trim(), FUNC_CHAIN_PATTERN)) {
			
			// Obtention de la liste des Fonctions
			String[] functions = extractToken(expression, FUNC_CHAIN_PATTERN);
			
			// Parcours
			for (String function : functions) {

				// Chaine en cours
				String currentExpression = expressionModel.getComputedExpression();
				
				// Nom de la Variable
				String parameterName = "var" + i++;
				
				// On remplace l'occurence courante par le nom de la variable
				currentExpression = currentExpression.replace(function, ":" + parameterName);
				
				// On met a jour l'expression computee
				expressionModel.setComputedExpression(currentExpression);
				
				// On ajoute les parametres
				expressionModel.addParameter(parameterName, function);
			}
			
		}
						
		// Tant que la chaene traitee contient des ENVs
		while(isExpressionContainPattern(expressionModel.getComputedExpression(), ENV_CHAIN_PATTERN)) {
			
			// Obtention de la liste des ENVs
			String[] envs = extractToken(expressionModel.getComputedExpression(), ENV_CHAIN_PATTERN);
			
			// Parcours
			for (String env : envs) {
				
				// Chaine en cours
				String currentExpression = expressionModel.getComputedExpression();
				
				// Nom de la Variable
				String parameterName = "var" + i++;
				
				// On remplace l'occurence courante par le nom de la variable
				currentExpression = currentExpression.replace(env, ":" + parameterName);
				
				// On met a jour l'expression computee
				expressionModel.setComputedExpression(currentExpression);
				
				// On ajoute les parametres
				expressionModel.addParameter(parameterName, env);
			}
		}
		
		// On retourne l'expression
		return expressionModel;
	}
	
	/**
	 * Methode permettant de verifier si un chemin contient des Fonctions
	 * @param expression	Chaine a controler
	 * @return	Resultat de la verification
	 */
	public static boolean isExpressionContainsFunction(String expression) {
		
		// Si la chaine est vide : false
		if(expression == null || expression.trim().length() == 0) {
			
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
			
			// Si la chaine est vide : false
			if(expression == null || expression.trim().length() == 0) {
				
				// On retourne false
				return false;
			}
			
			// Construction d'un Pattern
			Pattern regex = Pattern.compile(".*" + pattern + ".*");
			
			// On retourne le resultat
			return regex.matcher(expression).matches();
			
		} catch (PatternSyntaxException e) {
			
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
			
			// On retourne la chaine
			return functionToken;
		}
		
		int index0 = functionToken.indexOf(SIMPLE_FUNCTION_LEFT_DELIMITER);
		int index1 = functionToken.indexOf(SIMPLE_FUNCTION_OPEN);
		
		// Extraction du nom de la fonction
		String fName = functionToken.substring(index0 + SIMPLE_FUNCTION_LEFT_DELIMITER.length(), index1);
		
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
		
		// Si la chaine est vide
		if(expression == null || expression.trim().length() == 0) {
			
			// On retourne null;
			return null;
		}
		
		// Si le pattern est null
		if(pattern == null) {
			
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
			
			// Obtention du Nom de la fonction
			functionName = DAOValidatorHelper.extractFunctionName(expression);
			
			// On extrait le parametre
			String functionParameter = DAOValidatorHelper.extractParameter(expression);
			
			// Si le parametre est une fonction
			if(DAOValidatorHelper.isExpressionContainPattern(functionParameter, DAOValidatorHelper.FUNC_CHAIN_PATTERN)) {
				
				// Appel recursif
				parameterEvaluation = evaluateValueExpression(functionParameter, target);
				
			} else if(DAOValidatorHelper.isExpressionContainPattern(functionParameter, DAOValidatorHelper.ENV_CHAIN_PATTERN)) {
				
				// Appel recursif
				parameterEvaluation = evaluateValueExpression(functionParameter, target);
				
			} else if(functionParameter != null && functionParameter.equals("$$")) {
				
				// Appel recursif
				parameterEvaluation = target;
				
			}
			
			// Si l'executeur de fonction possede cette fonction
			if(methodExecutor.getMethodsName().contains(functionName)) {
				
				// Si la chaene parametre est vide
				if(functionParameter == null || functionParameter.trim().length() == 0) result = methodExecutor.invoke(functionName);
				
				// On evalue la fonction
				result = methodExecutor.invoke(functionName, parameterEvaluation);
				
			} else {
				
				// On recupere le parametre
				result = parameterEvaluation;
				
			}
			
		} else if(DAOValidatorHelper.isExpressionContainPattern(expression, DAOValidatorHelper.ENV_CHAIN_PATTERN)) {
			
			// Initialisation de la cible dans le resolver
			resolver.setBase(target);
			
			// Initialisation du resolver dans le contexte
			context.setELResolver(resolver);
			
			// Instanciation d'une expression
			ValueExpression ve = expressionFactory.createValueExpression(context, localExpression, Object.class);
			
			// On calcule le resultat
			result = ve.getValue(context);
			
		}
		
		// On retourne le resultat de l'evalo
		return result;
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