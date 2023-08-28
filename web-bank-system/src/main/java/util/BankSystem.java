package util;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;

import businessObjects.*;
import dataSources.*;
import exceptions.BankException;

public class BankSystem {
	
	public static Account login(String userName, String password) throws Exception{
		DAOManager manager= new DAOManager(false);
		AccountDAO accountdao = manager.getAccountDAO();
		return accountdao.login(userName, password);
	}
	
	public static boolean register(Account newAccount) throws BankException{
		DAOManager manager= new DAOManager(false);
		AccountDAO accountdao = manager.getAccountDAO();
		if(!accountdao.exists(newAccount)) {
			accountdao.insert(newAccount);
			return true;
		}else {
			throw new BankException(BankExceptionType.DUPLICATEUSER);
		}
	}
	
//	public static String generateTransaction(Account account) throws BankException {
//		
//			try {
//				Account receiver = findAccount(new BigDecimal("111111111111111111"));
//				double amount = 500;
//				account.transferTo(receiver, 500);
//				
//				return account.getUserName() + " ha transferido $" + amount + " a " + receiver.getUserName() +
//						"date: " + java.time.ZonedDateTime.now().toString();
//			}catch(BankException e) {
//				StringWriter sw = new StringWriter();
//				PrintWriter pw = new PrintWriter(sw);
//				e.printStackTrace(pw);
//				return sw.toString();
//			}
//	}
	
	public static boolean writeLog(String route, String message) {
		File file = new File("./" + route);
		if(file.isFile()) {
			try(BufferedWriter output = new BufferedWriter(new FileWriter(file, true))){
				output.newLine();
				output.write(message);
				return true;
					
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return false;
	}
	
	
	public static <K, V> boolean createPropertiesFile(String fileName, Map<K, V> properties) {
		File file = new File("./" + fileName + ".properties");
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))){
			for(Entry<K, V> data : properties.entrySet()) {
				writer.write(data.getKey() + "=" + data.getValue());
				writer.newLine();
			}
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static Properties getPropertiesFrom(String fileName) throws FileNotFoundException, IOException {
		File file = new File("./" + fileName);
		Properties p = new Properties();
		p.load(new BufferedReader(new FileReader(file)));
		return p;
	}

	
	public static void run() {
		
	}
	
}
