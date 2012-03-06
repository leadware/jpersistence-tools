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
package com.bulksoft.persistence.utils.test.dao.impl;

import com.bulksoft.persistence.utils.dao.JPAGenericDAORulesBased;
import com.bulksoft.persistence.utils.test.dao.api.IDummyDAO;
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
