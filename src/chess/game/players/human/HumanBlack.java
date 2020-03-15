package chess.game.players.human;

import chess.game.players.Player;
import chess.game.Game;
import chess.game.UndefinedPlayerException;

public class HumanBlack extends Player {
	
	public HumanBlack() {
		type=HUMAN;
		color=BLACK;
	}
	
	public int[][] enterMove(Game game, boolean check) throws UndefinedPlayerException {
		int[][] move=null;
		
		return move;
	}
}
