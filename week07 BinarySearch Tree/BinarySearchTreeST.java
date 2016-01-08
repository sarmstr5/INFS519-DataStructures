
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Binary Search Tree that implements SymbolTable.  
 * It does not balance so it is possible to have a degenerate tree with a runtime of O(n) as opposed to log(n)
 * It stores key/value pairs where keys map to unique values.  Duplicate keys have thier values overwritten
 * This implementation's nodes reference parents as well as children.  
 * @param <Key>
 * @param <Value>
 */
public class BinarySearchTreeST<Key extends Comparable, Value>
    implements OrderedSymbolTable<Key, Value>
{
	//BST attributes
	Node root;
	int numberOfNodes;
    
	//Constructor
   public BinarySearchTreeST(){
    	root = null;
    	numberOfNodes = 0;
    }
    
    /**
     * Gets the number of elements currently in the BST
     * @return number of elements in binary tree
     */
    public int size()
    {
        return numberOfNodes; // MODIFY CODE
    }
    
    /**
     * Determines if there are elements in the BST.
     * @return true if no elements, false otherwise
     */
    public boolean isEmpty()
    {
        if(numberOfNodes>0)
        	return false;
        else
        	return true; 
    }
    
    /**
     * Inserts the value into the table using specified key.  Overwrites
     * any previous value with specified value.
     * @param Comparable key of element
     * @param value of element
     * @throws NullPointerException if the key or value is null
     */
    public void put( Key key, Value value )
    {
        Node newNode = new Node(key, value);
    }
    
    /**
     * Finds Value for the given Key.
     * @param key, if not unique overwrites previous value
     * @return value that key maps to or null if not found
     * @throws NullPointerException if the key is null
     */
    public Value get(Key key )
    {
    	
    	NullPointerException e = new NullPointerException();
    	if(isEmpty()){
    		throw e;
    	}
    	
    	Node n = root;
    	boolean notFound= true;
    	while(notFound){
    		
    		//gone all the way down the tree
    		if(n== null)
    			notFound= false;
    		
    		//less, go left
    		if(less(key, n.key))
    			n= n.leftChild;
    		
    		//equal, return value
    		else if(equal(key, n.key))
    			return n.value;
    		
    		//greater, go right
    		else
    			n = n.rightChild;
    	}
    	throw e;
    }
    
    /**
     * Iterable that enumerates (in sorted order) each key in the table.
     * @return iterable key stored in dynamic array
     */
    public Iterable<Key> keys()
    {
		DynamicArray<Key> list = new DynamicArray<Key>(numberOfNodes);
		keys( root, list);
		return list;
    }
	   
    //Method that returns the nodes in order using recursion
	private void keys( Node node, DynamicArray<Key> list)
	{
		/* 
		go as far left as possible
		if no left child is available add it to the DynamicArray
		if there are right children continue down before going up the stack
		*/
		
	   if(node.leftChild != null)
		   keys(node.leftChild, list);
	   
	   
	   list.add(node.key);

	   
	   if(node.rightChild!= null)
		   keys(node.rightChild,list);
	   
	   //return up the stack
	   return;

		
	}
    

    /**
     * Finds and returns minimum key
     * @return smallest key in the BST
     * @throws NoSuchElementException if the BST is empty
     */
    public Key min() throws NoSuchElementException
    {
    	if(isEmpty())
    	{
    		NoSuchElementException e = new NoSuchElementException("The Binary Search Tree is empty");
    		throw e;
    	}
    	
    	else
		{	
    		Node n = getMin();
    		return n.key;
		}

    }
    
    /**
     * Finds and returns maximum key
     * @return largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key max() throws NoSuchElementException
    {
    	if(isEmpty())
    	{
    		NoSuchElementException e = new NoSuchElementException("The Binary Search Tree is empty");
    		throw e;
    	}
    	
    	else
		{	
    		Node n = getMax();
    		return n.key;
		}
    }
    
    /**
     * Removes the minimum key from the BST
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax( ) throws NoSuchElementException
    {
    	if(isEmpty())
    	{
    		NoSuchElementException e = new NoSuchElementException("The Binary Search Tree is empty");
    		throw e;
    	}
    	
    	else if(numberOfNodes==1){
    		root=null;
    		numberOfNodes--;
    	}
    	else
		{	
    		//holding variables 
    		Node nodeToRemove = getMax();
    		Node parent = nodeToRemove.parent;
    		Node lc = nodeToRemove.leftChild;
    		
    		//fixing links
    		parent.rightChild = lc;
    		if(lc!=null)
    			lc.parent = parent;
    		numberOfNodes--;
    		
		}
    }
    
    /**
     * Removes the maximum key from the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin( ) throws NoSuchElementException
    {
    	if(isEmpty())
    	{
    		NoSuchElementException e = new NoSuchElementException("The symbol table is empty");
    		throw e;
    	}
    	
    	else if(numberOfNodes==1){
    		root=null;
    		numberOfNodes--;
    	}
    	else
		{	
    		//holding the variables to make readable
    		Node nodeToRemove = getMin();
    		Node parent = nodeToRemove.parent;
    		Node rc = nodeToRemove.rightChild;
    		
    		//updating links
    		parent.leftChild = rc;
    		if(rc!=null)
    			rc.parent = parent;
    		numberOfNodes--;
    		
    		//garbage removal
    		
		}
    }
    
    private Node getMin(){
		Node n = root;
		while(n.leftChild != null)
		{
			n= n.leftChild;
		}
		return n;	
    }
    
    private Node getMax(){
		Node n = root;
		while(n.rightChild != null)
		{
			n= n.rightChild;
		}
		return n;	
    }
    
    
    /*
     * Node class used to create binary tree
     * 
     */
    private class Node{
    	
    	Key key;
    	Value value;
    	Node leftChild;
    	Node rightChild;
    	Node parent;
    	
    	Node(Key k, Value v)
    	{
	    	this.key = k;
	    	this.value = v;
	    	
	    	if(numberOfNodes ==0){
	        	Node leftChild=null;
	        	Node rightChild=null;
	        	Node parent=null;
	    		root = this;
	    		numberOfNodes++;
	    	}
	    	else
	    		insert(this, root);
	    	
    	}
    	

		private void insert(Node insertedNode, Node compareNode) {
			
			//need base case and recursive case
			
			boolean goLeft = less(insertedNode.key, compareNode.key);
			
			//continue down tree, go left
			if(goLeft){
				if(compareNode.leftChild == null){
					compareNode.leftChild = insertedNode;
					insertedNode.parent = compareNode;
					numberOfNodes++;
				}
				else
					insert(insertedNode,compareNode.leftChild);
			}
			
			// same key so replace
			else if(equal(insertedNode.key,compareNode.key))				
				compareNode.value = insertedNode.value;
			
			// key is greater than but no right child
			else if(compareNode.rightChild == null){
					compareNode.rightChild = insertedNode;
					insertedNode.parent = compareNode;
					numberOfNodes++;		
			}
			
			//continue down tree, go right
			else
				insert(this, compareNode.rightChild);
		}
		
    }
	private boolean equal(Comparable x, Comparable y) {
		boolean equals = (x.compareTo(y) ==0);
		return equals;
	}
	
	

	private boolean less(Comparable x, Comparable y){
		boolean lessThan = x.compareTo(y)<0;	
		return lessThan;
		
	}
    
    
    
    //--------------------- DO NOT MODIFY BELOW THIS -----------------------//

    @Override
    public String toString()
    {
        // Uses the iterator to build String
        StringBuilder buf = new StringBuilder();
        boolean first = true;
        buf.append("[");
        for (Key key : this.keys())
        {
            Value item = this.get(key);
            if( first ) first = false;
            else buf.append( ", " );
            buf.append( key );
            buf.append( "->" );
            buf.append( item.toString() );
        }
        buf.append("]");
        return buf.toString();
    }

    /**
     * Unit tests the ST data type.
     * @param args 
     */
    public static void main(String[] args)
    {
        Stdio.open( args[0] );
        BinarySearchTreeST<Integer,String> st = new BinarySearchTreeST<Integer,String>();
        while( Stdio.hasNext() )
        {
            String method = Stdio.readString();
            if( method.equalsIgnoreCase("insert") )
            {
                int key    = Stdio.readInt();
                String val = Stdio.readString();
                st.put( key, val );
                Stdio.println( "insert="+st.toString() );
            }
            else if( method.equalsIgnoreCase("deleteMin") )
            {
                st.deleteMin();
                Stdio.println( "deleteMin" );
            }
            else if( method.equalsIgnoreCase("deleteMax") )
            {
                st.deleteMax();
                Stdio.println( "deleteMax" );
            }
            else if( method.equalsIgnoreCase("size") )
            {
                Stdio.println( "size="+st.size() );
            }
            else if( method.equalsIgnoreCase("min") )
            {
                Stdio.println( "min="+st.min() );
            }
            else if( method.equalsIgnoreCase("max") )
            {
                Stdio.println( "max="+st.max() );
            }
            else if( method.equalsIgnoreCase("isEmpty") )
            {
                Stdio.println( "isEmpty?="+st.isEmpty() );
            }
        }
        Stdio.println( "Final symbol table=" +st.toString() );
        Stdio.close();
    }
}

