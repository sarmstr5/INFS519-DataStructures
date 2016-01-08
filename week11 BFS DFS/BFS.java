
/**
 * Breadth First Search that creates a spanning tree finds if two vertices are connected
 * @author Shane Armstrong
 */
public class BFS
{
    // ADD CODE
    int source;
    Queue pathQ;
    Queue searchQ;
    Queue goBackQSearch;
    Queue goBackQPath;
    Graph g;
    boolean[] markedVs;
    Stack pathStack = new Stack();
    Stack goBackStack = new Stack();
    
    /**
     * Creates a new BFS and performs search on the specified Graph.
     * @param graph
     * @param source
     */
    public BFS( Graph graph, int source )
    {    
        this.source = source;
        pathQ = new Queue();
        searchQ = new Queue();
        goBackQSearch = new Queue();
        goBackQPath = new Queue();
        g = graph;
        markedVs = new boolean[g.numberOfVertices];
        
     }
    
    /**
     * Iterative approach to BFS.  Uses Queue that may grow to E.  Keeps
     * track of the marked and paths for later queries.
     */
    private void search()
    {
    	
    	clear();
		searchQ.enqueue(source);
		goBackQSearch.enqueue(source);
		
		int currentV;
		int goBackV;
		
		 while(!searchQ.isEmpty()) //
		 {
	    	 currentV = (int) searchQ.dequeue();
	    	 goBackV = (int) goBackQSearch.dequeue();
	    	 
	    	 
	    	 if(!markedVs[currentV])  //if v is not marked
	    	 {
	    		 markedVs[currentV] =true; // mark v
	    		 pathQ.enqueue(currentV);
				 goBackQPath.enqueue(goBackV);
	    		 
				 for(int i: g.neighbors(currentV))//neighbors of v
				 {
					 if(!markedVs[i]) //if neighbor is not marked
					 {
	    				 searchQ.enqueue(i);
	    	    		 //pathQ.enqueue(currentV);
	    				 goBackQSearch.enqueue(currentV);
					 }
				 }	
	    	 }
		 }
    }
    
    private void clear() 
    {
        goBackQSearch = new Queue();
        goBackQPath = new Queue();
        pathStack = new Stack();
        pathQ = new Queue();
        goBackStack = new Stack();
        
    	for (int i = 0; i < markedVs.length; i++) 
    		markedVs[i] = false;	
	}

	/**
     * Returns whether or not a path exists from the source to v.
     * @param v
     * @return true if a path exists from the source to v, false otherwise
     */
    public boolean hasPathFromSource( int v )
    {
    	if(searchQ.isEmpty())
    		search();
 	
    	Queue tempPath = new Queue();
    	Queue tempReturn = new Queue();
    	
    	for(Object o: pathQ)
    		tempPath.enqueue(o);
    	
    	for(Object o: goBackQPath)
    		tempReturn.enqueue(o);
    	
    	int currentV;
    	int originV;

    	while(!tempPath.isEmpty())
    	{
    		currentV = (int) tempPath.dequeue();
    		originV = (int) tempReturn.dequeue();
    		
    		pathStack.push(currentV);
    		goBackStack.push(originV);
    		
    		if(currentV == v)
    			return true;
	
    	}
        return false; 
    }
    
    /**
     * Returns path from the source to the given vertex v, in that order.
     * @param v
     * @return path from the source to v, starts with source, ends with v
     *         returns a null if no path exists
     */
    public Iterable<Integer> pathFromSource( int v )
    {
//    	If path does not exists from source to v, return null
//		Create a Q
//		While v is not the source
//			enqueue v onto the Q
//			Set v equal to paths at v
//		Return Q
    	Stack returnPath = new Stack();
		int nextV =(int) goBackStack.peek();
		int pathV = (int) pathStack.pop();
		returnPath.push(pathV);
    	
		while(!pathStack.isEmpty())
		{
			if(pathV == nextV)
			{
				returnPath.push(pathV); 
				nextV = (int)goBackStack.peek();
				pathV = (int)pathStack.pop();
			}
			else
			{
				pathV = (int) pathStack.pop();
				goBackStack.pop();
			}
		}
		
		if((int) returnPath.peek() != source)
			returnPath.push(source);
			
		return returnPath;
    }
    
    
    //------- DO NOT MODIFY BELOW THIS LINE -------//

    /**
     * Unit test main for the BFS class.
     * @param args 
     * @throws java.io.FileNotFoundException 
     */
    public static void main( String[] args ) throws java.io.FileNotFoundException
    {
        if( args.length != 2 )
        {
            String u = "Usage: BFS <filename> <source>";
            Stdio.println(u);
            return;
        }
        
        String fileName = args[0];
        int source      = Integer.parseInt(args[1]);
        Graph graph = GraphFactory.make( fileName );
        
        BFS bfs = new BFS( graph, source );
        Stdio.println( "Paths to source: "+source );
        for (int vertexId = 0; vertexId < graph.V(); vertexId++)
        {
            Stdio.print( "  path for "+vertexId+" : " );
            if( bfs.hasPathFromSource(vertexId) )
            {
                for( int pathVertex : bfs.pathFromSource(vertexId) )
                {
                    Stdio.print( "->" + pathVertex );
                }
            }
            Stdio.println( "" );
        }
    }
}
