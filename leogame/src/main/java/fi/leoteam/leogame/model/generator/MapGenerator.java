package fi.leoteam.leogame.model.generator;

import java.io.IOException;
import java.util.Vector;

import fi.leoteam.leogame.model.MapFloor;
import fi.leoteam.leogame.model.MapHandler;
import fi.leoteam.leogame.model.SingleTileBlock;
import fi.leoteam.leogame.rendering.Texture;
import fi.leoteam.leogame.rendering.TextureLoader;

public class MapGenerator {
	
	private TextureLoader textureLoader = new TextureLoader();
	public static final int N_OF_FLOORS = 20;
	
	/**
	 * Generaters a random map based on the given seed
	 * (and parameters like type of the map, to be added)
	 * 
	 * @return handler to the newly generated map
	 * @throws IOException 
	 */
	public MapHandler generateMap(int size, RandomSeed seed) throws IOException{
		MapHandler handler = new MapHandler();
		handler.setFloors(generateMapFloors(size));
		House house = new House(seed);
		handler.merge(10, 10, house);
		house = new House(seed);
		handler.merge(40, 10, house);
		house = new House(seed);
		handler.merge(10, 40, house);
		house = new House(seed);
		handler.merge(40, 40, house);
		return handler;
	}
	
	private Vector<MapFloor> generateMapFloors(int size) throws IOException{
		Vector<MapFloor> floors = new Vector<MapFloor>();
		floors.add(generateEmptyFloor(size, textureLoader.getTexture("img/testtile2.png")));
		for(int i = 0; i < N_OF_FLOORS-1; i++){
			floors.add(generateEmptyFloor(size, null));
		}
		return floors;
	}
	
	private MapFloor generateEmptyFloor(int size, Texture texture){
		MapFloor floor = new MapFloor();
		for(int i = 0; i < size; i++){	
			Vector<SingleTileBlock> row = new Vector<SingleTileBlock>();
			for(int j = 0; j < size; j++){
				row.add(new SingleTileBlock(texture));
			}
			floor.add(row);
		}	
		return floor;
	}

}
