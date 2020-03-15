package chess.game.pieces;

import chess.game.players.Player;

public class Rook extends Piece {
	
	public Rook() {}
	
	public Rook(int color) {
		super("rok", color);
	}
	
	/* constructeur par recopie */
	public Rook (Rook p) {
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
		
		/*System.out.print("Rook.isValideMove ");
		System.out.println(nom+" "+x1+" "+x2+" "+x+" "+y);*/
		
		/* verification du mouvement en ligne droite */
		if ((x1 != x2 || y1 == y2) &&
			(x1 == x2 || y1 != y2))
			return false;
		/* verification des cases entre les positions de depart
		 * et d'arrivee de la piece */
		if (x1 == x2 && y1 < y2)
			for(int j=y1+1; j<y2; j++)
				if (board[j][x1]!=null)
					return false;
		if (x1 < x2 && y1 == y2)
			for(int i=x1+1; i<x2; i++)
				if (board[y1][i]!=null)
					return false;
		if (x1 == x2 && y1 > y2)
			for(int j=y1-1; j>y2; j--)
				if (board[j][x1]!=null)
					return false;
		if (x1 > x2 && y1 == y2)
			for(int i=x1-1; i>x2; i--)
				if (board[y1][i]!=null)
					return false;
		//System.out.println("castlingPoss: false");
		castlingPoss = false;
		
		return true;
	}
}
