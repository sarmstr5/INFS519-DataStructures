
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class uses the Huffman algorithm to compress and decompress text files
 * Coding algorithms as presented in INFS 519
 * Assuming ASCII only characters
 * @author Shane Armstrong
 */
public class Huffman
{

    /**
     * This method only works with ASCII characters. Anything outside of ASCII will possibly throw out of bounds erros or will not count correctly
     * @param char array of text to be compressed
     * @return int array of ASCII char frequencies
     */
    public static int[] determineFrequencies( char[] text )
    {

    	int[] radixFreq = new int[RADIX];
    	int nonASCIIcount =0;
    	
    	for(int i=0; i<text.length;i++){
    		
    		//cast char to into to get ASCII number index, increment that index
    		int asciiIndex = (int) text[i]; 
    		if(asciiIndex<=RADIX) //assuming no negatives
    			radixFreq[asciiIndex]++; 

    		else
    			nonASCIIcount++;
    	}
    	if(nonASCIIcount!=0)
    		System.out.println("There were " + nonASCIIcount +" nonASCII characters");
    	//System.out.println(radixFreq[65]);
    	return radixFreq;
    }

    /**
     * Optimally compresses text file using Hoffman's algorithm
     * @param char array of uncompressed text
     * @return byte array resenting the trie, number of symbols in original text, and compressed text
	*/
    public static byte[] compress( char[] text )
    {
    	/*
    	Compression steps (using alphabet of 256 symbols)
    	1.Read the input text
    	2.Determine frequency of each symbol (i.e. char)
    	3.Build Huffman encoding trie using frequencies, root is the trie
    	4.Build coding table from trie
    	5.Write trie as a bitstring
    	6.Write count of symbols in the input text
    	7.Write the text as a bitstring using the coding table
    	*/
    	
    	//2.
		int[] radixFreqs = determineFrequencies(text);
    	
		//3.
		Node trie = makeTrie(radixFreqs);
		
    	// 4. 
    	String[] table = new String[RADIX];
    	makeCodingTable( table, trie, "" );
    	
    	// 5. 
    	BitStreamOutput out = new BitStreamOutput();
    	writeTrie(trie, out);
    	
    	// 6. 
    	out.writeBits(text.length, 31);

    	
    	// 7. 
    	for( int i = 0; i < text.length; i++ )
    	{
    		String code = table[(int) text[i]]; //the index is a char's ascii number
    		for(int x = 0; x < code.length() ;x++){
    			if(code != null){
	    			if(code.charAt(x) =='0') out.writeBit(false);
	    			else out.writeBit(true);
    			}
    		}
    	}

    	out.flush(); // Important!!! May miss last byte if not called
    	return out.toArray();

    }
    
    /**
     * Decompresses files compressed files by this class not guaranteed to decompress other compressed files
     * @param compressed file using Huffman class
     * @return array of uncompressed chars representing original text
     */
    public static char[] decompress(BitStreamInput in)
    {
    	/*
    	Decompression steps
		1.Read the trie (should be at beginning of bitstream)
		2.Read count of symbols encoded
		3.Use the coding table to decode the bitstream
    	 */

		// 1. 
		Node trie = readTrie(in);

		// 2.
		int lengthOfOriginal = in.readBits(31);
		char[] decompressedText = new char[lengthOfOriginal];

		// 3.
		//using bit representations to go down trie until reaching a symbol node then adding to decompressedText array
		Node currentNode;
		boolean atLeaf;
		for (int i = 0; i < lengthOfOriginal; i++)
		{
			atLeaf=false;
			currentNode=trie;
			
			while(!atLeaf){
				
				atLeaf = currentNode.isLeaf();
				if(atLeaf)
					decompressedText[i]=currentNode.getSymbol();
				
				else{
					boolean left = !(in.readBit()); // 0/false is left, 1/true is right
					currentNode = (left) ? currentNode.left: currentNode.right;
				}
			}
		}
		return decompressedText;
        
    }


    
    /**
     * Converts a frequency table of ASCII characters to a trie using a min heap
     * The min heap is first created using the frequency table
     * Then the min heap is converted to a trie by removing min values and combining them with a null node as their parent
     * The parent is put back into the min heap with its frequency as the combination of its children
     * This algorithm is O(R*lg(R))
     * @param frequency array of compressed text that is naively indexed to ASCII chars (e.g. freq[65] = number of 'A's in original text)
     * @return root of optimal Trie
     */
    public static Node makeTrie( int[] freq )
    {
    	MinHeap<Node> pq = new MinHeap<Node>(freq.length);
    	for( char i = 0; i < freq.length; i++ ){
    		if(freq[i]>0)
        		pq.insert(new Node((char) i, freq[i]));
    	}
    	
    	// Special handling if only one "tree"
    	if(pq.size() ==1)
    		pq.insert(new Node((char)0,0)); //add null node
    	
    	// Merge all the sub-trees into one rooted tree
    	// Remove two smallest
    	// Create new node with sum of their frequencies
    	// Add new node back to priority queue
    	while( pq.size() > 1 )
    	{
    		Node left = pq.delMin();
	    	Node right = pq.delMin();
	    	Node parent = new Node((char) 0, left, right, left.getFreq()+right.getFreq() ); 
	    	pq.insert(parent);
    	}
    	
    	// the final node is the root of the completed trie
    	return pq.delMin();
    }
    

