package duckhunt2;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Area;

/**
 * A shotgun bullet in the game
 * 
 * @author Thomas Ejnefj�ll
 */
public class ShotgunBullet implements GameObject {

	private Image mImage = null, mHitImage = null;
	private int mX, mY, mTimeToLive;
	
	/**
	 * Constructs a bullet
	 * 
	 * @param missImage image if the bullet misses all targets
	 * @param hitImage image if the bullet hits a target
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param timeToLive how long time (number of game updates) the bullet should be visible
	 */
	public ShotgunBullet(Image missImage, Image hitImage, int x, int y, int timeToLive) {
		mImage = missImage;
		mHitImage = hitImage;
		mX = x;
		mY = y;		
		mTimeToLive = timeToLive;		
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
		return mX - mImage.getWidth(null) / 2;
	}
	@Override
	public int getY() {		
		return mY - mImage.getHeight(null) / 2;
	}
	@Override
	public boolean isVisible() {		
		return mTimeToLive > 0;
	}
	@Override
	public void update(int height, int width) {
		mTimeToLive --;		
	}
	@Override
	public void isHit() {
		mImage = mHitImage;		
	}
	@Override
	public boolean isAlive() {		
		return this.isVisible();
	}
	@Override
	public Area getArea() {		
		return new Area(new Rectangle(mX, mY, getWidth(), getHeight()));
	}
}