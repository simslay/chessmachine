package chess.gui;

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

public class MessageFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Container content;
	private JLabel messageLabel;
	
	public MessageFrame (Point location) {
		super("Messages");
		Toolkit tk = Toolkit.getDefaultToolkit();
	    Dimension dim = tk.getScreenSize();
	    int width=dim.width/5, height=dim.height/10;
	    
	    setContent(this.getContentPane());
	    content.setBackground(new Color(230, 230, 230));
	    
	    messageLabel=new JLabel();
	    messageLabel.setSize(new Dimension(width, height));
	    messageLabel.setHorizontalAlignment(JLabel.CENTER);
	    messageLabel.setFont(new Font("Helvetica", Font.BOLD, dim.width/60));
	    messageLabel.setForeground(new Color(255, 0, 0));
	    content.add(messageLabel, BorderLayout.CENTER);
	    
	    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(width, height));
		this.setResizable(false);
		this.setLocation(location.x+510, location.y);
		try {
        	this.setIconImage(ImageIO.read(getClass().getResource("/vide.jpg")));
        } catch (IOException e) {
        	e.printStackTrace();
        }
		this.setVisible(true);
	}

	/* getters */
	public Container getContent() {return content;}
	public JLabel getMessageLabel() {return messageLabel;}
	/* setters */
	public void setContent(Container content) {this.content = content;}
	public void setMessageLabel(JLabel messageLabel) {this.messageLabel = messageLabel;}
	
}
