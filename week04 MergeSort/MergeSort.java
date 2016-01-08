

/**
 * @author Shane Armstrong
 * INFS 519
 * Fall 2015
 */

/**
 * Class that sorts Comparable object araays strictly uses a "Top Down MergeSort" through divide and conquer recursion techniques
 * This sort has a best/average/worst case runtime of NlgN, is not in place, and is stable
 * Insertion Sort was not used for smaller arrays  
 * Pseudo code was used from INFS 519 Lecture 4 and http://www.sorting-algorithms.com/merge-sort 
 */
public class MergeSort
{
    /**
     * Sorts the items ascending, from smallest to largest.
     * @param Array of comparable items
     * @throws NullPointerException if items is null
     */
    public static void sort( Comparable[] items )
    {
    	if(items == null){
    		//throw new NullPointerException("Please give a non null array");
    	}
    	Comparable[] aux = new Comparable[items.length];
    	int low = 0;
    	int hi = items.length-1;
    	mergeSort(items, aux, low, hi);
    	
        // The sort method should create the auxiliary array and
    	// call the recursive method mergeSort with the appropriate arguments.
    	// break array into halfs until trival list
    	// sort 1 element list 1 to i into two element lsits
    	// sort 2nd level list until halved list
    	// call merge
    }

	/**
	 * The mergeSort divides the problem into two, solves them recursively, and then calls Merge 
	 * Mergesort breaks the problem down into smaller parts, Merge actually sorts
	 * @param items 
	 * @param aux
	 * @param lo
	 * @param hi
	 */
    public static void mergeSort( Comparable[] items, Comparable[] aux, int lo, int hi )
    {
    	if(lo == hi){
    		return;
    	}
    	else{
    		int mid = (lo + hi)/2;
    		mergeSort(items, aux, lo, mid);
    		mergeSort(items, aux, mid+1, hi);
    		merge(items, aux, lo, mid, hi);
    		return;
    	}
    }

	/**
	 * The merge method an array of Comprable items, adds them to the aux array in ascending order, 
	 * then overwrites the original array from the low pointer to the high pointer.  
	 * Preconditions using java assert, can also use junit tests
	 * @param items 
	 * @param aux
	 * @param lo
	 * @param hi
	 */   
    public static void merge( Comparable[] items, Comparable[] aux, int lo, int mid, int hi )
    {
    	//local variables
    	int hiPointer = mid+1;
    	int loHolder = lo;
    	int hiHolder = hi;
    	boolean equalToOrLess;    	   	
    	int auxPointer = lo;

    	// auxIn
	    while(auxPointer <= hi) 
	    {
	       	//can add shortcut to check if all of a is lower than b mid<mid+1
	    	
	    	/*
	    	troubleshooting variables
	    	Comparable loItem = (lo<=mid) ? items[lo] : null;
	    	Comparable hiItem = (hiPointer<=hi) ? items[hi] : null;
			*/
    	

	    	if(lo<=mid && hiPointer<=hi){
		    	//returns true if a<=b to keep stability
		    	equalToOrLess = items[lo].compareTo(items[hiPointer])<=0;
		    	aux[auxPointer++] = (equalToOrLess) ? items[lo++] : items[hiPointer++];	    		
	    	}
	    	
	    	//if all of b has been added
	    	else if(hiPointer>hi){
	    		aux[auxPointer++] = items[lo++];
	    	}
	    	
	    	//if all of a has been added
	    	else{
	    		aux[auxPointer++] = items[hiPointer++];
	    	}		    	
    	}
	    	

    	for(int i = loHolder; i<=hiHolder; i++){
    		items[i]=aux[i];
    	}
	     
        assert SortUtil.isSorted( items, lo, mid );
        assert SortUtil.isSorted( items, mid+1, hi );
        
        // Postcondition
        assert SortUtil.isSorted( items, lo, hi );
    }

    //--------------------- DO NOT MODIFY BELOW THIS -----------------------//

    /** USE IS OPTIONAL, NOT NECESSARY
     * Utility method that compares a and b.  Conceptually return a &lt b.
     * @param a
     * @param b
     * @return true if a strictly less than b, false otherwise
     */
    public static boolean less( Comparable a, Comparable b )
    {
        return a.compareTo(b) < 0;
    }

    /**
     * Unit tests MergeSort.
     * @param args 
     */
    public static void main( String[] args )
    {
        if( args.length != 1 )
        {
            String u = "Usage: MergeSort < n | inputFilename >";
            Stdio.println(u);
            return;
        }
        String argument = args[0];
        Comparable[] items;
        if( Stdio.isInteger( argument ) )
        {
            int n = Integer.parseInt(argument);
            int bounds = 100; // keep generated number between [0,bounds)
            items = Stdio.generate( n , bounds);
        }
        else
        {
            Stdio.open( args[0] );
            int length = Stdio.readInt();
            items = new Integer[length];
            for( int i = 0; i < length; i++ )
            {
                //String item = Stdio.readString();
                Integer item = Stdio.readInt();
                items[i] = item;
            }
            Stdio.close();
        }
        
        Clock time = new Clock();
        MergeSort.sort(items);

        Stdio.println( "Is sorted? "+SortUtil.isSorted(items, 0, items.length-1) );
        //Stdio.println( "the Array "+SortUtil.toString(items));
        Stdio.println( "Time=" + time );
    }
    
}
