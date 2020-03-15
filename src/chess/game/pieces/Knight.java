package chess.game.pieces;

import chess.game.players.Player;

public class Knight extends Piece {
	/* coordonnees de depart des pieces blanches */
	public static final char xWhite1='b';
	public static final int yWhite1=1;
	public static final char xWhite2='g';
	public static final int yWhite2=1;
	/* coordonnees de depart des pieces noires */
	public static final char xBlack1='g';
	public static final int yBlack1=8;
	public static final char xBlack2='b';
	public static final int yBlack2=8;
	
	public Knight() {}
	
	public Knight(int color) {
		super("knt", color);
	}
	
	/* constructeur par recopie */
	public Knight (Knight p) {
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
		
		/* verification du mouvement en L */
		if ((x2 == x1+1 && y2 == y1+2) ||
			(x1 == x2+1 && y2 == y1+2) ||
			(x2 == x1+1 && y1 == y2+2) ||
			(x1 == x2+1 && y1 == y2+2) ||
			(x2 == x1+2 && y2 == y1+1) ||
			(x1 == x2+2 && y2 == y1+1) ||
			(x2 == x1+2 && y1 == y2+1) ||
			(x1 == x2+2 && y1 == y2+1))
				return true;

		return false;
	}
}
