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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bulk.persistence.tools.api.exceptions.InvalidEntityInstanceStateException;
import com.bulk.persistence.tools.test.dao.CountryDAO;
import com.bulk.persistence.tools.test.dao.RegionDAO;
import com.bulk.persistence.tools.test.dao.SXGroupDAO;
import com.bulk.persistence.tools.test.dao.SXRoleDAO;
import com.bulk.persistence.tools.test.dao.SXUserDAO;
import com.bulk.persistence.tools.test.dao.TownDAO;
import com.bulk.persistence.tools.test.dao.entities.Country;

/**
 * Classe de test de l'implémentation de la DAO Générique basées sur l'évaluation des règles
 * @author Jean-Jacques
 * @see
 * 	<b>
 * 		<i>Class Under Test</i>
 * 		<ol>
 * 			<li>{@link com.bulk.persistence.tools.dao.impl.JPAGenericDAORulesBasedImpl}
 * 		</ol>
 * 	</b>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:applicationContext-persistence-test.xml"
})
public class TestJPAGenericDAORulesBased {
	
    /**
     * DAO de gestion des Pays
     */
	@Autowired
    private CountryDAO countryDao;

    /**
     * DAO de gestion des Regions
     */
	@Autowired
    private RegionDAO regionDao;

    /**
     * DAO de gestion des Villes
     */
	@Autowired
    private TownDAO townDao;
    
    /**
     * DAO de gestion des roles
     */
	@Autowired
    private SXRoleDAO roleDAO;
    
    /**
     * DAO de gestion des groupes
     */
	@Autowired
    private SXGroupDAO groupDAO;
    
    /**
     * DAO de gestion des Utilisateurs
     */
	@Autowired
    private SXUserDAO userDAO;
    
    
    /**
     * Méthode d'initialisation des méthodes de tests
     */
    @Before
    public void setUp() {
    	
    	// Initialisation de la base de donnees
    	populateDataBase();
    }
    
    /**
     * Méthode de finalisation des méthodes de test
     */
    @After
    public void tearDown() {
    	
    	// Vidage de la Base de données
    	cleanDataBase();
    }

    /**
     * Méthode de test d'enregistrement
     */
    @Test
    public void testSave() {
    	
    	// Nombre initial de Country
    	int count = countryDao.filter(null, null, null, 0, -1).size();
    	
        //////////////////////////////////////////////////////////////////////////////
        // Interception par la DAO Générique d'une contrainte d'intégrité violée 	//
        //  		La contrainte en question est une contrainte JSR 303 		 	//
    	//  	positionnée sur le champ hérité "code" de l'entité Country  		//
    	//	@Size(min = CODE_MIN_LENGTH, max = CODE_MAX_LENGTH, message = "...")	//
    	//  protected String code;													//
        //////////////////////////////////////////////////////////////////////////////
    	
    	// Une Country
    	Country country = new Country("CMR", "CAMEROUN");
    	
        // Un log
        System.out.println("PAYS À ENREGISTRER: " + country);

		// Tentative d'enregistrement
    	country = countryDao.save(country);
		
		// On vérifie qu'il ya eu aucun enregistrement
		assertEquals(count + 1, countryDao.filter(null, null, null, 0, -1).size());
    }
    
    /**
     * Méthode de test d'interception de la validation des contraintes d'intégrités (JSR 303) par le Framework
     */
    //@Test
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
        
        try {

    		// Tentative d'enregistrement
    		countryDao.save(noCodeCountry);
    		
    		// Echec si pa sd'erreur
    		fail("L'opération DAO devrait lever une exception");
    		
		} catch (InvalidEntityInstanceStateException e) {
			
			// On affiche
			System.err.println("CONSTRAINT VIOLATION: " + e);
		}
        
		// On vérifie qu'il ya eu aucun enregistrement
		assertEquals(initialCountryCount, countryDao.filter(null, null, null, 0, -1).size());
    }
    

    /**
     * Méthode de test d'opération DAO sur l'entité Country
     */
    //@Test
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
		countryDao.setValidateIntegrityConstraintOnSave(true);
		
    	// Nombre initial de Country
    	int initialCountryCount = countryDao.filter(null, null, null, 0, -1).size();
    	
    	// Une Country
    	Country noCodeCountry = new Country();
    	
    	// La désignation
    	noCodeCountry.setDesignation("CAMEROUN");

		// Tentative d'enregistrement
		countryDao.save(noCodeCountry);
		
		// Un log
		System.out.println("On vérifie qu'il ya eu aucun enregistrement");
		
		// On vérifie qu'il ya eu aucun enregistrement
		assertEquals(initialCountryCount, countryDao.filter(null, null, null, 0, -1).size());
    }
    
    /**
     * Méthode de vidage de la base de données
     */
    protected void cleanDataBase() {
    	
    	// Vidage des Utilisateurs
    	userDAO.clean();
    	
    	// Vidage des Groupes
    	groupDAO.clean();
    	
    	// Vidage des Roles
    	roleDAO.clean();
    	
    	// Vidage des Villes
    	townDao.clean();
    	
    	// Vidage des regions
    	regionDao.clean();
    	
    	// Vidage des Pays
    	countryDao.clean();
    }
    
    
    /**
     * Méthode d'initialisation de la base de données
     */
    protected void populateDataBase() {
    	
    }
    
}
