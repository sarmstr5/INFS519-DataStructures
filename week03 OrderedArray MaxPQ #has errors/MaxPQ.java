/**
 * Max priority queue API.
 */
public interface MaxPQ extends Iterable<Comparable>
{
    /**
     * Gets the number of elements currently in the queue
     * @return size
     */
    public int size();
    
    /**
     * Determines if there are not elements in the queue.
     * @return true is no elements, false otherwise
     */
    public boolean isEmpty();
    
    /**
     * Inserts the key into the queue.
     * @param key
     */
    public void insert( Comparable key );
    
    /**
     * Finds and removes the key from the queue with the maximum value.
     * @return max key
     */
    public Comparable delMax( );

    /**
     * Finds the key from the queue with the maximum key value.
     * @return max key
     */
    public Comparable findMax();
}
