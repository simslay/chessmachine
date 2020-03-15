package chess.game.ai;

import chess.game.State;

public class Pair {
	private State state;
	private int[][] move;
	
	public Pair (State state, int[][] move) {
		this.state = state;
		this.move = move;
	}

	/* getters */
	public State getState() {return state;}
	public int[][] getMove() {return move;}
	/* setters */
	public void setState(State state) {this.state = state;}
	public void setMove(int[][] move) {this.move = move;}
}
