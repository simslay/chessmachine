package chess.game;

import java.util.Vector;
import chess.game.pieces.*;
import chess.game.Game;
import chess.game.players.Player;
import chess.game.players.artificial.*;
import chess.game.players.human.*;
//import chess.game.players.artificial.*;

public class State {
	private Piece[][] board;
	private Piece[] white, black;
	private Player player;
	private Vector<Piece> whiteCaptures; // pieces des noirs
	private Vector<Piece> blackCaptures; // pieces des blancs
	private String[] move;
	private Vector<String[]> review;
	private King wKing, bKing;
	private boolean check=false;
	
	
	public State () {
		bKing=new King(Game.BLACK);
		wKing=new King(Game.WHITE);
		
		black=new Piece[] {	new Rook(Game.BLACK), new Knight(Game.BLACK), new Bishop(Game.BLACK), new Queen(Game.BLACK),
							bKing, new Bishop(Game.BLACK), new Knight(Game.BLACK), new Rook(Game.BLACK),
							new Pawn(Game.BLACK), new Pawn(Game.BLACK), new Pawn(Game.BLACK), new Pawn(Game.BLACK),
							new Pawn(Game.BLACK), new Pawn(Game.BLACK), new Pawn(Game.BLACK), new Pawn(Game.BLACK) };
		
		white=new Piece[] { new Pawn(Game.WHITE), new Pawn(Game.WHITE), new Pawn(Game.WHITE), new Pawn(Game.WHITE),
							new Pawn(Game.WHITE), new Pawn(Game.WHITE), new Pawn(Game.WHITE), new Pawn(Game.WHITE),
							new Rook(Game.WHITE), new Knight(Game.WHITE), new Bishop(Game.WHITE), new Queen(Game.WHITE),
							wKing, new Bishop(Game.WHITE), new Knight(Game.WHITE), new Rook(Game.WHITE) };
		
		board=new Piece[][] {{	black[0], black[1], black[2], black[3],
								black[4], black[5], black[6], black[7]},
								{black[8], black[9], black[10], black[11],
								black[12], black[13], black[14], black[15]},
								{null, null, null, null, null, null, null, null},
								{null, null, null, null, null, null, null, null},
								{null, null, null, null, null, null, null, null},
								{null, null, null, null, null, null, null, null},
								{white[0], white[1], white[2], white[3],
								white[4], white[5], white[6], white[7]},
								{white[8], white[9], white[10], white[11],
								white[12], white[13], white[14], white[15]	}};
		for (int i=0; i<2; i++)
			for (int j=0; j<8; j++) {
				board[i][j].setX(j);
				board[i][j].setY(i);
				board[i+6][j].setX(j);
				board[i+6][j].setY(i+6);
			}
		
		whiteCaptures=new Vector<Piece>();
		blackCaptures=new Vector<Piece>();
		move=new String[2];
		review=new Vector<String[]>();
	}
	
