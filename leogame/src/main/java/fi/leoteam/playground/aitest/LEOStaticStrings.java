package fi.leoteam.playground.aitest;

public class LEOStaticStrings {
	
	protected static String FACING_NORTH = "N";
	protected static String FACING_EAST = "E";
	protected static String FACING_SOUTH = "S";
	protected static String FACING_WEST = "W";
	
	protected static String FACING_NORTHEAST = "NE";
	protected static String FACING_SOUTHEAST = "SE";
	protected static String FACING_SOUTHWEST = "SW";
	protected static String FACING_NORTHWEST = "NW";
	
	protected static String OBJECT_STRING_WALL = "X";
	protected static String OBJECT_STRING_EMPTY = ".";
	protected static String OBJECT_STRING_NOGO = "N";
	protected static String OBJECT_STRING_SUSPECT = "S";
	protected static String OBJECT_STRING_NOT_VISIBLE = "?";
	
	protected static int OBJECT_ID_WALL = 1;
	protected static int OBJECT_ID_EMPTY = 0;
	protected static int OBJECT_ID_NOGO = 2;
	
	protected static int SQUARE_SIDE_LENGTH = 11;
	protected static int SQUARE_SIDE_LENGTH_WITHOUT_ONE_SIDE = LEOStaticStrings.SQUARE_SIDE_LENGTH-1;
	protected static int SQUARE_SIDE_HALF_WITHOUT_CENTER = (LEOStaticStrings.SQUARE_SIDE_LENGTH-1)/2;
	protected static int SQUARE_SIDE_HALF_WITH_CENTER = ((LEOStaticStrings.SQUARE_SIDE_LENGTH-1)/2)+1;


}
