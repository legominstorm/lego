import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class UltrasonicSensor extends EV3UltrasonicSensor {
			
		private final static double rangMax=0.33;
		private final static double rangMin=0.05;
	
		public UltrasonicSensor(Port port)
		{
				super(port);
		}
		public boolean findPalet()
		{
			SampleProvider distanceMode=super.getDistanceMode();
			float[] sample=new float[distanceMode.sampleSize()];
			distanceMode.fetchSample(sample, 0);
			if(sample[0]<rangMax&&sample[0]>rangMin)
				return true;
			else
				return false;
		}			

}
