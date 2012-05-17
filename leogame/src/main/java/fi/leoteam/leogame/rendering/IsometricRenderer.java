package fi.leoteam.leogame.rendering;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.Display;

import fi.leoteam.leogame.entities.Entity;
import fi.leoteam.leogame.model.MapFloor;
import fi.leoteam.leogame.model.MapHandler;
import fi.leoteam.leogame.model.SingleTileBlock;

public class IsometricRenderer {
	
	private static Logger LOG = Logger.getLogger(IsometricRenderer.class);
	
	private Camera camera = new Camera();
	
	public void drawMap(MapHandler handler){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		int index = 0;
		for(MapFloor floor : handler.getFloors()){
			drawFloor(floor, index++);
		}
		Display.update();
	}
	
	public void drawFloor(MapFloor floor, int floorIndex){
		int i = 0;
		int j = 0;
		int k = 0;
		int s = floor.get(0).size(); //size of a row in table
		//top to center
		while(k < s){
			i = k;
			j = 0;
			while(j <= k){
				drawSingleTileBlock(floor.get(i).get(j), i, j, (SingleTileBlock.GRIDITEMX*s)/2, getYOffsetFromFloorIndex(floorIndex));
				i--;
				j++;
			}
			k++;
		}
		k--;	
		//center to bottom
		while(i+1 != j-1){
			i = s-1;
			j = s-k;
			while(j < s){
				drawSingleTileBlock(floor.get(i).get(j), i, j, (SingleTileBlock.GRIDITEMX*s)/2, getYOffsetFromFloorIndex(floorIndex));
				i--;
				j++;
			}
			k--;
		}
	}
	
	public void drawSingleTileBlock(SingleTileBlock tileblock, int i, int j, float centerX, float yOffset){
		
		float xCoord = centerX + (SingleTileBlock.GRIDITEMX/2) * getXFromGridLocation(i, j);
		float yCoord = yOffset + (SingleTileBlock.GRIDITEMY/4) * getYFromGridLocation(i, j);
		float width = SingleTileBlock.GRIDITEMX;
		float height = SingleTileBlock.GRIDITEMY;
		if(tileblock.getCurrentTexture() != null){
			drawQuad(tileblock.getCurrentTexture(), xCoord, yCoord, width, height);
		}
		for(Entity e : tileblock.getInnerObjects()){
			drawQuad(e.getCurrentTexture(), xCoord, yCoord, width, height);
		}
	}
	
	private void drawQuad(Texture texture, float xCoord, float yCoord, float width, float height){
		glPushMatrix();

		glBindTexture(texture.getTarget(), texture.getTextureID());
		
    	glEnable(GL_BLEND);
    	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);	

    	glTranslatef(xCoord, yCoord, 0);

    	glBegin(GL_QUADS); 
    		
	      glTexCoord2f(0, 0);
	      glVertex2f(0, 0);
	      glTexCoord2f(0, 1);
	      glVertex2f(0, height);
	      glTexCoord2f(1, 1);
	      glVertex2f(width,height);
	      glTexCoord2f(1, 0);
	      glVertex2f(width,0);

    	glEnd();
		
		glPopMatrix();
	}
	
	protected int getYFromGridLocation(int i, int j){
		return i + j;
	}
	
	protected int getXFromGridLocation(int i, int j){
		return j - i;
	}
	
	protected float getYOffsetFromFloorIndex(int i){
		return -i*(SingleTileBlock.GRIDITEMY/2);
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
}
