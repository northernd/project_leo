package fi.leoteam.leogame.model;

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
	
	public void add(Vector<SingleTileBlock> e){
		floor.add(e);
	}

	public Vector<Vector<SingleTileBlock>> getFloor() {
		return floor;
	}

	public void setFloor(Vector<Vector<SingleTileBlock>> floor) {
		this.floor = floor;
	}
	
}
