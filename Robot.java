import java.util.HashMap;
import java.util.Map;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

public class Robot {
		private Position pos;
		private EV3LargeRegulatedMotor mLeftMotor;
	    private EV3LargeRegulatedMotor mRightMotor;
	    private EV3LargeRegulatedMotor mArm;
	    private DifferentialPilot mPilot;
	    private UltrasonicSensor ultrasonicSensor;
	    private TouchSensor touchSensor;
	    private boolean isCatch=false;
	    
	    private final static int rotateArm=180;
	    private final static int nbArmAction=4;
	    private final static int SPEED = 180;
	    private final static double PI=Math.PI;
	    private final static double diameter=7.5;
	    private final static double trackwidth=16.5;
	    private static double erreur=0.1;
	    
	    public Robot(Port left_port,Port right_port,Port arm_port,Position pos)
	    {
	    	mLeftMotor = new EV3LargeRegulatedMotor(left_port);
	    	mRightMotor= new EV3LargeRegulatedMotor(right_port);
	    	mArm= new EV3LargeRegulatedMotor(arm_port);
	    	ultrasonicSensor=new UltrasonicSensor(SensorPort.S3);
	    	touchSensor=new TouchSensor(SensorPort.S2);
	    	this.pos=new Position(pos);
	    	mPilot=new DifferentialPilot(diameter,trackwidth,mLeftMotor,mRightMotor);
	    	mLeftMotor.setSpeed(SPEED);
	    	mRightMotor.setSpeed(SPEED);
	    }
	    public void forward(){
	    	mLeftMotor.forward();
	    	mRightMotor.forward();
	    }
	    public void backward()
	    {
	    	mLeftMotor.backward();
	    	mRightMotor.backward();
	    }
	    public void RotateClockWise()
	    {
	    	mRightMotor.backward();
	    }
	    public void RotateAntiClockWise()
	    {
	    	mLeftMotor.backward();
	    }
	    public void RotateClockWise(double angle)
	    {	
	    	angle=0-angle;
	    	mPilot.rotate(angle);
	    	//System.out.println(pos.getdegree()+angle);
	    	pos.setDegree(pos.getdegree()+angle);	    	
	    	//Delay.msDelay(10000);
	    }
	    public void RotateAntiClockWise(double angle)
	    {
	    	mPilot.rotate(angle);
	    	pos.setDegree(pos.getdegree()+angle);
	    }
	    public void Stop()
	    {
	    	mPilot.stop();
	    }
	    public Position getPos()
	    {
	    	return pos;
	    }
	    public void forward(double distance)
	    {
	    	mPilot.travel(distance);
	    	double[] position=new double[3];
	    	double x=0;
	    	double y=0;	    	
	    	System.out.println("pos.getX:"+pos.getX());
	    	System.out.println("pos.getY:"+pos.getY());
	    	x=pos.getX()+distance* Math.cos(pos.getdegree()*(Math.PI/180));
	    	y=pos.getY()+distance* Math.sin(pos.getdegree()*(Math.PI/180));
	    	System.out.println("x:"+x);
	    	System.out.println("y:"+y);
	    	System.out.println("degree:"+pos.getdegree());
	    	//Delay.msDelay(10000);
	    	position[0]=x;
	    	position[1]=y;
	    	position[2]=pos.getdegree();
	    	pos.setPosition(position);
	    	
	    }
	    public void backward(double distance)
	    {
	    	mPilot.travel(-distance);
	    	double[] position=new double[3];
	    	double x=0;
	    	double y=0;
	    	x=pos.getX()-distance* Math.cos(pos.getdegree());
	    	y=pos.getY()-distance* Math.sin(pos.getdegree());
	    	position[0]=x;
	    	position[1]=y;
	    	position[2]=pos.getdegree();
	    	pos.setPosition(position);
	    	
	    }
	    public void catchPalet()
	    {
	    	for(int i=0;i<nbArmAction;i++)
	    	{  		    	
	    		mArm.rotate(-rotateArm);
	    	}
	    	isCatch=true;
	    }   
	    public void dropPalet()
	    {
	    	for(int i=0;i<nbArmAction;i++)
	    	{
	    		mArm.rotate(rotateArm);
	    	}
	    	isCatch=false;
	    }
	    public UltrasonicSensor getUS()
	    {
	    	return ultrasonicSensor;
	    }
	    public TouchSensor getTS()
	    {
	    	return touchSensor;
	    }
	    public void arrivePalet(Palet palet)
	    {
	    	double degree=0;
	    	double xRobot=pos.getX();
	    	double yRobot=pos.getY();
	    	//System.out.println("position de x:"+pos.getX()+"position de y:"+pos.getY());
	    	//Delay.msDelay(10000);
	    	double xPalet=palet.getX();
	    	double yPalet=palet.getY();
	    	//System.out.println("position de palet de x:"+palet.getX()+"position de palet de y:"+palet.getY());
	    	//Delay.msDelay(10000);
	    	double arc=0;
	    	double cosRob;
	    	arc=Math.sqrt(Math.pow(xRobot-xPalet, 2)+Math.pow(yRobot-yPalet, 2));
	    	//System.out.println("le arc: "+arc);
	    	//Delay.msDelay(10000);
	    	cosRob=(xPalet-xRobot)/arc;
	    	//System.out.println("cosRob:"+cosRob);
	    	//Delay.msDelay(10000);
	    	if((yPalet-yRobot)>0)
	    	{
	    		degree=Math.acos(cosRob)*(180/Math.PI);
	    		//System.out.println("degree:"+degree);
	    		//Delay.msDelay(10000);
	    	}
	    	else
	    	{
	    		degree=Math.acos(cosRob)*(180/Math.PI)+180;
	    	}
	    	RotateClockWise(pos.getdegree()-degree);
	    	forward(arc);
	    }
	    public void arrivePos(Position position)
	    {
	    	double degree=0;
	    	double xRobot=pos.getX();
	    	double yRobot=pos.getY();
	    	double xPos=position.getX();
	    	double yPos=position.getY();
	    	double arc=0;
	    	double cosRob=0;	    	
	    	arc=Math.sqrt(Math.pow(xRobot-xPos, 2)+Math.pow(yRobot-yPos, 2));
	    	cosRob=(xPos-xRobot)/arc;
	    	if((yPos-yRobot)>0)
	    	{
	    		degree=Math.acos(cosRob)*(180/Math.PI);
	    	}
	    	else
	    	{
	    		degree=360-(Math.acos(cosRob)*(180/Math.PI));
	    	}
	    	System.out.println(degree);
	    	RotateClockWise(pos.getdegree()-degree);
	    	forward(arc);
	    }
	    public int meillerPalet(Palet[] palet_table,Position pos )
	    {
	    	int i=0;
	    	double min=9999;
	    	double distance[] =new double[10];
	    	int j=0;
	    	double dis=0;
	    	for(j=0;j<10;j++)
	    	{
	    		if(palet_table[j].getNoter())
	    		{
	    			continue;
	    		}else
	    		{
	    			dis=Math.sqrt(Math.pow(pos.getX()-palet_table[j].getX(), 2)+Math.pow(pos.getY()-palet_table[j].getY(), 2));
	    			distance[j]=dis;
	    		}
	    		if(min>dis)
	    		{
	    			min=dis;
	    			i=j;
	    		}	    		
	    	}
	    	System.out.println("le minimum palet is :"+i);
	    	return i;
	    }
		public boolean test_erreur(Position finalpos) {
			// TODO Auto-generated method stub
		
			if(pos.getX()<=(finalpos.getX()+erreur)&&pos.getX()>=(finalpos.getX()-erreur)&&pos.getY()<=(finalpos.getY()+erreur)&&pos.getY()>=(finalpos.getY()-erreur))
			{
				return true;
			}
			return false;
		}
		public boolean getisCatch()
		{
			return isCatch;
		}
		public void setisCatch(boolean Catch)
		{
			isCatch=Catch;
		}
	    
}
