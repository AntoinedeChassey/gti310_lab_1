package view;

/**
 * Application launcher.
 * 
 * @author François Caron <francois.caron.7@ens.etsmtl.ca>
 */
public class Application {

	/**
	 * Application's entry point.
	 * The command line arguments are ignored.
	 * 
	 * @param args 
	 */
	public static void main(String args[]) {
		(new ImageViewer()).init();
	}
}
