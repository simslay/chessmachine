package chess.gui;

import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import chess.game.players.Player;

/**
 * @author Grandsire Simon
 */

public class MenuFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Container content=this.getContentPane();
	private Menu menu;
    private JButton bJouer,bQuitter;
    private JPanel pJouer;
	
	public MenuFrame () {
		
        /**************************/
        /* CREATION DE LA FENETRE */
        /**************************/
		
		super("Chessmachine (Menu)");
        this.setSize(new Dimension(600,570));
        this.setLocationRelativeTo(null); // a mettre apres les dimensions
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        try {
        	this.setIconImage(ImageIO.read(getClass().getResource("/200px-Chess_rlt45.svg.png")));
        } catch (IOException e) {
        	e.printStackTrace();
        }

            /* Barre de menus */

        JMenuItem mi1,mi2,mi3,mi4;
		// barre de menus
		JMenuBar mb=new JMenuBar();
		this.setJMenuBar(mb);
		JMenu m1=new JMenu("Fichier");
		JMenu m4=new JMenu("Help");
        m1.setMnemonic(ActionEvent.ALT_MASK);
		m1.getAccessibleContext().setAccessibleDescription("Menu Fichier");
		mb.add(m1);
		mb.add(m4);
		// item menu Fichier
		mi1=new JMenuItem("Quitter");
		mi1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		m1.add(mi1);
        mi1.addActionListener(this);
		// items menu Help
		mi1=new JMenuItem("Mode d'emploi");
        mi2=new JMenuItem("Informations");
        mi3=new JMenuItem("Versions");
        mi4=new JMenuItem("About");
		m4.add(mi1);
		m4.add(mi2);
		m4.add(mi3);
		m4.addSeparator();
		m4.add(mi4);
        mi1.addActionListener(this);mi2.addActionListener(this);
        mi3.addActionListener(this);mi4.addActionListener(this);

        menu=new Menu();
        pJouer=new JPanel();
        content.add(menu,BorderLayout.CENTER);
        bJouer=new JButton("Jouer");
        bQuitter=new JButton("Quitter");
        bJouer.addActionListener(this);
        bQuitter.addActionListener(this);
        pJouer.add(bJouer);pJouer.add(bQuitter);
        content.add(pJouer,BorderLayout.SOUTH);

        this.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent ae) {
        AbstractButton source;
        content=getContentPane();
        JButton bMenu=new JButton("Menu");
        bMenu.addActionListener(this);
        if (ae.getSource() instanceof JMenuItem)
            source = (JMenuItem)(ae.getSource());
        else {
            source = (JButton)(ae.getSource());
        }
		// menu Fichier
		if (source.getText().equals("Quitter")) {
			System.exit(0);
		}

        if (source.getText().equals("About"))
			new About();
        
        if (source.getText().equals("Jouer")) {
        	if (menu.getUn().isSelected()) {
        		
        		if (menu.getCombo1().getSelectedItem() == menu.getavatar1()) { // black
        			if (menu.getlevel1().isSelected())
        				new BoardFrame(Player.ARTIFICIAL, Player.HUMAN, 1);
        			else if (menu.getlevel2().isSelected())
        				new BoardFrame(Player.ARTIFICIAL, Player.HUMAN, 2);
        			else if (menu.getlevel3().isSelected())
        				new BoardFrame(Player.ARTIFICIAL, Player.HUMAN, 3);
        			else if (menu.getlevel4().isSelected())
        				new BoardFrame(Player.ARTIFICIAL, Player.HUMAN, 4);
        			else if (menu.getlevel5().isSelected())
        				new BoardFrame(Player.ARTIFICIAL, Player.HUMAN, 5);
        		} else if (menu.getCombo1().getSelectedItem() == menu.getavatar2()) { // white
        			if (menu.getlevel1().isSelected())
        				new BoardFrame(Player.HUMAN, Player.ARTIFICIAL, 1);
        			else if (menu.getlevel2().isSelected())
        				new BoardFrame(Player.HUMAN, Player.ARTIFICIAL, 2);
        			else if (menu.getlevel3().isSelected())
        				new BoardFrame(Player.HUMAN, Player.ARTIFICIAL, 3);
        			else if (menu.getlevel4().isSelected())
        				new BoardFrame(Player.HUMAN, Player.ARTIFICIAL, 4);
        			else if (menu.getlevel5().isSelected())
        				new BoardFrame(Player.HUMAN, Player.ARTIFICIAL, 5);
        		}
        		
        	} else if (menu.getDeux().isSelected()) {
        		
        		new BoardFrame(Player.HUMAN, Player.HUMAN, 0);

        	} else if (menu.getZero().isSelected()) {
        		if (menu.getlevel1().isSelected())
    				new BoardFrame(Player.ARTIFICIAL, Player.ARTIFICIAL, 1);
    			else if (menu.getlevel2().isSelected())
    				new BoardFrame(Player.ARTIFICIAL, Player.ARTIFICIAL, 2);
    			else if (menu.getlevel3().isSelected())
    				new BoardFrame(Player.ARTIFICIAL, Player.ARTIFICIAL, 3);
    			else if (menu.getlevel4().isSelected())
    				new BoardFrame(Player.ARTIFICIAL, Player.ARTIFICIAL, 4);
    			else if (menu.getlevel5().isSelected())
    				new BoardFrame(Player.ARTIFICIAL, Player.ARTIFICIAL, 5);
        	}
        }
    }
	
}
