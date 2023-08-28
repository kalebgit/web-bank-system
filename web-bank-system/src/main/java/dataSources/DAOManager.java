package dataSources;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import util.BankSystem;

public class DAOManager implements DAOManagerI{
	
	private AccountDAO accountDAO = null;
	private DebitCardDAO debitCardDAO = null;
	private TransferDAO transferDAO = null;
	private Connection conn;
	
	public DAOManager(boolean isTransaction) {
		this.conn = getConnection();
		this.accountDAO = new AccountDAO(conn);
		this.debitCardDAO = new DebitCardDAO(conn);
		
		try {
			if(isTransaction) {
				conn.setAutoCommit(false);
			}else {
				conn.setAutoCommit(true);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		this.transferDAO = new TransferDAO(conn);
	}
	
	public Connection getConnection() {
		Properties databaseInfo;
		Connection conn = null;
		try {
			databaseInfo = BankSystem.getPropertiesFrom("database.properties");
			conn = DriverManager.getConnection(databaseInfo.getProperty("sqlUrl"), databaseInfo.getProperty("sqlUser"), 
					databaseInfo.getProperty("sqlPassword"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	@Override
	public AccountDAO getAccountDAO() {
		return this.accountDAO;
	}

	@Override
	public DebitCardDAO getDebitCardDAO(){
		return this.debitCardDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public void setDebitCardDAO(DebitCardDAO debitCardDAO) {
		this.debitCardDAO = debitCardDAO;
	}

	public TransferDAO getTransferDAO() {
		return transferDAO;
	}

	public void setTransferDAO(TransferDAO transferDAO) {
		this.transferDAO = transferDAO;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	
	
}
