package chess.game.players.artificial;

import java.util.Vector;

import javax.swing.JLabel;

import chess.game.Game;
import chess.game.GameUtil;
import chess.game.State;
import chess.game.UndefinedPlayerException;
//import chess.game.ai.AIUtil;
import chess.game.ai.AIUtil;
import chess.game.ai.AlphaBeta;
import chess.game.ai.Horizon1;
//import chess.game.ai.Horizon1;
import chess.game.ai.Node;
import chess.game.pieces.Piece;
import chess.game.players.Player;

public class ArtificialBlack extends Player {
	public static Fevals feval = Fevals.bFeval;
	public static int mode = HORIZON1;
	public static int PMAX = 2;

	public ArtificialBlack() {
		type=ARTIFICIAL;
		color=BLACK;
	}
	
	public int[][] enterMove(Game game, boolean check) throws UndefinedPlayerException {
		State state = game.getState();
		Node node;
		
		//System.out.println(state.getPlayer().getColor() == Player.BLACK ? "black" : "white");
		
		node = new Node(state, new int[2][2], 0, 0);
		
		switch (mode) {
			case 0 :
				Vector<Node> nodes = null;
				nodes = Horizon1.expandEval(game, check, node);
				return AIUtil.searchOptimalMove(nodes);
			case 1 :
				return AlphaBeta.alphaBetaSearch(game, node, feval, PMAX);
			default :
				return null;
		}
	}

	/** fonction d'evaluation **/
	public static double feval(Game game, State state, Piece[][] board) {
		double res = 0;
		double rand = Math.random()/10;
		
		switch (feval) {
			case bFeval :
				double[] weights = {4, 5, 10, 5, 4};
				//double[] weights = {1, 0, 10, 0, 1};
				//double[] weights = {0, 0, 10, 0, 0};
				
				double[] fevals = {	fevalOwnCaptures(state),
									fevalOpponentCaptures(state),
									fevalCheckmate(game, state),
									fevalStalemate(game, state),
									fevalPromotion(state)};
				
				for (int i=0; i<weights.length; i++)
					res += weights[i]*fevals[i];
				//System.out.println("feval: res = "+res);
				return res + rand;
			case bAlea :
				return fevalAlea();
			case bCaptures :
				return fevalOwnCaptures(state) + rand;
			case bCheckmate :
				return fevalCheckmate(game, state) + rand;
			default :
				return fevalAlea();
		}
	}
	
	/** fonction d'evaluation aleatoire **/
	public static double fevalAlea() {
		return Math.random();
	}
	
	/** fonction d'evaluation selon valeur de la piece capturee a l'adversaire **/
	public static double fevalOwnCaptures(State state) {
		Vector<Piece> blackCaptures = state.getBlackCaptures();
		double res = 0;
		
		for (int i=0; i<blackCaptures.size(); i++) {
			if (blackCaptures.get(i).getNom().equals("pwn"))
				res += 1;
			else if (blackCaptures.get(i).getNom().equals("knt"))
				res += 3;
			else if (blackCaptures.get(i).getNom().equals("bsp"))
				res += 3;
			else if (blackCaptures.get(i).getNom().equals("rok"))
				res += 5;
			else if (blackCaptures.get(i).getNom().equals("qun"))
				res += 9;
		}
		
		return res;
	}
	
	/** fonction d'evaluation selon valeur de la piece capturee par l'adversaire **/
	public static double fevalOpponentCaptures(State state) {
		Vector<Piece> whiteCaptures = state.getWhiteCaptures();
		double res = 0;
		
		for (int i=0; i<whiteCaptures.size(); i++) {
			if (whiteCaptures.get(i).getNom().equals("pwn"))
				res -= 1;
			else if (whiteCaptures.get(i).getNom().equals("knt"))
				res -= 3;
			else if (whiteCaptures.get(i).getNom().equals("bsp"))
				res -= 3;
			else if (whiteCaptures.get(i).getNom().equals("rok"))
				res -= 5;
			else if (whiteCaptures.get(i).getNom().equals("qun"))
				res -= 9;
		}
		
		return res;
	}
	
	/** fonction d'evaluation du mat **/
	public static double fevalCheckmate(Game game, State state) {
		State gameState = game.getState();
		double res = 0;
		
		game.setState(state);
		
		//System.out.println(state.getPlayer().getColor() == Player.BLACK ? "black" : "white");
		
		if (GameUtil.whiteIsCheckmate(game, new JLabel())) {
			//System.out.println("checkmate");
			//System.out.println(state);
			res = 100;
		}
		
		game.setState(gameState);
		
		return res;
	}
	
	/** fonction d'evaluation du stalemate **/
	public static double fevalStalemate(Game game, State state) {
		State gameState = game.getState();
		double res = 0;
		
		game.setState(state);
		
		if (GameUtil.whiteIsStalemate(game, new JLabel()))
			res = -10;
		
		game.setState(gameState);
		
		return res;
	}
	
	/** fonction d'evaluation de la promotion **/
	public static double fevalPromotion(State state) {
		Piece[][] board = state.getBoard();
		Piece piece;
		
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				piece = board[i][j];
				if (piece != null && piece.getColor() == Game.BLACK && piece.isPromoted()) {
					if (piece.getNom().equals("knt"))
						return 3;
					else if (piece.getNom().equals("bsp"))
						return 3;
					else if (piece.getNom().equals("rok"))
						return 5;
					else if (piece.getNom().equals("qun"))
						return 9;
				}
			}
		}
		
		return 0;
	}
	
	/* setters */
	public void setFeval(Fevals feval) {ArtificialBlack.feval = feval;}
	
}
