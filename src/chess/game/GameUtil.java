package chess.game;

import java.util.Vector;

import javax.swing.JLabel;

import chess.game.pieces.*;
import chess.game.players.Player;

//import chess.game.UndefinedPlayerException;
//import chess.game.players.*;

public class GameUtil {
	public static int nbCheck=0;
	
	public static int convertX(String square, int p_size, int offset) {
		return (square.charAt(0)-'a')*p_size+offset;
	}
	
	public static int convertY(String square, int p_size, int offset) {
		return Math.abs(Integer.parseInt(""+square.charAt(1))-8)*p_size+offset;
	}
	
	public static String square(int px, int py) {
		char x=(char)('a'+(px/60));
		int y=(8-(py/60));
		return ""+x+y;
	}
	
	public static boolean isEndOfGame(Game game, JLabel message) {
		message.setText("");
		game.getState().setCheck(false);
		return	whiteIsCheckmate(game, message) ||
				blackIsCheckmate(game, message) ||
				whiteIsStalemate(game, message) ||
				blackIsStalemate(game, message);
	}
	
	public static boolean whiteIsStalemate(Game game, JLabel message) {
		State state = game.getState();
		State stateCopy = null;
		Player player = state.getPlayer();
		Piece[] white = state.getWhite();
		Piece piece;
		Vector<int[][]> validMoves;
		int[][] move;
		
		if (player.getColor() == Player.BLACK) return false;
		
		for (int i=0; i<16; i++) {
			piece=white[i];
			
			if (piece != null) {
				validMoves = validMoves(game, piece, true);
				for (int j=0; j<validMoves.size(); j++) {
					move = validMoves.get(j);
					try {
						stateCopy = new State(state);
					} catch (UndefinedPieceException e) {
						e.printStackTrace(System.err);
					}
					
					if (stateCopy == null) return false;
					
					try {
						game.verifyAndPlayMove(stateCopy,
												move[0][0], move[0][1], move[1][0], move [1][1], true);
					} catch (UndefinedPlayerException e) {
						e.printStackTrace();
					}
					if (!whiteIsCheck(game, stateCopy,
							stateCopy.getWKing().getX(), stateCopy.getWKing().getY())) {
						//System.out.println("whiteIsStalemate "+state.getBoard()[0][0].isCastlingPoss());
						return false;
					}
				}
			}
		}
		
		message.setText("White is stalemated!");
		return true;
	}
	
	public static boolean blackIsStalemate(Game game, JLabel message) {
		State state = game.getState();
		State stateCopy = null;
		Player player = state.getPlayer();
		Piece[] black = state.getBlack();
		Piece piece;
		Vector<int[][]> validMoves;
		int[][] move;
		
		if (player.getColor() == Player.WHITE) return false;
		
		for (int i=0; i<16; i++) {
			piece=black[i];
			
			if (piece != null) {
				//System.out.println("blackIsStalemate1 "+state.getBoard()[0][0].isCastlingPoss());
				
				validMoves = validMoves(game, piece, true);
				for (int j=0; j<validMoves.size(); j++) {
					move = validMoves.get(j);
					try {
						stateCopy = new State(state);
					} catch (UndefinedPieceException e) {
						e.printStackTrace(System.err);
					}
					
					if (stateCopy == null) return false;
					
					try {
						game.verifyAndPlayMove(stateCopy,
												move[0][0], move[0][1], move[1][0], move [1][1], true);
					} catch (UndefinedPlayerException e) {
						e.printStackTrace();
					}
					if (!blackIsCheck(game, stateCopy,
							stateCopy.getBKing().getX(), stateCopy.getBKing().getY())) {
						//System.out.println("blackIsStalemate2 "+state.getBoard()[0][0].isCastlingPoss());
						return false;
					}
				}
			}
		}
		
		message.setText("Black is stalemated!");
		return true;
	}
	
