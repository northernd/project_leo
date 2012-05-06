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
		System.out.println("Current-loc: "+getXCoord()+","+getYCoord());
		System.out.println("N-loc: "+map.getAdjacentN(this)[0]+","+map.getAdjacentN(this)[1]);
		System.out.println("NE-loc: "+map.getAdjacentNE(this)[0]+","+map.getAdjacentNE(this)[1]);
		System.out.println("E-loc: "+map.getAdjacentE(this)[0]+","+map.getAdjacentE(this)[1]);
		System.out.println("SE-loc: "+map.getAdjacentSE(this)[0]+","+map.getAdjacentSE(this)[1]);
		System.out.println("S-loc: "+map.getAdjacentS(this)[0]+","+map.getAdjacentS(this)[1]);
		System.out.println("SW-loc: "+map.getAdjacentSW(this)[0]+","+map.getAdjacentSW(this)[1]);
		System.out.println("W-loc: "+map.getAdjacentW(this)[0]+","+map.getAdjacentW(this)[1]);
		System.out.println("NW-loc: "+map.getAdjacentNW(this)[0]+","+map.getAdjacentNW(this)[1]);
		
	}
	
	
}
