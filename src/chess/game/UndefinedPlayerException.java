package chess.game;

public class UndefinedPlayerException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UndefinedPlayerException () {
		super("Undefined Player");
	}
}
