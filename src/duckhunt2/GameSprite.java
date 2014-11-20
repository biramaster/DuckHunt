package duckhunt2;

import java.awt.Image;

/**
 * Game objects that are associated with an image but is not 
 * able to move and/or collide, like backgrounds etc.
 * 
 * @author Thomas Ejnefj�ll
 */
public class GameSprite implements Sprite {

	private Image mImage = null;
	private int mX, mY;
	
	/**
	 * Constructs a GameSprite
	 * 
	 * @param image The image
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public GameSprite(Image image, int x, int y) {
		mImage = image;
		mX = x;
		mY = y;		
	}
	@Override
	public int getHeight() {
		return mImage.getHeight(null);
	}
	@Override
	public Image getImage() {
		return mImage;
	}
	@Override
	public int getWidth() {		
		return mImage.getWidth(null);
	}
	@Override
	public int getX() {		
		return mX;
	}
	@Override
	public int getY() {		
		return mY;
	}
	@Override
	public boolean isVisible() {		
		return true;
	}
}