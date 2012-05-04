package fi.leoteam.playground.aitest;

public class LEOCharacter extends LEOEntity {

	private String mapMarker;
	
	public LEOCharacter(String mapMarker) {
		super();
		this.mapMarker = mapMarker;
		
	}

	@Override
	public String toString() {
		return mapMarker;
	}

	
}
