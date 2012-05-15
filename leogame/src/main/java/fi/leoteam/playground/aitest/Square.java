package fi.leoteam.playground.aitest;

import java.util.ArrayList;

public class Square {
	private int x,y;
	private int pixel_y, pixel_x;
	private int real_y;
	private int ne_corner[], se_corner[], sw_corner[], nw_corner[];
	
	
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
		System.out.println("NE-corner: ("+ne_corner[0]+","+ne_corner[1]+")");
		
		se_corner = new int[2];
		se_corner[0] = pixel_x+LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		se_corner[1] = -pixel_y-LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		System.out.println("SE-corner: ("+se_corner[0]+","+se_corner[1]+")");
		
		
		sw_corner = new int[2];
		sw_corner[0] = pixel_x-LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER-x;
		sw_corner[1] = -pixel_y-LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		System.out.println("SW-corner: ("+sw_corner[0]+","+sw_corner[1]+")");
		
		nw_corner = new int[2];
		nw_corner[0] = pixel_x-LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER-x;
		nw_corner[1] = -pixel_y+LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER+y;
		System.out.println("NW-corner: ("+nw_corner[0]+","+nw_corner[1]+")");
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
	
	protected static ArrayList<Square> getPossibleSquares(Square point1, Square point2) {
		ArrayList<Square> result = new ArrayList<Square>();

		int x, y;
		System.out.println(point1.toString());
		System.out.println(point2.toString());
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
				System.out.println("Added square {"+i+","+j+"}");
			}
		}
		
		return result;
	}
	
	public boolean isSquareOnLine(Square startPoint, Square endPoint) {
		System.out.println("Checking square : "+toString());
		//West line
		int x = pixel_x-LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		boolean result = cutsLineVertically(x, sw_corner[1], nw_corner[1], startPoint, endPoint);
		if(result) {
			return result;
		}
		
		//East line
		x = pixel_x+LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		result = cutsLineVertically(x, se_corner[1], ne_corner[1], startPoint, endPoint);
		if(result) {
			return result;
		}
		
		//North line
		x = -pixel_y+LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		result = cutsLineHorizontally(x, nw_corner[0], ne_corner[0], startPoint, endPoint);
		if(result) {
			return result;
		}
		
		//South line
		x = -pixel_y-LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		result = cutsLineHorizontally(x, sw_corner[0], se_corner[0], startPoint, endPoint);
		if(result) {
			return result;
		}
		
		System.out.println("Line doesn't go through square "+getX()+","+getY());
		return false;
	}
	
	private boolean cutsLineVertically(int x, int start, int end, Square startPoint, Square endPoint) {
		//int tmp = ((endPoint.getReal_y()-startPoint.getReal_y())/(endPoint.getX()-startPoint.getX())*(x-startPoint.getX())+startPoint.getReal_y());
		float tmp = (float)((float)(-endPoint.getPixel_y()+startPoint.getPixel_y())/(float)(endPoint.getPixel_x()-startPoint.getPixel_x())*(x-startPoint.getPixel_x())-startPoint.getPixel_y());
		if(start >= tmp && tmp >= end) {
			System.out.println("Cuts line (vertically) at point "+tmp+" ["+start+","+end+"]");
			return true;
		}
		
		System.out.println("Doesn't cut line (vertically) at point "+tmp+" ["+start+","+end+"]");
		return false;
	}
	
	private boolean cutsLineHorizontally(int x, int start, int end, Square startPoint, Square endPoint) {
		//int tmp = (x-startPoint.getReal_y())*((endPoint.getX()-startPoint.getX())/(endPoint.getReal_y()-startPoint.getReal_y()))+startPoint.getX();
		float tmp = (float)(x+startPoint.getPixel_y())*((float)(endPoint.getPixel_x()-startPoint.getPixel_x())/(float)(-endPoint.getPixel_y()+startPoint.getPixel_y()))+startPoint.getPixel_x();
		if(start <= tmp && tmp <= end) {
			System.out.println("Cuts line (horizontally) at point "+tmp+" ["+start+","+end+"]");
			return true;
		}
		
		System.out.println("Doesn't cut line (horizontally) at point "+tmp+" ["+start+","+end+"]");
		return false;
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
	
	private Square getAdjacentN() {
		return new Square(getX(), getY()-1);
	}
	
	private Square getAdjacentNE() {
		return new Square(getX()+1, getY()-1);
	}
	
	private Square getAdjacentE() {
		return new Square(getX()+1, getY());
	}
	
	private Square getAdjacentSE() {
		return new Square(getX()+1, getY()+1);
	}
	
	private Square getAdjacentS() {
		return new Square(getX(), getY()+1);
	}
	
	private Square getAdjacentSW() {
		return new Square(getX()-1, getY()+1);
	}
	
	private Square getAdjacentW() {
		return new Square(getX()-1, getY());
	}
	
	private Square getAdjacentNW() {
		return new Square(getX()-1, getY()-1);
	}
	
}
