//CE204 Assignment 1 - Exercise 2 BST
//Written by Alex Ebbage - 1504283

package assignment1;

import java.util.NoSuchElementException;

public class BST{
	
	private BTNode<Integer> root;

	
	public BST(){
		root = null;
	}

	
	public boolean find(Integer i){
		BTNode<Integer> n = root;
		boolean found = false;

		while (n!=null && !found){
			int comp = i.compareTo(n.data);
			if (comp==0){
				found = true;
			}
			else if (comp<0){
				n = n.left;
			}
			else{
				n = n.right;
			}
		}

		return found;
	}

	
	public boolean insert(Integer i){
		BTNode<Integer> parent = root, child = root;
		boolean goneLeft = false;

		while (child!=null && i.compareTo(child.data)!=0){
			parent = child;
			if (i.compareTo(child.data)<0){
				child = child.left;
				goneLeft = true;
			}
			else{
				child = child.right;
				goneLeft = false;
			}
		}

		if (child!=null){
			return false;  // number already present
		}
		else{
			BTNode<Integer> leaf = new BTNode<Integer>(i);

			if (parent==null){ // tree was empty
				root = leaf;
			}
			else if (goneLeft){
				parent.left = leaf;
			}
			else{
				parent.right = leaf;
			}
			return true;
		}
	}
	
	
	//EXERCISE 2	
	//Recursively counts and then returns the amount of nodes down a branch, starting from the given node.
	private int nodeCount(BTNode node){
		if(node == null){
			return 0;
		}
		else{
			return 1 + nodeCount(node.left) + nodeCount(node.right);
		}
	}	

	
	//Returns how many numbers greater than n appear in the tree.
	public int greater(int n){
		int count = 0;
		Integer input = n;
		BTNode<Integer> currentNode = root;

		//Returns 0 if tree is empty.
		if(root == null) return 0;

		//Keep going until tree branch reaches end
		while(currentNode != null){
			//Compares the input to the current nodes data.
			int compareTo = input.compareTo(currentNode.data);
			
			//If n < currentNodes data, add the branch size to the right, add one to count itself, then go left.
			if(compareTo == -1){
				count++;
				count += nodeCount(currentNode.right);
				currentNode = currentNode.left;
			}

			//If n == currentNodes data, add the branch size to the right and stop.
			else if(compareTo == 0){
				count += nodeCount(currentNode.right);
				break;
			}

			//If n > currentNodes data, go right.
			else{
				currentNode = currentNode.right;
			}
		}

		return count;
	}

	
	
	//Return the element at location n if the contents of the tree were stored in an ascending order array.
	public int nth(int n){
		int treeSize = nodeCount(root);
		BTNode<Integer> currentNode = root;
		int count = -1;
		
		//Throw an exception if the input is less than 0, or greater then or equal to the tree size.
		if((treeSize <= n) || (n < 0)) throw new NoSuchElementException("Tried to call nth(" + n + "), but no such element exists.");
		
		//While the count doesn't equal the required position.
		while(count != n){
			
			//If the sum of the count, the left branch and the current node is greater than the input, and the left branch isn't empty, go left.
			if((count+nodeCount(currentNode.left)+1 > n) && ((nodeCount(currentNode.left) != 0))){
				currentNode = currentNode.left;
			}
			
			//Else increase the count by 1, plus the size of the left branch, breaking the loop if the count reaches the input, otherwise go right.
			else{
				count++;
				count += nodeCount(currentNode.left);
				if(count == n){
					break;
				}
				currentNode = currentNode.right;
			}
		}
		
		//Return the data of the node at that inputted position.
		return currentNode.data;
	}
}


class BTNode<T>{
	T data;
	BTNode<T> left, right;

	BTNode(T o){
		data = o; left = right = null;
	}
}
