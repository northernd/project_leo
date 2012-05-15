package fi.leoteam.leogame.main.rendering;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.io.IOException;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import fi.leoteam.leogame.rendering.IsometricRenderer;
import fi.leoteam.leogame.rendering.MapFloor;
import fi.leoteam.leogame.rendering.MapHandler;
import fi.leoteam.leogame.rendering.SingleTileBlock;
import fi.leoteam.leogame.rendering.TextureLoader;

public class IsometricRendererTest {
	
	TextureLoader loader = new TextureLoader();
	private MapHandler handler = new MapHandler();
	private static Logger LOG = Logger.getLogger(IsometricRenderer.class);
	
	@BeforeClass
	public static void initGL() {
		try {
			Display.setDisplayMode(new DisplayMode(1024, 768));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 1024, 768, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	@Before
	public void setUp(){
		Vector<MapFloor> floors = new Vector<MapFloor>();

		for(int k = 0; k < 1; k++){
			Vector<Vector<SingleTileBlock>> floor = new Vector<Vector<SingleTileBlock>>();
			for(int i = 0; i < 50; i++){
				floor.add(new Vector<SingleTileBlock>());
			}
			for(Vector<SingleTileBlock> floorRow : floor){
				for(int i = 0; i < 50; i++){
					SingleTileBlock item;
					try {
						item = new SingleTileBlock(loader.getTexture("img/testblock.png"));
						floorRow.add(item);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			MapFloor mapFloor = new MapFloor();
			mapFloor.setFloor(floor);
			floors.add(mapFloor);
			LOG.debug(String.format("floor %d done..", k));
		}
		
		handler.setFloors(floors);
	}
	
	@Test
	public void drawLayerTest() {
		IsometricRenderer renderer = new IsometricRenderer();
		while (!Display.isCloseRequested()) {
			renderer.drawMap(handler);
		}
	}
	
	//@Test
	public void drawSingleTileBlockTest() throws IOException{
		IsometricRenderer renderer = new IsometricRenderer();
		SingleTileBlock item = new SingleTileBlock(loader.getTexture("img/testblock.png"));
		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			renderer.drawSingleTileBlock(item, 0, 0, 50, 50);
			Display.update();
		}
	}

}
