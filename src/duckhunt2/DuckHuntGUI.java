package duckhunt2;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

/**
 * The main GUI of the Duck Hunt game
 * 
 * @author Thomas Ejnefj�ll
 */
public class DuckHuntGUI extends JFrame {

	private static final long serialVersionUID = -3309115035617683600L;
	private DuckHuntLogic mAnimationController = null;
	private AnimationArea mAnimationArea = null;
	
	/**
	 * Constructs the main GUI
	 */
	public DuckHuntGUI() {
		this.initializeGUI();				
	}
	private void initializeGUI() {
		this.setTitle("Duck hunt");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(800, 600); 
		
		mAnimationArea = new AnimationArea();
				
		this.getContentPane().add(mAnimationArea);
		this.mAnimationController = new DuckHuntLogic(mAnimationArea);
		
		this.addListeners(mAnimationArea);
	}
	private void addListeners(AnimationArea panel) {			
		this.addWindowFocusListener(new WindowAdapter() {
		    @Override
			public void windowGainedFocus(WindowEvent e) {
		    	mAnimationArea.requestFocusInWindow();
		    }
		});		
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mAnimationController.handleMouseMoved(e.getX(), e.getY());
			}
		});		
		panel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				try {
					mAnimationController.handleKeyPressed(e.getKeyCode());
				} catch (IOException ioe) {					
					ioe.printStackTrace();
				}				
			}
		});
	}
}