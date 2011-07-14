/**
 * 
 */
package com.yashiro.persistence.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Annotation permettant de marquer une methode protegee
 * 	@author Jean-Jacques
 *	@version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface AllowedRole {
	
	/**
	 * Nom du role
	 */
	public String name();
	
	/**
	 * Nom d'affichage du role
	 */
	public String displayName();
}
