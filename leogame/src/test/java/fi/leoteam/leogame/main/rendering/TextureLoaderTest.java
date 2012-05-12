package fi.leoteam.leogame.main.rendering;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import fi.leoteam.leogame.rendering.Texture;
import fi.leoteam.leogame.rendering.TextureLoader;

public class TextureLoaderTest {

	@BeforeClass
	public static void initGL() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	@Test
	public void getTextureTest() {
		TextureLoader loader = new TextureLoader();
		try {
			for(int i = 0; i < 2; i++){
				Texture texture = loader.getTexture("img/test.png");
				assertTrue(texture.getHeight() == 64);
				assertTrue(texture.getWidth() == 64);
				assertTrue(texture.getTextureHeight()== 64);
				assertTrue(texture.getTextureWidth() == 64);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
