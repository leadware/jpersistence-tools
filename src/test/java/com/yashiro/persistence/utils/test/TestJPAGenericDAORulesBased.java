package com.yashiro.persistence.utils.test;

import com.yashiro.persistence.utils.test.dao.api.IDummyDAO;
import com.yashiro.persistence.utils.test.dao.entities.Country;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Classe de test de l'implémentation de la DAO Générique basées sur l'évaluation des règles
 * @author Jean-Jacques
 * @see
 * 	<b>
 * 		<i>Class Under Test</i>
 * 		<ol>
 * 			<li>{@link com.yashiro.persistence.utils.dao.JPAGenericDAORulesBased}
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
    @Resource(name =  IDummyDAO.SERVICE_NAME)
    private IDummyDAO dao;

    /**
     * Méthode de test d'opération DAO sur l'entité Country
     */
    @Test
    public void testCountryDAOOperations() {

        // Enregistrement d'un pays

        // Une Country
        Country c01 = new Country();

        // Le Code
        c01.setCode("0001");
        c01.setDesignation("CAMEROUN");

        // Un log
        System.out.println("Enregistrement d'un pays: " + c01);

        // Enregistrement
        c01 = dao.save(c01);

        // Assertion d'ID non Null
        Assert.assertNotNull(c01.getId());

        // Assertion d'existence en BD
        Assert.assertNotNull(dao.findByPrimaryKey(Country.class, c01.getId(), null));
    }

}
