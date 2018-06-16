package assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Written by Alex Ebbage - 1504283 on 25/9/16
public class CE203_2016_Ex1{
	
	public static void main(String[] args){
		Ex1_Window window = new Ex1_Window();
		window.setTitle("Exercise 1 ( Alex Ebbage - 1504283 )");
		window.setSize(360,150);
		window.setVisible(true);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
	

class Ex1_Window extends JFrame{
	
	//All text fields used for input.
	JTextField inR, inG, inB;
	//Both buttons for confirming and resetting input.
	JButton confirm, reset;
	//2 labels to allow for neater output.
	JLabel outA, outB;
	//The color used for the text.
	Color outputColor = Color.RED;
	
	//Constructor for Ex1_Window, creates the GUI.
	public Ex1_Window(){
		JPanel top = new JPanel();
		reset = new JButton("Reset");
		reset.addActionListener(new Ex1_ButtonHandler(this));
		top.add(reset);

		JPanel mid = new JPanel();
		BoxLayout layout = new BoxLayout(mid, BoxLayout.Y_AXIS);
		mid.setLayout(layout);
		outA = new JLabel("CE203 Assignment 1, submitted by:");
		outB = new JLabel("Alex Ebbage - 1504283");
		outA.setAlignmentX(Component.CENTER_ALIGNMENT);
		outB.setAlignmentX(Component.CENTER_ALIGNMENT);
		outA.setForeground(outputColor);
		outB.setForeground(outputColor);
		mid.add(outA);
		mid.add(outB);

		JPanel bot = new JPanel();
		inR = new JTextField(7);
		inG = new JTextField(7);
		inB = new JTextField(7);
		confirm = new JButton("Confirm");
		confirm.addActionListener(new Ex1_ButtonHandler(this));
		bot.add(inR);
		bot.add(inG);
		bot.add(inB);
		bot.add(confirm);
		
		add(top, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);
		
	}
}


class Ex1_ButtonHandler implements ActionListener{

	//Used to store a reference to the application window.
	Ex1_Window theApp;

	//Constructor for Ex1_ButtonHandler, takes the application window as an argument.
	Ex1_ButtonHandler(Ex1_Window app){
		theApp = app;
	}

	//actionPerformed method called when a button is pressed.
	public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        boolean exceptionThrown = false;
		theApp.outA.setText("CE203 Assignment 1, submitted by:");
		theApp.outB.setText("Alex Ebbage - 1504283");
		String exceptionMessageA = "Only integers must be entered.";
		String exceptionMessageB = "Please try again.";
        
		//Code for confirming an input, contains error-checking for each input value.
        if (source == theApp.confirm) {
	            String inRString = theApp.inR.getText();            
	            String inGString = theApp.inG.getText();
	            String inBString = theApp.inB.getText();
	            int inRInt = 0;
	            int inGInt = 0;
	            int inBInt = 0;
	            
	            try{
	            	inRInt = Integer.parseInt(inRString); 
		            if(inRInt > 255){
		            	inRInt = 255;
		            	theApp.inR.setText("255");
		            }
		            if(inRInt < 0){
			            inRInt = 200;
		            	theApp.inR.setText("200");
		            }
		        }
	            catch(Exception ex){
	            	theApp.inR.setText("");
	            	theApp.outA.setText(exceptionMessageA);
	            	theApp.outB.setText(exceptionMessageB);
	            	exceptionThrown = true;
	            	System.out.println("Exception: " + ex);	
	            }
	            
	            try{
	            	inGInt = Integer.parseInt(inGString); 
		            if(inGInt > 255){
		            	inGInt = 255;
		            	theApp.inG.setText("255");
		            }
		            if(inGInt < 0){
			            inGInt = 200;
		            	theApp.inG.setText("200");
		            }
	            }
	            catch(Exception ex){
	            	theApp.inG.setText("");
	            	theApp.outA.setText(exceptionMessageA);
	            	theApp.outB.setText(exceptionMessageB);
	            	exceptionThrown = true;
	            	System.out.println("Exception: " + ex);	
	            }
	            
	            try{
	            	inBInt = Integer.parseInt(inBString); 
		            if(inBInt > 255){
		            	inBInt = 255;
		            	theApp.inB.setText("255");
		            }
		            if(inBInt < 0){
			            inBInt = 200;
		            	theApp.inB.setText("200");
		            }
	            }
	            catch(Exception ex){
	            	theApp.inB.setText("");
	            	theApp.outA.setText(exceptionMessageA);
	            	theApp.outB.setText(exceptionMessageB);
	            	exceptionThrown = true;
	            }
	            
	            if(!exceptionThrown){
		            theApp.outputColor = new Color(inRInt,inGInt,inBInt);
		            theApp.outA.setText("Alex Ebbage");
		            theApp.outB.setText("1504283");
	            }
        }
     
        //Code for reset button.
        if ( source == theApp.reset){
        	theApp.inR.setText("");
        	theApp.inG.setText("");
        	theApp.inB.setText("");
        	theApp.outputColor = Color.RED;
        }
        
        theApp.outA.setForeground(theApp.outputColor);
        theApp.outB.setForeground(theApp.outputColor);
	}
}


