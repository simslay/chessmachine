package chess.gui;

import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

/**
 * @author Grandsire Simon
 */

public class Menu extends JPanel {

	private static final long serialVersionUID = 1L;
	private JRadioButton zero, un, deux, level1, level2, level3, level4, level5;
    private JPanel panel, pJoueurs, pJoueur1, pJoueur2, pNbJ, grille;
    private JComboBox combo1, combo2;
    private Icon avatar1, avatar2;
    private JTextField joueur1, joueur2;
    private boolean humanWhite, humanBlack, artificialWhite, artificialBlack;
    
    public Menu() {
        panel=new JPanel(new BorderLayout());
        pJoueurs=new JPanel(new BorderLayout());
        pJoueur1=new JPanel(new BorderLayout());
        pJoueur2=new JPanel(new BorderLayout());
        grille=new JPanel(new GridLayout(5,3));
        joueur1=new JTextField();
        joueur2=new JTextField();
        pNbJ=new JPanel();
        un=new JRadioButton("1 joueur",true);
        deux=new JRadioButton("2 joueurs",false);
        zero=new JRadioButton("2 joueurs artificiels",false);
        level1=new JRadioButton("Very Easy",true);
        level2=new JRadioButton("Easy",false);
        level3=new JRadioButton("Medium",false);
        level4=new JRadioButton("Difficult",false);
        level5=new JRadioButton("Very difficult",false);
        avatar1=new ImageIcon(getClass().getResource("/100px-Chess_ndt45.png"));
        avatar2=new ImageIcon(getClass().getResource("/100px-Chess_nlt45.png"));
        Icon[] images1=new Icon[] {avatar2, avatar1};
        Icon[] images2=new Icon[] {avatar1, avatar2};
        combo1=new JComboBox(images1); combo2=new JComboBox(images2);
        /*combo1.setPreferredSize(new Dimension(190, 190));
        combo2.setPreferredSize(new Dimension(190, 190));*/

        un.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (un.isSelected()) {
                    deux.setSelected(false);
                    zero.setSelected(false);
                    pJoueur1.setBorder(BorderFactory.createTitledBorder(" Joueur 1 "));
                    pJoueur1.removeAll();
                    pJoueur1.add("North", joueur1);
                    pJoueur1.add("Center", combo1);
                    pJoueur1.validate();
                    pJoueur2.setBorder(BorderFactory.createTitledBorder(" CPU "));
                    pJoueur2.removeAll();
                    pJoueur2.add("Center", combo2);
                    pJoueur2.validate();
                }
				else
                    un.setSelected(true);
            }
        });
        deux.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (deux.isSelected()) {
                    un.setSelected(false);
                    zero.setSelected(false);
                    pJoueur1.setBorder(BorderFactory.createTitledBorder(" Joueur 1 "));
                    pJoueur1.removeAll();
                    pJoueur1.add("North", joueur1);
                    pJoueur1.add("Center", combo1);
                    pJoueur1.validate();
                    pJoueur2.setBorder(BorderFactory.createTitledBorder(" Joueur 2 "));
                    pJoueur2.removeAll();
                    pJoueur2.add("North", joueur2);
                    pJoueur2.add("Center", combo2);
                    pJoueur2.validate();
                }
                else
                    deux.setSelected(true);
            }
        });
        zero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (zero.isSelected()) {
                    un.setSelected(false);
                    deux.setSelected(false);
                    pJoueur1.setBorder(BorderFactory.createTitledBorder(" CPU "));
                    pJoueur1.removeAll();
                    pJoueur1.add("Center", combo1);
                    pJoueur1.validate();
                    pJoueur2.setBorder(BorderFactory.createTitledBorder(" CPU "));
                    pJoueur2.removeAll();
                    pJoueur2.add("Center", combo2);
                    pJoueur2.validate();
                }
                else
                	zero.setSelected(true);
            }
        });
        level1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (level1.isSelected()) {
                    level2.setSelected(false);
                    level3.setSelected(false);
                    level4.setSelected(false);
                    level5.setSelected(false);
                }
				else
                    level1.setSelected(true);
            }
        });
        level2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (level2.isSelected()) {
                    level1.setSelected(false);
                    level3.setSelected(false);
                    level4.setSelected(false);
                    level5.setSelected(false);
                }
				else
                    level2.setSelected(true);
            }
        });
        level3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (level3.isSelected()) {
                    level1.setSelected(false);
                    level2.setSelected(false);
                    level4.setSelected(false);
                    level5.setSelected(false);
                }
				else
                    level3.setSelected(true);
            }
        });
        level4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (level4.isSelected()) {
                    level1.setSelected(false);
                    level2.setSelected(false);
                    level3.setSelected(false);
                    level5.setSelected(false);
                }
				else
                    level4.setSelected(true);
            }
        });
        level5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (level5.isSelected()) {
                    level1.setSelected(false);
                    level2.setSelected(false);
                    level3.setSelected(false);
                    level4.setSelected(false);
                }
				else
                    level5.setSelected(true);
            }
        });
        combo1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae) {
        		Icon icon=(Icon) combo1.getSelectedItem();
        		if (icon == avatar1) { // black
        			combo2.setSelectedItem(avatar2); // white 
        		} else if (icon == avatar2) {
        			combo2.setSelectedItem(avatar1);
        		}
        	}
        });
        combo2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae) {
        		Icon icon=(Icon) combo2.getSelectedItem();
        		if (icon == avatar1) {
        			combo1.setSelectedItem(avatar2);
        		} else if (icon == avatar2) {
        			combo1.setSelectedItem(avatar1);
        		}
        	}
        });
        pNbJ.add(un);pNbJ.add(deux);pNbJ.add(zero);
        grille.add(new JPanel()); grille.add(level1); grille.add(new JPanel());
        grille.add(new JPanel()); grille.add(level2); grille.add(new JPanel());
        grille.add(new JPanel()); grille.add(level3); grille.add(new JPanel());
        grille.add(new JPanel()); grille.add(level4); grille.add(new JPanel());
        grille.add(new JPanel()); grille.add(level5); grille.add(new JPanel());

        pJoueur1.setBorder(BorderFactory.createTitledBorder(" Joueur 1 "));
        //pJoueur1.setPreferredSize(new Dimension(200,100));
        pJoueur1.add("North", joueur1);
        pJoueur1.add("Center", combo1);
        pJoueur2.setBorder(BorderFactory.createTitledBorder(" CPU "));
        //pJoueur2.setPreferredSize(new Dimension(200,100));
        pJoueur2.add("Center", combo2);
        pNbJ.setBorder(BorderFactory.createTitledBorder(" Choix des joueurs "));
        grille.setBorder(BorderFactory.createTitledBorder(" Choix du niveau "));
        pJoueurs.add("West", pJoueur1);
        pJoueurs.add("East", pJoueur2);
        panel.add("North", pNbJ);
        panel.add("South", pJoueurs);
        panel.add("Center", grille);
        panel.setPreferredSize(new Dimension(400,400));
        //panel.setBackground(Color.DARK_GRAY);

        add(panel);
    }

    /* getters */
	public JComboBox getCombo1() {return combo1;}
    public JComboBox getCombo2() {return combo2;}
    public Icon getavatar1() {return avatar1;}
    public Icon getavatar2() {return avatar2;}
    public JRadioButton getUn() {return un;}
    public JRadioButton getDeux() {return deux;}
    public JRadioButton getZero() {return zero;}
    public JRadioButton getlevel1() {return level1;}
    public JRadioButton getlevel2() {return level2;}
    public JRadioButton getlevel3() {return level3;}
    public JRadioButton getlevel4() {return level4;}
    public JRadioButton getlevel5() {return level5;}
    public JTextField getJoueur1() {return joueur1;}
    public JTextField getJoueur2() {return joueur2;}
    public JPanel getPJoueur1() {return pJoueur1;}
    public JPanel getPJoueur2() {return pJoueur2;}
    public boolean isHumanWhite() {return humanWhite;}
	public boolean isHumanBlack() {return humanBlack;}
	public boolean isArtificialWhite() {return artificialWhite;}
	public boolean isArtificialBlack() {return artificialBlack;}
    
    /* setters */
    public void setHumanWhite(boolean humanWhite) {this.humanWhite = humanWhite;}
	public void setHumanBlack(boolean humanBlack) {this.humanBlack = humanBlack;}
	public void setArtificialWhite(boolean artificialWhite) {this.artificialWhite = artificialWhite;}
	public void setArtificialBlack(boolean artificialBlack) {this.artificialBlack = artificialBlack;}
}
