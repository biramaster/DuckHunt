package duckhunt2;

import java.awt.Image;

/**
 * Interface for objects that can be drawn in the game.
 * 
 * @author Thomas Ejnefj�ll
 */
public interface Sprite {	
	/**
	 * Gets the image associated with the object
	 * 
	 * @return The image
	 */
	public Image getImage();
	/**
	 * Gets the x coordinate for the object
	 * 
	 * @return X coordinate
	 */
	public int getX();
	/**
	 * Gets the y coordinate for the object
	 * 
	 * @return Y coordinate
	 */
	public int getY();
	/**
	 * Gets the width of the object
	 * 
	 * @return The width
	 */
	public int getWidth();
	/**
	 * Gets the height of the object
	 * 
	 * @return The height
	 */
	public int getHeight();
	/**
	 * Gets the visibility state of the object.
	 * 
	 * @return true if the object is visible
	 */
	public boolean isVisible();
}