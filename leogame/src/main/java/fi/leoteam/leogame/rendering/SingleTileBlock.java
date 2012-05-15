package fi.leoteam.leogame.rendering;

import java.util.Vector;

import fi.leoteam.leogame.entities.Entity;

public class SingleTileBlock implements Textured{
	
	public static final float GRIDITEMX = 18f;
	public static final float GRIDITEMY = 20f;
	
	private Texture tile;
	private Vector<Entity> innerObjects;
	
	public SingleTileBlock(Texture tile, Vector<Entity> innerObjects) {
		super();
		this.setTile(tile);
		this.setInnerObjects(innerObjects);
	}
	
	public SingleTileBlock(Texture tile) {
		super();
		this.setTile(tile);
		this.setInnerObjects(new Vector<Entity>());
	}

	public Texture getTile() {
		return tile;
	}

	public void setTile(Texture tile) {
		this.tile = tile;
	}

	public Vector<Entity> getInnerObjects() {
		return innerObjects;
	}

	public void setInnerObjects(Vector<Entity> innerObjects) {
		this.innerObjects = innerObjects;
	}

	public Texture getCurrentTexture() {
		return tile;
	}
	
	
}
