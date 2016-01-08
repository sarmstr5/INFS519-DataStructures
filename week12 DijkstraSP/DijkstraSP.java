
/**
 * Dijkstra's single-source, shortest path algorithm.  Only works when all
 * weights are non-negative.
 * @author <your name>
 */
public class DijkstraSP
{
    private WeightedDigraph graph; 	//initial graph
    private int source; 			//starting v
    private double[] distTo;			//distance (weight) from source to vector i
    private WeightedDiedge[] edgeTo;//edge that connects vector i to parent
    private MinHeap<WeightedDiedge> minPQ;			//minheap that gives lowest weighted edge
    private WeightedDigraph minSPT;	//single source shortest path
    
    /**
     * Creates a new DijkstraSP and performs search on the specified Graph.
     * @param graph
     * @param source
     * @throws IllegalArgumentException if graph has a negative weight edge
     */
    public DijkstraSP( WeightedDigraph g, int source )
    {
    	//instantiating variables
    	this.graph = g;
    	this.source = source;
    	distTo = new double[graph.V()];
    	edgeTo = new WeightedDiedge[graph.E()];
    	minPQ = new MinHeap(graph.E());
    	minSPT = new WeightedDigraph(graph.V());    	
    	
    	//make sure no negative edges
    	graphNonNeg(graph);
    	
    	//set initial conditions
    	for(int i = 0; i<distTo.length; i++)
    		distTo[i] = Double.MAX_VALUE;    		
    	
    	distTo[source]=0;
    	edgeTo[source]= new WeightedDiedge(source,source,0);
    	
//    	Add source vertex's neighbors to the minPQ
    	for(WeightedDiedge e: g.neighbors(source))
    		minPQ.insert(e);
    	
    	createSPT();

    }
    
    private void createSPT() 
    {
    	WeightedDiedge e;
    	int v, w;
    	double weight;
    	
    	while(!minPQ.isEmpty())
    	{
    		e = minPQ.delMin();
    		v = e.from();
    		w = e.to();
    		if(edgeTo[w]==null || distTo[w] > distTo[v] + e.weight())
    		{
    			minSPT.addEdge(e);
    			relax(e);	
    		}
    	}		
	}

	private void relax(WeightedDiedge edge) 
	{

		int v = edge.from(); 
		int w = edge.to();
		
		// Update distance to w and remember edge
		distTo[w] = distTo[v] + edge.weight();
		edgeTo[w] = edge;
    	
		for(WeightedDiedge e: graph.neighbors(w))
    		minPQ.insert(e);
	}

	private void graphNonNeg(WeightedDigraph g) {
		for(WeightedDiedge e: g.edges())
			if(e.weight()<0)
				throw new IllegalArgumentException("There can not be any negative edges");
		
	}

	/**
     * Returns whether or not a path exists from the source to v.
     * @param v
     * @return true if a path exists from the source to v, false otherwise 
     */
    public boolean hasPathTo( int v )
    {
    	if(edgeTo[v]==null)
    		return false;
    	else
    		return true;
    }
    
    /**
     * Returns distance to the specified vertex v.  If the value is
     * Double.MAX_VALUE, the vertex is not reachable from the source.
     * @param v
     * @return distance to v from s
     */
    public double distTo( int v )
    {
        return distTo[v]; // MODIFY CODE
    }
    
    /**
     * Returns path from the source to the given vertex v, in that order.
     * @param v
     * @return path from the source to v, starts with source, ends with v
     *         returns a null if no path exists
     */
    public Iterable<WeightedDiedge> pathTo( int v )
    {
		if(edgeTo[v]==null)
			return null;
		else
		{
			//WeightedDiedge w = edgeTo[v];
			Stack<WeightedDiedge> s = new Stack<WeightedDiedge>();
			int i = 0;
			while(i<minSPT.E() && v != source)
			{
				s.push(edgeTo[v]);
				v = edgeTo[v].from();
				i++;
			}
		
			if(v == source)
				return s;
			System.out.println("edgeTo v was not null but did not reach the source");
		    return null; 
		}
    }
    
    
    //---------- DO NOT MODIFY BELOW THIS LINE ----------//

    /**
     * Unit test main for the DijkstraSP class.
     * @param args 
     * @throws java.io.FileNotFoundException 
     */
    public static void main( String[] args ) throws java.io.FileNotFoundException
    {
        if( args.length != 2 )
        {
            String u = "Usage: DijkstraSP <filename> <source>";
            Stdio.println(u);
            return;
        }
        
        String fileName = args[0];
        int source      = Integer.parseInt(args[1]);
        WeightedDigraph graph = GraphFactory.makeWeightedDigraph(fileName);
        Stdio.println( "Graph: "+graph );
        
        DijkstraSP dijkstraSP = new DijkstraSP( graph, source );
        Stdio.println( "Paths to source: "+source );
        for (int vertexId = 0; vertexId < graph.V(); vertexId++)
        {
            Stdio.print( "  path for "+vertexId+" : " );
            if( dijkstraSP.hasPathTo(vertexId) )
            {
                for( WeightedDiedge path : dijkstraSP.pathTo(vertexId) )
                {
                    if( path != null ) Stdio.print( path.toString() );
                }
            }
            Stdio.println( "" );
        }
    }
}
