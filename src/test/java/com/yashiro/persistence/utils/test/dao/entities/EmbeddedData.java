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
package com.yashiro.persistence.utils.test.dao.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

/**
 * Classe representant un Logo
 * @author Jean-Jacques
 * @version 1.0
 */
@Embeddable
public class EmbeddedData implements Serializable {
	
	/**
	 * ID Gener par Eclipse
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Nom de la ressource
	 */
	@Column(name = "GENEZIS_EMD_RESOURCE_NAME")
    private String resourceName = "";
    
    /**
     * Type MIME de la ressource
     */
	@Column(name = "GENEZIS_EMD_RESOURCE_MIME")
    private String mime = "";
    
    /**
     * Taille de la ressource
     */
	@Column(name = "GENEZIS_EMD_RESOURCE_LENGTH")
    private Long length = 0L;
    
    /**
     * Donnees de la ressource
     */
	@Column(name = "GENEZIS_EMD_RESOURCE_DATA")
	@Lob
    private byte[] data;
        
    /**
     * Constructeur par defaut
     */
    public EmbeddedData(){}
    
    /**
     * Constructeur avec initialisation des parametres
     * @param resourceName	Nom de la ressource
     * @param mime	Type MIME de la ressource
     * @param length	Taille de la ressource
     * @param data	Donnees de la ressource
     */
	public EmbeddedData(String resourceName, String mime, Long length, byte[] data) {
		this.resourceName = resourceName;
		this.mime = mime;
		this.length = length;
		this.data = data;
	}

	/**
     * Methode d'obtention des Donnees de la ressource
     * @return Donnees de la ressource
     */
    public byte[] getData() {
        return this.data;
    }
    
    /**
     * Methode de mise a jour des Donnees de la ressource
     * @param Donnees de la ressource
     */
    public void setData(byte[] data) {
        this.data = data;
    }
    
    /**
     * Methode d'obtention du Nom de la ressource
     * @return Nom de la ressource
     */
    public String getResourceName() {
        return this.resourceName;
    }
    
    /**
     * Methode de mise a jour du Nom de la ressource
     * @param Nom de la ressource
     */
    public void setResourceName(String resourceName) {
    	this.resourceName = resourceName;
    }
    
    /**
     * Methode d'obtention de la Taille de la ressource
     * @return Taille de la ressource
     */
    public Long getLength() {
        return this.length;
    }
    
    /**
     * Methode de mise a jour de la Taille de la ressource
     * @param Taille de la ressource
     */
    public void setLength(Long length) {
        this.length = length;
        
        // Si la taille est nulle
        if(this.length == null) this.length = 0L;
    }
    
    /**
     * Methode d'obtention du Type MIME de la ressource
     * @return Type MIME de la ressource
     */
    public String getMime(){
        return this.mime;
    }

    /**
     * Methode d'obtention du Type MIME de la ressource
     * @return Type MIME de la ressource
     */
    public void setMime(String mime){
        this.mime = mime;
        
        // Si le type est null
        if(mime == null || mime.trim().length() == 0) this.mime = "application/octet-stream";
    }
}
