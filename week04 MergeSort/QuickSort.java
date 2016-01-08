
/**
 * QuickSort partitions and then uses two recursive calls to sort
 * subsets of an array.  Another example of divide-and-conquer algorithm.
 * @author James Pope
 */
public class QuickSort
{
    /**
     * Sorts the items ascending, from smallest to largest.
     * @param items (modified)
     * @throws NullPointerException if items is null
     */
    public static void sort( Comparable[] items )
    {
        // Heuristic: Critical first step to better guarantee performance
        // Still issue if all duplicate values, unless partition heuristic
        SortUtil.shuffle( items );

        sort( items, 0, items.length-1 );
    }

    public static void sort( Comparable[] items, int lo, int hi )
    {
        // Heuristic: use InsertionSort for small ranges
        // Check base case
        if( hi <= lo ) return;

        // First partition around pivot value, into sets L and R
        int i = partition( items, lo, hi );

        // Sort left section before pivot
        sort( items, lo, i-1 );

        // Sort right section after pivot
        sort( items, i+1, hi );
    }
    
    public static int partition( Comparable[] items, int lo, int hi )
    {
        int i = lo;
        int j = hi-1; // select hi as pivot

        while( true )
        {
            // Move i as far right, until value > pivot
            while( i < hi )
            {
                if( less(items[i], items[hi]) == false ) break;
                i++;
            }

            // Move j as far left, until value < pivot
            while( j > lo  )
            {
                if( less(items[j], items[hi]) == true ) break;
                j--;
            }

            // If they crossed, we are done and i is in position
            if( i >= j ) break;

            // Otherwise, we need to swap
            swap( items, i, j );
        }

        // Found position for pivot value that meets invariant
        swap( items, i, hi );

        return i; // return the position of the pivot, in correct place
    }



    public static boolean less( Comparable a, Comparable b )
    {
        return a.compareTo(b) < 0;
    }

    public static void swap( Comparable[] items, int i, int j )
    {
        Comparable temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }
    

    

    /**
     * Unit tests the Stack data type.
     * @param args 
     */
    public static void main( String[] args )
    {
        Integer[] items = null;
        if( args.length > 1 )
        {
            items = new Integer[args.length];
            for( int i = 0; i < items.length; i++ )
            {
                items[i] = Integer.parseInt(args[i]);
            }
        }
        else
        {
            java.util.Random rand = new java.util.Random(123456);
            int n = Integer.parseInt(args[0]);
            items = new Integer[n];
            for( int i = 0; i < items.length; i++ )
            {
                items[i] = rand.nextInt(Integer.MAX_VALUE);
            }
        }
        
        Clock time = new Clock();
        QuickSort.sort(items);

        //Stdio.println( SortUtil.toString(items) );
        Stdio.println( SortUtil.isSorted(items, 0, items.length-1) );
        Stdio.println( "Time=" + time );
    }
    
}
