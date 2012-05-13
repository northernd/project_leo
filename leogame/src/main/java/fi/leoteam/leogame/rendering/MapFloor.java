package fi.leoteam.leogame.rendering;

import java.util.Vector;

public class MapFloor {

	private Vector<Vector<SingleTileBlock>> floor;

	/**
	 * Floors index (height) from 0 to n, 0 being the lowest floor
	 */
	private int floorIndex;

	public MapFloor(int floorIndex) {
		super();
		this.floor = new Vector<Vector<SingleTileBlock>>();
		setFloorIndex(floorIndex);
	}
	
	public Vector<SingleTileBlock> get(int i){
		return floor.get(i);
	}

	public int getFloorIndex() {
		return floorIndex;
	}

	public void setFloorIndex(int floorIndex) {
		this.floorIndex = floorIndex;
	}

	public Vector<Vector<SingleTileBlock>> getFloor() {
		return floor;
	}

	public void setFloor(Vector<Vector<SingleTileBlock>> floor) {
		this.floor = floor;
	}
	
}
