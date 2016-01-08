import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * BasicSymbolTable implementation using hash table with separate chaining.
 * @param <Key> represented by Product ID object
 * @param <Value> represented by Product
 */
public class ChainingSymbolTable <Key, Value> implements BasicSymbolTable<Key, Value>
{
    public static final int REHASH_MIN_THRESHOLD = 2;
    public static final int REHASH_MAX_THRESHOLD = 8;
    public static final int INITIAL_M = 4;
    
    private EntryList<Key, Value>[] items;
    private int size; //size = N, items.length=M
    
    public ChainingSymbolTable()
    {
        this(INITIAL_M);
    }
    
    public ChainingSymbolTable(int initialM)
    {
        this.items = new EntryList[initialM];
        size = 0;
        for (int i=0; i<initialM;i++)
        {
        	items[i] = new EntryList();
        }
    }
    
    /**
     * Gets the number of elements currently in the queue
     * @return number of elements in Symbol Table
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
        return this.size == 0;
    }

    /**
     * Inserts the value into the table using specified key.  Overwrites
     * any previous value with specified value.
     * @param Entry key 
     * @param Entry value
     * @throws NullPointerException if the key or value is null
     */
    public void put( Key key, Value value )
    {
    	//size = N, items.length=M
		//rehash to larger
    	
    	int hashIndex = hashFunction(key);
    	if(items[hashIndex].get(key)==null)
    		size++;

    	if(this.loadFactor()>REHASH_MAX_THRESHOLD)
    		rehash(this.getM()*2);

        items[hashIndex].put(key, value);
    }

    /**
     * Finds Value for the given Key.
     * @param Entry key
     * @return value that key maps to or null if not found
     * @throws NullPointerException if the key is null
     */
    public Value get( Key key )
    {
    	if(key  == null)
    		throw new NullPointerException("The key is null");
    	
        return items[hashFunction(key)].get(key); 
    }

    /**
     * Removes the Value for the given Key from the table.
     * @param key
     * @return value that was removed or null if not found
     * @throws NullPointerException if the key is null
     */
    public Value delete( Key key )
    {
    	if(key  == null)
    		throw new NullPointerException("The key is null");

    	Value v = items[hashFunction(key)].delete(key);
    	if(v == null)
    		return null;
    	
		//rehash to smaller
		size--;
    	double loadFactor = this.loadFactor();
    	if(loadFactor < REHASH_MIN_THRESHOLD && this.getM() > INITIAL_M )
    	{
    		int m = getM()/2;
    		m = (m>INITIAL_M) ? m : INITIAL_M;
    		rehash(m);
    	}    	
		return v;
    }

    /**
     * Iterable that enumerates each key in the table.
     * @return Queue that is iterable over the keys
     */
    public Iterable<Key> keys()
    {
    	Queue<Key> keys = new Queue<Key>();
    	for( int i = 0; i < this.items.length; i++ )
    	{
    		EntryList<Key, Value> list = this.items[i];
    		for( Key key : list.keys() )
    		{
    			keys.enqueue(key);
    		}
    	}
    	return keys;
    }
 /* possibly more efficient iterable method but harder to follow
    	Iterable<Key> iterable = new Iterable()
		{
			public Iterator<Key> iterator()
			{
    			return new Iterator()
				{
    				int position = 0;
    				int index = 0;
    				Iterator iter = items[index].keys().iterator();

					public boolean hasNext()
						return (position < size());	
						
					public Key next()
					{
						if(iter.hasNext())
						{
							position++;
							return	(Key) iter.next();
						}
						else
						{
							position++;
							index++;
							iter = items[index].keys().iterator();
							return (Key) iter.next();			
						}
					}		
					public void remove()
						throw new  UnsupportedOperationException("Not supported yet.");		
				};	
			}
		};
		return iterable;
	}
*/


    
    /**
     * Creates a new table (and thus new hash function), inserts previous
     * items using new hash function into the new table, sets new table.
     * @param new size of hash table, m
     */
    private void rehash( int newM )
    {
    	EntryList<Key, Value>[] tempHashTable = new EntryList[items.length];
    	System.arraycopy(items, 0, tempHashTable, 0, items.length );
    	items = new EntryList[newM];
    	     
    	int hashIndex;
    	Value value;
    	//add empty EntryList to each index
    	for(int i = 0; i<items.length; i++)
    		items[i]= new EntryList();
    	
    	//pull keys from temp and rehash keys to resized items array
    	for(int i = 0; i<tempHashTable.length;i++)
    	{
    		for(Key key: tempHashTable[i].keys())
    		{
            	hashIndex = hashFunction(key);
            	value = tempHashTable[i].get(key);
            	items[hashIndex].put(key, value);
    		}
    	}
    	tempHashTable = null;
    	
    }
    
