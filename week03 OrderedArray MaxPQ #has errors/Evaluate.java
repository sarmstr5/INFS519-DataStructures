
/**
 * Dijkstra two-stack algorithm to evaluate expressions.
 * ( 2 * ( 3 + 4 ) ) => 2 3 4 + * = 14
 * @author James Pope
 */
public class Evaluate
{
    /**
     * Unit tests the Stack data type.
     * @param args 
     */
    public static void main( String[] args )
    {
        Stack ops  = new Stack();
        Stack vals = new Stack();
        
        Stdio.open( args[0] );
        while( Stdio.hasNext() )
        {
            String s = Stdio.readString();
            if( s.equals("(") )                    ; // ignore
            else if( s.equals("+") )    ops.push(s);
            else if( s.equals("-") )    ops.push(s);
            else if( s.equals("*") )    ops.push(s);
            else if( s.equals("/") )    ops.push(s);
            else if( s.equals("sqrt") ) ops.push(s);
            else
            {
                if( s.equals(")") )
                {
                    String op = (String)ops.pop();
                    Double v1 = (Double)vals.pop();
                    
                    if( op.equals("sqrt") )
                    {
                        v1 = Math.sqrt(v1);
                    }
                    else
                    {
                        Double v2 = (Double)vals.pop();
                        if(      op.equals("+") ) v1 = v1 + v2;
                        else if( op.equals("-") ) v1 = v1 - v2;
                        else if( op.equals("*") ) v1 = v1 * v2;
                        else if( op.equals("/") ) v1 = v1 / v2;
                    }
                    vals.push(v1);
                }
                else
                {
                    vals.push( Double.parseDouble(s) );
                }
            }
        }
        Stdio.close();
        // Last value is the solution
        Stdio.println( "Solution=" +vals.pop() );
    }
       
}
