package fi.leoteam.playground.aitest;

import java.util.ArrayList;

public class MapGenerator {
	
	/**
	 * Determine how many Xs and Ns there is. 0 = no obstacles, 1.0 = all obstacles
	 */
	final static double OBSTACLES = 0.2;
	final static double XDENSITY = 0.5;
	
	public static ArrayList<ArrayList<LEOEntity>> generateMap(int size){
		ArrayList<ArrayList<LEOEntity>> grid = new ArrayList<ArrayList<LEOEntity>>();
		for(int i = 0; i < size; i++){
			ArrayList<LEOEntity> row = new ArrayList<LEOEntity>();
			for(int j = 0; j < size; j++){
				if(Math.random() < OBSTACLES){
					if(Math.random() < XDENSITY){
						row.add(new LEOWorldObject(LEOStaticStrings.OBJECT_STRING_WALL, String.format("%d-%d", i, j)));
					}else{
						row.add(new LEOWorldObject(LEOStaticStrings.OBJECT_STRING_NOGO, String.format("%d-%d", i, j)));
					}
				}else{
					row.add(new LEOWorldObject(LEOStaticStrings.OBJECT_STRING_EMPTY, String.format("%d-%d", i, j)));
				}
			}
			grid.add(row);
		}
		return grid;
	}
	
	public static void main(String[] args){
		ArrayList<ArrayList<LEOEntity>>  test = generateMap(30);
		for(ArrayList<LEOEntity> row : test){
			for(LEOEntity entity : row){
				System.out.print(entity);
			}
			System.out.println();
		}
	}

}
