public class Queue2Stacks
{
    private final Stack stack1;
    private final Stack stack2;
    
    public Queue2Stacks()
    {
        this.stack1 = new Stack();
        this.stack2 = new Stack();
    }
    
    public int size()
    {
        return stack1.size() + stack2.size();
    }
    
    public boolean isEmpty()
    {
        return size() == 0;
    }
    
    public void enqueue( Object item )
    {
        stack1.push(item);
    }
    
    public Object dequeue( )
    {
        if( isEmpty() )
        {
            throw new IllegalArgumentException("No item to dequeue");
        }
        
        // If some are on stack2, must use them, otherwise empty 1 to 2
        if( stack2.isEmpty() )
        {
            // empty stack1 to stack 2, makes FILO, FIFO
            while( stack1.isEmpty() == false )
            {
                stack2.push( stack1.pop() );
            }
        }
        Object item = stack2.pop();
        return item;
    }
    
    public String toString()
    {
        StringBuilder buf = new StringBuilder();
        buf.append( "Stack1="+this.stack1.toString() );
        buf.append( " Stack2="+this.stack2.toString() );
        return buf.toString();
    }
    
    
    /**
     * Unit tests the Queue data type.
     * @param args 
     */
    public static void main( String[] args )
    {
        Queue2Stacks queue = new Queue2Stacks();
        
        Stdio.open( args[0] );
        while( Stdio.hasNext() )
        {
            String item = Stdio.readString();
            if( ! item.equals("-") )     queue.enqueue(item);
            else if( ! queue.isEmpty() ) Stdio.print( queue.dequeue() +" " );
        }
        Stdio.println( "" );
        Stdio.println( "Final queue=" +queue.toString() );
        Stdio.close();
    }
}
