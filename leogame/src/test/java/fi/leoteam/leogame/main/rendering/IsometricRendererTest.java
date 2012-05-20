package fi.leoteam.leogame.main.rendering;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import fi.leoteam.leogame.model.MapHandler;
import fi.leoteam.leogame.model.SingleTileBlock;
import fi.leoteam.leogame.model.generator.MapGenerator;
import fi.leoteam.leogame.model.generator.RandomSeed;
import fi.leoteam.leogame.rendering.IsometricRenderer;
import fi.leoteam.leogame.rendering.TextureLoader;

public class IsometricRendererTest {
	
	TextureLoader loader = new TextureLoader();
	private MapHandler handler = new MapHandler();
	private static Logger LOG = Logger.getLogger(IsometricRenderer.class);
	
	@BeforeClass
	public static void initGL() {
		try {
			Display.setDisplayMode(new DisplayMode(1600, 900));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 1600, 900, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	@Before
	public void setUp() throws IOException{
		MapGenerator generator = new MapGenerator();
		handler = generator.generateMap(100, new RandomSeed(42));
	}
	
	@Test
	public void drawLayerTest() {
		IsometricRenderer renderer = new IsometricRenderer();
		int key = -1;
		while (!Display.isCloseRequested()) {
			renderer.drawMap(handler);
			if(key != -1){
				switch(key){
					case Keyboard.KEY_A: renderer.getCamera().addXStatic(-SingleTileBlock.GRIDITEMX*2); break;
					case Keyboard.KEY_D: renderer.getCamera().addXStatic(SingleTileBlock.GRIDITEMX*2); break;
					case Keyboard.KEY_W: renderer.getCamera().addYStatic(-SingleTileBlock.GRIDITEMY*2); break;
					case Keyboard.KEY_S: renderer.getCamera().addYStatic(SingleTileBlock.GRIDITEMY*2); break;
					case Keyboard.KEY_R: SingleTileBlock.GRIDITEMX = SingleTileBlock.GRIDITEMX*1.1f;  SingleTileBlock.GRIDITEMY = SingleTileBlock.GRIDITEMY*1.1f; break;
					case Keyboard.KEY_T: SingleTileBlock.GRIDITEMX = SingleTileBlock.GRIDITEMX*0.9f;  SingleTileBlock.GRIDITEMY = SingleTileBlock.GRIDITEMY*0.9f; break;
					default:;
				}
				key = -1;
			}else{
				while(Keyboard.next()){
					if(!Keyboard.getEventKeyState()){
						key = Keyboard.getEventKey();
					}
				}
			}
		}
	}
	
	//@Test
	public void drawSingleTileBlockTest() throws IOException{
		IsometricRenderer renderer = new IsometricRenderer();
		SingleTileBlock item = new SingleTileBlock(loader.getTexture("img/testblock3.png"));
		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			renderer.drawSingleTileBlock(item, 0, 0, 50, 50);
			Display.update();
		}
	}

}
