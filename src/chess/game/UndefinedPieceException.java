package chess.game;

public class UndefinedPieceException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UndefinedPieceException () {
		super("Undefined Piece");
	}
}
