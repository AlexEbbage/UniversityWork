//CE204 Assignment 1 - Priority Queue
//Written by Alex Ebbage - 1504283

package assignment1;


//Object containing a string for data and int for priorty.
//Used in PriorityQueue to represent each item.
class QueueItem{
	private String data;
	private int priority;

	
	//Constructor for the QueueItem.
	//Takes a String for data and int for priority.
	public QueueItem(String d, int p){
		this.data = d;
		this.priority = p;
	}
	
	
	//Gets the data for this object.
	public String getData(){
		return data;
	}
	
	
	//Gets the priority for this object.
	public int getPriority(){
		return priority;
	}
}


//Exception to be thrown by PriorityQueue.
class QueueException extends RuntimeException{ 

	//Constructor for the exception.
	//Takes a string as an argument.
	public QueueException(String s){
		super(s);
	}
}


//PriorityQueue class.
public class PriorityQueue{
	//Stores an array of QueueItem.
	private QueueItem[] queue;

	
	//Constructor for PriorityQueue.
	//Takes no arguments, creates an empty array.
	PriorityQueue(){
		queue = new QueueItem[0];
	}
	

	//Outputs the entire queue's contents, showing the data and priority of each item.
	public String toString(){
		StringBuffer output = new StringBuffer("<");
		
		for(int i = 0; i < queue.length; i ++){
			String data = queue[i].getData();
			String priority = "" + queue[i].getPriority();
		
			output.append("'"+data+"':"+priority);
			
			if(i < queue.length - 1){
				output.append(", ");
			}
		}
		return(output+">");
	}


	//Returns true if the queue is empty.
	public boolean isempty(){
		return (queue.length == 0);
	}


	//Returns the data of the element at the front of the queue.
	//Throws an exception if the queue is empty.
	public String front(){
		if(isempty()) throw new QueueException("Tried to apply front() to empty queue.");

		return queue[0].getData();
	}


	//Deletes the element at the front of the queue by creating a new array starting from the 2nd element,
	//then overwriting the previous array.
	//Throws an exception if the queue is empty.
	public void deletefront(){
		if(isempty()) throw new QueueException("Tried to apply deletefront() to empty queue.");

		QueueItem[] temp = new QueueItem[queue.length-1];
		for(int i = 1; i < queue.length; i++){
			temp[i-1] = queue[i];
		}
		queue = temp;
	}

	
	//Returns the priority of the element at the front of the queue.
	//Throws an exception if the queue is empty.
	public int frontpri(){
		if(isempty()) throw new QueueException("Tried to apply frontpri() to empty queue.");

		return queue[0].getPriority();
	}


	//Adds an item to the queue.
	//Takes a string representing the data and an int representing the priority as arguments.
	public void addtopq(String item, int priority){
		//Throws an exception if the priority is out of the 1-20 range.
		if((priority < 1) || (priority > 20)) throw new QueueException("Tried to apply addtopq('"+item+"', "+priority+") with priority outside range 1-20.");

		//Creates a temporary array with a size increase of 1.
		QueueItem[] temp = new QueueItem[queue.length+1];
		boolean itemAdded = false;	

		//If the queue is empty make the first item equal a queueItem with the input values.
		if(queue.length == 0){
			temp[0] = new QueueItem(item, priority);
		}
		
		//Else go through the elements of the current queue.
		else{
			for(int i = 0; i < queue.length; i++){
				
				//If their priority it greater than or equal to the input priority, add the current item to the temp array.
				if(queue[i].getPriority() >= priority){
					temp[i] = queue[i];
				}
				
				//Else if the priority is less, add the new queueItem, followed by the rest of the elements from the current array.
				else{
					if(!itemAdded){
						temp[i] = new QueueItem(item, priority);
						itemAdded = true;
						i--;
					}
					else{
						temp[i+1] = queue[i];
					}
				}
				
				//If not item was added, then put the new queueItem on the end of the temp array.
				if(!itemAdded){
					temp[i+1] =  new QueueItem(item, priority);
				}
			}
		}
		
		//Replace the current queue with the temp one.
		queue = temp;
	}


}