    /**
     * Recursively creates the coding table traversing trie in in-order
     * The path to a symbol is represented by 0's and 1's. 0 represents left, 1 right
     * @param ASCII naively indexed String array, node representing node currently at, string showing binary representation of the node in trie
     */
    public static void makeCodingTable( String[] table, Node x, String code )
    {

		// Base case, reached a leaf
    	if(x.isLeaf()){
    		table[(int) x.getSymbol()] = code;
    		return;
    	}
    	
		makeCodingTable( table, x.left, code+'0' );
		makeCodingTable( table, x.right, code+'1' );

    }



    //----- DO NOT MODIFY ANYTHING BELOW THIS LINE -----//

    public static final int RADIX = 256; // number of symbols for extended ascii

    private static class Node implements Comparable<Node>
    {
        private Node left;
        private Node right;
        private char symbol;
        private int freq;    

        public Node( char c, int freq )
        {
            this(c, null, null, freq);
        }

        public Node( char symbol, Node left, Node right, int freq )
        {
            this.symbol = symbol;
            this.left   = left;
            this.right  = right;
            this.freq   = freq;
        }

        public Node getLeft() { return this.left; }
        public Node getRight() { return this.right; }
        public char getSymbol() { return this.symbol; }
        public int getFreq() { return this.freq; }
        public boolean isLeaf() { return this.left == null && this.right == null; }

        public int compareTo( Node other )
        {
            return this.freq - other.freq;
        }
        
        @Override
        public String toString()
        {
            return "("+this.symbol+" " + freq+")";
        }
    }
    

    public static void writeTrie(Node x, BitStreamOutput out)
    {
        // Use preorder traversal to encode the trie
        if (x.isLeaf())
        {
            out.writeBit(true);
            out.writeBits(x.symbol, 8);
            return;
        }
        out.writeBit(false);
        writeTrie(x.left,  out);
        writeTrie(x.right, out);
    }

    public static Node readTrie( BitStreamInput in )
    {
        boolean bit = in.readBit();
        if( bit )
        {
            char symbol = (char)in.readBits(8);
            return new Node(symbol, 0);
        }
        Node internalNode = new Node('\0', 0);
        internalNode.left  = readTrie( in );
        internalNode.right = readTrie( in );

        return internalNode;
    }
    
    public static void printTable(String[] table)
    {
        for( int i = 0; i < table.length; i++ )
        {
            String code = table[i];
            if(code != null) Stdio.println(""+((char)i) + " = " +code );
        }
    }
    
    
    /**
     * Unit tests the Huffman compression/decompression algorithm.
     * @param args 
     * @throws java.io.IOException 
     */
    public static void main( String[] args ) throws IOException
    {
        if( args.length != 2 )
        {
            String u = "Usage: Huffman <+|-> <filename>";
            Stdio.println(u);
            return;
        }
        
        String option   = args[0];
        String filename = args[1];
        if( option.equals("-") )
        {
            BufferedReader fileIn = new BufferedReader( new FileReader(filename) );
            StringBuilder buf = new StringBuilder();
            int nextChar = fileIn.read();
            while( nextChar != -1 )
            {
                buf.append((char)nextChar);
                nextChar = fileIn.read();
            }
            fileIn.close();
            
            char[] text = buf.toString().toCharArray();
            
            byte[] compressedText = compress(text);
            
            FileOutputStream fos = new FileOutputStream( filename+".zip" );
            BufferedOutputStream file = new BufferedOutputStream(fos);
            file.write(compressedText);
            file.close();
        }
        else if( option.equals("+") )
        {
            // READ IN THE FILE
            FileInputStream fis = new FileInputStream( filename );
            BufferedInputStream file = new BufferedInputStream(fis);
            
            byte[] compressedText = new byte[16];
            int size = 0;
            int byteRead = file.read();
            while( byteRead != -1 )
            {
                if( size == compressedText.length )
                {
                    byte[] newCompressedText = new byte[compressedText.length*2];
                    System.arraycopy(compressedText, 0, newCompressedText, 0, compressedText.length);
                    compressedText = newCompressedText;
                }
                
                compressedText[size++] = (byte)byteRead;
                byteRead = file.read();
            }            
            file.close();
            
            BitStreamInput in = new BitStreamInput(compressedText);
            char[] decompressedText = decompress( in );
            
            String text = new String(decompressedText);
            Stdio.println("Decompressed: "+text);
        }
        else
        {
            Stdio.println("Invalid option: "+option);
        }
        
    }
}
