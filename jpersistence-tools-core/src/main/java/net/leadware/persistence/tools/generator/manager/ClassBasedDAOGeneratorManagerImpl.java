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
package net.leadware.persistence.tools.generator.manager;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.leadware.persistence.tools.api.dao.constants.DAOMode;
import net.leadware.persistence.tools.api.dao.constants.DAOValidatorEvaluationTime;
import net.leadware.persistence.tools.api.generator.IFieldGenerator;
import net.leadware.persistence.tools.api.generator.annotaions.FieldGenerator;
import net.leadware.persistence.tools.api.generator.manager.ClassBasedDAOGeneratorManager;
import net.leadware.persistence.tools.generator.base.AbstractDAOGeneratorManager;

/**
 * Implementation du gestionnaire de generateur base sur une classe de generation 
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 22 sept. 2015 - 17:59:58
 */
public class ClassBasedDAOGeneratorManagerImpl extends AbstractDAOGeneratorManager implements ClassBasedDAOGeneratorManager {
	
	/*
	 * (non-Javadoc)
	 * @see net.leadware.persistence.tools.api.generator.base.IDAOGeneratorManager#processGeneration(java.lang.Object, java.lang.reflect.Field)
	 */
	@Override
	public void processGeneration(Object entity, Field field) {

		// Si on ne doit pas evaluer cette annotation
		if(!this.isProcessable()) {
			
			// On sort
			return;
		}
		
		// On caste l'annotation
		FieldGenerator castedAnnotation = (FieldGenerator) this.annotation;
		
		// Classe de generation
		Class<? extends IFieldGenerator> generatorClass = castedAnnotation.generator();
		
		// Instanciation de la classe
		IFieldGenerator generator = null;
		
		try {
			
			// Instanciation de la classe
			generator = generatorClass.newInstance();
			
		} catch (Exception e) {
			
			// On relance
			throw new RuntimeException("jpersistencetools.generator.classbased.error.instanciate", e);
		}
		
		// Initialisation du gestionnaire d'entites
		generator.setEntityManager(entityManager);
		
		// Positionnement du champ a mettre a jour
		generator.setField(field);
		
		// Positionnement de l'entite
		generator.setEntity(entity);
		
		// Initialisation du generateur
		generator.initialize();
		
		// Generation
		Serializable generatedValue = generator.generate();
		
		try {
			
			// Obtention du descripteur de proprietes pour cette propriete
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), entity.getClass());
			
			// Obtention du setter
			Method propertySetter = propertyDescriptor.getWriteMethod();
			
			// Invocation du setter
			propertySetter.invoke(entity, generatedValue);
			
		} catch (Exception e) {
			
			// On relance
			throw new RuntimeException("jpersistencetools.generator.classbased.error.update.field", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.leadware.persistence.tools.generator.base.AbstractDAOGeneratorManager#getAnnotationMode()
	 */
	protected DAOMode[] getAnnotationMode() {
		
		// On caste l'annotation
		FieldGenerator castedAnnotation = (FieldGenerator) this.annotation;
		
		// On retourne le type de validation
		return castedAnnotation.mode();
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.leadware.persistence.tools.generator.base.AbstractDAOGeneratorManager#getAnnotationEvaluationTime()
	 */
	@Override
	protected DAOValidatorEvaluationTime[] getAnnotationEvaluationTime() {
		
		// On caste l'annotation
		FieldGenerator castedAnnotation = (FieldGenerator) this.annotation;
		
		// On retourne le tableau d'instant d'evalutation
		return castedAnnotation.evaluationTime();
	}
}
