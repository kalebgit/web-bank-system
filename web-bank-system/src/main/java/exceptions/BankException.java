package exceptions;
import util.*;

public class BankException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String otherMessage;
	private BankExceptionType type;
	
	public BankException(BankExceptionType type) {
		super();
		this.type = type;
	}
	
	public BankException(BankExceptionType type, Exception e) {
		super(e);
		this.type = type;
	}
	
	public BankException(BankExceptionType type, Exception e, String otherMessage) {
		super(e);
		this.type = type;
		this.otherMessage = otherMessage;
	}
	
	public BankException(BankExceptionType type, String otherMessage) {
		super();
		this.type = type;
		this.otherMessage = otherMessage;
	}
	
	
	
	@Override
	public String toString() {
		switch(type) {
			case PRODUCTNOTFOUND: 
				return type.getMessage() + otherMessage;
			case PRODUCTTYPENOTDECLARED: 
				return type.getMessage()  + otherMessage;
			case WRONGFORMAT:
				return type.getMessage()  + otherMessage;
			case USERNOTFOUND:
				return type.getMessage()  + otherMessage;
			case DUPLICATEUSER:
				return type.getMessage()  + otherMessage;
			case DAOSQLSERVER:
				return type.getMessage()  + otherMessage;
			default: 
				return super.getMessage()  + otherMessage;
		}
	}
}
