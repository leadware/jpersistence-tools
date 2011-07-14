/**
 * 
 */
package com.yashiro.persistence.utils.test.dao.entities;

import com.yashiro.persistence.utils.test.dao.entities.base.TPSParameterBase;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Classe representant une region du systeme
 * @author Jean-Jacques
 * @see {@link TPSParameterBase}
 * @version 1.0
 */

@Entity(name = "Devise")
@Table(name = "GENEZIS_DEVISE")
public class Devise extends TPSParameterBase {

	/**
	 * ID genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Taux de change de la devise par rapport
	 * � la devise de r�f�rence
	 */
	private Float exchangeRate;
	
	/**
	 * Code ISO de la devise.
	 */
	private String codeISO;
	

	/**
	 * Constructeur par d�faut
	 */
	public Devise(){
		
		//Appel parent
		super();
	}
	
	/**
	 * Constructeur d'initialisation
	 * @param code	Code local de la devise
	 * @param designation	Description de la devise
	 * @param exchangeRate	Taux de change
	 * @param codeISO	Code ISO de la devise
	 */
	public Devise(String code, String designation, Float exchangeRate, String codeISO){

		//Appel du constructeur du parent
		super(code, designation);
		
		// On positionne les parametres additionnels
		this.codeISO = codeISO;
		this.exchangeRate = exchangeRate;
	}

	/**
	 * Methode permettant d'obtenir le taux de change de la devise
	 * par rapport � la devise de r�f�rence. 
	 * @return Taux de change
	 */
	public float getExchangeRate() {
		return exchangeRate;
	}

	/**
	 * Methode permettant de d�finir le taux de change 
	 * de la devise par rapport � la devise de r�f�rence
	 * @param exchangeRate
	 */
	public void setExchangeRate(float exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * Methode permettant d'obtenir le Code ISO de la devise
	 * @return Code ISO
	 */
	public String getCodeISO() {
		return codeISO;
	}
	
   /**
    * Methode permettant de d�finir le Code ISO de la devise.
    * @param codeISO 
    */
	public void setCodeISO(String codeISO) {
		this.codeISO = codeISO;
	}
}
