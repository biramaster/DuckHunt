package duckhunt2;

import java.awt.geom.Area;

/**
 * Game objects that are associated with an image and 
 * able to move and/or collide.
 * 
 * @author Thomas Ejnefj�ll
 */
public interface GameObject extends Sprite {
	/**
	 * Perform action specific to the object
	 * 
	 * @param height The height of the area the object is allowed to move within
	 * @param width The width of the area the object is allowed to move within
	 */
	public void update(int height, int width);	
	/**
	 * Marks that the object is hit
	 */
	public void isHit();
	/**
	 * Returns weather the object is alive or not
	 * 
	 * @return True if the object is alive
	 */
	public boolean isAlive();
	/**
	 * Returns the area that the game object is occupying
	 * 
	 * @return The game objects shape
	 */
	public Area getArea();
}