package businessObjects;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import dataAccessObject.DAO;
import dataSources.*;
import exceptions.BankException;
import util.*;

public class DebitCard extends BankProduct {
	
	private BigDecimal cardNumber;
	private double money;
	private int nip;
	private boolean isDefault;
	
	public DebitCard(long productID, BigDecimal cardNumber, int nip, double money, boolean isDefault) throws BankException  {
		super(productID);
		this.money = money;
		if((cardNumber.compareTo(new BigDecimal("1000000000000000")) == 1 && 
				cardNumber.compareTo(new BigDecimal("9999999999999999")) == -1) 
				&&
			(nip > 1000 && nip < 9999)) {
			this.cardNumber = cardNumber;
			this.nip = nip;
		}
		else {
			throw new BankException(BankExceptionType.WRONGFORMAT);
		}
		this.isDefault = isDefault;
	}
	
	public double getMoney() {
		return this.money;
	}
	
	public void localChangeMoney(double money) {
		this.money += money;
	}
	
	public void updateMoney(double money) {
		this.money += money;
		DAOManager dao = new DAOManager(false);
		DebitCardDAO debitcarddao = dao.getDebitCardDAO();
		debitcarddao.update(this);
	}
	
	public BigDecimal getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(BigDecimal cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	

	public int getNip() {
		return nip;
	}

	public void setNip(int nip) {
		this.nip = nip;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public String formatID() {
		return NumberFormatter.formatNumber(this.productID.byteValue(), ProductFormatType.CARD.getFormat());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cardNumber);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DebitCard other = (DebitCard) obj;
		return Objects.equals(cardNumber, other.cardNumber);
	}

	

	
	
	
	
	
	
}
