package fi.leoteam.leogame.entities;

import fi.leoteam.leogame.misc.Location;
import fi.leoteam.leogame.rendering.Texture;
import fi.leoteam.leogame.rendering.Textured;

public abstract class Entity implements Textured{

	private boolean passable = false;
	private String facing = null;
	private boolean blockFOV = false;
	private Location location = null;
	private int height = 0;
	private Texture texture;
	
	public Entity(Location location) {
		super();
		setLocation(location);
	}
	
	public Entity(int i, int j, Texture texture) {
		super();
		this.texture = texture;
		setLocation(new Location(i, j));
	}
	
	public boolean isPassable() {
		return passable;
	}

	public void setPassable(boolean passable) {
		this.passable = passable;
	}

	public String getFacing() {
		return facing;
	}

	public void setFacing(String facing) {
		this.facing = facing;
	}

	public boolean isBlockFOV() {
		return blockFOV;
	}

	public void setBlockFOV(boolean blockFOV) {
		this.blockFOV = blockFOV;
	}

	public Location getLocation() {
		return location;
	}
	
	public int getXCoord() {
		return location.getX_coord();
	}
	
	public int getYCoord() {
		return location.getY_coord();
	}

	public void setLocation(Location loc) {
		location = loc;
	}

	public abstract String toString();

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public Texture getCurrentTexture() {
		return texture;
	}
}
