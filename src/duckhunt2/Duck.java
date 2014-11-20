package duckhunt2;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.List;
import java.util.Random;

/**
 * A duck in the game
 * 
 * @author Thomas Ejnefj�ll
 */
public class Duck implements GameObject {

	private Image mCurrentImage = null;
	private List<Image> mImages = null;
	private int mX, mY, mDX, mDY, mImageNumber;	
	private boolean mIsAlive = true;
	private Random mRandom = new Random();
	
	/**
	 * Constructs a Duck
	 * 
	 * @param images a list of images for animation. The last image should be the image of a dead duck
	 * @param width the width of the area the duck is allowed to move within 
	 * @param height the height of the area the object is allowed to move within
	 * @param dX the horizontal move speed of the duck
	 * @param dY the vertical move speed of the duck
	 */
	public Duck(List<Image> images, int width, int height, int dX, int dY) {
		mImages = images;
		mX = mRandom.nextInt(width);
		mY = mRandom.nextInt(height - 50);
		mDX = dX;
		mDY = dY;
		mImageNumber = 0;
		mCurrentImage = images.get(mImageNumber);
	}
	@Override
	public int getHeight() {
		return mCurrentImage.getHeight(null);
	}
	@Override
	public Image getImage() {
		return mCurrentImage;
	}
	@Override
	public int getWidth() {		
		return mCurrentImage.getWidth(null);
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
	@Override
	public void update(int height, int width) {
		if(mIsAlive) {
			updateAlive(height, width);			
		}
		else {
			updateDead(height, width);
		}		
	}
	private void updateAlive(int height, int width) {
		if((mX += mDX) >= width) {
			mX = 0 - getWidth();
		}
		if(mY + mDY + getHeight() >= height || mY + mDY < 0 || mRandom.nextInt(9) < 3) {
			mDY = - mDY;
		}
		mY += mDY;
		
		mCurrentImage = mImages.get(mImageNumber++ % (mImages.size() - 1));
	}
	private void updateDead(int height, int width) {
		if(mDY < 0) {
			mDY = - mDY;			
		}
		if((mY += mDY) + getHeight() > height) {
			mY = height - getHeight();
		}
	}
	@Override
	public void isHit() {
		if(mIsAlive) {
			mIsAlive = false;
			mCurrentImage = mImages.get(mImages.size() - 1);	
			mDY = mDY * 3;
		}		
	}
	@Override
	public boolean isAlive() {		
		return mIsAlive;
	}
	@Override
	public Area getArea() {		
		//TODO make shape more exact
		return new Area(new Rectangle(mX, mY, getWidth(), getHeight()));
	}
}