package chess.game.pieces;

import chess.game.Game;
import chess.game.players.Player;

public class Pawn extends Piece {

	public Pawn() {}
	
	public Pawn(int color) {
		super("pwn", color);
	}
	
	/* constructeur par recopie */
	public Pawn (Pawn p) {
		super(p);
	}
	
	public boolean isValidMove(int x1, int y1, int x2, int y2, Piece[][] board, Player player, boolean check) {
		/** commun a toutes les pieces (debut) **/
		
		/*System.out.print("isValidMove: ");
		System.out.println(x1+" "+y1+" "+x2+" "+y2);
		System.out.println("player: "+(player.getColor() == Player.WHITE ? "white" : "black"));*/
		
		if (x1 == x2 && y1 == y2)
			return false;
		if (!(this.getColor() == player.getColor() &&
			((board[y2][x2] != null && board[y2][x2].getColor() != this.getColor()) ||
			board[y2][x2] == null)))
			return false;
		
		/** commun a toutes les pieces (fin) **/
		
		if (color == Game.WHITE) {
			if (x1 == x2 && (y2-y1 == -1 || y2-y1 == -2)) {
				if (board[y2][x2]!=null)
					return false;
				/* premier coup du pion de deux cases en avant */
				if (y1 == 6 && y2-y1 == -2) {
					if (board[y1-1][x1]==null) {
						enPassantRisk=true;
						return true;
					} else
						return false;
				}
				if (y1 != 6 && y2-y1 == -2)
					return false;
				return true;
			}
			
			/*System.out.println("Pawn.isValideMove: enPassant");
			System.out.println(x1+" "+y1+" "+x2+" "+y2);*/
			//if (board[y1][x2] != null /*&& board[y1][x2].isEnPassantRisk()*/)
				//System.out.println(board[y1][x2]);
			
			/* mouvement en diagonale */
			if ((x2 == x1+1 || x2 == x1-1) && y2 == y1-1 &&
				((board[y2][x2]!=null && board[y2][x2].getColor() == Game.BLACK) ||
					(y1 == 3 && board[y1][x2]!=null && board[y1][x2].getNom().equals("pwn") &&
					board[y1][x2].isEnPassantRisk()) ) )
				return true;
			
			return false;
		}
		
		if (color == Game.BLACK) {
			if (x1 == x2 && (y2-y1 == 1 || y2-y1 == 2)) {
				if (board[y2][x2]!=null)
					return false;
				/* premier coup du pion de deux cases en avant */
				if (y1 == 1 && y2-y1 == 2) {
					if (board[y1+1][x1]==null) {
						enPassantRisk=true;
						return true;
					} else
						return false;
				}
				if (y1 != 1 && y2-y1 == 2)
					return false;
				return true;
			}
			
			if ((x2 == x1+1 || x2 == x1-1) && y2 == y1+1 &&
				((board[y2][x2]!=null && board[y2][x2].getColor() == Game.WHITE) ||
					(y1 == 4 && board[y1][x2]!=null && board[y1][x2].getNom().equals("pwn") &&
					board[y1][x2].isEnPassantRisk()) ) )
					return true;
				
			return false;
		}
		
		return false;
	}
	
}
