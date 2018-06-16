//ASSIGNMENT 2
//Written by Alex Ebbage - 1504283.
//Last updated 15/3/17.

package assignment2;

import java.util.HashMap;
import java.util.Map.Entry;

public abstract class ExpTree {

	//The hash map used to store identities, is maps a string to it's representative value.
	protected static HashMap<Character, Integer> identities;

	//The left and right children of the current node.
	protected ExpTree leftChild, rightChild;

	//Returns the left child of the current node.
	//@RETURNS: ExpTree - A pointer to the left node.
	public ExpTree leftChild(){ return leftChild;}

	//Returns the right child of the current node.
	//@RETURNS: ExpTree - A pointer to the right node.
	public ExpTree rightChild(){ return rightChild;}


	//Is recursively called to go through the tree using pre-order traversal, whilst printing the value
	//of each node.
	//@PARAMETERS: ExpTree tree - Used to pass the next node in the tree recursively.
	public void preOrderOutput(ExpTree tree){
		if(tree instanceof WhereTree)
			preOrderOutput(tree.leftChild());

		else if(tree != null){			
			if(tree instanceof NumberLeaf)
				System.out.print(((NumberLeaf)tree).value() + " ");
			else if(tree instanceof IdentityLeaf)
				System.out.print((char)((IdentityLeaf)tree).value() + " ");
			else
				System.out.print(((OperatorNode)tree).value() + " ");

			preOrderOutput(tree.leftChild());
			preOrderOutput(tree.rightChild());
		}
	}
	
	
	//Used to output the tree in-order with correct brackets and identities if any.
	//@RETURNS: String - The string produced by the contents of the tree.
	public String toString(){
		StringBuffer sb = new StringBuffer();
		boolean[] firstOperators = {true, true, true, true, true};
		
		if(this instanceof WhereTree){
			inOrderOutput(this.leftChild(), 's', sb, firstOperators);
			if(identities != null){
				sb.append(" where ");
				int i = 0;
				for(Entry<Character, Integer> entry : identities.entrySet()) {
					i++;
				    sb.append(entry.getKey()+" = "+entry.getValue());
				    if(i < identities.size())
				    	sb.append(" and ");
				    else
				    	sb.append(";");
				}
			}
		}
		else{
			identities = new HashMap<Character, Integer>();
			inOrderOutput(this, 's', sb, firstOperators);
		}

		return sb.toString();
	}


	//Used by toString to recursively go through the tree, inserting brackets into the correct locations.
	//@PARAMETERS: ExpTree tree - Used to recursively call the next node in the tree.
	//@PARAMETERS: char prevOp - Used to hold the previous operator in the tree.
	//@PARAMETERS: StringBuffer sb - Used to hold the StringBuffer, allowing it to be updated.
	//@PARAMETERS: boolean[] firstOps - Stores booleans which remove unnecessary brackets.
	public void inOrderOutput(ExpTree tree, char prevOp, StringBuffer sb, boolean[] firstOps){
		if(tree != null){
			if(tree instanceof OperatorNode){
				char op = ((OperatorNode)tree).value();
				boolean start = prevOp != 's';
				boolean minus = (op == '-' && (prevOp == '-'|| prevOp == '+'|| prevOp == '*'|| prevOp == '/'|| prevOp == '%') && firstOps[0]);
				boolean plus = (op == '+' && (prevOp == '+'|| prevOp == '*'|| prevOp == '/'|| prevOp == '%') && firstOps[1]);
				boolean multiply = ((op == '*' && (prevOp == '*'|| prevOp == '/'|| prevOp == '%')) && firstOps[2]);
				boolean divide = (op == '/' && (prevOp == '/' || prevOp == '%') && firstOps[3]);
				boolean modulus = (op == '%' && (prevOp == '%') && firstOps[4]);
				
				if(start && (plus || minus || multiply || divide || modulus)){
					sb.append("(");				
					inOrder(tree, op, sb, firstOps);
					sb.append(")");
				}
				else
					inOrder(tree, op, sb, firstOps);
			}
			else
				inOrder(tree, prevOp, sb, firstOps);
		}
	}


	//Used in the inOrderOutput method, determines the value of the firstOps booleans and outputs the trees contents.
	//@PARAMETERS: ExpTree tree - Used to recursively call the next node in the tree.
	//@PARAMETERS: char op - Used to hold the current operator in the tree.
	//@PARAMETERS: StringBuffer sb - Used to hold the StringBuffer, allowing it to be updated.
	//@PARAMETERS: boolean[] firstOps - Stores booleans which remove unnecessary brackets.
	public void inOrder(ExpTree tree, char op, StringBuffer sb, boolean[] firstOps){
		if(op == '-') firstOps[0] = false;
		if(op == '+') firstOps[1] = false;
		if(op == '*') firstOps[2] = false;
		if(op == '/') firstOps[3] = false;
		if(op == '%') firstOps[4] = false;
		inOrderOutput(tree.leftChild(), op, sb, firstOps);

		if(tree instanceof NumberLeaf)
			sb.append(((NumberLeaf)tree).value() + "");
		else if(tree instanceof IdentityLeaf)
			sb.append(((char)((IdentityLeaf)tree).value()) + "");
		else
			sb.append(((OperatorNode)tree).value() + "");
		
		if(op == '-') firstOps[0] = true;
		if(op == '+') firstOps[1] = true;
		if(op == '*') firstOps[2] = true;
		if(op == '/') firstOps[3] = true;
		if(op == '%') firstOps[4] = true;
		inOrderOutput(tree.rightChild(), op, sb, firstOps);
	}


