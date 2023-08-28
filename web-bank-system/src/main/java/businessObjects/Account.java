package businessObjects;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.*;

import dataAccessObject.DAO;
import dataSources.*;

import exceptions.BankException;
import util.*;

public class Account extends BankProduct{
	private String userName;
	private String password;
	private BigDecimal bankCode;
	private double money;
	private Set<DebitCard> debitCards;
	
	//GETTERS AND SETTERS
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigDecimal getBankCode() {
		return bankCode;
	}

	public void setBankCode(BigDecimal bankCode) {
		this.bankCode = bankCode;
	}
	
	
	
	
	public Set<DebitCard> getDebitCards() {
		return debitCards;
	}

	public void setDebitCards(Set<DebitCard> debitCards) {
		this.debitCards = debitCards;
	}

	public Account(long productID, String userName, String password, BigDecimal bankCode,
			double money) 
			throws BankException {
		
		super(productID);
		
		if(checkPasswordFormat(password)) {
			this.setPassword(password);
		}else {
			throw new BankException(BankExceptionType.WRONGFORMAT);
		}
		if((bankCode.compareTo(new BigDecimal("100000000000000000")) == 1 &&
				(bankCode.compareTo(new BigDecimal("999999999999999999")) == -1))) {
			this.bankCode = bankCode;
		}else {
			throw new BankException(BankExceptionType.WRONGFORMAT);
		}
		DAOManager manager = new DAOManager(false);
		DebitCardDAO debitcarddao = manager.getDebitCardDAO();
		this.userName = userName;
		this.debitCards = debitcarddao.getOwnerDebitCards(this);
		this.money = money;
	}
	
	
	
	public boolean checkPasswordFormat(String password) throws BankException{
		char[] passwordArray = password.toCharArray();
		char[] symbols = new char[] {'*', '#', '$', '%'};
		if((passwordArray[0] >= 65 && passwordArray[0] <= 90) && passwordArray.length > 12) {
			for(int i = 0; i < passwordArray.length; i++) {
				for(int y = 0; y < symbols.length; y++) {
					if(symbols[y] == passwordArray[i]) {
						return true;
					}
				}
			}
			throw new BankException(BankExceptionType.WRONGFORMAT);
		}
		throw new BankException(BankExceptionType.WRONGFORMAT);
	}
	
	public double getMoney() {
		return this.money;
	}
	
	public void localChangeMoney(double money) {
		this.money += money;
	}
	
	private void updateMoney(double money) {
		this.money += money;
		DAOManager manager = new DAOManager(false);
		AccountDAO accountdao = manager.getAccountDAO();
		accountdao.update(this);
	}
	
	public boolean hasFunds(double money) throws BankException{
		if(this.money >= money) {
			return true;
		}
		throw new BankException(BankExceptionType.NOTENOUGHMONEY);
	}
	
	public boolean withdrawMoney(double money) throws BankException{
		if(hasFunds(money)) {
			if(this.debitCards.size() > 0) {
				DebitCard dcdefault= getDefaultDebitCard();
				if(dcdefault.getMoney() >= money) {
					dcdefault.updateMoney(-money);
				}
			}
			this.updateMoney(-money);
			return true;
		}else {
			throw new BankException(BankExceptionType.NOTENOUGHMONEY, " no hay suficiente dinero "
					+ "en la cuenta");
		}


	}
	
	public boolean addMoney(double money) throws BankException{
		try {
			if(this.debitCards.size() > 0) {
				DebitCard card = this.getDefaultDebitCard();
				card.updateMoney(money);
			}
			this.updateMoney(money);
			return true;
		}catch(BankException e) {
			throw new BankException(BankExceptionType.TRANSACTIONFAILED, e);
		}
		
		
	}
	
	
	public void addDebitCard(DebitCard debitCard) {
		if(this.debitCards.size() == 0) {
			debitCard.setDefault(true);
		}
		DAOManager manager = new DAOManager(false);
		DebitCardDAO dcdao= manager.getDebitCardDAO();
		dcdao.insert(debitCard, this);
		this.debitCards.add(debitCard);
	}
	
	public void removeDebitCard(BigDecimal cardNumber) throws BankException {
		DAOManager manager = new DAOManager(false);
		DebitCardDAO dcdao= manager.getDebitCardDAO();
		DebitCard dcfound = dcdao.getSingleByNumber(cardNumber);
		dcdao.delete(dcfound);
		this.debitCards.remove(dcfound);
	}
	
	public DebitCard getDefaultDebitCard() throws BankException{
		for(DebitCard card : this.debitCards) {
			if(card.isDefault()) {
				return card;
			}
		}
		throw new BankException(BankExceptionType.CARDNOTFOUND, ", no se encontro tarjeta por default");
	}
	
	public DebitCard findAccountDebitCard(BigDecimal cardNumber) throws BankException {
		 for(DebitCard card : this.debitCards) {
			 if(card.getCardNumber() == cardNumber) {
				 return card;
			 }
		 }
		 throw new BankException(BankExceptionType.CARDNOTFOUND);
	}
	
	
	
	@Override
	public String formatID() {
		return NumberFormatter.formatNumber(this.productID.longValue(), ProductFormatType.ACCOUNT.getFormat());
	}

	

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(bankCode, userName);
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
		Account other = (Account) obj;
		return Objects.equals(bankCode, other.bankCode) && Objects.equals(userName, other.userName);
	}

}
