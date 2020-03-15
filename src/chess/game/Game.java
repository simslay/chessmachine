package chess.game;

import javax.swing.JOptionPane;

import chess.game.pieces.*;
import chess.game.players.Player;
import chess.game.players.artificial.*;
import chess.game.players.human.*;

public class Game implements Cloneable {
	private State state;
	private int nbMoves=0;
	private Player white, black;
	/* nombre maximum d'iterations */
	public final static int imax=200*1000;
	/* constantes */
	public final static int HUMAN=0;
	public final static int ARTIFICIAL=1;
	public final static int WHITE=0;
	public final static int BLACK=1;

	public Game (int wType, int bType) {
		state=new State();
		white=wType == HUMAN ? new HumanWhite() : new ArtificialWhite();
		black=bType == HUMAN ? new HumanBlack() : new ArtificialBlack();
		
		state.setPlayer(white);
	}
	
	public boolean verifyAndPlayMove(State s, int x1, int y1, int x2, int y2, boolean test) throws UndefinedPlayerException {
		Piece[][] board = s.getBoard();
		Piece[] white = s.getWhite();
		Piece[] black = s.getBlack();
		Player currentPlayer = s.getPlayer();
		Piece piece;
		
		piece = board[y1][x1];
		
		//System.out.println("verify "+board[0][0].isCastlingPoss());
		//System.out.println("verify "+state.getBoard()[0][0].isCastlingPoss());
		
		/*System.out.println(x1+" "+y1+" "+x2+" "+y2);
		System.out.println(piece);*/
		
		//System.out.println(currentPlayer.getColor() == Player.WHITE ? "white" : "black");
		//System.out.println(piece == null ? "null" : "");
		//System.out.println(""+x1+y1+" "+x2+y2);
		//System.out.println(s.isCheck());
		//System.out.println(piece.isValidMove(x1, y1, x2, y2, board, currentPlayer, s.isCheck()));
		
		//if (nbMoves++ < imax) {
		if (piece != null && (piece.isValidMove(x1, y1, x2, y2, board, currentPlayer, s.isCheck()) ||
			(s.getPlayer().getType() == Player.ARTIFICIAL && piece.isPromoted()))) {
			
			//System.out.println("verify");
			
			/* enlevement de la piece */
			board[y1][x1] = null;
			
			/* ajout de la piece capturee dans les captures */
			if (board[y2][x2] == null) {
				if (currentPlayer.getColor() == Player.WHITE) {
					if ((x2 == x1+1 || x2 == x1-1) && y1 == 3 && y2 == 2) {
						if (board[y2+1][x2] != null && board[y2+1][x2].isEnPassantRisk())
							for (int i=0; i<16; i++) {
								if (black[i]!=null && black[i].hashCode() == board[y2+1][x2].hashCode()) {
									black[i] = null;
									s.addCapture(board[y2+1][x2]);
									board[y2+1][x2] = null;
									break;
								}
							}
					} else { /* annulation de l'eventuel pion enPassantRisk=true */
						for (int i=0; i<16; i++) {
							if (black[i]!=null && black[i].isEnPassantRisk()) {
								black[i].setEnPassantRisk(false);
								break;
							}
						}
					}
				} else if (currentPlayer.getColor() == Player.BLACK) {
					if ((x2 == x1+1 || x2 == x1-1) && y1 == 4 && y2 == 5) {
						if (board[y2-1][x2] != null && board[y2-1][x2].isEnPassantRisk())
							for (int i=0; i<16; i++) {
								if (white[i]!=null && white[i].hashCode() == board[y2-1][x2].hashCode()) {
									white[i] = null;
									s.addCapture(board[y2-1][x2]);
									board[y2-1][x2] = null;
									break;
								}
							}
					} else { /* annulation de l'eventuel pion enPassantRisk=true */
						for (int i=0; i<16; i++) {
							if (white[i]!=null && white[i].isEnPassantRisk()) {
								white[i].setEnPassantRisk(false);
								break;
							}
						}
					}
				} else throw new UndefinedPlayerException();
			} else if (board[y2][x2]!=null) {
				s.addCapture(board[y2][x2]);
				if (currentPlayer.getColor() == Player.WHITE)
					for (int i=0; i<16; i++) {
						if (black[i]!=null && black[i].hashCode() == board[y2][x2].hashCode()) {
							black[i] = null;
							break;
						}
					}
				else if (currentPlayer.getColor() == Player.BLACK)
					for (int i=0; i<16; i++) {
						if (white[i]!=null && white[i].hashCode() == board[y2][x2].hashCode()) {
							white[i] = null;
							break;
						}
					}
				else throw new UndefinedPlayerException();
			}
						
			/* placement de la nouvelle piece */
				/* promotion */
			if (currentPlayer.getType() != Player.ARTIFICIAL) {
				for (int i=0; i<16; i++) {
					if (white[i]!=null && white[i].isPromoted())
						white[i].setPromoted(false);
					if (black[i]!=null && black[i].isPromoted())
						black[i].setPromoted(false);
				}
			}
			if (!test && piece.getNom().equals("pwn") &&
				currentPlayer.getType() != Player.ARTIFICIAL &&
				piece.getColor() == Game.WHITE &&
				y2 == 0) {
						
				Object[] possibleValues = { "Queen", "Rook", "Bishop", "Knight" };
				Object selectedValue = JOptionPane.showInputDialog(null,
				"Choose one piece for promotion", "Promotion",
				JOptionPane.INFORMATION_MESSAGE, null,
				possibleValues, possibleValues[0]);
				
				if (selectedValue == null) return false;
				
				if (selectedValue.equals("Queen"))
					board[y2][x2] = new Queen(Game.WHITE);
				else if (selectedValue.equals("Rook"))
					board[y2][x2] = new Rook(Game.WHITE);
				else if (selectedValue.equals("Bishop"))
					board[y2][x2] = new Bishop(Game.WHITE);
				else if (selectedValue.equals("Knight"))
					board[y2][x2] = new Knight(Game.WHITE);
				
				board[y2][x2].setX(x2);
				board[y2][x2].setY(y2);
				//System.out.println("Game: setPromoted(true)");
				board[y2][x2].setPromoted(true);
				for (int i=0; i<16; i++) {
					if (white[i] != null && white[i].hashCode() == piece.hashCode()) {
						white[i] = board[y2][x2];
						break;
					}
				}
				
			} else if (!test && piece.getNom().equals("pwn") &&
					currentPlayer.getType() != Player.ARTIFICIAL &&
					piece.getColor() == Game.BLACK &&
					y2 == 7) {
				
				Object[] possibleValues = { "Queen", "Rook", "Bishop", "Knight" };
				Object selectedValue = JOptionPane.showInputDialog(null,
				"Choose one piece for promotion", "Promotion",
				JOptionPane.INFORMATION_MESSAGE, null,
				possibleValues, possibleValues[0]);
				
				if (selectedValue == null) return false;
				
				if (selectedValue.equals("Queen"))
					board[y2][x2] = new Queen(Game.BLACK);
				else if (selectedValue.equals("Rook"))
					board[y2][x2] = new Rook(Game.BLACK);
				else if (selectedValue.equals("Bishop"))
					board[y2][x2] = new Bishop(Game.BLACK);
				else if (selectedValue.equals("Knight"))
					board[y2][x2] = new Knight(Game.BLACK);
				
				board[y2][x2].setX(x2);
				board[y2][x2].setY(y2);
				//System.out.println("Game: setPromoted(true)");
				board[y2][x2].setPromoted(true);
				for (int i=0; i<16; i++) {
					if (black[i] != null && black[i].hashCode() == piece.hashCode()) {
						black[i] = board[y2][x2];
						break;
					}
				}
				
			} else {
				board[y2][x2] = piece;
				piece.setX(x2);
				piece.setY(y2);
				/* castling */
				if (!s.isCheck() && x1 == 4 && (x2 == 2 || x2 == 6) &&
					((y1 == 0 && y2 == 0) || (y1 == 7 && y2 == 7))) {
					if (board[y2][x2].getNom().equals("kng")) {
						if (x2 == 2 && board[y2][0] != null &&
								board[y2][0].getNom().equals("rok") &&
								board[y2][0].isCastlingPoss()) {
						
							board[y2][3] = board[y2][0];
							board[y2][0] = null;
							board[y2][3].setX(3);
							board[y2][3].setY(y2);
							board[y2][3].setCastlingPoss(false);
								
						} else if (x2 == 6 && board[y2][7] != null &&
								board[y2][7].getNom().equals("rok") &&
								board[y2][7].isCastlingPoss()) {
							
								board[y2][5] = board[y2][7];
								board[y2][7] = null;
								board[y2][5].setX(5);
								board[y2][5].setY(y2);
								board[y2][5].setCastlingPoss(false);
									
						}
					}
				}
			}
				
			/** changement du joueur **/
			s.setPlayer(GameUtil.changePlayer(currentPlayer, this));
			/** check **/
			if (s.isCheck()) s.setCheck(false);
			/*System.out.print("verify ");
			System.out.println(s.getPlayer().getColor() == Player.WHITE ? "white" : "black");*/
			//System.out.println(s);
			return true;
		} else
			return false;
		/*} else
			return false;*/
	}
	
	public Object clone() {
	    Game game = null;
	    
	    try {
	    	game = (Game) super.clone();
	    } catch(CloneNotSupportedException cnse) {
	      	cnse.printStackTrace(System.err);
	    }
	    
	    return game;
	}

	/* getters */
	public State getState() {return state;}
	public int getNbMoves() {return nbMoves;}
	public Player getWhite() {return white;}
	public Player getBlack() {return black;}
	/* setters */
	public void setState(State state) {this.state = state;}
	public void setNbMoves(int nbMoves) {this.nbMoves = nbMoves;}
	public void setWhite(Player white) {this.white = white;}
	public void setBlack(Player black) {this.black = black;}
}
