/**
 * 
 */
package problems;

/**
 * @author or13uw
 *
 */
public class Points extends PointsAbstract  {

	
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
	
	
}
