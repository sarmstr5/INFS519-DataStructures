
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Graph represents a set of vertices and edges that connect them.  Uses
 * an adjacency list data structure where the vertex identifier is from
 * 1 to V-1.
 * <p>
 * The graph represents an undirected graph implemented where an edge
 * between two vertices is uses one edge in each direction.
 * @author James Pope
 */
public class Graph implements Iterable<Bag>
{
    private final Bag[] vertices;
    private int E;
    
    /**
     * Creates a new graph.  Vertices are fixed.  Edges can be added 
     * after creation but not removed.
     * @param numVertices
     */
    public Graph( int numVertices )
    {
        this.vertices = new Bag[numVertices];
        for (int i = 0; i < vertices.length; i++)
        {
            this.vertices[i] = new Bag(i);
        }
    }
    
    /**
     * Gets the number of vertices in the graph.
     * @return V
     */
    public int V()
    {
        return this.vertices.length;
    }
    
    /**
     * Gets the number of edges in the graph.
     * @return E
     */
    public int E()
    {
        return this.E;
    }
    
    /**
     * Gets the vertex at specified index.
     * @param i
     * @return 
     */
    public Bag get( int i )
    {
        return this.vertices[i];
    }
    
    /**
     * Gets iterator that enumerates the vertexId of the neighbors of given
     * vertexId.
     * @param vertexId
     * @return neighbor of vertexId
     */
    public Iterable<Integer> neighbors( final int vertexId )
    {
        return new Iterable()
        {
            public Iterator iterator()
            {
                return vertices[vertexId].iterator();
            }
        };
    }
    
    /**
     * Adds an edge between v and w.
     * @param v
     * @param w 
     * @throws IllegalArgumentException if v or w are invalid
     *         (less than 0, more than or equal to V)
     */
    public void addEdge( int v, int w )
    {
        this.validateId(w);
        this.validateId(v);
        
        this.vertices[v].add(w);
        this.vertices[w].add(v);
        this.E++;
    }
    
    private void validateId( int id )
    {
        if( id < 0 || id >= this.V() )
        {
            throw new IllegalArgumentException("Invalid vertex id: "+ id);
        }
    }

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
            Bag neighbors = this.vertices[vertexId];
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
    public static void main( String[] args ) throws java.io.IOException
    {
        String fileName = args[0];
        Graph graph = GraphFactory.make( fileName );
        Stdio.println( "Graph: "+graph );
    }

    public Iterator<Bag> iterator()
    {
        return new Iterator<Bag>()
        {
            private int current = 0;
            public boolean hasNext()
            {
                return current < vertices.length;
            }

            public Bag next()
            {
                if( current >= vertices.length )
                {
                    String e = "No more elements";
                    throw new NoSuchElementException(e);
                }
                
                Bag next = vertices[current];
                current++;
                return next;
            }

            public void remove()
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
        };
    }
}