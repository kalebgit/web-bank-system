package util;

public enum ProductFormatType {
	ACCOUNT("###-#-#####/#"), BANKCODE("######## ##############"), CARD("#### #### #### ####"),
	CREDITCARDCODE("###"), BRANCH("####");
	
	private String format;
	
	private ProductFormatType(String format) {
		this.format = format;
	}
	
	public String getFormat() {
		return this.format;
	}
}
