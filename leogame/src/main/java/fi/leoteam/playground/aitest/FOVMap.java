package fi.leoteam.playground.aitest;

public class FOVMap {
	private String[][] fovMap;
	private Boolean[][] visited;
	private int size;
	private LEOMapGrid entityMap;
	
	public FOVMap(int size, LEOMapGrid entityMap) {
		fovMap = new String[size][size];
		visited = new Boolean[size][size];
		this.entityMap = entityMap;
		this.size = size;
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				addNotVisibleToFovMap(j, i);
			}
		}
	}
	
	public boolean addNotVisibleToFovMap(int x, int y) {
		try {
			fovMap[y][x] = LEOStaticStrings.OBJECT_STRING_NOT_VISIBLE;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}
	
	public void addListToFovMap(FOVSquareList list) {
		boolean flag = true;
		for(int i = 0; i < list.size() && flag; i++) {
			Square tmp = list.get(i);
			flag = addToFovMap(tmp);
			if(entityMap.getEntityFromLocation(tmp).blocksFOV()) {
				flag = flag && false;
			}
		}
	}
	
	private boolean isSquareVisited(Square s) {
		Boolean tmp = visited[s.getY()][s.getX()];
		if(tmp == null) {
			return false;
		}
		return true;
	}
	
	private void markVisited(Square s) {
		visited[s.getY()][s.getX()] = true;
	}
	
	public boolean addToFovMap(Square s) {
		int x = s.getX();
		int y = s.getY();
		boolean flag = true;
		if(isSquareVisited(s)) {
			if(getFromFovMap(s).equals(LEOStaticStrings.OBJECT_STRING_NOT_VISIBLE)) {
				flag = false;
			}
		}
		else {
			markVisited(s);
		}
		if(flag) {
			try {
				fovMap[y][x] = entityMap.getEntityFromLocation(s).toString();
			} catch (ArrayIndexOutOfBoundsException e) {
	
			}
		}
		return flag;
	}
	
	public String getFromFovMap(Square s) {
		int x = s.getX();
		int y = s.getY();
		try {
			return fovMap[y][x];
		} catch (ArrayIndexOutOfBoundsException e) {
			return "";
		}
	}
	
	public void addCharacter(Square s, LEOEntity character) {
		int x = s.getX();
		int y = s.getY();
		try {
			fovMap[y][x] = character.toString();
		} catch (ArrayIndexOutOfBoundsException e) {

		}
	}
	
	@Override
	public String toString() {
		return printFOVMap();
	}
	
	private String printFOVMap() {
		String result = "   ";
		for(int i = 0; i < fovMap.length; i++) {
			if(i >= 10) {
				result +=i+" ";
			}
			else {
				result +=i+"  ";
			}
			
		}
		result += "\n";
		for(int i = 0; i < fovMap.length; i++) {
			if(i >= 10) {
				result +=i+" ";
			}
			else {
				result +=i+"  ";
			}
			for(int j = 0; j < fovMap.length; j++) {
				result += fovMap[i][j]+"  ";
			}
			result += "\n";
		}
		return result;
	}
}
