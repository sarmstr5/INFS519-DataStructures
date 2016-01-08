
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

/**
 * Coloring assigns colors to an undirected graph such that not two
 * adjacent vertices have the same color.
 * @author your name
 */
public class Coloring
{    
	static ArrayList vnArr;
	final static int BASE_COLOR = 1;
	final static int MAX_NUMBER_COLORS=300;
	static boolean[] colorArr;
	static int coloredVs= 0;

    /**
     * Assigns color (represented as an integer) to graph. Should be
     * colored such that no two  adjacent vertices have the same
     * vertex. Typical to minimize the number of distinct colors.
     * @param graph 
     */
    public static void color( Graph graph )
    {

    	// need to find out how to store the degrees
    	// the bags have a size but they arent ordered
    	// i could make a "node" that puts it to a minheap
		//Coloring(graph);
    	int largestDegree;
    	int maxDegree = 0;
		MinHeap minPQ = new MinHeap(graph.V());
	   	MinHeap minPQcolor = new MinHeap(graph.V());
    	//next step would be to go to neighbors with largest degree or use a minheap somehow
    	for(Bag b: graph)
    	{
    		VertNode vn = new VertNode(b); // why could i instantiate it inside?
    		VertNodeTwo vnt = new VertNodeTwo(b);
    		minPQ.insert(vn);
    		minPQcolor.insert(vnt);
    		
    		if(maxDegree<b.size()) 
    			maxDegree = b.size();
    		
    		Bag neighbor;
        	boolean[] neighborColors = new boolean[b.size()+1];
        	int lowestColor=1;
    		for(int vertex: graph.neighbors(b.getId()))
    		{
    			neighbor = graph.get(vertex);
    			neighborColors[neighbor.getColor()] = true;
    		}
    		
    		for(int i=1; i<neighborColors.length; i++) //index zero is not counted
    		{
    			if(!neighborColors[i])
    			{
    				lowestColor = i;
    				break;
    			}
    		}
    		b.setColor(lowestColor);
    		coloredVs++;
    	}
    	System.out.println(colorCount(graph));
    	int vWithHighestColor = 0;
    	int highestColor = 0;
 
    	
///////////////////////////////////////////////////////////////////////going for v's with highest degree
    	if(colorCount(graph)>MAX_NUMBER_COLORS)
    	{
    		for(Bag b: graph)
    		{
    			b.setColor(0);
    		}
	    	while(!minPQ.isEmpty())
	    	{
	    		VertNode highestDegreeV = (VertNode) minPQ.delMin();
	    		Bag currentBag = highestDegreeV.getBag();
	    		Bag neighbor;
	        	boolean[] neighborColors = new boolean[currentBag.size()+1];
	        	int lowestColor=1;
	    		for(int vertex: graph.neighbors(currentBag.getId()))
	    		{
	    			neighbor = graph.get(vertex);
	    			neighborColors[neighbor.getColor()] = true;
	    		}
	    		
	    		for(int i=1; i<neighborColors.length; i++) //index zero is not counted
	    		{
	    			if(!neighborColors[i])
	    			{
	    				lowestColor = i;
	    				break;
	    			}
	    		}
	    		if(lowestColor>highestColor)
	    		{
	    			highestColor = lowestColor;
	    			vWithHighestColor = currentBag.getId();
	    		}
	    		currentBag.setColor(lowestColor);
	    	}
    	}
    	System.out.println(colorCount(graph));
    	
    	
    	
    	
//////////////////////////////////////////////////////////trying to start with v's with highest colors
    	if(colorCount(graph)>MAX_NUMBER_COLORS)
    	{
    		for(Bag b: graph)
    		{
    			b.setColor(0);
    		}
	    	while(!minPQcolor.isEmpty())
	    	{
	    		VertNode highestColorV= (VertNode) minPQcolor.delMin();
	    		Bag currentBag = highestColorV.getBag();
	    		Bag neighbor;
	        	boolean[] neighborColors = new boolean[currentBag.size()+1];
	        	int lowestColor=1;
//	        	int 
	    		for(int vertex: graph.neighbors(currentBag.getId()))
	    		{
	    			neighbor = graph.get(vertex);
	    			neighborColors[neighbor.getColor()] = true;
	    		}
	    		
	    		for(int i=1; i<neighborColors.length; i++) //index zero is not counted
	    		{
	    			if(!neighborColors[i])
	    			{
	    				lowestColor = i;
	    				break;
	    			}
	    		}
	    		currentBag.setColor(lowestColor);
	    	}
    	}
    	System.out.printf("the largest degree of a vertex is %d ",maxDegree);
    }
    
    private static boolean allVsColored(Graph g) {
		return coloredVs == g.V();
	}

	public Coloring(Graph graph)
    {

    	ArrayList<VertNode> vnArr = new ArrayList(graph.V());
    	VertNode vn;
        for (Bag v : graph)
        {
        	vn = new VertNode(v);
        	vnArr.add(vn);
        }
        
        Collections.sort(vnArr);
    }
    
    
    //--------------- DO NOT MODIFY ANYTHING BELOW THIS LINE -----------------//
    
    /**
     * Determines the distinct number of colors for the specified graph.
     * @param graph
     * @return number of distinct colors
     */
    public static final int colorCount( Graph graph )
    {
        ChainingSymbolTable<Integer, Integer> colors =
                new ChainingSymbolTable<Integer,Integer>();
        for (Bag v : graph)
        {
            Integer vColor = v.getColor();
            if( colors.containsKey(vColor) == false )
            {
                colors.put(vColor, 0);
            }
            int count = colors.get(v.getColor());
            colors.put( v.getColor(), count+1);
        }
        
        return colors.size();
    }
    
    /**
     * Determines the number of color violations for the specified graph.
     * A valid coloring will have zero violations.
     * @param graph
     * @return number of violations for all vertices in the graph
     */
    public static final int violations( Graph graph )
    {
        int violations = 0;
        
        for (Bag v : graph)
        {
            violations += vertexViolations( graph, v );
        }
        return violations;
    }
    
    /**
     * Determines the number of color violations for the specified vertex.
     * A valid vertex will have zero violations.
     * @param graph
     * @param v
     * @return number of violations for v
     */
    public static final int vertexViolations( Graph graph, Bag v )
    {
        int violations = 0;
        
        for (int vNeighbor : graph.neighbors(v.getId()))
        {
            if( graph.get(vNeighbor).getColor() == v.getColor() )
            {
                violations++;
            }
        }
        return violations;
    }
    
    /**
     * The main class
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException
    {
        if( args.length != 1 )
        {
            String u = "Usage: Coloring <filename>";
            Stdio.println(u);
            return;
        }
        
        String fileName = args[0];
        Graph graph = GraphFactory.make(fileName);
        
        Clock clock = new Clock();
        color(graph);
        Stdio.println( "Took(ms): "+ clock.elapsed() );
        
        // prepare the solution in the specified output format
        Stdio.println( "valid? "+ (violations(graph)==0) +" #colors="+ colorCount(graph) );
        for (Bag v : graph)
        {
            Stdio.print(v.getColor() + " ");
        }
        Stdio.println("");
    }
    
}


//Coloring c = new Coloring(graph);
//
////int i = vnArr.size();    	
////start with random node
//Random r = new Random();
//int source = r.nextInt(graph.V());
//colorArr = new boolean[graph.V()-1];
//
//
//int[] colorArr	= new int[graph.V()+1]; //maybe plus one not necessary
//for(int x = 0; <graph.V()+1; x++)
//	colorArr[x] = -1;
//
////first node
//Bag v = graph.get(source);
//colorArr[v.getId()] = BASE_COLOR;
