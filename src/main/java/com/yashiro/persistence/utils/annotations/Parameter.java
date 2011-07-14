/**
 * 
 */
package com.yashiro.persistence.utils.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation permettant de savoir qu'une classe est une classe de parametrage
 * @author Jean-Jacques
 * @version 1.0
 */
@Target(value = ElementType.TYPE)
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {}
