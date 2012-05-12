package fi.leoteam.leogame.rendering;

import java.util.Vector;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

import fi.leoteam.leogame.entities.GridItem;

public class IsometricRenderer {
	
	static final float GRIDITEMWIDTH = 10f;
	static final float GRIDITEMHEIGHT = 7f;
	private static Logger LOG = Logger.getLogger(IsometricRenderer.class);
	float yOffSet = 0;
	
	public void drawLayers(Vector<Vector<Vector<GridItem>>> layers){
		yOffSet = 0;
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		for(Vector<Vector<GridItem>> layer : layers){
			drawLayer(layer);
			yOffSet = -7f;
		}
		Display.update();
	}
	
	public void drawLayer(Vector<Vector<GridItem>> layer){
		int i = 0;
		int j = 0;
		int k = 0;
		int s = layer.get(0).size(); //size of a row in table
		//top to center
		while(k < s){
			i = k;
			j = 0;
			while(j <= k){
				drawGridEntity(layer.get(i).get(j), i, j, (GRIDITEMWIDTH*s)/2);
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
				drawGridEntity(layer.get(i).get(j), i, j, (GRIDITEMWIDTH*s)/2);
				i--;
				j++;
			}
			k--;
		}
		
	}
	
	public void drawGridEntity(GridItem entity, int i, int j, float centerX){		
		if(entity.getTexture() == null){
			return;
		}
		
		float xCoord = centerX + (GRIDITEMWIDTH/2) * getXFromGridLocation(i, j);
		float yCoord = yOffSet + (GRIDITEMHEIGHT/2) * getYFromGridLocation(i, j);
		
		glPushMatrix();
				
		Texture texture = entity.getTexture();

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

		LOG.debug(String.format("drawing item (%d,%d) to (%f,%f)", i, j, xCoord, yCoord));
	}
	
	
	protected int getYFromGridLocation(int i, int j){
		return i + j;
	}
	
	protected int getXFromGridLocation(int i, int j){
		return j - i;
	}

}
