package fi.leoteam.playground.aitest;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import fi.leoteam.leogame.main.QuadDemo;

/**
 * Test class for Risto to make some initial tests with AI and pathfinding
 * @author Risto S.
 *
 */
public class LEOAITest {
	
	protected static Logger log = Logger.getLogger(LEOAITest.class);
	private LEOMapHandler map;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LEOAITest exe = new LEOAITest();

	}

	public LEOAITest() {
		super();
		
		map = new LEOMapHandler();
		map.printDebugMap(true);
		//map.printDebugMap(false);
	}

}
