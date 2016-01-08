
import java.util.Random;

/**
 * Set of utility methods for sorting algorithms and tests.
 * @author James Pope
 */
public class SortUtil
{
    public static boolean isSorted( Comparable[] items, int i, int j )
    {
        for(int k = i+1; k <= j; k++)
        {
            Comparable prev = items[k-1];
            Comparable next = items[k];
            if( next.compareTo(prev) < 0 ) return false;
        }
        return true;   
    }

    public static int ceil( int a, int b )
    {
        return (int)Math.ceil(a/(double)b);
    }

    public static String toString( Object[] objects )
    {
        return toString( objects, 0, objects.length-1 );
    }

    public static String toString( Object[] objects, int i, int j )
    {
        StringBuilder buf = new StringBuilder("[");
        for( int k = i; k <= j; k++ )
        {
            if( k != i ) buf.append(",");
            buf.append(objects[k].toString());
        }
        buf.append("]");
        return buf.toString();
    }

    /**
     * Similar to Knuth / Fisher-Yates shuffling algorithm
     * If generator independent, uniform, so is output
     * @param items
     */
    public static void shuffle( Comparable[] items )
    {
        // Same as Knuth, but works backwards
        Random rand = new Random(123456); // seed for testing
        for( int j = 1; j < items.length; j++ )
        {
            swap( items, j, rand.nextInt(j) ); // [0,j)
        }
    }

    public static boolean less( Comparable a, Comparable b )
    {
        return a.compareTo(b) < 0;
    }

    public static boolean lessEqual( Comparable a, Comparable b )
    {
        return !(a.compareTo(b) > 0);
    }

    public static void swap( Comparable[] items, int i, int j )
    {
        Comparable temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }


    public static void main( String[] args )
    {
        Random rand = new Random(123456);
        int n = Integer.parseInt(args[0]);
        Integer[] items = new Integer[n];
        for( int i = 0; i < items.length; i++ )
        {
            items[i] = rand.nextInt(100);
        }
        
        MergeSort.sort(items);
        Stdio.println( toString(items) );

        shuffle( items );
        Stdio.println( toString(items) );
    }
}
