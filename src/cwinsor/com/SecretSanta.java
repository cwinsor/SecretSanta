package cwinsor.com;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * Secret Santa
 * @author cwinsor
 *
 */
public class SecretSanta extends JPanel {
	private static final long serialVersionUID = 1L;

	protected List<String> nameList = new ArrayList<String>();

	protected Action addName;
	protected Action delName;
	protected Action genRandomPairs;

	protected static JFrame frame;
	protected JTextArea bottomTextArea;
	protected String newline = "\n ";


	/*
	 * main entry point
	 */
	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}


	/*
	 * construct the JFrame
	 */
	private static void createAndShowGUI(){

		//Create and set up the frame
		frame = new JFrame("Secret Santa");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SecretSanta leafRakingJPanel = new SecretSanta();
		frame.setJMenuBar(leafRakingJPanel.createMenuBar());
		frame.setContentPane(leafRakingJPanel.contentPaneAddMessageArea());

		//Display the window.
		frame.setSize(500, 400);
		frame.setVisible(true);
	}


	/**
	 * constructor
	 */
	public SecretSanta() {
		super(new BorderLayout());

		addName = new ActionAddName("Add Name",
				null,
				"Add a name", 
				new Integer(KeyEvent.VK_A));

		delName = new ActionDelName("Delete Name",
				null,
				"Delete a name", 
				new Integer(KeyEvent.VK_D));

		genRandomPairs = new ActionGenerateSantaPairs("Generate Secret Santa pairings",
				null,
				"Generate the Secret Santa pairings", 
				new Integer(KeyEvent.VK_G));
	}


	/*
	 * action class
	 */
	public class ActionAddName extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionAddName(String text, ImageIcon icon,
				String desc, Integer mnemonic) {
			super(text, icon);
			putValue(SHORT_DESCRIPTION, desc);
			putValue(MNEMONIC_KEY, mnemonic);
		}
		public void actionPerformed(ActionEvent e) {

			String s = (String)JOptionPane.showInputDialog(
					frame,
					"Name to add:\n",
					"Customized Dialog",
					JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					"");
			//If a string was returned, say so.
			if ((s != null) && (s.length() > 0)) {
				nameList.add(s);
				displayResult("----names---");
				for (String name : nameList) {
					displayResult(name);
				}
			}
			else {
				//If you're here, the return value was null/empty.
				displayResult("Come on, give me a name!");
			}
		}
	}

	/*
	 * action class
	 */
	public class ActionDelName extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public ActionDelName(String text, ImageIcon icon,
				String desc, Integer mnemonic) {
			super(text, icon);
			putValue(SHORT_DESCRIPTION, desc);
			putValue(MNEMONIC_KEY, mnemonic);
		}
		public void actionPerformed(ActionEvent e) {

			String s = (String)JOptionPane.showInputDialog(
					frame,
					"Name to delete:\n",
					"Customized Dialog",
					JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					"");
			//If a string was returned, say so.
			if ((s != null) && (s.length() > 0)) {
				nameList.remove(s);
				displayResult("----names---");
				for (String name : nameList) {
					displayResult(name);
				}
			}
			else {
				//If you're here, the return value was null/empty.
				displayResult("Come on, give me a name!");
			}
		}
	}


	
	/**
	 * action class
	 */
	public class ActionGenerateSantaPairs extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private int clkCnt = 0;
		public ActionGenerateSantaPairs(String text, ImageIcon icon,
				String desc, Integer mnemonic) {
			super(text, icon);
			putValue(SHORT_DESCRIPTION, desc);
			putValue(MNEMONIC_KEY, mnemonic);
		}
		public void actionPerformed(ActionEvent e) {
			clkCnt++;
			displayResult("clicked " + clkCnt + " time" + ((clkCnt==1)?"":"s"));

			ArrayList<Integer>pairing = new ArrayList<Integer>();
			for (Integer i=0; i<nameList.size(); i++) {
				pairing.add(i);
			}

			boolean done;
			int stopCnt = 100;
			do {
				stopCnt--;
				displayResult("shuffle...");
				Collections.shuffle(pairing);
				done = true;
				for (Integer i: pairing) {
					if (i.equals(pairing.get(i))) {
						done = false;
					}
				}
			} while (!done && (stopCnt>0));

			displayResult("------------");
			if (stopCnt <= 0) {
				displayResult("oops");
			} else {
				for (Integer i=0; i<nameList.size(); i++) {
					displayResult(nameList.get(i) + " buys for " + nameList.get(pairing.get(i)));
				}
			}
		}
	}

	/*
	 * create the menu bar
	 */
	protected JMenuBar createMenuBar() {

		// create menu bar
		JMenuBar menuBar;
		//		JMenu menu;
		//		JMenuItem menuItem;


		menuBar = new JMenuBar();

		//file menu
		//		menu = new JMenu("Menu");
		//		menu.setMnemonic(KeyEvent.VK_F);
		//		menu.getAccessibleContext().setAccessibleDescription("Menu");

		//		menuItem = new JMenuItem(addName);
		//		menu.add(menuItem);

		//		menuItem = new JMenuItem(genRandomPairs);
		//		menu.add(menuItem);

		//		menuBar.add(menu);

		JButton buttonAdd = new JButton(addName);
		menuBar.add(buttonAdd);

		JButton buttonDel = new JButton(delName);
		menuBar.add(buttonDel);

		JButton buttonGen = new JButton(genRandomPairs);
		menuBar.add(buttonGen);

		return  menuBar;
	}


	/*
	 * create content pane
	 */
	private Container contentPaneAddMessageArea() {

		//Create a scrolled text area.
		bottomTextArea = new JTextArea(200, 200);
		bottomTextArea.setEditable(false);

		JScrollPane scrollPaneEnd = new JScrollPane(bottomTextArea);
		this.add(scrollPaneEnd, BorderLayout.CENTER);

		return this;	
	}

	/*
	 * display a message in the bottom text box
	 */
	public void displayResult(String message) {
		String s = (message
				+ newline);
		bottomTextArea.append(s);
	}


}
