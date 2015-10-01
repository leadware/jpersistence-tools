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
package net.leadware.persistence.tools.core.dao.utils;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Classe representant un utilitaire d'encryption
 * @author <a href="mailto:jetune@leadware.net">Jean-Jacques ETUNE NGI (Leadware Enterprise Architect)</a>
 * @since 1 oct. 2015 - 09:13:40
 */
public class Encrypter {

	/**
	 *  La cle de cryptage
	 */
	private String stringKey = "sakazaki";
	
	/**
	 *  Le cipher
	 */
	private Cipher cipher;
	
	/**
	 *  Le Digester
	 */
	private MessageDigest digester;
	
	/**
	 * Instance unique de l'Encrypter
	 */
	private static Encrypter _instance = null;
	
	/**
	 * Constructeur prive
	 */
	public Encrypter(){
		
		try {
			
			// Initialisation du Digester
			digester = MessageDigest.getInstance("MD5");
			
			// Initialisation du Cipher
			cipher = Cipher.getInstance("DES");
			
		} catch (Exception e) {
			
			// On relance l'exception
			throw new RuntimeException(e);
			
		}
	}
	
	/**
	 * Factory de l'Encrypter
	 * @return	Encrypter
	 */
	public synchronized static Encrypter getInstance() {
		
		// Si l'instance est nulle
		if(_instance == null) {
			
			// Instanciation
			_instance = new Encrypter();
		}
		
		// On retourne l'instance
		return _instance;
	}
	
	
	/**
	 * Methode de decryptage d'un texte
	 * @param text	Texte a decrypter
	 * @return	Texte decrypte
	 */
	public synchronized String decryptText(String text) {
		
		try {
			
			// Mise en mode Decrypt
			cipher.init(Cipher.DECRYPT_MODE, this.createDESSecretKey(stringKey));
			
			// Obtention des bytes
			byte[] encodedByteText = new Base64().decode(text);
			
			// Decryption
			byte[] byteText = cipher.doFinal(encodedByteText);
			
			// Retour de la chaine
			return new String(byteText);
			
		} catch (Exception e) {
			
			// On relance l'exception
			throw new RuntimeException(e);
		}	
	}

	/**
	 * Methode d'encryptage d'un texte
	 * @param text	Texte a encrypter
	 * @return	Texte encrypte
	 */
	public synchronized String encryptText(String text) {
		
		try {
			// Mise en mode Encrypt
			cipher.init(Cipher.ENCRYPT_MODE, this.createDESSecretKey(stringKey));
			
			// Obtention des bytes
			byte[] byteText = text.getBytes();
			
			// Encodage
			byte[] encodedByteText = cipher.doFinal(byteText);
			
			// Retour de la chaine encodee
			return new Base64().encodeToString(encodedByteText);
			
		} catch (Exception e) {
			
			// On relance l'exception
			throw new RuntimeException(e);
		}
	}

	/**
	 * Methode de hachage d'un texte
	 * @param text	Texte a hacher
	 * @return	Texte hache
	 */
	public synchronized String hashText(String text) {
		
		// Hachage du texte
		return new Base64().encodeToString(digester.digest(text.getBytes()));
	}
	
	/**
	 * Methode de generation de cle prives sur la base d'un mot de passe
	 */
	private SecretKey createDESSecretKey(String keytext){
		
		try {
			
			// Generation de la cle DES bas�es sur une mot de passe
			DESKeySpec desKeySpec = new DESKeySpec(keytext.getBytes());
			
			// On retourne la cle DES
			return SecretKeyFactory.getInstance("DES").generateSecret(desKeySpec);
			
		} catch (Exception e) {
			
			// On relance l'exception
			throw new RuntimeException(e);
		}
	}
}
