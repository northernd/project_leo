package fi.leoteam.playground.aitest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.naming.spi.DirectoryManager;

public class LEOMapGrid {
	
	private ArrayList<ArrayList<LEOEntity>> mapgrid = new ArrayList<ArrayList<LEOEntity>>();

	public LEOMapGrid() {
		super();
		importMapFile();
		printMap();
	}
	
	private void importMapFile() {
		int rowLength = 0;
		try {
			 
			URL url = getClass().getResource("map.txt");
			FileInputStream input = new FileInputStream(new File(url.getPath()));
			InputStreamReader inputReader = new InputStreamReader(input);
			BufferedReader reader = new BufferedReader(inputReader);
			
			while (reader.ready()) {
				String line = reader.readLine();
				if(!line.startsWith("//")) {
					String[] row = line.split(",");
					
					if(mapgrid.size() == 0 || mapgrid.size() == 0) {
						rowLength = row.length;
					}
					ArrayList<LEOEntity> tmp = new ArrayList<LEOEntity>(rowLength);
					for(int i = 0; i < rowLength; i++) {
						tmp.add(LEOEntity.encodeMapMarkings(row[i]));
					}
					
					mapgrid.add(tmp);
				}
			}
			
		} catch (FileNotFoundException e) {
			LEOAITest.log.error("Virhe", e);
			System.exit(0);
			
		} catch (IOException e) {
			LEOAITest.log.error("Virhe", e);
			System.exit(0);
		}
		
		
		
	}
	
	public void printMap() {
		for(int i = 0; i < mapgrid.size(); i++) {
			ArrayList<LEOEntity> tmp = mapgrid.get(i);
			for(int j = 0; j < tmp.size(); j++) {
				System.out.print(tmp.get(j).toString()+" ");
			}
			System.out.println();
		}
	}
	

}
