package chess.gui.optional;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;

public class CaptureFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Container content;
	private JPanel capturePanel;
	
	public CaptureFrame (Point location) {
		super("Captures");
		Toolkit tk = Toolkit.getDefaultToolkit();
	    Dimension dim = tk.getScreenSize();
	    int width=dim.width/6, height=dim.height/6;
	    
	    setContent(this.getContentPane());
	    content.setBackground(new Color(150, 150, 150));
	    
	    capturePanel=new JPanel();
	    capturePanel.setSize(new Dimension(width, height));
	    //capturePanel.setHorizontalAlignment(JPanel.CENTER);
	    capturePanel.setFont(new Font("Helvetica", Font.BOLD, dim.width/60));
	    capturePanel.setForeground(Color.BLACK);
	    content.add(capturePanel, BorderLayout.CENTER);
	    
	    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(width, height));
		this.setResizable(false);
		this.setLocation(location.x+510, location.y+18*dim.height/80+10);
		try {
        	this.setIconImage(ImageIO.read(getClass().getResource("../images_gui/vide.jpg")));
        } catch (IOException e) {
        	e.printStackTrace();
        }
		this.setVisible(true);
	}
	
	/* getters */
	public Container getContent() {return content;}
	public JPanel getCapturePanel() {return capturePanel;}
	/* setters */
	public void setContent(Container content) {this.content = content;}
	public void setCapturePanel(JPanel capturePanel) {this.capturePanel = capturePanel;}
	
}
