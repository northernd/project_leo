package fi.leoteam.leogame.rendering;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.*;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.Display;

public class IsometricRenderer {
	
	static final float GRIDITEMWIDTH = 10f;
	static final float GRIDITEMHEIGHT = 7f;
	private static Logger LOG = Logger.getLogger(IsometricRenderer.class);
	
	public void drawMap(MapHandler handler){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		for(MapFloor floor : handler.getFloors()){
			drawFloor(floor);
		}
		Display.update();
	}
	
	public void drawFloor(MapFloor floor){
		int i = 0;
		int j = 0;
		int k = 0;
		int s = floor.get(0).size(); //size of a row in table
		//top to center
		while(k < s){
			i = k;
			j = 0;
			while(j <= k){
				drawSingleTileBlock(floor.get(i).get(j), i, j, (GRIDITEMWIDTH*s)/2, getYOffsetFromFloorIndex(floor.getFloorIndex()));
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
				drawSingleTileBlock(floor.get(i).get(j), i, j, (GRIDITEMWIDTH*s)/2, getYOffsetFromFloorIndex(floor.getFloorIndex()));
				i--;
				j++;
			}
			k--;
		}
		
	}
	
	public void drawSingleTileBlock(SingleTileBlock entity, int i, int j, float centerX, float yOffset){		
		if(entity.getCurrentTexture() == null){
			return;
		}
		
		float xCoord = centerX + (GRIDITEMWIDTH/2) * getXFromGridLocation(i, j);
		float yCoord = yOffset + (GRIDITEMHEIGHT/2) * getYFromGridLocation(i, j);
		
		glPushMatrix();
				
		Texture texture = entity.getCurrentTexture();

		glBindTexture(texture.getTarget(), texture.getTextureID());
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	    glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T, GL_REPEAT);
		
    	glEnable(GL_BLEND);
    	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);	

    	glTranslatef(xCoord, yCoord, 0);

    	glBegin(GL_QUADS); 
    		
    		glTexCoord2f(0,0);
    		glVertex2f(0,0);
    		glTexCoord2f(1,0);
    		glVertex2f(18,0);
    		glTexCoord2f(1,1);
    		glVertex2f(18,20);
    		glTexCoord2f(0,1);
    		glVertex2f(0,20);

    	glEnd();
		
		glPopMatrix();

		//LOG.debug(String.format("drawing item (%d,%d) to (%f,%f)", i, j, xCoord, yCoord));
	}
	
	
	protected int getYFromGridLocation(int i, int j){
		return i + j;
	}
	
	protected int getXFromGridLocation(int i, int j){
		return j - i;
	}
	
	protected float getYOffsetFromFloorIndex(int i){
		return -i*GRIDITEMHEIGHT;
	}

}
