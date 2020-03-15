package chess.game.ai;

import java.util.Vector;

import javax.swing.JLabel;

import chess.game.Game;
import chess.game.GameUtil;
import chess.game.State;
import chess.game.UndefinedPieceException;
import chess.game.UndefinedPlayerException;
import chess.game.pieces.Piece;
import chess.game.players.Player;
import chess.game.players.artificial.*;


public class Horizon1 {

	public static Vector<Pair> successors(Game game, State state, boolean check) throws UndefinedPlayerException {
		State stateCopy = null;
		//Piece[][] board;
		//Piece[] white, black;
		Piece[] pieces;
		Vector<Pair> successors;
		Vector<int[][]> validMoves;
		int [][] move;
		JLabel label = new JLabel();
		
		if (state.getPlayer().getColor() == Player.WHITE)
			pieces = state.getWhite();
		else if (state.getPlayer().getColor() == Player.BLACK)
			pieces = state.getBlack();
		else throw new UndefinedPlayerException();
		
		successors = new Vector<Pair>();
		
		for (int i=0; i<16; i++) {
			if (pieces[i] != null) {
				validMoves = GameUtil.validMoves(game, pieces[i], check);
				//System.out.println(pieces[i]);
				//System.out.println(validMoves.size()+"\n");
				for (int j=0; j<validMoves.size(); j++) {
					move = validMoves.get(j);
					
					try {stateCopy = new State(state);}
					catch (UndefinedPieceException e) {e.printStackTrace(System.err);}
					
					if (game.verifyAndPlayMove(stateCopy, move[0][0], move[0][1], move[1][0], move[1][1], true)) {
						//System.out.println("successors");
						game.setState(stateCopy);
						switch (stateCopy.getPlayer().getColor()) {
							case Player.WHITE :
								if (GameUtil.blackIsCheckmate(game, label)) {
									//System.out.println("continue");
									continue;
								}
								break;
							case Player.BLACK :
								if (GameUtil.whiteIsCheckmate(game, label)) {
									//System.out.println("continue");
									continue;
								}
								break;
							default :
								throw new UndefinedPlayerException();
						}
						
						/** promotion **/
						/*for (int k=0; k<16; k++) {
							if (stateCopy.getWhite()[k]!=null && stateCopy.getWhite()[k].isPromoted())
								stateCopy.getWhite()[k].setPromoted(false);
							if (stateCopy.getBlack()[k]!=null && stateCopy.getBlack()[k].isPromoted())
								stateCopy.getBlack()[k].setPromoted(false);
						}*/
						if (pieces[i].getNom().equals("pwn") &&
								pieces[i].getColor() == Game.WHITE &&
								move[1][1] == 0) {
							
							for (int k=0; k<4; k++)
								successors.add(new Pair(stateCopy,
														new int[][]{move[0], move[1], {k}}));
							continue;
								
						} else if (pieces[i].getNom().equals("pwn") &&
								pieces[i].getColor() == Game.BLACK &&
									move[1][1] == 7) {
							
							for (int k=0; k<4; k++)
								successors.add(new Pair(stateCopy,
														new int[][]{move[0], move[1], {k}}));
							continue;
								
						}
						
					
						successors.add(new Pair(stateCopy, move));
					}
				}
				game.setState(state);
			}
		}
		
		return successors;
	}
	
	public static Vector<Node> expandEval(Game game, boolean check, Node n) throws UndefinedPlayerException {
		State state = n.getState(), newState = null;
		State gameState = game.getState();
		Vector<Node> nodes;
		Vector<Pair> successors = null;
		int[][] move;
		double f = 0;
		
		nodes = new Vector<Node>();
		
		try {successors = successors(game, state, check);}
		catch (UndefinedPlayerException upe) {upe.printStackTrace(System.err);}
		
		if (successors != null && successors.size() != 0) {
			for (int i=0; i<successors.size(); i++) {
				newState = successors.get(i).getState();
				game.setState(newState);
				
				if (state.getPlayer().getColor() == Player.WHITE)
					f = ArtificialWhite.feval(game, newState, newState.getBoard());
				else if (state.getPlayer().getColor() == Player.BLACK)
					f = ArtificialBlack.feval(game, newState, newState.getBoard());
				else throw new UndefinedPlayerException();
			
				move = successors.get(i).getMove();
				//System.out.println("expandEval move: ("+move[1][0]+" "+move[1][1]+") f: "+f);
				nodes.add(new Node(newState, move, f, n.getDepth()+1));
			}
		}
		
		game.setState(gameState);
		
		return nodes;
	}
	
}
