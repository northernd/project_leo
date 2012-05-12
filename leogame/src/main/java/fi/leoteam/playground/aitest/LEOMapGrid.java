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
	//private int rowLength;

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
						
						/*if(mapgrid.size() == 0) {
							rowLength = row.length;
						}*/
						
						int rowLength = row.length;
	
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
			if(i%2 != 0) {
				map.append(" ");
			}
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

	/*public int getRowLength() {
		return rowLength;
	}*/
	
	public int[] getAdjacentN(LEOEntity entity) {
		int[] tmp = new int[2];
		tmp[0] = entity.getXCoord()-2;
		tmp[1] = entity.getYCoord();
		return tmp;
	}
	
	public int[] getAdjacentNE(LEOEntity entity) {
		int[] tmp = new int[2];
		if(entity.getYCoord()%2 == 0) {
			tmp[0] = entity.getXCoord()-1;
			tmp[1] = entity.getYCoord()+1;
		} else {
			tmp[0] = entity.getXCoord()-1;
			tmp[1] = entity.getYCoord();
			
		}
		
		return tmp;
	}
	
	public int[] getAdjacentE(LEOEntity entity) {
		int[] tmp = new int[2];
		tmp[0] = entity.getXCoord();
		tmp[1] = entity.getYCoord()+1;
		return tmp;
	}
	
	public int[] getAdjacentSE(LEOEntity entity) {
		int[] tmp = new int[2];
		if(entity.getYCoord()%2 == 0) {
			tmp[0] = entity.getXCoord()+1;
			tmp[1] = entity.getYCoord()+1;
		} else {
			tmp[0] = entity.getXCoord()-1;
			tmp[1] = entity.getYCoord();
			
		}
		
		return tmp;
	}
	
	public int[] getAdjacentS(LEOEntity entity) {
		int[] tmp = new int[2];
		tmp[0] = entity.getXCoord()+2;
		tmp[1] = entity.getYCoord();
		return tmp;
	}
	
	public int[] getAdjacentSW(LEOEntity entity) {
		int[] tmp = new int[2];
		if(entity.getYCoord()%2 == 0) {
			tmp[0] = entity.getXCoord()+1;
			tmp[1] = entity.getYCoord()-1;
		} else {
			tmp[0] = entity.getXCoord()+1;
			tmp[1] = entity.getYCoord();
			
		}
		
		return tmp;
	}
	
	public int[] getAdjacentW(LEOEntity entity) {
		int[] tmp = new int[2];
		tmp[0] = entity.getXCoord();
		tmp[1] = entity.getYCoord()-1;
		return tmp;
	}
	
	public int[] getAdjacentNW(LEOEntity entity) {
		int[] tmp = new int[2];
		if(entity.getYCoord()%2 == 0) {
			tmp[0] = entity.getXCoord()-1;
			tmp[1] = entity.getYCoord()-1;
		} else {
			tmp[0] = entity.getXCoord()-1;
			tmp[1] = entity.getYCoord();
			
		}
		
		return tmp;
	}
	
	private LEOEntity addEntityToLocation(int[] loc, LEOEntity entity) {
		LEOEntity tmp = mapgrid.get(loc[0]).get(loc[1]);
		
		mapgrid.get(loc[0]).set(loc[1], entity);
		
		return tmp;
	}
	
	public String getMapWithCharacters(ArrayList<LEOCharacter> characters) {
		ArrayList<ArrayList<LEOEntity>> mapgrid = this.mapgrid;
		String result = "";
		ArrayList<LEOEntity> oldOnes = new ArrayList<LEOEntity>();
		for(int i = 0; i < characters.size(); i++) {
			LEOCharacter tmp = characters.get(i);
			int[] loc = tmp.getLocation();
			oldOnes.add(addEntityToLocation(loc, tmp));
		}
		
		result = getMapAsString().toString();
		
		for(int i = 0; i < oldOnes.size(); i++) {
			addEntityToLocation(oldOnes.get(i).getLocation(), oldOnes.get(i));
		}
		
		return result;
	}
	
	public LEOEntity getEntityFromLocation(int[] loc) {
		return mapgrid.get(loc[0]).get(loc[1]);
	}
	
	private boolean isValidTile(int[] loc) {
		boolean result = false;
		
		if(mapgrid.get(loc[0]) == null) {
			return result;
		}
		if(mapgrid.get(loc[0]).get(loc[1]) == null) {
			return result;
		}
		
		return true;
	}
	
	public void calculateFOV(boolean straight, LEOEntity character) {
		int flag = 0;
		if(straight) {
			while(flag != -1) {
				
			}
			if(flag == 0) {
				int[] start, end;
				
			}
		}
	}
}
