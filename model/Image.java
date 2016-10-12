package model;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * The Image abstract class defines pure abstract methods that must be
 * redefined by the specialized class. All Images share one common attribute
 * that can only be store the same way : the pointer to the file handle. All
 * other attributes can be implemented differently.
 * 
 * @see ProxyImage
 * 
 * @author François Caron <francois.caron.7@ens.etsmtl.ca>
 */
public abstract class Image {

	/** The handle to the image file */
	protected File _file;
	
	/**
	 * Create a BufferedImage object to match the display's RGB requirements. 
	 * @return A BufferedImage representing the image file, or null if
	 * something went wrong.
	 */
	public abstract BufferedImage draw();
	
	/**
	 * 
	 * @return The Image's height
	 */
	public abstract int getHeight();
	
	/**
	 * 
	 * @return The Image's width
	 */
	public abstract int getWidth();
}
