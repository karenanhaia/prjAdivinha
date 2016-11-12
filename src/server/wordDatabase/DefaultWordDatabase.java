package server.wordDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DefaultWordDatabase extends AbstractWordDatabase {
	
	private String fileName;

	public DefaultWordDatabase(String fileName) {
		super();
		this.fileName = fileName;
	}

	@Override
	public void load() throws IOException {
		BufferedReader bf = null;
		
		//try(BufferedReader dados = new BufferedReader(new InputStreamReader(getClass().getResource(fileName).openStream()))) {
		try {
			System.out.println(System.getProperty("user.dir"));
			InputStreamReader isr = new InputStreamReader(getClass().getResource(fileName).openStream());
			bf = new BufferedReader(isr);
			String line;
			
			while((line = bf.readLine()) != null) {
				super.add(line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			bf.close();
		}
		
	}

}
