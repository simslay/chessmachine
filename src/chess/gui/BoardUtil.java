package chess.gui;

public class BoardUtil {
	/** coordonnees de depart des images des pieces **/
    /* pions */
    public static int xWhitePawn(int i) {return Board.getOffset()+i*60;}
    public static int yWhitePawn() {return Board.getOffset()+6*60;}
    public static int xBlackPawn(int i) {return Board.getOffset()+java.lang.Math.abs(i-7)*60;}
    public static int yBlackPawn() {return Board.getOffset()+60;}
    /* tours */
    public static int xWhiteRook1() {return Board.getOffset();}
    public static int yWhiteRook1() {return Board.getOffset()+7*60;}
    public static int xWhiteRook2() {return Board.getOffset()+7*60;}
    public static int yWhiteRook2() {return Board.getOffset()+7*60;}
    public static int xBlackRook1() {return Board.getOffset();}
    public static int yBlackRook1() {return Board.getOffset();}
    public static int xBlackRook2() {return Board.getOffset()+7*60;}
    public static int yBlackRook2() {return Board.getOffset();}
    /* cavaliers */
    public static int xWhiteKnight1() {return Board.getOffset()+60;}
    public static int yWhiteKnight1() {return Board.getOffset()+7*60;}
    public static int xWhiteKnight2() {return Board.getOffset()+6*60;}
    public static int yWhiteKnight2() {return Board.getOffset()+7*60;}
    public static int xBlackKnight1() {return Board.getOffset()+60;}
    public static int yBlackKnight1() {return Board.getOffset();}
    public static int xBlackKnight2() {return Board.getOffset()+6*60;}
    public static int yBlackKnight2() {return Board.getOffset();}
    /* fous */
    public static int xWhiteBishop1() {return Board.getOffset()+2*60;}
    public static int yWhiteBishop1() {return Board.getOffset()+7*60;}
    public static int xWhiteBishop2() {return Board.getOffset()+5*60;}
    public static int yWhiteBishop2() {return Board.getOffset()+7*60;}
    public static int xBlackBishop1() {return Board.getOffset()+5*60;}
    public static int yBlackBishop1() {return Board.getOffset();}
    public static int xBlackBishop2() {return Board.getOffset()+2*60;}
    public static int yBlackBishop2() {return Board.getOffset();}
    /* reines */
    public static int xQueen() {return Board.getOffset()+3*60;}
    public static int yWhiteQueen() {return Board.getOffset()+7*60;}
    public static int yBlackQueen() {return Board.getOffset();}
    /* rois */
    public static int xKing() {return Board.getOffset()+4*60;}
    public static int yWhiteKing() {return Board.getOffset()+7*60;}
    public static int yBlackKing() {return Board.getOffset();}
}
