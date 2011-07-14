/**
 * 
 */
package com.yashiro.persistence.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 *  Annotation permettant de marquer une classe protegee
 * 	@author Jean-Jacques
 *	@version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface ProtectedClass {
	
	/**
	 * Systeme parent
	 * @return	Nom du systeme parent
	 */
	public String system();
	
	/**
	 * Methode d'obtention de la liste des methodes protegees de la classe
	 * @return	Liste des methodes protegees de la classe
	 */
	AllowedRole[] methods();
}
