
/**
 * Binary search using array implementation.  Not an abstract data type
 * and assumes array is already sorted.
 * @author James Pope
 */
public class BinarySearch
{
    /**
     * Finds the index of the item specified in the specified array.
     * @param findItem
     * @param items
     * @return index of findItem in items or -1 if not found
     */
public static int search( Comparable findItem, Comparable[] items )
{
    int index = -1;
    int lo = 0;
    int hi = items.length-1;
    while( lo <= hi )
    {
        // Find half way position between begin and end
        int mid = lo + (hi - lo) / 2;
        Comparable midItem = items[mid];

        if( findItem.compareTo(midItem) < 0 )
        {
            // Move left
            hi   = mid-1;
            Stdio.println("move left  ["+lo+","+hi+"]");
        } 
        else if( findItem.compareTo(midItem) > 0 )
        {
            // Move right
            lo = mid+1;
            Stdio.println("move right ["+lo+","+hi+"]");
        }
        else
        {
            // Must be equal, narrowed to one index, exit loop
            Stdio.println("stopped at "+lo);
            index = mid;
            break;
        }
    }

    return index;
}
    
    /**
     * Insert item at pos into already sorted items [0,pos-1].
     * Uses variant which avoids swap routine.
     * @param items
     * @param pos index of item to insert
     */
    public static void insert( Comparable[] items, int pos )
    {
        Comparable unsortedItem = items[pos];
        int j = pos;
        
        while( j > 0 && unsortedItem.compareTo(items[j-1]) < 0 )
        {
            // Shift to the right
            items[j] = items[j-1];
            j--;
        }
        items[j] = unsortedItem;
    }
    
    /**
     * Gets String facsimile of items.
     * @param items
     * @return 
     */
    public static String toString( Comparable[] items )
    {
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for( int i = 0; i < items.length; i++ )
        {
            Comparable item = items[i];
            if(i!=0) buf.append( " " );
            buf.append( item.toString() );
        }
        buf.append("]");
        return buf.toString();
    }
    
    /**
     * Unit tests binary search over a sorted array.
     * @param args 
     */
    public static void main( String[] args )
    {
        int n = Integer.parseInt( args[0] );
        
        // Create random integers in range [0,100)
        Comparable[] items = Stdio.generate(n, 100);
        // Sort them using insertion sort O(n^2)
        for (int i = 1; i < items.length; i++)
        {
            insert( items, i );
        }
        Stdio.println( "Items=" + toString(items));
        
        Stdio.open();
        boolean quit = false;
        while( quit == false )
        {
            Stdio.print("Search for (-1 to quit): ");
            int findItem = Stdio.readInt();
            if( findItem < 0 )
            {
                quit = true;
            }
            else
            {
                int index = search( findItem, items );
                if( index < 0 ) Stdio.println("Could not find item "+findItem);
                else            Stdio.println("Found item at  "+index);
            }
        }
        Stdio.close();
    }
}
