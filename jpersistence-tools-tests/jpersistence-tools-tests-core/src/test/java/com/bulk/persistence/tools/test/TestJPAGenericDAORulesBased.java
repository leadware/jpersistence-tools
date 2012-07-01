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
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bulk.persistence.tools.api.exceptions.DAOValidationException;
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
@Transactional(propagation = Propagation.REQUIRED)
@TransactionConfiguration(transactionManager = "JPAHibernateTransactionManager")
public class TestJPAGenericDAORulesBased {

    /**
     * La DAO a tester
     */
    @Resource(name =  CountryDAO.SERVICE_NAME)
    private CountryDAO dao;

    /**
     * Méthode de test d'opération DAO sur l'entité Country
     */
    @Test
    public void testCountryDAOOperations() {

        // Enregistrement d'un pays

        // Une Country
        Country c01 = new Country();

        // Le Code
        c01.setCode("CMR");
        c01.setDesignation("CAMEROUN");

        // Un log
        System.out.println("Enregistrement d'un pays: " + c01);

        // Enregistrement
        c01 = dao.save(c01);

        // Un log
        System.out.println("Pays Enregistré: " + c01);

        // Assertion d'ID non Null
        assertNotNull(c01.getId());

        // Assertion d'existence en BD
        assertNotNull(dao.findByPrimaryKey(Country.class, "id", c01.getId(), null));


        // Enregistrement d'un pays avec un code existant

        // Ancien ID
        Long oldID = c01.getId();

        try{

            // Tentative d'enregistrement
            c01 = dao.save(c01);

            // Une erreur
            fail("Une exception de validation aurait dûe être levée: Le code @code est déja attribué".replaceFirst("@code", c01.getCode()));

        } catch (DAOValidationException e) {

            // On vérifie que l'objet a le même ID
            assertEquals(oldID, c01.getId());
        }

        // On nettoeis
        dao.clean(Country.class);

        // Vérification
        assertEquals(0, dao.filter(Country.class, null, null, null, 0, -1).size());
    }

}
