package fi.leoteam.leogame.rendering;

import java.util.Vector;

import fi.leoteam.leogame.entities.Entity;

public class SingleTileBlock {
	private Texture tile;
	private Vector<Entity> innerObjects;
	
	public SingleTileBlock(Texture tile, Vector<Entity> innerObjects) {
		super();
		this.tile = tile;
		this.innerObjects = innerObjects;
	}
	
	public SingleTileBlock(Texture tile) {
		super();
		this.tile = tile;
		this.innerObjects = new Vector<Entity>();
	}
	
	
}