	//Recursively goes over the identity branch of the tree and stores all the values in a map.
	//@PARAMETERS: ExpTree tree - Used to recursively go through each node of the tree.
	public void identify(ExpTree tree){
		if(tree != null){
			if(tree instanceof EqualsNode)
				identities.put(((EqualsNode) tree).value, evaluate(tree.leftChild()));
			
			identify(tree.leftChild());
			identify(tree.rightChild());
		}
	}


	//Recursively goes through the tree and evaluates the expression.
	//@PARAMETERS: ExpTree tree - Used to recursively go through each node of the tree.
	public int evaluate(ExpTree tree){
		int total = 0;

		if(tree instanceof WhereTree)
			tree = tree.leftChild();

		if(tree instanceof OperatorNode){
			switch(((OperatorNode)tree).value()){
			case '+':
				total += evaluate(tree.leftChild()) + evaluate(tree.rightChild());
				break;
			case '-':
				total += evaluate(tree.leftChild()) - evaluate(tree.rightChild());
				break;
			case '*':
				total += evaluate(tree.leftChild()) * evaluate(tree.rightChild());
				break;
			case '/':
				total += evaluate(tree.leftChild()) / evaluate(tree.rightChild());
				break;
			case '%':
				total += evaluate(tree.leftChild()) % evaluate(tree.rightChild());
				break;				
			}
		}
		else{
			if(tree instanceof NumberLeaf)
				total += ((NumberLeaf)tree).value();
			else{
				char leafValue = ((IdentityLeaf)tree).value();
				if(identities.size() == 0)
					total += ('z'-leafValue);
				
				else{
					if(identities.get(leafValue) != null)
						total += identities.get(leafValue);
					else
						System.out.println("No value has been given for '"+leafValue+"', so it's assumed to be 0.");				
				}
			}
		}

		return total;
	}

}

class NumberLeaf extends ExpTree{
	private int value;

	//Constructor for the number node that will hold a number value.
	//@PARAMETERS: int value - The value the node will hold.
	public NumberLeaf(int value){
		leftChild = rightChild = null;
		this.value = value;
	}

	//Returns the value stored by the node.
	//@Returns: int - The value held by the node.
	public int value(){ return value;}
}


class IdentityLeaf extends ExpTree{
	private char value;

	//Constructor for the identity node that will hold a identity value.
	//@PARAMETERS: char value - The value the node will hold.
	public IdentityLeaf(char value){
		leftChild = rightChild = null;
		this.value = value;
	}

	//Returns the identity stored by the node.
	//@Returns: char - The identity held by the node.
	public char value(){ return value;}
}


class OperatorNode extends ExpTree{
	private char value;

	//Constructor for the operator node that will hold an operator and 2 child nodes.
	//@PARAMETERS: char value - The operator the node will hold.
	//@PARAMETERS: ExpTree leftChild - A pointer to the left node.
	//@PARAMETERS: ExpTree rightChild - A pointer to the right node.
	public OperatorNode(char value, ExpTree leftChild, ExpTree rightChild){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.value = value;
	}

	//Returns the operator stored by the node.
	//@Returns: char - The operator held by the node.
	public char value(){ return value;}
}


class WhereTree extends ExpTree{

	//Constructor for the where node that will an expression on the left and identities on the right.
	//@PARAMETERS: ExpTree leftChild - A pointer to the left expression branch.
	//@PARAMETERS: ExpTree rightChild - A pointer to the right identity branch.
	public WhereTree(ExpTree leftChild, ExpTree rightChild){
		identities = new HashMap<Character, Integer>();
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		identify(this.rightChild());
	}
}


class AndNode extends ExpTree{

	//Constructor for the and node that can hold an equals node or an and node.
	//@PARAMETERS: ExpTree leftChild - A pointer to the left node.
	//@PARAMETERS: ExpTree rightChild - A pointer to the right node.
	public AndNode(ExpTree leftChild, ExpTree rightChild){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}
}


class EqualsNode extends ExpTree{
	char value;

	//Constructor for the equals node that will hold a character and it's value on the leftChild.
	//@PARAMETERS: char value - The character the node will hold.
	//@PARAMETERS: ExpTree leftChild - A number node that will hold the identities value.
	public EqualsNode(char value, ExpTree leftChild){
		this.leftChild = leftChild;
		this.rightChild = null;
		this.value = value;
	}
}