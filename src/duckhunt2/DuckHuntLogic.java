package duckhunt2;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

/**
 * Controls the game mechanics of the Duck hunt game
 * 
 * @author Thomas Ejnefj�ll
 */
public class DuckHuntLogic implements Runnable {
	
	private enum GameState { Play, Menu, GameOver };
	
	private AnimationArea mAnimationArea = null;
	private int mX, mY;
	private Weapon mCurrentWeapon = Weapon.GUN;
	private List<GameObject> mDucks = null, mBullets = null;
	private List<List<Sprite>> mAmmunition = null;
	private List<Sprite> mBackground = null;
	private GameState mCurrentState = GameState.Menu;
	private int mLevel;
	
	/**
	 * Constructs the object
	 * 
	 * @param animationArea the screen
	 */
	public DuckHuntLogic(AnimationArea animationArea) {
		this.mAnimationArea = animationArea;			
		new Thread(this).start();
	}	
	/**
	 * Called when the user moves the mouse
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void handleMouseMoved(int x, int y) {
		mX = x;
		mY = y;
	}
	/**
	 * Called when a key is pressed
	 * 
	 * @param keyCode the code of the pressed key
	 * @throws IOException
	 */
	public void handleKeyPressed(int keyCode) throws IOException {
		switch(keyCode) {
			case KeyEvent.VK_P:
				startGame();
				break;			
			case KeyEvent.VK_ESCAPE:
				pauseGame();
				break;
			case KeyEvent.VK_SPACE:
				fireGun(mX, mY);
				break;
			case KeyEvent.VK_1:
				changeWeapon(1);
				break;
			case KeyEvent.VK_2:
				changeWeapon(2);
				break;				
		}		
	}
	private void fireGun(int x, int y) throws IOException {
	
		int weaponNumber = 0;
		if(mCurrentWeapon == Weapon.GUN) {
			weaponNumber = 0;
		}
		else if(mCurrentWeapon == Weapon.SHOTGUN) {
			weaponNumber = 1;
		}
		
		if(mAmmunition.get(weaponNumber).size() > 0) {
			mAmmunition.get(weaponNumber).remove(mAmmunition.get(weaponNumber).size() - 1);
			Image bulletMiss = null, bulletHit = null;
			
			if(mCurrentWeapon == Weapon.GUN) {
				bulletMiss = ImageFactory.loadImage("gunMiss");
				bulletHit = ImageFactory.loadImage("gunHit");
				mBullets.add(new GunBullet(bulletMiss, bulletHit, x, y, 3));
			}
			else if(mCurrentWeapon == Weapon.SHOTGUN) {
				bulletMiss = ImageFactory.loadImage("shotgunMiss");
				bulletHit = ImageFactory.loadImage("shotgunHit");
				mBullets.add(new ShotgunBullet(bulletMiss, bulletHit, x, y, 3));
			}	
		}		
	}
	private void changeWeapon(int weapon) {

		if(weapon == 1) {
			mCurrentWeapon = Weapon.GUN;
		}
		else if(weapon == 2) {
			mCurrentWeapon = Weapon.SHOTGUN;
		}
	}
	private void gameLoop() throws Exception {				
		
		for(GameObject duck : mDucks) {
			duck.update(mAnimationArea.getHeight(), mAnimationArea.getWidth());
		}
		for(GameObject bullet : mBullets) {
				bullet.update(mAnimationArea.getHeight(), mAnimationArea.getWidth());				
		}		
		for(GameObject duck : mDucks) {
			for(GameObject bullet : mBullets) {
				if(intersects(duck, bullet)) {
					bullet.isHit();
					duck.isHit();
				}
			}
		}				
		List<Sprite> renderedImages = new Vector<Sprite>();	
		renderedImages.addAll(mBackground);
		for(List<Sprite> l : mAmmunition) {
			renderedImages.addAll(l);
		}
		renderedImages.addAll(mDucks);
		renderedImages.addAll(mBullets);
		mAnimationArea.render(renderedImages);	
		
		boolean gameOver = true, won = true;
		
		for(GameObject g : mDucks) {			
			if(g.isAlive()) {
				won = false;
			}
		}
		for(List<Sprite> l : mAmmunition) {
			if(l.size() > 0) {
				gameOver = false;
			}
		}
		if(won) {
			nextLevel(mLevel += 5);
		}
		else if(gameOver) {
			Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			mAnimationArea.setCursor(cursor);
			mCurrentState = GameState.GameOver;
			mDucks = null;
		}
	}		
	private boolean intersects(GameObject sprite1, GameObject sprite2) {		
		boolean intersects = false;
		
		if(sprite1.isVisible() && sprite2.isVisible()) {
			Area collisionArea = sprite1.getArea();
			collisionArea.intersect(sprite2.getArea());
			if(!collisionArea.isEmpty()) {
				intersects = true;
			}			
		}		
		return  intersects;	
	}
	private void nextLevel(int numberOfDucks) throws IOException {
		//TODO make new levels more different then just the number of ducks
		List<Image> duckImages = new Vector<Image>();
		duckImages.add(ImageFactory.loadImage("duck1"));
		duckImages.add(ImageFactory.loadImage("duck2"));
		duckImages.add(ImageFactory.loadImage("duck3"));
				
		mDucks = new Vector<GameObject>();
		mBullets = new Vector<GameObject>();
		
		for(int i = 0; i < numberOfDucks; i ++) {
			mDucks.add(new Duck(duckImages, mAnimationArea.getWidth(), mAnimationArea.getHeight(), 5, 3));	
		}		
		mBackground = new Vector<Sprite>();
		mBackground.add(new GameSprite(ImageFactory.loadImage("clouds"), 0, 0));
		mBackground.add(new GameSprite(ImageFactory.loadImage("gun"), 50, 10));
		mBackground.add(new GameSprite(ImageFactory.loadImage("shotgun"), 200, 10));
		
		List<Sprite> gunAmmunition = new Vector<Sprite>();
		List<Sprite> shotgunAmmunition = new Vector<Sprite>();
		
		for(int i = 0; i < 10; i ++) {
			gunAmmunition.add(new GameSprite(ImageFactory.loadImage("gunBullet"), 120 + 5 * i, 10));
			shotgunAmmunition.add(new GameSprite(ImageFactory.loadImage("shotgunBullet"), 329 + 6 * i, 10));
		}
		mAmmunition = new Vector<List<Sprite>>();
		mAmmunition.add(gunAmmunition);
		mAmmunition.add(shotgunAmmunition);
	}
	private void startGame() throws IOException {
		if(mDucks == null) {
			mLevel = 5;
			nextLevel(mLevel);
		}		
		Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		mAnimationArea.setCursor(cursor);
		
		mCurrentState = GameState.Play;
	}
	private void pauseGame() {		
		Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
		mAnimationArea.setCursor(cursor);
		
		mCurrentState = GameState.Menu;
	}
	private void showMenu() throws IOException {
		List<Sprite> menu = new Vector<Sprite>();
		menu.add(new GameSprite(ImageFactory.loadImage("menu"), 0, 0));
		mAnimationArea.render(menu);
	}
	private void gameOver() throws IOException {
		List<Sprite> menu = new Vector<Sprite>();
		menu.add(new GameSprite(ImageFactory.loadImage("gameOver"), 0, 0));
		mAnimationArea.render(menu);		
	}
	@Override
	public void run() {
		try {
			while(true) {
				if(mCurrentState == GameState.Play) {
					this.gameLoop();
				}
				else if(mCurrentState == GameState.Menu) {
					this.showMenu();
				}
				else if(mCurrentState == GameState.GameOver) {
					this.gameOver();
				}
				Thread.sleep(50);
			}			
		} catch (Exception e) {			
			e.printStackTrace();
		}		
	}
}