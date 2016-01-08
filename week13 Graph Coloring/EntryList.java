
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * SymbolTable implementation that holds key/value pairs in a linked list.  
 * @param <Key>
 * @param <Value>
 */
public class EntryList <Key, Value> implements BasicSymbolTable <Key, Value>
{
    private Entry front;
    private int size; // not strictly necessary, useful for debugging
    
    public EntryList()
    {
        this.size  = 0;
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
     * @return true if no elements, false otherwise
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
        this.checkKey(key);
        this.checkValue(value);
        
        // Make sure does not already exist
        Entry entry = this.findEntry(key);
        
        // Initial linked list case
        if( entry == null )
        {
            // Push onto the front
            entry = new Entry(key, value, this.front);
            this.front = entry;
            this.size++;
        }
        else
        {
            // Update the value
            entry.value = value;
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
        this.checkKey(key);
        
        Value value = null;
        
        Entry entry = this.findEntry(key);
        if( entry != null ) value = entry.value;
        
        return value;
    }

    /**
     * Removes the Value for the given Key from the table.
     * @param key
     * @return value that was removed or null if not found
     * @throws NullPointerException if the key is null
     */
    public Value delete( Key key )
    {
        this.checkKey(key);
        
        if( this.front == null ) return null;
        
        Value value = null;
        if( this.front.key.equals(key) )
        {
            value = front.value;
            this.front = front.next;
        }
        else if( this.front.next != null )
        {
            Entry entry = this.front;
            while( entry.next != null )
            {
                if( entry.next.key.equals(key) )
                {
                    value = entry.next.value;
                    entry.next = entry.next.next;
                    break;
                }
                entry = entry.next;
            }
        }
        
        if(value != null) this.size--;
        
        return value;
    }

    /**
     * Iterable that enumerates each key in the table.
     * @return iter
     */
    public Iterable<Key> keys()
    {
        Iterable<Key> iterable = new Iterable()
        {
            public Iterator<Key> iterator()
            {
                return new Iterator()
                {
                    private Entry entry = front;
                    
                    public boolean hasNext()
                    {
                        return entry != null;
                    }

                    public Key next()
                    {
                        if( entry == null )
                        {
                            throw new NoSuchElementException("No more elements");
                        }
                        
                        Key key = entry.key;
                        entry = entry.next;
                        return key;
                    }

                    public void remove()
                    {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }
                };
            }
        };
        return iterable;
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
    
    
    private void checkKey( Key key )
    {
        if( key == null ) throw new NullPointerException("Key cannot be null");
    }
    
    private void checkValue( Value val )
    {
        if( val == null ) throw new NullPointerException("Value cannot be null");
    }
    
    
    private Entry findEntry( Key key )
    {
        Entry entry = this.front;
        while( entry != null )
        {
            if( entry.key.equals(key) ) return entry;
            entry = entry.next;
        }
        
        return null;
    }
    
    private class Entry
    {
        private final Key key;
        private Value value;
        private Entry next;
        
        public Entry( Key key, Value value, Entry next )
        {
            this.key   = key;
            this.value = value;
            this.next  = next;
        }
        
        @Override
        public String toString()
        {
            return key.toString();
        }
    }
    
}
