package fi.leoteam.leogame.main.rendering;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Vector;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import fi.leoteam.leogame.entities.GridItem;
import fi.leoteam.leogame.rendering.IsometricRenderer;
import fi.leoteam.leogame.rendering.TextureLoader;

public class IsometricRendererTest {
	
	Vector<Vector<GridItem>> layer = new Vector<Vector<GridItem>>();
	Vector<Vector<GridItem>> layer2 = new Vector<Vector<GridItem>>();
	TextureLoader loader = new TextureLoader();
	
	@BeforeClass
	public static void initGL() {
		try {
			Display.setDisplayMode(new DisplayMode(1920, 1080));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 600, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	@Before
	public void setUp(){
		String textureName = "img/testblock2.png";

		for(int i = 0; i < 40; i++){
			layer.add(new Vector<GridItem>());
		}
		for(Vector<GridItem> layerRow : layer){
			for(int i = 0; i < 40; i++){
				GridItem item = new GridItem();
				try {
					item.setTexture(loader.getTexture(textureName));
					textureName = "img/testblock.png"; 
				} catch (IOException e) {
					e.printStackTrace();
				}
				layerRow.add(item);
			}
		}
		
		for(int i = 0; i < 40; i++){
			layer2.add(new Vector<GridItem>());
		}
		for(Vector<GridItem> layerRow : layer2){
			for(int i = 0; i < 40; i++){
				GridItem item = new GridItem();
				try {
					if(i < 20){
						item.setTexture(loader.getTexture(textureName));
					}
					textureName = "img/testblock.png"; 
				} catch (IOException e) {
					e.printStackTrace();
				}
				layerRow.add(item);
			}
		}
	}
	
	@Test
	public void drawLayerTest() {
		Vector<Vector<Vector<GridItem>>> layers = new Vector<Vector<Vector<GridItem>>>();
		layers.add(layer);
		layers.add(layer2);
		IsometricRenderer renderer = new IsometricRenderer();
		while (!Display.isCloseRequested()) {
			renderer.drawLayers(layers);
		}
	}

}
