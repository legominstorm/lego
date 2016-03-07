import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;

public class DifferentialDrive
{
    private EV3LargeRegulatedMotor mLeftMotor;
    private EV3LargeRegulatedMotor mRightMotor;
    private EV3LargeRegulatedMotor mArm;
    private final static int rotateArm=180;
    private final static int nbArmAction=4;

    private final static int SPEED = 50;


    public DifferentialDrive(Port left_port, Port right_port,Port arm)
    {
        mLeftMotor = new EV3LargeRegulatedMotor(left_port);
        mRightMotor = new EV3LargeRegulatedMotor(right_port);
        mArm =new EV3LargeRegulatedMotor(arm);

        mLeftMotor.setSpeed(SPEED);
        mRightMotor.setSpeed(SPEED);
    }


    public void forward()
    {
        mLeftMotor.forward();
        mRightMotor.forward();
    }


    public void stop()
    {
        mLeftMotor.stop();
        mRightMotor.stop();
    }


    public void rotateClockwise()
    {
        mLeftMotor.forward();
        mRightMotor.backward();
    }


    public void rotateCounterClockwise()
    {
        mLeftMotor.backward();
        mRightMotor.forward();
    }
    public void catchPalet()
    {
    	for(int i=0;i<nbArmAction;i++)
    	{  		    	
    		mArm.rotate(-rotateArm);
    	}
    }
    public void dropPalet()
    {
    	for(int i=0;i<nbArmAction;i++)
    	{
    		mArm.rotate(rotateArm);
    	}
    }
}