	/* constructeur par recopie */
	public State (State state) throws UndefinedPieceException {
		bKing = new King((King) state.getBKing());
		wKing = new King((King) state.getWKing());
		
		black=new Piece[16];
		for (int i=0; i<16; i++)
			if (state.black[i] != null) {
				if (state.black[i].getClass() == new Pawn().getClass())
					black[i] = new Pawn((Pawn) state.black[i]);
				else if (state.black[i].getClass() == new Rook().getClass())
					black[i] = new Rook((Rook) state.black[i]);
				else if (state.black[i].getClass() == new Knight().getClass())
					black[i] = new Knight((Knight) state.black[i]);
				else if (state.black[i].getClass() == new Bishop().getClass())
					black[i] = new Bishop((Bishop) state.black[i]);
				else if (state.black[i].getClass() == new Queen().getClass())
					black[i] = new Queen((Queen) state.black[i]);
				else if (state.black[i].getClass() == new King().getClass())
					black[i] = bKing;
			} else
				black[i] = null;
		
		white=new Piece[16];
		for (int i=0; i<16; i++)
			if (state.white[i] != null) {
				if (state.white[i].getClass() == new Pawn().getClass())
					white[i] = new Pawn((Pawn) state.white[i]);
				else if (state.white[i].getClass() == new Rook().getClass())
					white[i] = new Rook((Rook) state.white[i]);
				else if (state.white[i].getClass() == new Knight().getClass())
					white[i] = new Knight((Knight) state.white[i]);
				else if (state.white[i].getClass() == new Bishop().getClass())
					white[i] = new Bishop((Bishop) state.white[i]);
				else if (state.white[i].getClass() == new Queen().getClass())
					white[i] = new Queen((Queen) state.white[i]);
				else if (state.white[i].getClass() == new King().getClass())
					white[i] = wKing;
			} else
				white[i] = null;
		
		board=new Piece[8][8];
		for (int i=0; i<8; i++)
			for (int j=0; j<8; j++) {
				if (state.board[i][j] != null) {
					for (int k=0; k<16; k++)
						if (state.board[i][j] == state.black[k]) {
							board[i][j] = black[k];
							break;
						} else if (state.board[i][j] == state.white[k]) {
							board[i][j] = white[k];
							break;
						}
				} else
					board[i][j] = null;
			}

		if (state.getPlayer().getColor() == Game.WHITE &&
			state.getPlayer().getType() == Game.HUMAN)
			player = new HumanWhite();
		if (state.getPlayer().getColor() == Game.BLACK &&
			state.getPlayer().getType() == Game.HUMAN)
			player = new HumanBlack();
		if (state.getPlayer().getColor() == Game.WHITE &&
			state.getPlayer().getType() == Game.ARTIFICIAL)
			player = new ArtificialWhite();
		if (state.getPlayer().getColor() == Game.BLACK &&
			state.getPlayer().getType() == Game.ARTIFICIAL)
			player = new ArtificialBlack();
		
		whiteCaptures=new Vector<Piece>();
		for (int i=0; i<state.getWhiteCaptures().size(); i++) {
			if (state.getWhiteCaptures().get(i).getClass() == new Pawn().getClass())
				whiteCaptures.add(new Pawn((Pawn) state.getWhiteCaptures().get(i)));
			else if (state.getWhiteCaptures().get(i).getClass() == new Rook().getClass())
				whiteCaptures.add(new Rook((Rook) state.getWhiteCaptures().get(i)));
			else if (state.getWhiteCaptures().get(i).getClass() == new Knight().getClass())
				whiteCaptures.add(new Knight((Knight) state.getWhiteCaptures().get(i)));
			else if (state.getWhiteCaptures().get(i).getClass() == new Bishop().getClass())
				whiteCaptures.add(new Bishop((Bishop) state.getWhiteCaptures().get(i)));
			else if (state.getWhiteCaptures().get(i).getClass() == new Queen().getClass())
				whiteCaptures.add(new Queen((Queen) state.getWhiteCaptures().get(i)));
			else if (state.getWhiteCaptures().get(i).getClass() == new King().getClass())
				whiteCaptures.add(new King((King) state.getWhiteCaptures().get(i)));
			else
				throw new UndefinedPieceException();
		}
		
		blackCaptures=new Vector<Piece>();
		for (int i=0; i<state.getBlackCaptures().size(); i++) {
			if (state.getBlackCaptures().get(i).getClass() == new Pawn().getClass())
				blackCaptures.add(new Pawn((Pawn) state.getBlackCaptures().get(i)));
			else if (state.getBlackCaptures().get(i).getClass() == new Rook().getClass())
				blackCaptures.add(new Rook((Rook) state.getBlackCaptures().get(i)));
			else if (state.getBlackCaptures().get(i).getClass() == new Knight().getClass())
				blackCaptures.add(new Knight((Knight) state.getBlackCaptures().get(i)));
			else if (state.getBlackCaptures().get(i).getClass() == new Bishop().getClass())
				blackCaptures.add(new Bishop((Bishop) state.getBlackCaptures().get(i)));
			else if (state.getBlackCaptures().get(i).getClass() == new Queen().getClass())
				blackCaptures.add(new Queen((Queen) state.getBlackCaptures().get(i)));
			else if (state.getBlackCaptures().get(i).getClass() == new King().getClass())
				blackCaptures.add(new King((King) state.getBlackCaptures().get(i)));
			else
				throw new UndefinedPieceException();
		}
		
		move = state.move.clone();
		
		review = new Vector<String[]>();
		
		for (int i=0; i<state.review.size(); i++)
			review.add(state.review.get(i).clone());
		
		check = state.check;
	}
	
