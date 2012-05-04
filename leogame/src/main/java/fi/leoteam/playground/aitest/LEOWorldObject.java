package fi.leoteam.playground.aitest;

public class LEOWorldObject extends LEOEntity {
	
	private int id;
	private String mapMarker;

	public LEOWorldObject(String strId) {
		super();
		this.mapMarker = strId;
		if (strId.equals(LEOStaticStrings.OBJECT_STRING_NORMAL_TILE)) {
			this.id = LEOStaticStrings.OBJECT_ID_NORMAL_TILE;
			
		} else {
			//Entity is wall
			this.id = LEOStaticStrings.OBJECT_ID_WALL;
			
		}
		
	}

	@Override
	public String toString() {
		return mapMarker;
	}
	
	

}
