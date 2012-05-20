package fi.leoteam.leogame.rendering;

public class Camera {
	
	private float x = 500;

	private float y = 500;
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void addXStatic(float x){
		this.x+=x;
	}
	
	public void addYStatic(float y){
		this.y+=y;
	}

}
