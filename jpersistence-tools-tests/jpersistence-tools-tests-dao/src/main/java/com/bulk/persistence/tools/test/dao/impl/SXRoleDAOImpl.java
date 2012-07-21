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

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bulk.persistence.tools.dao.impl.JPAGenericDAORulesBasedImpl;
import com.bulk.persistence.tools.test.dao.SXRoleDAO;
import com.bulk.persistence.tools.test.dao.entities.sx.SXRole;

/**
 * Implémentation de la DAO de gestion des Roles
 * @author Jean-Jacques ETUNÈ NGI
 */
@Repository(value = SXRoleDAO.SERVICE_NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class SXRoleDAOImpl extends JPAGenericDAORulesBasedImpl<SXRole> implements SXRoleDAO {

    /**
     * Gestionnaire d'entités
     */
    @PersistenceContext
    private EntityManager entityManager;
    
	@Override
	public EntityManager getEntityManager() {
		 return this.entityManager;
	}

	@Override
	public Class<SXRole> getManagedEntityClass() {
		return SXRole.class;
	}
	
	@Override
	public SXRole save(SXRole entity) {
		// TODO Auto-generated method stub
		return super.save(entity);
	}
	
	@Override
	public SXRole save(SXRole entity, boolean validateIntegrityConstraint,
			boolean preValidateReferentialConstraint,
			boolean postValidateReferentialConstraint) {
		// TODO Auto-generated method stub
		return super.save(entity, validateIntegrityConstraint,
				preValidateReferentialConstraint, postValidateReferentialConstraint);
	}
	
	@Override
	public SXRole update(Object id, SXRole entity) {
		// TODO Auto-generated method stub
		return super.update(id, entity);
	}
	
	@Override
	public SXRole update(Object id, SXRole entity, boolean validateIntegrityConstraint,
			boolean preValidateReferentialConstraint,
			boolean postValidateReferentialConstraint) {
		// TODO Auto-generated method stub
		return super.update(id, entity, validateIntegrityConstraint,
				preValidateReferentialConstraint, postValidateReferentialConstraint);
	}
	
	@Override
	public void delete(Object entityID) {
		// TODO Auto-generated method stub
		super.delete(entityID);
	}
	
	@Override
	public void delete(Object entityID,
			boolean preValidateReferentialConstraint,
			boolean postValidateReferentialConstraint) {
		// TODO Auto-generated method stub
		super.delete(entityID, preValidateReferentialConstraint,
				postValidateReferentialConstraint);
	}
	
	@Override
	public void clean() {
		// TODO Auto-generated method stub
		super.clean();
	}
	
	@Override
	public SXRole findByPrimaryKey(String entityIDName, Object entityID,
			Set<String> properties) {
		// TODO Auto-generated method stub
		return super.findByPrimaryKey(entityIDName, entityID, properties);
	}
	
	@Override
	public List<SXRole> filter(List<Predicate> predicates, List<Order> orders,
			Set<String> properties, int firstResult, int maxResult) {
		// TODO Auto-generated method stub
		return super.filter(predicates, orders, properties, firstResult, maxResult);
	}
}
