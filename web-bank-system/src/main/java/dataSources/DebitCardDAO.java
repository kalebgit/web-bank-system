package dataSources;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import businessObjects.Account;
import businessObjects.DebitCard;
import dataAccessObject.DAO;
import exceptions.BankException;
import util.BankExceptionType;

public class DebitCardDAO implements DAO<DebitCard, Long>{
	
	private Connection conn;
	
	public DebitCardDAO(Connection conn) {
		this.conn = conn;
	}
	
	public boolean insert(DebitCard element, Account owner) {
		
		String query = "INSERT INTO DebitCard(Number, NIP, Funds, IsDefault, AccountID) "
				+ " VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement p = conn.prepareStatement(query);
			p.setBigDecimal(1, element.getCardNumber());
			p.setInt(2, element.getNip());
			p.setDouble(3, element.getMoney());
			p.setInt(4, element.isDefault() ? 1 : 0);
			p.setLong(5, owner.getProductID());
			p.execute();
			p.close();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public boolean update(DebitCard element) {
		
		String query = "UPDATE DebitCard SET Number=?, NIP=?, Funds=?, IsDefault=? "
				+ "WHERE DebitCardID=? AND Number=? AND NIP=?";
		try {
			PreparedStatement p = conn.prepareStatement(query);
			p.setBigDecimal(1, element.getCardNumber());
			p.setInt(2, element.getNip());
			p.setDouble(3, element.getMoney());
			p.setInt(4, element.isDefault() ? 1 : 0);
			p.setLong(5, element.getProductID());
			p.setBigDecimal(6, element.getCardNumber());
			p.setInt(7, element.getNip());
			p.execute();
			p.close();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public boolean delete(DebitCard element) {
		
		String query = "DELETE FROM DebitCard WHERE DebitCardID=? AND Number=? AND NIP=? ";
		try {
			PreparedStatement p = conn.prepareStatement(query);
			p.setLong(1, element.getProductID());
			p.setBigDecimal(2, element.getCardNumber());
			p.setInt(3, element.getNip());
			p.execute();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean insert(DebitCard element) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
	public DebitCard getSingleByNumber(BigDecimal number) throws BankException {
		String query = "SELECT * FROM DebitCard WHERE Number=?";
		try {
			PreparedStatement p = conn.prepareStatement(query);
			p.setBigDecimal(1, number);
			ResultSet set = p.executeQuery();
			if(set.next()) {
				DebitCard debitcardfound = new DebitCard(set.getLong("DebitCardID"), 
						set.getBigDecimal("Number"), 
						set.getInt("NIP"), set.getDouble("Funds"), set.getInt("IsDefault") == 1 ? 
								true : false);
				p.close();
				set.close();
				return debitcardfound;
				
			}else {
				throw new BankException(BankExceptionType.CARDNOTFOUND);
			}
		}catch(Exception e) {
			throw new BankException(BankExceptionType.DAOSQLSERVER, e);
		}
	}
	
	public Set<DebitCard> getOwnerDebitCards(Account account)throws BankException{
		String query = "SELECT DebitCardID, Number, NIP, DebitCard.Funds, IsDefault FROM "
				+ "DebitCard "
				+ "INNER JOIN "
				+ "Account "
				+ " ON DebitCard.AccountID = Account.AccountID "
				+ " WHERE Account.BankCode=?";
		
		Set<DebitCard> debitCards = new HashSet<DebitCard>();
		try {
			PreparedStatement p = conn.prepareStatement(query);
			p.setBigDecimal(1, account.getBankCode());
			ResultSet set = p.executeQuery();
			while(set.next()) {
				debitCards.add(new DebitCard(set.getLong("DebitCardID"), set.getBigDecimal("Number"), 
						set.getInt("NIP"), set.getDouble("Funds"), set.getInt("IsDefault") == 1 ? 
								true : false));
			}
			p.close();
			set.close();
			return debitCards;
		}catch(Exception e) {
			throw new BankException(BankExceptionType.DAOSQLSERVER, e, " hubo un error en el proceso "
					+ "de busqueda de tarjetas que le pertenezcan a una cuenta");
		}
	}

	@Override
	public DebitCard getSingle(Long key) throws Exception {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
