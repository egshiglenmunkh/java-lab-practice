package GitHub_testing;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Reverse {
	public static void main(String[] args) throws IOException{
		
		ArrayList<String> lines = new ArrayList<>();
		
		try( FileInputStream fis = new FileInputStream ("input.txt");
		InputStreamReader isr = new InputStreamReader (fis);
		BufferedReader br = new BufferedReader(isr)){
			String line;
			
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			System.err.println("Error reading:" + e.getMessage());
			return;
		}
		
		try( FileOutputStream fos = new FileOutputStream("output.txt");
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw)){
			for(int i = lines.size() - 1; i >= 0; i--) {
				bw.write(lines.get(i));
				bw.newLine();
			}
		} catch (IOException e) {
			System.err.println("Error writing:" + e.getMessage());
		}
		
	}
}
