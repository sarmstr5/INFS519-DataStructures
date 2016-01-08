
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * GraphFactory creates graphs from various inputs including text files.
 * @author James Pope
 */
public class GraphFactory
{
    /**
     * Creates a new graph from input file.  Format is expected to have
     * the integer number of vertices on the first line followed by a
     * variable number of lines with each line having the edge as two
     * vertex identifiers.
     * <p>
     * For example, graph with 4 vertices and 5 edges. 
     * 4
     * 0 3
     * 0 2
     * 0 1
     * 1 2
     * 2 3
     * 
     * @param fileName
     * @return 
     * @throws java.io.FileNotFoundException 
     */
    public static Graph make( String fileName ) throws IOException
    {
        // read the lines out of the file
        List<String> lines = new ArrayList<String>();

        BufferedReader input =  new BufferedReader(new FileReader(fileName));
        try
        {
            String line;
            while (( line = input.readLine()) != null)
            {
                lines.add(line);
            }
        }
        finally
        {
            input.close();
        }
        
        // parse the data in the file
        String[] firstLine = lines.get(0).split("\\s+");
        int items = Integer.parseInt(firstLine[0]);
        int edges = Integer.parseInt(firstLine[1]);
        
        /*
         * Set all the vertex colors to the same color, massive violations
         */
        Graph graph = new Graph( items );
        
        // Now read in all the edges for the vertices defined in the graph/file
        for (int e = 1; e < edges+1; e++)
        {
            String line = lines.get(e);
            String[] parts = line.split("\\s+");
            int i = Integer.parseInt(parts[0]);
            int j = Integer.parseInt(parts[1]);
            
            graph.addEdge(i, j);
        }
        
        return graph;
    }
}