    /**
     * Determines an index in [0,M-1] using specified key to generate hash code.
     * @param key with properly implemented hash code
     * @return index in [0,M-1]
     */
    private int hashFunction( Key key )
    {
    	int hashNum = key.hashCode();
    	int index = hashNum%this.getM();
        return index; 
    }

    //------------------ DO NOT MODIFY BELOW THIS LINE -------------------//
    
    /**
     * The expected length of the linked lists, i.e. the load factor.
     * @return N / M
     */
    public double loadFactor()
    {
        return this.getN() / (double)this.getM();
    }
    
    // Utility method
    public int getN()
    {
        return this.size;
    }
    
    // Utility method
    public int getM()
    {
        return this.items.length;
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
            
            buf.append( "\n" );
            
            buf.append( key );
            
            buf.append( "->" );
            buf.append( item.toString() );
        }
        buf.append("]");
        return buf.toString();
    }
    
    public String toTableString( boolean verbose )
    {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < this.items.length; i++)
        {
            String prefix = "\n["+i+"] size="+this.items[i].size()+":";
            buf.append( prefix );
            if(verbose)
            {
                boolean first = true;
                for( Key key : this.items[i].keys() )
                {
                    if( first ) first = false;
                    else buf.append( ", " );

                    buf.append( "(" );
                    buf.append( key );
                    buf.append( ")" );
                }
            }
        }
        return buf.toString();
    }
    
    
    
    /**
     * Unit tests the ST data type.
     * @param args 
     */
    public static void main(String[] args)
    {
        ChainingSymbolTable<ProductID,Product> st =
                new ChainingSymbolTable<ProductID,Product>();
        
        if( Stdio.isInteger(args[0]) )
        {
            int n = Integer.parseInt( args[0] );
            java.util.Random rand = new java.util.Random();
            Clock time = new Clock();
            for( int i = 0; i < n; i++ )
            {
                String university = "GMU"+rand.nextInt();
                int identifer     = rand.nextInt();
                String name       = "["+university+","+identifer+"]";
                int age           = 0;
                double grade      = 0.0;

                ProductID key = new ProductID(university, identifer);
                Product value = new Product( key, name, age, grade );
                
                st.put( key, value );

                if( st.get(key) == null ) throw new IllegalStateException("Put failed: "+key);
                if( i % 100000 == 0 ) Stdio.println("Put "+i);
            }
            Stdio.println( "Put "+n+ " items took " +time );
            Stdio.println( "Final symbol table=" +st.toTableString(false) );
            Stdio.printf(  "Load factor=%.2f\n", st.loadFactor() );
        }
        else
        {
            Stdio.open( args[0] );
            while( Stdio.hasNext() )
            {
                String method = Stdio.readString();
                if( method.equalsIgnoreCase("put") )
                {
                    String epc        = Stdio.readString();
                    int serialNumber  = Stdio.readInt();
                    String description= Stdio.readString();
                    int quantity      = Stdio.readInt();
                    double cost       = Stdio.readDouble();

                    ProductID key = new ProductID(epc, serialNumber);
                    Product value = new Product( key, description, quantity, cost );

                    st.put( key, value );
                    //Stdio.println( "put="+key.toString() );
                }
                else if( method.equalsIgnoreCase("delete") )
                {
                    String epc        = Stdio.readString();
                    int serialNumber  = Stdio.readInt();
                    ProductID key = new ProductID(epc, serialNumber);

                    Product deletedValue = st.delete(key);
                    //Stdio.println( "deleted="+deletedValue );
                }
                else if( method.equalsIgnoreCase("get") )
                {
                    String epc        = Stdio.readString();
                    int serialNumber  = Stdio.readInt();
                    ProductID key = new ProductID(epc, serialNumber);

                    Product value = st.get(key);
                    Stdio.println( "get="+value );
                }
                else if( method.equalsIgnoreCase("size") )
                {
                    Stdio.println( "size="+st.size() );
                }
                else if( method.equalsIgnoreCase("isEmpty") )
                {
                    Stdio.println( "isEmpty?="+st.isEmpty() );
                }
                else if( method.equalsIgnoreCase("toString") )
                {
                    Stdio.println( "toString?="+st.toTableString(true) );
                }
            }
            Stdio.println( "Final mappings=" +st.toString() );
            Stdio.println( "Final symbol table=" +st.toTableString(true) );
            Stdio.printf(  "Load factor=%.2f\n", st.loadFactor() );
            Stdio.close();
        }
    }
}
