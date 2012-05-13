package fi.leoteam.leogame.rendering;

/**
 * Interface providing method for retrieving the current texture.
 * All entities with varying/static texture must implement this interface to provide access to the texture
 * 
 * @author tuomohy
 */
public interface Textured {
	
	public Texture getCurrentTexture();
	
}
