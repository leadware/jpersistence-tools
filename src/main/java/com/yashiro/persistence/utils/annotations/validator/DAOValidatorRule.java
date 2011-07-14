/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yashiro.persistence.utils.annotations.validator.engine.IDAOValidator;
import com.yashiro.persistence.utils.dao.constant.DAOValidatorEvaluationTime;

/**
 * Annotation permettant de specifier classe implementant la logique 
 * de validation d'une Fonction-Annotation
 * @author Jean-Jacques
 * @version 2.0
 */
@Target(value = {ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DAOValidatorRule {
	
	/**
	 * Methode permettant d'obtenir le moment d'evaluation de la regle
	 * @return Moment d'evaluation de la regle
	 */
	public DAOValidatorEvaluationTime evaluationTime() default DAOValidatorEvaluationTime.PRE_CONDITION;
	
	/**
	 * Methode d'obtention de la classe d'implementation de la logique de validation
	 * @return	Classe d'implementation de la logique de validation
	 */
	public Class<? extends IDAOValidator<? extends Annotation>> logicClass();
}
