package assignment;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


//Written by Alex Ebbage - 1504283 on 25/9/16
public class CE203_2016_Ex2 {
	
	//Main method for the application.
	public static void main(String[] args){
		Ex2_Window window = new Ex2_Window();
		window.setTitle("Exercise 2 ( Alex Ebbage - 1504283 )");
		window.setSize(460,460);
		window.setVisible(true);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}


class Ex2_Window extends JFrame{
	
	//All the buttons that will be used.
	JButton addWord, displayWords, countWord, removeFirst, removeAll, clear;
	//The text area for the output.
	JTextArea sysOut;
	//A text field to allow the user to input
	JTextField input;
	//An ArrayList to store all the words.
	ArrayList<String> wordList = new ArrayList<String>();

	//Constructor for Ex2_Window, creates the GUI.
	public Ex2_Window(){
		JPanel top = new JPanel();
		GridLayout buttonLayout = new GridLayout(3,2);
		top.setLayout(buttonLayout);
		addWord = new JButton("Add [x] to the list:");
		displayWords = new JButton("Display words beginning with [x]");
		countWord = new JButton("Count occurences of [x]");
		removeFirst = new JButton("Remove first occurence of [x]");
		removeAll = new JButton("Remove all occurences of [x]");
		clear = new JButton("Clear list of all words");
		addWord.addActionListener(new Ex2_ButtonHandler(this));
		displayWords.addActionListener(new Ex2_ButtonHandler(this));
		countWord.addActionListener(new Ex2_ButtonHandler(this));
		removeFirst.addActionListener(new Ex2_ButtonHandler(this));
		removeAll.addActionListener(new Ex2_ButtonHandler(this));
		clear.addActionListener(new Ex2_ButtonHandler(this));
		top.add(addWord);
		top.add(displayWords);
		top.add(countWord);
		top.add(removeFirst);
		top.add(removeAll);
		top.add(clear);

		JPanel mid = new JPanel();
		sysOut = new JTextArea(20, 40);
		sysOut.setEditable(false);
		sysOut.setLineWrap(true);
		sysOut.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(sysOut, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mid.add(scroll);
		
		JPanel bot = new JPanel();
		input = new JTextField("Input here then select an option...", 40);
		bot.add(input);

		add(top, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);
	}
}


class Ex2_ButtonHandler implements ActionListener{
	
	//Used to store a reference to the application window.
	Ex2_Window theApp;

	//Constructor for Ex2_ButtonHandler, takes the application window as an argument.
	Ex2_ButtonHandler(Ex2_Window app){
		theApp = app;
	}

	//actionPerformed method called when a button is pressed.
	public void actionPerformed(ActionEvent e){
		Object source = e.getSource();
		String input = theApp.input.getText(); 

		if(inputCheck(input)){
			//Code to handle adding a word.
			if (source == theApp.addWord){
				if(input.length() != 0){
					theApp.wordList.add(input);
					theApp.sysOut.append("Word '"+input+"' has been added to the list.\n");
				}
				else{
					theApp.sysOut.append("The input cannot be left empty for this option.\n");
				}
			}

			//Code to handle displaying all words starting with a certain character.
			if (source == theApp.displayWords){
				boolean wordFound = false;
				if(input.length() != 0){
					ArrayList<String> foundWords = new ArrayList<String>();
					if(!(input.length() > 1)){
						String inputLower = input.toLowerCase();
						char inputLetter = inputLower.charAt(0);
						String listWord;
						for(String word: theApp.wordList){
							listWord = word.toLowerCase();
							char firstLetter = listWord.charAt(0);
							if(inputLetter == firstLetter){
								wordFound = true;
								foundWords.add(word);
							}
						}
						if(!wordFound){
							theApp.sysOut.append("No words beginning with '"+input+"' found.\n");
						}
						else{
							theApp.sysOut.append("Words in the list starting with '"+input+"' are: \n");
							for(String word: foundWords){
								theApp.sysOut.append(word+"\n");
							}
						}
					}
					else{
						theApp.sysOut.append("'"+input+"' is an invalid input for this option.\n");
						theApp.sysOut.append("Input must be a single character.\n");
					}
				}
				else{
					theApp.sysOut.append("The input cannot be left empty for this option.\n");
				}
			}

			//Code to handle counting the occurrences of a particular word.
			if (source == theApp.countWord){
				int count = 0;
				int size = theApp.wordList.size();
				if(input.length() != 0){
					for(String word: theApp.wordList){
						if(input.equals(word)){
							count++;
						}
					}
					if(count > 0){
						theApp.sysOut.append("There are "+count+" occurences of the word '"+input+"'.\n");
					}
					else{
						theApp.sysOut.append("'"+input+"' does not occur in a list of "+size+" words.\n");	
					}
				}
				else{
					theApp.sysOut.append("There are "+size+" words in the list.\n");
				}

			}

			//Code to handle removing first occurrence of a particular word..
			if (source == theApp.removeFirst){
				if(input.length() != 0){
					boolean wordRemoved = false;
					for(String word: theApp.wordList){
						int index = theApp.wordList.indexOf(word);
						if(input.equals(word)){
							theApp.wordList.remove(index);
							theApp.sysOut.append("The first occurence of '"+input+"' has been removed.\n"); 			       
							wordRemoved = true;
							break;
						}
					}
					if(!wordRemoved){
						theApp.sysOut.append("Nothing was removed as '"+input+"' does not occur in the list.\n");
					}
				}
				else{
					theApp.sysOut.append("The input cannot be left empty for this option.\n");
				}
			}

			//Code to handle removing a particular word.
			if (source == theApp.removeAll){
				if(input.length() != 0){
					int count = 0;
					Iterator<String> iterator = theApp.wordList.iterator();
					while(iterator.hasNext()){
						if(input.equals(iterator.next())){
							iterator.remove();
							count++;
						}
					}
					if((count == 0) || (count > 1)){
						theApp.sysOut.append(count+" occurences of '"+input+"' have been removed.\n");
					}
					else{
						theApp.sysOut.append("1 occurence of '"+input+"' has been removed.\n");
					}
				}
				else{
					theApp.sysOut.append("The input cannot be left empty for this option.\n");
				}
			}
		}

		//Code to handle removing all words.
		if (source == theApp.clear){
			int size = theApp.wordList.size();
			theApp.wordList = new ArrayList<String>();
			theApp.sysOut.append("The list has been cleared of "+size+" words.\n");
		}

	}

	//Takes a string as an argument and checks it contains only letters and hyphens, and that the first character is a letter; displaying an error if failing.
	public boolean inputCheck(String input){
		boolean validInput = true;
		boolean startCheck = true;
		boolean characterCheck = true;
		String validChar = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ-";
		int inputLength = input.length();
		if(inputLength != 0){
			for(int i = 0; i < inputLength; i++){
				String letter = Character.toString(input.charAt(i));
				if(!validChar.contains(letter)){
					validInput = false;
					characterCheck = false;
				}
			}
			String letter = Character.toString(input.charAt(0));
			if("-".equals(letter)){
				validInput = false;
				startCheck = false;
			}
		}
		if(!validInput){
			if(!characterCheck){
				theApp.sysOut.append("The string '"+input+"' is invalid because it must only contain letters and hyphens.\n");
			}
			else if(!startCheck){
				theApp.sysOut.append("The string '"+input+"' is invalid because it must start with a letter.\n");
			}
		}
		return validInput;
	}
}