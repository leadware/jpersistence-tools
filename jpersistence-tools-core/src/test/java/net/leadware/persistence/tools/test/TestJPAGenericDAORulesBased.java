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
package net.leadware.persistence.tools.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.hibernate.LazyInitializationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.leadware.persistence.tools.api.collection.utils.ConverterUtil;
import net.leadware.persistence.tools.api.exceptions.InvalidEntityInstanceStateException;
import net.leadware.persistence.tools.api.utils.PropertyContainer;
import net.leadware.persistence.tools.api.utils.RestrictionsContainer;
import net.leadware.persistence.tools.test.dao.CountryDAO;
import net.leadware.persistence.tools.test.dao.RegionDAO;
import net.leadware.persistence.tools.test.dao.SXGroupDAO;
import net.leadware.persistence.tools.test.dao.SXRoleDAO;
import net.leadware.persistence.tools.test.dao.SXUserDAO;
import net.leadware.persistence.tools.test.dao.TownDAO;
import net.leadware.persistence.tools.test.dao.entities.Country;
import net.leadware.persistence.tools.test.dao.entities.Region;
import net.leadware.persistence.tools.test.dao.entities.Town;
import net.leadware.persistence.tools.test.dao.entities.field.generator.IdentityFieldGenerator;
import net.leadware.persistence.tools.test.dao.entities.sx.SXGroup;
import net.leadware.persistence.tools.test.dao.entities.sx.SXRole;
import net.leadware.persistence.tools.test.dao.entities.sx.SXUser;
import net.leadware.persistence.tools.test.dao.entities.sx.constants.Sex;
import net.leadware.persistence.tools.test.dao.entities.sx.constants.UserState;


