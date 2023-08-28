package dataSources;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.sql.SQLException;
import java.time.*;

import businessObjects.*;
import dataAccessObject.DAO;
import exceptions.BankException;
import util.BankExceptionType;
public class TransferDAO implements DAO<Transfer, Long>{

	private Connection conn;
	
	public TransferDAO(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public boolean insert(Transfer element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Transfer element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Transfer element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Transfer getSingle(Long key) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//TransferID, OriginAccountID, OriginDebitCardID, ReceiverAccountID, ReceiverDebitCardID, 
	//Amount, TransactionDate
	
	//String query = "INSERT INTO Transfer VALUES (?, ?, ?, ?, ?, ?)";
	public String generateTransaction(Transfer element) throws Exception {
		DebitCard origin = null; 
		DebitCard receiver = null;
		PreparedStatement updateOriginAccount = null, updateOriginDebitCard = null, 
				updateReceiverAccount = null, updateReceiverDebitCard = null, insert = null;
		try {
			if(element.getOriginAccount().hasFunds(element.getAmount())) {
				if(element.getOriginAccount().getDebitCards().size() > 0) {
					origin = element.getOriginAccount().getDefaultDebitCard();
					if(origin.getMoney() >= element.getAmount()) {
						origin.localChangeMoney(-element.getAmount());
						element.setOriginDebitCard(origin);
						updateOriginDebitCard = conn.prepareStatement("UPDATE DebitCard SET Funds=?"
								+ " WHERE DebitCardID=?");
						updateOriginDebitCard.setDouble(1, origin.getMoney());
						updateOriginDebitCard.setLong(2, origin.getProductID());
						updateOriginDebitCard.execute();
					}
				}
				updateOriginAccount = conn.prepareStatement("UPDATE Account SET Funds=? WHERE AccountID=?");
				element.getOriginAccount().localChangeMoney(-element.getAmount());
				updateOriginAccount.setDouble(1, element.getOriginAccount().getMoney());
				updateOriginAccount.setLong(2, element.getOriginAccount().getProductID());
				updateOriginAccount.execute();
				
				
				if(element.getReceiverAccount().getDebitCards().size() > 0) {
					receiver = element.getReceiverAccount().getDefaultDebitCard();
					receiver.localChangeMoney(element.getAmount());
					element.setReceiverDebitCard(receiver);
					updateReceiverDebitCard = conn.prepareCall("UPDATE DebitCard SET Funds=?"
								+ " WHERE DebitCardID=?");
					updateReceiverDebitCard.setDouble(1, receiver.getMoney());
					updateReceiverDebitCard.setLong(2, receiver.getProductID());
					updateReceiverDebitCard.execute();
				}
				updateReceiverAccount = conn.prepareStatement("UPDATE Account SET Funds=? WHERE AccountID=?");
				element.getReceiverAccount().localChangeMoney(element.getAmount());
				updateReceiverAccount.setDouble(1, element.getReceiverAccount().getMoney());
				updateReceiverAccount.setLong(2, element.getReceiverAccount().getProductID());
				updateReceiverAccount.execute();
				
				insert = conn.prepareStatement("INSERT INTO Transfer VALUES (?, ?, ?, ?, ?, ?)");
				insert.setLong(1, element.getOriginAccount().getProductID());
				if(origin != null) {
					insert.setLong(2, origin.getProductID());
				}else {
					insert.setString(2, null);;
				}
				insert.setLong(3, element.getReceiverAccount().getProductID());
				if(receiver != null) {
					insert.setLong(4, receiver.getProductID());
				}else {
					insert.setString(4, null);
				}
				insert.setDouble(5, element.getAmount());
				insert.setTimestamp(6, Timestamp.valueOf(element.getDate().toLocalDateTime()));
				insert.execute();
				conn.commit();
				return element.toString();
			}
		}catch(Exception e) {
			conn.rollback();
			BankException ex = new BankException(BankExceptionType.TRANSACTIONFAILED, e);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			return sw.toString();
			
		}finally {
			try {
				closeStatement(updateOriginAccount);
				closeStatement(updateOriginDebitCard);
				closeStatement(updateReceiverAccount);
				closeStatement(updateReceiverDebitCard);
				closeStatement(insert);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return element.toString();
	}
	
	public void closeStatement(PreparedStatement state) throws SQLException {
		if(state != null) {
			state.close();
		}
	}

}
