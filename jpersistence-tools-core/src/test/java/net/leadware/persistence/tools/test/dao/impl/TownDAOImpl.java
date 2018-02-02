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
package net.leadware.persistence.tools.test.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.leadware.persistence.tools.api.dao.constants.OrderType;
import net.leadware.persistence.tools.api.utils.restrictions.Predicate;
import net.leadware.persistence.tools.core.dao.impl.JPAGenericDAORulesBasedImpl;
import net.leadware.persistence.tools.test.dao.TownDAO;
import net.leadware.persistence.tools.test.dao.entities.Town;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implémentation de la DAO de gestion des Villes
 * @author Jean-Jacques ETUNÈ NGI
 */
@Repository(value = TownDAO.SERVICE_NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class TownDAOImpl extends JPAGenericDAORulesBasedImpl<Town> implements TownDAO {

    /**
     * Gestionnaire d'entités
     */
    @PersistenceContext
    private EntityManager entityManager;
    
    /*
     * (non-Javadoc)
     * @see net.leadware.persistence.tools.test.dao.JPAGenericDAO#getEntityManager()
     */
    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

	/* (non-Javadoc)
	 * @see net.leadware.persistence.tools.test.dao.JPAGenericDAO#getManagedEntityClass()
	 */
	@Override
	public Class<Town> getManagedEntityClass() {
		return Town.class;
	}
	
	@Override
	public Town save(Town entity) {
		// TODO Auto-generated method stub
		return super.save(entity);
	}
	
	@Override
	public Town save(Town entity, boolean validateIntegrityConstraint,
			boolean preValidateReferentialConstraint,
			boolean postValidateReferentialConstraint) {
		// TODO Auto-generated method stub
		return super.save(entity, validateIntegrityConstraint,
				preValidateReferentialConstraint, postValidateReferentialConstraint);
	}
	
	@Override
	public Town update(Object id, Town entity) {
		// TODO Auto-generated method stub
		return super.update(id, entity);
	}
	
	@Override
	public Town update(Object id, Town entity, boolean validateIntegrityConstraint,
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
	public Town findByPrimaryKey(String entityIDName, Object entityID,
			Set<String> properties) {
		// TODO Auto-generated method stub
		return super.findByPrimaryKey(entityIDName, entityID, properties);
	}
	
	@Override
	public List<Town> filter(List<Predicate> predicates, Map<String, OrderType> orders,
			Set<String> properties, int firstResult, int maxResult) {
		// TODO Auto-generated method stub
		return super.filter(predicates, orders, properties, firstResult, maxResult);
	}
}
