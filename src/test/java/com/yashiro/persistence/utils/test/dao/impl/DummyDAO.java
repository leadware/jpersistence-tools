package com.yashiro.persistence.utils.test.dao.impl;

import com.yashiro.persistence.utils.dao.JPAGenericDAORulesBased;
import com.yashiro.persistence.utils.test.dao.api.IDummyDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by IntelliJ IDEA.
 * User: Jean-Jacques ETUNE NGI
 * Date: 14/07/11
 * Time: 17:22
 */
@Repository(value = IDummyDAO.SERVICE_NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class DummyDAO extends JPAGenericDAORulesBased implements IDummyDAO {

    /**
     * Gestionnaire d'entités
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Méthode d'obtention du Gestionnaire d'entités
     * @return  Gestionnaire d'entités
     */
    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }
}
