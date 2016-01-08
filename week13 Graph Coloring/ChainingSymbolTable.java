

/**
 * BasicSymbolTable implementation using hash table with separate chaining.
 * @param <Key>
 * @param <Value>
 */
public class ChainingSymbolTable <Key, Value> implements BasicSymbolTable<Key, Value>
{
    public static final int REHASH_MIN_THRESHOLD = 2;
    public static final int REHASH_MAX_THRESHOLD = 8;
    public static final int INITIAL_M = 4;
    
    private EntryList<Key,Value>[] items;
    private int size;
    
    public ChainingSymbolTable()
    {
        this(INITIAL_M);
    }
    
    public ChainingSymbolTable(int initialM)
    {
        this.size = 0;
        this.items = (EntryList<Key,Value>[]) new EntryList[initialM];
        for (int i = 0; i < this.items.length; i++)
        {
            this.items[i] = new EntryList<Key,Value>();
        }
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
        return this.size == 0;
    }

    /**
     * Inserts the value into the table using specified key.  Overwrites
     * any previous value with specified value.
     * @param key
     * @param value
     * @throws NullPointerException if the key or value is null
     */
    public void put( Key key, Value value )
    {
        int hashIndex = this.hashFunction(key);
        if( this.get(key) == null ) this.size++;
        this.items[hashIndex].put(key, value);
        
        if( this.size > REHASH_MAX_THRESHOLD*this.items.length )
        {
            this.rehash(this.items.length*2);
        }
    }

    /**
     * Finds Value for the given Key.
     * @param key
     * @return value that key maps to or null if not found
     * @throws NullPointerException if the key is null
     */
    public Value get( Key key )
    {
        int hashIndex = this.hashFunction(key);
        return this.items[hashIndex].get(key);
    }
    
    /**
     * Determines if this symbol table contains the specified key.
     * @param key
     * @return true if key is in table, false otherwise
     */
    public boolean containsKey( Key key )
    {
        return this.get(key) != null;
    }

    /**
     * Removes the Value for the given Key from the table.
     * @param key
     * @return value that was removed or null if not found
     * @throws NullPointerException if the key is null
     */
    public Value delete( Key key )
    {
        int hashIndex = this.hashFunction(key);
        Value value = this.items[hashIndex].delete(key);
        if( value != null ) this.size--;
        if( this.items.length > INITIAL_M &&
            this.size < REHASH_MIN_THRESHOLD*this.items.length )
        {
            this.rehash( this.items.length / 2 );
        }
        return value; 
    }

    /**
     * Iterable that enumerates each key in the table.
     * @return iterable over the keys
     */
    public Iterable<Key> keys()
    {
        Queue<Key> keys = new Queue<Key>();
        for (EntryList<Key, Value> list : items)
        {
            for (Key key : list.keys())
            {
                keys.enqueue(key);
            }
        }
        return keys;
    }
    
    /**
     * Creates a new table (and thus new hash function), inserts previous
     * items using new hash function into the new table, sets new table.
     * @param newM 
     */
    private void rehash( int newM )
    {
        ChainingSymbolTable newTable = new ChainingSymbolTable(newM);
        for( Key key : this.keys() )
        {
            Value value = this.get(key);
            newTable.put(key, value);
        }
        this.items = newTable.items;
    }
    
    /**
     * Determines an index in [0,M-1] using specified key to generate hash code.
     * @param key with properly implemented hash code
     * @param M
     * @return index in [0,M-1]
     */
    private int hashFunction( Key key )
    {
        return (key.hashCode() & 0x7fffffff) % this.items.length;
    }
    
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
    
}
