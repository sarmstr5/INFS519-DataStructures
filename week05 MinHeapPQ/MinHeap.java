
import java.util.Iterator;

/**
 * @author Shane Armstrong
 * INFS 519
 * Fall 2015
 */

/**
 * An Implementation of a minimum binary heap using an array.  It must be keep two invariants: heap structure and min heap
 * So it must be a balanced, complete bindary tree, and the root must be the minimum number in the array.
 * It will read in a sequence of numbers and give the minimum value node
 * It runs reads, writes, and searches in lg(n) time
 */
public class MinHeap implements MinPQ
{
    public static final int DEFAULT_CAPACITY = 8;

    //instance variables
    int arrayTreeHeight;
    int numberOfNodes; 
    Comparable[] nodeArray;
    

    
    //invariants - heap structure and min heap 
    /*
     * Want the logical tree represented by the heap to be balanced
	 “complete binary tree” is a tree that is 
	completely filled, with the possible 
	exception of the bottom level, which is 
	filled from left to right and has no missing 
	nodes
     */
    
    /**
     * Default constructor sets the tree as null with a defualt capacity of 8
     */
    public MinHeap( )
    {
        arrayTreeHeight=3;
        numberOfNodes=1;
        nodeArray = new Comparable[DEFAULT_CAPACITY];
        
    }
    
    /**
     * Overloaded constructor that accepts a determined initial capacity for the tree
     * @param initialCapacity the initial capacity of the binary tree
     */
    public MinHeap( int initialCapacity )
    {
        //choosing to round down because if the size increases it will 
        //increase to perfect tree at that depth and not skipping to the next size
        arrayTreeHeight= (int) Math.floor(Math.log(initialCapacity)/Math.log(2)); 
        numberOfNodes=1;
        nodeArray = new Comparable[initialCapacity];
    }

    /**
     * Inserts the value at the next leaf in min heap, likely violating the heap order property.
     * The value then "swims" up through the tree until heap order property reestablish
     * @param item, the value to be inserted into the min heap
     */
    public void insert( Comparable item )
    {

    	checkGrow();
    	nodeArray[numberOfNodes] = item;
    	swim(numberOfNodes);
    	numberOfNodes++;
    }
    
    /**
     * Code was derived from week 5 class notes
     * Takes a node and checks it vs its parents then swaps if the heap order property is violated
     * Child node swims until a parent node is smaller than child node
     * @param index of node in array to swim up min heap
     */
    private void swim( int childNode )
    {
		// while parent is smaller than the child
    	int parentNode = childNode/2;
		 while( parentNode >= 1 && less(childNode, parentNode) )
		 {
			 // swap parent and child and move up tree
			 swap(parentNode,childNode);
			 childNode = parentNode;
			 parentNode = parentNode/2;
 
		 }
	}
         
    	
    	
    /**
     * The minimum root is returned
     * 
     * @return min root
     */
    public Comparable min()
    {
    	if(numberOfNodes>0)
    		return nodeArray[1]; // MODIFY
    	else
    		return null;
    }
    
    /**
     * The minimum root is deleted and returned. The most right leaf is swapped with the root and then sinks until 
     * the heap order propoerty is maintained
     * @return min root
     */
    public Comparable delMin()
    {
    	if(numberOfNodes<2){

			System.out.println("There are no nodes");
			return null;
    	}
    	Comparable min = nodeArray[1];
    	swap(1,--numberOfNodes);
    	nodeArray[numberOfNodes] = null;
    	//heap order property is currently violated
    	sink(1);
    	
        return min;
    }
    
    /**
     * Code derived week 5 class notes
     * The root is checked vs its children and the most minimum child is promoted
     */
    private void sink( int parentNode )
    {
       
			 while( 2*parentNode < numberOfNodes ) 
			 {

				 int childNode = 2*parentNode; 
				 if( childNode+1 < numberOfNodes && less(childNode+1,childNode) ) 
					 childNode++; // Now j points to smallest child
				 if( less(childNode, parentNode) == false) 
					 break;
				 
				 // Otherwise, swap and continue sink
				 swap(parentNode,childNode);
				 parentNode = childNode; // move down
			 }
        
    }    

