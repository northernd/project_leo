package fi.leoteam.leogame.entities;

import fi.leoteam.leogame.misc.Location;

public abstract class Entity {

	private boolean passable = false;
	private String facing = null;
	private boolean blockFOV = false;
	private Location location = null;
	private int height = 0;
	
	public Entity(Location location) {
		super();
		setLocation(location);
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
}
