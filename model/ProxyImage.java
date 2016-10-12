package model;

import java.awt.image.BufferedImage;
import java.io.File;
import controller.ConcreteFactory;

/**
 * The ProxyImage object acts as an intermediate between the actual Image and
 * the object using the Image. As long as the Image doesn't need to be drawn,
 * the ProxyImage will sent information to the caller (false one in most cases,
 * but it is better than a NullPointerException).
 * 
 * @author François Caron <francois.caron.7@ens.etsmtl.ca>
 */
public class ProxyImage extends Image {

	/** The actual subject the proxy is replacing */
	private Image _concrete = null;
	
	/**
	 * Create a proxy to cover for the actual Image.
	 * @param file The handle to the image file.
	 */
	public ProxyImage(File file) {
		super._file = file;		
	}
	
	@Override
	public BufferedImage draw() {
		/* if the Image is not created, instanciate it */
		if (_concrete == null)
			_concrete = ConcreteFactory.getInstance().build(super._file);
		else
			return _concrete.draw();
		
		/* if the Image could not be created for some reason, return null */
		return null;
	}

	@Override
	public int getHeight() {
		/* if the Image is not created, send false information */
		if(_concrete == null)
			return 100;
		else
			return _concrete.getHeight();
	}

	@Override
	public int getWidth() {
		/* if the Image is not created, send false information */
		if(_concrete == null)
			return 100;
		else
			return _concrete.getWidth();
	}
}
