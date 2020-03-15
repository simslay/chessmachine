package chess.game.pieces;

import chess.game.players.Player;

public class Bishop extends Piece {
	
	public Bishop() {};
	
	public Bishop(int color) {
		super("bsp", color);
	}
	
	/* constructeur par recopie */
	public Bishop (Bishop p) {
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
		
		/*System.out.print("Bishop.isValideMove "+(color== Player.WHITE ? "white " : "black "));
		System.out.println(nom+" coord (param): "+x1+" "+y1+" "+x2+" "+y2);*/
		
		/* verification du mouvement en diagonale */
		if (x1 == x2 || y1 == y2 || Math.abs(x2-x1) != Math.abs(y2-y1))
			return false;
		/* verification des cases entre les positions de depart
		 * et d'arrivee de la piece */
		if (x1 < x2 && y1 < y2)
			for(int i=x1+1; i<x2; i++)
				if (board[y1+(i-x1)][i]!=null)
					return false;
		if (x1 < x2 && y1 > y2)
			for(int i=x1+1; i<x2; i++)
				if (board[y1-(i-x1)][i]!=null)
					return false;
		if (x1 > x2 && y1 < y2)
			for(int i=x2+1; i<x1; i++)
				if (board[y2-(i-x2)][i]!=null)
					return false;
		if (x1 > x2 && y1 > y2)
			for(int i=x2+1; i<x1; i++)
				if (board[y2+(i-x2)][i]!=null)
					return false;
		
		return true;
	}
}
