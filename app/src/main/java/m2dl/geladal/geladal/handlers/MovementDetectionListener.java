package m2dl.geladal.geladal.handlers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by Alexandre on 28/01/2016.
 */
public class MovementDetectionListener implements SensorEventListener {

    private IMovementDetected activity;

    public MovementDetectionListener(IMovementDetected activity) {
        this.activity = activity;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
