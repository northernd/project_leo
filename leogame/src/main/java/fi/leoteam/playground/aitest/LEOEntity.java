package fi.leoteam.playground.aitest;

public abstract class LEOEntity {

	private boolean passable = false;
	private String facing = null;
	private boolean blockFOV = false;
	
	public static LEOEntity encodeMapMarkings(String code) {
		if(code.equals(LEOStaticStrings.OBJECT_STRING_SUSPECT)) {
			return new LEOCharacter(LEOStaticStrings.OBJECT_STRING_SUSPECT);
			
		} else if (code.equals(LEOStaticStrings.OBJECT_STRING_NORMAL_TILE)) {
			return new LEOWorldObject(code);
			
		} else {
			//Entity is wall
			return new LEOWorldObject(code);
		}
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

	public abstract String toString();
}
