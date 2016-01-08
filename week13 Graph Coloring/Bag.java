
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Bag supports add, size, and isEmpty in constant time.  Provides
 * iterator in time proportional to N.  Implemented using a single
 * linked list.
 * @author James Pope
 */
public final class Bag implements Iterable<Integer>
{
    private class Node
    {
        private final Integer item;
        private Node next;
        
        public Node( Integer item )
        {
            this.item = item;
            this.next = null;
        }
        
        @Override
        public String toString()
        {
            return (this.next==null)? "null" : this.next.toString();
        }
    }
    
    // NOTE: Interestingly, single linked list here is faster than ArrayList!!!
    private Node front;
    private int size; // not strictly necessary, useful for debugging
    
    private final Integer id;
    private int color;
    
    /**
     * Creates a new Bag with no elements.
     * @param id
     */
    public Bag( Integer id )
    {
        this.id    = id;
        this.size  = 0;
        this.color = 0;
    }
    
    public Integer getId()
    {
        return this.id;
    }
    
    public int getColor()
    {
        return this.color;
    }
    
    
    public final void setColor( int color )
    {
        this.color = color;
    }

    /**
     * Adds an item to the bag (no attempt to prevent duplicates).
     * @param item 
     */
    public void add(Integer item)
    {
        Node previousFront = this.front;
        this.front = new Node(item);
        this.front.next = previousFront;
        this.size++;
    }

    /**
     * Returns the number of elements in the bag.
     * @return number of elements
     */
    public int size()
    {
        return this.size;
    }
    

    /**
     * 
     * @return 
     */
    public Iterator<Integer> iterator()
    {
        return new Iterator<Integer>()
        {
            private Node current = front;
            
            public boolean hasNext()
            {
                return current != null;
            }

            public Integer next()
            {
                if( this.current == null )
                {
                    throw new NoSuchElementException("No more elements");
                }
                Integer currentItem = this.current.item;
                this.current = this.current.next;
                return currentItem;
            }

            public void remove()
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
        };
    }
}
