package chess.net;

import java.awt.event.*;
import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import chess.game.UndefinedPlayerException;
import chess.game.players.Player;
import chess.game.players.Player.Fevals;
import chess.game.players.artificial.*;
import chess.gui.MessageFrame;
import chess.gui.optional.*;


/**
 * @author Grandsire Simon
 */

public class BoardFrameServer extends JFrame implements ActionListener, WindowListener {

	private static final long serialVersionUID = 1L;
	private Container content = this.getContentPane();
	private BoardServer board;
	private PositionFrame positionFrame;
	private MessageFrame messageFrame;
	private CaptureFrame captureFrame;

	public BoardFrameServer (int wType, int bType, int level) {
		
        /**************************/
        /* CREATION DE LA FENETRE */
        /**************************/
		
		super("Chessmachine (Board)");
        this.setSize(new Dimension(600, 570));
        this.setLocationRelativeTo(null); // a mettre apres les dimensions
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(true);
        try {
        	this.setIconImage(ImageIO.read(getClass().getResource("/200px-Chess_rlt45.svg.png")));
        } catch (IOException e) {
        	e.printStackTrace();
        }
        addWindowListener(this);
        
        /* Barre de menus */

        JMenuItem mi1, mi2, mi3, mi4;
		// bar de menus
		JMenuBar mb=new JMenuBar();
		// menu Game
		this.setJMenuBar(mb);
		JMenu m1=new JMenu("Game");
        m1.setMnemonic(ActionEvent.ALT_MASK);
		m1.getAccessibleContext().setAccessibleDescription("Game Menu");
		mb.add(m1);
		// item menu Game
		mi1=new JMenuItem("Quit");
		mi1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		m1.add(mi1);
        mi1.addActionListener(this);
        // menu Display
        JMenu m2=new JMenu("Display");
        m1.getAccessibleContext().setAccessibleDescription("Display Menu");
        mb.add(m2);
        // item menu Display
        mi2=new JMenuItem("Message");
        m2.add(mi2);
        mi2.addActionListener(this);
        mi3=new JMenuItem("Position");
        m2.add(mi3);
        mi3.addActionListener(this);
        mi4=new JMenuItem("Captures");
        m2.add(mi4);
        mi4.addActionListener(this);
        
        board=new BoardServer(wType, bType);
        System.out.println("level = "+level);
        if (level != 0) {
        	switch (level) {
        		case 1 :
        			System.out.println("very easy");
        			if (wType == Player.ARTIFICIAL) {
        				ArtificialWhite.feval = Fevals.wAlea;
        				ArtificialWhite.mode = Player.HORIZON1;
        				
        				if (bType == Player.ARTIFICIAL) {
            				ArtificialBlack.feval = Fevals.bAlea;
            				ArtificialBlack.mode = Player.HORIZON1;
            			}
        			} else if (bType == Player.ARTIFICIAL) {
        				ArtificialBlack.feval = Fevals.bAlea;
        				ArtificialBlack.mode = Player.HORIZON1;
        				
        				if (wType == Player.ARTIFICIAL) {
            				ArtificialWhite.feval = Fevals.wAlea;
            				ArtificialWhite.mode = Player.HORIZON1;
        				}
        			}
        			break;
        		case 2 :
        			System.out.println("easy");
        			if (wType == Player.ARTIFICIAL) {
        				ArtificialWhite.feval = Fevals.wCaptures;
        				ArtificialWhite.mode = Player.HORIZON1;
        				
        				if (bType == Player.ARTIFICIAL) {
            				ArtificialBlack.feval = Fevals.bCaptures;
            				ArtificialBlack.mode = Player.HORIZON1;
            			}
        			} else if (bType == Player.ARTIFICIAL) {
        				ArtificialBlack.feval = Fevals.bCaptures;
        				ArtificialBlack.mode = Player.HORIZON1;
        				
        				if (wType == Player.ARTIFICIAL) {
            				ArtificialWhite.feval = Fevals.wCaptures;
            				ArtificialWhite.mode = Player.HORIZON1;
        				}
        			}
        			break;
        		case 3 :
        			System.out.println("medium");
        			if (wType == Player.ARTIFICIAL) {
        				ArtificialWhite.feval = Fevals.wCaptures;
        				ArtificialWhite.mode = Player.ALPHABETA;
        				ArtificialWhite.PMAX = 2;
        				
        				if (bType == Player.ARTIFICIAL) {
            				ArtificialBlack.feval = Fevals.bCaptures;
            				ArtificialBlack.mode = Player.ALPHABETA;
            				ArtificialBlack.PMAX = 2;
            			}
        			} else if (bType == Player.ARTIFICIAL) {
        				ArtificialBlack.feval = Fevals.bCaptures;
        				ArtificialBlack.mode = Player.ALPHABETA;
        				ArtificialBlack.PMAX = 2;
        				
        				if (wType == Player.ARTIFICIAL) {
            				ArtificialWhite.feval = Fevals.wCaptures;
            				ArtificialWhite.mode = Player.ALPHABETA;
            				ArtificialWhite.PMAX = 2;
        				}
        			}
        			break;
        		case 4 :
        			System.out.println("difficult");
        			if (wType == Player.ARTIFICIAL) {
        				ArtificialWhite.feval = Fevals.wFeval;
        				ArtificialWhite.mode = Player.ALPHABETA;
        				ArtificialWhite.PMAX = 2;
        				
        				if (bType == Player.ARTIFICIAL) {
            				ArtificialBlack.feval = Fevals.bFeval;
            				ArtificialBlack.mode = Player.ALPHABETA;
            				ArtificialBlack.PMAX = 2;
            			}
        			} else if (bType == Player.ARTIFICIAL) {
        				ArtificialBlack.feval = Fevals.bFeval;
        				ArtificialBlack.mode = Player.ALPHABETA;
        				ArtificialBlack.PMAX = 2;
        				
        				if (wType == Player.ARTIFICIAL) {
            				ArtificialWhite.feval = Fevals.wFeval;
            				ArtificialWhite.mode = Player.ALPHABETA;
            				ArtificialWhite.PMAX = 2;
        				}
        			}
        			break;
        		case 5 :
        			System.out.println("very difficult");
        			if (wType == Player.ARTIFICIAL) {
        				ArtificialWhite.feval = Fevals.wFeval;
        				ArtificialWhite.mode = Player.ALPHABETA;
        				ArtificialWhite.PMAX = 4;
        				
        				if (bType == Player.ARTIFICIAL) {
        					ArtificialBlack.feval = Fevals.bFeval;
            				ArtificialBlack.mode = Player.ALPHABETA;
            				ArtificialBlack.PMAX = 4;
        				}
        				
        			} else if (bType == Player.ARTIFICIAL) {
        				ArtificialBlack.feval = Fevals.bFeval;
        				ArtificialBlack.mode = Player.ALPHABETA;
        				ArtificialBlack.PMAX = 4;
        				
        				if (wType == Player.ARTIFICIAL) {
            				ArtificialWhite.feval = Fevals.wFeval;
            				ArtificialWhite.mode = Player.ALPHABETA;
            				ArtificialWhite.PMAX = 4;
        				}
        			}
        			break;
        		default :
        			System.out.println("default");
        			break;
        	}
        }
        content.add(board, BorderLayout.CENTER);
        
        positionFrame=null;

        this.pack();
        this.setVisible(true);
        
        messageFrame=new MessageFrame(this.getLocationOnScreen());
		board.setMessageLabel(messageFrame.getMessageLabel());
		
		//System.out.println("new BoardFrame()");
		
		if (wType == Player.ARTIFICIAL && bType == Player.HUMAN) {
			try {board.play();}
			catch (UndefinedPlayerException e) {e.printStackTrace();}
		}
	}

