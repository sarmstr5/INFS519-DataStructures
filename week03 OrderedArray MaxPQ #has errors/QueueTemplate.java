
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Your Name Here
 * INFS 519
 * Fall 2015
 */

/**
 * ADD DOCUMENTATION
 */
public class Queue <Type> implements QueueAPI<Type>
{
    // Add Node inner class here
    
    // ADD CODE
    
    public Queue()
    {
        // ADD CODE
    }
    
    public int size()
    {
        return -1; // MODIFY
    }
    
    public boolean isEmpty()
    {
        return false; // MODIFY
    }
    
    public void enqueue( Type item )
    {
        // ADD CODE
    }
    
    public Type peek()
    {
        return null;  // MODIFY
    }
    
    public Type dequeue( )
    {
        return null;  // MODIFY
    }
    
    public Iterator<Type> iterator()
    {
        return null;  // MODIFY
    }
    
    //--------------------- DO NOT MODIFY BELOW THIS -----------------------//
    
    @Override
    public String toString()
    {
        // Uses the iterator to build String
        StringBuilder buf = new StringBuilder();
        boolean first = true;
        buf.append("[");
        for (Type item : this)
        {
            if( first ) first = false;
            else buf.append( ", " );
            buf.append( item.toString() );
        }
        buf.append("]");
        return buf.toString();
    }
    
    
    /**
     * Unit tests the Queue data type.
     * @param args 
     */
    public static void main( String[] args )
    {
        Queue queue = new Queue();
        
        Stdio.open( args[0] );
        while( Stdio.hasNext() )
        {
            String operation = Stdio.readString();
            if( operation.equals("enqueue") )
            {
                String item = Stdio.readString();
                Stdio.println( "enqueue "+ item );
                queue.enqueue(item);
            }
            else if( operation.equals("dequeue") )
            {
                Stdio.println( "dequeue "+ queue.dequeue() );
            }
            else if( operation.equals("peek") )
            {
                Stdio.println( "peek "+ queue.peek() );
            }
            else if( operation.equals("size") )
            {
                Stdio.println( "size "+ queue.size() );
            }
        }
        
        Stdio.println( "Queue=" +queue.toString() );
        
        Stdio.close();
    }
}
