package utilities;

import java.awt.*;
import javax.swing.JFrame;

public class JEasyFrame extends JFrame{
	public Component comp;
	  public JEasyFrame(Component comp, String title) {
	    super(title);
	    this.comp = comp;
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
		  setUndecorated(true);
	    getContentPane().add(BorderLayout.CENTER, comp);
	    getContentPane().setBackground(new Color(0, 6, 30));
	    pack();
	    this.setVisible(true);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    repaint();
	  }
}
