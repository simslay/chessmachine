package chess.gui;

import java.awt.*;
import javax.swing.*;

/**
 * @author Grandsire Simon
 */

public class About extends JFrame {

	private static final long serialVersionUID = 1L;
	// ContentPane
	Container cp;
	// Panel
	JPanel jp=new JPanel();
	
	public About () { // contructeur
		super("About");
		setLayout(new BorderLayout());
		jp.setLayout(new GridLayout(5,1,0,0));
		// label 1
		addlab("    CHESSMACHINE version 0.2    ",0,150,255);
		// label 2
		addlab("24/08/2010",255,150,170);
		// label 3
		addlab(" bonne partie ! ",255,255,150);
		// label 4
		addlab(" createur : simslay ",255,255,150);
		// label 5
		addlab(" simon.grandsire@gmail.com ",255,255,150);
		add("Center",jp);
		this.setResizable(false);
		this.setSize(new Dimension(200,270));
		this.setLocationRelativeTo(null); // centre la fenetre
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
	public void addlab(String s, int a, int b, int c) {
		JLabel l = new JLabel(s); 
		l.setOpaque(true);
		l.setBackground(new Color(a,b,c));
		l.setHorizontalAlignment(JLabel.CENTER);
		jp.add(l);
	}
	
	//public Insets getInsets() {
		//return new Insets(5,5,5,5);
	//}
	
	 public void start() {
		 requestFocus();
		 toFront();
	}
	
}
