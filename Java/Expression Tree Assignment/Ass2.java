//ASSIGNMENT 2
//Written by Alex Ebbage - 1504283.
//Last updated 15/3/17.

package assignment2;

public class Ass2 {
	
	public static void main(String[] args) {

		//Name and introductory message.
		System.out.println("Alex Ebbage - 1504283");
		System.out.println("Hello, this is my expression parsing program.");
		
		//Parser for input.
		Parser p = new Parser();
		
		//Boolean to control loop.
		boolean isLooping = true;
		
		//Input for continuing the loop or not.
		String input;
		
		do {
			//Input an expression.
			System.out.println("\nInput an expression:");
				try{
					ExpTree myTree = p.parseLine();
					System.out.println("\nPre-Order:");
					myTree.preOrderOutput(myTree);
					System.out.println("\n\nIn-Order:\n" + myTree);
					System.out.println("\nEvaluation:\n"+myTree.evaluate(myTree));
				}
				catch(ParseException e){
					System.out.println("\nERROR: " + e);
				}
				
				//Asks if the user wants to continue.
				System.out.println("\nInput 'exit' to finish or anything else to continue.");
				input = p.getLine();
				if(input.equals("exit")){
					System.out.println("\nGoodbye!");
					isLooping = false;
				}
			}
		while(isLooping);
	}
}