package ass;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BelishaBeacon {
	private Timer timer;
	private int windowWidth = 400;	
	int boxHeight = 30;
	int boxWidth = 14;
	int ovalHeight = 100;
	int ovalWidth = 100;
	int yStartPos = 50;
	int yOffset = yStartPos;
	int xOffset = (windowWidth/2) - (boxWidth/2) - 8;
	int xOffsetOval = (windowWidth/2) - (ovalWidth/2) - 8;	
	private int windowHeight = 2*yStartPos + 10*boxHeight + ovalHeight + 30 + 40;

	public class Drawing extends JPanel {

		private boolean changeColors = true;

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
					
			g.setColor(Color.orange);
		    g.fillOval(xOffsetOval, yStartPos, ovalWidth, ovalHeight);
		    g.drawOval(xOffsetOval, yStartPos, ovalWidth, ovalHeight);
			yOffset = yStartPos + ovalHeight;
			
			for(int i = 0; i < 5; i++){
				g.setColor(Color.black);
				g.fillRect(xOffset, yOffset, boxWidth, boxHeight);
				g.drawRect(xOffset, yOffset, boxWidth, boxHeight);
				yOffset += boxHeight;
				
				g.setColor(Color.white);
				g.fillRect(xOffset, yOffset, boxWidth, boxHeight);
				g.setColor(Color.black);
				g.drawRect(xOffset, yOffset, boxWidth, boxHeight);
				yOffset += boxHeight;
			}

			changeColors = !changeColors;

			if (changeColors) {
			    g.setColor(Color.darkGray);
			    g.fillOval(xOffsetOval, yStartPos, ovalWidth, ovalHeight);
			    g.drawOval(xOffsetOval, yStartPos, ovalWidth, ovalHeight);
			}
		}

		public void changeColors() {
			changeColors = true;
			repaint();
		}
	}

	public BelishaBeacon() {
	    //Creation of frame
	    JFrame frame = new JFrame();
	    frame.setSize(windowWidth, windowHeight);
	    frame.setTitle("Belisha Beacon");
	    frame.setLayout(new BorderLayout(0, 0));
	    final Drawing beacon = new Drawing();
		
	    timer = new Timer(500, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	beacon.repaint();
	        }
	    });

	    JButton flash = new JButton("Flash");
	    flash.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            timer.start(); 
	        }
	    });
	    
	    final JButton steady = new JButton("Steady");
	    steady.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		beacon.changeColors();
	    		timer.stop();
	    	}
	    });

		//Positioning
		JPanel window = new JPanel();
		window.setLayout(new GridLayout(1, 2, 0, 0));
		window.setPreferredSize(new Dimension(windowWidth/2, 30));
		window.add(flash);
		window.add(steady);

		frame.add(window, BorderLayout.SOUTH);
		frame.add(beacon);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	    timer.start(); 
	}

	public static void main(String[] args) {
		new BelishaBeacon();
	}
}

