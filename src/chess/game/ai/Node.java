package chess.game.ai;

import chess.game.State;

public class Node {
	private State state;
	private int[][] action;
	private Node parent;
	private int depth;
	private double f;
	private boolean expanded;
	
	public Node (State state, int[][] move, double f, int depth) {
		this.state = state;
		action = move;
		parent = null;
		this.depth = depth;
		this.f = f;
		expanded = true;
	}

	/* getters */
	public State getState() {return state;}
	public int[][] getAction() {return action;}
	public Node getParent() {return parent;}
	public int getDepth() {return depth;}
	public double getF() {return f;}
	public boolean isExpanded() {return expanded;}
	/* setters */
	public void setState(State state) {this.state = state;}
	public void setAction(int[][] action) {this.action = action;}
	public void setParent(Node parent) {this.parent = parent;}
	public void setDepth(int depth) {this.depth = depth;}
	public void setF(double f) {this.f = f;}
	public void setExpanded(boolean expanded) {this.expanded = expanded;}
}
