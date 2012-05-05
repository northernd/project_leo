package fi.leoteam.playground.aitest;

public class LEOCharacter extends LEOEntity {

	private String mapMarker;
	private LEOMapGrid map;
	
	public LEOCharacter(String mapMarker, String location, LEOMapGrid map) {
		super(location);
		this.mapMarker = mapMarker;
		this.map = map;
		
	}

	@Override
	public String toString() {
		return mapMarker;
	}

	public LEOMapGrid getMap() {
		return map;
	}

	public void setMap(LEOMapGrid map) {
		this.map = map;
	}

	public void calculateFOV() {
		System.out.println("NE-loc: "+map.getAdjacentNE(getLocation()).getXCoord()+","+map.getAdjacentNE(getLocation()).getYCoord());
		
	}
	
	
}
