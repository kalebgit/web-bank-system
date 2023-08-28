package util;

public class NumberFormatter {
	public static <T> String formatNumber(T number, String format) {
		char[] stringNumber = number.toString().toCharArray();
		
		String result = "";
		int i = 0;
		for(char c : format.toCharArray()) {
			if(c == '#') {
				result += stringNumber[i];
				i++;
			}
		}
		
		return result;
	}
}
