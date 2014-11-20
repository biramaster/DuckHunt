package duckhunt2;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * Loads images from file.
 * 
 * @author Thomas Ejnefj�ll
 */
public class ImageFactory {
	private ImageFactory() {
		
	}
	/**
	 * Loads an image with the given name
	 * 
	 * @param imageName name of the image without extension
	 * @return image with the given name if it exists
	 * @throws IOException if the image does not exist or there was an IO error
	 */
	public static Image loadImage(String imageName) throws IOException {
		Image image = null;
        URL imageURL = ImageFactory.class.getResource("/img/" + imageName + ".png");
        
        //Running from jar-file
        if(imageURL != null)
        {        	
        	image = Toolkit.getDefaultToolkit().getImage(imageURL); 
        	
        	if(image == null) {
        		throw new IOException();
        	}
        }
        //Running from IDE
        else
        {
        	image = ImageIO.read(new File("img/" + imageName + ".png"));
        }    
        return image;
	}
}