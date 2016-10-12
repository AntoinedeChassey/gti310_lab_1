package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import model.Image;

/**
 * The ImagePanel adds image display capabilities to a regular JPanel. The
 * image displayed on the panel cannot be changed. The ImagePanel does not
 * check to see if the image is valid, but makes sure that null exceptions
 * are not raised.
 * 
 * @author Fran�ois Caron <francois.caron.7@ens.etsmtl.ca>
 */
public class ImagePanel extends JPanel {	
	/** Generated serial version ID */
	private static final long serialVersionUID = -3554101564819375032L;
	
	/** The ARGB representation of the image */
	private BufferedImage _buffer = null;
	
	/** The Image handle */
	private Image _image;
	
	/**
	 * Create a new ImagePanel.
	 * @param image The image handled by the panel
	 */
	public ImagePanel(Image image) {
		/* store the image for further usage */
		_image = image;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		int x, y;
		
		/* make sure image is valid */
		if(_image != null) {
			
			/*
			 * Not sure...
			 */
			// Draw image, why do we need to check the below condition ?
			_buffer = _image.draw();
			/*
			 * End of ./Not sure...
			 */
			
			if(_buffer == null)
				_buffer = _image.draw();
			
			/* make sure received a valid Image to display */
			if(_buffer != null) {				
				/* center the image */
				x = (this.getWidth() - _image.getWidth()) / 2;
				y = (this.getHeight() - _image.getHeight()) / 2;
				setBounds(x,y,_image.getWidth(),_image.getHeight());
				
				/* draw the image */
				g.drawImage(_buffer,x,y,Color.BLACK,null);
			}
		}
	}
}
