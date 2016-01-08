
import java.util.Iterator;

/**
 * Creates adjacency list graph using the bag class array to hold the edges 
 */
public class Graph
{
    private Bag<Integer>[] vertices;

    int numberOfVertices;
    int numberOfEdges;
    
    /**
     * Creates a new graph.  Vertices are fixed.  Edges can be added 
     * after creation but not removed.
     * @param numVertices
     */
    public Graph( int numVertices )
    {

    	numberOfVertices = numVertices;
    	numberOfEdges=0;
    	vertices = new Bag[numVertices];
    	for(int i = 0; i<numVertices; i++)
    		vertices[i] = new Bag();
    	
    }
    
    /**
     * Gets the number of vertices in the graph.
     * @return V
     */
    public int V()
    {
        return numberOfVertices; 
    }
    
    /**
     * Gets the number of edges in the graph.
     * @return E
     */
    public int E()
    {
        return numberOfEdges; 
    }
    
    /**
     * Gets iterator that enumerates the vertexId of the neighbors of given
     * vertexId.
     * @param vertexId
     * @return neighbor of vertexId
     * @throws IndexOutOfBoundsException if vertexId is invalid
     *         (less than 0, more than or equal to V)
     */
    public Iterable<Integer> neighbors( final int vertexId )
    {
    	//how do i know the which bag represents a vertex? 
        return vertices[vertexId]; // MODIFY CODE
    }
    /**
     * Adds an edge between v and w.
     * @param v
     * @param w 
     * @throws IndexOutOfBoundsException if v or w are invalid
     *         (less than 0, more than or equal to V)
     */
    public void addEdge( int v, int w )
    {
//    	if(vertices[v].size()==0)
//    		numberOfVertices++;
        vertices[v].add(w);
        
//        if(vertices[w].size()==0)
//        	numberOfVertices++;
        vertices[w].add(v);
        numberOfEdges++;
    }
    


    //------- DO NOT MODIFY BELOW THIS LINE -------//

    /**
     * Gets String facsimile of this graph.
     * @return 
     */
    @Override
    public String toString()
    {
        StringBuilder buf = new StringBuilder();
        String title = "V=" + this.V() + " E=" + this.E();
        buf.append( title );
        for (int vertexId = 0; vertexId < this.vertices.length; vertexId++)
        {
            Bag<Integer> neighbors = this.vertices[vertexId];
            String prefix = "\n["+vertexId+"] neighbors="+neighbors.size()+": ";
            buf.append( prefix );
            boolean first = true;
            for( int neighborId : neighbors )
            {
                if( first ) first = false;
                else buf.append( ", " );
                buf.append( neighborId );
            }
        }
        return buf.toString();
    }
    
    
    /**
     * Unit test main for the Graph class.  Reads a file and prints out.
     * @param args 
     * @throws java.io.FileNotFoundException 
     */
    public static void main( String[] args ) throws java.io.FileNotFoundException
    {
        String fileName = args[0];
        Graph graph = GraphFactory.make( fileName );
        Stdio.println( "Graph: "+graph );
    }
}
