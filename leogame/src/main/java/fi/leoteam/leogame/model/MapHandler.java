package fi.leoteam.leogame.model;

import java.util.Vector;

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
	
}
