/**
 * 
 */
package problems;

/**
 * @author or13uw
 *
 */
public class Points implements Comparable {

	
	public double x;
	public double y;
	public int id;
	
	/*Points(double xCoordinate, double yCoordinate)
	{
		
		this.x = xCoordinate;
		this.y= yCoordinate;
	}
	*/
	public Points(int mid, double xCoordinate, double yCoordinate)
	{
		this.id = mid;
		this.x = xCoordinate;
		this.y= yCoordinate;
	}
	
	
	

	public Points(int id) {
		this.id = id;

	}

	@Override
	public boolean equals(Object obj) {
		Points p = (Points) obj;

		if (this.id == p.id) {
			return true;
		}
		return false;

	}

	public int hashCode() {
		return java.util.Objects.hashCode(this.id);
	}
	
	@Override
	public String toString()
	{
		return String.valueOf(this.id);
		
	}




	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object obj) {
		Points p = (Points) obj;

		int returnValue = 0;
		if(p.id > this.id)
			returnValue =  -1;
		if(p.id == this.id)
			returnValue = 0 ;
		if(p.id < this.id)
			returnValue = 1;
		
		return returnValue;
		
	}
	
	
	
}