	public void actionPerformed(ActionEvent ae) {
        AbstractButton source;
        
        if (ae.getSource() instanceof JMenuItem)
            source = (JMenuItem)(ae.getSource());
        else {
            source = (JButton)(ae.getSource());
        }
        
		// menu Fichier
		if (source.getText().equals("Quit")) {
			if (positionFrame!=null && positionFrame.isEnabled())
				positionFrame.dispose();
			this.dispose();
		}
		
		if (source.getText().equals("Position") && positionFrame==null) {
			setPositionFrame(new PositionFrame(this.getLocationOnScreen()));
			board.setPositionLabel(positionFrame.getPositionLabel());
		} else if (source.getText().equals("Position") && positionFrame!=null)
			positionFrame.setVisible(true);
		
		if (source.getText().equals("Message") && messageFrame==null) {
			setMessageFrame(new MessageFrame(this.getLocationOnScreen()));
			board.setMessageLabel(messageFrame.getMessageLabel());
		} else if (source.getText().equals("Message") && messageFrame!=null)
			messageFrame.setVisible(true);
		
		if (source.getText().equals("Captures") && captureFrame==null) {
			setCaptureFrame(new CaptureFrame(this.getLocationOnScreen()));
			board.setCapturePanel(captureFrame.getCapturePanel());
		} else if (source.getText().equals("Captures") && captureFrame!=null)
			captureFrame.setVisible(true);
    }
	
	public void windowActivated(WindowEvent we) {
		/*if (messageFrame.isValid()) {
			messageFrame.setVisible(true);
			messageFrame.requestFocusInWindow();
		}*/
	}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we) {
		if (positionFrame!=null) {
			positionFrame.dispose();
		}
		if (messageFrame!=null) {
			messageFrame.dispose();
		}
		if (captureFrame!=null) {
			captureFrame.dispose();
		}
	}
	public void windowDeactivated(WindowEvent we) {
		/*if (positionFrame!=null)
			this.requestFocus();*/
	}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}

	/* getters */
	public static long getSerialversionuid() {return serialVersionUID;}
	public Container getContent() {return content;}
	public BoardServer getBoard() {return board;}
	public PositionFrame getPositionFrame() {return positionFrame;}
	public MessageFrame getMessageFrame() {return messageFrame;}
	public CaptureFrame getCaptureFrame() {return captureFrame;}
	/* setters */
	public void setContent(Container content) {this.content = content;}
	public void setBoard(BoardServer board) {this.board = board;}
	public void setPositionFrame(PositionFrame positionFrame) {this.positionFrame = positionFrame;}
	public void setMessageFrame(MessageFrame messageFrame) {this.messageFrame = messageFrame;}
	public void setCaptureFrame(CaptureFrame captureFrame) {this.captureFrame = captureFrame;}
	
}
