
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Shane Armstrong
 * INFS 519
 * Fall 2015
 * PA3
 */

/**
 * Queue using Linked list derived from Professor Pope's INFS 519 lectures
 * Linked lists have start and ending points and are doubly linked
 * Iterator is also implemented through the QueueAPI class
 * Remove is unsupported, trying to use remove through the iterator throws an Unsupported Operation Exception
 * 
 */
public class Queue <Type> implements QueueAPI<Type>
{
    // Add Node inner class here
 
    
    // ADD CODE
    // instantiating first and last node element
    int numberOfNodes;
    Node frontOfQueue;
    Node backOfQueue;
    
    /**
     * Constructs an empty queue using linked lists
     */
    public Queue()
    {
    	//public Node(Type nodeData, Node next, Node Previous, int place)
    	numberOfNodes = 0;
        Node frontOfQueue = new Node(null, null, null, 1);
        Node backOfQueue = new Node(null, null, null, 2);
    }
    
    public int size()
    {
        return numberOfNodes; // MODIFY
    }
    
    public boolean isEmpty()
    {
    	if(numberOfNodes==0){
    		return true;
    	}
        return false; 
    }
    
    public void enqueue( Type item )
    {
        // ADD CODE
    	if(numberOfNodes == 0)
    	{
    		//create new node at the back of the queue pointing to back node and now 2nd to last node
    		Node newNode = new Node(item, backOfQueue, frontOfQueue);
    		
    		//updating the connections of previous nodes
    		frontOfQueue.setPrevious(newNode);
    		backOfQueue.setNext(newNode);
    		
    		numberOfNodes++;
    	}
    	else
    	{

    		Node newNode = new Node(item,backOfQueue, backOfQueue.next);
    		backOfQueue.next.setPrevious(newNode);
    		backOfQueue.setNext(newNode);
    		numberOfNodes++;
    	}
    	
    }
    
    public Type peek()
    {
    	
        return backOfQueue.previous.getData();  // MODIFY
    }
    
    public Type dequeue( )
    {
    	if(numberOfNodes>2)
    	{
    		//saving temp data from element in front of queue
	    	//Type tempData = frontOfQueue.getPrevious().getData();
	    	Node tempNode = frontOfQueue.previous;
	    	
	    	//node 2nd from front sets the front of Queue as next
	    	frontOfQueue.previous.previous.setNext(frontOfQueue);
	    	tempNode.setNext(null);
	    	
	    	//front of Queue is set to previous 2nd to front
	    	frontOfQueue.setPrevious(frontOfQueue.previous.previous);
	    	tempNode.setPrevious(null);
	    	
	    	numberOfNodes--;
	    	
	    	return tempNode.getData();
    	}
    	else if(numberOfNodes==1)
    	{

	    	Type tempData = frontOfQueue.previous.getData();
	    	
	    	//Since is only one node set all pointers to null
	    	frontOfQueue.previous.setNext(null);
	    	frontOfQueue.previous.setPrevious(null);
	    	frontOfQueue.setPrevious(null);
	    	
	    	numberOfNodes--;
	    	
	    	return tempData;
	    	
    	}
    	else 
    	{
    		System.out.println("No Nodes in Queue");
    		return null;
    	}

    }//dequeue
   
    public Iterator<Type> iterator()
    {
    	return new Iterator<Type>()
    	{
        	private int current = numberOfNodes;
        	private Node currentNode = frontOfQueue;
    		public boolean hasNext()
    		{
        		
    			if(current > 0)
    			{
        			return true;
        		}
        		else
        		{
        			return false;
        		}
        	}
        	public Type next()
        	{
        		
        		if(current<=0)
        		{
        			throw new java.util.NoSuchElementException("No more nodes to iterate through");
        		}
        		else
        		{
        			currentNode=currentNode.previous;
        			return currentNode.getData();
        		}
        	}
        	

        	
			public void remove(){
        		throw new UnsupportedOperationException("remove");
        	}//remove
        	
        };//anynomous iterator
        	
    }//Iterator class

    /**
     * 
     * @author Armstrs
     * Node class that populates the linked list queue
     */
    private class Node
    {
    	Type data;
    	Node next;
    	Node previous;
    	// 1 = starting spot, 2 = ending spot
    	int place;
    	
    	public Node(Type nodeData, Node previous,  Node next)
    	{
    		this.data = nodeData;
    		this.next = next;
    		this.previous = previous;
    		this.place = 0;
    	}

		public Node(Type nodeData, Node previous,  Node next, int position)
    	{
    		this.data = nodeData;
    		this.next = next;
    		this.previous = previous;
    		this.place = position;
    	}
    	public void setNext(Queue<Type>.Node newNode) 
    	{
			this.next = newNode;
			
		}
		public void setPrevious(Queue<Type>.Node newNode) 
		{
			this.previous = newNode;
			
		}
		public Node getPrevious()
		{
			return this.previous;
		}
		public Type getData()
		{
			return this.data;
		}
		public Node(Type nodeData, int position)
    	{
    		this.data = nodeData;
    		this.next = null;
    		this.place = position;
    	}
    }
    
    //--------------------- DO NOT MODIFY BELOW THIS -----------------------//
    
    @Override
    public String toString()
    {
        // Uses the iterator to build String
        StringBuilder buf = new StringBuilder();
        boolean first = true;
        buf.append("[");
        for (Type item : this)
        {
            if( first ) first = false;
            else buf.append( ", " );
            buf.append( item.toString() );
        }
        buf.append("]");
        return buf.toString();
    }
        
    /**
     * Unit tests the Queue data type.
     * @param args 
     */
    public static void main( String[] args )
    {
        Queue queue = new Queue();
        
        Stdio.open( args[0] );
        while( Stdio.hasNext() )
        {
            String operation = Stdio.readString();
            if( operation.equals("enqueue") )
            {
                String item = Stdio.readString();
                Stdio.println( "enqueue "+ item );
                queue.enqueue(item);
            }
            else if( operation.equals("dequeue") )
            {
                Stdio.println( "dequeue "+ queue.dequeue() );
            }
            else if( operation.equals("peek") )
            {
                Stdio.println( "peek "+ queue.peek() );
            }
            else if( operation.equals("size") )
            {
                Stdio.println( "size "+ queue.size() );
            }
        }
        
        Stdio.println( "Queue=" +queue.toString() );
        
        Stdio.close();
    }
}
