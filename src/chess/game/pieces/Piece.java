package chess.game.pieces;

import chess.game.players.Player;

public abstract class Piece {
	protected int x;
	protected int y;
	protected int color;
	protected String nom;
	
	protected boolean enPassantRisk=false;
	protected boolean castlingPoss=true;
	protected boolean promoted=false;
	
	public Piece () {}
	
	public Piece (String nom, int color) {
		this.nom = nom;
		this.color = color;
	}
	
	/* constructeur par recopie */
	public Piece (Piece p) {
		super();
		
		this.x=p.x;
		this.y=p.y;
		this.color=p.color;
		this.nom=p.nom;
		
		this.enPassantRisk=p.enPassantRisk;
		this.castlingPoss=p.castlingPoss;
		this.promoted=p.promoted;
	}

	public abstract boolean isValidMove(int x1, int y1, int x2, int y2, Piece[][] board, Player player, boolean check);
	
	public String toString() {
		String s="";
		
		s+=x+" "+y+"\n";
		s+=color == Player.WHITE ? "white" : "black";
		s+="\n"+nom;
		s+="\nen passant risk: "+enPassantRisk;
		s+="\n";
		
		return s;
	}

	/* getters */
	public int getX() {return x;}
	public int getY() {return y;}
	public int getColor() {return color;}
	public String getNom() {return nom;}
	
	public boolean isEnPassantRisk() {return enPassantRisk;}
	public boolean isCastlingPoss() {return castlingPoss;}
	public boolean isPromoted() {return promoted;}
	/* setters */
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	public void setColor(int color) {this.color = color;}
	public void setNom(String nom) {this.nom = nom;}
	
	public void setEnPassantRisk(boolean enPassantRisk) {this.enPassantRisk = enPassantRisk;}
	public void setCastlingPoss(boolean castlingPoss) {this.castlingPoss = castlingPoss;}
	public void setPromoted(boolean promoted) {this.promoted = promoted;}
}
