package chess.gui.optional;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;

public class PositionFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Container content;
	private JLabel positionLabel;
	
	public PositionFrame (Point location) {
		super("Position");
		Toolkit tk = Toolkit.getDefaultToolkit();
	    Dimension dim = tk.getScreenSize();
	    int width=dim.width/8, height=dim.height/8;
	    
	    setContent(this.getContentPane());
	    content.setBackground(new Color(150, 150, 150));
	    
	    positionLabel=new JLabel();
	    positionLabel.setSize(new Dimension(width, height));
	    positionLabel.setHorizontalAlignment(JLabel.CENTER);
	    positionLabel.setFont(new Font("Helvetica", Font.BOLD, 30));
	    positionLabel.setForeground(new Color(100, 255, 100));
	    content.add(positionLabel, BorderLayout.CENTER);
	    
	    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(width, height));
		this.setResizable(false);
		this.setLocation(location.x+510, location.y+dim.height/10+5);
		try {
        	this.setIconImage(ImageIO.read(getClass().getResource("../images_gui/vide.jpg")));
        } catch (IOException e) {
        	e.printStackTrace();
        }
		this.setVisible(true);
	}

	/* getters */
	public Container getContent() {return content;}
	public JLabel getPositionLabel() {return positionLabel;}
	/* setters */
	public void setContent(Container content) {this.content = content;}
	public void setPositionLabel(JLabel positionLabel) {this.positionLabel = positionLabel;}
	
}
