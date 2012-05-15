package fi.leoteam.leogame.rendering;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL11;

/**
 * Creates OpenGL textures from images
 * 
 * @author tuomohy
 */
public class TextureLoader {

	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	private static Logger LOG = Logger.getLogger(TextureLoader.class);
	private static ComponentColorModel glAlphaColorModel;
	private static ComponentColorModel glColorModel;

	public TextureLoader() {
		glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 }, true, false, ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);

		glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 0 }, false, false, ComponentColorModel.OPAQUE, DataBuffer.TYPE_BYTE);
	}

	/**
	 * Attempt to find previously loaded texture and create new texture if
	 * existing one can not be found
	 * 
	 * @param resourceName
	 *            filename of the image
	 * @throws IOException
	 */
	public Texture getTexture(String resourceName) throws IOException {
		Texture texture = textures.get(resourceName);
		if (texture != null) {
			// LOG.debug("existing texture found with key " + resourceName);
			return texture;
		} else {
			LOG.debug("texture not previously loaded, creating a new one from " + resourceName);
			return loadTexture(resourceName);
		}
	}

	/**
	 * Load image & create a new texture Add the new texture to the list of
	 * loaded textures
	 * 
	 * @param resourceName
	 *            filename of the image
	 * @throws IOException
	 */
	public Texture loadTexture(String resourceName) throws IOException {
		int target = GL_TEXTURE_2D;
		int textureID = createTextureID();
		Texture texture = new Texture(target, textureID);
		glBindTexture(target, textureID);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);


		BufferedImage bufferedImage = loadImage(resourceName);
		texture.setWidth(bufferedImage.getWidth());
		texture.setHeight(bufferedImage.getHeight());
		texture.setTextureWidth(getClosestPow2(bufferedImage.getWidth()));
		texture.setTextureHeight(getClosestPow2(bufferedImage.getHeight()));

		int format = GL_RGB;
		if (bufferedImage.getColorModel().hasAlpha()) {
			format = GL_RGBA;
		}

		ByteBuffer textureBuffer = getTextureData(bufferedImage);
		GL11.glTexImage2D(target, 0, GL_RGBA, texture.getTextureWidth(), texture.getTextureHeight(), 0, format, GL11.GL_UNSIGNED_BYTE, textureBuffer);

		textures.put(resourceName, texture);
		return texture;
	}

	private BufferedImage loadImage(String resourceName) throws IOException {
		InputStream stream = getClass().getClassLoader().getResourceAsStream(resourceName);
		if (stream == null) {
			throw new IOException("Resource not found: " + resourceName);
		}
		BufferedImage bufferedImage = ImageIO.read(stream);
		return bufferedImage;
	}

	private int createTextureID() {
		IntBuffer id = ByteBuffer.allocateDirect(4).asIntBuffer();
		glGenTextures(id);
		return id.get(0);
	}

	private ByteBuffer getTextureData(BufferedImage bufferedImage) {
		ByteBuffer imageBuffer = null;
		WritableRaster raster;
		BufferedImage texImage;

		int texWidth = getClosestPow2(bufferedImage.getHeight());
		int texHeight = getClosestPow2(bufferedImage.getWidth());

		if (bufferedImage.getColorModel().hasAlpha()) {
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 4, null);
			texImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable<Object, Object>());
		} else {
			raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 3, null);
			texImage = new BufferedImage(glColorModel, raster, false, new Hashtable<Object, Object>());
		}

		Graphics g = texImage.getGraphics();
		g.setColor(new Color(0, 0, 0, 0));
		g.fillRect(0, 0, texWidth, texHeight);
		g.drawImage(bufferedImage, 0, 0, texWidth, texHeight, null);

		byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();

		imageBuffer = ByteBuffer.allocateDirect(data.length);
		imageBuffer.order(ByteOrder.nativeOrder());
		imageBuffer.put(data, 0, data.length);
		imageBuffer.flip();

		return imageBuffer;
	}

	private int getClosestPow2(int size) {
		int c = 2;
		while (c < size) {
			c *= 2;
		}
		return c;
	}

}
