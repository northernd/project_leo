package fi.leoteam.playground.aitest;

public abstract class LEOEntity {

	private boolean passable = false;
	private String facing = null;
	private boolean blockFOV = false;
	private int[] location = new int[2];
	
	
	
	public LEOEntity(String location) {
		super();
		setLocation(location);
	}

	public static LEOEntity encodeMapMarkings(String code, String location) {
		return new LEOWorldObject(code, location);
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

	public boolean blocksFOV() {
		return blockFOV;
	}

	public void setBlockFOV(boolean blockFOV) {
		this.blockFOV = blockFOV;
	}

	public int[] getLocation() {
		return location;
	}
	
	public int getXCoord() {
		return location[0];
	}
	
	public int getYCoord() {
		return location[1];
	}

	public void setLocation(String str) {
		String[] tmp = str.split("-");
		location[0] = Integer.parseInt(tmp[0]);
		location[1] = Integer.parseInt(tmp[1]);
	}

	public abstract String toString();
}
