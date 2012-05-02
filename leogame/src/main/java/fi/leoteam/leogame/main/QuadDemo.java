package fi.leoteam.leogame.main;

import org.apache.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class QuadDemo {
	
	private static Logger log = Logger.getLogger(QuadDemo.class);

	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		// init OpenGL
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		while (!Display.isCloseRequested()) {
			// Clear the screen and depth buffer
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			// set the color of the quad (R,G,B,A)
			GL11.glColor3f(0.88f, 0.11f, 2.0f);

			// draw quad
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(50, 50);
			GL11.glVertex2f(60, 55);
			GL11.glVertex2f(70, 50);
			GL11.glVertex2f(60, 45);
			GL11.glEnd();
			
			//drawQuadReformed(50, 50, 50);
			Display.update();
			
		}

		Display.destroy();
	}
	
	private void drawQuadReformed(int x, int y, float length) {
		float kulma = new Float(26.57);
		float puolikas, korkeus;
		puolikas = (float)Math.sin(kulma)*length;
		korkeus = (float)Math.cos(kulma)*length;
		//System.out.println("k: "+korkeus+" p: "+puolikas);
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x, y);
		GL11.glVertex2f(x+puolikas, y+korkeus);
		GL11.glVertex2f(x+(2*puolikas), y);
		GL11.glVertex2f(x+puolikas, y-korkeus);
		GL11.glEnd();
		
	}

	public static void main(String[] argv) {
		log.debug("starting demo.");
		QuadDemo test = new QuadDemo();
		test.start();
	}
}
