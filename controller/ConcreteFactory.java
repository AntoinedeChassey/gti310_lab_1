package controller;

import java.io.File;

import model.Image;
import model.ConcreteImage;

public class ConcreteFactory implements ImageFactory {
	/** Singleton instance to the ProxyFactory */
	private static ImageFactory _instance = null;
	
	/** Hides the constructor from outside the class. */
	private ConcreteFactory() {};

	/**
	 * Creates a new instance of the ProxyFactory class if none exist.
	 * @return The well-known instance of the ProxyFactory class.
	 */
	public static ImageFactory getInstance() {
		if(_instance == null)
			_instance = new ConcreteFactory();
		return _instance;
	}

	@Override
	public Image build(File file) {
		// TODO Auto-generated method stub
		return new ConcreteImage(file);
	}

}