	public static boolean whiteIsCheck(Game game, State state, int x, int y) {
		Piece[][] board=state.getBoard();
		Piece[] black=state.getBlack();
		Piece piece;
		
		/*System.out.print("whiteIsCheck ");
		System.out.println(state.getPlayer().getColor() == Player.WHITE ? "white" : "black");*/
		
		for (int i=0; i<16; i++) {
			piece=black[i];
			
			if (piece != null /*&& state.getPlayer().getType() == Player.HUMAN*/) {
				/*if (piece.getNom().equals("qun")) {
					System.out.print("qun "+(piece.getColor() == Game.WHITE ? "white " : "black "));
					System.out.println(piece.getX()+" "+piece.getY()+" "+x+" "+y);
				}*/
				if (piece.isValidMove(piece.getX(), piece.getY(), x, y, board, game.getBlack(), false)) {
					//System.out.println("whiteIsCheck: true");
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean blackIsCheck(Game game, State state, int x, int y) {
		Piece[][] board=state.getBoard();
		Piece[] white=state.getWhite();
		Piece piece;
		
		/*System.out.print("whiteIsCheck ");
		System.out.println(state.getPlayer().getColor() == Player.WHITE ? "white" : "black");*/
		
		for (int i=0; i<16; i++) {
			piece=white[i];
			
			if (piece != null /*&& state.getPlayer().getType() == Player.HUMAN*/) {
				/*if (piece.getNom().equals("qun")) {
					System.out.print("qun "+(piece.getColor() == Game.WHITE ? "white " : "black "));
					System.out.println(piece.getX()+" "+piece.getY()+" "+x+" "+y);
				}*/
				if (piece.isValidMove(piece.getX(), piece.getY(), x, y, board, game.getWhite(), false)) {
					//System.out.println("whiteIsCheck: true");
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean whiteIsCheckmate(Game game, JLabel message) {
		State state = game.getState();
		State stateCopy = null;
		Piece wKing = state.getWKing();
		int x = wKing.getX();
		int y = wKing.getY();
		Piece[] white = state.getWhite();
		Vector<int[][]> validMoves;
		int[][] move;
		
		try {
			stateCopy = new State(state);
		} catch (UndefinedPieceException e) {
			e.printStackTrace(System.err);
		}
		
		if (game.getState().getPlayer().getColor() == Player.BLACK) {
			if (whiteIsCheck(game, stateCopy, x, y)) {
				message.setText("White will be check!");
				try {
					state.setPlayer(GameUtil.changePlayer(state.getPlayer(), game));
				} catch (UndefinedPlayerException e) {
					e.printStackTrace(System.err);
				}
				return true;
			}
			//System.out.println("whiteIsCheckmate "+state.getBoard()[0][0].isCastlingPoss());
			return false;
		}
		
		try {
			stateCopy = new State(state);
		} catch (UndefinedPieceException e) {
			e.printStackTrace(System.err);
		}
		
		//System.out.println(state.hashCode()+"\n"+stateCopy.hashCode());
		
		if (whiteIsCheck(game, stateCopy, x, y)) {
			
			for (int i=0; i<16; i++) {
				//System.out.println("i = "+i);
				// validMoves prend en compte les pieces null
				validMoves=validMoves(game, white[i], true);
				if (validMoves.size() == 0) {
					/*if (white[i] != null)
						System.out.println(white[i].getNom());*/
					continue;
				}
				
				for (int j=0; j<validMoves.size(); j++) {
					move = validMoves.get(j);
					try {
						stateCopy = new State(state);
					} catch (UndefinedPieceException e) {
						e.printStackTrace(System.err);
					}
					
					if (stateCopy == null) return false;
					
					try {
						game.verifyAndPlayMove(stateCopy,
												move[0][0], move[0][1], move[1][0], move [1][1], true);
					} catch (UndefinedPlayerException e) {
						e.printStackTrace();
					}
					
					//System.out.println(move[0][0]+" "+move[0][1]);
					//System.out.println(stateCopy.toString());
					//System.out.println(state.toString());
					
					/*try {
						stateCopy.setPlayer(GameUtil.changePlayer(stateCopy.getPlayer(), game));
						System.out.print("whiteIsCheckmate ");
						System.out.println(state.getPlayer().getColor() == Player.WHITE ? "white" : "black");
					} catch (UndefinedPlayerException e) {
						e.printStackTrace(System.err);
					}*/
					
					if (!whiteIsCheck(game, stateCopy,
						stateCopy.getWKing().getX(), stateCopy.getWKing().getY())) {
						
						//System.out.println(stateCopy);
						//System.out.println("Check!");
						message.setText("Check!");
						state.setCheck(true);
						return false;
						
					}
				}
			}
			message.setText("Checkmate! Black wins");
			return true;
		}
		
		return false;
	}
	
	public static boolean blackIsCheckmate(Game game, JLabel message) {
		State state = game.getState();
		State stateCopy = null;
		Piece bKing = state.getBKing();
		int x = bKing.getX();
		int y = bKing.getY();
		Piece[] black = state.getBlack();
		Vector<int[][]> validMoves;
		int[][] move;
		
		try {
			stateCopy = new State(state);
		} catch (UndefinedPieceException e) {
			e.printStackTrace(System.err);
		}
		
		if (game.getState().getPlayer().getColor() == Player.WHITE) {
			if (blackIsCheck(game, stateCopy, x, y)) {
				message.setText("Black will be check!");
				try {
					state.setPlayer(GameUtil.changePlayer(state.getPlayer(), game));
				} catch (UndefinedPlayerException e) {
					e.printStackTrace(System.err);
				}
				return true;
			}
			//System.out.println("blackIsCheckmate1 "+state.getBoard()[0][0].isCastlingPoss());
			return false;
		}
		
		try {
			stateCopy = new State(state);
		} catch (UndefinedPieceException e) {
			e.printStackTrace(System.err);
		}
		
		//System.out.println(state.hashCode()+"\n"+stateCopy.hashCode());
		
		if (blackIsCheck(game, stateCopy, x, y)) {
			
			for (int i=0; i<16; i++) {
				//System.out.println("i = "+i);
				// validMoves prend en compte les pieces null
				validMoves=validMoves(game, black[i], true);
				if (validMoves.size() == 0) {
					/*if (black[i] != null)
						System.out.println(black[i].getNom());*/
					continue;
				}
				
				for (int j=0; j<validMoves.size(); j++) {
					move = validMoves.get(j);
					try {
						stateCopy = new State(state);
					} catch (UndefinedPieceException e) {
						e.printStackTrace(System.err);
					}
					
					if (stateCopy == null) return false;
					
					try {
						game.verifyAndPlayMove(stateCopy,
												move[0][0], move[0][1], move[1][0], move [1][1], true);
					} catch (UndefinedPlayerException e) {
						e.printStackTrace();
					}
					
					//System.out.println(move[0][0]+" "+move[0][1]);
					//System.out.println(stateCopy.toString());
					//System.out.println(state.toString());
					
					/*try {
						stateCopy.setPlayer(GameUtil.changePlayer(stateCopy.getPlayer(), game));
						System.out.print("whiteIsCheckmate ");
						System.out.println(state.getPlayer().getColor() == Player.WHITE ? "white" : "black");
					} catch (UndefinedPlayerException e) {
						e.printStackTrace(System.err);
					}*/
					
					if (!blackIsCheck(game, stateCopy,
						stateCopy.getBKing().getX(), stateCopy.getBKing().getY())) {
						
						//System.out.println(stateCopy);
						//System.out.println("Check!");
						message.setText("Check!");
						state.setCheck(true);
						return false;
						
					}
				}
			}
			message.setText("Checkmate! White wins");
			return true;
		}
		//System.out.println("blackIsCheckmate2 "+state.getBoard()[0][0].isCastlingPoss());
		return false;
	}
	
	public static Vector<int[][]> validMoves(Game game, Piece piece, boolean check) {
		Vector<int[][]> list;
		int[] square1;
		Piece[][] board = game.getState().getBoard();
		Piece p = null;
		Player player = game.getState().getPlayer();
		
		list=new Vector<int[][]>();
		if (piece == null)
			return list;
		square1=new int[] {piece.getX(), piece.getY()};
		
		//System.out.println(game.getState());
		//System.out.println("validMoves1 "+board[0][7].isCastlingPoss());
		/*System.out.print("valideMoves ");
		System.out.println(piece.getNom()+" "+piece.getX()+" "+piece.getY());*/
		
		if (piece.getNom().equals("pwn"))
			p = new Pawn((Pawn) piece);
		else if (piece.getNom().equals("rok"))
			p = new Rook((Rook) piece);
		else if (piece.getNom().equals("knt"))
			p = new Knight((Knight) piece);
		else if (piece.getNom().equals("bsp"))
			p = new Bishop((Bishop) piece);
		else if (piece.getNom().equals("qun"))
			p = new Queen((Queen) piece);
		else if (piece.getNom().equals("kng"))
			p = new King((King) piece);
		
		if (p == null)
			return list;
		
		
		for (int i=0; i<8; i++)
			for (int j=0; j<8; j++) {
				if (p.isValidMove(square1[0], square1[1], i, j, board, player, check)) {
					//System.out.println("validMoves2 "+board[0][7].isCastlingPoss());
					//System.out.println(i+" "+j);
					list.add(new int[][] {square1, {i, j}});
				}
			}
		
		return list;
	}
	
	public static Player changePlayer(Player player, Game game) throws UndefinedPlayerException {
		/*System.out.print("change ");
		System.out.println(player.getColor() == Player.WHITE ? "white" : "black");*/
		if (player.getColor() == Player.WHITE) {
			//System.out.println("black");
			return game.getBlack();
		} else if (player.getColor() == Player.BLACK) {
			//System.out.println("white");
			return game.getWhite();
		} else
			throw new UndefinedPlayerException();
	}
	
	// pour intelligence artificielle
	/*public static String[] inputMove(State state, String[] move) throws UndefinedPlayerException {
		Player p=state.getPlayer();
		if (p.getColor() == Player.WHITE)
			return p.inputWhiteMove(state, move);
		else if (p.getColor() == Player.BLACK)
			return p.inputBlackMove(state, move);
		else
			throw new UndefinedPlayerException();
	}*/

}
