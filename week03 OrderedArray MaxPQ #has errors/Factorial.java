
/**
 * Factorial recursion example.
 * @author James Pope
 */
public class Factorial
{
    /**
     * Unit tests factorial method.
     * @param args 
     */
    public static void main( String[] args )
    {
        System.out.println( "5!="+factorial(5) );
    }
    
    public static int factorial(int n)
    {
      // Base Case
      if(n <= 1)
      {
          System.out.println( "n="+n+" return="+1 );
          return 1;
      }
      // Recursive Case
      else
      {
          int x = n * factorial(n-1);
          System.out.println( "n="+n+" return="+x );
          return x;
      }
    }
}
