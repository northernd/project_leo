package fi.leoteam.leogame.model.generator;

import java.io.IOException;
import java.util.Vector;

import fi.leoteam.leogame.entities.StaticEntity;
import fi.leoteam.leogame.model.SingleTileBlock;
import fi.leoteam.leogame.rendering.TextureLoader;


/**
 * Basic (test) implementation of a Structure
 * 
 * @author tuomohy
 */
public class House extends Structure{
	
	public House(RandomSeed seed) throws IOException{
		TextureLoader loader = new TextureLoader();
		Vector<Floor> floors = new Vector<Floor>();
		
		int floorCount = (int) (2+(14 * seed.getNextRandom()));
		int width = (int) (3+(8 * seed.getNextRandom()));
		
		for(int a = 0; a < floorCount; a++){
			Floor floor = new Floor();
			for(int i = 0; i < width; i++){	
				Vector<SingleTileBlock> row = new Vector<SingleTileBlock>();
				for(int j = 0; j < width; j++){
					SingleTileBlock tileBlock = new SingleTileBlock(null);
					tileBlock.addInnerObject(new StaticEntity(0,0, loader.getTexture("img/testblock3.png")));
					row.add(tileBlock);
				}
				floor.add(row);
			}
			floors.add(floor);
		}
		
		this.setFloors(floors);
	}
	
}
