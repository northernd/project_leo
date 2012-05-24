package fi.leoteam.playground.aitest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Square {
	private int x,y;
	private int pixel_y, pixel_x;
	private int real_y;
	private int ne_corner[], se_corner[], sw_corner[], nw_corner[];
	private float area;
	
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
		
		area = (float)Math.pow(LEOStaticStrings.SQUARE_SIDE_LENGTH,2);
		
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
		double result = Math.sqrt(Math.pow((getPixel_x()-s.getPixel_x()),2)+Math.pow((getPixel_y()-s.getPixel_y()),2));
		//System.out.println("Ruutujen "+toString()+" **** "+s.toString()+" välimatka on: "+result);
		return result;
	}
	
	protected boolean isLastSquare(Square s, int fovlength) {
		double result = Math.sqrt(Math.pow((getPixel_x()-s.getPixel_x()),2)+Math.pow((getPixel_y()-s.getPixel_y()),2))+LEOStaticStrings.SQUARE_SIDE_LENGTH;
		//System.out.println("Ruutujen "+toStringShort()+" **** "+s.toStringShort()+" välimatka on: "+result+" (fovlength="+fovlength);
		return result > fovlength;
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
	
	//TODO : Check that result is initialized in a best way
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
	
	protected float calculateSurfaceAreaRatio(HashMap<Integer, Float> intersections) {
		float area;
		if(intersections.containsKey(E_SECTOR) && intersections.containsKey(S_SECTOR)) {
			area = Math.abs(((se_corner[0]-intersections.get(S_SECTOR))*(se_corner[1]-intersections.get(E_SECTOR))))/2;
		}
		else if(intersections.containsKey(E_SECTOR) && intersections.containsKey(N_SECTOR)) {
			area = Math.abs(((ne_corner[0]-intersections.get(N_SECTOR))*(intersections.get(E_SECTOR)-ne_corner[1])))/2;
		}
		else if(intersections.containsKey(W_SECTOR) && intersections.containsKey(N_SECTOR)) {
			area = Math.abs(((intersections.get(N_SECTOR)-nw_corner[0])*(intersections.get(W_SECTOR)-nw_corner[1])))/2;
		}
		else if(intersections.containsKey(W_SECTOR) && intersections.containsKey(S_SECTOR)) {
			area = Math.abs(((intersections.get(S_SECTOR)-sw_corner[0])*(sw_corner[1]-intersections.get(W_SECTOR))))/2;
		}
		else {
			return 1f;
		}
		
		return (area/(this.area-area));
	}
	
	public int showSquare(Square startPoint, Square endPoint) {
		HashMap<Integer, Float> intersections = (HashMap<Integer, Float>)isSquareOnLine(startPoint, endPoint);
		
		if(intersections.size() < 2) {
			return -1;
		}
		else {
			float result = calculateSurfaceAreaRatio(intersections);
			//System.out.println("FLOAT: "+result);
			if(result < 0.1) {
				return 0;
			}
			return 1;
		}
	}
	
	public Map<Integer, Float> isSquareOnLine(Square startPoint, Square endPoint) {
		//System.out.println("Checking square : "+toString());
		HashMap<Integer, Float> intersections = new HashMap<Integer, Float>();
		float tmp;

		//West line
		int x = pixel_x-LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		tmp = cutsLineVertically(x, nw_corner[1], sw_corner[1], startPoint, endPoint);
		if(tmp != 0f) {
			intersections.put(W_SECTOR, tmp);
		}
		
		
		//East line
		x = pixel_x+LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		tmp = cutsLineVertically(x, ne_corner[1], se_corner[1], startPoint, endPoint);
		if(tmp != 0f) {
			intersections.put(E_SECTOR, tmp);
		}
		if(intersections.size() == 2) {
			return intersections;
		}
		
		//North line
		x = -pixel_y+LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		tmp = cutsLineHorizontally(x, ne_corner[0], nw_corner[0], startPoint, endPoint);
		if(tmp != 0f) {
			intersections.put(N_SECTOR, tmp);
		}
		if(intersections.size() == 2) {
			return intersections;
		}
		
		//South line
		x = -pixel_y-LEOStaticStrings.SQUARE_SIDE_HALF_WITHOUT_CENTER;
		tmp = cutsLineHorizontally(x, se_corner[0], sw_corner[0], startPoint, endPoint);
		if(tmp != 0f) {
			intersections.put(S_SECTOR, tmp);
		}
		if(intersections.size() == 2) {
			return intersections;
		}
		
		return intersections;
		
		//System.out.println("Line doesn't go through square "+getX()+","+getY());
	}
	
	private float cutsLineVertically(int x, int start, int end, Square startPoint, Square endPoint) {
		//int tmp = ((endPoint.getReal_y()-startPoint.getReal_y())/(endPoint.getX()-startPoint.getX())*(x-startPoint.getX())+startPoint.getReal_y());
		float tmp = (float)((float)(-endPoint.getPixel_y()+startPoint.getPixel_y())/(float)(endPoint.getPixel_x()-startPoint.getPixel_x())*(x-startPoint.getPixel_x())-startPoint.getPixel_y());
		if(start > tmp && tmp > end) {
			//System.out.println("Cuts line (vertically) at point "+tmp+" ["+start+","+end+"]");
			return tmp;
		}
		
		//System.out.println("Doesn't cut line (vertically) at point "+tmp+" ["+start+","+end+"]");
		return 0f;
	}
	
	private float cutsLineHorizontally(int x, int start, int end, Square startPoint, Square endPoint) {
		//int tmp = (x-startPoint.getReal_y())*((endPoint.getX()-startPoint.getX())/(endPoint.getReal_y()-startPoint.getReal_y()))+startPoint.getX();
		float tmp = (float)(x+startPoint.getPixel_y())*((float)(endPoint.getPixel_x()-startPoint.getPixel_x())/(float)(-endPoint.getPixel_y()+startPoint.getPixel_y()))+startPoint.getPixel_x();
		if(start >= tmp && tmp >= end) {
			//System.out.println("Cuts line (horizontally) at point "+tmp+" ["+start+","+end+"]");
			return tmp;
		}
		
		//System.out.println("Doesn't cut line (horizontally) at point "+tmp+" ["+start+","+end+"]");
		return 0f;
	}
	
	@Override
	public String toString() {
		String result = "Center: ("+getX()+","+getY()+") - Pixel Location: ("+getPixel_x()+","+getPixel_y()+") - Real Y:"+getReal_y();
		result += "\n REAL CORNERS: NE ("+ne_corner[0]+","+ne_corner[1]+"), SE ("+se_corner[0]+","+se_corner[1]+"), SW ("+sw_corner[0]+","
				+sw_corner[1]+"), NW ("+nw_corner[0]+","+nw_corner[1]+")";
		return result;
	}
	
	public String toStringShort() {
		String result = "("+getX()+","+getY()+")";
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
	
	public List<Square> calculateLastFOVSquaresNWSector(int fovlength) {
		ArrayList<Square> result = new ArrayList<Square>();
		Square tmp = new Square(getX()-fovlength, getY());
		
		int multipler = ((fovlength-6)/2)*1+3;
		
		for(int i = 1; i < multipler; i++) {
			tmp = tmp.getAdjacentN();
			result.add(tmp);
		}
		
		if(fovlength % 2 == 0) {
			for(int i = 0; i <= multipler; i++) {
				tmp = tmp.getAdjacentNE();
				result.add(tmp);
			}
		}
		else {
			tmp = tmp.getAdjacentNE();
			result.add(tmp);
			tmp = tmp.getAdjacentN();
			result.add(tmp);
			for(int i = 1; i < multipler; i++) {
				tmp = tmp.getAdjacentNE();
				result.add(tmp);
			}
			tmp = tmp.getAdjacentE();
			result.add(tmp);
			tmp = tmp.getAdjacentNE();
			result.add(tmp);
		}
		
		for(int i = 1; i < multipler-1; i++) {
			tmp = tmp.getAdjacentE();
			result.add(tmp);
		}
		
		return result;
	}
	
	public List<Square> calculateLastFOVSquaresNESector(int fovlength) {
		ArrayList<Square> result = new ArrayList<Square>();
		Square tmp = new Square(getX()+fovlength, getY());
		
		int multipler = ((fovlength-6)/2)*1+3;
		
		for(int i = 1; i < multipler; i++) {
			tmp = tmp.getAdjacentN();
			result.add(tmp);
		}
		
		if(fovlength % 2 == 0) {
			for(int i = 0; i <= multipler; i++) {
				tmp = tmp.getAdjacentNW();
				result.add(tmp);
			}
		}
		else {
			tmp = tmp.getAdjacentNW();
			result.add(tmp);
			tmp = tmp.getAdjacentN();
			result.add(tmp);
			for(int i = 1; i < multipler; i++) {
				tmp = tmp.getAdjacentNW();
				result.add(tmp);
			}
			tmp = tmp.getAdjacentW();
			result.add(tmp);
			tmp = tmp.getAdjacentNW();
			result.add(tmp);
		}
		
		for(int i = 1; i < multipler-1; i++) {
			tmp = tmp.getAdjacentW();
			result.add(tmp);
		}
		
		return result;
	}
	
	public List<Square> calculateLastFOVSquaresSESector(int fovlength) {
		ArrayList<Square> result = new ArrayList<Square>();
		Square tmp = new Square(getX()+fovlength, getY());
		
		int multipler = ((fovlength-6)/2)*1+3;
		
		for(int i = 1; i < multipler; i++) {
			tmp = tmp.getAdjacentS();
			result.add(tmp);
		}
		
		if(fovlength % 2 == 0) {
			for(int i = 0; i <= multipler; i++) {
				tmp = tmp.getAdjacentSW();
				result.add(tmp);
			}
		}
		else {
			tmp = tmp.getAdjacentSW();
			result.add(tmp);
			tmp = tmp.getAdjacentS();
			result.add(tmp);
			for(int i = 1; i < multipler; i++) {
				tmp = tmp.getAdjacentSW();
				result.add(tmp);
			}
			tmp = tmp.getAdjacentW();
			result.add(tmp);
			tmp = tmp.getAdjacentSW();
			result.add(tmp);
		}
		
		for(int i = 1; i < multipler-1; i++) {
			tmp = tmp.getAdjacentW();
			result.add(tmp);
		}
		
		return result;
	}
	
	public List<Square> calculateLastFOVSquaresSWSector(int fovlength) {
		ArrayList<Square> result = new ArrayList<Square>();
		Square tmp = new Square(getX()-fovlength, getY());
		
		int multipler = ((fovlength-6)/2)*1+3;
		
		for(int i = 1; i < multipler; i++) {
			tmp = tmp.getAdjacentS();
			result.add(tmp);
		}
		
		if(fovlength % 2 == 0) {
			for(int i = 0; i <= multipler; i++) {
				tmp = tmp.getAdjacentSE();
				result.add(tmp);
			}
		}
		else {
			tmp = tmp.getAdjacentSE();
			result.add(tmp);
			tmp = tmp.getAdjacentS();
			result.add(tmp);
			for(int i = 1; i < multipler; i++) {
				tmp = tmp.getAdjacentSE();
				result.add(tmp);
			}
			tmp = tmp.getAdjacentE();
			result.add(tmp);
			tmp = tmp.getAdjacentSE();
			result.add(tmp);
		}
		
		for(int i = 1; i < multipler-1; i++) {
			tmp = tmp.getAdjacentE();
			result.add(tmp);
		}
		
		return result;
	}
	
	public static void sort(List<Square> list, int sector) {
		List<Square> tmp = new ArrayList<Square>();
		int i,j,x;
		boolean flag = true;
		if(sector == NW_SECTOR) {
			for(i = 0; i < list.size(); i++) {
				Square s = list.get(i);
				x = tmp.size();
				flag = true;
				for(j = 0; j < tmp.size() && flag; j++) {
					Square compare = tmp.get(j);
					if(s.getX() > compare.getX()) {
						flag = false;
						x = j;
					}
					else if(s.getX() == compare.getX()) {
						flag = false;
						if(s.getY() >= compare.getY()) {
							x = j;
						}
						else {
							x = j+1;
						}
					}
				}
				tmp.add(x, s);
			}
			
			list.clear();
			list.addAll(tmp);
			/*System.out.println("tmp");
			for(i = 0; i < tmp.size(); i++) {
				System.out.println(tmp.get(i).toStringShort());
			}
			System.out.println("list");
			for(i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).toStringShort());
			}*/
		}
		else if (sector == SW_SECTOR) {
			for(i = 0; i < list.size(); i++) {
				Square s = list.get(i);
				x = tmp.size();
				flag = true;
				for(j = 0; j < tmp.size() && flag; j++) {
					Square compare = tmp.get(j);
					if(s.getX() > compare.getX()) {
						flag = false;
						x = j;
					}
					else if(s.getX() == compare.getX()) {
						flag = false;
						if(s.getY() < compare.getY()) {
							x = j;
						}
						else {
							x = j+1;
						}
					}
				}
				tmp.add(x, s);
			}
			
			list.clear();
			list.addAll(tmp);
		}
		else if (sector == NE_SECTOR) {
			for(i = 0; i < list.size(); i++) {
				Square s = list.get(i);
				x = tmp.size();
				flag = true;
				for(j = 0; j < tmp.size() && flag; j++) {
					Square compare = tmp.get(j);
					if(s.getX() < compare.getX()) {
						flag = false;
						x = j;
					}
					else if(s.getX() == compare.getX()) {
						flag = false;
						if(s.getY() >= compare.getY()) {
							x = j;
						}
						else {
							x = j+1;
						}
					}
				}
				tmp.add(x, s);
			}
			
			list.clear();
			list.addAll(tmp);
		}
		else {
			for(i = 0; i < list.size(); i++) {
				Square s = list.get(i);
				x = tmp.size();
				flag = true;
				for(j = 0; j < tmp.size() && flag; j++) {
					Square compare = tmp.get(j);
					if(s.getX() < compare.getX()) {
						flag = false;
						x = j;
					}
					else if(s.getX() == compare.getX()) {
						flag = false;
						if(s.getY() < compare.getY()) {
							x = j;
						}
						else {
							x = j+1;
						}
					}
				}
				tmp.add(x, s);
			}
			
			list.clear();
			list.addAll(tmp);
		}
	}
	
}
