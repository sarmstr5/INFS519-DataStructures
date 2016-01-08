
/**
 * A Depth First Search that creates a spanning tree finds if two vertices are connected
 */
public class DFS
{
    Stack searchStack;
  	Stack pathStack;
  	Stack goBackStackSearch;
  	Stack goBackStackPath;
  	int source;
  	Graph g;
  	boolean[] markedVs;
    
    /**
     * Creates a new DFS and performs search on the specified Graph.
     * @param graph
     * @param source
     */
    public DFS( Graph graph, int source )
    {
        this.source = source;
        pathStack=new Stack();
        searchStack= new Stack();
        goBackStackSearch = new Stack();
        goBackStackPath = new Stack();
        g = graph;
        markedVs = new boolean[g.numberOfVertices];

    }
    
    /**
     * Iterative approach to DFS.  Uses Stack that may grow to E.  Keeps
     * track of the marked and paths for later queries.
     */
    private void search()
    {


    }
    
    /**
     * Returns whether or not a path exists from the source to v.
     * @param v
     * @return true if a path exists from the source to v, false otherwise
     */
    public boolean hasPathFromSource( int v )
    {
        clear();
    	searchStack.push(source);
    	goBackStackSearch.push(source);
		int currentV;
		int goBackV;
		
		 while(!searchStack.isEmpty()) //
		 {
	    	 currentV = (int) searchStack.pop();
	    	 goBackV = (int) goBackStackSearch.pop();
	    	 
	    	 if(currentV==v)
	    	 {
	    		 pathStack.push(v);
	    		 goBackStackPath.push(goBackV);
	    		 return true;
	    	 }
	    	 
	    	 if(!markedVs[currentV])  //if v is not marked
	    	 {
	    		 markedVs[currentV] =true; // mark v
	    		 pathStack.push(currentV);
				 goBackStackPath.push(goBackV);
	    		 
				 for(int i: g.neighbors(currentV))//neighbors of v
				 {
					 if(!markedVs[i]) //if neighbor is not marked
					 {
	    				 searchStack.push(i);
	    				 goBackStackSearch.push(currentV);
					 }
				 }	
	    	 }
		 }
		 return false; 
    }
    
    private void clear() {
    	for (int i = 0; i < markedVs.length; i++) 
    		markedVs[i] = false;

    	searchStack = null;
    	goBackStackSearch = null;
    	goBackStackPath = null;
    	pathStack = null;
    	
    	goBackStackPath = new Stack();
    	pathStack = new Stack();
    	searchStack = new Stack();
    	goBackStackSearch = new Stack();
    	
	
		
	}

	/**
     * Returns path from the source to the given vertex v, in that order.
     * @param v
     * @return path from the source to v, starts with source, ends with v
     *         returns a null if no path exists
     */
    public Iterable<Integer> pathFromSource( int v )
    {

    	Stack returnPath = new Stack();
    	
		int nextV =(int) goBackStackPath.peek();
		int pathV = (int) pathStack.pop();
		returnPath.push(pathV);
    	
		while(!pathStack.isEmpty())
		{
			if(pathV == nextV)
			{
				returnPath.push(pathV); 
				nextV = (int)goBackStackPath.peek();
				pathV = (int)pathStack.pop();
			}
			else
			{
				pathV = (int) pathStack.pop();
				goBackStackPath.pop();
			}
		}
		
		if((int) returnPath.peek() != source)
			returnPath.push(source);
			
		return returnPath;
    	
    }
    


    //------- DO NOT MODIFY BELOW THIS LINE -------//
    
    /**
     * Unit test main for the DFS class.
     * @param args 
     * @throws java.io.FileNotFoundException 
     */
    public static void main( String[] args ) throws java.io.FileNotFoundException
    {
        if( args.length != 2 )
        {
            String u = "Usage: DFS <filename> <source>";
            Stdio.println(u);
            return;
        }
        
        String fileName = args[0];
        int source      = Integer.parseInt(args[1]);
        Graph graph = GraphFactory.make( fileName );
        
        DFS dfs = new DFS( graph, source );
        Stdio.println( "Paths to source: "+source );
        for (int vertexId = 0; vertexId < graph.V(); vertexId++)
        {
            Stdio.print( "  path for "+vertexId+" : " );
            if( dfs.hasPathFromSource(vertexId) )
            {
                for( int pathVertex : dfs.pathFromSource(vertexId) )
                {
                    Stdio.print( "->" + pathVertex );
                }
            }
            Stdio.println( "" );
        }
    }
}
