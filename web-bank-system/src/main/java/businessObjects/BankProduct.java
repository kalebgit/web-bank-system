package businessObjects;

import java.util.Objects;

public abstract class BankProduct {
	
	protected Long productID;
	
	
	//constructors
	public BankProduct(long productID) {
		this.productID = Long.valueOf(productID);
	}

	public long getProductID() {
		return this.productID;
	}


	public void setProductID(long productID) {
		this.productID = productID;
	}

	//getters and setters

	
	//other members
	protected abstract String formatID();
	
	public String toString() {
		return formatID();
	}

	@Override
	public int hashCode() {
		return Objects.hash(productID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankProduct other = (BankProduct) obj;
		return Objects.equals(productID, other.productID);
	}
	
	
	
	
}
