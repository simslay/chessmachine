package chess.game.players.human;

import chess.game.players.Player;
import chess.game.Game;
import chess.game.UndefinedPlayerException;

public class HumanWhite extends Player {
	
	public HumanWhite() {
		type=HUMAN;
		color=WHITE;
	}
	
	public int[][] enterMove(Game game, boolean check) throws UndefinedPlayerException {
		int[][] move=null;
		
		return move;
	}
}
