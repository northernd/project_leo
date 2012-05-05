package fi.leoteam.playground.aitest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
public class LEOMapGrid {
	
	private ArrayList<ArrayList<LEOEntity>> mapgrid = new ArrayList<ArrayList<LEOEntity>>();
	private int rowLength;

	public LEOMapGrid() {
		super();
	}
	
	public ArrayList<LEOCharacter> importMapFile() {
		ArrayList<LEOCharacter> locations = new ArrayList<LEOCharacter>();
		try {
			 
			URL url = getClass().getResource("map.txt");
			FileInputStream input = new FileInputStream(new File(url.getPath()));
			InputStreamReader inputReader = new InputStreamReader(input);
			BufferedReader reader = new BufferedReader(inputReader);
			
			while (reader.ready()) {
				String line = reader.readLine();
				if(!line.startsWith("//")) {
					
					if(line.startsWith("@")) {
						String row = line.substring(1);
						String[] tmp = row.split(",");
						
						for(int i = 0; i < tmp.length; i++) {
							locations.add(new LEOCharacter(LEOStaticStrings.OBJECT_STRING_SUSPECT, tmp[i], this));
						}
						

					} else {
						String[] row = line.split(",");
						
						if(mapgrid.size() == 0) {
							rowLength = row.length;
						}
	
						ArrayList<LEOEntity> tmp = new ArrayList<LEOEntity>(rowLength);
						for(int i = 0; i < rowLength; i++) {
							tmp.add(LEOEntity.encodeMapMarkings(row[i],mapgrid.size()+"-"+i));
						}
						
						mapgrid.add(tmp);
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			LEOAITest.log.error("Virhe", e);
			System.exit(0);
			
		} catch (IOException e) {
			LEOAITest.log.error("Virhe", e);
			System.exit(0);
		}
		
		return locations;
	}
	
	public StringBuilder getMapAsString() {
		StringBuilder map = new StringBuilder("");
		for(int i = 0; i < mapgrid.size(); i++) {
			ArrayList<LEOEntity> tmp = mapgrid.get(i);
			for(int j = 0; j < tmp.size(); j++) {
				map.append(tmp.get(j).toString());
				
				if(j+1 < tmp.size()) {
					map.append(" ");
				}
			}
			map.append("\n");
		}
		
		return map;
	}

	public ArrayList<ArrayList<LEOEntity>> getMapgrid() {
		return mapgrid;
	}

	public void setMapgrid(ArrayList<ArrayList<LEOEntity>> mapgrid) {
		this.mapgrid = mapgrid;
	}

	public int getRowLength() {
		return rowLength;
	}
	
	public LEOEntity getAdjacentN(int[] loc) {
		try {
			return mapgrid.get(loc[0]-1).get(loc[1]);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public LEOEntity getAdjacentNE(int[] loc) {
		try {
			return mapgrid.get(loc[0]-1).get(loc[1]+1);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public LEOEntity getAdjacentE(int[] loc) {
		try {
			return mapgrid.get(loc[0]).get(loc[1]+1);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public LEOEntity getAdjacentSE(int[] loc) {
		try {
			return mapgrid.get(loc[0]+1).get(loc[1]+1);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public LEOEntity getAdjacentS(int[] loc) {
		try {
			return mapgrid.get(loc[0]+1).get(loc[1]);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public LEOEntity getAdjacentSW(int[] loc) {
		try {
			return mapgrid.get(loc[0]+1).get(loc[1]-1);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public LEOEntity getAdjacentW(int[] loc) {
		try {
			return mapgrid.get(loc[0]).get(loc[1]-1);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public LEOEntity getAdjacentNW(int[] loc) {
		try {
			return mapgrid.get(loc[0]-1).get(loc[1]-1);
		} catch (NullPointerException e) {
			return null;
		}
	}
}
