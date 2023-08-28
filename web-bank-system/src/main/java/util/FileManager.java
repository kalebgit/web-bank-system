package util;
import java.io.*;


public class FileManager {
	public static void createFile(String fileName) {
		File file = new File(fileName);
		try {
			PrintWriter output = new PrintWriter(file);
			output.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void writeFile(String fileName, String content) {
		File file = new File(fileName);
		try {
			PrintWriter output = new PrintWriter(file);
			output.println(content);
			output.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void addInfoFile(String fileName, String content) {
		File file = new File(fileName);
		try {
			PrintWriter output = new PrintWriter(new FileWriter(file, true));
			output.println(content);
			output.close();
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readFile(String fileName) {
		File file = new File(fileName);
		try {
			BufferedReader input = new BufferedReader(new FileReader(fileName));
			String lecture = input.readLine();
			while(lecture != null) {
				
				lecture = input.readLine();
			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
