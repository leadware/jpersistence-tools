/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yashiro.persistence.utils.annotations.validator.engine.IdentityDAOValidatorRule;

/**
 * Annotation permettant de specifier une classe de validation qui ne fait rien du tout
 * @author Jean-Jacques
 * @version 2.0
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DAOValidatorRule(logicClass = IdentityDAOValidatorRule.class)
public @interface IdentityDAOValidator {}
