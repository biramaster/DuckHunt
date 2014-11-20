package duckhunt2;
 
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.List;

/**
 * An animation area that uses double buffering and can draw
 * any sprite
 * 
 * @author Thomas Ejnefj�ll
 */
public class AnimationArea extends Canvas {

	private static final long serialVersionUID = -5880894372317064598L;
	private BufferStrategy mBufferStrategy = null;

	/**
	 * Constructs an AnimationArea
	 */
	public AnimationArea() {	
		this.setIgnoreRepaint(true);			
	}
	@Override
	public void addNotify() {
		super.addNotify();
		
		if(mBufferStrategy == null) {
			this.createBufferStrategy(2);
			mBufferStrategy = this.getBufferStrategy();		
		}		
	}
	/**
	 * Draws the provided sprites in the list starting with index 0
	 * 
	 * @param sprites the sprites we want to draw
	 */
	public void render(List<Sprite> sprites) {
		if(mBufferStrategy != null) {
			Graphics g = mBufferStrategy.getDrawGraphics();	
			for(Sprite s : sprites) {
				if(s.isVisible()) {
					g.drawImage(s.getImage(), s.getX(), s.getY(), s.getWidth(), s.getHeight(), null);
				}				
			}
			g.dispose();
			mBufferStrategy.show();
		}
	}	
}