package fi.leoteam.playground.aitest;

import java.util.ArrayList;

public class LEOMapHandler {
	
	LEOMapGrid grid;
	ArrayList<LEOCharacter> characterLocations;

	public LEOMapHandler() {
		super();
		grid = new LEOMapGrid();
		characterLocations = new ArrayList<LEOCharacter>();
		characterLocations.addAll(grid.importMapFile());
		
		characterLocations.get(0).calculateFOV();
	}
	
	public void printDebugMap() {
		StringBuilder map = grid.getMapAsString();
		
		for(int i = 0; i < characterLocations.size(); i++) {
			LEOCharacter tmp = characterLocations.get(i);
			int[] loc = tmp.getLocation();
			
			int placeInString = loc[0]*(grid.getRowLength()*2)+loc[1]+loc[1];
			map.replace(placeInString, placeInString+1, tmp.toString());
		}
		System.out.println(map.toString());
	}
	
	

}
