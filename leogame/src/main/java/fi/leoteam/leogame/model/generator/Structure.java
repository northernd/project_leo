package fi.leoteam.leogame.model.generator;

import java.util.Vector;

/**
 * Base class for generated structures. Structures will be merged to the map after generation
 * so they will only exist on their own during the map generation stage
 * 
 * @author tuomohy
 */
public abstract class Structure {
	
	private Vector<Floor> floors = new Vector<Floor>();

	public Vector<Floor> getFloors() {
		return floors;
	}

	public void setFloors(Vector<Floor> floors) {
		this.floors = floors;
	}
	
	public int getHeight(){
		return floors.size();
	}
	
	public Floor get(int i){
		return floors.get(i);
	}
	
}
