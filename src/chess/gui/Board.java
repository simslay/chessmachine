package chess.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import chess.game.Game;
import chess.game.GameUtil;
import chess.game.State;
import chess.game.UndefinedPieceException;
import chess.game.UndefinedPlayerException;
import chess.game.pieces.*;
import chess.game.players.Player;
//import chess.game.players.artificial.ArtificialBlack;

/**
 * @author Grandsire Simon
 */

public class Board extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	
	/* jeu */
	private Game game;
	/* image de l'echiquier */
	private Image board;
	/* pieces images des pieces blanches */
	private PieceImage[] wht_pwns;
	private PieceImage wht_rk1, wht_rk2;
	private PieceImage wht_knt1, wht_knt2;
	private PieceImage wht_bsp1, wht_bsp2;
	private PieceImage wht_kng, wht_qn;
	/* pieces images des pieces noires */
	private PieceImage[] blk_pwns;
	private PieceImage blk_rk1, blk_rk2;
	private PieceImage blk_knt1, blk_knt2;
	private PieceImage blk_bsp1, blk_bsp2;
	private PieceImage blk_kng, blk_qn;
	/* images des pieces blanches */
	Image wht_pwn_img, wht_rk_img, wht_knt_img;
	Image wht_bsp_img, wht_qn_img, wht_kng_img;
	/* images des pieces noires */
	Image blk_pwn_img, blk_rk_img, blk_knt_img;
	Image blk_bsp_img, blk_qn_img, blk_kng_img;
	/* tableau des images des pieces */
	private PieceImage[] pieces;
	/* piece selectionee */
	PieceImage selectedPiece;
	/* decalage de l'echiquier dans le panel */
	private static int offset=5;
	/* taille des pieces */
	private int p_size=60;
	/* coordonnees du curseur de la souris */
	private int xcurs, ycurs;
	/* decalages du curseur de la souris */
	private int xi, yi;
	/* selection d'une piece */
	private boolean select=false;
	/* statut de depart */
	private boolean begin=true;
	//private int[][][] squares=new int[8][8][2];
	private JLabel positionLabel;
	private JLabel messageLabel;
	private JPanel capturePanel;
	/* cases d'un coup */
	private String square1, square2;
	/* checkmate */
	private boolean endOfGame;
	
	private boolean moveEntered;
	
	private int imax=1000;
	
	// boucle d'affichage
	private RenderingThread renderingThread = new RenderingThread();
	// buffer m�moire (2eme buffer)
	//private Graphics buffer;
	// image m�moire correspondante au buffer
	//private Image image; 

	public Board(int wType, int bType) {
		wht_pwns=new PieceImage[8];
		blk_pwns=new PieceImage[8];
		init();
		pieces=new PieceImage[]{wht_pwns[0], wht_pwns[1], wht_pwns[2], wht_pwns[3],
								wht_pwns[4], wht_pwns[5], wht_pwns[6], wht_pwns[7],
								blk_pwns[0], blk_pwns[1], blk_pwns[2], blk_pwns[3],
								blk_pwns[4], blk_pwns[5], blk_pwns[6], blk_pwns[7],
								wht_rk1, wht_rk2, blk_rk1, blk_rk2,
								wht_knt1, wht_knt2, blk_knt1, blk_knt2,
								wht_bsp1, wht_bsp2, blk_bsp1, blk_bsp2,
								wht_qn, blk_qn, wht_kng, blk_kng
								};
		
		/** initialisation des coordonnees de depart des images des pieces **/
		/* pions */
		for (int i=0; i<8; i++) {
			wht_pwns[i].setX(BoardUtil.xWhitePawn(i));
			wht_pwns[i].setY(BoardUtil.yWhitePawn());
			blk_pwns[i].setX(BoardUtil.xBlackPawn(i));
			blk_pwns[i].setY(BoardUtil.yBlackPawn());
		}
		/* tours */
		wht_rk1.setX(BoardUtil.xWhiteRook1());
		wht_rk1.setY(BoardUtil.yWhiteRook1());
		wht_rk2.setX(BoardUtil.xWhiteRook2());
		wht_rk2.setY(BoardUtil.yWhiteRook2());
		blk_rk1.setX(BoardUtil.xBlackRook1());
		blk_rk1.setY(BoardUtil.yBlackRook1());
		blk_rk2.setX(BoardUtil.xBlackRook2());
		blk_rk2.setY(BoardUtil.yBlackRook2());
		/* cavaliers */
		wht_knt1.setX(BoardUtil.xWhiteKnight1());
		wht_knt1.setY(BoardUtil.yWhiteKnight1());
		wht_knt2.setX(BoardUtil.xWhiteKnight2());
		wht_knt2.setY(BoardUtil.yWhiteKnight2());
		blk_knt1.setX(BoardUtil.xBlackKnight1());
		blk_knt1.setY(BoardUtil.yBlackKnight1());
		blk_knt2.setX(BoardUtil.xBlackKnight2());
		blk_knt2.setY(BoardUtil.yBlackKnight2());
		/* fous */
		wht_bsp1.setX(BoardUtil.xWhiteBishop1());
		wht_bsp1.setY(BoardUtil.yWhiteBishop1());
		wht_bsp2.setX(BoardUtil.xWhiteBishop2());
		wht_bsp2.setY(BoardUtil.yWhiteBishop2());
		blk_bsp1.setX(BoardUtil.xBlackBishop1());
		blk_bsp1.setY(BoardUtil.yBlackBishop1());
		blk_bsp2.setX(BoardUtil.xBlackBishop2());
		blk_bsp2.setY(BoardUtil.yBlackBishop2());
		/* reines */
		wht_qn.setX(BoardUtil.xQueen());
		wht_qn.setY(BoardUtil.yWhiteQueen());
		blk_qn.setX(BoardUtil.xQueen());
		blk_qn.setY(BoardUtil.yBlackQueen());
		/* rois */
		wht_kng.setX(BoardUtil.xKing());
		wht_kng.setY(BoardUtil.yWhiteKing());
		blk_kng.setX(BoardUtil.xKing());
		blk_kng.setY(BoardUtil.yBlackKing());
		
		/* initialisation des decalages */
		xi=0;
		yi=0;
		
		/* initialisation de la variable de fin de jeu */
		endOfGame=false;
		
		/* initialisation du jeu */
		game=new Game(wType, bType);
	}
	
	public void init() {
		
		try {
			board = ImageIO.read(getClass().getResource("/board01.png"));
			
			/** chargement des images des pieces **/
			/* pions */
			wht_pwn_img = ImageIO.read(getClass().getResource("/200px-Chess_plt45.svg.png"));
			blk_pwn_img = ImageIO.read(getClass().getResource("/200px-Chess_pdt45.svg.png"));
			for (int i=0; i<8; i++) {
				wht_pwns[i] = new PieceImage(wht_pwn_img, "pwn", Game.WHITE);
				blk_pwns[i] = new PieceImage(blk_pwn_img, "pwn", Game.BLACK);
			}
			/* tours */
			wht_rk_img = ImageIO.read(getClass().getResource("/200px-Chess_rlt45.svg.png"));
			wht_rk1 = new PieceImage(wht_rk_img, "rok", Game.WHITE);
			wht_rk2 = new PieceImage(wht_rk_img, "rok", Game.WHITE);
			blk_rk_img = ImageIO.read(getClass().getResource("/200px-Chess_rdt45.svg.png"));
			blk_rk1 = new PieceImage(blk_rk_img, "rok", Game.BLACK);
			blk_rk2 = new PieceImage(blk_rk_img, "rok", Game.BLACK);
			/* cavaliers */
			wht_knt_img = ImageIO.read(getClass().getResource("/200px-Chess_nlt45.svg.png"));
			wht_knt1 = new PieceImage(wht_knt_img, "knt", Game.WHITE);
			wht_knt2 = new PieceImage(wht_knt_img, "knt", Game.WHITE);
			blk_knt_img = ImageIO.read(getClass().getResource("/200px-Chess_ndt45.svg.png"));
			blk_knt1 = new PieceImage(blk_knt_img, "knt", Game.BLACK);
			blk_knt2 = new PieceImage(blk_knt_img, "knt", Game.BLACK);
			/* fous */
			wht_bsp_img = ImageIO.read(getClass().getResource("/200px-Chess_blt45.svg.png"));
			wht_bsp1 = new PieceImage(wht_bsp_img, "bsp", Game.WHITE);
			wht_bsp2 = new PieceImage(wht_bsp_img, "bsp", Game.WHITE);
			blk_bsp_img = ImageIO.read(getClass().getResource("/200px-Chess_bdt45.svg.png"));
			blk_bsp1 = new PieceImage(blk_bsp_img, "bsp", Game.BLACK);
			blk_bsp2 = new PieceImage(blk_bsp_img, "bsp", Game.BLACK);
			/* reines */
			wht_qn_img = ImageIO.read(getClass().getResource("/200px-Chess_qlt45.svg.png"));
			wht_qn = new PieceImage(wht_qn_img, "qun", Game.WHITE);
			blk_qn_img = ImageIO.read(getClass().getResource("/200px-Chess_qdt45.svg.png"));
			blk_qn = new PieceImage(blk_qn_img, "qun", Game.BLACK);
			/* rois */
			wht_kng_img = ImageIO.read(getClass().getResource("/200px-Chess_klt45.svg.png"));
			wht_kng = new PieceImage(wht_kng_img, "kng", Game.WHITE);
			blk_kng_img = ImageIO.read(getClass().getResource("/200px-Chess_kdt45.svg.png"));
			blk_kng = new PieceImage(blk_kng_img, "kng", Game.BLACK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addMouseListener(this);
		addMouseMotionListener(this);
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(490, 490));
	}
	
	public void update(Graphics g) {
		paintComponent(g);
	}
    
	/* en double buffering */
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);

    	/** affichage de l'echiquier **/
    	g.drawImage(board, offset, offset, this);
    	
    	/** affichage des images des pieces **/
    	for (int i=0; i<32; i++)
    		if (pieces[i]!=null) {
    			if (pieces[i].isSelected() || begin) {
    				g.drawImage(pieces[i].getImage(),
    							pieces[i].getX()+xi,
    							pieces[i].getY()+yi,
    							p_size, p_size,
    							this);
    				pieces[i].setX(pieces[i].getX()+xi);
    				pieces[i].setY(pieces[i].getY()+yi);
    			} else
    				g.drawImage(pieces[i].getImage(),
    							pieces[i].getX(),
    							pieces[i].getY(),
    							p_size, p_size,
    							this);
    		}
    	
    	/*** version double buffering (laisse des trainees) ***/
    	
    	/*if (buffer == null) {
    		image = createImage(getHeight(), getWidth());
    		buffer = image.getGraphics();
    	}*/
    	
    	/** affichage de l'echiquier **/
    	//buffer.drawImage(board, offset, offset, this);
    	
    	/** affichage des images des pieces **/
    	/*for (int i=0; i<32; i++)
    		if (pieces[i]!=null)
    			if (pieces[i].getIsSelected() || begin) {
    				buffer.drawImage(pieces[i].getImage(),
    							pieces[i].getX()+xi,
    							pieces[i].getY()+yi,
    							p_size, p_size,
    							this);
    				pieces[i].setX(pieces[i].getX()+xi);
    				pieces[i].setY(pieces[i].getY()+yi);
    			} else
    				buffer.drawImage(pieces[i].getImage(),
    							pieces[i].getX(),
    							pieces[i].getY(),
    							p_size, p_size,
    							this);
    	g.drawImage(image, 0, 0, this);*/
    }
    
    public void play() throws UndefinedPlayerException {
    	//System.out.println("play");
    	//System.out.println(imax);
    	//System.out.println(ArtificialBlack.PMAX);
    	
    	State state = game.getState();
    	Player player = state.getPlayer();
    	Piece[][] board = state.getBoard();
    	int[][] move = null;
    	String sq2="";
    	Piece piece;
    	
		if (player.getType() == Player.HUMAN) return;
		
		//System.out.println("Player.ARTIFICIAL");
		try {
			move = player.enterMove(game, state.isCheck());
		} catch (UndefinedPlayerException upe) {
			upe.printStackTrace(System.err);
		}
		
		if (move == null) return;
		
		//System.out.println("play: ("+move[1][0]+" "+move[1][1]+")");
		
		moveEntered = true;
		square1 = ""+((char) ('a'+move[0][0]))+(Math.abs(move[0][1]-8));
		//System.out.println(square1);
		sq2 = ""+((char) ('a'+move[1][0]))+(Math.abs(move[1][1]-8));
		System.out.println("CPU tries "+sq2);
		
		//System.out.println(state);
		
		for (int i=0; i<32; i++) {
			if (pieces[i]!=null &&
					pieces[i].getX() == GameUtil.convertX(square1, p_size, offset) &&
					pieces[i].getY() == GameUtil.convertY(square1, p_size, offset)) {
				pieces[i].setSelected(true);
				selectedPiece = pieces[i];
				/* promotion */
				if (move.length == 3) {
					//System.out.println("play: promotion");
					for (int j=0; j<16; j++) {
						if (state.getWhite()[j]!=null && state.getWhite()[j].isPromoted())
							state.getWhite()[j].setPromoted(false);
						if (state.getBlack()[j]!=null && state.getBlack()[j].isPromoted())
							state.getBlack()[j].setPromoted(false);
					}
					piece = board[move[0][1]][move[0][0]];
					board[move[0][1]][move[0][0]] = null;
					switch (move[2][0]) {
						case 0 :
							board[move[0][1]][move[0][0]] = new Queen(player.getColor());
							break;
						case 1 :
							board[move[0][1]][move[0][0]] = new Rook(player.getColor());
							break;
						case 2 :
							board[move[0][1]][move[0][0]] = new Bishop(player.getColor());
							break;
						case 3 :
							board[move[0][1]][move[0][0]] = new Knight(player.getColor());
							break;
						default :
							board[move[0][1]][move[0][0]] = new Queen(player.getColor());
							break;
					}
					
					board[move[0][1]][move[0][0]].setPromoted(true);
					
					if (player.getColor() == Player.WHITE) {
						for (int j=0; j<16; j++) {
							if (state.getWhite()[j] != null &&
									state.getWhite()[j].hashCode() == piece.hashCode()) {
								state.getWhite()[j] = board[move[0][1]][move[0][0]];
								break;
							}
						}
					} else if (player.getColor() == Player.BLACK) {
						for (int j=0; j<16; j++) {
							if (state.getBlack()[j] != null &&
									state.getBlack()[j].hashCode() == piece.hashCode()) {
								state.getBlack()[j] = board[move[0][1]][move[0][0]];
								break;
							}
						}
					} else throw new UndefinedPlayerException();
				}
				//System.out.println("selection");
				break;
			}
			if (i+1 == 32) return;
		}
		
		//System.out.println(game.getState());
		
		playMove(GameUtil.convertX(sq2, p_size, offset), GameUtil.convertY(sq2, p_size, offset));
	}
    
    public void playMove(int x, int y) throws UndefinedPlayerException {
    	//System.out.println("playMove");
    	//System.out.println(x+" "+y);
    	if (game.getState().getPlayer().getType() == Player.ARTIFICIAL && !moveEntered) {
    		//System.out.println("playMove");
    		return;
    	} else {
    		//System.out.println("playMove");
    		moveEntered=false;
    	}
    	
		for (int i=0; i<32; i++) {
			if (pieces[i]!=null && pieces[i].isSelected()) {
				break;
			}
			if (i+1 == 32) return;
		}
		
		State state=game.getState();
		State nextState = null;
		xcurs=x;
		ycurs=y;
		int y1=Math.abs(Integer.parseInt(""+square1.charAt(1))-8);
		int x1=square1.charAt(0)-'a';
		int y2, x2;
		Player currentPlayer=game.getState().getPlayer();
		Piece piece=(game.getState().getBoard())[y1][x1];
		
		//System.out.println("playMove1 "+state.getBoard()[0][0].isCastlingPoss());
		
		/*for (int i=0; i<8; i++)
    		for (int j=0; j<8; j++) {
    			if (state.getBoard()[i][j] != null) {
    				int k;
    				for (k=0; k<32; k++)
    					if (pieces[k] != null &&
    						j == ((pieces[k].getX()-offset)/p_size) &&
    						i == ((pieces[k].getY()-offset)/p_size))
    						break;
    				if (k == 32) {
    					System.out.println("PIECE NON VISIBLE : "+j+i);
    					System.out.println(state);
    					imax = 0;
    					return;
    				}
    			}
    		}*/

		if (piece == null)
			return;
		
		try {
			nextState = new State(state);
		} catch (UndefinedPieceException upe) {
			upe.printStackTrace(System.err);
		}
		
		if (!contains(xcurs, ycurs) && currentPlayer.getType() != Player.ARTIFICIAL) {
			selectedPiece.setX(GameUtil.convertX(square1, p_size, offset));
			selectedPiece.setY(GameUtil.convertY(square1, p_size, offset));
			selectedPiece.setSelected(false);
			repaint();
			return;
		}

		select=false;
		square2=GameUtil.square(xcurs-offset, ycurs-offset);
		y2=Math.abs(Integer.parseInt(""+square2.charAt(1))-8);
		x2=square2.charAt(0)-'a';
			
		try {
			if (game.verifyAndPlayMove(nextState, x1, y1, x2, y2, false) && !endOfGame) {
				/*System.out.print("mouseReleased (verify: true) ");
				System.out.println(state.getPlayer().getColor() == Player.WHITE ? "white" : "black");*/
				game.setState(nextState);
				/*System.out.print("mouseReleased ");
				System.out.println(state.getPlayer().getColor() == Player.WHITE ? "white" : "black");*/
				//System.out.println("playMove2 "+nextState.getBoard()[0][0].isCastlingPoss());
				if (!GameUtil.isEndOfGame(game, messageLabel)) {
					
					//System.out.println("playMove3 "+game.getState().getBoard()[0][0].isCastlingPoss());
					//System.out.println(game.getState());
					
					/** placement de la piece **/
					selectedPiece.setX(GameUtil.convertX(square2, p_size, offset));
					selectedPiece.setY(GameUtil.convertY(square2, p_size, offset));
					selectedPiece.setSelected(false);
					
					int n = 0;

					for (int i=0; i<8; i++) {
						for (int j=0; j<8; j++) {
							Piece p = game.getState().getBoard()[j][i];
							if (p != null) {
								
								/*if (p.getNom().equals("rok") &&
									p.getColor() == Game.WHITE)
									System.out.println("rok");*/
								
								for (int k=n; k<32; k++) {
									if (pieces[k] != null &&
										pieces[k].isSelected() == false) {
										
										/*if (pieces[k].getNom().equals("rok") &&
												pieces[k].getColor() == Game.WHITE)
												System.out.println("rok"+k);*/
										
										changeImage(pieces[k], p);
										
										/*if (pieces[k].getNom().equals("rok") &&
												pieces[k].getColor() == Game.WHITE)
												System.out.println("rok"+k);*/
										
										//System.out.println(k);
										
										pieces[k].setX(GameUtil.convertX(
										""+(char)(i+'a')+Math.abs(j-8), p_size, offset));
										pieces[k].setY(GameUtil.convertY(
										""+(char)(i+'a')+Math.abs(j-8), p_size, offset));
										pieces[k].setSelected(true);
										//System.out.println(""+(char)(i+'a')+Math.abs(j-8));
										n = k+1;
										break;
									}
								}
							}
						}
					}
					for (int k=n; k<32; k++) {
						if (pieces[k] != null && pieces[k].isSelected() == false) {
							//System.out.println("null");
							pieces[k] = null;
						}
					}
					
					game.getState().getReview().add(new String[]{square1, square2});
					
					/*if (capturePanel!=null && capturePanel.isEnabled())
						capturePanel.setText(state.capturesToString());*/
					//System.out.println(state.toString());
					
				} else {
					//System.out.println("end");
					if (messageLabel.getText().equals("White will be check!") ||
						messageLabel.getText().equals("Black will be check!")) {
						/*System.out.print("mouseReleased (verify: true) ");
						System.out.println(state.getPlayer().getColor() == Player.WHITE ? "white" : "black");*/
						game.setState(state);
						/*System.out.print("mouseReleased ");
						System.out.println(state.getPlayer().getColor() == Player.WHITE ? "white" : "black");*/
						for(int i=0; i<32; i++)
							if (pieces[i]!=null && pieces[i].isSelected()) {
								pieces[i].setSelected(false);
								pieces[i].setX(GameUtil.convertX(square1, p_size, offset));
								pieces[i].setY(GameUtil.convertY(square1, p_size, offset));
								break;
							}
					} else if (messageLabel.getText().equals("Checkmate! White wins") ||
							messageLabel.getText().equals("Checkmate! Black wins") ||
							messageLabel.getText().equals("White is stalemated!") ||
							messageLabel.getText().equals("Black is stalemated!")) {
						endOfGame=true;
						imax=0;
						
						/** capture et/ou placement du dernier coup **/
						selectedPiece.setX(GameUtil.convertX(square2, p_size, offset));
						selectedPiece.setY(GameUtil.convertY(square2, p_size, offset));
						selectedPiece.setSelected(false);
						
						int n = 0;

						for (int i=0; i<8; i++) {
							for (int j=0; j<8; j++) {
								Piece p = game.getState().getBoard()[j][i];
								if (p != null) {
									
									for (int k=n; k<32; k++) {
										if (pieces[k] != null &&
											pieces[k].isSelected() == false) {
											
											changeImage(pieces[k], p);
											
											pieces[k].setX(GameUtil.convertX(
											""+(char)(i+'a')+Math.abs(j-8), p_size, offset));
											pieces[k].setY(GameUtil.convertY(
											""+(char)(i+'a')+Math.abs(j-8), p_size, offset));
											pieces[k].setSelected(true);
											n = k+1;
											break;
										}
									}
								}
							}
						}
						for (int k=n; k<32; k++) {
							if (pieces[k] != null && pieces[k].isSelected() == false) {
								//System.out.println("null");
								pieces[k] = null;
							}
						}
						
						game.getState().getReview().add(new String[]{square1, square2});
					}
				}
				repaint();
			} else {
				/*System.out.print("mouseReleased  (verify: false) ");
				System.out.println(state.getPlayer().getColor() == Player.WHITE ? "white" : "black");*/
				if (!endOfGame)
					game.setState(nextState);
				/*System.out.print("mouseReleased ");
				System.out.println(state.getPlayer().getColor() == Player.WHITE ? "white" : "black");*/
				
				String player=piece.getColor()==Game.WHITE ? "white" : "black";
				
				if (piece.getColor() == currentPlayer.getColor() && !endOfGame)
					messageLabel.setText("Move is not valid");
				else if (!endOfGame)
					messageLabel.setText("This is not "+player+"'s turn");
				
				selectedPiece.setSelected(false);
				selectedPiece.setX(GameUtil.convertX(square1, p_size, offset));
				selectedPiece.setY(GameUtil.convertY(square1, p_size, offset));
				
				repaint();
			}
		} catch(UndefinedPlayerException upe) {
			upe.printStackTrace();
		}
		
		for (int i=0; i<32; i++) {
			if (pieces[i]!=null)
				pieces[i].setSelected(false);
		}
		
		//System.out.println(square1+square2);
		
		//System.out.println(state.toString());
		//System.out.println(game.getBlack().getType() == Player.ARTIFICIAL ? "artificial" : "human");
		//System.out.println(nextState.getPlayer().getType() == Player.ARTIFICIAL ? "artificial" : "human");
		
		Player p;
		
		if (nextState.getPlayer().getColor() == Player.WHITE)
			p = game.getBlack();
		else
			p = game.getWhite();
		
		if (nextState.getPlayer().getType() == Player.ARTIFICIAL &&
			p.getType() == Player.HUMAN &&
			!endOfGame) {
			//System.out.println("play");
			play();
		}
    }
    
    public void changeImage(PieceImage pimage, Piece piece) {
    	String nom = piece.getNom();
    	int color = piece.getColor();
    	
    	/*if (nom.equals("rok") &&
    		color == Game.WHITE)
			System.out.println("rok");*/
    	
    	if (pimage.getNom().equals(nom) &&
    		pimage.getColor() == color) {
    		//System.out.println("ret"+pimage.getNom()+pimage.getColor());
    		return;
    	}
    	
    	//System.out.println("deb"+pimage.getNom()+pimage.getColor());
    	
    	pimage.setNom(nom);
    	pimage.setColor(color);
    	
    	if (color == Game.WHITE) {
			if (nom.equals("rok")) {
				pimage.setImage(wht_rk_img);
			} else if (nom.equals("knt")) {
				pimage.setImage(wht_knt_img);
			} else if (nom.equals("bsp")) {
				pimage.setImage(wht_bsp_img);
			} else if (nom.equals("qun")) {
				pimage.setImage(wht_qn_img);
			} else if (nom.equals("kng")) {
				pimage.setImage(wht_kng_img);
			} else if (nom.equals("pwn")) {
				pimage.setImage(wht_pwn_img);
			}
		} else if (color == Game.BLACK) {
			if (nom.equals("rok")) {
				pimage.setImage(blk_rk_img);
			} else if (nom.equals("knt")) {
				pimage.setImage(blk_knt_img);
			} else if (nom.equals("bsp")) {
				pimage.setImage(blk_bsp_img);
			} else if (nom.equals("qun")) {
				pimage.setImage(blk_qn_img);
			} else if (nom.equals("kng")) {
				pimage.setImage(blk_kng_img);
			} else if (nom.equals("pwn")) {
				pimage.setImage(blk_pwn_img);
			}
		}
    	
    	//System.out.println("fin"+pimage.getNom()+pimage.getColor());
    }
	
	/* Methodes interface MouseListener */

	public void mouseReleased(MouseEvent e) {
		if (game.getState().getPlayer().getType() == Game.HUMAN) {
			try {playMove(e.getX(), e.getY());}
			catch (UndefinedPlayerException e1) {e1.printStackTrace();}
		}
	}
	public void mousePressed(MouseEvent e) {
		if (game.getState().getPlayer().getType() == Player.ARTIFICIAL) return;
		begin=false;
		xcurs=e.getX();
		ycurs=e.getY();
		square1=GameUtil.square(xcurs-offset, ycurs-offset);
		for (int i=0; i<32; i++) {
			if (pieces[i]!=null)
				if (xcurs > pieces[i].getX() && xcurs < pieces[i].getX()+p_size
						&& ycurs > pieces[i].getY() && ycurs < pieces[i].getY()+p_size) {
					select=true;
					pieces[i].setSelected(true);
					selectedPiece = pieces[i];
					if (positionLabel!=null)
						positionLabel.setText(GameUtil.square(xcurs-offset, ycurs-offset));
					break;
				}
		}
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	
	/* Methodes interface MouseMotionListener */
	
	public void mouseDragged(MouseEvent e) {
		if (contains(e.getX(), e.getY()) && select) {
			xi=e.getX()-xcurs;
			yi=e.getY()-ycurs;
			xcurs=e.getX();
			ycurs=e.getY();
			if (positionLabel!=null && positionLabel.isEnabled())
				positionLabel.setText(GameUtil.square(xcurs-offset, ycurs-offset));
			repaint();
		}
	}
	public void mouseMoved(MouseEvent e) {
		xcurs=e.getX();
		ycurs=e.getY();
		int x=xcurs-offset;
		int y=ycurs-offset;
		String pos=GameUtil.square(x, y);
		
		if (positionLabel!=null
			&& xcurs<480+offset && ycurs<480+offset
			&& xcurs>=offset && ycurs>=offset)
			positionLabel.setText(pos);
		else if (positionLabel!=null)
			positionLabel.setText("");
	}

	/* getters */
	public String getSquare1() {return square1;}
	public String getSquare2() {return square2;}
	public Game getGame() {return game;}
    public static int getOffset() {return offset;}
	public static long getSerialversionuid() {return serialVersionUID;}
	public Image getBoard() {return board;}
	public PieceImage[] getWht_pwns() {return wht_pwns;}
	public PieceImage getWht_rk1() {return wht_rk1;}
	public PieceImage getWht_rk2() {return wht_rk2;}
	public PieceImage getWht_knt1() {return wht_knt1;}
	public PieceImage getWht_knt2() {return wht_knt2;}
	public PieceImage getWht_bsp1() {return wht_bsp1;}
	public PieceImage getWht_bsp2() {return wht_bsp2;}
	public PieceImage getWht_kng() {return wht_kng;}
	public PieceImage getWht_qn() {return wht_qn;}
	public PieceImage[] getBlk_pwns() {return blk_pwns;}
	public PieceImage getBlk_rk1() {return blk_rk1;}
	public PieceImage getBlk_rk2() {return blk_rk2;}
	public PieceImage getBlk_knt1() {return blk_knt1;}
	public PieceImage getBlk_knt2() {return blk_knt2;}
	public PieceImage getBlk_bsp1() {return blk_bsp1;}
	public PieceImage getBlk_bsp2() {return blk_bsp2;}
	public PieceImage getBlk_kng() {return blk_kng;}
	public PieceImage getBlk_qn() {return blk_qn;}
	public PieceImage[] getPieces() {return pieces;}
	public int getP_size() {return p_size;}
	public int getXcurs() {return xcurs;}
	public int getYcurs() {return ycurs;}
	public int getXi() {return xi;}
	public int getYi() {return yi;}
	public boolean isSelect() {return select;}
	public boolean isBegin() {return begin;}
	public JLabel getPositionLabel() {return positionLabel;}
	public JLabel getMessageLabel() {return messageLabel;}
	public JPanel getCapturePanel() {return capturePanel;}
	public boolean isEndOfGame() {return endOfGame;}
	public int getImax() {return imax;}
	public RenderingThread getRenderingThread() {return renderingThread;}
	
	/* setters */
	public void setSquare1(String square1) {this.square1 = square1;}
	public void setSquare2(String square2) {this.square2 = square2;}
	public void setGame(Game game) {this.game = game;}
	public void setBoard(Image board) {this.board = board;}
	public void setWht_pwns(PieceImage[] whtPwns) {wht_pwns = whtPwns;}
	public void setWht_rk1(PieceImage whtRk1) {wht_rk1 = whtRk1;}
	public void setWht_rk2(PieceImage whtRk2) {wht_rk2 = whtRk2;}
	public void setWht_knt1(PieceImage whtKnt1) {wht_knt1 = whtKnt1;}
	public void setWht_knt2(PieceImage whtKnt2) {wht_knt2 = whtKnt2;}
	public void setWht_bsp1(PieceImage whtBsp1) {wht_bsp1 = whtBsp1;}
	public void setWht_bsp2(PieceImage whtBsp2) {wht_bsp2 = whtBsp2;}
	public void setWht_kng(PieceImage whtKng) {wht_kng = whtKng;}
	public void setWht_qn(PieceImage whtQn) {wht_qn = whtQn;}
	public void setBlk_pwns(PieceImage[] blkPwns) {blk_pwns = blkPwns;}
	public void setBlk_rk1(PieceImage blkRk1) {blk_rk1 = blkRk1;}
	public void setBlk_rk2(PieceImage blkRk2) {blk_rk2 = blkRk2;}
	public void setBlk_knt1(PieceImage blkKnt1) {blk_knt1 = blkKnt1;}
	public void setBlk_knt2(PieceImage blkKnt2) {blk_knt2 = blkKnt2;}
	public void setBlk_bsp1(PieceImage blkBsp1) {blk_bsp1 = blkBsp1;}
	public void setBlk_bsp2(PieceImage blkBsp2) {blk_bsp2 = blkBsp2;}
	public void setBlk_kng(PieceImage blkKng) {blk_kng = blkKng;}
	public void setBlk_qn(PieceImage blkQn) {blk_qn = blkQn;}
	public void setPieces(PieceImage[] pieces) {this.pieces = pieces;}
	public static void setOffset(int offset) {Board.offset = offset;}
	public void setP_size(int pSize) {p_size = pSize;}
	public void setXcurs(int xcurs) {this.xcurs = xcurs;}
	public void setYcurs(int ycurs) {this.ycurs = ycurs;}
	public void setXi(int xi) {this.xi = xi;}
	public void setYi(int yi) {this.yi = yi;}
	public void setSelect(boolean select) {this.select = select;}
	public void setBegin(boolean begin) {this.begin = begin;}
	public void setPositionLabel(JLabel positionLabel) {this.positionLabel = positionLabel;}
	public void setMessageLabel(JLabel messageLabel) {this.messageLabel = messageLabel;}
	public void setCapturePanel(JPanel capturePanel) {this.capturePanel = capturePanel;}
	public void setEndOfGame(boolean endOfGame) {this.endOfGame = endOfGame;}
	public void setImax(int imax) {this.imax = imax;}
	public void setRenderingThread(RenderingThread renderingThread) {this.renderingThread = renderingThread;}
	
	class RenderingThread extends Thread {
		public void run(){
			if (game.getState().getPlayer().getType() == Player.HUMAN) return;
			for (int n=0; n<imax; n++) {
				System.out.println(n);
				try {
					play();
					//sleep(50);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
