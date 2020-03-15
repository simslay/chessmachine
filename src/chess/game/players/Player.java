package chess.game.players;

import chess.game.Game;
import chess.game.UndefinedPlayerException;

public abstract class Player {
	/* constantes */
	public final static int HUMAN=Game.HUMAN;
	public final static int ARTIFICIAL=Game.ARTIFICIAL;
	public final static int WHITE=Game.WHITE;
	public final static int BLACK=Game.BLACK;
	public final static int HORIZON1 = 0;
	public final static int ALPHABETA = 1;
	
	protected int type;
	protected int color;
	protected String nom;
	
	public static Fevals feval;
	public static int mode;
	public static int PMAX;
	public enum Fevals {
		wFeval("wFeval"), bFeval("bFeval"),
		wAlea("wAlea"), bAlea("bAlea"),
		wCaptures("wCaptures"), bCaptures("bCaptures"),
		wStalemate("wStalemate"), bStalemate("bStalemate"),
		wCheckmate("wCheckmate"), bCheckmate("bCheckmate");
	
		protected String label;
		Fevals(String pLabel) {this.label = pLabel;}
		public String getLabel() {return this.label;}
	}
	
	abstract public int[][] enterMove(Game game, boolean check) throws UndefinedPlayerException;
	
	/* getters */
	public int getType() {return type;}
	public int getColor() {return color;}
	public String getNom() {return nom;}
	/* setters */
	public void seType(int type) {this.type = type;}
	public void setColor(int color) {this.color = color;}
	public void setNom(String nom) {this.nom = nom;}
}
