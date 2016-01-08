
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * SymbolTable implementation that holds key/value pairs in a linked list. reference only to front  
 * @param <Key>
 * @param <Value>
 */
public class EntryList <Key, Value> implements BasicSymbolTable <Key, Value>
{
	Entry frontOfList;
	int size;
	
	EntryList(){
		this(0);
	}
	EntryList(int size){
		this.size =0;
		this.frontOfList = new Entry(null, null, null);
	}
	

    /**
     * Gets the number of elements currently in the queue
     * @return size
     */
    public int size()
    {
        return this.size; // MODIFY CODE
    }

    /**
     * Determines if there are not elements in the queue.
     * @return true if no elements, false otherwise
     */
    public boolean isEmpty()
    {
        return (this.size==0); // MODIFY CODE
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
        Entry newE = new Entry(key, value);
        
        //only item in list
        if(isEmpty()){
        	frontOfList.next=newE;
        	size++;
        	return;
        }
        
        Entry currentEntry=frontOfList;
        // go through list until entry with same key is found
        while(currentEntry.next!=null)
        {
        	if(newE.equals(currentEntry.next))
        	{
        		currentEntry.next.v = newE.v;
        		break;
        	}
        	currentEntry = currentEntry.next;
        }
        
        //if looped to end of list
        newE.next = frontOfList.next;
        frontOfList.next = newE;
        //currentEntry.next=newE;
        size++;
    }

    
    /**
     * Finds Value for the given Key.
     * @param key
     * @return value that key maps to or null if not found
     * @throws NullPointerException if the key is null
     */
    public Value get( Key key )
    {
        Entry currentEntry=frontOfList;
        if(key ==null)
        	throw new NullPointerException("Key is null");
        if(isEmpty())
        	return null;
           
        for(int i =0; i<size; i++)
        {
        	//keep going until 
        	if(key == currentEntry.next.k)
        		return currentEntry.next.v;
        	
        	currentEntry=currentEntry.next;        		
        }
    	
        return null;
    }

    /**
     * Removes the Value for the given Key from the table.
     * @param key
     * @return value that was removed or null if not found
     * @throws NullPointerException if the key is null
     */
    public Value delete( Key key )
    {
        Entry currentEntry=frontOfList;
        if(key==null)
            throw new NullPointerException("Key is null");
        if(isEmpty())
        	return null;
        for(int i =0; i<size; i++)
        {
        	//keep going until 
        	if(key.equals(currentEntry.next.k))
        	{
        		Value val = currentEntry.next.v;
        		currentEntry.next = currentEntry.next.next;
        		size--;
        		return val;
        	}
        	
        	currentEntry=currentEntry.next;        		
        }
    	
        return null;
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
                    int position=0;
                    Entry currentE=frontOfList;
                    
                    public boolean hasNext()
                    {
                        return (position<size()); // MODIFY CODE
                    }

                    public Key next()
                    {
                    	currentE= currentE.next;
                    	position++;
                        return currentE.k;
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
    	
    	
     class Entry
    {
    	Key k;
    	Value v;
    	Entry next;

    	Entry(Key k, Value v){
    		this(k, v, null);
    	}
    	
		Entry(Key k, Value v, Entry e){
    		this.k = k;
    		this.v = v;
    		next = e;
    	}
    	
    	public Key getKey(Entry e){	return this.k;	}
    	public Value getValue(Entry e){return this.v; }
    	
    	@Override
    	public String toString(){
    		
			return "Key = "+this.k+" Value = "+this.v;
    		
    	}

    }	
    

    //------------------ DO NOT MODIFY BELOW THIS LINE -------------------//
    
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
    
}


