
public class Position {
			private double x;
			private double y;
			private double degree;
			Position()
			{
				this.x=0;
				this.y=0;
				this.degree=0;
			}
			Position(double x,double y,double degree)
			{
				this.x=x;
				this.y=y;
				this.degree=degree;
			}
			Position(double x,double y)
			{
				this.x=x;
				this.y=y;
				this.degree=0;
			}
			Position(Position pos)
			{
				x=pos.x;
				y=pos.y;
				degree=pos.degree;
			}
			/* un position est un tableau qui contient 3 data : x, y ,degree.
			 * on peut toujours return le tableau pour gagner le position de robot
			 */
			public double[] getPosition()
			{
				double []position=new double[3];
				position[0]=x;
				position[1]=y;
				position[2]=degree;
				return position;
			}
			
			public double getX()
			{
				return x;
			}
			
			public double getY()
			{
				return y;
			}
			
			public double getdegree()
			{
				return degree;
			}
			
			public void setPosition(double[] position)
			{
				x=position[0];
				y=position[1];
				degree=position[2];
			}
			public void setX(double x)
			{
				this.x=x;
			}
			public void setY(double y)
			{
				this.y=y;
			}
			public void setDegree(double degree)
			{
				this.degree=degree;
			}
			
}
