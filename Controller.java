import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;

public class Controller implements Runnable
{
    private final static int DELAY = 25;

    private final static int SKIP_FORWARD = 10;

    private final static float DELAYS_PER_DEG = 100.0f / 180;

    private final static int SMALL_ROT = 5;
    private final static int MED_ROT = 10;
    private final static int MAX_ROT = 30;
    private boolean isPressPalet=false;
    private boolean isCatchPalet=false;
    private boolean isFindPalet=false;
    private static boolean end =false;

    private ColorSensor sensor;
    private DifferentialDrive drive;
    private UltrasonicSensor ultrasonicSensor;
    private Thread Sensorthread;
    private TouchSensor touchSensor;

    public Controller(Port sensor_port, Port left_port, Port right_port,Port arm)
    {
        log("Initializing Controller");

        sensor = new ColorSensor(sensor_port);
        ultrasonicSensor=new UltrasonicSensor(SensorPort.S3);
        touchSensor=new TouchSensor(SensorPort.S2);
        drive = new DifferentialDrive(left_port, right_port,arm);
        Sensorthread=new Thread(new Runnable(){
        	
			public void run() {
				// TODO Auto-generated method stub
			while(!end){
				log("running search");
				while(!ultrasonicSensor.findPalet())
				{
					log("not find");
				}
				while(!touchSensor.isPressed())
				{
					log("wait touche");
				}
				isPressPalet=true;
				catchPalet();				
				while(!ending())
				{
					//log("not find2");
					if(ultrasonicSensor.findPalet())
					{	
						log("find");
						isFindPalet=true;
						delay();
						evitePalet();
						isFindPalet=false;
					}
				}
				dropPalet();
			}
				
			}
        	
        });
    }
    public void catchPalet()
    {
    	log("catchPalet");
    	drive.catchPalet();
    	isPressPalet=false;
    	isCatchPalet=true;
    }
    public void dropPalet()
    {
    	log("dropPalet");
    	drive.dropPalet();
    	isCatchPalet=false;
    }
    public void evitePalet()
    {	
    	for (int i = 0; i < 20; i++)
    	{
    			delay();
    			drive.rotateClockwise();
    	}
    	for(int i=0;i<20;i++)
    	{
    		delay();
    		drive.forward();
    	}
    	for(int i=0;i<40;i++)
    	{
    		delay();
    		drive.rotateCounterClockwise();
    	}
    	for(int i=0;i<20;i++)
    	{
    		drive.forward();
    	}
    	for(int i=0;i<20;i++)
    	{
    		drive.rotateClockwise();
    	}
    	
    		
    }
    public boolean ending()
    {	
    	if(Button.ENTER.isDown())
    	{
    		return true;
    	}
    	return false;
    }
    public void run()
    {
        log("Running controller");
        Sensorthread.start();
        do
        {        		
        	move();       		       	
        }
        while (wide_seek());

        end();
    }

    private void move()
    {
        log("Forward");

        do
        {
            naive_move();
        }
        while (narrow_seek());

        log("Stop");
    }

    private void naive_move()
    {
        log("tutu");  
        
        drive.forward();
        log("toto");
        System.out.println(sensor.onPath());
        //Button.ENTER.waitForPressAndRelease();     
        while (sensor.onPath())
        {
        	drive.forward();
            delay();
            System.out.println(sensor.onPath());
         if(isPressPalet)
         {
        	 drive.stop();
         }
         while(isPressPalet);
		if(isCatchPalet&&isFindPalet)
		{
			drive.stop();
		}
		while(isFindPalet);
            //Button.ENTER.waitForPressAndRelease();
        }

        drive.stop();
    }

    private boolean narrow_seek()
    {
        log("Seeking Path in Narrow Arc.");

        return sweepClockwise(SMALL_ROT) || sweepCounterClockwise(2 * SMALL_ROT) || sweepClockwise(SMALL_ROT);
    }

    private void skip_forward(int duration)
    {
        drive.forward();

        for (int i = 0; i < duration; i++)
        {
            delay();
        }

        drive.stop();
    }

    private boolean wide_seek()
    {
        skip_forward(SKIP_FORWARD);

        return sweepClockwise(MED_ROT) ||
               sweepCounterClockwise(MED_ROT + MAX_ROT) ||
               sweepClockwise(2 * MAX_ROT) ||
               sweepCounterClockwise(MAX_ROT);
    }

    private boolean sweepClockwise(int rot_limit)
    {
        log("Sweeping clockwise. Limit: " + rot_limit + " degrees.");

        drive.rotateClockwise();

        return sweep(rot_limit);
    }

    private boolean sweepCounterClockwise(int rot_limit)
    {
        log("Sweeping counter-clockwise. Limit: " +  rot_limit + " degrees");

        drive.rotateCounterClockwise();

        return sweep(rot_limit);
    }

    private boolean sweep(int rot_limit)
    {
        for (int i = 0; i < (rot_limit * DELAYS_PER_DEG); i++)
        {
            delay();

            System.out.println(sensor.onPath());
            //Button.ENTER.waitForPressAndRelease();
            
            if (sensor.onPath())
            {
                log("Path Detected");

                drive.stop();

                return true;
            }
        }

        log("Path not detected. Rotation limit exceeded.");

        drive.stop();

        return false;
    }

    private void end()
    {
        Sound.beepSequence();
        end=true;
        log("Program ends");
    }

    private void delay()
    {
        Delay.msDelay(DELAY);
    }

    private static void log(String msg)
    {
        System.out.println("log>\t" + msg);
    } 
    
}
