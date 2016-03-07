import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import sun.management.Sensor;

/**
 * Extends the EV3TouchSensor to provide it with isPressed() functionality.
 */

public class TouchSensor extends EV3TouchSensor
{
    public TouchSensor(Port port)
    {
        super(port);
    }

    public boolean isPressed()
    {
    	SensorMode mode=super.getTouchMode();
    	float[] sample = new float[mode.sampleSize()];
        mode.fetchSample(sample, 0);
        fetchSample(sample, 0);
        return sample[0]==1.0;
    }
}
