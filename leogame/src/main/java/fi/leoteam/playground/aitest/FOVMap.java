package fi.leoteam.playground.aitest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * HUOM! TOIMII AINOASTAAN VÄLIAIKAISENA SÄILYTYSLUOKKANA
 * @author rsalo
 *
 */
public class FOVMap {
	private ArrayList<ArrayList<LEOEntity>> mapgrid = new ArrayList<ArrayList<LEOEntity>>();
	private String[][] fovMap;
	private int mapsize = 24;
	private int fovLength = 10;
	//private int rowLength;
	
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
							//locations.add(new LEOCharacter(LEOStaticStrings.OBJECT_STRING_SUSPECT, tmp[i], this));
						}
						

					} else {
							String[] row = line.split(",");
							
							/*if(mapgrid.size() == 0) {
								rowLength = row.length;
							}*/
							
							int rowLength = row.length;
		
							ArrayList<LEOEntity> tmp = new ArrayList<LEOEntity>(rowLength);
							for(int i = 0; i < rowLength; i++) {
								//System.out.println(i+","+mapgrid.size());
								tmp.add(LEOEntity.encodeMapMarkings(row[i],i+"-"+mapgrid.size()));
							}
							
							mapgrid.add(tmp);
						
					}
				}
			}
			
			//mapgrid = MapGenerator.generateMap(mapsize);
			fovMap = new String[mapsize][mapsize];
			
			for(int i = 0; i < mapsize; i++) {
				for(int j = 0; j < mapsize; j++) {
					fovMap[i][j] = "?";
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
			/*if(i%2 != 0) {
				map.append(" ");
			}*/
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
		tmp[0] = entity.getXCoord()-1;
		tmp[1] = entity.getYCoord();
		return tmp;
	}
	
	public int[] getAdjacentNE(LEOEntity entity) {
		int[] tmp = new int[2];
			tmp[0] = entity.getXCoord()-1;
			tmp[1] = entity.getYCoord()+1;
		
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
			tmp[0] = entity.getXCoord()+1;
			tmp[1] = entity.getYCoord()+1;

		
		return tmp;
	}
	
	public int[] getAdjacentS(LEOEntity entity) {
		int[] tmp = new int[2];
		tmp[0] = entity.getXCoord()+1;
		tmp[1] = entity.getYCoord();
		return tmp;
	}
	
	public int[] getAdjacentSW(LEOEntity entity) {
		int[] tmp = new int[2];
			tmp[0] = entity.getXCoord()+1;
			tmp[1] = entity.getYCoord()-1;

		
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
			tmp[0] = entity.getXCoord()-1;
			tmp[1] = entity.getYCoord()-1;

		
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
	
	public LEOEntity getEntityFromLocation(Square s) {
		LEOEntity result;
		try {
			result = mapgrid.get(s.getY()).get(s.getX());
		} catch (NullPointerException e) {
			result = null;
		}
		
		return result;
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
	
	private boolean isValidTile(Square s) {
		boolean result = false;
		
		if(s.getY() < 0 && s.getY() >= fovMap.length) {
			return result;
		}
		if(s.getX() < 0 && s.getX() >= fovMap.length) {
			return result;
		}
		
		return true;
	}
	
	public void calculateFOV(LEOEntity character) {
		Square point = new Square(character.getLocation());
		
		long start = System.currentTimeMillis();
		//First step, set every tile adjacent to character as visible
		insertCharacterToFOVMap(character);
		
		//Second step, calculate N, E, S and W directions
		calculateMainDirections(point);
		
		//printFOVMap();
		
		//Third step, calculate remaining areas
		//calculateRemainingAreasSE(point, point.getAdjacentSE().getAdjacentE(), point.getAdjacentSE().getAdjacentS());
		calculateRemainingAreasNE(point, point.getAdjacentNE().getAdjacentE(), point.getAdjacentNE().getAdjacentN());
		//calculateRemainingAreasNW(point, point.getAdjacentNW().getAdjacentW(), point.getAdjacentNW().getAdjacentN());
		//calculateRemainingAreasSW(point, point.getAdjacentSW().getAdjacentW(), point.getAdjacentSW().getAdjacentS());
		long end = System.currentTimeMillis()-start;
		
		System.out.println("Aika: "+end);
		
		printFOVMap();
		
		
		
		
	}
	
	private void calculateRemainingAreasSE(Square initial, Square point, Square point2) {
		int x = point.getX();
		int y = point.getY();
		int x2 = point2.getX();
		int y2 = point2.getY();
		int fovLength = this.fovLength*LEOStaticStrings.SQUARE_SIDE_LENGTH;
		Square tmp;
		boolean flag, roundFlag = true;
		do {	
			tmp = new Square(x, y);
			Square tmp2 = new Square(x2, y2);
			if(initial.twoPointsDistance(tmp) <= fovLength && initial.twoPointsDistance(tmp2) <= fovLength) {
				flag = true;
				for(int i = x; i < mapsize && y < mapsize && flag; i++) {
					//System.out.println("PISTE ("+i+","+y+")");
					tmp = new Square(i, y);
					if(initial.twoPointsDistance(tmp) <= fovLength) {
						checkIfToAddToFOVMap(initial, tmp, Square.SE_SECTOR);
					}
					else {
						flag = false;
					}
				}
				flag = true;
				for(int j = y2; j < mapsize && x2 < mapsize && flag; j++) {
					//System.out.println("PISTE ("+x2+","+j+")");
					tmp = new Square(x2, j);
					if(initial.twoPointsDistance(tmp) <= fovLength) {
						checkIfToAddToFOVMap(initial, tmp, Square.SE_SECTOR);
					}
					else {
						flag = false;
					}
				}
				x = x2+1;
				y++;
				
				x2++;
				y2 = y+1;
				
				//System.out.println("*****************************************************************");
				//System.out.println("x: "+x+" y: "+y+" x2:"+x2+" y2:"+y2);
			}
			else {
				roundFlag = false;
			}
		} while (((x < mapsize && y < mapsize) || (x2 < mapsize && y2 < mapsize)) && roundFlag);
		//printFOVMap();
	}
	
	private void calculateRemainingAreasSW(Square initial, Square point, Square point2) {
		int x = point.getX();
		int y = point.getY();
		int x2 = point2.getX();
		int y2 = point2.getY();
		int fovLength = this.fovLength*LEOStaticStrings.SQUARE_SIDE_LENGTH;
		Square tmp;
		boolean flag, roundFlag = true;
		do {
			tmp = new Square(x, y);
			Square tmp2 = new Square(x2, y2);
			if(initial.twoPointsDistance(tmp) <= fovLength && initial.twoPointsDistance(tmp2) <= fovLength) {
				flag = true;
				for(int i = x; i >= 0 && y < mapsize && flag; i--) {
					//System.out.println("PISTE ("+i+","+y+")");
					tmp = new Square(i, y);
					if(initial.twoPointsDistance(tmp) <= fovLength) {
						checkIfToAddToFOVMap(initial, tmp, Square.SW_SECTOR);
					}
					else {
						flag = false;
					}
				}
				flag = true;
				for(int j = y2; j < mapsize && x2 >= 0 && flag; j++) {
					//System.out.println("PISTE ("+x2+","+j+")");
					tmp = new Square(x2, j);
					if(initial.twoPointsDistance(tmp) <= fovLength) {
						checkIfToAddToFOVMap(initial, tmp, Square.SW_SECTOR);
					}
					else {
						flag = false;
					}
				}
				x = x2-1;
				y++;
				
				x2--;
				y2 = y+1;
				
				
				//System.out.println("*****************************************************************");
				//System.out.println("x: "+x+" y: "+y+" x2: "+x2+" y2: "+y2);
			}
			else {
				roundFlag = false;
			}
		} while (((x >= 0 && y < mapsize) || (x2 >= 0 && y2 < mapsize)) && roundFlag);
		//printFOVMap();
	}
	
	private void calculateRemainingAreasNE(Square initial, Square point, Square point2) {
		int x = point.getX();
		int y = point.getY();
		int x2 = point2.getX();
		int y2 = point2.getY();
		int fovLength = this.fovLength*LEOStaticStrings.SQUARE_SIDE_LENGTH;
		//System.out.println(point.toString()+" "+point2.toString());
		Square tmp;
		boolean flag, roundFlag = true;
		do {
			tmp = new Square(x, y);
			Square tmp2 = new Square(x2, y2);
			if(initial.twoPointsDistance(tmp) <= fovLength && initial.twoPointsDistance(tmp2) <= fovLength) {
				flag = true;
				for(int i = x; i < mapsize && y >= 0 && flag; i++) {
					//System.out.println("PISTE ("+i+","+y+")");
					tmp = new Square(i, y);
					if(initial.twoPointsDistance(tmp) <= fovLength) {
						checkIfToAddToFOVMap(initial, tmp, Square.NE_SECTOR);
					}
					else {
						flag = false;
					}
	
				}
				flag = true;
				for(int j = y2; j >= 0 && x2 < mapsize && flag; j--) {
					//System.out.println("PISTE ("+x2+","+j+")");
					tmp = new Square(x2, j);
					if(initial.twoPointsDistance(tmp) <= fovLength) {
						checkIfToAddToFOVMap(initial, tmp, Square.NE_SECTOR);
					}
					else {
						flag = false;
					}
				}
				x = x2+1;
				y--;
				
				x2++;
				y2 = y-1;
				
				//System.out.println("*****************************************************************");
				//System.out.println("x: "+x+" y: "+y+" x2:"+x2+" y2:"+y2);
			}
			else {
				roundFlag = false;
			}
		} while (((x < mapsize && y >= 0) || (x2 < mapsize && y2 >= 0)) && roundFlag);
		//printFOVMap();
	}
	
	private void calculateRemainingAreasNW(Square initial, Square point, Square point2) {
		int x = point.getX();
		int y = point.getY();
		int x2 = point2.getX();
		int y2 = point2.getY();
		int fovLength = this.fovLength*LEOStaticStrings.SQUARE_SIDE_LENGTH;
		Square tmp;
		boolean flag, roundFlag = true;
		//System.out.println(point.toString()+" "+point2.toString());
		do {
			tmp = new Square(x, y);
			Square tmp2 = new Square(x2, y2);
			if(initial.twoPointsDistance(tmp) <= fovLength && initial.twoPointsDistance(tmp2) <= fovLength) {
				flag = true;
				for(int i = x; i >= 0 && y >= 0 && flag; i--) {
					//System.out.println("PISTE ("+i+","+y+")");
					tmp = new Square(i, y);
					if(initial.twoPointsDistance(tmp) <= fovLength) {
						checkIfToAddToFOVMap(initial, tmp, Square.NW_SECTOR);
					}
					else {
						flag = false;
					}
					
				}
				flag = true;
				for(int j = y2; j >= 0 && x2 < mapsize && flag; j--) {
					//System.out.println("PISTE ("+x2+","+j+")");
					tmp = new Square(x2, j);
					if(initial.twoPointsDistance(tmp) <= fovLength) {
						checkIfToAddToFOVMap(initial, tmp, Square.NW_SECTOR);
					}
					else {
						flag = false;
					}
				}
				x = x2-1;
				y--;
				
				x2--;
				y2 = y-1;
				//System.out.println("*****************************************************************");
				//System.out.println("x: "+x+" y: "+y+" x2:"+x2+" y2:"+y2);
			}
			else {
				roundFlag = false;
			}
			
			
		} while (((x >= 0 && y >= 0) || (x2 >= 0 && y2 >= 0)) && roundFlag);
		//printFOVMap();
	}
	
	private void checkIfToAddToFOVMap(Square initial, Square s, int sector) {
		ArrayList<Square> possibilities = s.getPossibleLastSquares(sector);
		boolean result = false;
		System.out.println("******************\n Checking point "+s.toString());
		for(int i = 0; i < possibilities.size() && !result; i++) {
			Square tmp = possibilities.get(i);
			System.out.println(i+". mahdollisuus: "+tmp.toString());
			if(tmp.isSquareOnLine(initial, s)) {
				System.out.println("Is on line");
				if(isTileVisibleInFOVMap(tmp)) {
					System.out.println("Is visible");
					result = getEntityFromLocation(tmp).isBlockFOV();
					System.out.println("Blocks sight: "+result);
				}
				else {
					System.out.println("Is not visible");
					result = true;
				}
				
			}
		}
		
		if(!result) {
			System.out.println("Point will be visible on map");
			addToFOVMap(s);
		}
	}
	
	private void calculateMainDirections(Square point) {
		//North
		int x, y;
		int calc = 1;
		
		x = point.getX();
		for(int i = point.getY()-2; i >= 0 && calc <= fovLength; i--) {
			Square tmp = new Square(x, i);
			Square tmp2 = tmp.getAdjacentS();
			if(isTileVisibleInFOVMap(tmp2)) {
				if(!getEntityFromLocation(tmp2).isBlockFOV()) {
					addToFOVMap(tmp);
				}
			}
			calc++;
		}
		
		calc = 1;
		//South
		for(int i = point.getY()+2; i < mapsize && calc <= fovLength; i++) {
			Square tmp = new Square(x, i);
			Square tmp2 = tmp.getAdjacentN();
			if(isTileVisibleInFOVMap(tmp2)) {
				if(!getEntityFromLocation(tmp2).isBlockFOV()) {
					addToFOVMap(tmp);
				}
			}
			calc++;
		}
		
		calc = 1;
		//West
		y = point.getY();
		for(int i = point.getX()-2; i >= 0 && calc <= fovLength; i--) {
			Square tmp = new Square(i, y);
			Square tmp2 = tmp.getAdjacentE();
			if(isTileVisibleInFOVMap(tmp2)) {
				if(!getEntityFromLocation(tmp2).isBlockFOV()) {
					addToFOVMap(tmp);
				}
			}
			calc++;
		}
		
		calc = 1;
		y = point.getY();
		for(int i = point.getX()+2; i < mapsize && calc <= fovLength; i++) {
			Square tmp = new Square(i, y);
			Square tmp2 = tmp.getAdjacentW();
			if(isTileVisibleInFOVMap(tmp2)) {
				if(!getEntityFromLocation(tmp2).isBlockFOV()) {
					addToFOVMap(tmp);
				}
			}
			calc++;
		}
	}
	
	private boolean isTileVisibleInFOVMap(Square s) {
		if(isValidTile(s)) {
			if(fovMap[s.getY()][s.getX()].equals("?")) {
				return false;
			}
		}

		return true;
	}
	
	private void insertCharacterToFOVMap(LEOEntity character) {
		Square point = new Square(character.getLocation());
		fovMap[point.getY()][point.getX()] = character.toString();
		
		Square tmp = point.getAdjacentN();
		addToFOVMap(tmp);
		
		tmp = point.getAdjacentNE();
		addToFOVMap(tmp);
		
		tmp = point.getAdjacentE();
		addToFOVMap(tmp);
		
		tmp = point.getAdjacentSE();
		addToFOVMap(tmp);
		
		tmp = point.getAdjacentS();
		addToFOVMap(tmp);
		
		tmp = point.getAdjacentSW();
		addToFOVMap(tmp);
		
		tmp = point.getAdjacentW();
		addToFOVMap(tmp);
		
		tmp = point.getAdjacentNW();
		addToFOVMap(tmp);
	}
	
	private void printFOVMap() {
		System.out.print("   ");
		for(int i = 0; i < fovMap.length; i++) {
			if(i >= 10) {
				System.out.print(i+" ");
			}
			else {
				System.out.print(i+"  ");
			}
			
		}
		System.out.println("");
		for(int i = 0; i < fovMap.length; i++) {
			if(i >= 10) {
				System.out.print(i+" ");
			}
			else {
				System.out.print(i+"  ");
			}
			for(int j = 0; j < fovMap.length; j++) {
				System.out.print(fovMap[i][j]+"  ");
			}
			System.out.println("");
		}
	}
	
	private void addToFOVMap(Square loc) {
		int x = loc.getX();
		int y = loc.getY();
		if(x >= 0 && x < fovMap.length && y >= 0 && y < fovMap.length) {
			fovMap[y][x] = getEntityFromLocation(loc).toString();
		}
	}
	
	public void calculateFOV(int[] point, LEOEntity character) {
		Square point1 = new Square(character.getLocation());
		
		//Square point2 = new Square(299,299);
		long startTime = System.currentTimeMillis();
		//ArrayList<Square>possibleSquares = Square.getPossibleSquares(point1, point2);
		//calculateIntersection(possibleSquares, point1, point2);
		for(int i = 0; i < 50; i++) {
			for(int j = 0; j < 50; j++) {
				Square tmp = new Square(i, j);
				if(!tmp.equals(point1)) {
					calculateFOVToSinglePoint(point1, tmp);
				}
			}
		}
		
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("Kulunut aika: "+endTime);
	}
	
	private boolean calculateFOVToSinglePoint(Square startPoint, Square targetPoint) {
		boolean resultFlag = false;
		int sector = Square.getPossibleSector(startPoint, targetPoint);
		ArrayList<Square> nextOnes = startPoint.getPossibleNextSquares(sector);
		Square current = startPoint;
		boolean outerFlag = true;
		
		while(outerFlag && !resultFlag) {
			
			if(nextOnes.contains(targetPoint)) {
				outerFlag = false;
			}
			else {
				for(int i = 0; i < nextOnes.size(); i++) {
					Square tmp = nextOnes.get(i);
					if(tmp.isSquareOnLine(startPoint, targetPoint)) {
						resultFlag = getEntityFromLocation(tmp).isBlockFOV();
						current = tmp;
						System.out.println("****** Ruutu "+tmp.getLocationAsSimpleString()+" blockkaa näkymän "+resultFlag);
					}
				}
			}
			
			nextOnes = current.getPossibleNextSquares(sector);
			
		}
		
		resultFlag = !resultFlag;
		System.out.println("Näköyhteys pisteiden välillä on: "+resultFlag);
		
		return resultFlag;
	}
	
	public ArrayList<Square> calculateIntersection(ArrayList<Square> possibleSquares, Square point1, Square point2) {
		long startTime = System.currentTimeMillis();
		ArrayList<Square> onLine = new ArrayList<Square>();
		for(int i = 0; i < possibleSquares.size(); i++) {
			Square tmp = possibleSquares.get(i);
			if(!tmp.equals(point1) && !tmp.equals(point2)) {
				if(tmp.isSquareOnLine(point1, point2)) {
					onLine.add(tmp);
				}
			}
			
		}
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("Kulunut aika: "+endTime);
		System.out.println("**** Following squares are on the line ****");
		for(int i = 0; i < onLine.size(); i++) {
			System.out.println(onLine.get(i).toString());
		}
		System.out.println("********");

		return onLine;
	}
}
