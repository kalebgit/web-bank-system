package businessObjects;
import java.sql.Timestamp;
import java.time.*;

import exceptions.BankException;
import util.BankExceptionType;
public class Transfer {
	
	private Long transferID;
	private Account originAccount;
	private DebitCard originDebitCard;
	private Account receiverAccount;
	private DebitCard receiverDebitCard;
	private double amount;
	private OffsetDateTime date;
	
	
	public Transfer(Long transferID, Account originAccount, Account receiverAccount, double amount) {
		this.transferID = transferID;
		this.originAccount = originAccount;
		this.receiverAccount = receiverAccount;
		this.amount = amount;
		this.date = OffsetDateTime.now();
	}


	public Long getTransferID() {
		return transferID;
	}


	public void setTransferID(Long transferID) {
		this.transferID = transferID;
	}


	public Account getOriginAccount() {
		return originAccount;
	}


	public void setOriginAccount(Account originAccount) {
		this.originAccount = originAccount;
	}


	public DebitCard getOriginDebitCard() {
		return originDebitCard;
	}


	public void setOriginDebitCard(DebitCard originDebitCard) {
		this.originDebitCard = originDebitCard;
	}


	public Account getReceiverAccount() {
		return receiverAccount;
	}


	public void setReceiverAccount(Account receiverAccount) {
		this.receiverAccount = receiverAccount;
	}


	public DebitCard getReceiverDebitCard() {
		return receiverDebitCard;
	}


	public void setReceiverDebitCard(DebitCard receiverDebitCard) {
		this.receiverDebitCard = receiverDebitCard;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public OffsetDateTime getDate() {
		return date;
	}


	public void setDate(OffsetDateTime date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "transfer | origin = " + this.getOriginAccount().getProductID() + ""
				+ " | card = " + (this.getOriginDebitCard() == null ? "false" : 
					this.getOriginDebitCard().getCardNumber() +
					" | receiver = " + this.getReceiverAccount().getProductID() + ""
							+ " | card = " + (this.getReceiverDebitCard() == null ? "false" : 
								this.getReceiverDebitCard().getCardNumber()) + 
							" | amount: " + this.getAmount() + " | date: " + 
							Timestamp.valueOf(this.getDate().toLocalDateTime()).toString());
	}
}
