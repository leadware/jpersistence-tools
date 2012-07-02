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
package com.bulk.persistence.tools.test.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bulk.persistence.tools.dao.impl.JPAGenericDAORulesBasedImpl;
import com.bulk.persistence.tools.test.dao.api.SXUserDao;
import com.bulk.persistence.tools.test.dao.entities.sx.SXUser;

/**
 * Implémentation de la DAO de gestion des Utilisateurs
 * @author Jean-Jacques ETUNÈ NGI
 */
@Repository(value = SXUserDao.SERVICE_NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class SXUserDAOImpl extends JPAGenericDAORulesBasedImpl<SXUser> implements SXUserDao {

    /**
     * Gestionnaire d'entités
     */
    @PersistenceContext
    private EntityManager entityManager;
    
	/* (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/* (non-Javadoc)
	 * @see com.bulk.persistence.tools.dao.JPAGenericDAO#getManagedEntityClass()
	 */
	@Override
	public Class<SXUser> getManagedEntityClass() {
		return SXUser.class;
	}

}
