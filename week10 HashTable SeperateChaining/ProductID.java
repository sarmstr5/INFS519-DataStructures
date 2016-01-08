/**
 * ProductID uniquely identifies a product.  Should be IMMUTABLE!!!
 */
public class ProductID
{
    private final String epc;
    private final int serialNumber;
    
    public ProductID(String epc, int serialNumber)
    {
        this.epc = epc;
        this.serialNumber = serialNumber;
    }
    
    @Override
    public int hashCode()
    {
    	int hash = 17; //low prime
    	hash = 31*hash + epc.hashCode();
    	hash = 31*hash + ((Integer)serialNumber).hashCode();
    	
        return hash &0x7FFFFFFF; //make positive
    }

    @Override
    public boolean equals(Object obj)
    {
    	if(obj == this) return true;
    	if(!(obj instanceof ProductID)) return false;  //i am comparing entries so this may not be a good comparison
    	ProductID that = (ProductID) obj;
    	return (this.epc.equals(that.epc) &&
    			this.serialNumber == that.serialNumber);
    }
    
    @Override
    public String toString()
    {
        return epc + ","+ serialNumber;
    }
}
