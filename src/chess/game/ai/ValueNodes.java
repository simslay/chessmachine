package chess.game.ai;

import java.util.Vector;

public class ValueNodes {
	private double value;
	private Vector<Node> nodes;
	
	public ValueNodes(double value, Vector<Node> nodes) {
		this.value = value;
		this.nodes = nodes;
	}

	/** getters **/
	public double getValue() {return value;}
	public Vector<Node> getNodes() {return nodes;}
	/** setters **/
	public void setValue(double value) {this.value = value;}
	public void setNodes(Vector<Node> nodes) {this.nodes = nodes;}

}
