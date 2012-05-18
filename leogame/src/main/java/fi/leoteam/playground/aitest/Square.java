package fi.leoteam.playground.aitest;

import java.util.ArrayList;
import java.util.List;

public class Square {
	private int x,y;
	private int pixel_y, pixel_x;
	private int real_y;
	private int ne_corner[], se_corner[], sw_corner[], nw_corner[];
	
	public static int N_SECTOR = 0;
	public static int NE_SECTOR = 1;
	public static int E_SECTOR = 2;
	public static int SE_SECTOR = 3;
	public static int S_SECTOR = 4;
	public static int SW_SECTOR = 5;
	public static int W_SECTOR = 6;
	public static int NW_SECTOR = 7;
	
	
	public Square(int x, int y) {
		this.x = x;
		this.y = y;
		
		pixel_x = convertOnePointToPixelLocation(x);
		pixel_y = convertOnePointToPixelLocation(y);
		
		real_y = -y;
		
		calculateCorners();
	}
	
	public Square(int[] location) {
		this(location[0], location[1]);
	}
	
	private void calculateCorners() {
		int x = (getX() == 0)? 1 : 0;
		int y = (getY() == 0)? 1 : 0;
		ne_corner = new int[2];
		ne_corner[0] = pixel_x+LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		ne_corner[1] = -pixel_y+LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER+y;
		//System.out.println("NE-corner: ("+ne_corner[0]+","+ne_corner[1]+")");
		
		se_corner = new int[2];
		se_corner[0] = pixel_x+LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		se_corner[1] = -pixel_y-LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		//System.out.println("SE-corner: ("+se_corner[0]+","+se_corner[1]+")");
		
		
		sw_corner = new int[2];
		sw_corner[0] = pixel_x-LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER-x;
		sw_corner[1] = -pixel_y-LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		//System.out.println("SW-corner: ("+sw_corner[0]+","+sw_corner[1]+")");
		
		nw_corner = new int[2];
		nw_corner[0] = pixel_x-LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER-x;
		nw_corner[1] = -pixel_y+LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER+y;
		//System.out.println("NW-corner: ("+nw_corner[0]+","+nw_corner[1]+")");
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getReal_y() {
		return real_y;
	}
	public int getPixel_y() {
		return pixel_y;
	}
	public int getPixel_x() {
		return pixel_x;
	}
	
	private int convertOnePointToPixelLocation(int point) {
		//int half = ((LEOStaticStrings.SQUARE_SIDE_LENGTH-1)/2)+1;
		//return (point*LEOStaticStrings.SQUARE_SIDE_LENGTH)+(half+1);
		int half = (LEOStaticStrings.SQUARE_SIDE_LENGTH-3)/2;
		return ((half*(1+(point*2)))+(1*(2+(point*2))));
	}
	
	protected static double twoPointsDistance(Square s1, Square s2) {
		return Math.sqrt(Math.pow((s1.getPixel_x()-s2.getPixel_x()),2)+Math.pow((s1.getPixel_y()-s2.getPixel_y()),2));
	}
	
	protected double twoPointsDistance(Square s) {
		return Math.sqrt(Math.pow((getPixel_x()-s.getPixel_x()),2)+Math.pow((getPixel_y()-s.getPixel_y()),2));
	}
	
	protected static ArrayList<Square> getPossibleSquares(Square point1, Square point2) {
		ArrayList<Square> result = new ArrayList<Square>();

		int x, y;
		//System.out.println(point1.toString());
		//System.out.println(point2.toString());
		int[] startPoint = new int[2];
		if(point1.getX() <= point2.getX()) {
			startPoint[0] = point1.getX();
			x = point2.getX();
		}
		else {
			startPoint[0] = point2.getX();
			x = point1.getX();
		}
		
		if(point1.getY() <= point2.getY()) {
			startPoint[1] = point1.getY();
			y = point2.getY();
		}
		else {
			startPoint[1] = point2.getY();
			y = point1.getY();
		}
		
		for(int i = startPoint[0]; i <= x; i++) {
			for(int j = startPoint[1]; j <= y; j++) {
				result.add(new Square(i, j));
				//System.out.println("Added square {"+i+","+j+"}");
			}
		}
		
		return result;
	}
	
	protected static int getPossibleSector(Square point1, Square point2) {
		int x = point1.getX()-point2.getX();
		int y = point1.getY()-point2.getY();
		
		if(x < 0 && y > 0) {
			return NE_SECTOR;
		}
		else if(x < 0 && y < 0) {
			return SE_SECTOR;
		}
		else if(x > 0 && y < 0) {
			return SW_SECTOR;
		}
		else if(x > 0 && y > 0) {
			return NW_SECTOR;
		}
		else if (x == 0 && y > 0) {
			return N_SECTOR;
		}
		else if (x < 0 && y == 0) {
			return E_SECTOR;
		}
		else if (x == 0 && y < 0) {
			return S_SECTOR;
		}
		else {
			return W_SECTOR;
		}

	}
	
	protected ArrayList<Square> getPossibleNextSquares(int sector) {
		ArrayList<Square> result = new ArrayList<Square>();
		
		if(sector == N_SECTOR) {
			result.add(getAdjacentN());
		}
		else if(sector == NE_SECTOR) {
			result.add(getAdjacentN());
			result.add(getAdjacentE());
			result.add(getAdjacentNE());
		}
		else if(sector == E_SECTOR) {
			result.add(getAdjacentE());
		}
		else if(sector == SE_SECTOR) {
			result.add(getAdjacentE());
			result.add(getAdjacentS());
			result.add(getAdjacentSE());
		}
		else if(sector == S_SECTOR) {
			result.add(getAdjacentS());
		}
		else if(sector == SW_SECTOR) {
			result.add(getAdjacentS());
			result.add(getAdjacentW());
			result.add(getAdjacentSW());
		}
		else if(sector == W_SECTOR) {
			result.add(getAdjacentW());
		}
		else {
			result.add(getAdjacentW());
			result.add(getAdjacentN());
			result.add(getAdjacentNW());
		}
		//System.out.print("Mahdollisia seuraavia ruutuja ovat: ");
		for(int i = 0; i < result.size(); i++) {
			//System.out.print(" ("+result.get(i).getX()+","+result.get(i).getY()+") ");
		}
		//System.out.println("");
		return result;
	}
	
	protected ArrayList<Square> getPossibleLastSquares(int sector) {
		ArrayList<Square> result = new ArrayList<Square>();
		
		if(sector == N_SECTOR) {
			result.add(getAdjacentS());
		}
		else if(sector == NE_SECTOR) {
			result.add(getAdjacentW());
			result.add(getAdjacentS());
			result.add(getAdjacentSW());
		}
		else if(sector == E_SECTOR) {
			result.add(getAdjacentW());
		}
		else if(sector == SE_SECTOR) {
			result.add(getAdjacentN());
			result.add(getAdjacentW());
			result.add(getAdjacentNW());
		}
		else if(sector == S_SECTOR) {
			result.add(getAdjacentN());
		}
		else if(sector == SW_SECTOR) {
			result.add(getAdjacentN());
			result.add(getAdjacentE());
			result.add(getAdjacentNE());
		}
		else if(sector == W_SECTOR) {
			result.add(getAdjacentE());
		}
		else {
			result.add(getAdjacentE());
			result.add(getAdjacentS());
			result.add(getAdjacentSE());
		}

		return result;
	}
	
	protected ArrayList<Square> getPossibleDoubleLastSquares(int sector) {
		Square tmp;
		ArrayList<Square> result = new ArrayList<Square>();
		if(sector == NE_SECTOR) {
			tmp = getAdjacentSW();
			result.add(tmp.getAdjacentW());
			result.add(tmp.getAdjacentS());
			result.add(tmp.getAdjacentSW());
		}
		else if(sector == SE_SECTOR) {
			tmp = getAdjacentNW();
			result.add(tmp.getAdjacentN());
			result.add(tmp.getAdjacentW());
			result.add(tmp.getAdjacentNW());
		}
		else if(sector == SW_SECTOR) {
			tmp = getAdjacentNE();
			result.add(tmp.getAdjacentN());
			result.add(tmp.getAdjacentE());
			result.add(tmp.getAdjacentNE());
		}
		else if(sector == NW_SECTOR) {
			tmp = getAdjacentSE();
			result.add(tmp.getAdjacentE());
			result.add(tmp.getAdjacentS());
			result.add(tmp.getAdjacentSE());
		}
		
		return result;
	}
	
	public boolean isSquareOnLine(Square startPoint, Square endPoint) {
		//System.out.println("Checking square : "+toString());
		//West line
		int x = pixel_x-LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		int result = cutsLineVertically(x, nw_corner[1], sw_corner[1], startPoint, endPoint);
		
		//East line
		x = pixel_x+LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		result += cutsLineVertically(x, ne_corner[1], se_corner[1], startPoint, endPoint);
		if(result == 2) {
			return true;
		}
		
		//North line
		x = -pixel_y+LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		result += cutsLineHorizontally(x, ne_corner[0], nw_corner[0], startPoint, endPoint);
		if(result == 2) {
			return true;
		}
		
		//South line
		x = -pixel_y-LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		result += cutsLineHorizontally(x, se_corner[0], sw_corner[0], startPoint, endPoint);
		if(result == 2) {
			return true;
		}
		
		//System.out.println("Line doesn't go through square "+getX()+","+getY());
		return false;
	}
	
	private int cutsLineVertically(int x, int start, int end, Square startPoint, Square endPoint) {
		//int tmp = ((endPoint.getReal_y()-startPoint.getReal_y())/(endPoint.getX()-startPoint.getX())*(x-startPoint.getX())+startPoint.getReal_y());
		float tmp = (float)((float)(-endPoint.getPixel_y()+startPoint.getPixel_y())/(float)(endPoint.getPixel_x()-startPoint.getPixel_x())*(x-startPoint.getPixel_x())-startPoint.getPixel_y());
		if(start > tmp && tmp > end) {
			//System.out.println("Cuts line (vertically) at point "+tmp+" ["+start+","+end+"]");
			return 1;
		}
		
		//System.out.println("Doesn't cut line (vertically) at point "+tmp+" ["+start+","+end+"]");
		return 0;
	}
	
	private int cutsLineHorizontally(int x, int start, int end, Square startPoint, Square endPoint) {
		//int tmp = (x-startPoint.getReal_y())*((endPoint.getX()-startPoint.getX())/(endPoint.getReal_y()-startPoint.getReal_y()))+startPoint.getX();
		float tmp = (float)(x+startPoint.getPixel_y())*((float)(endPoint.getPixel_x()-startPoint.getPixel_x())/(float)(-endPoint.getPixel_y()+startPoint.getPixel_y()))+startPoint.getPixel_x();
		if(start >= tmp && tmp >= end) {
			//System.out.println("Cuts line (horizontally) at point "+tmp+" ["+start+","+end+"]");
			return 1;
		}
		
		//System.out.println("Doesn't cut line (horizontally) at point "+tmp+" ["+start+","+end+"]");
		return 0;
	}
	
	@Override
	public String toString() {
		String result = "Center: ("+getX()+","+getY()+") - Pixel Location: ("+getPixel_x()+","+getPixel_y()+") - Real Y:"+getReal_y();
		result += "\n REAL CORNERS: NE ("+ne_corner[0]+","+ne_corner[1]+"), SE ("+se_corner[0]+","+se_corner[1]+"), SW ("+sw_corner[0]+","
				+sw_corner[1]+"), NW ("+nw_corner[0]+","+nw_corner[1]+")";
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		Square tmp = (Square)obj;
		if(getX() != tmp.getX()) {
			return false;
		}
		if(getY() != tmp.getY()) {
			return false;
		}
		return true;
	}
	
	public Square getAdjacentN() {
		return new Square(getX(), getY()-1);
	}
	
	public Square getAdjacentNE() {
		return new Square(getX()+1, getY()-1);
	}
	
	public Square getAdjacentE() {
		return new Square(getX()+1, getY());
	}
	
	public Square getAdjacentSE() {
		return new Square(getX()+1, getY()+1);
	}
	
	public Square getAdjacentS() {
		return new Square(getX(), getY()+1);
	}
	
	public Square getAdjacentSW() {
		return new Square(getX()-1, getY()+1);
	}
	
	public Square getAdjacentW() {
		return new Square(getX()-1, getY());
	}
	
	public Square getAdjacentNW() {
		return new Square(getX()-1, getY()-1);
	}
	
	public String getLocationAsSimpleString() {
		return "("+getX()+","+getY()+")";
	}
	
}
