package fi.leoteam.leogame.model;

import java.util.Vector;

import fi.leoteam.leogame.entities.Entity;
import fi.leoteam.leogame.rendering.Texture;
import fi.leoteam.leogame.rendering.Textured;

public class SingleTileBlock implements Textured{
	
	public static float GRIDITEMX = 48f;
	public static float GRIDITEMY = 55.25f;
	
	private Texture tile = null;
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
	
	public void addInnerObject(Entity innerObject){
		this.innerObjects.add(innerObject);
	}

	public Texture getCurrentTexture() {
		return tile;
	}
	
	
}
