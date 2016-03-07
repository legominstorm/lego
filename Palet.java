
public class Palet {
	private double x;
	private double y;
	private int ID;
	private boolean noter;
	//private boolean moving;
	Palet()
	{
		
		x=0;
		y=0;
		ID=0;
		noter=false;
		//moving=false;
	}
	Palet(double x,double y)
	{
		this.x=x;
		this.y=y;
		noter=false;
		//moving=false;
	}		
	Palet(int ID, double x,double y)
	{
		this.ID=ID;
		this.x=x;
		this.y=y;
				
	}
	public boolean getNoter()
	{
		return noter;
	}
	public void setNoter(boolean noter)
	{
		this.noter=noter;
	}	
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	public void setPos(double x,double y)
	{
		this.x=x;
		this.y=y;
	}

}