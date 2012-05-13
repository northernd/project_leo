package fi.leoteam.leogame.rendering;

import java.util.Vector;

public class MapFloor {

	private Vector<Vector<SingleTileBlock>> map;

	/**
	 * Floors index (height) from 0 to n, 0 being the lowest floor
	 */
	private int floorIndex;

	public MapFloor(int floorIndex) {
		super();
		this.map = new Vector<Vector<SingleTileBlock>>();
		setFloorIndex(floorIndex);
	}
	
	public Vector<SingleTileBlock> get(int i){
		return map.get(i);
	}
	
	public Vector<Vector<SingleTileBlock>> getMap() {
		return map;
	}

	public void setMap(Vector<Vector<SingleTileBlock>> map) {
		this.map = map;
	}

	public int getFloorIndex() {
		return floorIndex;
	}

	public void setFloorIndex(int floorIndex) {
		this.floorIndex = floorIndex;
	}
	
}
