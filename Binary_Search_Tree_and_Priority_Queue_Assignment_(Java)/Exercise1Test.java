//CE204 Assignment 1 - Exercise 1 Test
//Written by Alex Ebbage - 1504283

package assignment1;


public class Exercise1Test {

	public static void main(String[] args){
		System.out.println("EXERCISE 1 TEST");
		

		System.out.println("\nCreate a new priority queue:");
		PriorityQueue test = new PriorityQueue();
		System.out.println("Queue contents: " + test);

		System.out.println("\nCall isempty() on empty queue:");
		System.out.println(test.isempty());
		
		
		System.out.println("\nCall front() on empty queue:");
		try{
			System.out.println("Returns: " + test.front());
		}

		catch (QueueException e){
			System.out.println("ERROR: "+e);
		}
		
		
		System.out.println("\nCall frontpri() on empty queue:");
		try{
			System.out.println("Returns: " + test.frontpri());
		}

		catch (QueueException e){
			System.out.println("ERROR: "+e);
		}	
		
		
		System.out.println("\nCall deletefront() on empty queue:");
		try{
			test.deletefront();
			System.out.println("Queue contents: " + test);
		}

		catch (QueueException e){
			System.out.println("ERROR: "+e);
		}
		
		
		System.out.println("\nAdd 'Apple' with priority 0:");
		try{
			test.addtopq("Apple", 0);
			System.out.println("Queue contents: " + test);
		}

		catch (QueueException e){
			System.out.println("ERROR: "+e);
		}
		
		
		System.out.println("\nAdd 'Banana' with priority 21:");
		try{
			test.addtopq("Banana", 21);
			System.out.println("Queue contents: " + test);
		}

		catch (QueueException e){
			System.out.println("ERROR: "+e);
		}
		
		
		System.out.println("\nAdd 'Green' with priority 16:");
		test.addtopq("Green", 16);
		System.out.println("Queue contents: " + test);
		
		System.out.println("\nAdd 'Black' with priority 1:");
		test.addtopq("Black", 1);
		System.out.println("Queue contents: " + test);
		
		System.out.println("\nAdd 'Grey' with priority 2:");
		test.addtopq("Grey", 2);
		System.out.println("Queue contents: " + test);
		
		System.out.println("\nAdd 'Blue' with priority 14:");
		test.addtopq("Blue", 14);
		System.out.println("Queue contents: " + test);
		
		System.out.println("\nAdd 'Violet' with priority 8:");
		test.addtopq("Violet", 8);
		System.out.println("Queue contents: " + test);
		
		System.out.println("\nAdd 'Indigo' with priority 10:");
		test.addtopq("Indigo", 10);
		System.out.println("Queue contents: " + test);
		
		System.out.println("\nAdd 'White' with priority 5:");
		test.addtopq("White", 5);
		System.out.println("Queue contents: " + test);
		
		System.out.println("\nAdd 'Red' with priority 19:");
		test.addtopq("Red", 19);
		System.out.println("Queue contents: " + test);
		
		System.out.println("\nAdd 'Orange' with priority 19:");
		test.addtopq("Orange", 19);
		System.out.println("Queue contents: " + test);
		
		System.out.println("\nAdd 'Yellow' with priority 19:");
		test.addtopq("Yellow", 19);
		System.out.println("Queue contents: " + test);
		
		System.out.println("\nAdd 'Rooster' with priority 20:");
		test.addtopq("Rooster", 20);
		System.out.println("Queue contents: " + test);
		
		System.out.println("\nCall isempty():");
		System.out.println(test.isempty());
		
		System.out.println("\nCall front():");
		System.out.println("Returns: " + test.front());
		
		System.out.println("\nCall frontpri():");
		System.out.println("Returns: " + test.frontpri());
		
		System.out.println("\nCall deletefront():");
		test.deletefront();
		System.out.println("Queue contents: " + test);
		
		System.out.println("\nCall front():");
		System.out.println("Returns: " + test.front());
		
		System.out.println("\nCall frontpri():");
		System.out.println("Returns: " + test.frontpri());
	}
}
