package fi.leoteam.leogame.rendering;

import java.util.Vector;

public class MapFloor {

	private Vector<Vector<SingleTileBlock>> floor;

	public MapFloor() {
		super();
		this.floor = new Vector<Vector<SingleTileBlock>>();
	}
	
	public Vector<SingleTileBlock> get(int i){
		return floor.get(i);
	}

	public Vector<Vector<SingleTileBlock>> getFloor() {
		return floor;
	}

	public void setFloor(Vector<Vector<SingleTileBlock>> floor) {
		this.floor = floor;
	}
	
}
