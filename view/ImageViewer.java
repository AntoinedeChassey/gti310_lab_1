package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import model.Image;
import controller.ImageFileFilter;
import controller.ProxyFactory;

/**
 * The ImageViewer class is a JFrame in which it is possible to view many
 * images at the same time. Each image will be displayed on a tab.
 * 
 * @author François Caron <francois.caron.7@ens.etsmtl.ca>
 */
public class ImageViewer extends JFrame implements ActionListener {
	/** Generated serial version ID */
	private static final long serialVersionUID = -4720005788645827377L;	
	
	/** Tabs manager */
	private JTabbedPane _tabs;
	
	/** Graphical file selection engine */
	private JFileChooser _dialog;
	
	/*
	 *  (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();
		if(action.equals("open")) {
			/* add a panel to display the selected image */
			if(_dialog.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
				addTab(_dialog.getSelectedFile());
		} else if(action.equals("close")) {
			/* close current tab */
			if(_tabs.getSelectedComponent() != null)
				_tabs.remove(_tabs.getSelectedComponent());
		} else if(action.equals("quit")) {
			/* terminate the application */
			System.exit(0);
		} else {
			/* unknown action */
			System.out.println("unknown action: " + arg0.getActionCommand());
		}
	}
	
	/**
	 * Create a new tab that will be added to the main section. The new tab
	 * will display the image associated with the given handle.
	 * @param file The handle to the image file that will be displayed.
	 */
	private void addTab(File file) {
		/* create a proxy image*/
		Image image = ProxyFactory.getInstance().build(file);
		
		/* 
		 * if the proxy was created successfuly, add a tab to display the
		 * image.
		 */
		if(image != null)
			_tabs.addTab(file.getName(),new ImagePanel(image));
	}
	
	/**
	 * Build a menu for the user. Ofter "open", "close" and "quit" actions.
	 */
	private void buildMenu() {
		/* create menu bar */
		JMenuBar menubar = new JMenuBar();
		
		/* title of the menu that will hold the possible actions */
		JMenu menu = new JMenu("File");
		
		/* open file action */
		JMenuItem open = new JMenuItem("Open image");
		open.setActionCommand("open");
		open.addActionListener(this);
		open.setToolTipText("Select an image file to open");
		menu.add(open);
		
		/* close tab action */
		JMenuItem close = new JMenuItem("Close image");
		close.setActionCommand("close");
		close.addActionListener(this);
		close.setToolTipText("Close the active tab");
		menu.add(close);
		
		/* draw line between previous items and "quit" element */
		menu.addSeparator();
		
		/* quit action */
		JMenuItem quit = new JMenuItem("Quit");
		quit.setActionCommand("quit");
		quit.addActionListener(this);
		quit.setToolTipText("Quit the application");
		menu.add(quit);
		
		/* add menu 'File' to the menubar */
		menubar.add(menu);
		
		/* add menu bar to the frame */
		setJMenuBar(menubar);
	}

	/**
	 * Set the frame's properties to have a correct display.
	 */
	public void init() {
		/* set the frame's caption */
		setTitle("Java Image Viewer");
		
		/* regular border layout */
		setLayout(new BorderLayout());
		
		/* create menu */
		buildMenu();
		
		/* prepare file chooser engine */
		setupFileChooser();
		
		/* add tabs panel (main area) */
		_tabs = new JTabbedPane();
		add(_tabs);
		
		/* set default frame size */
		setSize(800,600);
		setVisible(true);
		
		/* terminate the application if the frame gets closed */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Prepare a JFileChooser dialog with the correct options to minimize the
	 * possible errors caused by an invalid selection by the user.
	 */
	private void setupFileChooser() {
		_dialog = new JFileChooser();
		/* remove the *.* option when selecting a file */
		_dialog.setAcceptAllFileFilterUsed(false);
		
		/* set the dialog's caption */
		_dialog.setDialogTitle("Please select a valid image file");
		
		/* make sure only files can be selected, not folders */
		_dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		/* add our custom filter */
		_dialog.addChoosableFileFilter(new ImageFileFilter());
		
		/* allow only one selection */
		_dialog.setMultiSelectionEnabled(false);
		
		/* set the starting directory to the project's dir */
		_dialog.setCurrentDirectory(new File("."));
	}
}
