package chess.game.pieces;

import chess.game.players.Player;

public class Queen extends Piece {
	
	public Queen() {}
	
	public Queen(int color) {
		super("qun", color);
	}
	
	/* constructeur par recopie */
	public Queen (Queen p) {
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
		
		Piece bsp=new Bishop(this.getColor());
		bsp.setX(this.x);
		bsp.setY(this.y);
		Piece rk=new Rook(this.getColor());
		rk.setX(this.x);
		rk.setY(this.y);
		
		/*System.out.print("Queen.isValideMove "+(color== Player.WHITE ? "white " : "black "));
		System.out.println(nom+" coord (param): "+x1+" "+y1+" "+x2+" "+y2);*/
		
		return bsp.isValidMove(x1, y1, x2, y2, board, player, check) ||
			rk.isValidMove(x1, y1, x2, y2, board, player, check);
	}
}
