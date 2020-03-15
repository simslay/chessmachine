package chess.gui;

import java.awt.Image;

public class PieceImage {
	private Image img;
	private int x;
	private int y;
	private boolean selected;
	private String nom;
	private int color;
	
	public PieceImage (Image img, String nom, int color) {
		this.img = img;
		this.nom = nom;
		this.color = color;
		selected = false;
	}

	/* accesseurs */
	public Image getImage() {return img;}
	public int getX() {return x;}
	public int getY() {return y;}
	public boolean isSelected() {return selected;}
	public String getNom() {return nom;}
	public int getColor() {return color;}
	/* modifieurs */
	public void setImage(Image img) {this.img=img;}
	public void setX(int x) {this.x=x;}
	public void setY(int y) {this.y=y;}
	public void setSelected(boolean b) {this.selected=b;}
	public void setNom(String nom) {this.nom = nom;}
	public void setColor(int color) {this.color = color;}
}
