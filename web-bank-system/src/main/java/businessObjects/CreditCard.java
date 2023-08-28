package businessObjects;
import util.*;

public class CreditCard extends BankProduct{
	
	public CreditCard(long productID) {
		super(productID);
	}
	
	@Override
	public String formatID() {
		return NumberFormatter.formatNumber(this.productID.longValue(), ProductFormatType.CARD.getFormat());
	}
	
	
}
