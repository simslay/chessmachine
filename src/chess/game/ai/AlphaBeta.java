package chess.game.ai;

import java.util.Vector;

import chess.game.Game;
import chess.game.State;
import chess.game.UndefinedPlayerException;
import chess.game.players.Player.Fevals;

public class AlphaBeta {

	public static int[][] alphaBetaSearch(Game game, Node n, Fevals fev, int PMAX) throws UndefinedPlayerException {
		ValueNodes valueNodes;
		State state = game.getState();

		valueNodes = maxValue(game, n, fev, PMAX, -999999, 999999);
		game.setState(state);
		
		//System.out.println("alphaBetaSearch: "+valueNodes.getValue());
		
		return AIUtil.searchMove(valueNodes);
	}
	
	public static ValueNodes maxValue(Game game, Node n, Fevals fev, int PMAX, double alpha, double beta)
	throws UndefinedPlayerException {
		double v = -999999;
		State state = n.getState();
		
		//System.out.println("maxValue");
		
		game.setState(state);
		
		if (AIUtil.isEndOfExploration(game, n, PMAX)) {
			//System.out.println("n.getDepth "+n.getDepth());
			double f = AIUtil.eval(game, fev, game.getState(), game.getState().getBoard());
			Vector<Node> nodes = new Vector<Node>();
			nodes.add(n);
			n.setF(f);
			return new ValueNodes(f, nodes);
		}
		
		Vector<Node> nodes = Horizon1.expandEval(game, state.isCheck(), n);
		for (int i=0; i<nodes.size(); i++) {
			Node node = nodes.get(i);
			
			game.setState(node.getState());
			v = Math.max(v, minValue(game, node, fev, PMAX, alpha, beta).getValue());
			if (v >= beta) {
				n.setF(v);
				return new ValueNodes(v, nodes);
			}
			alpha = Math.max(alpha, v);
		}
		
		n.setF(v);
		return new ValueNodes(v, nodes);
	}
	
	public static ValueNodes minValue(Game game, Node n, Fevals fev, int PMAX, double alpha, double beta)
	throws UndefinedPlayerException {
		double v = 999999;
		State state = n.getState();
		
		//System.out.println("minValue");
		
		game.setState(state);
		
		if (AIUtil.isEndOfExploration(game, n, PMAX)) {
			double f = AIUtil.eval(game, fev, game.getState(), game.getState().getBoard());
			Vector<Node> nodes = new Vector<Node>();
			System.out.print("n.getDepth "+n.getDepth());
			System.out.println(" "+f);
			nodes.add(n);
			n.setF(f);
			return new ValueNodes(f, nodes);
		}
		
		Vector<Node> nodes = Horizon1.expandEval(game, state.isCheck(), n);
		for (int i=0; i<nodes.size(); i++) {
			Node node = nodes.get(i);
			
			game.setState(node.getState());
			v = Math.min(v, maxValue(game, node, fev, PMAX, alpha, beta).getValue());
			if (v <= alpha) {
				n.setF(v);
				return new ValueNodes(v, nodes);
			}
			beta = Math.min(beta, v);
		}
		
		n.setF(v);
		return new ValueNodes(v, nodes);
	}
	
}
