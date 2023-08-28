package dataSources;

import java.sql.Connection;

public interface DAOManagerI {
	public abstract AccountDAO getAccountDAO();
	public abstract DebitCardDAO getDebitCardDAO();
	public abstract Connection getConnection();
}
