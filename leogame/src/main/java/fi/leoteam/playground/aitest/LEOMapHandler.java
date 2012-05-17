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
		
		//System.out.println(grid.getMapWithCharacters(characterLocations));
		System.out.println("");
		characterLocations.get(0).calculateFOV();
	}
	
	public void printDebugMap(boolean withCharacters) {
		String map;
		if(withCharacters) {
			
			//map = grid.getMapWithCharacters(characterLocations);
		} else {
			//map = grid.getMapAsString().toString();
		}
		
		//System.out.println(map);
	}
	
	

}