/**
 * Classe de test de l'implémentation de la DAO Générique basées sur l'évaluation des règles
 * @author Jean-Jacques ETUNÈ NGI
 * @see
 * 	<b>
 * 		<i>Class Under Test</i>
 * 		<ol>
 * 			<li>{@link net.leadware.persistence.tools.core.dao.impl.JPAGenericDAORulesBasedImpl}
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
	private Country c1, c2;
    
	/**
	 * Regions
	 */
	private Region r1, r2, r3;
	
	/**
	 * Towns
	 */
	private Town t1, t2, t3;

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
	private SXUser u1;
	
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
    	
        ////////////////////////////////////////////////////////////////////////////////
        // Interception par la DAO Générique d'une contrainte d'intégrité violée 		//
        //  		La contrainte en question est une contrainte JSR 303 		 			//
    		//  	positionnée sur le champ hérité "code" de l'entité Country  				//
    		//	@Size(min = CODE_MIN_LENGTH, max = CODE_MAX_LENGTH, message = "...")		//
    		//  protected String code;													//
        ////////////////////////////////////////////////////////////////////////////////
    	
    		// Nombre initial de Country
    		long count = countryDao.count(null);

    		// Affichage du count
    		System.out.println("INITIAL COUNT: " + count);
    	
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
			
			// On affiche les valeurs
			System.err.println("CONSTRAINT VIOLATION [Entity Name]: " + e.getEntityName());

			// On affiche les valeurs
			System.err.println("CONSTRAINT VIOLATION [Property Name]: " + e.getPropertyName());

			// On affiche les valeurs
			System.err.println("CONSTRAINT VIOLATION [Message Code]: " + e.getMessage());

			// On affiche les valeurs
			System.err.println("CONSTRAINT VIOLATION [Parameters]: " + (e.getParameters() != null ? Arrays.toString(e.getParameters()) : ""));
		}
        
		// On vérifie qu'il ya eu aucun enregistrement
		assertEquals(count, countryDao.count(null));
		
		// On verifie la valeur de la designation
		assertEquals(IdentityFieldGenerator.GENERATED_VALUE, country.getDesignation());
    }
    
    /**
     * Méthode de test d'opération DAO sur l'entité Country
     */
    @Test
    @Ignore
    public void testIntegrityConstraintDesactivated() {
    	
	    	////////////////////////////////////////////////////////////////////////////////
	    // Désactivation de l'Interception par la DAO Générique d'une contrainte		//
		// 							d'intégrité violée 								//
	    //  		La contrainte en question est une contrainte JSR 303 		 			//
	    	//  	positionnée sur le champ hérité "code" de l'entité Country  				//
	    	//	@Size(min = CODE_MIN_LENGTH, max = CODE_MAX_LENGTH, message = "..")		//
	    	//  protected String code;													//
	    ////////////////////////////////////////////////////////////////////////////////
	
	    	// Nombre initial de Country
	    	long count = countryDao.count(null);
	    	
	    	// Affichage du count
	    	System.out.println("INITIAL COUNT: " + count);
    	
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
		assertEquals(count, countryDao.count(null));

		// On verifie la valeur de la designation
		assertEquals(IdentityFieldGenerator.GENERATED_VALUE, country.getDesignation());
    }
    
    /**
     * Méthode de test des Opérations de filtre
     */
    @Test
    @Ignore
    public void testFilterMethods() {
    	
    	// Recherche
    	List<Country> filteredCountry = null;
    	List<Region> filteredRegions = null;
    	List<Town> filteredTowns = null;
    	
        //////////////////////////////////////////////////////////////////////////////
        // 	  Filtre par la DAO Générique des Pays dont le nom commence par CAM 	//
        //////////////////////////////////////////////////////////////////////////////
    	
    	// Predicat de recherche
    	RestrictionsContainer restrictionsContainer = RestrictionsContainer.newInstance().addLike("designation", "CAM%");
    	
    	// Recherche
    	filteredCountry = countryDao.filter(restrictionsContainer.getRestrictions(), null, null, 0, -1);

    	// Vérification
    	assertNotNull(filteredCountry);
    	assertEquals(0, filteredCountry.size());

        //////////////////////////////////////////////////////////////////////////////
        // 	  Filtre par la DAO Générique des Pays dont le nom commence par C	 	//
        //////////////////////////////////////////////////////////////////////////////
    	
    	// Predicat de recherche
    	restrictionsContainer = RestrictionsContainer.newInstance().addLike("designation", "C%");
    	
    	// Recherche
    	filteredCountry = countryDao.filter(restrictionsContainer.getRestrictions(), null, null, 0, -1);

    	// Vérification
    	assertNotNull(filteredCountry);
    	assertEquals(0, filteredCountry.size());

        //////////////////////////////////////////////////////////////////////////////
        // 	  Filtre par la DAO Générique des Pays dont le code commence par C	 	//
    	//							et le nom par CA								//
        //////////////////////////////////////////////////////////////////////////////
    	
    	// Predicat de recherche

    	// Predicat de recherche
    	restrictionsContainer = RestrictionsContainer.newInstance().addLike("code", "C%").addLike("designation", "CA%");
    	
    	// Recherche
    	filteredCountry = countryDao.filter(restrictionsContainer.getRestrictions(), null, null, 0, -1);

    	// Vérification
    	assertNotNull(filteredCountry);
    	assertEquals(0, filteredCountry.size());
    	
        //////////////////////////////////////////////////////////////////////////////
        // 	  		Filtre par la DAO Générique des Regions du Pays c1	 			//
        //////////////////////////////////////////////////////////////////////////////
    	
    	// Predicat de recherche

    	// Predicat de recherche
    	restrictionsContainer = RestrictionsContainer.newInstance().addEq("country.code", c1.getCode());
    	
    	// Recherche
    	filteredRegions = regionDao.filter(restrictionsContainer.getRestrictions(), null, null, 0, -1);

    	// Vérification
    	assertNotNull(filteredRegions);
    	assertEquals(3, filteredRegions.size());
    	
        //////////////////////////////////////////////////////////////////////////////
        // 	  		Filtre par la DAO Générique des Regions du Pays c2	 			//
    	//						dont le nom commence par PARIS						//
        //////////////////////////////////////////////////////////////////////////////
    	
    	// Predicat de recherche

    	// Predicat de recherche
    	restrictionsContainer = RestrictionsContainer.newInstance().addEq("country.code", c2.getCode()).addLike("designation", "PARIS%");
    	
    	// Recherche
    	filteredRegions = regionDao.filter(restrictionsContainer.getRestrictions(), null, null, 0, -1);

    	// Vérification
    	assertNotNull(filteredRegions);
    	assertEquals(0, filteredRegions.size());
    	
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        // 	  	Chargement de la liste des villes dont le nom commence par Y avec chargement de leur pays	//
        //////////////////////////////////////////////////////////////////////////////////////////////////////
    	
    	// Conteneur de proprietes
    	PropertyContainer propertyContainer = PropertyContainer.newInstance().add("region.country");
    	
    	// Conteneur de restrictions
    	restrictionsContainer = RestrictionsContainer.newInstance().addEq("region.code", "CNTR");
    	
    	// Filtre
    	filteredTowns = townDao.filter(restrictionsContainer.getRestrictions(), null, propertyContainer.getProperties(), 0, -1);

    	// Vérification
    	assertNotNull(filteredTowns);
    	assertEquals(2, filteredTowns.size());
    	
    	System.out.println("====================================================");
    	System.out.println("====================================================");
    	System.out.println("===========> TOWNS SIZE : " + filteredTowns.size());
    	System.out.println("====================================================");
    	System.out.println("====================================================");
    	
        //////////////////////////////////////////////////////////////////////////////
        // 	  	Chargement d'un Utilisateur par sa clé primaire sans ses groupes	//
        //////////////////////////////////////////////////////////////////////////////
    	
    	// Recherche
    	u1 = userDAO.findByPrimaryKey("id", u1.getId(), null);
    	
    	try {
			
    		// Tentative d'accès à ses groupes
    		u1.getGroups().size();
			
    		// Echec
    		fail("Erreur: On devrait avoir une LazyInitializationException");
    		
		} catch (LazyInitializationException e) {
			
			// Un log
			System.err.println("ERREUR SURVENUE : " + e.getMessage());
		}

        //////////////////////////////////////////////////////////////////////////////
        // 	  	Chargement d'un Utilisateur par sa clé primaire avec ses groupes	//
        //////////////////////////////////////////////////////////////////////////////
    	
    	// Recherche
    	u1 = userDAO.findByPrimaryKey("id", u1.getId(), ConverterUtil.convertArrayToSet("groups"));
    	
    	try {
			
    		// Tentative d'accès à ses groupes
    		System.out.println("GROUP COUNT : " + u1.getGroups().size());
			
		} catch (LazyInitializationException e) {

    		// Echec
    		fail("Erreur: On devrait pas avoir une LazyInitializationException");
		}
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
	    	countryDao.save(new Country("ANGL", "ANGLETERRE"));
	    	countryDao.save(new Country("CHN", "CHINE"));
	    	countryDao.save(new Country("JPN", "JAPON"));
	    	
	    	// Les Region
	    	r1 = regionDao.save(new Region("CNTR", "CENTRE", c1));
	    	r2 = regionDao.save(new Region("OUE", "OUEST", c1));
	    	r3 = regionDao.save(new Region("LTTR", "LITTORAL", c1));
	    	regionDao.save(new Region("PRII", "PARIS II", c2));
	    	regionDao.save(new Region("PRI", "PARIS I", c2));
	    	
	    	// Les Town
	    	t1 = townDao.save(new Town("YDE", "YAOUNDE", r1));
	    	t2 = townDao.save(new Town("DLA", "DOUALA", r3));
	    	t3 = townDao.save(new Town("LMB", "LIMBE", r3));
	    	townDao.save(new Town("MBLY", "MBALMAYO", r1));
	    	townDao.save(new Town("BFSS", "BAFOUSSAM", r2));
	    	
	    	// Les Role
	    	sxr1 = roleDAO.save(new SXRole("saveCountry", "Enregistrement des Pays"));
	    	sxr2 = roleDAO.save(new SXRole("updateCountry", "Modification des Pays"));
	    	sxr3 = roleDAO.save(new SXRole("deleteCountry", "Suppression des Pays"));
	    	sxr4 = roleDAO.save(new SXRole("saveUser", "Création des Utilisateurs"));
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
	    			"sakazaki", "jetune@yahoo.fr", "99105161", UserState.VALID, t1, ConverterUtil.convertArrayToSet(g1)));
	    	userDAO.save(new SXUser("Jean Vincent", "NGA NTI", Sex.MAN, "vince_nti", 
	    			"vince", "vince_nti@yahoo.fr", "94757270", UserState.VALID, t1, ConverterUtil.convertArrayToSet(g2)));
	    	userDAO.save(new SXUser("Guy Landry", "TCHATCHOUANG NONO", Sex.MAN, "guytchatch", 
	    			"landry", "guytchatch@yahoo.fr", "98889022", UserState.VALID, t2, ConverterUtil.convertArrayToSet(g3)));
	    	userDAO.save(new SXUser("Celestine", "KANMO", Sex.WOMAN, "kcelestine", 
	    			"celestine", "kcelestine@yahoo.fr", "77735678", UserState.VALID, t3, ConverterUtil.convertArrayToSet(g3)));
    }
}