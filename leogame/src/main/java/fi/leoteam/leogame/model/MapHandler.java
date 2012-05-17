package fi.leoteam.leogame.model;

import java.util.Vector;

import fi.leoteam.leogame.model.generator.Structure;

public class MapHandler {

	private Vector<MapFloor> floors;

	public MapHandler() {
		super();
	}

	public Vector<MapFloor> getFloors() {
		return floors;
	}

	public void setFloors(Vector<MapFloor> floors) {
		this.floors = floors;
	}
	

	/**
	 * Merge a structure into this map
	 * 
	 * @param iLocation
	 * @param jLocation
	 * @param structure
	 * @return
	 */
	public boolean merge(int iLocation, int jLocation, Structure structure){
		//TODO check if the structure fits
		for(int i = 0; i < structure.getHeight(); i++){
			for(int j = 0; j < structure.get(i).size(); j++){
				Vector<SingleTileBlock> row = structure.get(i).get(j);
				for(int k = 0; k < row.size(); k++){
					floors.get(i).get(j+iLocation).get(k+jLocation).setInnerObjects(row.get(k).getInnerObjects());
				}
			}
		}
		return true;
	}
	
}
