package chess.game.pieces;

import chess.game.Game;
import chess.game.players.Player;

public class King extends Piece {
	
	public King () {}
	
	public King(int color) {
		super("kng", color);
	}
	
	/* constructeur par recopie */
	public King (King p) {
		super(p);
	}
	
	public boolean isValidMove(int x1, int y1, int x2, int y2, Piece[][] board, Player player, boolean check) {
		/** commun a toutes les pieces (debut) **/
		
		if (x1 == x2 && y1 == y2)
			return false;
		if (!(this.getColor() == player.getColor() &&
			((board[y2][x2] != null && board[y2][x2].getColor() != this.getColor()) ||
			board[y2][x2] == null)))
			return false;
		
		/** commun a toutes les pieces (fin) **/
		
		/*if (check) System.out.println("isValidMove: check");*/
		
		if ((x1 == x2+1 && y1 == y2+1) ||
			(x1 == x2+1 && y1 == y2-1) ||
			(x1 == x2+1 && y1 == y2) ||
			(x1 == x2-1 && y1 == y2+1) ||
			(x1 == x2-1 && y1 == y2-1) ||
			(x1 == x2-1 && y1 == y2) ||
			(x1 == x2 && y1 == y2+1) ||
			(x1 == x2 && y1 == y2-1)) {
			
			castlingPoss=false;
			return true;
			
		} else if (color == Game.WHITE && !check) { // castling
			if (castlingPoss == true)
				if (x1 == 4 && y1 == 7 && y2 == 7)
					if (x2 == 6 && board[7][5] == null &&
						board[7][7] !=null && board[7][7].getNom().equals("rok") &&
						board[7][7].isCastlingPoss()) {
						
						castlingPoss=false;
						return true;
						
					} else if (x2 == 2 && board[7][3] == null &&
							board[7][0] !=null && board[7][0].getNom().equals("rok") &&
							board[7][0].isCastlingPoss()) {
						
						castlingPoss=false;
						return true;
						
					}
		} else if (color == Game.BLACK && !check) { // castling
			if (castlingPoss == true) {
				//System.out.print("castling ");
				//System.out.println(board[0][0].isCastlingPoss());
				if (x1 == 4 && y1 == 0 && y2 == 0)
					if (x2 == 6 && board[0][5] == null &&
						board[0][7] !=null && board[0][7].getNom().equals("rok") &&
						board[0][7].isCastlingPoss()) {
						
						castlingPoss=false;
						return true;
						
					} else if (x2 == 2 && board[0][3] == null &&
							board[0][0] !=null && board[0][0].getNom().equals("rok") &&
							board[0][0].isCastlingPoss()) {
						//System.out.println("castling: true");
						castlingPoss=false;
						return true;
						
					}
			}
		}
		
		return false;
	}
}