	public void addCapture(Piece p) {
		if (p.getColor() == Game.WHITE)
			blackCaptures.add(p);
		else if (p.getColor() == Game.BLACK)
			whiteCaptures.add(p);
	}
	
	public String capturesToString() {
		String s="";
		
		s+="\ncaptured white pieces :\n";
		for(int i=0; i<whiteCaptures.size(); i++)
			s+=whiteCaptures.elementAt(i).toString()+" ";
		s+="\ncaptured black pieces :\n";
		for(int i=0; i<blackCaptures.size(); i++)
			s+=blackCaptures.elementAt(i).toString()+" ";
		
		return s+"\n";
	}
	
	/*public State copy() {
		State copy;
		
		copy=new State();
		private Piece[][] board;
		private Piece[] white, black;
		private Player player;
		private Vector<Piece> whiteCaptures;
		private Vector<Piece> blackCaptures;
		private String[] move;
		private String[][] review;
		copy.setwKing(wKing);
		copy.setbKing(bKing);
		
		return copy;
	}*/

	public String toString() {
		String s="\n";
		s+="--------------------------------------\n";
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(board[i][j]!=null) {
					if (board[i][j].getColor() == Game.WHITE)
						s+="w"+board[i][j].getNom()+" ";
					else
						s+="b"+board[i][j].getNom()+" ";
				} else
					s+="     ";
			}
			s+="\n";
		}
		s+="--------------------------------------\n";
		s+="\nCurrent player: ";
		s+=player.getColor() == Player.WHITE ? "white\n" : "black\n";
		
		/*s+="\nCaptured white pieces:\n";
		for(int i=0; i<whiteCaptures.size(); i++)
			s+=whiteCaptures.elementAt(i).toString()+" ";
		s+="\nCaptured black pieces:\n";
		for(int i=0; i<blackCaptures.size(); i++)
			s+=blackCaptures.elementAt(i).toString()+" ";*/
		
		return s+"\n";
	}

	/* getters */
	public Piece[][] getBoard() {return board;}
	public Piece[] getWhite() {return white;}
	public Piece[] getBlack() {return black;}
	public Player getPlayer() {return player;}
	public Vector<Piece> getWhiteCaptures() {return whiteCaptures;}
	public Vector<Piece> getBlackCaptures() {return blackCaptures;}
	public String[] getMove() {return move;}
	public Vector<String[]> getReview() {return review;}
	public King getWKing() {return wKing;}
	public King getBKing() {return bKing;}
	public boolean isCheck() {return check;}
	/* setters */
	public void setBoard(Piece[][] board) {this.board = board;}
	public void setWhite(Piece[] white) {this.white = white;}
	public void setBlack(Piece[] black) {this.black = black;}
	public void setPlayer(Player player) {this.player = player;}
	public void setWhiteCaptures(Vector<Piece> whiteCaptures) {this.whiteCaptures = whiteCaptures;}
	public void setBlackCaptures(Vector<Piece> blackCaptures) {this.blackCaptures = blackCaptures;}
	public void setMove(String[] move) {this.move = move;}
	public void setReview(Vector<String[]> review) {this.review = review;}
	public void setWKing(King wKing) {this.wKing = wKing;}
	public void setBKing(King bKing) {this.bKing = bKing;}
	public void setCheck(boolean check) {this.check = check;}
}
