
/**
 * Triangle recursive program.  Demonstrates recursion converted into stack.
 * @author James Pope
 */
public class Triangle
{
    private static class ActivationRecord
    {
        private final Integer[] params;
        public ActivationRecord(int arg1)
        {
            this.params = new Integer[]{arg1};
        }
        public ActivationRecord(int arg1, int arg2)
        {
            this.params = new Integer[]{arg1, arg2};
        }
        @Override
        public String toString()
        {
            StringBuilder b = new StringBuilder("[");
            for (int i = 0; i < params.length; i++)
            {
                if( i != 0 ) b.append(",");
                b.append(params[0]);
            }
            b.append("]");
            return b.toString();
        }
    }

    // Explicit stack approach
    public static final int triangleStack( int n )
    {
        Stack<ActivationRecord> stack = new Stack<ActivationRecord>();
        
        int paramN = n;
        Stdio.println("Pushing onto the stack.");
        while( paramN > 0 )
        {
            ActivationRecord ar = new ActivationRecord(paramN);
            stack.push( ar );
            Stdio.println( stack );
            paramN--;
        }
        
        int sum = 0;
        Stdio.println("Popping from the stack.");
        while( !stack.isEmpty() )
        {
            ActivationRecord ar = stack.pop();
            Stdio.println( stack );
            sum = sum + ar.params[0];
        }
        return sum;
    }
    
    public static void main( String[] args )
    {
        if( args.length != 1 )
        {
            String u = "Usage: Triangle <n>";
            Stdio.println(u);
            return;
        }
        
        int n = Integer.parseInt(args[0]);
        if( n < 1 ) throw new IllegalArgumentException("n >= 1");       

        
        Stdio.println( "N = " +n);
        Stdio.println( "Explicit Stack Approach: "+ triangleStack(n));
        Stdio.println( "     Recursive Approach: "+ triangleRecursive(n));
        Stdio.println( "     Iterative Approach: "+ triangleIterative(n));
    }
    
    // Recursive approach
    public static final int triangleRecursive( int n )
    {
        if( n == 1 ) return 1;
        else return n + triangleRecursive( n-1 );
    }

    // Iterative approach
    public static final int triangleIterative( int n )
    {
        int sum = 0;
        int i = 1;
        while( i <= n  )
        {
            sum = sum + i;
            i++;
        }
        return sum;
    }

    
}
