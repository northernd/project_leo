package fi.leoteam.leogame.model.generator;

import java.util.Random;

public class RandomSeed {
	
	Random random = new Random();
	
	public RandomSeed(long seed){
		random.setSeed(seed);
	}
	
	public float getNextRandom(){
		return random.nextFloat();
	}

}
