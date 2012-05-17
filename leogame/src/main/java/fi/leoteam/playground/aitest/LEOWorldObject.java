package fi.leoteam.playground.aitest;

public class LEOWorldObject extends LEOEntity {
	
	private int id;
	private String mapMarker;

	public LEOWorldObject(String strId, String location) {
		super(location);
		this.mapMarker = strId;
		if (strId.equals(LEOStaticStrings.OBJECT_STRING_EMPTY)) {
			this.id = LEOStaticStrings.OBJECT_ID_EMPTY;
			
		} else if (strId.equals(LEOStaticStrings.OBJECT_STRING_NOGO)) {
			this.id = LEOStaticStrings.OBJECT_ID_NOGO;
			
		} else {
			//Entity is wall
			this.id = LEOStaticStrings.OBJECT_ID_WALL;
			setBlockFOV(true);
			
		}
		
	}

	@Override
	public String toString() {
		return mapMarker;
	}
	
	

}
