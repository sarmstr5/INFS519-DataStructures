
import java.util.NoSuchElementException;

/**
 * Balanced BST symbol table.
 * It searches and inserts in guaranteed lg(n) time
 * Code was used and recycled from INFS 519 
 * @param <Key>
 * @param <Value>
 */
public class AVLTreeST<Key extends Comparable, Value>
    implements OrderedSymbolTable<Key, Value>
{   

	

	private Node put( Node node, Key key, Value value )
    {
		//node is the root or leaf
        if( node == null )
        {
            node = new Node(key, value);
            this.size++;
            node.height = 0;  //do i need this?
            return node;
        }
        
        // go left
        else if( key.compareTo( node.key ) < 0 )
        {
            node.left = put( node.left,  key, value );       
          
            //left side is longer than right, level should not differ by more than one
            //case 1: left subtree left child, single rotate
            //case 2: left subtree right child, double rotate
            if(height(node.left)-height(node.right)==2){
            	
            	if(key.compareTo(node.left.key)<0)
            		node = rotateWithLeftChild(node); 
            	
            	else
            		node= doubleRotateLeft(node);			
            } 
        }
        
        //go right
        else if( key.compareTo( node.key ) > 0 )
        {
            node.right = put( node.right, key, value );
            
            //right side is longer than left
            //case 3: right subtree left child, double rotate
        	//case 4: right subtree right child, single rotate
            if(height(node.right)-height(node.left)==2){
            	
            	if(key.compareTo(node.right.key)<0)
            		node= doubleRotateRight(node);
        
            	else 
            		node = rotateWithRightChild(node);		
            }   
        }
        
        //same key overwrite
        else
        {
            node.value = value;
        }
        
        //update height of node to height of largest child plus one
        node.height = subtreeHeight(node);
   
        return node;
    }
    
	/*
	 * Any BST can be collapsed to bottom
	 * pick up k1 and k2 , and let gravity take effect. K becomes subtree root and k2 drops to right of k1
	 * have to move subtree B to k2 left child
	 * previously subtree B held items between k1 and k2
	 * after rotation subtree B remains between k1 and k2 maintaining order property
	 */
    private Node rotateWithLeftChild( Node k2 )
    {
        // left subtree of left child, rotate right
    	Node k1 = k2.left;
    	k2.left = k1.right;
    	k1.right = k2;
    	
    	// update heights of nodes
    	k1.height = subtreeHeight(k1);
    	k2.height = subtreeHeight(k2);
    	
        return k1;
    }
    
    private Node rotateWithRightChild( Node k2 )
    {
        // right subtree of right child, rotate left
    	Node k1 = k2.right;
    	k2.right = k1.left;
    	k1.left = k2;
    	
    	// update heights of nodes
    	k1.height = subtreeHeight(k1);
    	k2.height = subtreeHeight(k2);
    	
        return k1;
    	
    }
    
    /*
     * Know either/both subtrees b and c is two levels deeper than d
     * Rotation between x's child and grandchild
     * Rotation between x and its new child
     * subtree b remains between k1 and k2
     * subtree C remains between k2 and k3
     */
    private Node doubleRotateLeft( Node k3 )
    {

    	Node k1 = k3.left;
    	Node k2 = k1.right;
    	    	
        // rotate 1
    	k3.left = k2;
    	k1.right = (k2.left != null) ? k2.left : k2.right;
    	
    	//rotate 2
    	k2.left = k1;
    	k3.left = k2.right;	
    	k2.right = k3;

    	
    	//update heights
    	k1.height = subtreeHeight(k1);
    	k3.height = subtreeHeight(k3);
    	k2.height = subtreeHeight(k2);
    	
        return k2;
    }
    
    private Node doubleRotateRight( Node k3 )
    {
    	Node k1 = k3.right;
    	Node k2 = k1.left;
    	    	
        // rotate 1
    	k3.right = k2;
    	k1.left = (k2.right != null) ? k2.right : k2.left;
    	
    	//rotate 2
    	k2.right = k1;
    	k3.right = k2.left;	
    	k2.left = k3;

    	
    	//update heights
    	k1.height = subtreeHeight(k1);
    	k3.height = subtreeHeight(k3);
    	k2.height = subtreeHeight(k2);
    	
        return k2;
    }


    private int subtreeHeight(Node node){
    	int height = ((height(node.left) >= height(node.right)) ? height(node.left): height(node.right))+1;
    	return height;
    }
    //--------------------- DO NOT MODIFY BELOW THIS -----------------------//

    private class Node
    {
        private Key key;
        private Value value;
        
        private Node left;
        private Node right;
        
        private int height;
        
        public Node( Key key, Value value )
        {
            this.key   = key;
            this.value = value;
        }
       
        @Override public String toString(){
        	return "key = "+this.key+", height = "+this.height;
        }
        
    }
    
    private Node root;
    private int size;
    
    /**
     * Creates a new search tree with default parameters.
     */
    public AVLTreeST()
    {
        this.size = 0;
    }


    private int max( int h1, int h2 )
    {
        return Math.max(h1, h2);
    }
    
    private int height( Node x )
    {
        return ( x == null ) ? -1 : x.height;
    }

    /**
     * Gets the number of elements currently in the queue
     * @return size
     */
    public int size()
    {
        return this.size;
    }
    
    /**
     * Determines if there are not elements in the queue.
     * @return true is no elements, false otherwise
     */
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    /**
     * Inserts the value into the table using specified key.  Overwrites
     * any previous value with specified value.
     * @param key
     * @param value
     * @throws NullPointerException if key or value is null
     */
    public void put( Key key, Value value )
    {
        this.root = this.put(this.root, key, value);
    }
    
    /**
     * Finds Value for the given Key.
     * @param key
     * @return value that key maps to or null if not found
     */
    public Value get( Key key )
    {
        Node node = this.find(key, this.root);
        return (node!=null)?node.value:null;
    }
    
    /**
     * Recursive function that finds the Node with specified Key or null.
     * @param key
     * @param node
     * @return node for key or null if not found
     */
    private Node find( Key key, Node node )
    {
        if(      node == null )         return null;
        else
        {
            // Do we go left or right?
            int cmp = key.compareTo(node.key);
            if(      cmp < 0 ) return find( key, node.left  );
            else if( cmp > 0 ) return find( key, node.right );
            else     return node;
        }
    }
    
    
    
    public Iterable<Key> keys()
    {
        List<Key> list = new DynamicArray<Key>();
        this.keys(root, list);
        return list;
    }
    
    private void keys( Node x, List list )
    {
        if( x == null ) return;
        this.keys(x.left, list);
        list.add(x.key);
        this.keys(x.right, list);
    }
    
    private void checkEmpty()
    {
        if( this.size == 0 )
        {
            throw new NoSuchElementException("SymbolTable is empty");
        }
    }
    
    public Key min() throws NoSuchElementException
    {
        checkEmpty();
        return this.min(this.root).key;
    }
    
    private Node min( Node t )
    {
        if( t != null )
        {
            while( t.left != null ) t = t.left;
        }
        return t;
    }

    public Key max() throws NoSuchElementException
    {
        checkEmpty();
        return this.max(this.root).key;
    }
    
    private Node max( Node t )
    {
        if( t != null )
        {
            while( t.right != null ) t = t.right;
        }
        return t;
    }
    
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
    
    public boolean balanced( )
    {
        if( root == null ) throw new IllegalStateException("Expect root to be non-null");
        return this.balanced(root);
    }
    
    private boolean balanced( Node x )
    {
        if( x.left == null && x.right == null )
        {
            // leaf, height should be zero
            if( x.height != 0 ) return false;
        }
        
        // Check to see if our left and right subtrees are balanced
        boolean lb = (x.left != null) ? balanced(x.left)  : true;
        boolean rb = (x.right!= null) ? balanced(x.right) : true;
        
        if( lb == false || rb == false ) return false;
        
        // If subtrees are balanced, is our height correct
        // Non-leaf, should be one more than left/right height
        int lh = (x.left  != null) ? x.left.height  : -1;
        int rh = (x.right != null) ? x.right.height : -1;
            
        int expectedHeight = max(lh, rh) + 1;
            
        if( x.height != expectedHeight ) return false;
        
        // Everything up to here makes sure the height is correct
        // This final check makes sure balanced condition is met
        int diff = lh - rh;
        if( Math.abs(diff) >= 2 ) return false;
        
        return true;
    }
    
    public static boolean isInteger( String s )
    {
        try
        {
            Integer.parseInt(s);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }
    
    /**
     * Unit tests the ST data type.
     * @param args 
     */
    public static void main(String[] args)
    {
        AVLTreeST<Integer,String> st = new AVLTreeST<Integer,String>();
        
        if( isInteger(args[0]) )
        {
            int n = Integer.parseInt( args[0] );
            java.util.Random rand = new java.util.Random();
            Clock time = new Clock();
            for( int i = 0; i < n; i++ )
            {
                //int key      = rand.nextInt();
                int key      = i;
                
                String value = "value"+key;
                st.put( key, value );

                if( st.get(key) == null ) throw new IllegalStateException("Put failed: "+key);
            }
            Stdio.println( "Put "+n+ " items took " +time );
            Stdio.println( "Balanced? " +st.balanced() );
        }
        else
        {
            Stdio.open( args[0] );
            while( Stdio.hasNext() )
            {
                String method = Stdio.readString();
                if( method.equalsIgnoreCase("put") )
                {
                    int key    = Stdio.readInt();
                    String val = Stdio.readString();
                    st.put( key, val );
                    //Stdio.println( "put="+st.toString() );
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
            Stdio.println( "Balanced? " +st.balanced() );
            Stdio.close();
        }
    }
}

