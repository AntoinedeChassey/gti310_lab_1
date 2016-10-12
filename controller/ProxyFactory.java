package controller;

import java.io.File;

import model.Image;
import model.ProxyImage;

/**
 * 
 * @author François Caron <francois.caron.7@ens.etsmtl.ca>
 */
public class ProxyFactory implements ImageFactory {
	
	/** Singleton instance to the ProxyFactory */
	private static ImageFactory _instance = null;
	
	/** Hides the constructor from outside the class. */
	private ProxyFactory() {};
	
	/*
	 *  (non-Javadoc)
	 * @see controller.ImageFactory#build(java.io.File)
	 */
	public Image build(File file) {
		return new ProxyImage(file);
	}
	
	/**
	 * Creates a new instance of the ProxyFactory class if none exist.
	 * @return The well-known instance of the ProxyFactory class.
	 */
	public static ImageFactory getInstance() {
		if(_instance == null)
			_instance = new ProxyFactory();
		return _instance;
	}

}
