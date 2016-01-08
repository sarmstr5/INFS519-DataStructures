import java.io.IOException;

/**
 * @author Shane Armstrong
 * INFS 519
 * Fall 2015
 */

/**
 * Dynamic array that grows and shrinks as needed as taught in INFS 519 lecture two
 */
public class DynamicArray <Type> implements List<Type>
{
    public static final int DEFAULT_INIT_CAPACITY = 2;
    private Object[] array= null;  // array of objects casted to Type
    private int size;			  // number of elements used in array
    private int capacity;
    
    /**
     * Java compiler complains regarding 
     * @param void
     * @return DynamicArray
     */
    public DynamicArray()
    {
        this.array = (Type[]) new Object[DEFAULT_INIT_CAPACITY];
        this.size = 0;
        this.capacity = DEFAULT_INIT_CAPACITY;
    }
    /**
     * Java compiler complains regarding 
     * @param void
     * @return DynamicArray
     */
    public DynamicArray(int i, int n)
    {
        this.array = (Type[]) new Object[i];
        this.size = n;  
    }    
    /**
     * Gets the item at index i
     * @param i
     * @return item at i
     * @throws IndexOutOfBoundsException if i is not in range
     */
    public Type get(int i)
    {
        try{
        	Type element = (Type) this.array[i];
        	return element;
        }
        catch(IndexOutOfBoundsException e){
        	System.err.println("The index was out of bounds " +e.getMessage());
        	return null;
        }

    }
    
    /**
     * Overwrites the item at position i with specified item
     * @param i
     * @param item
     * @throws IndexOutOfBoundsException if i is not in range
     * @throws NullPointerException is item is null
     */ 
    public void set(int i, Type item) throws IndexOutOfBoundsException, NullPointerException
    {
        try
        {
        	this.array[i] = item;
        }
        catch(Exception e)
        {
        	System.err.println("The index was out of bounds " +e.getMessage());
        }
    }

    /**
     * Appends item to the end of the list.
     * @param item
     * @throws NullPointerException if item is null
     */
    public void add(Type item)
    {
    	try{

            if(capacity<=size){
            	this.capacity*=2;
            	this.array = DynamicArray.reSize(this.capacity, this.size, this.array);
            }
            this.array[size++]=item;
	            	
    	}
    	catch(NullPointerException e){  //need to 
    		System.err.println(e.getMessage());
    	}
    	

    }

    /**
     * Removes item at index from the list.
     * @param i
     * @return item at i
     * @throws IndexOutOfBoundsException if i is not in range
     */
    public Type remove(int indexRemoved)
    {
    	
        try{
        	if(this.size == 0){
        		return null;
        	}
        	else if(capacity<=size){
            	this.capacity*=2;
            	this.array = DynamicArray.reSize(this.capacity, this.size, this.array);
            }
        	Type removedElement = (Type) this.array[indexRemoved];
        	// Shift items to left of i
        	for(int i = indexRemoved; i<this.size;i++){
            	this.array[i] = this.array[i+1];
        	}
        	
        	//decreasing size and changing last element to null
        	this.array[--size]=null;
        	if(4*size<capacity){
            	this.array=DynamicArray.Trim(this.array, this.capacity, this.size);
            	this.capacity/=2;
        	}
        	return removedElement;
        }
        
        catch(IndexOutOfBoundsException e){
        	System.err.println(e.getMessage());
        	
        }
        return null;
    }
    
    /**
     * Shrink the size the array 
     * @param array2
     * @param capacity
     * @param size
     * @return newArr
     */
    private static Object[] Trim(Object[] array2, int capacity, int size) {
    	Object[] newArr =  new Object[capacity/2];
    	for(int i = 0; i<size;i++){
        	newArr[i]=array2[i];
    	}
		return newArr;
	}
	/**
     * Inserts item at index into the list at index i.
     * @param i
     * @param item
     * @throws IndexOutOfBoundsException if i is not in range
     * @throws NullPointerException is item is null
     */
    public void insert(int iInsert, Type item) throws IndexOutOfBoundsException, NullPointerException
    {
        try{
        	//checkpreconditions
        	//shift items to right
        	// set i to item 
        	// increment size
        	// Shift items to left of i
        	if(capacity<=size){
            	this.capacity*=2;
            	this.array = DynamicArray.reSize(this.capacity, this.size, this.array);
            }
        	
        	Object[] newArr =  new Object[capacity];
        	for(int i = 0; i<iInsert;i++){
            		newArr[i] = this.array[i];
        	}
        	newArr[iInsert]= item;
        	for(int i = iInsert; i<this.size;i++){
        		newArr[i+1] = this.array[i];
        	}        	
        	this.size++;
        	
        }
        catch(Exception e){
        	System.err.println(e);
        }
    }
    
    /**
     * Gets the size of the list (number of items).
     * @return size
     */
    public int size()
    {
        return size;
    }
    
    /**
     * Searches an array for value
     * @param o
     * @return index
     */
    private int search(Type o){
    	for (int i = 0; i<this.size ; i++){
    		if(this.array[i]==o){
    			return i;
    		}
    	}
    	return -1;
    }
    /**
     * Resizes the array to a new capacity and size 
     * @return Object[]
     * @param capacity
     * @param size
     * @param array
     */
    private static <T> Object[] reSize(int capacity, int size, Object[] array){
    	Object[] newArr =  new Object[capacity];
    	for(int i = 0; i<size;i++){
        	newArr[i]=array[i];
    	}
    	return newArr;

    }


    //--------------------- DO NOT MODIFY BELOW THIS -----------------------//
    @Override
    public String toString()
    {
        StringBuilder buf = new StringBuilder();
        //buf.append("cap="+this.items.length+"[");
        buf.append("[");
        for( int i = 0; i < this.size(); i++ )
        {
            Type item = this.get(i);
            if( i != 0 ) buf.append( ", " );
            buf.append( item.toString() );
        }
        buf.append("]");
        return buf.toString();
    }


    public static void main(String[] args) throws IOException
    {
        DynamicArray<String> list = new DynamicArray<String>();
        System.out.println(args[0]);
        Stdio.open( args[0] );
        Stdio.generate( "operations.txt", 10000, 12 );
        while( Stdio.hasNext() )
        {
            String method = Stdio.readString();
            if( method.equals("add") )
            {
                String param1 = Stdio.readString();
                list.add( param1 );
            }
            else if( method.equals("get") )
            {
                int index = Stdio.readInt();
                Stdio.println( list.get(index) );
            }
            else if( method.equals("size") )
            {
                Stdio.println( list.size() );
            }
            else if( method.equals("remove") )
            {
                int index = Stdio.readInt();
                Stdio.println( list.remove(index) );
            }
        }
        Stdio.println( "" );
        Stdio.println( "Final list=" +list.toString() );
        Stdio.close();
    }

}
