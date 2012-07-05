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

import static junit.framework.Assert.*;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.validation.ConstraintViolationException;
import org.hibernate.ejb.criteria.CriteriaBuilderImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.bulk.persistence.tools.api.exceptions.InvalidEntityInstanceStateException;
import com.bulk.persistence.tools.collection.utils.ConverterUtil;
import com.bulk.persistence.tools.test.dao.CountryDAO;
import com.bulk.persistence.tools.test.dao.RegionDAO;
import com.bulk.persistence.tools.test.dao.SXGroupDAO;
import com.bulk.persistence.tools.test.dao.SXRoleDAO;
import com.bulk.persistence.tools.test.dao.SXUserDAO;
import com.bulk.persistence.tools.test.dao.TownDAO;
import com.bulk.persistence.tools.test.dao.entities.Country;
import com.bulk.persistence.tools.test.dao.entities.Region;
import com.bulk.persistence.tools.test.dao.entities.Town;
import com.bulk.persistence.tools.test.dao.entities.sx.SXGroup;
import com.bulk.persistence.tools.test.dao.entities.sx.SXRole;
import com.bulk.persistence.tools.test.dao.entities.sx.SXUser;
import com.bulk.persistence.tools.test.dao.entities.sx.constants.Sex;
import com.bulk.persistence.tools.test.dao.entities.sx.constants.UserState;

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
	 * Countries
	 */
	private Country c1, c2, c3, c4, c5;
    
	/**
	 * Regions
	 */
	private Region r1, r2, r3, r4, r5;
	
	/**
	 * Towns
	 */
	private Town t1, t2, t3, t4, t5;

	/**
	 * Permissions
	 */
	private SXRole sxr1, sxr2, sxr3, sxr4, sxr5;
	
	/**
	 * Groups
	 */
	private SXGroup g1, g2, g3;
	
	/**
	 * Users
	 */
	private SXUser u1, u2, u3, u4;
	
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
     * Méthode de test d'interception de la validation des contraintes d'intégrités (JSR 303) par le Framework
     */
    @Test
    public void testIntegrityConstraintValidation() {
    	
        //////////////////////////////////////////////////////////////////////////////
        // Interception par la DAO Générique d'une contrainte d'intégrité violée 	//
        //  		La contrainte en question est une contrainte JSR 303 		 	//
    	//  	positionnée sur le champ hérité "code" de l'entité Country  		//
    	//	@Size(min = CODE_MIN_LENGTH, max = CODE_MAX_LENGTH, message = "...")	//
    	//  protected String code;													//
        //////////////////////////////////////////////////////////////////////////////
    	
    	// Nombre initial de Country
    	int count = countryDao.filter(null, null, null, 0, -1).size();
    	
    	// Une Country
    	Country country = new Country();
    	
    	// La désignation
    	country.setDesignation("CAMEROUN");

        // Un log
        System.out.println("PAYS À ENREGISTRER: " + country);
        
        try {

    		// Tentative d'enregistrement
    		countryDao.save(country);
    		
    		// Echec si pa sd'erreur
    		fail("L'opération DAO devrait lever une exception");
    		
		} catch (InvalidEntityInstanceStateException e) {
			
			// On affiche
			System.err.println("CONSTRAINT VIOLATION: " + e);
		}
        
		// On vérifie qu'il ya eu aucun enregistrement
		assertEquals(count, countryDao.filter(null, null, null, 0, -1).size());
    }
    
    /**
     * Méthode de test d'opération DAO sur l'entité Country
     */
    @Test
    public void testIntegrityConstraintDesactivated() {
    	
    	//////////////////////////////////////////////////////////////////////////////
        // Désactivation de l'Interception par la DAO Générique d'une contrainte 	//
		// 							d'intégrité violée 								//
        //  		La contrainte en question est une contrainte JSR 303 		 	//
    	//  	positionnée sur le champ hérité "code" de l'entité Country  		//
    	//	@Size(min = CODE_MIN_LENGTH, max = CODE_MAX_LENGTH, message = "...")	//
    	//  protected String code;													//
        //////////////////////////////////////////////////////////////////////////////

    	// Nombre initial de Country
    	int count = countryDao.filter(null, null, null, 0, -1).size();
    	
		// Désactivation de la validation des contraintes d'intégrités
		countryDao.setValidateIntegrityConstraintOnSave(false);
		
    	// Une Country
    	Country country = new Country();
    	
    	// La désignation
    	country.setDesignation("CAMEROUN");

        // Un log
        System.out.println("PAYS À ENREGISTRER: " + country);
        
        try {

    		// Tentative d'enregistrement
    		countryDao.save(country);
    		
    		// Echec si pa sd'erreur
    		fail("L'opération DAO devrait lever une exception");
    		
		} catch (InvalidEntityInstanceStateException e) {

    		// Echec si pa sd'erreur
    		fail("Exception invalide (celle-ci est uniquement levée par le Framework qui est supposé etre désactivé)");
    		
		} catch (ConstraintViolationException e) {
			
			// Un log
			System.err.println("Exception levée si la Validation JSR 303 est activée pour JPA 2.0");
			
			// Violations
			System.err.println("CONSTRAINT VIOLATION: " + e);
			
		} catch (Exception e) {
			
			// Un log
			System.err.println("Exception propriétaire si la validation JSR 303 n'est pas activée pour JPA 2.0");
			
			// Exception
			System.err.println("EXCEPTION: " + e);
		}
        
		// On vérifie qu'il ya eu aucun enregistrement
		assertEquals(count, countryDao.filter(null, null, null, 0, -1).size());
    }
    
    /**
     * Méthode de test des Opérations de filtre
     */
    @Test
    public void testFilterMethods() {

    	// Predicat de recherche
    	Predicate predicate = null;
    	
    	// Recherche
    	List<Country> filtered = null;
    	
        //////////////////////////////////////////////////////////////////////////////
        // 	  Filtre par la DAO Générique des Pays dont le nom commence par CAM 	//
        //////////////////////////////////////////////////////////////////////////////
    	
    	// Predicat de recherche
    	predicate = countryDao.like("designation", "CAM%");
    	
    	// Recherche
    	filtered = countryDao.filter(ConverterUtil.convertArrayToList(predicate), null, null, 0, -1);

    	// Vérification
    	assertNotNull(filtered);
    	assertEquals(1, filtered.size());

        //////////////////////////////////////////////////////////////////////////////
        // 	  Filtre par la DAO Générique des Pays dont le nom commence par C	 	//
        //////////////////////////////////////////////////////////////////////////////
    	
    	// Predicat de recherche
    	predicate = countryDao.like("designation", "C%");
    	
    	// Recherche
    	filtered = countryDao.filter(ConverterUtil.convertArrayToList(predicate), null, null, 0, -1);

    	// Vérification
    	assertNotNull(filtered);
    	assertEquals(2, filtered.size());
    	
    	
    	
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
    	
    	// Les Country
    	c1 = countryDao.save(new Country("CMR", "CAMEROUN"));
    	c2 = countryDao.save(new Country("FR", "FRANCE"));
    	c3 = countryDao.save(new Country("ANGL", "ANGLETERRE"));
    	c4 = countryDao.save(new Country("CHN", "CHINE"));
    	c5 = countryDao.save(new Country("JPN", "JAPON"));
    	
    	// Les Region
    	r1 = regionDao.save(new Region("CNTR", "CENTRE", c1));
    	r2 = regionDao.save(new Region("OUE", "OUEST", c1));
    	r3 = regionDao.save(new Region("LTTR", "LITTORAL", c1));
    	r4 = regionDao.save(new Region("PRII", "PARIS II", c2));
    	r5 = regionDao.save(new Region("PRI", "PARIS I", c2));
    	
    	// Les Town
    	t1 = townDao.save(new Town("YDE", "YAOUNDE", r1));
    	t2 = townDao.save(new Town("DLA", "DOUALA", r3));
    	t3 = townDao.save(new Town("LMB", "LIMBE", r3));
    	t4 = townDao.save(new Town("MBLY", "MBALMAYO", r1));
    	t5 = townDao.save(new Town("BFSS", "BAFOUSSAM", r2));
    	
    	// Les Role
    	sxr1 = roleDAO.save(new SXRole("saveCountry", "Enregistrement des Pays"));
    	sxr2 = roleDAO.save(new SXRole("updateCountry", "Modification des Pays"));
    	sxr3 = roleDAO.save(new SXRole("deleteCountry", "Suppression des Pays"));
    	sxr4 = roleDAO.save(new SXRole("createUser", "Création des Utilisateurs"));
    	sxr5 = roleDAO.save(new SXRole("deleteUser", "Suppression des Utilisateurs"));
    	
    	// les Group
    	g1 = groupDAO.save(new SXGroup("ROOT", "ROOT", "Groupes des Super Utilisateurs", 
    			ConverterUtil.convertArrayToSet(sxr1, sxr2, sxr3, sxr4, sxr5)));
    	g2 = groupDAO.save(new SXGroup("ADM", "Administrateur", "Groupes des Administrateurs", 
    			ConverterUtil.convertArrayToSet(sxr4, sxr5)));
    	g3 = groupDAO.save(new SXGroup("USR", "Utilisateurs", "Groupes des Utilisateurs", 
    			ConverterUtil.convertArrayToSet(sxr1, sxr2, sxr3)));
    	
    	// Les User
    	u1 = userDAO.save(new SXUser("Jean-Jacques", "ETUNÈ NGI", Sex.MAN, "jetune", 
    			"sakazaki", "jetune@yahoo.fr", "99105161", UserState.VALID, t1));
    	u2 = userDAO.save(new SXUser("Jean Vincent", "NGA NTI", Sex.MAN, "vince_nti", 
    			"vince", "vince_nti@yahoo.fr", "94757270", UserState.VALID, t1));
    	u3 = userDAO.save(new SXUser("Guy Landry", "TCHATCHOUANG NONO", Sex.MAN, "guytchatch", 
    			"landry", "guytchatch@yahoo.fr", "98889022", UserState.VALID, t2));
    	u4 = userDAO.save(new SXUser("Celestine", "KANMO", Sex.WOMAN, "kcelestine", 
    			"celestine", "kcelestine@yahoo.fr", "77735678", UserState.VALID, t3));
    }
}
