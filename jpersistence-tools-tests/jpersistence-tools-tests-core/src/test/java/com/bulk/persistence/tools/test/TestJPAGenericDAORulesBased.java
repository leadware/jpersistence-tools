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
package com.bulk.persistence.tools.test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bulk.persistence.tools.api.exceptions.InvalidEntityInstanceStateException;
import com.bulk.persistence.tools.test.dao.api.CountryDAO;
import com.bulk.persistence.tools.test.dao.entities.Country;

/**
 * Classe de test de l'implémentation de la DAO Générique basées sur l'évaluation des règles
 * @author Jean-Jacques
 * @see
 * 	<b>
 * 		<i>Class Under Test</i>
 * 		<ol>
 * 			<li>{@link com.bulk.persistence.tools.dao.yashiro.persistence.utils.dao.JPAGenericDAORulesBased}
 * 		</ol>
 * 	</b>
 * @version 2.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/META-INF/spring-config-test.xml"
})
public class TestJPAGenericDAORulesBased {

    /**
     * La DAO a tester
     */
    @Resource(name =  CountryDAO.SERVICE_NAME)
    private CountryDAO countryDao;

    /**
     * Méthode de test d'opération DAO sur l'entité Country
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    @ExpectedException(value = InvalidEntityInstanceStateException.class)
    public void testIntegrityConstraintValidation() {
    	
    	// Nombre initial de Country
    	int initialCountryCount = countryDao.filter(null, null, null, 0, -1).size();
    	
        //////////////////////////////////////////////////////////////////////////////
        // Interception par la DAO Générique d'une contrainte d'intégrité violée 	//
        //  		La contrainte en question est une contrainte JSR 303 		 	//
    	//  	positionnée sur le champ hérité "code" de l'entité Country  		//
    	//	@Size(min = CODE_MIN_LENGTH, max = CODE_MAX_LENGTH, message = "...")	//
    	//  protected String code;													//
        //////////////////////////////////////////////////////////////////////////////
    	
    	// Une Country
    	Country noCodeCountry = new Country();
    	
    	// La désignation
    	noCodeCountry.setDesignation("CAMEROUN");

        // Un log
        System.out.println("PAYS À ENREGISTRER: " + noCodeCountry);
        
		// Tentative d'enregistrement
		countryDao.save(noCodeCountry);
		
		// Echec si pa sd'erreur
		fail("L'opération DAO devrait lever une exception");
		
		// On vérifie qu'il ya eu aucun enregistrement
		assertEquals(initialCountryCount, countryDao.filter(null, null, null, 0, -1).size());
    }
    

    /**
     * Méthode de test d'opération DAO sur l'entité Country
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    @ExpectedException(value = ConstraintViolationException.class)
    public void testIntegrityConstraintDesactivated() {

    	//////////////////////////////////////////////////////////////////////////////
        // Désactivation de l'Interception par la DAO Générique d'une contrainte 	//
		// 							d'intégrité violée 								//
        //  		La contrainte en question est une contrainte JSR 303 		 	//
    	//  	positionnée sur le champ hérité "code" de l'entité Country  		//
    	//	@Size(min = CODE_MIN_LENGTH, max = CODE_MAX_LENGTH, message = "...")	//
    	//  protected String code;													//
        //////////////////////////////////////////////////////////////////////////////
    	
		// Désactivation de la validation des contraintes d'intégrités
		countryDao.setValidateIntegrityConstraintOnSave(false);
		
    	// Nombre initial de Country
    	int initialCountryCount = countryDao.filter(null, null, null, 0, -1).size();
    	
    	// Une Country
    	Country noCodeCountry = new Country();
    	
    	// La désignation
    	noCodeCountry.setDesignation("CAMEROUN");

		// Tentative d'enregistrement
		countryDao.save(noCodeCountry);
		
		// Flush
		countryDao.getEntityManager().flush();
		
		// Un log
		System.out.println("On vérifie qu'il ya eu aucun enregistrement");
		
		// On vérifie qu'il ya eu aucun enregistrement
		assertEquals(initialCountryCount, countryDao.filter(null, null, null, 0, -1).size());
    }
}
