package chess.game.ai;

import java.util.Vector;

import javax.swing.JLabel;

import chess.game.Game;
import chess.game.GameUtil;
import chess.game.State;
import chess.game.UndefinedPlayerException;
import chess.game.pieces.Piece;
import chess.game.players.Player.Fevals;
import chess.game.players.artificial.*;

public class AIUtil {
	
	public static boolean isEndOfExploration(Game game, Node node, int PMAX) {
		return node.getDepth() == PMAX || GameUtil.isEndOfGame(game, new JLabel());
	}
	
	public static double eval(Game game, Fevals fev, State state, Piece[][] board) throws UndefinedPlayerException {
		if (fev.getLabel().charAt(0) == 'w') {
			ArtificialWhite.feval = fev;
			return ArtificialWhite.feval(game, state, board);
		} else if (fev.getLabel().charAt(0) == 'b') {
			ArtificialBlack.feval = fev;
			return ArtificialBlack.feval(game, state, board);
		} else throw new UndefinedPlayerException();
	}
	
	public static int[][] searchOptimalMove(Vector<Node> nodes) {
		Node node = null;
		double f = 0;
		int [][] move = null;
		
		if (nodes.size() > 0)
			move = nodes.get(0).getAction();
		else
			return null;
		
		for (int i=0; i<nodes.size(); i++) {
			node = nodes.get(i);
			
			/*System.out.println(node.getAction()[0][0]+" "+node.getAction()[0][1]+" "+
			node.getAction()[1][0]+" "+node.getAction()[1][1]);*/
			
			if (node.getF() > f) {
				f = node.getF();
				move = node.getAction();
			}
			
		}
		
		//System.out.println("searchOptimalMove: ("+move[1][0]+" "+move[1][1]+") f: "+f);
		return move;
	}
	
	public static int[][] searchMove(ValueNodes valueNodes) {
		double v = valueNodes.getValue();
		Vector<Node> nodes = valueNodes.getNodes();
		
		for (int i=0; i<nodes.size(); i++)
			if (nodes.get(i).getF() == v) {
				System.out.println("searchMove: "+v);
				//System.out.print(""+nodes.get(i).getAction()[0][0]+nodes.get(i).getAction()[0][1]);
				//System.out.println(" "+nodes.get(i).getAction()[1][0]+nodes.get(i).getAction()[1][1]);
				return nodes.get(i).getAction();
			}

		return null;
	}
	
}
