package fi.leoteam.leogame.model.generator;

import java.util.Vector;

import fi.leoteam.leogame.model.SingleTileBlock;

/**
 * Represents a floor inside structure.
 * 
 * @author tuomohy
 */
public class Floor {

	Vector<Vector<SingleTileBlock>> floor = new Vector<Vector<SingleTileBlock>>();
	
	public Vector<Vector<SingleTileBlock>> getFloor() {
		return floor;
	}

	public void setFloor(Vector<Vector<SingleTileBlock>> floor) {
		this.floor = floor;
	}
	
	public Vector<SingleTileBlock> get(int j){
		return floor.get(j);
	}
	
	public void add(Vector<SingleTileBlock> row){
		floor.add(row);
	}
	
	public int size(){
		return floor.get(0).size();
	}

}
