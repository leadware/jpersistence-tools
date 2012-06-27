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
package com.bulk.persistence.tools.validator;

import com.bulk.persistence.tools.validator.base.AbstractJPAConstraintValidator;

/**
 * Classe de validation par defaut des contraintes sur l'entite 
 * (Elle n'effectue que la validation des contraintes d'integrites)
 * @author Jean-Jacques ETUNÈ NGI
 */
public class DoNothingJPAConstraintValidator extends AbstractJPAConstraintValidator {

	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.validator.IJPAConstraintValidator#validateReferentialConstraint()
	 */
	@Override
	public void validateReferentialConstraint() {}
	
	/*
	 * (non-Javadoc)
	 * @see com.bulk.persistence.tools.validator.base.AbstractJPAConstraintValidator#validateIntegrityConstraint()
	 */
	@Override
	public void validateIntegrityConstraint() {}
}
