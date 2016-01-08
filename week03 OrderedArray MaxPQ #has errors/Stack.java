
/**
 * Stack implemented using dynamically sized array.
 * @author James Pope
 * @param <Type>
 */
public class Stack <Type>
{
    private static final int DEFAULT_CAPACITY = 8;
    
    private Type[] items;
    private int size;

    public Stack()
    {
        this.items = (Type[])new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public void push(Type item)
    {
        // If not eough room , expand
        if( this.size == this.items.length )
        {
            grow();
        }
        this.items[size] = item;
        this.size++;
    }
    
    public Type pop()
    {
        if( this.isEmpty() )
        {
            throw new IllegalArgumentException("No item to pop");
        }
        this.size--;
        Type item = this.items[size];
        this.items[size] = null; // prevent loitering
        return item;
    }
    
    public Type peek()
    {
        return this.items[size-1];
    }
    
    public int size()
    {
        return this.size;
    }
    
    public boolean isEmpty()
    {
        return this.size == 0;
    }

    private void grow()
    {
        Type[] newItems = (Type[])new Object[this.items.length*2];
        System.arraycopy(this.items, 0, newItems, 0, this.items.length);
        this.items = newItems;
    }
    
    @Override
    public String toString()
    {
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for (int i = 0; i < this.size; i++)
        {
            Type item = items[i];
            if( i != 0 ) buf.append(", ");
            buf.append( item.toString() );
        }
        buf.append("]");
        return buf.toString();
    }
    
    /**
     * Unit tests the Stack data type.
     * @param args 
     */
    public static void main( String[] args )
    {
        if( args.length == 0 ) userMain(args);
        else                   testMain(args);
    }
    
    public static void testMain( String[] args )
    {
        Stack<String> stack = new Stack<String>();
        
        Stdio.open( args[0] );
        while( Stdio.hasNext() )
        {
            String item = Stdio.readString();
            if( ! item.equals("-") )     stack.push(item);
            else if( ! stack.isEmpty() ) stack.pop();
        }
        Stdio.println( "Final stack=" +stack.toString() );
        Stdio.close();
    }
    
    public static void userMain( String[] args )
    {
        String[] options = new String[]
        {
            "Push",
            "Pop",
            "Peek",
            "Exit"
        };
        
        Stack<String> stack = new Stack<String>();
        
        Stdio.open();
        boolean exit = false;
        while( exit == false )
        {
            int option = Stdio.getOption(options);
            if( option == 0 )
            {
                String item = Stdio.readString("item=");
                stack.push(item);
            }
            else if( option == 1 )
            {
                Object item = stack.pop();
                Stdio.println( "item="+ item.toString() );
            }
            else if( option == 2 )
            {
                Object item = stack.peek();
                Stdio.println( "item="+ item.toString() );
            }
            else if( option == 3 )
            {
                exit = true;
            }
            Stdio.println( "stack="+stack );
        }
        
        Stdio.println("That's all folks!");
        Stdio.close();
    }
    
}
