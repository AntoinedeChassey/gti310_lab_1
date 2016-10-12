package controller;

import java.io.File;

import model.Image;

/**
 * The ImageFactory interface presents the definition to the method used to
 * create Image objects. A class redefining the ImageFactory interface should
 * only be used to create one type of Image (i.e. GIF, JPEG, propreatary file
 * format, ...).
 * 
 * @see ProxyFactory
 * 
 * @author François Caron <francois.caron.7@ens.etsmtl.ca>
 */
public interface ImageFactory {

	/**
	 * Create a new concrete Image instance using the file handle.
	 * @param file The file handle to the Image file.
	 * @return A new concrete Image instance or null if something went wrong.
	 */
	public abstract Image build(File file);
}