    private boolean greater( int x, int y )
    {
    	if(numberOfNodes<=y){
    		boolean greater = (nodeArray[x].compareTo(nodeArray[y])>0) ? true: false; 
    		return greater; // MODIFY
    	}
    	else
    		return true;
    }
    private boolean less(int child, int parent){
    	if(numberOfNodes>=child){
	    	boolean less = (nodeArray[child].compareTo(nodeArray[parent])<0) ? true: false; 
	    	return less;
    	}
    	else
    		return true;
    }
    
    // y is smaller so swapping x for y
    private void swap( int parent, int child )
    {
        Comparable temp = nodeArray[parent];
    	nodeArray[parent] = nodeArray[child];
    	nodeArray[child] = temp;
    }

    /**
     * @return the size of heap
     */
    public int size()
    {
        return numberOfNodes-1; // MODIFY
    }

    /**
     * @return if emplty
     */
    public boolean isEmpty()
    {
        return numberOfNodes==1; // MODIFY
    }

    private void checkGrow()
    {

        if(numberOfNodes >= nodeArray.length){
        	Comparable[] temp = new Comparable[nodeArray.length];
        	System.arraycopy(nodeArray, 0, temp, 0, nodeArray.length);
        	
        	//increasing the tree array size by one more level
        	arrayTreeHeight++;
        	nodeArray = new Comparable[(int) Math.pow(2, arrayTreeHeight)];
        	System.arraycopy(temp, 0, nodeArray, 0, temp.length);
        	
        	temp = null;
        	
        }
    }
    
    
    /**
     * Returns Iterator that returns items in level order.  Does not support
     * remove.
     * @return iterator
     */
    public Iterator<Comparable> iterator()
    {
        return new Iterator<Comparable>(){

        	int currentNode = 1;
        	
			@Override
			public boolean hasNext() {
				if(currentNode<=numberOfNodes-1)
					return true;
				else
					return false;
			}

			@Override
			public Comparable next(){ 
				Comparable temp = nodeArray[currentNode];
				currentNode++;
				return temp;
			}
        	
			public void remove(){
				UnsupportedOperationException e = new UnsupportedOperationException("this method is not supported");
				throw e;
			}
        };
    }
    


    //--------------------- DO NOT MODIFY BELOW THIS -----------------------//

    @Override
    public String toString()
    {
        StringBuilder buf = new StringBuilder();
        boolean first = true;
        for (Comparable item : this)
        {
            if( first ) first = false;
            else buf.append(", ");
            
            buf.append( item.toString() );
        }
        return buf.toString();
    }
    
    /**
     * Unit tests the BinaryHeap data type.
     * @param args 
     */
    public static void main(String[] args)
    {
        Stdio.open( args[0] );
        MinPQ pq = new MinHeap();
        while( Stdio.hasNext() )
        {
            String method = Stdio.readString();
            if( method.equalsIgnoreCase("insert") )
            {
                int value = Stdio.readInt();
                pq.insert( value );
                Stdio.println( "insert="+pq.toString() );
            }
            else if( method.equalsIgnoreCase("delMin") )
            {
                Stdio.println( "delMin="+pq.delMin() );
            }
            else if( method.equalsIgnoreCase("size") )
            {
                Stdio.println( "size="+pq.size() );
            }
            else if( method.equalsIgnoreCase("min") )
            {
                Stdio.println( "min="+pq.min() );
            }
            else if( method.equalsIgnoreCase("empty") )
            {
                while( pq.isEmpty() == false )
                {
                    Stdio.println( "delMin="+pq.delMin() );
                }
            }
        }
        Stdio.println( "Final priority queue=" +pq.toString() );
        Stdio.close();
    }
}
