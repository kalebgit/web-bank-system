package dataSources;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import businessObjects.Account;
import dataAccessObject.DAO;
import exceptions.BankException;
import util.BankExceptionType;

public class AccountDAO implements DAO<Account, Long>{
	
	private Connection conn;
	
	public AccountDAO(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public boolean insert(Account element) {
		String query = "INSERT INTO Account(BankCode, Username, Password, Funds) VALUES "
				+ " (?, ?, ?, ?)";
		PreparedStatement p;
		try {
			p = conn.prepareStatement(query);
			p.setBigDecimal(1,element.getBankCode());
			p.setString(2, element.getUserName());
			p.setString(3, element.getPassword());
			p.setDouble(4, element.getMoney());
			p.execute();
			p.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Account element) {
		String query = "UPDATE Account SET BankCode=?, Username=?, password=?, funds=? "
				+ "WHERE AccountID=? AND BankCode=? AND Username=?";
		try {
			PreparedStatement p = conn.prepareStatement(query);
			p.setBigDecimal(1, element.getBankCode());
			p.setString(2, element.getUserName());
			p.setString(3, element.getPassword());
			p.setDouble(4, element.getMoney());
			p.setLong(5, element.getProductID());
			p.setBigDecimal(6, element.getBankCode());
			p.setString(7, element.getUserName());
			p.execute();
			p.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean delete(Account element) {
		String query = "DELETE FROM Account WHERE AccountID=? AND BankCode=? AND Username=? ";
		try {
			PreparedStatement p = conn.prepareStatement(query);
			p.setLong(1, element.getProductID());
			p.setBigDecimal(2, element.getBankCode());
			p.setString(3, element.getUserName());
			p.execute();
			p.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	@Override
	public Account getSingle(Long key) throws Exception {
		String query = "SELECT * FROM Account WHERE AccountID=?";
		try {
			PreparedStatement p = conn.prepareStatement(query);
			p.setLong(1, key);
			ResultSet set = p.executeQuery();
			if(set.next()) {
				Account accountFound = new Account(set.getLong("AccountID"),
						set.getString("Username"), set.getString("Password"), 
						set.getBigDecimal("BankCode"), 
						set.getDouble("Funds"));
				set.close();
				return accountFound;
			}else {
				set.close();
				throw new BankException(BankExceptionType.DAOSQLSERVER, "Usuario no encontrado con ese id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new BankException(BankExceptionType.DAOSQLSERVER, "Usuario no encontrado con ese id");
	}
	
	public Account getSingleByBankCode(BigDecimal bankCode) throws Exception {
		String query = "SELECT * FROM Account WHERE BankCode=?";
		try {
			PreparedStatement p = conn.prepareStatement(query);
			p.setBigDecimal(1, bankCode);
			ResultSet set = p.executeQuery();
			if(set.next()) {
				Account accountFound = new Account(set.getLong("AccountID"),
						set.getString("Username"), set.getString("Password"), 
						set.getBigDecimal("BankCode"), 
						set.getDouble("Funds"));
				set.close();
				return accountFound;
			}else {
				set.close();
				throw new BankException(BankExceptionType.DAOSQLSERVER, "Usuario no encontrado con ese id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new BankException(BankExceptionType.DAOSQLSERVER, "Usuario no encontrado con ese id");
	}
	
	public Account login(String username, String password) throws Exception {
		String query = "SELECT * FROM Account WHERE Username=? AND Password=?";
		try {
			PreparedStatement p = conn.prepareStatement(query);
			p.setString(1, username);
			p.setString(2, password);
			ResultSet set = p.executeQuery();
			if(set.next()) {
				Account accountfound = new Account(set.getLong("AccountID"),
						set.getString("Username"), set.getString("Password"), 
						set.getBigDecimal("BankCode"), 
						set.getDouble("Funds"));
				set.close();
				return accountfound;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new BankException(BankExceptionType.USERNOTFOUND, e, ": NO SE ENCONTRO UN USUARIO ");
		} catch (BankException e) {
			// TODO Auto-generated catch block
			throw new BankException(BankExceptionType.USERNOTFOUND, e, ": NO SE ENCONTRO UN USUARIO ");
		}
		throw new BankException(BankExceptionType.USERNOTFOUND,": NO SE ENCONTRO UN USUARIO ");
	}
	
	public boolean exists(Account element) throws BankException {
		String query = "SELECT * FROM Account WHERE BankCode=?";
		try {
			PreparedStatement p = conn.prepareStatement(query);
			p.setBigDecimal(1, element.getBankCode());
			ResultSet set = p.executeQuery();
			if(set.next()) {
				set.close();
				return true;
			}else {
				set.close();
				return false;
			}
		}catch(Exception e) {
			throw new BankException(BankExceptionType.DUPLICATEUSER, e);
		}
	}
}
